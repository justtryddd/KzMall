package com.gyb.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyb.vo.ResultVo;
import com.gyb.vo.ResStatus;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @date 2023/3/17 - 13:52
 *
 */
@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String method = request.getMethod();        //复杂跨域问题需要
        if(method.equals("OPTIONS"))
            return true;

        String token = request.getHeader("token");

        if(token == null){
            ResultVo resultVo = new ResultVo(ResStatus.LOGIN_FAIL_NOT, "请先登录", null);
            doResponse(response,resultVo);
        }else {
            try {
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("gogogo");             //解析所用密码跟设置的密匙一致
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);   //验证该token是否满足可用条件（未国企，密匙正确）
                Claims body = claimsJws.getBody();          //获取token中的用户信息
                String subject = body.getSubject();
                body.get("key", Object.class);

                return true;
            } catch (ExpiredJwtException e) {
                ResultVo resultVo = new ResultVo(ResStatus.LOGIN_FAIL_OVERDUE, "登录过期，请重新登录！", null);
                doResponse(response,resultVo);
            } catch (UnsupportedJwtException e) {
                ResultVo resultVO = new ResultVo(ResStatus.NO, "Tonken不合法，请⾃重！", null);
                doResponse(response,resultVO);
            } catch (Exception e) {
                ResultVo resultVo = new ResultVo(ResStatus.LOGIN_FAIL_NOT, "请重新登录！", null);
                doResponse(response,resultVo);
            }
        }
        return false;
    }

    //这个方法理解的不好，IO问题应该是
    private void doResponse(HttpServletResponse response,ResultVo resultVo) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVo);
        writer.print(s);
        writer.flush();
        writer.close();
    }
}
