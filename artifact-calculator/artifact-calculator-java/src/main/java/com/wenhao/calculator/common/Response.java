package com.wenhao.calculator.common;

import lombok.Data;

import java.util.Date;

/**
 * Response
 *
 * @author: Wenhao Tong
 * @date: 2023/6/16
 */
@Data
public class Response<T> {
    private Integer code;

    private String desc;

    private final Date timestamp = new Date();

    private T data;

    public Response(T data) {
        this.code = 200;
        this.data = data;
    }

    public Response(String desc) {
        this.code = 500;
        this.desc = desc;
    }
}
