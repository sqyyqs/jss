FROM openjdk:18.0.2.1-slim-buster
COPY jss-app/target/jss-app-1.0-RELEASE.jar /app/jss-app-1.0-RELEASE.jar
WORKDIR /app
CMD java -jar jss-app-1.0-RELEASE.jar
