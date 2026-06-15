# stage 1: Use Maven for building
FROM maven:3.9.15-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# stage 2: use OpenJdk for running
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar discoveryserver.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","discoveryserver.jar"]