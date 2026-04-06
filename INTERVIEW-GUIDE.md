# On-Demand Car Wash System - Interview Preparation Guide

> A comprehensive interview preparation guide for explaining the **On-Demand Car Wash System** - a production-grade microservices backend built with **Java 21**, **Spring Boot 3.x**, and **Spring Cloud 2023.x**. This guide covers architecture decisions, technical deep dives, design patterns, challenges faced, and ready-to-use answers for common interview questions.

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.3-brightgreen?style=for-the-badge&logo=spring)](https://spring.io/projects/spring-cloud)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-Deployment-blue?style=for-the-badge&logo=kubernetes)](https://kubernetes.io/)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Event%20Driven-orange?style=for-the-badge&logo=rabbitmq)](https://www.rabbitmq.com/)
[![Redis](https://img.shields.io/badge/Redis-Rate%20Limiting-red?style=for-the-badge&logo=redis)](https://redis.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)](https://www.mysql.com/)

---

## Table of Contents

| # | Section | What It Covers |
|---|---------|---------------|
| 1 | [Project Introduction](#1-project-introduction) | How to start describing the project |
| 2 | [Architecture Overview](#2-architecture-overview) | System design and service inventory |
| 3 | [Why Microservices?](#3-why-microservices) | Justification and comparison with monolith |
| 4 | [API Gateway Pattern](#4-api-gateway-pattern) | Spring Cloud Gateway deep dive |
| 5 | [Service Discovery](#5-service-discovery---eureka) | Netflix Eureka explanation |
| 6 | [Centralized Configuration](#6-centralized-configuration---config-server) | Spring Cloud Config + Cloud Bus |
| 7 | [Inter-Service Communication](#7-inter-service-communication) | OpenFeign (sync) + RabbitMQ (async) |
| 8 | [Authentication & Authorization](#8-authentication--authorization---jwt) | JWT flow, Gateway Trust Pattern |
| 9 | [Resilience & Fault Tolerance](#9-resilience--fault-tolerance---resilience4j) | Circuit Breaker, Retry, Timeout |
| 10 | [Event-Driven Architecture](#10-event-driven-architecture---rabbitmq) | RabbitMQ messaging deep dive |
| 11 | [Distributed Tracing](#11-distributed-tracing---zipkin) | Zipkin + Micrometer Brave |
| 12 | [Rate Limiting](#12-rate-limiting---redis) | Redis Token Bucket algorithm |
| 13 | [Monitoring & Observability](#13-monitoring--observability) | Prometheus + Grafana |
| 14 | [Docker & Containerization](#14-docker--containerization) | Docker Compose, 17 containers |
| 15 | [Kubernetes Deployment](#15-kubernetes-deployment) | K8s manifests, probes, services |
| 16 | [CI/CD Pipeline](#16-cicd-pipeline---github-actions) | GitHub Actions 3-stage pipeline |
| 17 | [Database Design](#17-database-design) | Database-per-service pattern |
| 18 | [Design Patterns Used](#18-design-patterns-used) | 16 design patterns identified |
| 19 | [Project Metrics](#19-project-metrics) | Numbers and specifics |
| 20 | [Challenges & Solutions](#20-challenges-faced--how-i-solved-them) | Problem-solving demonstrations |
| 21 | [Future Improvements](#21-what-would-you-improve) | Shows architectural awareness |
| 22 | [Quick-Fire Q&A](#22-quick-fire-qa) | Common follow-up questions |
| 23 | [Tech Stack Summary](#23-tech-stack-summary) | Quick reference card |
| 24 | [Resume Bullet Points](#24-resume-bullet-points) | Ready-to-copy descriptions |

---

## 1. Project Introduction

> **How to start describing the project in an interview:**

*"I built a production-grade, microservices-based On-Demand Car Wash System using Java 21, Spring Boot 3.x, and the Spring Cloud ecosystem. The system consists of 10+ independently deployable services communicating via both synchronous REST (OpenFeign) and asynchronous event-driven messaging (RabbitMQ).*

*The platform allows customers to register, add vehicles, place car wash orders, make payments, receive real-time notifications, download GST-compliant PDF invoices, and leave ratings. Admins can manage service plans and monitor users.*

*I implemented the full DevOps pipeline including Docker containerization, Kubernetes orchestration, CI/CD with GitHub Actions, and observability with Prometheus, Grafana, and Zipkin distributed tracing."*

---

## 2. Architecture Overview

### System Architecture Diagram

```
                        ┌────────────────────────────────────────────────────────────┐
                        │                     INFRASTRUCTURE                          │
                        │                                                             │
                        │   ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐  │
                        │   │  Eureka  │  │  Config  │  │ RabbitMQ │  │  Redis   │  │
                        │   │  Server  │  │  Server  │  │  (AMQP)  │  │  (Cache) │  │
                        │   │  :8761   │  │  :8888   │  │  :5672   │  │  :6379   │  │
                        │   └──────────┘  └──────────┘  └──────────┘  └──────────┘  │
                        │                                                             │
                        │   ┌──────────┐  ┌──────────────────────────────────┐       │
                        │   │  Zipkin  │  │  Prometheus (9090) + Grafana    │       │
                        │   │  :9411   │  │  (3000) - Monitoring Stack      │       │
                        │   └──────────┘  └──────────────────────────────────┘       │
                        └────────────────────────────┬───────────────────────────────┘
                                                     │
┌──────────┐       ┌─────────────────────────────────▼──────────────────────────────┐
│          │ HTTP  │                     API GATEWAY (8080)                          │
│  CLIENT  │──────►│  JWT Filter → Rate Limiter (Redis) → Route (Eureka) → LB      │
│          │       └──┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬────────────┘
└──────────┘          │      │      │      │      │      │      │      │
                      ▼      ▼      ▼      ▼      ▼      ▼      ▼      ▼
                  ┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐
                  │ USER ││ORDER ││ PAY  ││ CAR  ││ADMIN ││RATING││ INV  ││NOTIF │
                  │ 8081 ││ 8083 ││ 8084 ││ 8082 ││ 8088 ││ 8086 ││ 8087 ││ 8089 │
                  └──────┘└──┬───┘└──────┘└──────┘└──────┘└──────┘└──────┘└──┬───┘
                             │                                                │
                             │          RabbitMQ (Async Events)               │
                             └───────────────────────────────────────────────►│
```

### Service Inventory

| Service | Port | Database | Key Responsibility |
|---------|------|----------|--------------------|
| **API Gateway** | 8080 | - | JWT validation, rate limiting, routing |
| **Eureka Server** | 8761 | - | Service registry and discovery |
| **Config Server** | 8888 | - | Centralized Git-backed configuration |
| **USER-SERVICE** | 8081 | user_db | Authentication, JWT generation, user CRUD |
| **ORDER-SERVICE** | 8083 | order_db | Order lifecycle, Feign orchestration, RabbitMQ events |
| **PAYMENT-SERVICE** | 8084 | - | Payment processing (stateless) |
| **CAR-SERVICE** | 8082 | car_db | Vehicle management |
| **ADMIN-SERVICE** | 8088 | admin_db | Service plan management, admin reports |
| **RATING-SERVICE** | 8086 | rating_db | Customer reviews and ratings (1-5) |
| **INVOICE-SERVICE** | 8087 | invoice_db | GST calculation, PDF invoice generation |
| **NOTIFICATION-SERVICE** | 8089 | notif_db | Async event-driven notifications |

> **Key Phrase:** *"Each service follows the Single Responsibility Principle, has its own database (Database-per-Service pattern), and can be independently deployed, scaled, and maintained."*

---

## 3. Why Microservices?

> **Interviewer will definitely ask this. Here's how to answer:**

### 1. Independent Scalability
*"During peak hours, ORDER-SERVICE might need 10 instances while ADMIN-SERVICE needs only 1. With microservices, I can scale each service independently using Kubernetes Horizontal Pod Autoscaler, optimizing infrastructure costs."*

### 2. Fault Isolation
*"If PAYMENT-SERVICE crashes, users can still register, browse, and create orders. I implemented circuit breakers with Resilience4j to prevent cascading failures. The system degrades gracefully instead of failing completely."*

### 3. Independent Deployment
*"Each service has its own CI/CD pipeline. I can deploy a bug fix to ORDER-SERVICE without redeploying all 10 services. This reduces deployment risk and enables faster release cycles."*

### 4. Technology Heterogeneity
*"Although I used Java for all services, the architecture supports polyglot development. NOTIFICATION-SERVICE could be rewritten in Node.js without affecting other services."*

### 5. Team Autonomy
*"In a real organization, different teams can own different services with their own release cycles, reducing coordination overhead."*

---

## 4. API Gateway Pattern

> **Spring Cloud Gateway - Built on WebFlux & Netty (Reactive, Non-Blocking)**

### What It Does

*"I implemented the API Gateway pattern using Spring Cloud Gateway, which is built on Spring WebFlux and Project Reactor for non-blocking, reactive request handling."*

### Cross-Cutting Concerns Handled

| Concern | Implementation | Technical Detail |
|---------|---------------|-----------------|
| **Authentication** | Custom `GlobalFilter` | JWT validation, header enrichment |
| **Rate Limiting** | Redis Token Bucket | `KeyResolver` by email or IP |
| **Routing** | Predicate-based | `Path=/order/**` → `lb://ORDER-SERVICE` |
| **Load Balancing** | Spring Cloud LoadBalancer | Round-robin via Eureka |
| **Header Enrichment** | Request mutation | `X-User-Email`, `X-User-Role` |

### Gateway Trust Pattern

```
EXTERNAL (Untrusted)          │         INTERNAL (Trusted)
                              │
Client ── JWT Token ──► API Gateway ── X-User-Email ──► Services
                              │        X-User-Role
                              │
                              │  Gateway is the ONLY component
                              │  that validates JWT tokens.
                              │  Internal services TRUST the headers.
```

*"JWT is validated ONCE at the gateway. The gateway extracts user identity from the token and forwards it as trusted HTTP headers. Downstream services read these headers without re-validating the token. This eliminates redundant cryptographic operations and improves latency."*

### Reactive Architecture

*"Spring Cloud Gateway uses Netty instead of Tomcat, handling thousands of concurrent connections with a small thread pool. Each request is processed as a reactive Mono pipeline, making it ideal for an I/O-bound gateway workload."*

> **Technical Keywords:** `GlobalFilter`, `ServerWebExchange`, `GatewayFilterChain`, `Mono<Void>`, reactive pipeline, non-blocking I/O, Netty event loop

---

## 5. Service Discovery - Eureka

*"I used Netflix Eureka for client-side service discovery."*

### How It Works

```
1. SERVICE STARTS    → Registers with Eureka (name + IP + port)
2. HEARTBEAT         → Every 30 seconds: "I'm still alive"
3. DISCOVERY         → Service A asks Eureka: "Where is Service B?"
4. LOAD BALANCING    → If multiple instances, round-robin selection
5. EVICTION          → 3 missed heartbeats → service removed
```

### Key Features

| Feature | Description |
|---------|-------------|
| **Self-Preservation** | Prevents mass deregistration during network partitions |
| **Client-Side Caching** | Services cache registry locally, reducing Eureka load |
| **Heartbeat Mechanism** | 30-second interval health monitoring |
| **Dashboard** | Web UI at `:8761` showing all registered services |

> **Technical Keywords:** Service registry, client-side discovery, heartbeat, lease renewal, self-preservation mode, service eviction

---

## 6. Centralized Configuration - Config Server

*"I implemented centralized configuration management using Spring Cloud Config Server backed by a Git repository."*

### Architecture

```
┌─────────────────┐         ┌──────────────────┐         ┌──────────────┐
│  GitHub Repo     │ clone   │   CONFIG SERVER   │  fetch  │   SERVICES   │
│                  │◄────────│     (8888)        │────────►│              │
│ USER-SERVICE.    │         │                   │         │ user-service │
│  properties      │         │ Serves configs    │         │ order-service│
│ ORDER-SERVICE.   │         │ via REST API      │         │ car-service  │
│  properties      │         │                   │         │ ...          │
│ application.     │         └─────────┬────────┘         └──────────────┘
│  properties      │                   │
└─────────────────┘          Spring Cloud Bus
                             (RabbitMQ broadcast)
```

### Dynamic Configuration Refresh (Zero Downtime)

*"I integrated Spring Cloud Bus with RabbitMQ for dynamic configuration refresh. When I update a property in Git and trigger `/actuator/busrefresh`, the Config Server broadcasts a refresh event via RabbitMQ to all services. Beans annotated with `@RefreshScope` are re-initialized with the new values - zero downtime configuration updates."*

> **Technical Keywords:** Externalized configuration, Git-backed config store, Spring Cloud Bus, `@RefreshScope`, environment profiles

---

## 7. Inter-Service Communication

### Two Patterns Used

| Pattern | Technology | When Used | Example |
|---------|-----------|-----------|---------|
| **Synchronous** | OpenFeign | Need immediate response | ORDER → USER (get user ID) |
| **Asynchronous** | RabbitMQ | Fire-and-forget | ORDER → NOTIFICATION (send email) |

### OpenFeign - Declarative REST Client

```java
// Instead of manually constructing HTTP requests:
@FeignClient(name = "USER-SERVICE", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/user/email")
    User getUserByEmail(@RequestParam("email") String email);
}

// Usage: User user = userClient.getUserByEmail("sarthak@gmail.com");
// Feign resolves USER-SERVICE via Eureka, load-balances, and forwards headers
```

### Feign Header Propagation

*"I implemented a Feign `RequestInterceptor` that propagates `X-User-Email` and `X-User-Role` headers from the original request context to inter-service calls, maintaining the authentication chain across the entire call graph."*

> **Technical Keywords:** Declarative HTTP client, `RequestInterceptor`, `RequestContextHolder`, service mesh, client-side load balancing

---

## 8. Authentication & Authorization - JWT

### Complete JWT Flow

```
┌──────────┐      ┌──────────┐      ┌──────────────┐      ┌──────────────┐
│  CLIENT  │      │ GATEWAY  │      │ USER-SERVICE │      │ ORDER-SERVICE│
└────┬─────┘      └────┬─────┘      └──────┬───────┘      └──────┬───────┘
     │                 │                    │                      │
     │ 1. POST /auth/login                 │                      │
     │ {email, password}                   │                      │
     │────────────────►│ 2. Route (public) │                      │
     │                 │──────────────────►│                      │
     │                 │                    │ 3. Validate BCrypt   │
     │                 │                    │ 4. Generate JWT      │
     │                 │ 5. Return token   │    (email + role)    │
     │                 │◄──────────────────│                      │
     │ 6. Store token  │                    │                      │
     │◄────────────────│                    │                      │
     │                 │                    │                      │
     │ 7. POST /order/create               │                      │
     │ Authorization: Bearer <token>       │                      │
     │────────────────►│                    │                      │
     │                 │ 8. Validate JWT    │                      │
     │                 │ 9. Extract email   │                      │
     │                 │ 10. Add headers    │                      │
     │                 │    X-User-Email    │                      │
     │                 │    X-User-Role     │                      │
     │                 │───────────────────────────────────────────►
     │                 │                    │    11. Read headers   │
     │                 │                    │    Trust Gateway      │
     │                 │ 12. Response       │    Process order      │
     │◄────────────────│◄──────────────────────────────────────────│
```

### Security Measures

| Measure | Implementation |
|---------|---------------|
| **Password Hashing** | BCrypt with adaptive cost factor |
| **Token Signing** | HMAC-SHA256 with centralized secret |
| **Token Expiry** | 24-hour expiration |
| **Validation** | Centralized at Gateway (single security perimeter) |
| **Authorization** | Role-based: CUSTOMER, WASHER, ADMIN |
| **Admin Limit** | Only one ADMIN allowed (enforced at registration) |

### Why JWT Over Sessions

*"JWT is stateless - the server doesn't maintain session state. This is critical in microservices because any instance of any service can validate the token independently without shared session storage. It eliminates the sticky session problem and enables horizontal scaling."*

> **Technical Keywords:** Stateless authentication, JWT claims, HMAC-SHA256, BCrypt adaptive hashing, Gateway Trust Pattern, RBAC, bearer token

---

## 9. Resilience & Fault Tolerance - Resilience4j

### The Cascading Failure Problem

```
WITHOUT Circuit Breaker:
USER-SERVICE down → ORDER-SERVICE threads blocked → Thread pool exhausted
→ ORDER-SERVICE rejects ALL requests → ADMIN-SERVICE fails → ENTIRE SYSTEM DOWN

WITH Circuit Breaker:
USER-SERVICE down → Circuit OPENS → Fallback returns default response
→ ORDER-SERVICE continues working → System degrades gracefully
```

### Circuit Breaker State Machine

```
┌──────────┐    Failure threshold    ┌──────────┐
│  CLOSED  │────── exceeded ────────►│   OPEN   │
│ (Normal) │                         │(Tripped) │
│          │                         │          │
│ Requests │                         │ Requests │
│ flow     │                         │ REJECTED │
│ through  │                         │ instantly │
│          │                         │ Fallback │
└──────────┘                         └────┬─────┘
     ▲                                    │
     │          Test calls succeed        │ After 60 seconds
     │◄───────────────────────────────────│
     │                              ┌─────▼──────┐
     │          Test calls fail     │ HALF-OPEN  │
     └──────────────────────────────│ (Testing)  │
              (back to OPEN)        │ 3 test     │
                                    │ requests   │
                                    └────────────┘
```

### How Retry + Circuit Breaker Work Together

```java
@Retry(name = "userService")                                          // Layer 1: Try 3 times
@CircuitBreaker(name = "userService", fallbackMethod = "userFallback") // Layer 2: Then circuit break
public User getUser(String email) {
    return userClient.getUserByEmail(email);
}
```

**Execution Flow:**
```
1st attempt → FAIL → Retry (wait 1s)
2nd attempt → FAIL → Retry (wait 1s)
3rd attempt → FAIL → All retries exhausted
→ Circuit breaker records failure
→ After 50% failure rate → Circuit OPENS
→ Next call: Instantly returns fallback (NO network call)
→ After 60 seconds: HALF-OPEN → tests recovery
→ If USER-SERVICE is back: Circuit CLOSES → normal operation
```

> **Technical Keywords:** Circuit breaker state machine (CLOSED/OPEN/HALF-OPEN), sliding window, failure rate threshold, fallback degradation, retry with exponential backoff, cascading failure prevention

---

## 10. Event-Driven Architecture - RabbitMQ

### Architecture

```
ORDER-SERVICE              RabbitMQ                    NOTIFICATION-SERVICE
┌──────────────┐    ┌───────────────────────┐    ┌──────────────────┐
│              │    │                       │    │                  │
│ OrderService │    │ Exchange:             │    │ @RabbitListener  │
│              │    │   order.exchange      │    │                  │
│ publishEvent ├───►│ Routing Key:         ├───►│ consume(event)   │
│  (fire &     │    │   order.routing      │    │                  │
│   forget)    │    │ Queue:               │    │ - Save to DB     │
│              │    │   order_queue         │    │ - Send email     │
└──────────────┘    └───────────────────────┘    └──────────────────┘
```

### Why Async Here

| Benefit | Explanation |
|---------|-------------|
| **Temporal Decoupling** | Producer and consumer don't need to be online simultaneously |
| **Failure Isolation** | Notification failures never impact order creation |
| **Scalability** | Add more consumers (SMS, push) without modifying producer |
| **Backpressure** | Messages queue naturally without overwhelming consumer |

### Key Distinction

```java
// WRONG (Synchronous - order fails if notification fails):
Order order = repo.save(newOrder);
notificationClient.sendEmail(email, "Order Created");  // If this fails, order fails!

// RIGHT (Asynchronous - our approach):
Order order = repo.save(newOrder);
publisher.sendOrderEvent(new OrderEvent(...));  // Fire and forget. Order always succeeds.
```

> **Technical Keywords:** Event-driven architecture, AMQP protocol, Direct Exchange, routing key, message durability, at-least-once delivery, eventual consistency, `@RabbitListener`, `Jackson2JsonMessageConverter`

---

## 11. Distributed Tracing - Zipkin

*"I implemented distributed tracing using Zipkin with Micrometer Tracing (Brave) for end-to-end request visibility across microservices."*

### How It Works

```
Trace ID: abc-123
├── Span 1: API-GATEWAY        (0ms  - 250ms)  duration: 250ms
│   └── Span 2: ORDER-SERVICE  (10ms - 240ms)  duration: 230ms
│       ├── Span 3: USER-SVC   (20ms -  50ms)  duration:  30ms
│       ├── Span 4: CAR-SVC    (55ms - 100ms)  duration:  45ms
│       └── Span 5: PAYMENT    (105ms- 230ms)  duration: 125ms
```

*"Every incoming request is assigned a unique Trace ID that propagates through all service calls via B3 HTTP headers. Each service generates Spans representing its processing time. This allows me to identify latency bottlenecks, failed calls, and the critical path of any request."*

> **Technical Keywords:** Distributed tracing, Trace ID, Span ID, B3 propagation, Micrometer instrumentation, span reporting, latency analysis, critical path, observability

---

## 12. Rate Limiting - Redis

*"I implemented API rate limiting at the gateway using Redis-backed Token Bucket algorithm."*

### How Token Bucket Works

```
Bucket Capacity: 20 tokens (burstCapacity)
Refill Rate: 10 tokens/second (replenishRate)

Request 1:  Take 1 token → 19 remaining → ALLOWED ✓
Request 2:  Take 1 token → 18 remaining → ALLOWED ✓
...
Request 20: Take 1 token → 0 remaining  → ALLOWED ✓
Request 21: No tokens    →               → REJECTED ✗ (429 Too Many Requests)

After 1 second: 10 tokens replenished → 10 available
Request 22: Take 1 token → 9 remaining  → ALLOWED ✓
```

### Key Resolution Strategy

| User Type | Rate Limit Key | Purpose |
|-----------|---------------|---------|
| Authenticated | Email address | Per-user rate limiting |
| Anonymous | IP address | Per-IP rate limiting |

> **Technical Keywords:** Token Bucket algorithm, Redis atomic operations, distributed rate limiting, `KeyResolver`, replenish rate, burst capacity, HTTP 429

---

## 13. Monitoring & Observability

### Three Pillars of Observability

| Pillar | Tool | Purpose |
|--------|------|---------|
| **Metrics** | Prometheus + Grafana | Quantitative system health (CPU, memory, request rates) |
| **Tracing** | Zipkin | Request flow across services |
| **Logging** | Structured logs + Trace ID | Correlated log analysis |

### Prometheus Scraping

```
Services ──/actuator/prometheus──► Prometheus (every 15s) ──► Grafana Dashboards
```

### Key Metrics Collected

| Metric | Type | Example |
|--------|------|---------|
| `http_server_requests_seconds` | Histogram | p50=10ms, p95=50ms, p99=200ms |
| `jvm_memory_used_bytes` | Gauge | Heap: 128MB |
| `resilience4j_circuitbreaker_state` | Gauge | 0=closed, 1=open |
| `process_cpu_usage` | Gauge | 0.05 (5%) |

> **Technical Keywords:** Time-series database, scrape interval, PromQL, Micrometer registry, Actuator endpoints, histogram buckets, alerting rules

---

## 14. Docker & Containerization

### Docker Compose Stack (17 Containers)

```
┌─────────────── Docker Network: carwash-network ───────────────────┐
│                                                                    │
│  INFRASTRUCTURE:  MySQL | RabbitMQ | Redis | Zipkin | Prometheus  │
│                   Grafana                                          │
│                                                                    │
│  DISCOVERY:       Eureka Server | Config Server                   │
│                                                                    │
│  GATEWAY:         API Gateway (only externally exposed)           │
│                                                                    │
│  SERVICES:        User | Car | Order | Payment | Rating           │
│                   Invoice | Admin | Notification                  │
└────────────────────────────────────────────────────────────────────┘
```

### Key Docker Concepts Used

| Concept | Implementation |
|---------|---------------|
| **Multi-stage builds** | Build with JDK, run with JRE (smaller images) |
| **Health checks** | `depends_on: condition: service_healthy` |
| **Named volumes** | `mysql-data` for persistent database storage |
| **Bridge network** | All containers communicate via service names |
| **Environment overrides** | Docker hostnames instead of localhost |

> **Technical Keywords:** Multi-stage build, container image, Docker Compose, health check, named volumes, bridge network, Docker DNS, image registry

---

## 15. Kubernetes Deployment

### Manifest Structure

| File | Resources | Service Type |
|------|-----------|-------------|
| `namespace.yml` | `carwash` namespace | - |
| `secrets.yml` | MySQL credentials | - |
| `infrastructure.yml` | MySQL, RabbitMQ, Redis, Zipkin | ClusterIP |
| `discovery-config.yml` | Eureka, Config Server | ClusterIP |
| `gateway.yml` | API Gateway | **LoadBalancer** (external) |
| `services.yml` | All 8 business services | ClusterIP (internal) |
| `monitoring.yml` | Prometheus, Grafana | NodePort |

### Production Features

| Feature | Implementation |
|---------|---------------|
| **Health Probes** | Startup, readiness, liveness on every pod |
| **Resource Limits** | CPU/memory requests and limits |
| **Secrets** | K8s Secrets for database credentials |
| **Persistent Storage** | PVC for MySQL data |
| **Dependency Order** | Infrastructure → Discovery → Gateway → Services |

> **Technical Keywords:** Namespace, Deployment, Service (ClusterIP/LoadBalancer/NodePort), Pod, Secret, PersistentVolumeClaim, health probes, resource limits, rolling update

---

## 16. CI/CD Pipeline - GitHub Actions

### 3-Stage Pipeline

```
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│  STAGE 1:        │     │  STAGE 2:        │     │  STAGE 3:        │
│  DETECT CHANGES  │────►│  BUILD & PUSH    │────►│  DEPLOY TO K8S   │
│                  │     │                  │     │                  │
│  paths-filter    │     │  Maven build     │     │  kubectl apply   │
│  Which services  │     │  Docker build    │     │  (main branch    │
│  changed?        │     │  Push to Hub     │     │   only)          │
└──────────────────┘     └──────────────────┘     └──────────────────┘
```

### Smart Build Strategy

*"If only ORDER-SERVICE changed, only ORDER-SERVICE is built and pushed. This reduces CI time from ~15 minutes to ~3 minutes and saves Docker Hub bandwidth."*

> **Technical Keywords:** Continuous Integration, Continuous Delivery, path-based change detection, matrix strategy, Docker BuildX, artifact registry

---

## 17. Database Design

### Database-per-Service Pattern

| Service | Database | Key Tables |
|---------|----------|-----------|
| USER-SERVICE | user_db | `user` (id, name, email, password, phone, role) |
| ORDER-SERVICE | order_db | `orders` (id, userId, carId, serviceType, status, washerId) |
| CAR-SERVICE | car_db | `car` (id, userId, brand, model, color, numberPlate) |
| RATING-SERVICE | rating_db | `reviews` (id, userId, orderId, rating, comment) |
| INVOICE-SERVICE | invoice_db | `invoice` (id, userId, orderId, amount, cgst, sgst, totalAmount) |
| ADMIN-SERVICE | admin_db | `service_plan` (id, name, price, description) |
| NOTIFICATION-SERVICE | notif_db | `notification` (id, email, message, type) |

*"Cross-service data references use logical foreign keys (e.g., `userId` in order_db references user_db) but are NOT enforced via SQL constraints. Data consistency is maintained through application logic and eventual consistency via events."*

> **Technical Keywords:** Database-per-service, logical foreign key, schema isolation, eventual consistency, JPA/Hibernate ORM, Spring Data JPA

---

## 18. Design Patterns Used

| # | Pattern | Where Used |
|---|---------|-----------|
| 1 | **API Gateway** | Spring Cloud Gateway - centralized entry point |
| 2 | **Service Discovery** | Eureka - dynamic service resolution |
| 3 | **Circuit Breaker** | Resilience4j - fault tolerance |
| 4 | **Retry** | Resilience4j - transient failure handling |
| 5 | **Event-Driven** | RabbitMQ - async messaging |
| 6 | **Database-per-Service** | Separate MySQL databases |
| 7 | **Externalized Configuration** | Config Server + Git |
| 8 | **Gateway Trust** | Centralized auth, trusted headers |
| 9 | **DTO Pattern** | Separate API contract from entities |
| 10 | **Repository Pattern** | Spring Data JPA abstraction |
| 11 | **Builder Pattern** | JWT token construction (Jwts.builder()) |
| 12 | **Proxy Pattern** | Feign client generation |
| 13 | **Observer Pattern** | RabbitMQ publish/subscribe |
| 14 | **Factory Pattern** | @Bean methods in @Configuration |
| 15 | **Singleton Pattern** | Spring beans (default scope) |
| 16 | **Template Method** | JpaRepository query generation |

---

## 19. Project Metrics

> **Interviewers love specifics. Memorize these numbers:**

| Metric | Value |
|--------|-------|
| Total microservices | 11 (8 business + 3 infrastructure) |
| Docker containers | 17 in Compose stack |
| Source files | 224 across all services |
| K8s manifest files | 7 |
| CI/CD pipeline stages | 3 |
| Prometheus scrape targets | 9 |
| User roles | 3 (CUSTOMER, WASHER, ADMIN) |
| Order states | 3 (CREATED, ASSIGNED, COMPLETED) |
| JWT expiration | 24 hours |
| Password hashing | BCrypt |
| GST calculation | 18% (9% CGST + 9% SGST) |
| Platform fee | 15% |
| README documentation | 5,689 lines |

---

## 20. Challenges Faced & How I Solved Them

### Challenge 1: 403 Forbidden Errors Across Services

> **Root Cause:** Spring Security's default configuration blocking internal requests

**Solution:** *"I implemented the Gateway Trust Pattern - disabled Spring Security filters in downstream services and relied on Gateway-validated headers. Services trust internal traffic unconditionally."*

### Challenge 2: Feign Clients Losing Authentication Headers

> **Root Cause:** Feign creates new HTTP requests without the original headers

**Solution:** *"I created a FeignConfig `RequestInterceptor` that reads the current request from Spring's `RequestContextHolder` and copies `X-User-Email` and `X-User-Role` headers to the outgoing Feign request."*

### Challenge 3: ReadOnlyHttpHeaders Error in Gateway

> **Root Cause:** Spring Cloud Gateway's request headers are immutable

**Solution:** *"Instead of modifying headers directly, I used `exchange.getRequest().mutate()` to create a new request with additional headers."*

### Challenge 4: RabbitMQ Message Deserialization Failures

> **Root Cause:** Producer and consumer had different Jackson converters

**Solution:** *"I standardized on `Jackson2JsonMessageConverter` on both sides and ensured the `OrderEvent` DTO had matching field names and a no-args constructor."*

### Challenge 5: Services Starting Before Dependencies Were Ready

> **Root Cause:** Docker Compose starts containers in parallel by default

**Solution:** *"I used `depends_on` with `condition: service_healthy` and configured health checks with `start_period` to allow Spring Boot applications sufficient startup time."*

### Challenge 6: Circuit Breaker Not Triggering Correctly

> **Root Cause:** Retry was running AFTER circuit breaker instead of BEFORE

**Solution:** *"I reordered annotations so `@Retry` wraps `@CircuitBreaker` - retries happen first, then failures are recorded in the circuit breaker."*

---

## 21. What Would You Improve?

> **They always ask this. Shows architectural maturity:**

| Priority | Improvement | Why |
|----------|------------|-----|
| High | OAuth2 with Keycloak | Enterprise-grade identity management |
| High | Saga Pattern | Distributed transactions across services |
| High | Redis Caching | Reduce database load for hot data |
| Medium | API Versioning (`/v1/`, `/v2/`) | Backward compatibility |
| Medium | Swagger/OpenAPI | Auto-generated API documentation |
| Medium | Flyway Migrations | Schema version control |
| Medium | Integration Tests | Testcontainers for real DB/MQ testing |
| Low | gRPC | Lower latency inter-service communication |
| Low | Event Sourcing | Audit trail and temporal queries |
| Low | Service Mesh (Istio) | mTLS, advanced traffic management |
| Low | K8s HPA | Auto-scaling based on Prometheus metrics |
| Low | WebSocket Notifications | Real-time push to clients |

---

## 22. Quick-Fire Q&A

> **Common follow-up questions with ready answers:**

### Q: "Why Spring Boot?"
*"Convention over configuration, auto-configuration, embedded server, production-ready features, and the largest Java ecosystem."*

### Q: "Why not Kafka instead of RabbitMQ?"
*"RabbitMQ is ideal for task queues with complex routing. Kafka is better for high-throughput event streaming. For our notification use case, RabbitMQ's simplicity and AMQP routing was the right fit."*

### Q: "Why Eureka and not Consul or K8s DNS?"
*"Eureka integrates natively with Spring Cloud. Using Eureka makes the code portable - same code works locally and in Kubernetes."*

### Q: "How do you handle distributed transactions?"
*"I use eventual consistency via RabbitMQ events. For critical flows, I would implement the Saga pattern with compensating transactions."*

### Q: "What if Config Server goes down?"
*"Services cache their last-known configuration. The `optional:` prefix in `spring.config.import` allows services to start with local defaults."*

### Q: "What if Eureka goes down?"
*"Services cache the registry locally. Existing communication continues. New services won't register, but running services are unaffected."*

### Q: "How do you secure inter-service communication?"
*"Currently through network isolation (Docker network / K8s ClusterIP). For production, I would add mTLS via Istio service mesh."*

### Q: "How do you handle database schema changes?"
*"JPA's `ddl-auto=update` for development. For production, I would use Flyway for versioned, repeatable database migrations."*

### Q: "What testing strategy?"
*"Unit tests for service layer. For integration testing, Testcontainers to spin up real MySQL and RabbitMQ in Docker during tests."*

---

## 23. Tech Stack Summary

### Quick Reference Card

| Category | Technologies |
|----------|-------------|
| **Language & Framework** | Java 21, Spring Boot 3.3.5, Spring Cloud 2023.0.3 |
| **Microservices Infra** | Spring Cloud Gateway, Netflix Eureka, Spring Cloud Config, Spring Cloud Bus |
| **Communication** | OpenFeign (sync REST), RabbitMQ (async AMQP) |
| **Security** | JWT (jjwt 0.11.5), BCrypt, Spring Security |
| **Resilience** | Resilience4j (Circuit Breaker, Retry, Timeout) |
| **Data** | MySQL 8.0, Spring Data JPA, Hibernate ORM |
| **Observability** | Zipkin, Prometheus, Grafana, Micrometer, Spring Boot Actuator |
| **DevOps** | Docker, Docker Compose, Kubernetes, GitHub Actions |
| **Tools** | Maven, Lombok, OpenPDF, Redis |

---

## 24. Resume Bullet Points

### One-Liner (For quick intro)

> Built a microservices-based On-Demand Car Wash System with 10+ services using Spring Boot, Spring Cloud, RabbitMQ, Docker, and Kubernetes.

### Medium Description (2-3 lines)

> Designed and developed a production-grade microservices backend for an On-Demand Car Wash platform using Java 21 and Spring Cloud ecosystem. Implemented API Gateway with JWT auth, event-driven messaging with RabbitMQ, circuit breakers with Resilience4j, and full DevOps pipeline with Docker, Kubernetes, and GitHub Actions CI/CD.

### Detailed Resume Bullets

- Architected **10+ microservices** with Spring Boot 3.x and Spring Cloud following **Domain-Driven Design** and **12-Factor App** methodology
- Implemented **API Gateway** with JWT authentication, **Redis rate limiting**, and **Eureka-based service discovery** with client-side load balancing
- Built **event-driven notification system** using RabbitMQ AMQP messaging with guaranteed delivery and consumer acknowledgment
- Integrated **Resilience4j circuit breakers**, retry, and timeout patterns preventing **cascading failures** with graceful degradation
- Containerized all services with **Docker**, orchestrated with **Kubernetes**, and automated CI/CD with **GitHub Actions** (smart change-detection builds)
- Implemented full **observability stack**: Zipkin distributed tracing, Prometheus metrics collection, and Grafana dashboards

---

## Repository

**Source Code:** [github.com/sarthakpawar0912/On-Demand-Car-Wash-Backend](https://github.com/sarthakpawar0912/On-Demand-Car-Wash-Backend)

---

> *Built with Java 21, Spring Boot 3.x, and Spring Cloud 2023.x by [Sarthak Pawar](https://github.com/sarthakpawar0912)*
