package com.dem.es.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserDTO implements Serializable {
    private Long userid;
    private String username;
}
