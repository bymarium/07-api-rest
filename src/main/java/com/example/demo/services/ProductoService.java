package com.example.demo.services;

import com.example.demo.models.Producto;
import com.example.demo.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository repositorio;

    @Autowired
    public ProductoService(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void agregarProducto(Producto producto) {
        repositorio.save(producto);
    }

    public Optional<Producto> obtenerProducto(Long id) {
        return repositorio.findById(id);
    }

    public List<Producto> listarProductos() {
        return repositorio.findAll();
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Producto producto1 = repositorio.findById(id).map(producto -> {
            producto.setNombre(productoActualizado.getNombre());
            producto.setCategoria(productoActualizado.getCategoria());
            producto.setCantidad(productoActualizado.getCantidad());
            return repositorio.save(producto);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id));

        return producto1;

    }

    public void eliminarProducto(Long id) {
        repositorio.deleteById(id);
    }
}
