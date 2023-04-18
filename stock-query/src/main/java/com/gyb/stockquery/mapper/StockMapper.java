package com.gyb.stockquery.mapper;

import com.gyb.entity.ShoppingCartVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @date 2023/4/11 - 18:38
 */
@Repository
public interface StockMapper {

    public List<ShoppingCartVo> checkStock(List<Integer> list);
}
