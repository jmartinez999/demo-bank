FROM openjdk:17-alpine
COPY target/bankdemo-1.0-SNAPSHOT-jar-with-dependencies.jar /app/bankdemo-service.jar
ENTRYPOINT ["java", "-jar", "/app/bankdemo-service.jar"]