package com.littlexx.shiro.demo.security.cache.impl;

import com.littlexx.shiro.demo.model.SysUser;
import com.littlexx.shiro.demo.security.cache.IRedisTokenCacheService;
import com.littlexx.shiro.demo.security.model.JwtTokenRedis;
import com.littlexx.shiro.demo.utils.JwtTokenUtil;
import com.littlexx.shiro.demo.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class RedisTokenCacheServiceImpl implements IRedisTokenCacheService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void cacheLoginInfo(String token, SysUser sysUser) {
        if (token == null || sysUser == null) {
            throw new IllegalArgumentException("token or loginBody can't be null");
        }
        JwtTokenRedis jwtTokenRedis = new JwtTokenRedis();
        jwtTokenRedis.setUsername(sysUser.getUsername());
        jwtTokenRedis.setSalt(sysUser.getSalt());
        jwtTokenRedis.setToken(token);
        jwtTokenRedis.setCreateDate(new Date());
        long expireTime = jwtTokenUtil.getExpireTime();
        jwtTokenRedis.setExpireSecond(expireTime);
        jwtTokenRedis.setExpireDate(new Date(System.currentTimeMillis() + expireTime));
        try {
            redisUtil.setCacheObject(sysUser.getUsername(), jwtTokenRedis, Integer.valueOf((int) expireTime), TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean tokenCached(String key, String token) {
        JwtTokenRedis jwtTokenRedis = new JwtTokenRedis();
        try {
            jwtTokenRedis = redisUtil.getCacheObject(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (jwtTokenRedis == null) return false;
        String redisToken = jwtTokenRedis.getToken();
        if (token.equals(redisToken)) {
            return true;
        }
        return false;
    }

    @Override
    public void refreshTokenCache(String token) {
        try {
            JwtTokenRedis tokenRedis = redisUtil.getCacheObject(jwtTokenUtil.getUsernameFromToken(token));
            tokenRedis.setExpireDate(new Date(System.currentTimeMillis() + jwtTokenUtil.getExpireTime()));
            redisUtil.setCacheObject(jwtTokenUtil.getUsernameFromToken(token),
                    tokenRedis,
                    Integer.valueOf((int) jwtTokenUtil.getExpireTime()),
                    TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
