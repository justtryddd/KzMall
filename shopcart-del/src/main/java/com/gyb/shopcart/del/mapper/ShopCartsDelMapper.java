package com.gyb.shopcart.del.mapper;

import com.gyb.entity.ShoppingCart;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @date 2023/4/12 - 0:13
 */
@Repository
public interface ShopCartsDelMapper extends Mapper<ShoppingCart>, MySqlMapper<ShoppingCart> {
}
