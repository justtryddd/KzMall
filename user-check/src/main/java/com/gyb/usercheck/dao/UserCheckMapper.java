package com.gyb.usercheck.dao;

import com.gyb.entity.Users;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @date 2023/4/14 - 22:47
 */
@Repository
public interface UserCheckMapper extends MySqlMapper<Users>, Mapper<Users> {

}
