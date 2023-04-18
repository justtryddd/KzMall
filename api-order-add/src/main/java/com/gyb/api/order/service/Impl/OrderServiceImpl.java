package com.gyb.api.order.service.Impl;

import com.gyb.api.order.feign.OrderAddClient;
import com.gyb.api.order.feign.OrderItemAddClient;
import com.gyb.api.order.feign.ShopCartsDelClient;
import com.gyb.api.order.feign.StockUpadateClient;
import com.gyb.api.order.service.OrderService;
import com.gyb.beans.OrderPO;
import com.gyb.beans.ShoppingCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @date 2023/4/11 - 18:16
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderAddClient orderAddClient;

    @Resource
    private OrderItemAddClient orderItemAddClient;

    @Resource
    private StockUpadateClient stockUpadateClient;

    @Resource
    private ShopCartsDelClient shopCartsDelClient;

    @Override
    public HashMap<String, String> addOrder(String cids, OrderPO order) {

        HashMap<String, String> map = null;

        //创建订单号
        String orderId = UUID.randomUUID().toString().replace("-","");  
        order.setOrderId(orderId);
        //1.添加订单
        List<ShoppingCartVo> carts = orderAddClient.addOrder(cids, order);

        if(carts != null) {
            //2.添加订单快照
            int i = orderItemAddClient.orderItemAdd(carts, orderId);
            if (i > 0) {
                //3.修改库存
                int j = stockUpadateClient.stockUpdate(carts);

                if (j > 0) {
                    //4.删除购物车已下单商品信息
                    int k = shopCartsDelClient.deleteCarts(cids);

                    if (k > 0) {
                        String totalName = null;
                        for (int l = 0; l < carts.size(); l++) {
                            totalName += carts.get(l).getSkuName();
                            totalName = l == carts.size() - 1 ? totalName : ",";
                        }
                        map = new HashMap<>();
                        map.put("orderId", orderId);
                        map.put("productName", totalName);
                    }
                }
            }
            return map;
        }else {
            return null;
        }
    }
}
