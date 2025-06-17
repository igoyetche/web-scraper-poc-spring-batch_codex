FROM eclipse-temurin:21-jdk-jammy as build
WORKDIR /workspace
COPY . .
RUN ./gradlew build -x test

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /workspace/build/libs/article-scraper-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
