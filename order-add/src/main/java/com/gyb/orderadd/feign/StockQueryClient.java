package com.gyb.orderadd.feign;

import com.gyb.entity.ShoppingCartVo;
import com.gyb.orderadd.fallback.StockQueryClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @date 2023/4/11 - 19:08
 */

@FeignClient(value = "stock-query",fallback = StockQueryClientFallback.class)
public interface StockQueryClient {

    @GetMapping("/stock/query")
    public List<ShoppingCartVo> checkStock(@PathParam("list") List<Integer> list);
}
