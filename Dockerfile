FROM openjdk:17-jre-slim

WORKDIR /app

COPY target/patient-trials-0.0.1-SNAPSHOT.jar app.jar

EXPOSE $PORT

CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]