# Use the latest OpenJDK image
FROM openjdk:22-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY target/user-service-0.0.1.jar /app/user-service.jar

# Expose the port that the application will run on
EXPOSE 9090

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "user-service.jar"]
