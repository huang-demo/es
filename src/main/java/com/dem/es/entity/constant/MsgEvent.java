package com.dem.es.entity.constant;

public enum  MsgEvent {
     ADD("add"),
     UPDATE("update"),
     DEL("del"),
     ;
     private String code;

     MsgEvent(String code) {
         this.code = code;
     }
 }
