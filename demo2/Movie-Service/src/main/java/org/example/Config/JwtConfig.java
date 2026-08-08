package org.example.Config;

import org.example.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


//将application.yml中的jwt令牌signature的两要素————密钥和过期时间映射为java封装工具类
@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    //在jwt工具类初始化对象时构建密钥和过期时间
    @PostConstruct
    public void init() {
        JwtUtils.init(secret, expiration);
    }
}
