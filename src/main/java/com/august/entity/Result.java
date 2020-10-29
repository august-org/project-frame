package com.august.entity;

import com.august.exception.code.RestCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/28 15:30
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("响应状态码")
    private Integer code;// 编号
    @ApiModelProperty("响应提示信息")
    private String msg;// 信息
    @ApiModelProperty("响应数据信息")
    private T data;// 数据

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(RestCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public Result(RestCode code, T data) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }
    public Result() {
        this.code = RestCode.SUCCESS.getCode();
        this.msg = RestCode.SUCCESS.getMsg();
        this.data = null;
    }
    public Result(T data) {
        this.code = RestCode.SUCCESS.getCode();
        this.msg = RestCode.SUCCESS.getMsg();
        this.data = data;
    }

    public static <T> Result get(Integer code, String msg, T data) {
        return new Result(code, msg, data);
    }

    public static <T> Result get(Integer code, T data) {
        return new Result(code, data);
    }

    public static Result get(Integer code, String msg) {
        return new Result(code, msg);
    }

    public static <T> Result get(RestCode code) {
        return new Result(code);
    }

    public static <T> Result get(RestCode code, T data) {
        return new Result(code, data);
    }
    public static Result success() {
        return new Result();
    }
    public static <T> Result success(T data) {
        return new Result(data);
    }
}
