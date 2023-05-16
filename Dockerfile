FROM openjdk:18.0.2.1-slim-buster
COPY jss-app/target/jss-app-0.1-SNAPSHOT.jar /app/jss-app-0.1-SNAPSHOT.jar
WORKDIR /app
CMD java -jar jss-app-0.1-SNAPSHOT.jar
