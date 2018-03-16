#!/usr/bin/env bash
echo 'Building the jar ...'
mvn clean test package

echo 'Building docker image ...'
docker build -t challenge .

echo 'Starting stack up ...'
docker stack deploy -c docker-compose.yml demo

echo 'Waiting for sonar to be started'
sleep 40

echo 'Pushing test coverage to sonar ...'
mvn sonar:sonar -Dsonar.host.url=http://localhost:9001
