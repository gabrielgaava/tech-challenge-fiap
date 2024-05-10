FROM amazoncorretto:21-alpine

WORKDIR /app
COPY build/libs/tech-challenge-*.jar /app/tech-challenge.jar
COPY src/main/resources /app/resources

CMD ["java", "-jar", "tech-challenge.jar"]