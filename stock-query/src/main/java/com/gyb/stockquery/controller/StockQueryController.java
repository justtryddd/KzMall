package com.gyb.stockquery.controller;

import com.gyb.entity.ShoppingCartVo;
import com.gyb.stockquery.service.StockQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @date 2023/4/11 - 18:35
 */
public class StockQueryController {

    @Autowired
    private StockQueryService stockQueryService;

    @GetMapping("/stock/query")
    public List<ShoppingCartVo> checkStock(List<Integer> list){

        System.out.println("stock-query执行");
        return stockQueryService.checkStock(list);
    }

}
