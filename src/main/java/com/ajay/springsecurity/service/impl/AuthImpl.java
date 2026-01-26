package com.ajay.springsecurity.service.impl;

import com.ajay.springsecurity.dto.LoginRequestDto;
import com.ajay.springsecurity.dto.LoginResponseDto;
import com.ajay.springsecurity.service.AuthService;
import com.ajay.springsecurity.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthImpl implements AuthService {
    private final AuthenticationManager  authenticationManager;
    private final AuthUtil authUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        return null;
    }
}
