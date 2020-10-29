package com.august.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/22
 */
@Data
public class PageVO <T> {
    @ApiModelProperty("总记录数")
    private Long totalRows;

    @ApiModelProperty("总页数")
    private Integer totalPages;

    @ApiModelProperty("当前第几页")
    private Integer pageNum;

    @ApiModelProperty("每页记录数")
    private Integer pageSize;

    @ApiModelProperty("当前页记录数")
    private Integer curPageSize;

    @ApiModelProperty("数据列表")
    private List<T> list;
}
