package com.gyb.service.Imp;

import com.gyb.entity.ShoppingCartVo;
import com.gyb.vo.ResultVo;
import com.gyb.entity.ShoppingCart;
import com.gyb.mapper.ShoppingCartMapper;
import com.gyb.service.ShoppingCartSerive;
import com.gyb.vo.ResStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @date 2023/3/20 - 0:21
 */
@Service
public class ShoppingCartSeriveImpl implements ShoppingCartSerive {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    public ResultVo addShoppingCart(ShoppingCart shoppingCart) {

        shoppingCart.setCartTime(new Date().toString());
        int i = shoppingCartMapper.insertUseGeneratedKeys(shoppingCart);
        if(i > 0){
            return new ResultVo(ResStatus.OK,"success",null);
        }else {
            return new ResultVo(ResStatus.NO,"添加失败",null);
        }
    }


    public ResultVo listShoppingCart(String userId){

        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.selectShopCartByUserId(userId);

        return new ResultVo(ResStatus.OK, "success", shoppingCartVos);
    }

    @Override
    public ResultVo updateShoppingCart(int cartId, String cartNum) {
        int i = shoppingCartMapper.updateShopCartNumById(cartId, cartNum);
        if(i>0){
            return new ResultVo(ResStatus.OK,"修改成功",null);
        }else {
            return new ResultVo(ResStatus.OK,"修改失败",null);
        }
    }

    public ResultVo listSignedCartById(String cid) {
        String[] split = cid.split(",");
        ArrayList<Integer> list = new ArrayList<>();
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }
        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.listSignProductInCart(list);

        return new ResultVo(ResStatus.OK,"success",shoppingCartVos);
    }

    @Override
    public ResultVo listSignedCartById2(String cid) {
        String[] split = cid.split(",");
        ArrayList<Integer> list = new ArrayList<>();
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }
        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.listSignProductInCartPro(list);

        return new ResultVo(ResStatus.OK,"success",shoppingCartVos);
    }
}
