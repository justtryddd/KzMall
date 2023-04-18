package com.gyb.api.order.feign.fallback;

import com.gyb.api.order.feign.OrderItemAddClient;
import com.gyb.beans.ShoppingCartVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @date 2023/4/14 - 20:06
 */
@Component
public class OrderItemAddClientFallback implements OrderItemAddClient {
    @Override
    public int orderItemAdd(List<ShoppingCartVo> list, String orderId) {
        System.out.println("--------------orderItem-add的服务降级");
        return 0;
    }
}
