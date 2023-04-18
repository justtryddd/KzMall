package com.gyb.usercheck.controller;

import com.gyb.entity.Users;
import com.gyb.usercheck.service.UserCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2023/4/14 - 22:42
 */
@RestController
public class UserCheckController{

    @Autowired
    private UserCheckService userCheckService;

    @GetMapping("/user/check")
    public Users userCheck(String name){

        return userCheckService.userCheck(name);
    }
}
