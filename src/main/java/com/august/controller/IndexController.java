package com.august.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/29 10:16
 */
@Controller
@RequestMapping("index")
@Api("视图")
public class IndexController {
    @GetMapping("403")
    @ApiOperation("403错误页面")
    public String error403() {
        return "error/403";
    }

    @GetMapping("404")
    @ApiOperation("404错误页面")
    public String error404() {
        return "error/404";
    }

    @GetMapping("500")
    @ApiOperation("500错误页面")
    public String error500() {
        return "error/500";
    }

}
