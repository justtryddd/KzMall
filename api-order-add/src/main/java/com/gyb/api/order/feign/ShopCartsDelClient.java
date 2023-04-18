package com.gyb.api.order.feign;

import com.gyb.api.order.feign.fallback.ShopCartsDelClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * @date 2023/4/14 - 20:10
 */
@FeignClient(value = "shopcart-del",fallback = ShopCartsDelClientFallback.class)
public interface ShopCartsDelClient {

    @DeleteMapping("/shopcart/del")
    public int deleteCarts(String cids);
}
