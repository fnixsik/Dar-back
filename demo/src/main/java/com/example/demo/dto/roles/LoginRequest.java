package com.example.demo.dto.roles;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
