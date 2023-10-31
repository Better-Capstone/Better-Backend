#FROM gradle:7.6-jdk17-alpine as builder
#WORKDIR /build
#
## 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
#COPY build.gradle.kts settings.gradle.kts /build/
#RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true
#
## 빌더 이미지에서 애플리케이션 빌드
#COPY . /build
#RUN gradle build -x test --parallel
#
## APP
#FROM openjdk:17.0-slim
#WORKDIR /app
#
## 빌더 이미지에서 jar 파일만 복사
#COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar
#
#EXPOSE 8080
#
## root 대신 nobody 권한으로 실행
#USER nobody
#ENTRYPOINT [                                                \
#    "java",                                                 \
#    "-jar",                                                 \
#    "-Djava.security.egd=file:/dev/./urandom",              \
#    "-Dsun.net.inetaddr.ttl=0",                             \
#    "app.jar"              \
#]

FROM gradle:7.6-jdk AS build
WORKDIR /build

COPY api/src/main /build/api/src/main
COPY api/build.gradle.kts /build/api

COPY core/src/main /build/core/src/main
COPY core/build.gradle.kts /build/core

COPY build.gradle.kts settings.gradle.kts /build/

RUN ls
RUN ls /
RUN ls /build
RUN ls /build/api
RUN ls /build/api/build
RUN ls /build/api/build/libs

RUN gradle clean build

FROM openjdk:17.0.1-jdk-slim AS run
WORKDIR /app

RUN adduser --system --group app-api

COPY --from=build --chown=app-api:app-api /build/api/build/libs/*.jar ./app.jar

EXPOSE 8080
USER app-api

CMD ["java", "-jar", "app.jar"]