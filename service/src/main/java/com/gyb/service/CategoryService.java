package com.gyb.service;

import com.gyb.vo.ResultVo;

/**
 * @date 2023/3/18 - 12:44
 */
public interface CategoryService {

    public ResultVo listCategories();

    public ResultVo listFirstLevelCategories();

}
