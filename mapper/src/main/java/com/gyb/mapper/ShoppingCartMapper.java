package com.gyb.mapper;

import com.gyb.entity.ShoppingCart;
import com.gyb.entity.ShoppingCartVo;
import com.gyb.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartMapper extends GeneralDao<ShoppingCart> {

    //根据用户Id查询购物车信息 自定方法那就是联表查询
    public List<ShoppingCartVo> selectShopCartByUserId(String userId);

    public int updateShopCartNumById(@Param("cartId") int cartId,@Param("cartNum")String cartNum);

    public List<ShoppingCartVo> listSignProductInCart(List<Integer> cids);

    public List<ShoppingCartVo> listSignProductInCartPro(List<Integer> cids);
}