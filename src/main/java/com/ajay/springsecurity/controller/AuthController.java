package com.ajay.springsecurity.controller;

import com.ajay.springsecurity.dto.LoginRequestDto;
import com.ajay.springsecurity.dto.LoginResponseDto;
import com.ajay.springsecurity.dto.SignupResponseDto;
import com.ajay.springsecurity.service.impl.AuthImpl;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.signup(loginRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/firebase")
    public ResponseEntity<LoginResponseDto> firebaseLogin(
            @RequestHeader("Authorization")String token
    ) throws FirebaseAuthException {
        return ResponseEntity.ok(authService.firebaseLogin(token.replace("Bearer ","")));
    }



}
