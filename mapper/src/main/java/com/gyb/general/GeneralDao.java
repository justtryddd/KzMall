package com.gyb.general;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @date 2023/3/14 - 17:56
 */
public interface GeneralDao<T> extends Mapper<T>, MySqlMapper<T> {
}
