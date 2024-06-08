FROM openjdk:17-jdk-slim

VOLUME /app

WORKDIR /app

COPY target/ecoocean-0.0.1-SNAPSHOT.jar /app/ecoocean-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/ecoocean-0.0.1-SNAPSHOT.jar"]
