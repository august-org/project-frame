package com.august.service.impl;

import com.august.constant.Constant;
import com.august.entity.SysUser;
import com.august.exception.CustomException;
import com.august.exception.code.RestCode;
import com.august.mapper.SysUserMapper;
import com.august.service.UserService;
import com.august.utils.JwtTokenUtil;
import com.august.utils.PageUtil;
import com.august.vo.request.LoginRequestVO;
import com.august.vo.request.UserPageRequestVO;
import com.august.vo.response.LoginResponseVO;
import com.august.vo.response.PageVO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/21
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public LoginResponseVO login(LoginRequestVO vo) {
        SysUser sysUser = sysUserMapper.selectByUsername(vo.getUsername());
        if (sysUser == null) {
            throw new CustomException(RestCode.ACCOUNT_ERROR);
        }
        if (sysUser.getStatus() == 2) {
            throw new CustomException(RestCode.ACCOUNT_LOCK);
        }
        if (!vo.getPassword().equals("324ce32d86224b00a02b")) {
            throw new CustomException(RestCode.ACCOUNT_PASSWORD_ERROR);
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.JWT_USER_NAME, sysUser.getUsername());
        claims.put(Constant.ROLES_INFOS_KEY, getRoleByUserId(sysUser.getId()));
        claims.put(Constant.PERMISSIONS_INFOS_KEY, getPermissionByUserId(sysUser.getId()));
        String accessToken = JwtTokenUtil.getAccessToken(sysUser.getId(), claims);
        log.info("accessToken={}", accessToken);
        Map<String, Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put(Constant.JWT_USER_NAME, sysUser.getUsername());
        String refreshToken = null;
        if (vo.getType().equals("1")) {
            refreshToken = JwtTokenUtil.getRefreshToken(sysUser.getId(), claims);
        } else {
            refreshToken = JwtTokenUtil.getRefreshAppToken(sysUser.getId(), refreshTokenClaims);
        }
        log.info("refreshToken={}", refreshToken);
        LoginResponseVO responseVO = new LoginResponseVO();
        responseVO.setUserId(sysUser.getId());
        responseVO.setAccessToken(accessToken);
        responseVO.setRefreshToken(refreshToken);
        return responseVO;
    }

    private List<String> getRoleByUserId(String userId) {
        List<String> list = new ArrayList<>();
        if (userId.equals("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8")) {
            list.add("admin");
        } else {
            list.add("dev");
        }
        return list;
    }

    private List<String> getPermissionByUserId(String userId) {
        List<String> list = new ArrayList<>();
        if (userId.equals("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8")) {
            list.add("sys:user:add");
            list.add("sys:user:update");
            list.add("sys:user:delete");
            list.add("sys:user:list");
        } else {
            //list.add("sys:user:list");
            list.add("sys:user:add");
        }
        return list;
    }

    @Override
    //public PageInfo<SysUser> pageInfo(UserPageRequestVO vo) {
    public PageVO<SysUser> pageInfo(UserPageRequestVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysUser> list = sysUserMapper.listAll();
        //PageInfo<SysUser> info = new PageInfo<>(list);
        return PageUtil.getPageVO(list);
    }

}
