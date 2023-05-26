# Use a base image with Java 11 installed
FROM adoptopenjdk:11-jdk-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/dashboard-0.0.1-SNAPSHOT.jar /app/dashboard.jar

# Expose the port on which the application will run
EXPOSE 8080

# Set the command to run when the container starts
CMD ["java", "-jar", "dashboard.jar"]
