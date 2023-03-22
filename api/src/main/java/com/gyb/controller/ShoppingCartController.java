package com.gyb.controller;

import com.gyb.entity.ShoppingCart;
import com.gyb.service.ShoppingCartSerive;
import com.gyb.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2023/3/20 - 0:26
 */

@RestController
@CrossOrigin
@RequestMapping("/shopcart")
@Api(value = "提供购物车业务相关的接口",tags = "购物车管理")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartSerive shoppingCartSerive;


    @ApiOperation("将商品添加到购物车")
    @PostMapping("/add")
    public ResultVo addShoppingCart(@RequestBody ShoppingCart shoppingCart, @RequestHeader("token") String token){
        ResultVo resultVo = shoppingCartSerive.addShoppingCart(shoppingCart);
        return resultVo;
    }

    @ApiOperation("显示购物车")
    @GetMapping("/list")
    public ResultVo listShoppingCart(String userId,@RequestHeader String token){
        ResultVo resultVo = shoppingCartSerive.listShoppingCart(userId);
        return  resultVo;
    }

    @ApiOperation("修改购物车中商品购买数量")
    @PutMapping("/update/{cartId}/{cartNum}")
    public ResultVo updateBuyCountInCart(@PathVariable("cartId") Integer cartId,
                                     @PathVariable("cartNum") String cartNum,
                                     @RequestHeader String token){
        ResultVo resultVo = shoppingCartSerive.updateShoppingCart(cartId, cartNum);
        return  resultVo;
    }

    @ApiOperation("获取购物车中已勾选商品详细信息")
    @GetMapping("//listbycids")
    public ResultVo listSignedProductInCartById(String cids,@RequestHeader("token") String token) {
        ResultVo resultVo = shoppingCartSerive.listSignedCartById(cids);
        return resultVo;
    }
}
