FROM eclipse-temurin:21-jdk AS api-builder

WORKDIR /app
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle

COPY src ./src

RUN chmod +x gradlew

RUN ./gradlew build --no-daemon

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=api-builder /app/build/libs/api-*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]