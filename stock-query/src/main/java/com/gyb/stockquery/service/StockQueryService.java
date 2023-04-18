package com.gyb.stockquery.service;

import com.gyb.entity.ShoppingCartVo;

import java.util.List;

/**
 * @date 2023/4/11 - 18:36
 */
public interface StockQueryService {

    public List<ShoppingCartVo> checkStock(List<Integer> list);
}
