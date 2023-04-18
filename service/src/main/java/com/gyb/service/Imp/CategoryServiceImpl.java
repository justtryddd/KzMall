package com.gyb.service.Imp;

import com.gyb.entity.CategoryVo;
import com.gyb.mapper.CategoryMapper;
import com.gyb.service.CategoryService;
import com.gyb.vo.ResultVo;
import com.gyb.vo.ResStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @date 2023/3/18 - 12:46
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResultVo listCategories() {
        List<CategoryVo> categoryVos = categoryMapper.selectAllCategories();

        return new ResultVo(ResStatus.OK,"success",categoryVos);

    }

    @Override
    public ResultVo listFirstLevelCategories() {
        List<CategoryVo> categoryVos = categoryMapper.selectFirstLevelCategories();
        return new ResultVo(ResStatus.OK,"success",categoryVos);
    }


}
