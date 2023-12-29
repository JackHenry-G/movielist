FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.01-jdk-slim
COPY --from=build /target/movielist-0.0.1-SNAPSHOT.jar movielist.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "-Dspring.profiles.active=prod", "movielist.jar" ]