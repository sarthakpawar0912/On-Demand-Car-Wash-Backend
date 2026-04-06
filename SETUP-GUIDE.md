# Car Wash System - Complete Setup Guide

## After Restart Instructions

### Step 1: Start Infrastructure (Docker)
Open Docker Desktop, wait for it to start, then in admin terminal:
```
net stop MySQL80
net stop Grafana
```

### Step 2: Run Docker Compose
```bash
cd "C:\Users\Sarthak\OneDrive\Desktop\Car Wash System"
docker compose up -d
```

This starts ALL 17 containers:
- MySQL, RabbitMQ, Redis, Zipkin, Prometheus, Grafana
- Eureka Server, Config Server
- API Gateway + 8 business services

### Step 3: Verify
```bash
docker compose ps
```
All containers should show "Up" or "Healthy".

### Step 4: Test
```bash
# Login
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d "{\"email\":\"sarthak@gmail.com\",\"password\":\"1234\"}"

# Use token for order
curl http://localhost:8080/order/my -H "Authorization: Bearer <token>"
```

---

## OR: Run Services Locally (without Docker Compose)

### Start Infrastructure Docker Containers
```bash
docker run -d --hostname rabbitmq -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:3-management
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
docker run -d -p 6379:6379 --name redis redis
```

### Start MySQL
MySQL80 Windows service should be running (or start it: `net start MySQL80`)

### Start Prometheus (standalone)
```bash
cd C:\Users\Sarthak\Downloads\prometheus-3.11.0.windows-amd64\prometheus-3.11.0.windows-amd64
prometheus.exe
```

### Start Grafana (standalone)
It auto-starts as Windows service. Or: `net start Grafana`

### Start Services (in order)
1. Eureka Server (8761)
2. Config Server (8888)
3. API Gateway (8080)
4. All other services (any order)

---

## Access URLs

| Tool | URL | Login |
|------|-----|-------|
| API Gateway | http://localhost:8080 | JWT token |
| Eureka | http://localhost:8761 | - |
| Config Server | http://localhost:8888 | - |
| Prometheus | http://localhost:9090 | - |
| Grafana | http://localhost:3000 (standalone) or :3001 (docker) | admin/admin |
| Zipkin | http://localhost:9411 | - |
| RabbitMQ | http://localhost:15672 | guest/guest |

## Grafana Setup (if fresh)
1. Open Grafana → login admin/admin
2. Add datasource → Prometheus → URL: http://localhost:9090 (standalone) or http://prometheus:9090 (docker)
3. Import dashboards via JSON file: grafana-carwash-dashboard.json

## Service Ports
| Service | Port |
|---------|------|
| API Gateway | 8080 |
| User Service | 8081 |
| Car Service | 8082 |
| Order Service | 8083 |
| Payment Service | 8084 |
| Rating Service | 8086 |
| Invoice Service | 8087 |
| Admin Service | 8088 |
| Notification Service | 8089 |
| Eureka Server | 8761 |
| Config Server | 8888 |

## Test Users
| Email | Password | Role |
|-------|----------|------|
| sarthak@gmail.com | 1234 | CUSTOMER |
| washer@gmail.com | 1234 | WASHER |
| admin@gmail.com | 1234 | ADMIN |

## Architecture
```
Client → API Gateway (JWT + Rate Limiter) → Microservices
         Gateway validates JWT once
         Forwards X-User-Email + X-User-Role headers
         Services read headers (no JWT parsing)
```

## Key Files Created/Modified

### Docker
- 11 Dockerfiles (one per service)
- docker-compose.yml (full system)
- prometheus-docker.yml (for Docker networking)

### Kubernetes
- k8s/namespace.yml
- k8s/infrastructure.yml (MySQL, RabbitMQ, Redis, Zipkin)
- k8s/discovery-config.yml (Eureka, Config Server)
- k8s/gateway.yml (API Gateway with LoadBalancer)
- k8s/services.yml (all 8 business services)

### Monitoring
- prometheus.yml (standalone config)
- grafana-carwash-dashboard.json (custom dashboard)

### Security Changes
- API Gateway: JwtAuthenticationFilter.java, RateLimiterConfig.java
- User Service: SecurityConfig.java (permitAll), JwtAuthFilter.java (disabled)
- All services: Controllers read X-User-Email/X-User-Role headers
- All services: FeignConfig forwards X-User headers
- All services: micrometer-registry-prometheus added to pom.xml
- Admin/Invoice: Circuit breaker + Resilience4j added

### GitHub Central Config Updates Needed
- ADMIN-SERVICE.properties: add resilience4j config
- INVOICE-SERVICE.properties: add resilience4j config
- NOTIFICATION-SERVICE.properties: add eureka.instance.hostname=localhost
- API-GATEWAY.properties: notification route added
