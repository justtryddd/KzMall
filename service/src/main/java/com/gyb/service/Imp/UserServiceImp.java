package com.gyb.service.Imp;

import com.gyb.entity.Users;
import com.gyb.mapper.UsersMapper;
import com.gyb.service.UserService;
import com.gyb.utils.Base64Utils;
import com.gyb.utils.Md5Utils;
import com.gyb.vo.ResStatus;
import com.gyb.vo.ResultVo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2023/3/12 - 12:52
 */
@Service
public class UserServiceImp implements UserService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public ResultVo checkLogin(String name, String password) {

        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();      //内部类Criteria的初始化
        criteria.andEqualTo("username",name);
        List<Users> users = usersMapper.selectByExample(example);//根据条件查询

        if(users.size() == 0){
            //没有该用户信息
            return new ResultVo(ResStatus.NO,"该用户未注册",null);
        }else{
            //对输入的密码进行加密，再与user中的密码（已加密）进行匹配，匹配成功即登录成功，否则失败。
            String md5pwd = Md5Utils.md5(password);
            if(md5pwd.equals(users.get(0).getPassword())){
                //验证成功

/*                //生成令牌token，前端收到该token存入cookie中，作为令牌访问受限资源    ,
                    缺点：1、加密算法太简单容易被破解.2、无能完成时效性（登录过期）
                String token = Base64Utils.encode(name + "gogogo");*/

                //JWT技术来完成token的升级。
                JwtBuilder builder = Jwts.builder();

                Map<String,Object> map = new HashMap<>();
                map.put("key1","value1");
                map.put("key2","value2");

                String token = builder.setSubject(name)         //token中携带的数据
                        .setIssuedAt(new Date())                //tokem生成时间
                        .setId(users.get(0).getUserId() + "")   //设置tokenID 为用户ID
                        .setClaims(map)                            //设置用户权限信息  不明白
                        .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                        .signWith(SignatureAlgorithm.HS256,"gogogo")    //设置加密算法和密匙
                        .compact();


                return new ResultVo(ResStatus.OK,token,users.get(0));
            }else {
                //密码错误
                return new ResultVo(ResStatus.NO,"密码错误",null);
            }
        }
    }

    @Override
    @Transactional
    public ResultVo register(String username,String password) {

        synchronized (this) {           //同步监听器保证线程安全

            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username", username);
            List<Users> users1 = usersMapper.selectByExample(example);

            if (users1.size() != 0) {
                return new ResultVo(ResStatus.NO, "该用户名已被注册", null);
            } else {
                String md5pwd = Md5Utils.md5(password);
                Users users = new Users();
                users.setPassword(md5pwd);
                users.setUsername(username);
                users.setUserRegtime(new Date());
                users.setUserModtime(new Date());
                int i = usersMapper.insertUseGeneratedKeys(users);
                if (i > 0) {
                    return new ResultVo(ResStatus.OK, "注册成功", users);
                } else return new ResultVo(ResStatus.NO, "请稍后再试", null);
            }
        }
    }
}
