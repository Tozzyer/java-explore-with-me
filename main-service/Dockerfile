FROM amazoncorretto:21
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
