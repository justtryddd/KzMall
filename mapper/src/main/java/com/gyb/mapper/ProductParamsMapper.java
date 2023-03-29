package com.gyb.mapper;

import com.gyb.entity.ProductParams;
import com.gyb.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductParamsMapper extends GeneralDao<ProductParams> {


    //根据三级分类ID查询商品品牌
    public List<String> selectProductBrandsByCid(int cid);

    //根据搜索关键字查询商品品牌
    public List<String> selectProductBrandByKW(String kw);


}