# Use an OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled application
COPY target/appointment-booking.jar appointment-booking.jar

# Expose the port
EXPOSE 3000

# Run the application
CMD ["java", "-jar", "appointment-booking.jar"]
