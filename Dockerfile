# Multi-stage Dockerfile for TalentLens
# Stage 1: Build React frontend
FROM node:18-alpine AS frontend-build

WORKDIR /app/frontend

# Debug: Show environment info
RUN echo "==== STAGE 1: Frontend Build ====" && \
    echo "Node version:" && node --version && \
    echo "npm version:" && npm --version && \
    echo "Working directory:" && pwd

# Copy package files and install dependencies
COPY frontend/package*.json ./

# Debug: Show copied files
RUN echo "==== Copied package files ====" && \
    ls -lah && \
    echo "==== package.json content ====" && \
    cat package.json

# Install dependencies with verbose output
RUN echo "==== Running npm ci ====" && \
    npm ci || (echo "npm ci failed, trying npm install..." && npm install)

# Debug: Show installed dependencies
RUN echo "==== Dependencies installed ====" && \
    ls -lah node_modules/ | head -20 && \
    echo "Total node_modules size:" && \
    du -sh node_modules/

# Copy frontend source code
COPY frontend/ ./

# Debug: Show all files before build
RUN echo "==== All frontend files ====" && \
    ls -lah && \
    echo "==== src directory ====" && \
    ls -lah src/ || echo "No src directory found"

# Build the React app with verbose output
RUN echo "==== Building React app ====" && \
    npm run build

# Debug: Verify build output
RUN echo "==== Build output ====" && \
    ls -lah build/ && \
    echo "Build size:" && \
    du -sh build/

# Stage 2: Build Spring Boot backend
FROM maven:3.9-eclipse-temurin-17 AS backend-build

WORKDIR /app

# Debug: Show environment info
RUN echo "==== STAGE 2: Backend Build ====" && \
    echo "Java version:" && java -version && \
    echo "Maven version:" && mvn -version && \
    echo "Working directory:" && pwd

# Copy pom.xml and download dependencies (for layer caching)
COPY pom.xml ./

# Debug: Show pom.xml
RUN echo "==== pom.xml copied ====" && \
    ls -lah pom.xml && \
    echo "First 30 lines of pom.xml:" && \
    head -30 pom.xml

# Download dependencies with debug output
RUN echo "==== Downloading Maven dependencies ====" && \
    mvn dependency:go-offline -B -X || \
    (echo "==== Maven dependency download failed ====" && exit 1)

# Copy source code
COPY src ./src

# Debug: Show source structure
RUN echo "==== Source code structure ====" && \
    find src -type f -name "*.java" | head -20 && \
    echo "Total Java files:" && \
    find src -type f -name "*.java" | wc -l

# Copy the built frontend to Spring Boot's static resources directory
COPY --from=frontend-build /app/frontend/build ./src/main/resources/static

# Debug: Verify frontend copied to static resources
RUN echo "==== Frontend copied to static resources ====" && \
    ls -lah src/main/resources/static/ && \
    echo "Static resources size:" && \
    du -sh src/main/resources/static/ && \
    echo "Files in static:" && \
    find src/main/resources/static -type f | head -20

# Build the application (skip tests for faster build) with verbose output
RUN echo "==== Building Spring Boot application ====" && \
    mvn clean package -DskipTests -X || \
    (echo "==== Maven build failed ====" && exit 1)

# Debug: Verify JAR was created
RUN echo "==== Build artifacts ====" && \
    ls -lah target/*.jar && \
    echo "JAR size:" && \
    du -sh target/*.jar

# Stage 3: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Debug: Show environment info
RUN echo "==== STAGE 3: Runtime Container ====" && \
    echo "Java version:" && java -version && \
    echo "Working directory:" && pwd

# Create a non-root user for security
RUN echo "==== Creating non-root user ====" && \
    addgroup -S spring && adduser -S spring -G spring && \
    chown -R spring:spring /app

# Copy the JAR file from build stage
COPY --chown=spring:spring --from=backend-build /app/target/TalentLens-1.0-SNAPSHOT.jar app.jar

# Debug: Verify JAR copied
RUN echo "==== JAR file in runtime ====" && \
    ls -lah app.jar && \
    echo "JAR size:" && \
    du -sh app.jar

USER spring:spring

# Expose port
EXPOSE 8080

# Debug: Show final setup
RUN echo "==== Runtime setup complete ====" && \
    echo "User: $(whoami)" && \
    echo "Home: $HOME" && \
    echo "Working dir: $(pwd)" && \
    ls -lah

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/admin/settings || exit 1

# Run the application with verbose GC and debug output
ENTRYPOINT ["java", "-verbose:gc", "-XX:+PrintCommandLineFlags", "-jar", "app.jar"]

