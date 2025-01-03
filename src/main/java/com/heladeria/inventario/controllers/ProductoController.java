package com.heladeria.inventario.controllers;

import com.heladeria.inventario.models.Producto;
import com.heladeria.inventario.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService servicio;

    @Autowired
    public ProductoController(ProductoService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<String> agregarProducto(@RequestBody Producto producto){
        servicio.agregarProducto(producto);
        return ResponseEntity.ok("Producto agregado exitosamente.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id){
    return servicio.obtenerProducto(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){
        return ResponseEntity.ok(servicio.listarProductos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto){
        try{
            Producto productoActualizado = servicio.actualizarProducto(id, producto);
            return ResponseEntity.ok("Se ha actualizado exitosamente el producto");
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        servicio.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

}
