package com.gyb.orderadd.service;

import com.gyb.entity.Orders;
import com.gyb.entity.ShoppingCartVo;

import java.util.List;

/**
 * @date 2023/4/11 - 19:05
 */
public interface OrderAddService {

    public List<ShoppingCartVo> orderAdd(String cids, Orders order);
}
