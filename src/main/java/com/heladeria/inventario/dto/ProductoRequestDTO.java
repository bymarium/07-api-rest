package com.heladeria.inventario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoRequestDTO {

    private String nombre;
    private String categoria;
    private Integer cantidad;
}
