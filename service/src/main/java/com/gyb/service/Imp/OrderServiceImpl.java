package com.gyb.service.Imp;

import com.gyb.mapper.OrderItemMapper;
import com.gyb.mapper.OrdersMapper;
import com.gyb.mapper.ProductSkuMapper;
import com.gyb.mapper.ShoppingCartMapper;
import com.gyb.service.OrderService;
import com.gyb.service.config.RedissonConfig;
import com.gyb.utils.PageHelper;
import com.gyb.vo.ResultVo;
import com.gyb.entity.OrderItem;
import com.gyb.entity.Orders;
import com.gyb.entity.ProductSku;
import com.gyb.entity.ShoppingCartVo;
import com.gyb.vo.ResStatus;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @date 2023/3/20 - 21:00
 */
//            打勾的是后端完善的，其他是前端传来的
//        order_id      √
//        user_id
//        untitled      √
//        receiver_name
//        receiver_mobile
//        receiver_address
//        total_amount
//        actual_amount
//        pay_type
//        order_remark
//        status    √
//        delivery_type
//        delivery_flow_id
//        order_freight
//        delete_status
//        create_time   √
//        update_time
//        pay_time
//        delivery_time
//        flish_time
//        cancel_time
//        close_type
@Service
public class OrderServiceImpl implements OrderService {



    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private RedissonClient redissonClient;


    @Transactional
    public HashMap<String,String> addOrder(Orders orders, String cids) {

        HashMap<String, String> map = null;


        //1.判断库存是否足够。2.找到untitled（将订单中所有商品名字拼接一起）
        String[] split = cids.split(",");
        ArrayList<Integer> list = new ArrayList<>();
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }

        boolean isLock = true;
//        Map<String,String> cfmIsSameLock = new HashMap<>();
        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.listSignProductInCartPro(list);
        Map<String,RLock> locks = new HashMap<>();
        String[] locked = new String[shoppingCartVos.size()];
        for(int i=0;i<shoppingCartVos.size();i++){
            String skuId = shoppingCartVos.get(i).getSkuId();
            boolean ifAbsent = false;
            try {
                RLock lock = redissonClient.getLock(skuId);
                ifAbsent = lock.tryLock(10,3, TimeUnit.SECONDS);
                if(ifAbsent){
                    locked[i] =skuId;
                    locks.put(skuId,lock);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isLock = isLock && ifAbsent;

            /*String value = UUID.randomUUID().toString();
            Boolean ifAbsent = stringRedisTemplate.boundValueOps(skuId).setIfAbsent(value);
            if(ifAbsent){
                locked[i] = skuId;
                cfmIsSameLock.put(skuId,value);
            }
            isLock = isLock && ifAbsent;*/
        }

        //isLock为true，表示所有购物车中的商品加锁成功，将进行下一步
        if(isLock){
            try{
                String untitled = null;
                //比较库存，当第一次查询购物车记录之后，在加锁成功之前，可能被其他并发线程修改了库存
                boolean isStockEmpty = true;
                List<ShoppingCartVo> shoppingCartVos2 = shoppingCartMapper.listSignProductInCart(list);
                for (ShoppingCartVo shoppingCartVo : shoppingCartVos2) {
                    if(shoppingCartVo.getSkuStock() > Integer.parseInt(shoppingCartVo.getCartNum())){
                        isStockEmpty = false;
                    }
                    untitled = untitled + shoppingCartVo.getProductName();
                }
                //如果还有库存，将进行下一步
                if (isStockEmpty) {
                    map = new HashMap<>();

                    //2.保存订单信息
                    orders.setUntitled(untitled);
                    orders.setStatus("1");
                    orders.setCreateTime(new Date());
                    //生成订单编号
                    String orderId = UUID.randomUUID().toString().replace("-", "");
                    orders.setOrderId(orderId);
                    ordersMapper.insert(orders);

                    //3.生成商品快照
                    for (ShoppingCartVo shoppingCartVo : shoppingCartVos) {
                        int cnum = Integer.parseInt(shoppingCartVo.getCartNum());
                        String itemId = System.currentTimeMillis() + "" + (new Random().nextInt(89999) + 10000);
                        //因为VO版的购物车商品类有很多内容与快照表中属性一致，故直接赋值
                        OrderItem orderItem = new OrderItem(itemId, orderId, shoppingCartVo.getProductId(), shoppingCartVo.getProductName(),
                                shoppingCartVo.getProductImg(), shoppingCartVo.getSkuId(), shoppingCartVo.getSkuName(), shoppingCartVo.getProductPrice()
                                , cnum, new BigDecimal(shoppingCartVo.getSellPrice() * cnum), new Date(), new Date(), 0);
                        int insert = orderItemMapper.insert(orderItem);
                    }

                    //减库存
                    for (ShoppingCartVo shoppingCartVo : shoppingCartVos) {
                        int nowStock = shoppingCartVo.getSkuStock() - Integer.parseInt(shoppingCartVo.getCartNum());
                        ProductSku productSku = new ProductSku();
                        productSku.setSkuId(shoppingCartVo.getSkuId());
                        productSku.setStock(nowStock);
                        int i = productSkuMapper.updateByPrimaryKeySelective(productSku);
                    }

                    //删除购物车中这些商品记录
                    for (Integer cid : list) {
                        shoppingCartMapper.deleteByPrimaryKey(cid);
                    }

                    map.put("orderId", orderId);
                    map.put("productName", untitled);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }finally {
                for (int m = 0; m < locked.length; m++) {
                    String skuid = locked[m];
                    if (skuid != null && !skuid.equals("")) {
                        if(locks.get(skuid).isLocked() && locks.get(skuid).isHeldByCurrentThread())
                        locks.get(skuid).unlock();
                        System.out.println("解锁了吗");
                    }
                }
            }
            return map;
         }
         else {
             for (int i = 0; i < locked.length; i++) {
                 String skuid = locked[i];
                 if (skuid != null && !skuid.equals("")) {
                     locks.get(skuid).unlock();
                     //stringRedisTemplate.delete(skuid);
                 }
             }
             return null;
         }
    }

    public int updateOrderStatus(String orderId) {
        Orders orders = new Orders();
        orders.setStatus("2");
        int i = ordersMapper.updateByPrimaryKeySelective(orders);
        return i;
    }



    @Transactional
    public void restoreSkuStock(String orderId) {
        //1.修改订单状态为订单超时
        synchronized (this) {
            Orders order = new Orders();
            order.setOrderId(orderId);
            order.setStatus("6");
            ordersMapper.updateByPrimaryKeySelective(order);

            //2.释放库存
            Example example = new Example(OrderItem.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo(orderId);
            List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
            Integer buyCount = orderItems.get(0).getBuyCounts();
            String skuId = orderItems.get(0).getSkuId();

            ProductSku productSku = productSkuMapper.selectByPrimaryKey(skuId);
            productSku.setStock(productSku.getStock() + buyCount);
            int i = productSkuMapper.updateByPrimaryKeySelective(productSku);

            if (i > 0) {
                System.out.println("成功");
            }
        }
    }




/*    public ResultVo listAllOrdersById(String userId,int pageNum,int limit) {

        //Mapper层要查询第pageNum页的数据且是按照每页limit个数据的原则进行分页。
        //那就要找到要的pageNum页的一第个数据的位置start，然后limit限制查询的数量免得超出一页最大数量要求
        int start = (pageNum-1)*limit;
        List<Orders> orders = ordersMapper.selectAllOrdersByID(userId, start, limit);


        //我们要按照分页返回模板PageHelper来返回。
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        int count = ordersMapper.selectCountByExample(example);

        int totalPageNum = count % 10 == 0 ? count/limit : count/limit+1;

        return new ResultVo(ResStatus.OK,"success",new PageHelper<Orders>(count,totalPageNum,orders));
    }*/



    public ResultVo listAllStatusOrdersById(String userId, String status, int pageNum, int limit) {

        //Mapper层要查询第pageNum页的数据且是按照每页limit个数据的原则进行分页。
        //那就要找到要的pageNum页的一第个数据的位置start，然后limit限制查询的数量免得超出一页最大数量要求
        int start = (pageNum-1)*limit;
        List<Orders> orders = ordersMapper.selectAllOrdersByIDAndStatus(userId,status,start,limit);


        //我们要按照分页返回模板PageHelper来返回。
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        int count = ordersMapper.selectCountByExample(example);

        int totalPageNum = count % 10 == 0 ? count/limit : count/limit+1;

        return new ResultVo(ResStatus.OK,"success",new PageHelper<Orders>(count,totalPageNum,orders));
    }
}