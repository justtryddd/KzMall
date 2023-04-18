package com.gyb.api.order.feign.fallback;

import com.gyb.api.order.feign.OrderAddClient;
import com.gyb.beans.OrderPO;
import com.gyb.beans.ShoppingCartVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @date 2023/4/14 - 20:03
 */
@Component
public class OrderAddClientFallback implements OrderAddClient {
    @Override
    public List<ShoppingCartVo> addOrder(String cids, OrderPO order) {
        System.out.println("------------------------order-add的服务降级");
        return null;
    }
}
