FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/app_restaurant-0.0.1.jar
COPY ${JAR_FILE} app_restaurant.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app_restaurant.jar"]

