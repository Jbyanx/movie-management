# 🎬 Movie Management API

API REST para la gestión de **usuarios**, **películas** y **calificaciones**.  
Desarrollada con **Java + Spring Boot**, siguiendo arquitectura **N-capas** y utilizando el patrón **DTO**.

## 🚀 Características

✅ CRUD de **usuarios**, **películas** y **calificaciones**.  
✅ **Arquitectura REST N-capas** con **DTOs**.  
✅ **Spring Data JPA** con **JPA Specification** y consultas **JPQL** (Query Methods).  
✅ **Documentación OpenAPI** con Swagger UI.  
✅ **Manejador global de excepciones** con `@RestControllerAdvice`.  
✅ **Context Path**: `/api/v1`.  

🔜 Mejoras futuras:  
- **Seguridad con JWT** 🔒  
- **Migraciones con Flyway** 🚀  
- **Contenerización con Docker** 🐳  

## 🏗 Arquitectura REST N-capas

El backend sigue la **arquitectura en N-capas**, asegurando **separación de responsabilidades**:


📌 **Flujo de ejecución**:
1. El **Controller** recibe la solicitud HTTP.
2. Valida los **DTOs** de entrada.
3. Llama al **Service**, donde se implementa la lógica de negocio.
4. **Repository** maneja las consultas a la base de datos con **JPA**.
5. Se devuelve la respuesta en un **DTO**.

## 🛠 Tecnologías

| Tecnología       | Descripción                     |
|-----------------|--------------------------------|
| **Java 17+**    | Lenguaje principal             |
| **Spring Boot** | Framework para microservicios  |
| **Spring Data JPA** | ORM para base de datos |
| **PostgreSQL**  | Base de datos relacional       |
| **Maven**       | Gestión de dependencias        |
| **Swagger UI**  | Documentación de la API       |

## 📌 Uso de JPA Specification y JPQL

Se utiliza **JPA Specification** para realizar **búsquedas dinámicas**, permitiendo filtrar entidades según criterios sin escribir múltiples consultas personalizadas.

## 1. Docker run
docker run --hostname=5d6a3b9195ca --env=POSTGRES_PASSWORD=sebaj --env=POSTGRES_USER=jabes --env=POSTGRES_DB=movie-management-db -d postgres

##2. run aplication

## 📌 Swagger UI disponible en: http://localhost:9090/api/v1/swagger-ui.html
