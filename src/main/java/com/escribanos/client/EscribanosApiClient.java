package com.escribanos.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.escribanos.config.ApiConfig;
import com.escribanos.model.EscribanoResponse;
import com.escribanos.util.JwtGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Cliente para realizar consultas a la API de Escribanos.
 * Maneja las llamadas HTTP.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EscribanosApiClient {
  private final RestTemplate restTemplate;
  private final JwtGenerator jwtGenerator;
  private final ApiConfig apiConfig;

  public EscribanoResponse searchByCuit(String cuit) {
    return restTemplate.exchange(buildUrl(cuit),
					             HttpMethod.GET,
					   	         createHttpEntity(),
					   	         EscribanoResponse.class)
    				   .getBody();
  }

  private HttpEntity<?> createHttpEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(jwtGenerator.createJWT());
    return new HttpEntity<>(headers);
  }

  private String buildUrl(String cuit) {
    return apiConfig.getBaseUrl() + apiConfig.getEscribanoEndpoint() + cuit;
  }
}
