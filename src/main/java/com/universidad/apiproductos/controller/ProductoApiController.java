package com.universidad.apiproductos.controller;

import com.universidad.apiproductos.model.Producto;
import com.universidad.apiproductos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone el CRUD de productos bajo /api/productos.
 *
 * Diferencias clave respecto a @Controller (Post-Contenido 1):
 *   - @RestController = @Controller + @ResponseBody: serializa automáticamente a JSON.
 *   - Retorna ResponseEntity para controlar el código HTTP de cada respuesta.
 *   - No usa vistas (Thymeleaf); la respuesta es siempre JSON.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoApiController {

    @Autowired
    private ProductoService servicio;

    // ── GET /api/productos ──────────────────────────────────────────────────
    /**
     * Lista todos los productos.
     * @return 200 OK + arreglo JSON con todos los productos
     */
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    // ── GET /api/productos/{id} ─────────────────────────────────────────────
    /**
     * Busca un producto por su ID.
     * @return 200 OK + objeto JSON, o 404 Not Found si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)                      // 200 OK con el producto
                .orElse(ResponseEntity.notFound().build());   // 404 Not Found
    }

    // ── POST /api/productos ─────────────────────────────────────────────────
    /**
     * Crea un nuevo producto a partir del JSON del body.
     * @param producto deserializado automáticamente por Jackson desde el body
     * @return 201 Created + objeto creado (con ID asignado)
     */
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto nuevo = servicio.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // ── PUT /api/productos/{id} ─────────────────────────────────────────────
    /**
     * Actualiza completamente un producto existente.
     * @return 200 OK + producto actualizado, o 404 Not Found si el ID no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id,
                                               @RequestBody Producto producto) {
        return servicio.buscarPorId(id)
                .map(existente -> {
                    producto.setId(id);                              // garantiza el ID correcto
                    return ResponseEntity.ok(servicio.guardar(producto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE /api/productos/{id} ──────────────────────────────────────────
    /**
     * Elimina el producto con el ID indicado.
     * @return 204 No Content (sin cuerpo) si existía, o 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (servicio.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();   // 404 Not Found
        }
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();      // 204 No Content
    }
}
