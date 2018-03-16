FROM openjdk:8-jre-alpine

COPY target/challenge*.jar /usr/src/challenge/challenge.jar
WORKDIR /usr/src/challenge

EXPOSE 8080 9090

CMD java -jar challenge.jar