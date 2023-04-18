package com.gyb.orderadd.service.Impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gyb.entity.Orders;
import com.gyb.entity.ShoppingCartVo;
import com.gyb.orderadd.feign.StockQueryClient;
import com.gyb.orderadd.mapper.OrderAddMapper;
import com.gyb.orderadd.service.OrderAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @date 2023/4/11 - 19:06
 */

@Service
public class OrderAddServiceImpl implements OrderAddService {
    @Autowired
    private OrderAddMapper orderAddMapper;

    @Resource
    private StockQueryClient stockQueryClient;


    boolean temp = true;
    String totalName = "";

    @Override
    public List<ShoppingCartVo> orderAdd(String cids, Orders order) {

        String[] split = cids.split(",");
        ArrayList<Integer> list = new ArrayList<>();
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }

        //获取选中购物车中商品的信息，便于修改库存
        List<ShoppingCartVo> shoppingCartVos = stockQueryClient.checkStock(list);

        for (ShoppingCartVo sc : shoppingCartVos) {
            if(Integer.parseInt(sc.getCartNum()) > sc.getSkuStock()){
                temp = false;
                break;
            }
            totalName += sc.getProductName() + ",";

        }
        if(!temp){
            return null;
        }else{
            //库存充足 保存订单信息
            order.setUntitled(totalName);
            order.setCreateTime(new Date());
            order.setStatus("1");
            orderAddMapper.insert(order);
            return shoppingCartVos;
        }

    }
}
