package com.gyb.api.order.service;

import com.gyb.beans.OrderPO;

import java.util.HashMap;

/**
 * @date 2023/4/11 - 18:02
 */
public interface OrderService {

    public HashMap<String,String> addOrder(String cids, OrderPO order);
}
