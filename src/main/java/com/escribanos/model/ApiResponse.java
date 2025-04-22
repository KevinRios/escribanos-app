package com.escribanos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
  private String mensaje;
  private T data;
  
  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder()
        .data(data)
        .build();
  }

  public static <T> ApiResponse<T> success(T data, String mensaje) {
    return ApiResponse.<T>builder()
        .mensaje(mensaje)
        .data(data)
        .build();
  }
  
  public static <T> ApiResponse<T> error(String mensaje) {
    return ApiResponse.<T>builder()
        .mensaje(mensaje)
        .build();
  }
}
