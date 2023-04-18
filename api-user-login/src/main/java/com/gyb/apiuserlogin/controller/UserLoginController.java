package com.gyb.apiuserlogin.controller;

import com.gyb.apiuserlogin.service.UserLoginService;
import com.gyb.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @date 2023/3/13 - 14:27
 */
@RestController         //包含@ResponseBody和@Controller
@RequestMapping("/user")
@CrossOrigin            //设置跨域访问
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @GetMapping(value = "/login")
    public ResultVo checkLogin(@RequestParam("username") String name, @RequestParam("password") String password){
        ResultVo resultVo = userLoginService.userLogin(name, password);
        return resultVo;
    }

}