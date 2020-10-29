package com.august.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/21
 */
public class CustomToken extends UsernamePasswordToken {
    private String jwtToken;

    public CustomToken(String jwtToken){
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}
