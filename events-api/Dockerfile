FROM openjdk:8-jdk-alpine

WORKDIR /app

ADD ./target/*.jar /app/service.jar

RUN chown -R nobody:nobody *

ADD ./wait-for.sh /app

USER nobody

EXPOSE 9090
