FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace/app

COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17
VOLUME /tmp
COPY --from=build /workspace/app/target/arjun_ai_project-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080