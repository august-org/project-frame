package com.august.utils;

import com.alibaba.druid.util.StringUtils;
import com.august.config.TokenConfig;
import com.august.constant.Constant;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

/**
 * @author AUGUST
 * @description JWT工具类
 * @date 2020/10/15
 */
@Slf4j
public class JwtTokenUtil {
    private static String securityKey;
    private static Duration accessTokenExpireTime;
    private static Duration refreshTokenExpireTime;
    private static Duration refreshTokenExpireAppTime;
    private static String issuser;

    public static void setJwtProperties(TokenConfig config) {
        securityKey = config.getSecurityKey();
        accessTokenExpireTime = config.getAccessTokenExpireTime();
        refreshTokenExpireTime = config.getRefreshTokenExpireTime();
        refreshTokenExpireAppTime = config.getRefreshTokenExpireAppTime();
        issuser = config.getIssuser();
    }

    /**
     * 签发Token
     *
     * @param issuser   签发人
     * @param subject   代表JWT的主体，即他的所有人，一般为用户id
     * @param claims    存储在JWT里面的信息 用户权限/角色信息等
     * @param ttlMillis 有效时间（毫秒）
     * @param secret    密钥
     * @return
     */
    public static String generateToken(String issuser, String subject, Map<String, Object> claims, long ttlMillis, String secret) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long timeMillis = System.currentTimeMillis();
        byte[] signKey = DatatypeConverter.parseBase64Binary(secret);
        JwtBuilder builder = Jwts.builder();
        builder.setHeaderParam("typ", "JWT");
        if (claims != null) {
            builder.setClaims(claims);
        }
        if (!StringUtils.isEmpty(issuser)) {
            builder.setIssuer(issuser);
        }
        if (!StringUtils.isEmpty(subject)) {
            builder.setSubject(subject);
        }
        builder.setIssuedAt(new Date(timeMillis));
        if (ttlMillis >= 0) {
            long expMillis = timeMillis + ttlMillis;
            builder.setExpiration(new Date(expMillis));
        }
        builder.signWith(signatureAlgorithm, signKey);
        return builder.compact();
    }

    /**
     * 生产accessToken
     */
    public static String getAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(issuser, subject, claims, accessTokenExpireTime.toMillis(), securityKey);
    }

    /**
     * 生产RefreshToken
     */
    public static String getRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(issuser, subject, claims, accessTokenExpireTime.toMillis(), securityKey);
    }

    /**
     * 生产RefreshAppToken
     */
    public static String getRefreshAppToken(String subject, Map<String, Object> claims) {
        return generateToken(issuser, subject, claims, accessTokenExpireTime.toMillis(), securityKey);
    }

    /**
     * 解析token数据：从令牌中获取数据声明
     */
    public static Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(securityKey)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            if (e instanceof ClaimJwtException) {
                claims = ((ClaimJwtException) e).getClaims();
            }
        }
        return claims;
    }

    /**
     * 通过token获取userId
     */
    public static String getUserId(String token) {
        String userId = null;
        try {
            userId = getClaims(token).getSubject();
        } catch (Exception e) {
            log.error("error={}", e.getMessage());
        }
        return userId;
    }

    /**
     * 通过token获取用户名
     */
    public static String getUSerName(String token) {
        String userName = null;
        try {
            userName = (String) getClaims(token).get(Constant.JWT_USER_NAME);
        } catch (Exception e) {
            log.error("error={}", e.getMessage());
        }
        return userName;
    }

    /**
     * 验证token是否过期
     */
    public static Boolean isExpired(String token) {
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("error={}", e.getMessage());
            return true;
        }
    }

    /**
     * 验证token是否有效
     */
    public static Boolean validateToken(String token) {
        return (getClaims(token) != null && !isExpired(token));
    }

    /**
     * 获取token剩余过期时间
     */
    public static long getRemainingTime(String token) {
        long result = 0;
        try {
            result = getClaims(token).getExpiration().getTime() - System.currentTimeMillis();
        } catch (Exception e) {
            log.error("error={}", e.getMessage());
        }
        return result;
    }
}
