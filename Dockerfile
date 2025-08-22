# Builder Stage
FROM maven:3.9.11-amazoncorretto-21 AS builder

WORKDIR /app

COPY .mvn .mvn
COPY pom.xml .
COPY mvnw .

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

# Runner Stage
FROM amazoncorretto:21-alpine AS runner

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 5000

ENTRYPOINT ["java","-jar","app.jar"]