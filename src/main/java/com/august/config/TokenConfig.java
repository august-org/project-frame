package com.august.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/15
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")//扫描配置文件中的token配置
@Data
public class TokenConfig {
    private String securityKey;
    private Duration accessTokenExpireTime;
    private Duration refreshTokenExpireTime;
    private Duration refreshTokenExpireAppTime;
    private String issuser;
}
