package com.gyb.orderitem.add.mapper;

import com.gyb.entity.OrderItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @date 2023/4/11 - 22:03
 */
@Repository
public interface OrderItemAddMapper extends Mapper<OrderItem>, MySqlMapper<OrderItem> {
}
