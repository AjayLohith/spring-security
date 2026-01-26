package com.ajay.springsecurity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequestDto {

    @Email
    private String email;

    @NotBlank
    @Size(min=8,max = 30, message = "Password required")
    private String password;
}
