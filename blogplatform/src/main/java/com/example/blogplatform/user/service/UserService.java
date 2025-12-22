package com.example.blogplatform.user.service;

import com.example.blogplatform.user.dto.RegisterUserRequest;
import com.example.blogplatform.user.dto.RegisterUserResponse;

public interface UserService {

    RegisterUserResponse register(RegisterUserRequest request);
}
