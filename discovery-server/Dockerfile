# syntax=docker/dockerfile:1.7-labs

# Minimal runtime image for Spring Boot app built with Gradle
# Assumes the jar is built at build/libs/*.jar

FROM eclipse-temurin:24-jre-alpine AS runtime

# Create app user and directories
ENV APP_HOME=/app \
    JAVA_OPTS=""
WORKDIR ${APP_HOME}

# Add a non-root user
RUN addgroup -S app && adduser -S app -G app

# Copy the bootable jar from local build context
# Use wildcard to match versioned artifact name, copy and rename to app.jar
COPY --chown=app:app target/*.jar app.jar

USER app

# Expose Eureka default port
EXPOSE 8761

# Healthcheck hitting Spring Boot actuator if enabled (optional)
# Use busybox wget available in alpine; fall back to exit 1 if unavailable
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=5 \
  CMD sh -c "wget -qO- http://127.0.0.1:8761/actuator/health || exit 1"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
