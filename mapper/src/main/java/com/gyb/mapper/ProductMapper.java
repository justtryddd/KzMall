package com.gyb.mapper;

import com.gyb.entity.Product;
import com.gyb.entity.ProductParams;
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


    //根据三级分类ID分页查询商品信息          为了实现首页中的分类商品栏中的商品点击可看详情
    public List<ProductVO> selectProductByCategoryId(int cid,int start,int limit);

    //根据商品名字关键字分页查询商品信息          为了实现首页中的分类商品栏中的商品点击可看详情
    public List<ProductVO> selectProductByProductKw(String kw,int start,int limit);


}