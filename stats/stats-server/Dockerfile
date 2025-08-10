FROM amazoncorretto:21
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9090 9091
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
