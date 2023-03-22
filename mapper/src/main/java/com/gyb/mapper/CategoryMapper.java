package com.gyb.mapper;

import com.gyb.entity.Category;
import com.gyb.entity.CategoryVo;
import com.gyb.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends GeneralDao<Category> {

    //连接查询
    public List<CategoryVo> selectAllCategories();

    //子查询
    public List<CategoryVo> selectAllCategories2(int parentId);


    public List<CategoryVo> selectFirstLevelCategories();

}