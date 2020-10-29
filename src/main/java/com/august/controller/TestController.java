package com.august.controller;

import com.august.entity.Result;
import com.august.entity.SysUser;
import com.august.exception.code.RestCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/15
 */
@RestController
@RequestMapping("swagger")
@Api(tags = "测试")
public class TestController {
    @GetMapping("/test")
    @ApiOperation(value = "测试接口")
    public String test2(){
        return "测试成功";
    }
    @GetMapping("/test2")
    @ApiOperation(value = "测试接口")
    public Result test1(){
        SysUser sysUser = new SysUser();
        sysUser.setId("1");
        int i = 1/0;
        return Result.get(RestCode.SUCCESS,sysUser);
    }
}
