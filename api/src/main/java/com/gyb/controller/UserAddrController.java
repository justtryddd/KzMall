package com.gyb.controller;

import com.gyb.service.UserAddrService;
import com.gyb.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2023/3/20 - 15:02
 */
@RestController
@CrossOrigin
@RequestMapping("/useraddr")
@Api(value = "收货地址相关接口",tags = "收获地址管理")
public class UserAddrController {

    @Autowired
    private UserAddrService userAddrService;

    @ApiOperation("显示当前用户的所有收货地址")
    @GetMapping("/list")
    public ResultVo listUserAddrById(Integer userId, @RequestHeader("token") String token) {

        ResultVo resultVo = userAddrService.listUserAddrById(userId);
        return resultVo;
    }



}
