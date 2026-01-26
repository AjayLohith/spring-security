package com.ajay.springsecurity.controller;

import com.ajay.springsecurity.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {

    private final

    @PostMapping("/login")
    public ResponseEntity<LoginRequestDto> loginUser(@RequestBody LoginRequestDto loginRequestDto){


    }
}
