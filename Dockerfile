FROM maven:3-amazoncorretto-23-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM amazoncorretto:23-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY .env .
CMD ["java", "-jar", "app.jar"]
