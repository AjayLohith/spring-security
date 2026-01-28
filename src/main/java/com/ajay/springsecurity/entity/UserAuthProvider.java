package com.ajay.springsecurity.entity;

import com.ajay.springsecurity.entity.type.AuthProviderType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider","providerId"})
        }
)
public class UserAuthProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;

    private String providerId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
