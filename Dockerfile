# Usar la imagen de OpenJDK 21 como base
FROM openjdk:21-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo gradlew y las configuraciones de Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src/ ./src/

# Dar permisos de ejecución al archivo gradlew
RUN chmod +x gradlew

# Ejecutar gradle build para construir el proyecto
RUN ./gradlew build

# Copiar el archivo JAR generado por Gradle
COPY build/libs/resource-management-service-0.0.1-SNAPSHOT.jar resource-management-service.jar

# Exponer el puerto en el que el servicio escuchará
EXPOSE 8083

# Comando para ejecutar el Resource Management Service
ENTRYPOINT ["java", "-jar", "resource-management-service.jar"]
