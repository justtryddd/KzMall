package com.gyb.orderitem.add.service.Impl;

import com.gyb.entity.OrderItem;
import com.gyb.entity.ShoppingCartVo;
import com.gyb.orderitem.add.mapper.OrderItemAddMapper;
import com.gyb.orderitem.add.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @date 2023/4/11 - 22:25
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderItemAddMapper orderItemAddMapper;

    @Override
    public int orderItemAdd(List<ShoppingCartVo> list, String orderId) {

        int j = 1;

        for (ShoppingCartVo sc : list) {
            String itemId = System.currentTimeMillis() + "" + (new Random().nextInt(89999) + 10000);
            int cnum = Integer.parseInt(sc.getCartNum());
            OrderItem orderItem = new OrderItem(itemId, orderId, sc.getProductId(), sc.getProductName(), sc.getProductImg(), sc.getSkuId(), sc.getSkuName(), new BigDecimal(sc.getSellPrice()), cnum, new BigDecimal(sc.getSellPrice() * cnum), new Date(), new Date(), 0);
            int i = orderItemAddMapper.insert(orderItem);
            j *= i;
        }
        return j;
    }
}
