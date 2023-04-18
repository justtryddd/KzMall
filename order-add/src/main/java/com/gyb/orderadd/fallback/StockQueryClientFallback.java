package com.gyb.orderadd.fallback;

import com.gyb.entity.ShoppingCartVo;
import com.gyb.orderadd.feign.StockQueryClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @date 2023/4/11 - 20:31
 */
@Component
public class StockQueryClientFallback implements StockQueryClient {

    @Override
    public List<ShoppingCartVo> checkStock(List<Integer> list) {
        System.out.println("----------stock-query服务降级");
        return null;
    }
}
