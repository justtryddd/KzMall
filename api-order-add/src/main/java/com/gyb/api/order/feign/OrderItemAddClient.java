package com.gyb.api.order.feign;

import com.gyb.api.order.feign.fallback.OrderItemAddClientFallback;
import com.gyb.beans.ShoppingCartVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @date 2023/4/14 - 20:04
 */
@FeignClient(value = "orderitem-add",fallback = OrderItemAddClientFallback.class)
public interface OrderItemAddClient {

    @PostMapping("orderitem/add")
    public int orderItemAdd(List<ShoppingCartVo> list,@RequestParam("orderId")String orderId);
}
