# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
# Copy Maven project files
COPY pom.xml .
COPY src ./src
# Build the project
RUN mvn clean package
# Clean up unnecessary folders, ignore errors if folders don't exist
RUN rm -rf target/classes target/maven-status target/generated-* target/test-classes target/maven-* || true

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy only the JAR from the build stage
COPY --from=build /app/target/helloworld-v1.jar /app
EXPOSE 8090
CMD ["java", "-jar", "helloworld-v1.jar"]