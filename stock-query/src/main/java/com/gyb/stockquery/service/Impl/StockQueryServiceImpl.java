package com.gyb.stockquery.service.Impl;

import com.gyb.entity.ShoppingCartVo;
import com.gyb.stockquery.mapper.StockMapper;
import com.gyb.stockquery.service.StockQueryService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @date 2023/4/11 - 18:37
 */
@Service
public class StockQueryServiceImpl implements StockQueryService {

    @Autowired
    private StockMapper stockMapper;


    boolean temp = true;
    @Override
    public List<ShoppingCartVo> checkStock(List<Integer> list) {
        List<ShoppingCartVo> shoppingCartVos = stockMapper.checkStock(list);

        return shoppingCartVos;
    }
}
