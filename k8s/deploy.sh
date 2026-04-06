#!/bin/bash
# ==============================================
# Deploy Car Wash System to Kubernetes
# Usage: ./deploy.sh
# ==============================================

echo "============================================"
echo "Deploying Car Wash System to Kubernetes"
echo "============================================"

# Step 1: Namespace & Secrets
echo ""
echo "[1/6] Creating namespace and secrets..."
kubectl apply -f namespace.yml
kubectl apply -f secrets.yml

# Step 2: Infrastructure
echo ""
echo "[2/6] Deploying infrastructure (MySQL, RabbitMQ, Redis, Zipkin)..."
kubectl apply -f infrastructure.yml
echo "Waiting for MySQL to be ready..."
kubectl wait --for=condition=ready pod -l app=mysql -n carwash --timeout=120s
echo "Waiting for RabbitMQ to be ready..."
kubectl wait --for=condition=ready pod -l app=rabbitmq -n carwash --timeout=120s
echo "Waiting for Redis to be ready..."
kubectl wait --for=condition=ready pod -l app=redis -n carwash --timeout=60s

# Step 3: Monitoring
echo ""
echo "[3/6] Deploying monitoring (Prometheus, Grafana)..."
kubectl apply -f monitoring.yml

# Step 4: Discovery & Config
echo ""
echo "[4/6] Deploying Eureka Server & Config Server..."
kubectl apply -f discovery-config.yml
echo "Waiting for Eureka Server to be ready..."
kubectl wait --for=condition=ready pod -l app=eureka-server -n carwash --timeout=180s
echo "Waiting for Config Server to be ready..."
kubectl wait --for=condition=ready pod -l app=config-server -n carwash --timeout=180s

# Step 5: API Gateway
echo ""
echo "[5/6] Deploying API Gateway..."
kubectl apply -f gateway.yml
echo "Waiting for API Gateway to be ready..."
kubectl wait --for=condition=ready pod -l app=api-gateway -n carwash --timeout=180s

# Step 6: Business Services
echo ""
echo "[6/6] Deploying business services..."
kubectl apply -f services.yml

echo ""
echo "============================================"
echo "Deployment complete!"
echo "============================================"
echo ""
echo "Check status:  kubectl get pods -n carwash"
echo "Gateway URL:   kubectl get svc api-gateway -n carwash"
echo "Grafana:       kubectl port-forward svc/grafana 3000:3000 -n carwash"
echo "Prometheus:    kubectl port-forward svc/prometheus 9090:9090 -n carwash"
