package com.gyb.api.order.feign.fallback;

import com.gyb.api.order.feign.ShopCartsDelClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @date 2023/4/14 - 20:11
 */
@Component
public class ShopCartsDelClientFallback implements ShopCartsDelClient {
    @Override
    public int deleteCarts(String cids) {
        System.out.println("------------shopCart-del服务降级");
        return 0;
    }
}
