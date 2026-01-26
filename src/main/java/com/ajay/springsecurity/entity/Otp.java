package com.ajay.springsecurity.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Otp {
    @Id
    private Long id;

    private String email;
    private Long otp;
    private String purpose;
    private LocalDateTime createdAt;

    private LocalDateTime expiryAt;
    private Boolean used;
}
