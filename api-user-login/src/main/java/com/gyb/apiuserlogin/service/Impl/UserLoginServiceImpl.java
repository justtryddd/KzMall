package com.gyb.apiuserlogin.service.Impl;

import com.gyb.apiuserlogin.feign.UserCheckClient;
import com.gyb.apiuserlogin.service.UserLoginService;
import com.gyb.beans.Users;
import com.gyb.utils.Md5Utils;
import com.gyb.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @date 2023/4/14 - 22:38
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Resource
    private UserCheckClient userCheckClient;

    @Override
    public ResultVo userLogin(String username, String password) {
        Users user = userCheckClient.userCheck(username);
        if(user != null){
            if(user.getPassword().equals(Md5Utils.md5(password))){

            }
        }
    }
}
