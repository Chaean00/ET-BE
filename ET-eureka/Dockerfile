# 1단계: 빌드 단계
FROM gradle:8.12.1-jdk17 AS builder
WORKDIR /workspace
COPY . ./

# 1) 특정 모듈(ET-eureka)만 빌드
RUN gradle :ET-eureka:clean :ET-eureka:build --no-daemon

# 2단계: 실행 단계
FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /workspace/ET-eureka/build/libs/*.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]