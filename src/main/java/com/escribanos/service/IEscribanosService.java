package com.escribanos.service;

import com.escribanos.model.ApiResponse;
import com.escribanos.model.EscribanoResponse;

public interface IEscribanosService {
	
  ApiResponse<EscribanoResponse> searchByCuit(String cuit);
  
}
