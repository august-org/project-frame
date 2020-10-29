package com.august.shiro;

import com.august.constant.Constant;
import com.august.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/23 20:57
 */
public class CustomRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String accessToken = (String) principals.getPrimaryPrincipal();
        Claims claims = JwtTokenUtil.getClaims(accessToken);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (claims.get(Constant.PERMISSIONS_INFOS_KEY) != null) {
            info.addStringPermissions((Collection<String>) claims.get(Constant.PERMISSIONS_INFOS_KEY));
        }
        if (claims.get(Constant.ROLES_INFOS_KEY) != null) {
            info.addRoles((Collection<String>) claims.get(Constant.ROLES_INFOS_KEY));
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CustomToken customToken = (CustomToken) token;
        return new SimpleAuthenticationInfo(customToken.getPrincipal(), customToken.getCredentials(), this.getName());
    }
}
