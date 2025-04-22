package com.escribanos.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.escribanos.model.ApiResponse;
import com.escribanos.model.EscribanoResponse;
import com.escribanos.service.impl.EscribanosServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EscribanosApiClientTest {

    @Autowired
    private EscribanosServiceImpl escribanoService;

    @Test
    void testValidCuit() throws Exception {
        ApiResponse<EscribanoResponse> response = escribanoService.searchByCuit("20261573009");
        assertEquals("Escribano encontrado exitosamente", response.getMensaje());
        assertNotNull(response.getData());
        
        EscribanoResponse data = response.getData();
        assertEquals("Santiago Joaquin Enrique", data.getNombre());
        assertEquals("PANO", data.getApellido());
        assertEquals(4818, data.getMatricula());
        assertEquals("4322-4322/4201", data.getTelefono());
        assertEquals("desarrollo@colegio-escribanos.org.ar", data.getEmail());
        assertEquals("ACTIVO", data.getEstado());
    }

    @Test
    void testInvalidCuit() throws Exception {
        ApiResponse<EscribanoResponse> response = escribanoService.searchByCuit("24365225407");
        assertNull(response.getData());
        assertEquals("No se encontró un escribano para la CUIT informada", response.getMensaje());
    }

   
}
