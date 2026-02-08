# Используем готовый образ с Maven и Java
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Копируем исходный код
COPY payment-service/pom.xml /tmp/
COPY payment-service/src /tmp/src/

# Собираем проект
WORKDIR /tmp
RUN mvn clean package -DskipTests

# Финальный образ
FROM eclipse-temurin:17-jre
COPY --from=build /tmp/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]