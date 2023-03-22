package com.gyb.service;

import com.gyb.vo.ResultVo;

/**
 * @date 2023/3/20 - 14:16
 */
public interface UserAddrService {

    //根据用户Id获取其收货地址信息
    public ResultVo listUserAddrById(int userId);
}
