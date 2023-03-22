package com.gyb;


import com.gyb.entity.CategoryVo;
import com.gyb.mapper.CategoryMapper;
import com.gyb.service.Imp.UserServiceImp;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ApiApplicationTests {

    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    private CategoryMapper categoryMapper;





}
