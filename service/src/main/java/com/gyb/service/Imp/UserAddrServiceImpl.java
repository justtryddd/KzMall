package com.gyb.service.Imp;

import com.gyb.entity.UserAddr;
import com.gyb.mapper.UserAddrMapper;
import com.gyb.service.UserAddrService;
import com.gyb.vo.ResStatus;
import com.gyb.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @date 2023/3/20 - 14:17
 */
@Service
public class UserAddrServiceImpl implements UserAddrService {

    @Autowired
    private UserAddrMapper userAddrMapper;

    public ResultVo listUserAddrById(int userId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        List<UserAddr> userAddrs = userAddrMapper.selectByExample(example);

        return new ResultVo(ResStatus.OK,"success",userAddrs);
    }
}
