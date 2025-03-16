# Imagen base optimizada
FROM openjdk:17-jdk-slim

# Metadata del autor
LABEL authors="jabesborrejy"

# Crear un usuario para ejecutar la app
RUN useradd -m appuser

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR con permisos del usuario no root
COPY --chown=appuser:appuser target/moviemanagement-0.0.1-SNAPSHOT.jar app.jar

# Cambiar al usuario no root
USER appuser

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la app
CMD ["java", "-jar", "app.jar"]
