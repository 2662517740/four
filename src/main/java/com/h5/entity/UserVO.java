package com.h5.entity;

import lombok.Data;

@Data
public class UserVO extends User {

    private String token;

    private String UserNewPassword;
}
