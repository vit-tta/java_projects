# Общий Dockerfile
FROM maven:3.9.7-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# ARG для имени модуля (будет передан из docker-compose)
ARG MODULE_NAME

# Собираем указанный модуль
RUN mvn clean package -pl ${MODULE_NAME} -am -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

ARG MODULE_NAME
ARG PORT=8080

COPY --from=build /app/${MODULE_NAME}/target/*.jar app.jar

EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]