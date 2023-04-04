package com.book.bus.utils;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor

public class Result {
    // 响应业务状态
    private Integer status;
    // 响应中的数据
    private Object data;

    // 响应消息
    private String msg;

    public Result() {
    }

    public Result(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
