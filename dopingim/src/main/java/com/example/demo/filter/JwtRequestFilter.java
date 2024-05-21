package com.example.demo.filter;


import com.example.demo.service.StudentService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil, StudentService studentService) {
        this.jwtUtil = jwtUtil;
        this.studentService = studentService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
   

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        System.out.println("JWT Token Doğrulama Başlıyor --------------------------------");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            System.out.println("Kullanıcı Adı: " + username + " --------------------------------");
        }
        if (username != null) {
            System.out.println("Username is not null");
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("SecurityContext is null, proceeding with authentication");
            } else {
                System.out.println("SecurityContext is not null, user might be already authenticated");
            }
        } else {
            System.out.println("Username is null, cannot proceed with authentication");
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.studentService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println("Kullanıcı Doğrulandı --------------------------------");
            } else {
                System.out.println("Token Geçerli Değil --------------------------------");
            }
        } else {
            System.out.println("Kullanıcı Adı Bulunamadı veya Zaten Doğrulanmış --------------------------------");
        }
        chain.doFilter(request, response);
    }

}
