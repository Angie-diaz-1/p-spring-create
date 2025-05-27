package com.eam.p_spring_create.service;

import com.eam.p_spring_create.entity.Producto;
import com.eam.p_spring_create.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Crear producto (solo si el código no existe)
    public Producto crearProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        if (productoRepository.existsByCodigo(producto.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un producto con el mismo código");
        }
        return productoRepository.save(producto);
    }


}
