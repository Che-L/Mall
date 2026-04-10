# 多阶段构建：按模块产出 Spring Boot 可执行 JAR（Java 21）
# 全栈一键构建并启动（含中间件）：在项目根目录执行  docker compose up -d --build
# 单独构建某模块：docker build -f docker/Dockerfile --build-arg MODULE=mall-admin -t mall-admin:local .

FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /build

COPY pom.xml .
COPY mall-common mall-common
COPY mall-mbg mall-mbg
COPY mall-security mall-security
COPY mall-admin mall-admin
COPY mall-portal mall-portal
COPY mall-search mall-search
COPY mall-demo mall-demo

ARG MODULE=mall-admin
RUN mvn -B -q package -pl ${MODULE} -am -DskipTests

FROM eclipse-temurin:21-jre-jammy
ARG MODULE=mall-admin
ENV MODULE=${MODULE}
WORKDIR /app

COPY --from=builder /build/${MODULE}/target/${MODULE}-*.jar /app/app.jar

# 默认端口；各模块可在运行时通过 SERVER_PORT 覆盖
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS:-} -jar /app/app.jar"]
