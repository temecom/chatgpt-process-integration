#!/bin/bash
# Build the Docker image
docker build -t temecom/brainstorm-db .

# Tag the Docker image
docker tag temecom/brainstorm-db temecom/brainstorm-db:latest

# Log in to Docker Hub
docker login

# Push the Docker image
docker push temecom/brainstorm-db:latest
