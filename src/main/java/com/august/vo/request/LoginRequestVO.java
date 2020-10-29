package com.august.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/21
 */
@Data
public class LoginRequestVO {
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
    @ApiModelProperty("登录类型（1 web 2 app）")
    @NotBlank(message = "登录类型不能为空")
    private String type;
}
