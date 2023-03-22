package com.gyb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @date 2023/3/18 - 12:25
 */

//
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryVo {

    private Integer categoryId;
    private String categoryName;
    private Integer categoryLevel;
    private Integer parentId;
    private String categoryIcon;
    private String categorySlogan;
    private String categoryPic;
    private String categoryBgColor;
    //实现首页的类别显示
    private List<CategoryVo> categories;
    //实现首页分类商品推荐
    private List<ProductVO> products;

}