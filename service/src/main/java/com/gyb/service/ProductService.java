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
     *
     * @param productId 商品ID
     * @param pageNum 查询页码
     * @param limit 每页显示条数
     * @return
     */
    public ResultVo listCommentsByProductId(String productId,int pageNum,int limit);


    public ResultVo ProductComments(String productId);


}
