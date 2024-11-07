#!/bin/bash

# Variables
DOCKERHUB_USERNAME="davidnjau21"
REPOSITORY_NAME="open_chanjo"
DOCKERFILE_PATH="./Dockerfile"
CONFIG_FILE="./server/src/main/resources/roles-config.json"

# Extract platform information from roles-config.json
PLATFORM=$(jq -r '.baseUrl.platform' $CONFIG_FILE)

# Check if jq is installed (jq is used to parse JSON)
if ! [ -x "$(command -v jq)" ]; then
  echo "Error: jq is not installed. Install it by running 'sudo apt-get install jq' or use your package manager."
  exit 1
fi

# Check if platform is available
if [ -z "$PLATFORM" ]; then
  echo "Error: Could not read platform information from $CONFIG_FILE."
  exit 1
fi

# Build the Docker image
IMAGE_TAG="${DOCKERHUB_USERNAME}/${REPOSITORY_NAME}:${PLATFORM}"
echo "Building Docker image with tag: $IMAGE_TAG"
docker build -t $IMAGE_TAG -f $DOCKERFILE_PATH .

# Check if Docker build was successful
if [ $? -ne 0 ]; then
  echo "Error: Docker image build failed."
  exit 1
fi

# Push the image to Docker Hub
echo "Pushing image to Docker Hub: $IMAGE_TAG"
docker push $IMAGE_TAG

# Check if Docker push was successful
if [ $? -ne 0 ]; then
  echo "Error: Failed to push Docker image to Docker Hub."
  exit 1
fi

echo "Docker image $IMAGE_TAG pushed successfully."
