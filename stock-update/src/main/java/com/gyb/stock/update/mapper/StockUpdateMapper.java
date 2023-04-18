package com.gyb.stock.update.mapper;

import com.gyb.entity.ProductSku;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @date 2023/4/11 - 23:31
 */
public interface StockUpdateMapper extends MySqlMapper<ProductSku>, Mapper<ProductSku> {
}
