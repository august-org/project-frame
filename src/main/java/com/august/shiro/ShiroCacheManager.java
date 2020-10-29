package com.august.shiro;

import com.august.utils.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/28 16:55
 */
public class ShiroCacheManager implements CacheManager {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<>(redisUtil);
    }
}
