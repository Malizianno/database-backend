# --- Stage 1: Build the JAR ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the production package
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Run the Application ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the dynamic port Render provides
EXPOSE 8881

# Run the app with the production profile active
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "app.jar", "--spring.profiles.active=prod"]