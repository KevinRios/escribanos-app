package com.escribanos.service.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.escribanos.client.EscribanosApiClient;
import com.escribanos.model.ApiResponse;
import com.escribanos.model.EscribanoResponse;
import com.escribanos.mapper.EscribanoMapper;
import com.escribanos.service.IEscribanosService;

@Service
public class EscribanosServiceImpl implements IEscribanosService {
	
  private final EscribanosApiClient escribanosApiClient;
  private final EscribanoMapper escribanoMapper;

  public EscribanosServiceImpl(EscribanosApiClient escribanosApiClient, EscribanoMapper escribanoMapper) {
    this.escribanosApiClient = escribanosApiClient;
    this.escribanoMapper = escribanoMapper;
  }
  
  @Override
  public ApiResponse<EscribanoResponse> searchByCuit(String cuit) {
    return Optional.ofNullable(escribanoMapper.toResponse(escribanosApiClient.searchByCuit(cuit)))
	        	   		.map(escribano -> ApiResponse.success(escribano, "Escribano encontrado exitosamente"))
	        	   .orElseGet(() -> ApiResponse.success(null));
  }
  
}
