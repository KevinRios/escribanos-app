package com.escribanos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class EscribanoResponse {
    private String nombre;
    private String apellido;
    private Integer matricula;
    private String telefono;
    private String email;
    private String estado;

    private String barrio;
    private String cargo;
    private String celular;
    private String dias;
    private String domicilio;
    private String fechaDestitucion;
    private String horario;
    private String imagen;
    private String localidad;
    private Boolean mostrarImagen;
    private Integer registro;
    private String sexo;
}
