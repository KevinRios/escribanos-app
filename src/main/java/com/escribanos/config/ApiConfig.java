package com.escribanos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@Getter
public class ApiConfig {
    
    @Value("${api.base-url}")
    private String baseUrl;
    
    @Value("${api.escribano.estado.endpoint}")
    private String escribanoEndpoint;
}
