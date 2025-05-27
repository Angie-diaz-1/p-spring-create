package com.eam.p_spring_create.controller;

import com.eam.p_spring_create.entity.Producto;
import com.eam.p_spring_create.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void crearProducto_DeberiaRetornarProductoCreado() throws Exception {
        Producto producto = new Producto(1L, "P001", "Camisa", 50000.0, 10);

        when(productoService.crearProducto(any())).thenReturn(producto);

        mockMvc.perform(post("/api/productos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(producto)))
                .andExpect(status().isOk());
    }

    @Test
    void crearProducto_DeberiaRetornarBadRequestSiFalla() throws Exception {
        Producto producto = new Producto(null, "P001", "Camisa", 50000.0, 10);

        when(productoService.crearProducto(any())).thenThrow(new IllegalArgumentException("Código duplicado"));

        mockMvc.perform(post("/api/productos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(producto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Código duplicado"));
    }


}
