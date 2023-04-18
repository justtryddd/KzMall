package com.gyb.service;

import com.gyb.vo.ResultVo;
import com.gyb.entity.ShoppingCart;

/**
 * @date 2023/3/20 - 0:19
 */

public interface ShoppingCartSerive {

    //添加购物车
    public ResultVo addShoppingCart(ShoppingCart shoppingCart);

    //显示购物车信息根据用户Id
    public ResultVo listShoppingCart(String userId);

    //更改购物车中指定商品的购买数量
    public ResultVo updateShoppingCart(int carId,String cart_num);

    //提交订单功能：将从前端获取字符串类型的cid（用“,”分隔）转换成list<Integer>集合，方便使用Dao层方法
    public ResultVo listSignedCartById(String cid);

    //相较于上面一个方法，这里的返回值对象多了sku_stock属性。该方法用于订单提交时获取购物车中已选中商品的信息
    public ResultVo listSignedCartById2(String cid);




}
