# Appointment Booking

This project provides a solution for the Appointment Booking. It includes a Spring Boot application connected to a PostgreSQL database. The entire setup can be easily run using Docker and Docker Compose.

## Prerequisites

Before running the application, ensure you have the following installed:

- Docker
- Docker Compose

## Steps to Run the Application

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/yourusername/enpal-coding-challenge.git
cd enpal-coding-challenge
```

### 2. Build and Start the Application with Docker Compose

To set up both the PostgreSQL database and the Spring Boot application, use Docker Compose. This will automatically build the necessary Docker images for both services and run them together.

Run the following command:

```bash
docker-compose up --build
```

### 3. Wait for the Application to Start

Docker Compose will automatically initialize the services. The database will start first, followed by the Spring Boot application. You can monitor the progress by checking the terminal output.

- The PostgreSQL database will be available at `localhost:5432`.
- The Spring Boot application will be available at `localhost:3000`.

### 4. Access the Application

Once the services are running, you can access the Spring Boot application at the following URL:

```bash
http://localhost:3000
```

### 5. Test the Application

To test the application, you can make HTTP requests to the following endpoint:

`POST /calendar/query`

Sample JSON request body:

```json
{
  "date": "2024-05-03",
  "products": ["SolarPanels", "Heatpumps"],
  "language": "German",
  "rating": "Gold"
}
```

### 6. Stop the Application

To stop the application and remove the containers, run:

```bash
docker-compose down
```
