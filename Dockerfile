FROM maven
WORKDIR /app
COPY . .
CMD ["mvn", "clean", "spring-boot:run"]