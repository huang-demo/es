package com.dem.es.exception;

public class ValidaParamExceltion extends RuntimeException{
    private String msg;
    private int code = 500;

    public ValidaParamExceltion(String msg){
        super(msg);
        this.msg = msg;
    }
}
