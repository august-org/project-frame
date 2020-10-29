package com.august.shiro;

import com.august.constant.Constant;
import com.august.exception.CustomException;
import com.august.exception.code.RestCode;
import com.august.utils.JwtTokenUtil;
import com.august.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/23 21:15
 */
@Slf4j
public class CustomMatcher extends HashedCredentialsMatcher {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CustomToken customToken = (CustomToken) token;
        String accessToken = (String) customToken.getPrincipal();
        String userId= JwtTokenUtil.getUserId(accessToken);
        log.info("doCredentialsMatch userId={}",userId);
        // 判断用户是否被删除
        if (redisUtil.hasKey(Constant.DELETED_USER_KEY+userId)){
            throw new CustomException(RestCode.ACCOUNT_HAS_DELETED_ERROR);
        }
        // 判断是否被锁定
        if (redisUtil.hasKey(Constant.ACCOUNT_LOCK_KEY+userId)){
            throw new CustomException(RestCode.ACCOUNT_LOCKED);
        }
        // 校验token
        if (!JwtTokenUtil.validateToken(accessToken)){
            throw new CustomException(RestCode.TOKEN_PAST_DUE);
        }
        return true;
    }
}
