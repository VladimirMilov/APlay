# World Cup events streaming API
***
## Introduction
***
This project has been developed for the **[Sofia adidas Hackathon](https://adidas-hack.com/location/Sofia)** and it is just a starter kit for the hackers who want to make use of it. The repository provides a streaming API that publishes soccer events associated with the **Russia 2018 World Cup**. Additionally, it contains several subscribers that retrieve those events and display them into the console. 

## Installation requirements
***
Here is a list of the **required technologies** needed to run the project:

1. Docker
2. Docker compose v3
3. Java v1.8
4. Maven v3
5. Node JS v8
6. NPM

## Components
***
### Option 1: Step by step

#### Message broker
Start **Rabbit MQ Docker** image
```bash
docker run -d --name rabbitmq-broker -p 8080:15672 -p 5672:5672 rabbitmq:3-management
```

#### Streaming API
Move to the events-api directory
```bash
cd events-api
```

Build the application using maven
```bash
mvn clean package -Dlocal
```

Run the streaming API publisher
```bash
java -jar target/*.jar
```

#### Subscribers
Move to the events-client directory
```bash
cd events-client
```

Download and install **NPM** dependencies
```bash
npm -i
```

Run a streaming API subscriber
```bash
QUEUE_NAME=events-queue-team TOPIC=fifa.worldcup.#.Russia.# node index.js 
```

### Option 2: Automated scipts

#### Build everything 
From the root directory execute the  build script: build applications & create **Docker** images with **Docker Compose**
```bash
./build.sh
```

#### Start all the containers with Docker Compose
From the root directory execute **Docker Compose** up command to start the containers
```bash
docker-compose -f development.yml up -d 
```
