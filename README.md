# Arjun_AI_Project

[![Java Version](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A comprehensive Spring Boot application that provides REST APIs for mathematical operations and bill splitting functionality. Built with industry best practices and modern Spring Boot features.

## 📋 Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Local Development](#local-development)
  - [Docker Development](#docker-development)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Development Guidelines](#development-guidelines)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [Code Coverage](#code-coverage)
- [License](#license)

## ✨ Features
- Mathematical Operations API
  - Addition, Subtraction, Multiplication, Division
  - Input validation and error handling
- Bill Splitting API
  - Equal split calculation
  - Split with tip calculation
  - Item-wise split calculation
- Comprehensive API Documentation
- Actuator endpoints for monitoring
- Logging and Error Handling
- Unit and Integration Tests
- Docker Support

## 🛠 Technology Stack
- Java 17
- Spring Boot 3.2.3
- SpringDoc OpenAPI 2.3.0
- Project Lombok
- MapStruct
- JUnit 5
- Mockito
- Maven
- Docker
- JaCoCo for Code Coverage

## 📝 Prerequisites
- Java 17 or higher
- Maven 3.9+
- Docker (optional)
- Git

## 🚀 Getting Started

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
   docker build -t arjun_ai_project:latest .
   ```

2. Run the container:
   ```bash
   docker run -d -p 8080:8080 --name arjun_ai_project arjun_ai_project:latest
   ```

## 📚 API Documentation
Access the Swagger UI documentation at:
- Local: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/api-docs`

## 📁 Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── arjunai/
│   │           └── project/
│   │               ├── config/
│   │               ├── controllers/
│   │               ├── services/
│   │               ├── models/
│   │               ├── repositories/
│   │               ├── exceptions/
│   │               └── utils/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/
            └── arjunai/
                └── project/
                    └── controllers/
```

## 💻 Development Guidelines
- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Write comprehensive unit tests
- Document all public APIs
- Use meaningful commit messages
- Keep the code clean and maintainable

## 🧪 Testing
Run tests using Maven:
```bash
mvn test
```

Generate test coverage report:
```bash
mvn verify
```

## 📦 Deployment
1. Build the production JAR:
   ```bash
   mvn clean package -DskipTests
   ```

2. Build the production Docker image:
   ```bash
   docker build -t arjun_ai_project:prod .
   ```

3. Run in production:
   ```bash
   docker run -d -p 8080:8080 --name arjun_ai_project_prod arjun_ai_project:prod
   ```

## 🤝 Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📊 Code Coverage
JaCoCo code coverage reports are generated during the build process. View the reports in:
```
target/site/jacoco/index.html
```

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details. 