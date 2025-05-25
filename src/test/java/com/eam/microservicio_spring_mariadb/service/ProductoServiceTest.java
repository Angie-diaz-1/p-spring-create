import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.NoSuchElementException;

import com.eam.microservicio_spring_mariadb.repository.ProductoRepository;
import com.eam.microservicio_spring_mariadb.service.ProductoService;
import com.eam.microservicio_spring_mariadb.entity.Producto;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void crearProducto_DeberiaGuardarProductoCorrectamente() {
        Producto producto = new Producto(null, "P001", "Camisa", 50000.0, 10);
        when(productoRepository.existsByCodigo("P001")).thenReturn(false);
        when(productoRepository.save(any())).thenReturn(producto);

        Producto resultado = productoService.crearProducto(producto);

        assertNotNull(resultado);
        assertEquals("P001", resultado.getCodigo());
        verify(productoRepository).save(producto);
    }

    @Test
    void crearProducto_DeberiaLanzarExcepcionSiCodigoYaExiste() {
        Producto producto = new Producto(null, "P001", "Camisa", 50000.0, 10);
        when(productoRepository.existsByCodigo("P001")).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.crearProducto(producto);
        });

        assertEquals("Ya existe un producto con el mismo código", exception.getMessage());
        verify(productoRepository, never()).save(any());
    }

    @Test
    void crearProducto_DeberiaLanzarExcepcionSiProductoEsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.crearProducto(null);
        });

        assertEquals("El producto no puede ser nulo", exception.getMessage());
    }


}
