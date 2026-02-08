# Используем готовый образ с Maven и Java
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Копируем исходный код gateway
COPY api-gateway/pom.xml /tmp/
COPY api-gateway/src /tmp/src/

# Собираем проект
WORKDIR /tmp
RUN mvn clean package -DskipTests

# Финальный образ
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /tmp/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
