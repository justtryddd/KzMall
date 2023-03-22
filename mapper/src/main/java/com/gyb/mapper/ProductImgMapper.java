package com.gyb.mapper;

import com.gyb.entity.ProductImg;
import com.gyb.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgMapper extends GeneralDao<ProductImg> {

    public List<ProductImg> selectProductImgByProductId(int productId);

}