#!/bin/bash
IMAGE_FOLDER=dockerImages

echo "######################################################"
echo "# LOAD OPENJDK"
echo "######################################################"
docker load -i $IMAGE_FOLDER/openjdk.tar

echo "######################################################"
echo "# LOAD NODE"
echo "######################################################"
docker load -i $IMAGE_FOLDER/node.tar

echo "######################################################"
echo "# LOAD RABBITMQ"
echo "######################################################"
docker load -i $IMAGE_FOLDER/rabbitmq.tar
