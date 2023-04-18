package com.gyb.apiuserlogin.feign.fallback;

import com.gyb.apiuserlogin.feign.UserCheckClient;
import org.springframework.stereotype.Component;

/**
 * @date 2023/4/14 - 22:56
 */
@Component
public class UserCheckClientFallback implements UserCheckClient {
    @Override
    public int userCheck(String name) {
        System.out.println("--------user-check服务降级");
        return 0;
    }
}
