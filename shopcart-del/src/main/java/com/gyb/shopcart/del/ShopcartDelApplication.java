package com.gyb.shopcart.del;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.gyb.shopcart.del.mapper")
public class ShopcartDelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopcartDelApplication.class, args);
    }

}
