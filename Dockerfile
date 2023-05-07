FROM openjdk:18.0.2.1-slim-buster
COPY app/target/app-0.1-SNAPSHOT.jar /app/app-0.1-SNAPSHOT.jar
WORKDIR /app
CMD java -jar app-0.1-SNAPSHOT.jar
