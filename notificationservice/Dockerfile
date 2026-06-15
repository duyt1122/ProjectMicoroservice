FROM maven:3.9.15-eclipse-temurin-17 AS build
WORKDIR /app
COPY ./notificationservice ./notificationservice
COPY ./commonservice ./commonservice
WORKDIR /app/commonservice
RUN mvn clean install -DskipTests
WORKDIR /app/notificationservice
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/notificationservice/target/*.jar notificationservice.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","notificationservice.jar"]