# Используем официальный образ Maven с Java 17 для сборки
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app

# Копируем файлы проекта и выполняем сборку
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Используем образ Ubuntu для запуска приложения
FROM openjdk:17-jdk-slim
WORKDIR /app

# Копируем JAR-файл из предыдущего этапа
COPY --from=build /app/target/CarShopService-1.0.jar /app/CarShopService-1.0.jar

# Копируем конфигурацию Liquibase
COPY src/main/resources/properties/liquibase.properties /app/liquibase.properties

# Определяем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/CarShopService-1.0.jar"]
