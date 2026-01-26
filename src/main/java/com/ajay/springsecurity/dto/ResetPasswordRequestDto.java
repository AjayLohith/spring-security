package com.ajay.springsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordRequestDto {
    @NotBlank
    private String resetToken;

    @NotBlank
    @Size(min = 8)
    private String newPassword;
}
