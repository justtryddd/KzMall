package com.gyb.usercheck.service.Impl;

import com.gyb.entity.Users;
import com.gyb.usercheck.dao.UserCheckMapper;
import com.gyb.usercheck.service.UserCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @date 2023/4/14 - 22:45
 */
@Service
public class UserCheckServiceImpl implements UserCheckService {

    @Autowired
    private UserCheckMapper userCheckMapper;

    @Override
    public Users userCheck(String name) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",name);
        List<Users> users = userCheckMapper.selectByExample(example);

        if(users.get(0) != null){
            return users.get(0);
        }else {
            return null;
        }

    }
}
