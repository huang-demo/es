package com.dem.es.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {
    private Long userId;
    private String userName;
}
