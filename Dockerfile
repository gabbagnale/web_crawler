FROM eclipse-temurin:17-jdk-focal
ADD target/web_crawler-1.0.0.jar web_crawler-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "web_crawler-1.0.0.jar"]