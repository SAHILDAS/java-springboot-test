package com.example.blogplatform.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class RegisterUserResponse {

    private UUID id;
    private String name;
    private String email;
    private Boolean isActive;
}
