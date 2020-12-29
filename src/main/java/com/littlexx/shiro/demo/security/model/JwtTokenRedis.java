package com.littlexx.shiro.demo.security.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JwtTokenRedis implements Serializable {

    private static final long serialVersionUID = 7846175278936529262L;

    /**
     * 登录用户名称
     */
    private String username;

    /**
     * 登录盐值
     */
    private String salt;

    /**
     * 登录token
     */
    private String token;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 多长时间过期，默认一小时
     */
    private long expireSecond;

    /**
     * 过期日期
     */
    private Date expireDate;

}
