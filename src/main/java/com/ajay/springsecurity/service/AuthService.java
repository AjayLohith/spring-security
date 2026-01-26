package com.ajay.springsecurity.service;

import com.ajay.springsecurity.dto.LoginRequestDto;
import com.ajay.springsecurity.dto.LoginResponseDto;
import org.springframework.stereotype.Service;


public interface AuthService {
    public LoginResponseDto login(LoginRequestDto loginRequestDto);
}
