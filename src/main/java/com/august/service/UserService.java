package com.august.service;

import com.august.entity.SysUser;
import com.august.vo.request.LoginRequestVO;
import com.august.vo.request.UserPageRequestVO;
import com.august.vo.response.LoginResponseVO;
import com.august.vo.response.PageVO;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/21
 */
public interface UserService {
    LoginResponseVO login(LoginRequestVO vo);
    //PageInfo<SysUser> pageInfo(UserPageRequestVO vo);
    PageVO<SysUser> pageInfo(UserPageRequestVO vo);
}
