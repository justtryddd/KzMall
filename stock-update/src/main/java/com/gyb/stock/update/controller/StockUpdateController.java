package com.gyb.stock.update.controller;

import com.gyb.entity.ShoppingCartVo;
import com.gyb.stock.update.service.StockUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @date 2023/4/11 - 22:57
 */
public class StockUpdateController {

    @Autowired
    private StockUpdateService stockUpdateService;

    @PutMapping("/stock/update")
    public int stockUpdate(@RequestBody List<ShoppingCartVo> list){
        int i = stockUpdateService.stockUpdate(list);
        return i;
    }

}
