package com.gyb.mapper;

import com.gyb.entity.Product;
import com.gyb.entity.ProductVO;
import com.gyb.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends GeneralDao<Product> {

    public List<ProductVO> selectRecommendProduct();


    /**
     * 查询指定类别下销量最高的6个产品
     * @param cid
     * @return
     */
    public List<ProductVO> selectTop6ByCategory(int cid);

}