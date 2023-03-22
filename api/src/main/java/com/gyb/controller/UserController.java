package com.gyb.controller;

import com.gyb.entity.Users;
import com.gyb.service.UserService;
import com.gyb.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @date 2023/3/13 - 14:27
 */
@RestController                 //包含@ResponseBody和@Controller
@RequestMapping("/user")
@Api(value = "提供用的登录和注册接口",tags = "用户管理")
@CrossOrigin            //设置跨域访问
public class UserController {
    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "password", value = "用户登录密码", required = true),
            @ApiImplicitParam(dataType = "string", name = "username", value = "用户登录账号", required = true)
    })
    @GetMapping(value = "/login")
    public ResultVo checkLogin(@RequestParam("username") String name,@RequestParam("password") String password){
        return userService.checkLogin(name,password);
    }


    @Resource
    private UserService userService;        //用的接口，但会调用IMPL来使用


    @ApiImplicitParam(dataType = "object",name = "user",value = "注册所需信息",required = true)
    @PostMapping(value = "/register")
    public ResultVo register(@RequestBody Users user){
        return userService.register(user.getUsername(),user.getPassword());
    }
}