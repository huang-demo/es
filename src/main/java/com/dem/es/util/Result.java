package com.dem.es.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Result<T> implements Serializable {
    public static Integer SUCCESS = 200;
    public static Integer ERROR = 500;
    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(SUCCESS, "", data);
    }

    public static Result error(String msg) {
        return new Result(ERROR, msg, null);
    }
}
