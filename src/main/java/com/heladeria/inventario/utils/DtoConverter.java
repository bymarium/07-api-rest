package com.heladeria.inventario.utils;

import com.heladeria.inventario.dto.ProductoResponseDTO;
import com.heladeria.inventario.models.Producto;

public class DtoConverter {

    public static ProductoResponseDTO convertToResponseDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setCategoria(producto.getCategoria());
        dto.setCantidad(producto.getCantidad());
        return dto;
    }

}
