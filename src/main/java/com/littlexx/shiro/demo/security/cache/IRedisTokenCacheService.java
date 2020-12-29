package com.littlexx.shiro.demo.security.cache;

import com.littlexx.shiro.demo.model.SysUser;

public interface IRedisTokenCacheService {

    public void cacheLoginInfo(String token, SysUser sysUser);

    /**
     * 根据key判断token是否已经被缓存
     * @param key
     * @return
     */
    public Boolean tokenCached(String key, String token);

    /**
     * 刷新redis中token缓存
     * @param token
     */
    public void refreshTokenCache(String token);
}
