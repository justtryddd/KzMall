package com.gyb.api.order.feign.fallback;

import com.gyb.api.order.feign.StockUpadateClient;
import com.gyb.beans.ShoppingCartVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @date 2023/4/14 - 20:09
 */
@Component
public class StockUpadateClientFallback implements StockUpadateClient {
    @Override
    public int stockUpdate(List<ShoppingCartVo> list) {
        System.out.println("-------------stock-update服务降级");
        return 0;
    }
}
