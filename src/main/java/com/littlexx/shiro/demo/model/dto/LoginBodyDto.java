package com.littlexx.shiro.demo.model.dto;

import lombok.Data;

@Data
public class LoginBodyDto {

    private String username;

    private String password;

    private String code;
}
