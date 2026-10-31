# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application securely
FROM eclipse-temurin:17-jre-alpine
# Patch Alpine system packages to fix CVE-2026-111497 in brotli-libs
RUN apk update && apk upgrade --no-cache && rm -rf /var/cache/apk/*
RUN addgroup -S app && adduser -S app -G app
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
USER app
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]