package com.universidad.apiproductos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Manejador global de excepciones para la API REST.
 *
 * Sin esta clase, Spring Boot devuelve una página HTML de error (Whitelabel),
 * lo cual es inapropiado para una API que siempre debe responder JSON.
 *
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura cualquier RuntimeException lanzada en los controladores.
     * Ejemplo: "Producto no encontrado: 999"
     * @return 404 Not Found con cuerpo JSON { "error": "...", "timestamp": "..." }
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error",     ex.getMessage(),
                        "timestamp", LocalDateTime.now().toString()
                ));
    }

    /**
     * Captura errores genéricos no contemplados por otros handlers.
     * @return 500 Internal Server Error con cuerpo JSON
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error",     "Error interno del servidor: " + ex.getMessage(),
                        "timestamp", LocalDateTime.now().toString()
                ));
    }
}
