package com.august.controller;

import com.august.entity.Result;
import com.august.entity.SysUser;
import com.august.service.UserService;
import com.august.vo.request.LoginRequestVO;
import com.august.vo.request.UserPageRequestVO;
import com.august.vo.response.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/21
 */
@RestController
@RequestMapping("api")
@Api(tags = "用户")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("user/login")
    @ApiOperation("用户登录接口")
    public Result<LoginRequestVO> login(@RequestBody @Valid LoginRequestVO vo){
        return Result.success(userService.login(vo));
    }

    @PostMapping("users")
    @ApiOperation("分页查询用户接口")
    @RequiresPermissions("sys:user:list")
    public Result<PageVO<SysUser>> list(@RequestBody @Valid UserPageRequestVO vo){
        return Result.success(userService.pageInfo(vo));
    }
}
