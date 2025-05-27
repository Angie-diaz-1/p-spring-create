package com.eam.p_spring_create.service;

import com.eam.p_spring_create.entity.Producto;
import com.eam.p_spring_create.repository.ProductoRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductoServiceIT {

    @Container
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:10.6")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariadb::getJdbcUrl);
        registry.add("spring.datasource.username", mariadb::getUsername);
        registry.add("spring.datasource.password", mariadb::getPassword);
    }

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    @BeforeEach
    void cleanDB() {
        productoRepository.deleteAll();
    }

    // 1. Crear producto
    @Test
    @Order(1)
    void crearProducto_DeberiaGuardarCorrectamente() {
        Producto producto = new Producto(null, "C001", "Camisa", 50000.0, 10);
        Producto guardado = productoService.crearProducto(producto);

        assertNotNull(guardado.getId());
        assertEquals("C001", guardado.getCodigo());
    }

    @Test
    @Order(2)
    void crearProducto_DeberiaFallarSiCodigoExiste() {
        productoService.crearProducto(new Producto(null, "C002", "Pantalón", 80000.0, 5));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            productoService.crearProducto(new Producto(null, "C002", "Pantalón copia", 70000.0, 4));
        });

        assertEquals("Ya existe un producto con el mismo código", ex.getMessage());
    }


}
