package com.august.exception;

import com.august.exception.code.RestCode;
import lombok.Data;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/15
 */
@Data
public class CustomException extends RuntimeException {
    private int code;
    private String msg;

    public CustomException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CustomException(RestCode error) {
        this.code = error.getCode();
        this.msg = error.getMsg();
    }
}
