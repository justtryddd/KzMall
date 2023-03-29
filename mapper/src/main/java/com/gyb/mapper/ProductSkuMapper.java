package com.gyb.mapper;

import com.gyb.entity.ProductSku;
import com.gyb.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuMapper extends GeneralDao<ProductSku> {

    //根据商品ID查询套餐中价格最低的套餐
    public List<ProductSku> selectLowestSkuByID(String pid);
}