FROM openjdk:8-jdk-alpine
COPY target/trade_simulator-0.0.1-SNAPSHOT.jar app1.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app1.jar"]
