FROM maven:3.9.15-eclipse-temurin-17 AS build
WORKDIR /app
COPY ./apigateway ./apigateway

WORKDIR /app/apigateway
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/apigateway/target/*.jar apigateway.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","apigateway.jar"]