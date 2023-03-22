package com.gyb.mapper;

import com.gyb.entity.OrderItem;
import com.gyb.general.GeneralDao;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemMapper extends GeneralDao<OrderItem> {
}