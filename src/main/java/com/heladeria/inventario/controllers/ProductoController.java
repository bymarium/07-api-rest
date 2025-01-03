package com.heladeria.inventario.controllers;

import com.heladeria.inventario.dto.ProductoRequestDTO;
import com.heladeria.inventario.dto.ProductoResponseDTO;
import com.heladeria.inventario.models.Producto;
import com.heladeria.inventario.services.ProductoService;
import com.heladeria.inventario.utils.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService servicio;

    @Autowired
    public ProductoController(ProductoService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<String> agregarProducto(@RequestBody ProductoRequestDTO productoRequest){
        Producto producto = new Producto(
                productoRequest.getNombre(),
                productoRequest.getCategoria(),
                productoRequest.getCantidad()
        );
        servicio.agregarProducto(producto);
        return ResponseEntity.ok("Producto agregado exitosamente.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerProducto(@PathVariable Long id){
        return servicio.obtenerProducto(id)
                .map(producto -> ResponseEntity.ok(DtoConverter.convertToResponseDTO(producto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos(){
        List<Producto> productos = servicio.listarProductos();
        List<ProductoResponseDTO> response = productos.stream()
                .map(DtoConverter::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoRequestDTO productoRequest) {
        try {
            Producto actualizado = servicio.actualizarProducto(id, new Producto(
                    productoRequest.getNombre(),
                    productoRequest.getCategoria(),
                    productoRequest.getCantidad()
            ));
            return ResponseEntity.ok(DtoConverter.convertToResponseDTO(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        servicio.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }


}
