package com.gyb.shopcart.del.service.Impl;

import com.gyb.shopcart.del.mapper.ShopCartsDelMapper;
import com.gyb.shopcart.del.service.ShopCartsDelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @date 2023/4/12 - 0:12
 */

@Service
public class ShopCartsDelServiceImp implements ShopCartsDelService {

    @Autowired
    private ShopCartsDelMapper shopCartsDelMapper;

    @Override
    public int shopCartsDel(String cids) {

        int j = 1;

        String[] split = cids.split(",");
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < split.length; i++) {
            int cid = Integer.parseInt(split[i]);
            int k = shopCartsDelMapper.deleteByPrimaryKey(cid);
            j *= k;
        }
        return j;
    }
}
