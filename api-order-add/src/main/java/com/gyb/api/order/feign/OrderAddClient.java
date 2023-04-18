package com.gyb.api.order.feign;

import com.gyb.api.order.feign.fallback.OrderAddClientFallback;
import com.gyb.beans.OrderPO;
import com.gyb.beans.ShoppingCartVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @date 2023/4/14 - 20:01
 */
@FeignClient(value = "order-add",fallback = OrderAddClientFallback.class)
public interface OrderAddClient {

    @PostMapping("/order/add")
    public List<ShoppingCartVo> addOrder(@RequestParam("cids") String cids, OrderPO order);
}
