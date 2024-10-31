FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE ${INSURANCE_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]