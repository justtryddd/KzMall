package com.gyb.orderitem.add.service;

import com.gyb.entity.ShoppingCartVo;

import java.util.List;

/**
 * @date 2023/4/11 - 22:07
 */
public interface OrderService {

    public int orderItemAdd(List<ShoppingCartVo> list,String orderId);

}

