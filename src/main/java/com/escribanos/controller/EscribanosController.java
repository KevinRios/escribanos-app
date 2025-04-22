package com.escribanos.controller;

import javax.validation.constraints.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.escribanos.model.ApiResponse;
import com.escribanos.model.EscribanoResponse;
import com.escribanos.service.IEscribanosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/nomina")
@RequiredArgsConstructor
@Validated
public class EscribanosController {
  private final IEscribanosService escribanosService;

  /**
   * Busca un escribano por su CUIT.
   * 
   * @param cuit CUIT del escribano a buscar
   * @return Datos del escribano
   */
  @GetMapping("/search")
  public ResponseEntity<ApiResponse<EscribanoResponse>> searchEscribano(
      @Pattern(regexp = "\\d{11}", message = "El CUIT debe ser numérico, de longitud 11")
      @RequestParam String cuit) {
    return ResponseEntity.ok(escribanosService.searchByCuit(cuit));
  }
}
