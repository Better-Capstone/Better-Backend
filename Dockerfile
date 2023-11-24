FROM gradle:7.6-jdk AS build
WORKDIR /build

COPY api/src/main /build/api/src/main
COPY api/build.gradle.kts /build/api

COPY batch/src/main /build/batch/src/main
COPY batch/build.gradle.kts /build/batch

COPY core/src/main /build/core/src/main
COPY core/build.gradle.kts /build/core

COPY build.gradle.kts settings.gradle.kts /build/

RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

FROM openjdk:17.0.1-jdk-slim AS run
WORKDIR /app

RUN adduser --system --group app-api

COPY --from=build --chown=app-api:app-api /build/api/build/libs/*.jar ./api.jar
COPY --from=build --chown=app-api:app-api /build/batch/build/libs/*.jar ./batch.jar

EXPOSE 8080
USER app-api

CMD ["java", "-jar", "api.jar"]
CMD ["java", "-jar", "batch.jar"]