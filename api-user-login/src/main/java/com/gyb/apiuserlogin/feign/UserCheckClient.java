package com.gyb.apiuserlogin.feign;

import com.gyb.apiuserlogin.feign.fallback.UserCheckClientFallback;
import com.gyb.beans.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @date 2023/4/14 - 22:41
 */
@FeignClient(value = "user-check",fallback = UserCheckClientFallback.class)
public interface UserCheckClient {

    @GetMapping("/user/check")
    public Users userCheck(@RequestParam("name") String name);

}
