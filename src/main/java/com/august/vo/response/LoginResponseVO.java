package com.august.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/21
 */
@Data
public class LoginResponseVO {
    @ApiModelProperty("业务访问token")
    private String accessToken;
    @ApiModelProperty("业务刷新凭证")
    private String refreshToken;
    @ApiModelProperty("用户id）")
    private String userId;
}
