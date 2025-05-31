# Arjun_AI_Project

A comprehensive Spring Boot application that provides REST APIs for mathematical operations and bill splitting functionality.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
  - [Local Development](#local-development)
  - [Docker Development](#docker-development)
- [API Documentation](#api-documentation)
- [Development Workflow](#development-workflow)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)

## Prerequisites
- Java 17 or higher
- Maven 3.9+
- Docker (optional, for containerized development)
- Git

## Technology Stack
- Spring Boot 3.2.3
- SpringDoc OpenAPI 2.3.0
- JUnit 5
- Docker
- Maven

## Getting Started

### Local Development
1. Clone the repository:
   ```bash
   git clone https://github.com/arjun-master/arjun_ai_project.git
   cd arjun_ai_project
   ```

2. Build the application:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`

### Docker Development
1. Build the Docker image:
   ```bash
   docker build -t helloworld-spring:latest .
   ```

2. Run the container:
   ```bash
   docker run -d -p 8081:8080 --name helloworld-app helloworld-spring:latest
   ```

The application will be available at `http://localhost:8081`

To stop the container:
```bash
docker stop helloworld-app
```

## API Documentation

### Math Operations
- **Addition**: `GET /api/math/add/{num1}/{num2}`
  - Example: `GET /api/math/add/5/3` returns `8`

- **Subtraction**: `GET /api/math/subtract/{num1}/{num2}`
  - Example: `GET /api/math/subtract/10/4` returns `6`

- **Multiplication**: `GET /api/math/multiply/{num1}/{num2}`
  - Example: `GET /api/math/multiply/6/7` returns `42`

- **Division**: `GET /api/math/divide/{num1}/{num2}`
  - Example: `GET /api/math/divide/20/5` returns `4`
  - Note: Division by zero returns an error

### Bill Splitting
- **Equal Split**: `GET /api/split/equal`
  - Parameters:
    - `amount`: Total bill amount
    - `people`: Number of people
  - Example: `GET /api/split/equal?amount=100&people=4`

- **Split with Tip**: `GET /api/split/withTip`
  - Parameters:
    - `amount`: Total bill amount
    - `people`: Number of people
    - `tipPercentage`: Tip percentage
  - Example: `GET /api/split/withTip?amount=100&people=4&tipPercentage=15`

- **Split by Items**: `GET /api/split/byItems`
  - Parameters:
    - `items`: Comma-separated list of item=price pairs
    - `participants`: Comma-separated list of participants
  - Example: `GET /api/split/byItems?items=Pizza=20,Salad=15&participants=Alice,Bob`

## Development Workflow

### Git Workflow
1. Create a feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit:
   ```bash
   git add .
   git commit -m "Descriptive commit message"
   ```

3. Push changes:
   ```bash
   git push origin feature/your-feature-name
   ```

### Docker Workflow
1. After making changes to the application:
   ```bash
   mvn clean package -DskipTests
   docker build -t helloworld-spring:latest .
   ```

2. Test the changes:
   ```bash
   docker run -d -p 8081:8080 --name helloworld-app helloworld-spring:latest
   ```

3. Commit Docker-related changes:
   ```bash
   git add Dockerfile .dockerignore
   git commit -m "Update Docker configuration"
   git push origin main
   ```

## Testing
Run tests using Maven:
```bash
mvn test
```

### Test Coverage
- Unit tests for all controllers
- Integration tests for API endpoints
- Edge case scenarios covered
- Error handling tests

## Deployment
1. Build the production JAR:
   ```bash
   mvn clean package -DskipTests
   ```

2. Build the production Docker image:
   ```bash
   docker build -t helloworld-spring:prod .
   ```

3. Run in production:
   ```bash
   docker run -d -p 8080:8080 --name helloworld-prod helloworld-spring:prod
   ```

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## API Documentation UI
Access the Swagger UI documentation at:
- Local: `http://localhost:8080/swagger-ui.html`
- Docker: `http://localhost:8081/swagger-ui.html` 