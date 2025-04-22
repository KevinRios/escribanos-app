package com.escribanos.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.escribanos.model.ApiResponse;
import com.escribanos.model.EscribanoResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	
	/* desde el server API, varios msj de respuesta no eran correctos, por eso desde aca se modifica para devolver el correcto
	ej: al no encontrar un escribano -> 404 cuanod deberia ser 200 y un msj acorde
		token vencido 500 en lugar de 401 UNAUTHORIZED
	*/
	
	
	
    private static final String MSG_UNAUTHORIZED = "Error de autorización: Token inválido o mal formado";
    private static final String MSG_FORBIDDEN = "No tiene permisos para realizar esta operación";
    private static final String MSG_INTERNAL_ERROR = "Error interno del servidor";
    private static final String MSG_ESCRIBANO_NOT_FOUND = "No se encontro un escribano para la CUIT informada";
    private static final String ESCRIBANO_NOT_FOUND_STATE = "{\"estado\":\"No se encontro un escribano para la CUIT informada\"}";
    private static final String RECURSO_NOT_FOUND = "El recurso solicitado no fue encontrado";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<EscribanoResponse>> handleValidationError(ConstraintViolationException e) {
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return respond(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponse<EscribanoResponse>> handleHttpClientError(HttpClientErrorException e) {
        String message = e.getResponseBodyAsString();
        HttpStatus status = e.getStatusCode();

        ResponseEntity<ApiResponse<EscribanoResponse>> response;
        switch (status) {
            //404
            case NOT_FOUND:
                response = handleNotFoundError(message);
                break;
            //401
            case UNAUTHORIZED:
                response = respond(MSG_UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
                break;
            //403
            case FORBIDDEN:
                response = respond(MSG_FORBIDDEN, HttpStatus.FORBIDDEN);
                break;
            //400
            case BAD_REQUEST:
                response = respond(extractErrorMessage(message), HttpStatus.BAD_REQUEST);
                break;
            default:
                response = respond(message, status);
        }
        return response;
    }

    

    
    
    
    
    // server errors
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ApiResponse<EscribanoResponse>> handleServerError(HttpServerErrorException e) {
        String message = e.getResponseBodyAsString();
        if (message.contains("Invalid token") || message.contains("Error while parsing")) {
            return respond(MSG_UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
        return respond(message, e.getStatusCode());
    }

    
    
    // exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<EscribanoResponse>> handleGeneral(Exception e) {
        return respond(MSG_INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private static ResponseEntity<ApiResponse<EscribanoResponse>> respond(String message, HttpStatus status) {
        log.error(message);
        return ResponseEntity.status(status).body(ApiResponse.error(message));
    }

    private static ResponseEntity<ApiResponse<EscribanoResponse>> handleNotFoundError(String message) {
        if (message.contains(ESCRIBANO_NOT_FOUND_STATE)) {
            log.info(MSG_ESCRIBANO_NOT_FOUND);
            return ResponseEntity.ok().body(ApiResponse.error(MSG_ESCRIBANO_NOT_FOUND));
        }
        log.error(RECURSO_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(RECURSO_NOT_FOUND));
    }

    private static String extractErrorMessage(String message) {
        return message.substring(message.indexOf(":")+2, message.lastIndexOf("\""));
    }
}
