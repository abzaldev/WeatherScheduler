FROM gradle:7.5.1-jdk17-alpine AS builder
COPY src /usr/src/app/src
COPY *.mvn  /usr/src/app
RUN gradle build -x test --project-dir  /usr/src/app
 
FROM openjdk:18.0.2-jdk-oracle
WORKDIR /app
COPY --from=builder /usr/src/app/build/libs/*.jar /app/app.jar
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 3281
CMD ["java","-jar","app.jar"]

FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]