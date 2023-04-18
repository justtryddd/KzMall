package com.gyb.orderitem.add.controller;

import com.gyb.entity.ShoppingCartVo;
import com.gyb.orderitem.add.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @date 2023/4/11 - 22:07
 */

public class OrderItemAddController {

    @Autowired
    private OrderService orderService;

    @PostMapping("orderitem/add")
    public int orderItemAdd(@RequestBody List<ShoppingCartVo> list, String orderId){
        return orderService.orderItemAdd(list,orderId);
    }
}
