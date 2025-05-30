package com.eam.p_spring_create.controller;

import com.eam.p_spring_create.entity.Producto;
import com.eam.p_spring_create.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearProducto(@Valid @RequestBody Producto producto) {
        try {
            Producto productoCreado = productoService.crearProducto(producto);

            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Producto creado exitosamente.");
            response.put("producto", productoCreado);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
