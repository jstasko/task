FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/task-1.0-SNAPSHOT.jar app.jar
COPY src/main/resources/app.properties /app/app.properties
COPY src/main/resources/init.sql /app/init.sql

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]