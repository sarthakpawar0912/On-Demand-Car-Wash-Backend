#!/bin/bash
# ==============================================
# Push all Car Wash images to Docker Hub
# Usage: ./push-to-dockerhub.sh [tag]
# Default tag: latest
# ==============================================

DOCKER_HUB_USER="sarthakpawar09"
TAG="${1:-latest}"

SERVICES=(
  "admin-service"
  "api-gateway"
  "car-service"
  "config-server"
  "eureka-server"
  "invoice-service"
  "notification-service"
  "order-service"
  "payment-service"
  "rating-service"
  "user-service"
)

echo "============================================"
echo "Docker Hub Push - Car Wash System"
echo "User: $DOCKER_HUB_USER | Tag: $TAG"
echo "============================================"

# Make sure you're logged in first: docker login -u sarthakpawar09

# Build, tag, and push each service
for svc in "${SERVICES[@]}"; do
  echo ""
  echo "--- $svc ---"

  LOCAL_IMAGE="carwashsystem-${svc}:latest"
  REMOTE_IMAGE="${DOCKER_HUB_USER}/carwash-${svc}:${TAG}"

  # Tag
  echo "Tagging: $LOCAL_IMAGE -> $REMOTE_IMAGE"
  docker tag "$LOCAL_IMAGE" "$REMOTE_IMAGE"

  # Push
  echo "Pushing: $REMOTE_IMAGE"
  docker push "$REMOTE_IMAGE"

  if [ $? -eq 0 ]; then
    echo "OK: $svc pushed successfully"
  else
    echo "FAILED: $svc push failed!"
  fi
done

echo ""
echo "============================================"
echo "All images pushed to Docker Hub!"
echo "Images available at: hub.docker.com/u/$DOCKER_HUB_USER"
echo "============================================"
