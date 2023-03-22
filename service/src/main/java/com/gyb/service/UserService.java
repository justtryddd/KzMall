package com.gyb.service;

import com.gyb.entity.Users;
import com.gyb.vo.ResultVo;

/**
 * @date 2023/3/12 - 12:52
 */
public interface UserService {
    public ResultVo checkLogin(String name, String password);

    public ResultVo register(String name,String pwd);
}
