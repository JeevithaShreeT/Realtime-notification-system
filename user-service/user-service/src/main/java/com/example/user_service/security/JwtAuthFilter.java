package com.example.user_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final Jwtutil jwtutil;

    public JwtAuthFilter(Jwtutil jwtutil){
        this.jwtutil=jwtutil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(header==null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        if(jwtutil.validateToken(token)){

            String email = jwtutil.extractEmail(token);

            //to create authentication object
            UsernamePasswordAuthenticationToken authenticate =
                    new UsernamePasswordAuthenticationToken(email, null, List.of());

            //set authentication in security context
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            filterChain.doFilter(request, response);
        }
    }
}
