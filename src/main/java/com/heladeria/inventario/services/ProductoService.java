package com.heladeria.inventario.services;

import com.heladeria.inventario.models.Producto;
import com.heladeria.inventario.repositories.ProductoRepository;
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

    //En esta clase se deberia de hacer la logica adicional al manejo del repo
    public void agregarProducto(Producto producto){
        repositorio.save(producto);
    }

    public Optional<Producto> obtenerProducto(Long id){
       return repositorio.findById(id);
    }

    public List<Producto> listarProductos(){
        return repositorio.findAll();
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado){
        return repositorio.findById(id).map(x -> {
            x.setNombre(productoActualizado.getNombre());
            x.setCategoria(productoActualizado.getCategoria());
            x.setCantidad(productoActualizado.getCantidad());
            return repositorio.save(x);
        }).orElseThrow(()-> new RuntimeException("Producto con el id "+id+" no pudo ser actualizado"));
    }

    public void eliminarProducto(Long id){
        repositorio.deleteById(id);
    }
}
