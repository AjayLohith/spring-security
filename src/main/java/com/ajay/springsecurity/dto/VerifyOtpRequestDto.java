package com.ajay.springsecurity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VerifyOtpRequestDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6,max = 6)
    private String otp;
}
