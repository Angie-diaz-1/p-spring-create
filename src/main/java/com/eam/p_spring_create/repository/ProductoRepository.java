package com.eam.p_spring_create.repository;

import com.eam.p_spring_create.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Verifica si ya existe un producto con un código específico
    boolean existsByCodigo(String codigo);

}
