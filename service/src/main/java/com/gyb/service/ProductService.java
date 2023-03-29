package com.gyb.service;

import com.gyb.vo.ResultVo;

/**
 * @date 2023/3/18 - 14:03
 */
public interface ProductService {

    public ResultVo listRecommendProduct();

    public ResultVo getProductBasicInfo(String productId);

    public ResultVo getProductParams(String productId);

    /**
     *根据商品ID分页查询评论
     * @param productId 商品ID
     * @param pageNum 查询页码
     * @param limit 每页显示条数
     * @return
     */
    public ResultVo listCommentsByProductId(String productId,int pageNum,int limit);


    //统计评论（好评、差评、中评、评论数、好评率）
    public ResultVo productComments(String productId);

    //根据三级分类ID分页查询商品信息          为了实现首页中的分类商品栏中的商品点击可看详情
    public ResultVo listProductByCategoryId(int cid,int pageNum,int limit);

    //根据三级分类ID分页查询商品品牌
    public ResultVo listProductBrandByCid(int cid);

    //根据商品关键字分页查询商品信息          为了实现首页中搜索商品功能
    public ResultVo listProductByProductKW(String kw, int pageNum, int limit);

    //根据商品关键字查询商品品牌
    public ResultVo listBrandsByProductKW(String kw);
}
