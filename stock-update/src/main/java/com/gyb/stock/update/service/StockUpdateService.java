package com.gyb.stock.update.service;

import com.gyb.entity.ShoppingCartVo;

import java.util.List;

/**
 * @date 2023/4/11 - 23:14
 */
public interface StockUpdateService {
    public int stockUpdate(List<ShoppingCartVo> list);
}
