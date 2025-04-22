package com.escribanos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@Getter
public class JwtConfig {
    
    @Value("${jwt.issuer}")
    private String issuer;
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.subject}")
    private String subject;
    
    @Value("${jwt.audience}")
    private String audience;
    
    @Value("${jwt.role}")
    private String role;
    
    @Value("${jwt.expiration}")
    private long expirationTime;
}
