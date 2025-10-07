# Use Maven image to build the project
FROM maven:3.9.5-eclipse-temurin-21 AS build

# Install protobuf and grpc dependencies
USER root
RUN apt-get update && apt-get install -y protobuf-compiler

WORKDIR /app

# Copy only the pom.xml first to leverage Docker cache
COPY pom.xml .

# Download all dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller JRE image to run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Set the entry point
ENTRYPOINT ["java", "-jar", "app.jar"]