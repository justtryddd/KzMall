package com.gyb;


import com.gyb.mapper.CategoryMapper;
import com.gyb.service.Imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiApplicationTests {

    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    private CategoryMapper categoryMapper;





}
