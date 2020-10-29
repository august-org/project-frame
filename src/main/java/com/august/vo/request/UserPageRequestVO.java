package com.august.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/22
 */
@Data
public class UserPageRequestVO {
    @ApiModelProperty("当前第几页")
    private Integer pageNum;
    @ApiModelProperty("当前页的总数")
    private Integer pageSize;
}
