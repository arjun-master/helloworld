# Calculator and Bill Splitting API

A Spring Boot application that provides REST APIs for mathematical operations and bill splitting functionality.

## Features

- Basic mathematical operations (add, subtract, multiply, divide)
- Bill splitting functionality:
  - Equal split
  - Split with tip
  - Unequal split by percentages
  - Split by items

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- SpringDoc OpenAPI 2.3.0
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/helloworld.git
cd helloworld
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:
```
http://localhost:8080/swagger-ui.html
```

### Math Operations API

Base path: `/api/math`

#### Addition
```
GET /api/math/add/{num1}/{num2}
```

#### Subtraction
```
GET /api/math/subtract/{num1}/{num2}
```

#### Multiplication
```
GET /api/math/multiply/{num1}/{num2}
```

#### Division
```
GET /api/math/divide/{num1}/{num2}
```

### Bill Splitting API

Base path: `/api/split`

#### Equal Split
```
GET /api/split/equal/{totalAmount}/{numberOfPeople}
```

#### Split with Tip
```
GET /api/split/with-tip/{billAmount}/{tipPercentage}/{numberOfPeople}
```

#### Unequal Split
```
GET /api/split/unequal/{totalAmount}?shares={personName}={sharePercentage}
```
Example:
```
GET /api/split/unequal/100?Alice=40&Bob=35&Charlie=25
```

#### Split by Items
```
GET /api/split/per-item?items={itemName}={price}&participants={name1},{name2}&tipPercentage={tip}
```
Example:
```
GET /api/split/per-item?items=Pizza=20&items=Drinks=15&participants=Alice,Bob&tipPercentage=15
```

## Error Handling

The API uses standard HTTP status codes:
- 200: Successful operation
- 400: Bad request (invalid input)
- 500: Internal server error

## Logging

The application uses SLF4J for logging:
- INFO level for incoming requests
- DEBUG level for calculation results
- ERROR level for validation failures

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 