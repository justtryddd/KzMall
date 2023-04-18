package com.gyb.api.order.feign;

import com.gyb.api.order.feign.fallback.StockUpadateClientFallback;
import com.gyb.beans.ShoppingCartVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @date 2023/4/14 - 20:08
 */
@FeignClient(value = "stock-update",fallback = StockUpadateClientFallback.class)
public interface StockUpadateClient {

    @PutMapping("/stock/update")
    public int stockUpdate(@RequestBody List<ShoppingCartVo> list);

}
