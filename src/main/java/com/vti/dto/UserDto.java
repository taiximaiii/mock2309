package com.vti.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String phoneNumber;
    private String image;
}
