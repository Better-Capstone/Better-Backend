FROM gradle:7.6-jdk AS build
WORKDIR /build

COPY batch/src/main /build/batch/src/main
COPY batch/build.gradle.kts /build/batch

COPY core/src/main /build/core/src/main
COPY core/build.gradle.kts /build/core

COPY build.gradle.kts settings.gradle.kts /build/

RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

FROM openjdk:17.0.1-jdk-slim AS run
WORKDIR /app

RUN adduser --system --group app-batch

COPY --from=build --chown=app-api:app-batch /build/batch/build/libs/*.jar ./app.jar

EXPOSE 8081
USER app-batch

CMD ["java", "-jar", "app.jar"]