# Step 1: Use a Maven image to build the project
FROM maven:3.8.7-eclipse-temurin-17-focal as build

RUN apt-get update && apt-get install -y nodejs npm
RUN npm cache clean -f && npm install -g n && n stable


# Set the working directory inside the container
WORKDIR /app

# Copy everything
COPY . .

# Build the project using Maven
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight JDK image to run the application
FROM eclipse-temurin:17-jdk-focal as main

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/exec/target/fhir-gateway-exec.jar ./fhir-gateway-exec.jar

# Expose the application port (adjust this based on your app's configuration)
EXPOSE 8080

# Run the application
ENTRYPOINT java -jar fhir-gateway-exec.jar --server.port=${PROXY_PORT}
