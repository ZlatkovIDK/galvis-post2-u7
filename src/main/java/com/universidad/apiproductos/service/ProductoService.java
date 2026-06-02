package com.universidad.apiproductos.service;

import com.universidad.apiproductos.model.Producto;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Capa de servicio que simula un repositorio usando un LinkedHashMap en memoria.
 * Al estar anotada con @Service, Spring la gestiona como singleton:
 * los datos persisten durante toda la vida de la aplicación.
 */
@Service
public class ProductoService {

    // LinkedHashMap mantiene el orden de inserción
    private final Map<Long, Producto> productos = new LinkedHashMap<>();
    private Long contadorId = 1L;

    // Datos de ejemplo cargados al iniciar la aplicación
    public ProductoService() {
        guardar(new Producto(null, "Laptop",  "Laptop 15 pulgadas 16 GB RAM", 1299.99));
        guardar(new Producto(null, "Mouse",   "Mouse inalámbrico ergonómico",    29.99));
        guardar(new Producto(null, "Teclado", "Teclado mecánico TKL",            89.99));
    }

    /** Retorna todos los productos como lista. */
    public List<Producto> obtenerTodos() {
        return new ArrayList<>(productos.values());
    }

    /**
     * Busca un producto por ID.
     * Retorna Optional vacío si no existe (el controlador lo convierte en 404).
     */
    public Optional<Producto> buscarPorId(Long id) {
        return Optional.ofNullable(productos.get(id));
    }

    /**
     * Guarda o actualiza un producto.
     * Si el ID es null, asigna uno nuevo (creación).
     * Si ya tiene ID, sobreescribe el existente (actualización).
     */
    public Producto guardar(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(contadorId++);
        }
        productos.put(producto.getId(), producto);
        return producto;
    }

    /** Elimina el producto con el ID indicado. */
    public void eliminar(Long id) {
        productos.remove(id);
    }
}
