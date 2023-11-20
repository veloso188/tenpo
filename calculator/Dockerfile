FROM openjdk:17
LABEL authors="gabriel"
EXPOSE 8080
ADD target/calculator.jar calculator.jar
ENTRYPOINT ["java", "-jar","calculator.jar"]