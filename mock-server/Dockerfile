FROM openjdk:17
LABEL authors="gabriel"
EXPOSE 8080
ADD target/mock-server.jar mock-server.jar
ENTRYPOINT ["java", "-jar","mock-server.jar"]