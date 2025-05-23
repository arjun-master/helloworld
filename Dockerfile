FROM --platform=linux/amd64 openjdk:17
WORKDIR /app
COPY ./target/helloworld-v1.jar /app
EXPOSE 8090
CMD ["java", "-jar", "helloworld-v1.jar"]
RUN rm -rf target/classes target/maven-status target/generated-* target/test-classes target/maven*
