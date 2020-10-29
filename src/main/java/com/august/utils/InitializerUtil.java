package com.august.utils;

import com.august.config.TokenConfig;
import org.springframework.stereotype.Component;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/15
 */
@Component
public class InitializerUtil {
    public InitializerUtil(TokenConfig config){
        JwtTokenUtil.setJwtProperties(config);
    }
}
