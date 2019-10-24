package com.h5.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserVO extends User {

    private String token;

    private User user;

    private String UserNewPassword;
}
