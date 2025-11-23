# Use Maven image to build the app
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -e -DskipTests clean package

# Use lightweight JDK image to run the app
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render will inject PORT environment variable
EXPOSE 8080
ENV PORT=8080

# Start Spring Boot
CMD ["java", "-jar", "app.jar"]
