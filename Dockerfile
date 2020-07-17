FROM openjdk:11-jdk-slim

ADD ./target/demo-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]