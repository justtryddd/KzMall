package com.gyb.mapper;

import com.gyb.entity.ProductComments;
import com.gyb.entity.ProductCommentsVo;
import com.gyb.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentsMapper extends GeneralDao<ProductComments> {


    /**
     *
     * @param productId 商品ID
     * @param start 起始索引
     * @param limit 查询条数
     * @return
     */
    public List<ProductCommentsVo> getCommentsById(@Param("productId") String productId,
                                                    @Param("start") int start,
                                                   @Param("limit") int limit);
}