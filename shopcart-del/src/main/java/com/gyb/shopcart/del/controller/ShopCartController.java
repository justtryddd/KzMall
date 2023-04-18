package com.gyb.shopcart.del.controller;

import com.gyb.shopcart.del.service.ShopCartsDelService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2023/4/11 - 23:50
 */
@RestController
public class ShopCartController {

    @Autowired
    private ShopCartsDelService shopCartsDelService;

    @DeleteMapping("/shopcart/del")
    public int deleteCarts(String cids){
        return shopCartsDelService.shopCartsDel(cids);
    }

}
