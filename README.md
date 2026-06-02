# galvis-post2-u7 — API REST CRUD con Spring Boot

**Programación Web · Unidad 7: Spring Boot Básico · Post-Contenido 2**  
Universidad Francisco de Paula Santander · Ingeniería de Sistemas · 2026

---

## Descripción

API REST CRUD de gestión de productos construida con **Spring Boot 3.2**.  
Utiliza `@RestController` y `ResponseEntity` para exponer endpoints que devuelven **JSON** con los códigos HTTP correctos para cada operación. La persistencia es en memoria con un `LinkedHashMap`. Incluye manejo global de errores con `@RestControllerAdvice`.

---

## Diferencia respecto al Post-Contenido 1

| Característica | Post-Contenido 1 (MVC + Thymeleaf) | Post-Contenido 2 (API REST) |
|----------------|-----------------------------------|-----------------------------|
| Anotación | `@Controller` | `@RestController` |
| Respuesta | HTML (vistas Thymeleaf) | JSON (Jackson) |
| Redirección | `redirect:/productos` | No aplica |
| Código HTTP | Implícito (200) | Explícito (`ResponseEntity`) |
| Cliente | Navegador | Postman / curl / frontend |

---

## Endpoints de la API

| Método | URL | Código éxito | Código error | Descripción |
|--------|-----|-------------|-------------|-------------|
| GET | `/api/productos` | 200 OK | — | Lista todos los productos |
| GET | `/api/productos/{id}` | 200 OK | 404 Not Found | Busca un producto por ID |
| POST | `/api/productos` | 201 Created | 400 Bad Request | Crea un nuevo producto |
| PUT | `/api/productos/{id}` | 200 OK | 404 Not Found | Actualiza un producto existente |
| DELETE | `/api/productos/{id}` | 204 No Content | 404 Not Found | Elimina un producto |

---

## Prerrequisitos

| Herramienta | Versión |
|-------------|---------|
| Java JDK | 17 o superior |
| Maven | 3.8+ (o usar el wrapper `mvnw`) |
| Postman | Desktop (recomendado) o Thunder Client |

---

## Estructura del proyecto

```
galvis-post2-u7/
├── src/
│   ├── main/
│   │   ├── java/com/universidad/apiproductos/
│   │   │   ├── ApiProductosApplication.java      ← Punto de entrada
│   │   │   ├── model/
│   │   │   │   └── Producto.java                 ← Entidad del dominio
│   │   │   ├── service/
│   │   │   │   └── ProductoService.java          ← Lógica + datos en memoria
│   │   │   └── controller/
│   │   │       ├── ProductoApiController.java    ← Endpoints REST
│   │   │       └── GlobalExceptionHandler.java   ← Manejo global de errores
│   │   └── resources/
│   │       └── application.properties
│   └── test/...
├── capturas/                                     ← Capturas de Postman
├── pom.xml
└── README.md
```

---

## Instrucciones de ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/TU_USUARIO/galvis-post2-u7.git
cd galvis-post2-u7
```

### 2. Ejecutar con Maven
```bash
mvn spring-boot:run
```
O con el wrapper:
```bash
./mvnw spring-boot:run      # Linux / macOS
mvnw.cmd spring-boot:run    # Windows
```

### 3. Verificar que arrancó
En consola debe aparecer:
```
Started ApiProductosApplication in X.XXX seconds
```

### 4. Probar con Postman

**GET — Listar todos:**
```
GET http://localhost:8080/api/productos
```
Respuesta `200 OK`:
```json
[
  { "id": 1, "nombre": "Laptop",  "descripcion": "Laptop 15 pulgadas 16 GB RAM", "precio": 1299.99 },
  { "id": 2, "nombre": "Mouse",   "descripcion": "Mouse inalámbrico ergonómico",  "precio": 29.99  },
  { "id": 3, "nombre": "Teclado", "descripcion": "Teclado mecánico TKL",          "precio": 89.99  }
]
```

**POST — Crear producto:**
```
POST http://localhost:8080/api/productos
Content-Type: application/json

{
  "nombre": "Monitor",
  "descripcion": "Monitor 27 pulgadas 4K",
  "precio": 499.99
}
```
Respuesta `201 Created`:
```json
{ "id": 4, "nombre": "Monitor", "descripcion": "Monitor 27 pulgadas 4K", "precio": 499.99 }
```

**PUT — Actualizar producto:**
```
PUT http://localhost:8080/api/productos/4
Content-Type: application/json

{
  "nombre": "Monitor Curvo",
  "descripcion": "Monitor curvo 32 pulgadas QHD",
  "precio": 699.99
}
```
Respuesta `200 OK`:
```json
{ "id": 4, "nombre": "Monitor Curvo", "descripcion": "Monitor curvo 32 pulgadas QHD", "precio": 699.99 }
```

**DELETE — Eliminar producto:**
```
DELETE http://localhost:8080/api/productos/4
```
Respuesta `204 No Content` (sin cuerpo).

**GET — ID inexistente (error):**
```
GET http://localhost:8080/api/productos/999
```
Respuesta `404 Not Found`:
```json
{ "error": "Producto no encontrado: 999", "timestamp": "2026-06-01T..." }
```

---

## Capturas de pantalla

Las capturas de Postman se encuentran en la carpeta [`/capturas`](./capturas/).

---

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.2.5
- Spring MVC (`@RestController`, `ResponseEntity`)
- Jackson (serialización JSON automática)
- Maven 3
- Git / GitHub
