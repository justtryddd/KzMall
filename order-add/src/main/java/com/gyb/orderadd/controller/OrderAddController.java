package com.gyb.orderadd.controller;

import com.gyb.beans.OrderPO;
import com.gyb.entity.Orders;
import com.gyb.entity.ShoppingCartVo;
import com.gyb.orderadd.service.OrderAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @date 2023/4/11 - 18:59
 */

public class OrderAddController {

    @Autowired
    private OrderAddService orderAddService;

    @PostMapping("/order/add")
    public List<ShoppingCartVo> addOrder(String cids,@RequestBody Orders order){
        List<ShoppingCartVo> shoppingCartVos = orderAddService.orderAdd(cids, order);
        return shoppingCartVos;
    }
}
