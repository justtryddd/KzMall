package com.gyb.service.job;

import com.github.wxpay.sdk.WXPay;
import com.gyb.entity.Orders;
import com.gyb.mapper.OrdersMapper;
import com.gyb.service.OrderService;
import com.gyb.service.config.WeChatPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2023/3/22 - 14:17
 */

@Component
public class OrderTimeOutCheckJob {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderService orderService;

    WXPay wxPay = new WXPay(new WeChatPayConfig());



    @Scheduled(cron = "0/60 * * * * ?")
    public void orderCheck() throws Exception {
        //1.每隔1分钟查询订单状态，找出30min内可能未付款的订单
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status",1);
        Date date = new Date(System.currentTimeMillis() - 30 * 60 * 60 * 1000);
        criteria.andLessThan("createTime",date);
        List<Orders> orders = ordersMapper.selectByExample(example);

        //2、向微信支付API确认这些订单是否已付款。一个订单一个订单的处理
        for (Orders order : orders) {
            String signOrder = order.getOrderId();
            Map<String, String> map = new HashMap<>();
            map.put("transaction_id", signOrder);
            Map<String, String> resp = wxPay.orderQuery(map);

            //若已付款，修改订单状态为代发货
            if (resp.get("trade_state").equals("SUCCESS")) {
                Orders orders1 = new Orders();
                orders1.setOrderId(signOrder);
                orders1.setStatus("2");
                ordersMapper.updateByPrimaryKeySelective(orders1);
            }
            //若未付款，告知支付API关闭支付链接（关闭订单），释放库存
            else {
                Map<String, String> resp1 = wxPay.closeOrder(map);
                System.out.println(resp1);
                orderService.restoreSkuStock(signOrder);
            }
        }
    }



}