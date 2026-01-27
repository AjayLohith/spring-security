package com.ajay.springsecurity.security;

import com.ajay.springsecurity.entity.User;
import com.ajay.springsecurity.repository.UserRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class FirebaseAuthFilter extends OncePerRequestFilter {
    private final UserRepo userRepo;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String header=request.getHeader("Authorization");
            return header==null || !header.startsWith("Firebase");
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header=request.getHeader("Authorization");
        if(header == null || header.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        String token=header.substring(7);
        try{
            FirebaseToken decodedToken= FirebaseAuth.getInstance().verifyIdToken(token);
            String email=decodedToken.getEmail();

            request.setAttribute("uid",decodedToken.getUid());
            request.setAttribute("email",decodedToken.getEmail());

            User user =userRepo.findByEmail(email).orElseThrow();

            UsernamePasswordAuthenticationToken authenticationToken=
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (
                FirebaseAuthException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or Expired Firebase Token");
            return;
        }
        filterChain.doFilter(request,response);
    }

}
