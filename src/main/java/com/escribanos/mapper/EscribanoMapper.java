package com.escribanos.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.escribanos.model.EscribanoResponse;

@Component
public class EscribanoMapper {

    private String trimIfPresent(String value) {
        return Optional.ofNullable(value)
			           		.map(String::trim)
			           		.filter(str -> !str.isBlank())
			           .orElse(null);
    }

    public EscribanoResponse toResponse(EscribanoResponse escribanoResponse) {
        return Optional.ofNullable(escribanoResponse)
			           		.map(s -> {
			           			EscribanoResponse response = new EscribanoResponse();
				                response.setNombre(trimIfPresent(s.getNombre()));
				                response.setApellido(trimIfPresent(s.getApellido()));
				                response.setMatricula(s.getMatricula());
				                response.setTelefono(trimIfPresent(s.getTelefono()));
				                response.setEmail(trimIfPresent(s.getEmail()));
				                response.setEstado(trimIfPresent(s.getEstado()));
				                response.setBarrio(trimIfPresent(s.getBarrio()));
				                response.setCargo(trimIfPresent(s.getCargo()));
				                response.setCelular(trimIfPresent(s.getCelular()));
				                response.setDias(trimIfPresent(s.getDias()));
				                response.setDomicilio(trimIfPresent(s.getDomicilio()));
				                response.setFechaDestitucion(s.getFechaDestitucion());
				                response.setHorario(trimIfPresent(s.getHorario()));
				                response.setImagen(trimIfPresent(s.getImagen()));
				                response.setLocalidad(trimIfPresent(s.getLocalidad()));
				                response.setMostrarImagen(s.getMostrarImagen());
				                response.setRegistro(s.getRegistro());
				                response.setSexo(trimIfPresent(s.getSexo()));
				                return response;
			           		})
			            .orElse(null);
    }
}
