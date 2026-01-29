package com.ajay.springsecurity.service.impl;

import com.ajay.springsecurity.dto.LoginRequestDto;
import com.ajay.springsecurity.dto.LoginResponseDto;
import com.ajay.springsecurity.dto.SignupResponseDto;
import com.ajay.springsecurity.entity.User;
import com.ajay.springsecurity.entity.type.AuthProviderType;
import com.ajay.springsecurity.repository.UserRepo;
import com.ajay.springsecurity.service.AuthService;
import com.ajay.springsecurity.util.AuthUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthImpl implements AuthService {
    private final AuthenticationManager  authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        User user= (User) authentication.getPrincipal();

        if(user.getPassword() == null){
            throw new IllegalArgumentException("Use social login ");
        }

        String token = authUtil.generateToken(user);
        return new LoginResponseDto(token,user.getId());
    }

    @Override
    public LoginResponseDto firebaseLogin(String token) throws FirebaseAuthException {

        FirebaseToken decoded =
                FirebaseAuth.getInstance().verifyIdToken(token);

        String email = decoded.getEmail();
        String uid = decoded.getUid();

        Map<String, Object> firebase =
                (Map<String, Object>) decoded.getClaims().get("firebase");

        if (firebase == null) {
            throw new IllegalStateException("Firebase provider info missing");
        }

        String provider = firebase.get("sign_in_provider").toString();

        AuthProviderType authProviderType = resolveAuthProvider(provider);

        User user = userRepo.findByEmail(email)
                .map(existing -> {

                    // Link provider if not already linked
                    if (existing.getProviderId() == null) {
                        existing.setProviderId(uid);
                    }

                    // DO NOT overwrite LOCAL
                    if (existing.getAuthProviderType() == null) {
                        existing.setAuthProviderType(authProviderType);
                    }

                    return userRepo.save(existing);
                })
                .orElseGet(() -> userRepo.save(
                        User.builder()
                                .email(email)
                                .providerId(uid)
                                .authProviderType(authProviderType)
                                .password(null)
                                .build()
                ));

        String jwt = authUtil.generateToken(user);
        return new LoginResponseDto(jwt, user.getId());
    }



    @Override
    public SignupResponseDto signup(LoginRequestDto signupResponseDto) {
        if(userRepo.existsByEmail(signupResponseDto.getEmail())){
            throw new IllegalArgumentException("User Already Exists try login or social login");
        }

        User user=userRepo.save(User.builder()
                        .email(signupResponseDto.getEmail())
                        .password(passwordEncoder.encode(signupResponseDto.getPassword()))
                        .authProviderType(AuthProviderType.LOCAL)
                        .build());
        return new SignupResponseDto(user.getId(),user.getEmail());
    }


    @Override
    public AuthProviderType resolveAuthProvider(String provider) {
        return switch (provider.toLowerCase()){
            case "google.com","google"-> AuthProviderType.GOOGLE;
            case "github.com","github"-> AuthProviderType.GITHUB;
            case "local","password"-> AuthProviderType.LOCAL;

            default -> throw new IllegalArgumentException("Invalid Auth Provider"+provider);
        };

    }


}
