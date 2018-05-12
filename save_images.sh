#!/bin/bash
IMAGE_FOLDER=dockerImages

echo "######################################################"
echo "# SAVE OPENJDK"
echo "######################################################"
docker save openjdk:8-jdk-alpine -o $IMAGE_FOLDER/openjdk.tar

echo "######################################################"
echo "# SAVE NODE"
echo "######################################################"
docker save node:carbon-alpine -o $IMAGE_FOLDER/node.tar

echo "######################################################"
echo "# SAVE RABBITMQ"
echo "######################################################"
docker save rabbitmq:3-management -o $IMAGE_FOLDER/rabbitmq.tar

echo "######################################################"
echo "# SAVE EVENTS API"
echo "######################################################"
docker save events-api -o $IMAGE_FOLDER/events-api.tar

echo "######################################################"
echo "# SAVE CLIENT API"
echo "######################################################"
docker save events-client -o $IMAGE_FOLDER/events-client.tar
