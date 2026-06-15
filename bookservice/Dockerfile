FROM maven:3.9.15-eclipse-temurin-17 AS build
WORKDIR /app
COPY ./bookservice ./bookservice
COPY ./commonservice ./commonservice
WORKDIR /app/commonservice
RUN mvn clean install -DskipTests
WORKDIR /app/bookservice
RUN mvn clean package -DskipTests

# stage 2: use OpenJdk for running
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/bookservice/target/*.jar bookservice.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","bookservice.jar"]