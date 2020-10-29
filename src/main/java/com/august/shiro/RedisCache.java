package com.august.shiro;

import com.alibaba.fastjson.JSON;
import com.august.constant.Constant;
import com.august.utils.JwtTokenUtil;
import com.august.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/28 16:56
 */
@Slf4j
public class RedisCache<K, V> implements Cache<K, V> {
    private RedisUtil redisUtil;
    private String cacheKey = Constant.IDENTIFY_CACHE_KEY;

    public RedisCache(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private String getCacheKey(K token) {
        if (token != null) {
            return this.cacheKey + JwtTokenUtil.getUserId(token.toString());
        }
        return null;
    }

    @Override
    public V get(K k) throws CacheException {
        log.info("shiro从缓存中获取key:[{}]", k);
        if (k != null) {
            String key = getCacheKey(k);
            Object o = redisUtil.get(key);
            if (o != null) {
                SimpleAuthorizationInfo info = JSON.parseObject(o.toString(), SimpleAuthorizationInfo.class);
                return (V) info;
            }
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        log.info("put key [{}]", k);
        if (k != null) {
            String key = getCacheKey(k);
            redisUtil.set(key, v, 24L, TimeUnit.HOURS);
            return v;
        }
        log.warn("Saving a null key is meaningless, return value directly without call Redis.");
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        log.info("remove key [{}]", k);
        if (k != null) {
            String key = getCacheKey(k);
            Object o = redisUtil.get(key);
            redisUtil.delete(key);
            return (V) o;
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        log.debug("clear cache");
        Set<String> keys = null;
        try {
            keys = redisUtil.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
        }
        if (keys != null && keys.size() > 0) {
            for (Object key : keys) {
                redisUtil.delete(key.toString());
            }
        }
    }

    @Override
    public int size() {
        int result = 0;
        try {
            result = redisUtil.keys(this.cacheKey + "*").size();
        } catch (Exception e) {
            log.error("get keys error", e);
        }
        return result;
    }

    @Override
    public Set<K> keys() {
        Set<String> keys;
        try {
            keys = redisUtil.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        Set<K> convertedKeys = new HashSet<>();
        for (String key : keys) {
            try {
                convertedKeys.add((K) key);
            } catch (Exception e) {
                log.error("deserialize keys error", e);
            }
        }
        return convertedKeys;
    }

    @Override
    public Collection<V> values() {
        Set<String> keys;
        try {
            keys = redisUtil.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get values error", e);
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        List<V> values = new ArrayList<>(keys.size());
        for (String key : keys) {
            V value = null;
            try {
                value = (V) redisUtil.get(key);
            } catch (Exception e) {
                log.error("deserialize values= error", e);
            }
            if (value != null) {
                values.add(value);
            }
        }
        return Collections.unmodifiableList(values);
    }
}
