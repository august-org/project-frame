package com.august.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/15
 */
@Data
public class TestRequestVO {
    @NotEmpty(message = "list不能为空")
    private List<String> list;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotNull(message = "年龄不能为空")
    private Integer age;
}
