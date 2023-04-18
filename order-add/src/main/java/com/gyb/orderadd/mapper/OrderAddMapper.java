package com.gyb.orderadd.mapper;

import com.gyb.entity.Orders;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @date 2023/4/11 - 20:41
 */
@Repository
public interface OrderAddMapper extends MySqlMapper<Orders>, Mapper<Orders> {
}
