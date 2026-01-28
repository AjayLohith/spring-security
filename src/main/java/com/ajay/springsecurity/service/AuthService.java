package com.ajay.springsecurity.service;

import com.ajay.springsecurity.dto.LoginRequestDto;
import com.ajay.springsecurity.dto.LoginResponseDto;
import com.ajay.springsecurity.dto.SignupResponseDto;
import com.ajay.springsecurity.entity.type.AuthProviderType;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public LoginResponseDto login(LoginRequestDto loginRequestDto);
    public LoginResponseDto firebaseLogin(String token) throws FirebaseAuthException;
    public AuthProviderType resolveAuthProvider(String provider);
    public SignupResponseDto signup(LoginRequestDto signupResponseDto);



}
