package com.gyb.apiuserlogin.service;

import com.gyb.vo.ResultVo;

/**
 * @date 2023/4/14 - 22:37
 */
public interface UserLoginService {

    public ResultVo userLogin(String username,String password);
}
