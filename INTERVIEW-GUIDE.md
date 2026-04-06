# On-Demand Car Wash System - Complete Interview Preparation Guide

> A comprehensive, in-depth interview preparation guide for explaining the **On-Demand Car Wash System** - a production-grade microservices backend built with **Java 21**, **Spring Boot 3.x**, and **Spring Cloud 2023.x**. This guide is divided into two parts: **Part A** covers detailed project explanation with technical depth, and **Part B** contains **50 real-life interview questions** with project-specific answers.

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.3-brightgreen?style=for-the-badge&logo=spring)](https://spring.io/projects/spring-cloud)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![Kubernetes](https://img.shields.io/badge/Kubernetes-Deployment-blue?style=for-the-badge&logo=kubernetes)](https://kubernetes.io/)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Event%20Driven-orange?style=for-the-badge&logo=rabbitmq)](https://www.rabbitmq.com/)
[![Redis](https://img.shields.io/badge/Redis-Rate%20Limiting-red?style=for-the-badge&logo=redis)](https://redis.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)](https://www.mysql.com/)

---

# ============================================================
# PART A: DETAILED PROJECT EXPLANATION
# ============================================================

---

## Table of Contents - Part A

| # | Section | What It Covers |
|---|---------|---------------|
| 1 | [Project Introduction](#1-project-introduction---how-to-open) | Multiple ways to introduce the project |
| 2 | [System Architecture Deep Dive](#2-system-architecture---deep-dive) | Complete architecture with diagrams |
| 3 | [Why Microservices Over Monolith](#3-why-microservices-over-monolith) | Detailed justification with real examples |
| 4 | [API Gateway - Complete Explanation](#4-api-gateway---complete-explanation) | Gateway internals, filters, reactive model |
| 5 | [Service Discovery - Eureka](#5-service-discovery---eureka-deep-dive) | Registration, heartbeat, self-preservation |
| 6 | [Centralized Configuration](#6-centralized-configuration---config-server) | Git-backed config, dynamic refresh, Cloud Bus |
| 7 | [Inter-Service Communication](#7-inter-service-communication---complete-guide) | Feign, RabbitMQ, header propagation |
| 8 | [JWT Authentication Flow](#8-jwt-authentication---complete-flow) | Token lifecycle, Gateway Trust Pattern |
| 9 | [Spring Security in Microservices](#9-spring-security-in-microservices) | Why 403 happens, how we solved it |
| 10 | [Resilience & Fault Tolerance](#10-resilience--fault-tolerance) | Circuit Breaker, Retry, Fallback deep dive |
| 11 | [Event-Driven Architecture](#11-event-driven-architecture---rabbitmq) | RabbitMQ internals, message lifecycle |
| 12 | [Distributed Tracing](#12-distributed-tracing---zipkin) | Trace propagation, span hierarchy |
| 13 | [Rate Limiting](#13-rate-limiting---redis) | Token Bucket, KeyResolver, Redis internals |
| 14 | [Monitoring & Observability](#14-monitoring--observability) | Three pillars, Prometheus, Grafana |
| 15 | [Docker & Containerization](#15-docker--containerization) | Dockerfile, Compose, networking |
| 16 | [Kubernetes Deployment](#16-kubernetes-deployment) | Manifests, probes, service types |
| 17 | [CI/CD Pipeline](#17-cicd-pipeline) | 3-stage GitHub Actions |
| 18 | [Database Design](#18-database-design) | Database-per-service, schema details |
| 19 | [Design Patterns](#19-design-patterns-used) | 16 patterns with explanations |
| 20 | [Project Metrics](#20-project-metrics) | Numbers interviewers love |
| 21 | [Challenges & Solutions](#21-challenges-faced--solutions) | 8 real problems solved |
| 22 | [Future Improvements](#22-future-improvements) | What you'd add next |
| 23 | [Complete Request Flow](#23-complete-end-to-end-request-flow) | Step-by-step request walkthrough |
| 24 | [Tech Stack Summary](#24-tech-stack-summary) | Quick reference |
| 25 | [Resume Bullet Points](#25-resume-bullet-points) | Ready-to-copy descriptions |

---

## 1. Project Introduction - How to Open

### Opening Statement (Memorize This)

*"I designed and developed a production-grade, microservices-based On-Demand Car Wash System using Java 21, Spring Boot 3.x, and the Spring Cloud ecosystem. The system consists of 11 independently deployable services including 8 business services, an API Gateway, a Eureka Service Registry, and a Config Server - all communicating via synchronous REST calls using OpenFeign and asynchronous event-driven messaging using RabbitMQ.*

*The platform enables customers to register, add vehicles, place car wash orders, make payments, receive real-time notifications via RabbitMQ events, download GST-compliant PDF invoices, and submit ratings. Administrators can manage service plans and monitor the platform.*

*I implemented the complete DevOps lifecycle - Docker containerization with a 17-container Docker Compose stack, Kubernetes manifests for production orchestration, a 3-stage CI/CD pipeline with GitHub Actions featuring smart change-detection builds, and a full observability stack with Zipkin distributed tracing, Prometheus metrics collection, and Grafana dashboards."*

### Shorter Version (30 seconds)

*"I built a microservices-based Car Wash booking platform with 11 services using Spring Boot and Spring Cloud. It has an API Gateway with JWT authentication and Redis rate limiting, event-driven notifications via RabbitMQ, Resilience4j circuit breakers for fault tolerance, and is fully containerized with Docker Compose and Kubernetes. I also set up CI/CD with GitHub Actions and monitoring with Prometheus and Grafana."*

### What Makes This Project Stand Out

Tell the interviewer these differentiators:

1. **Not just CRUD** - It has real distributed systems challenges: service discovery, distributed tracing, circuit breakers, event-driven messaging, rate limiting
2. **Production-ready** - Docker Compose, Kubernetes, CI/CD, health checks, monitoring - not just a local development project
3. **Full observability** - Three pillars: metrics (Prometheus), tracing (Zipkin), logging
4. **Resilient** - Handles service failures gracefully with circuit breakers and fallbacks
5. **Secure** - JWT authentication with centralized validation, BCrypt password hashing, role-based access control

---

## 2. System Architecture - Deep Dive

### High-Level Architecture Diagram

```
                        ┌────────────────────────────────────────────────────────────┐
                        │                     INFRASTRUCTURE LAYER                    │
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
│  CLIENT  │──────►│                                                                │
│ (Browser/│       │  1. JWT Authentication (GlobalFilter)                          │
│  Mobile/ │       │  2. Rate Limiting (Redis Token Bucket)                         │
│  Postman)│       │  3. Route Resolution (Eureka Service Discovery)                │
│          │       │  4. Load Balancing (Spring Cloud LoadBalancer)                  │
│          │       │  5. Header Enrichment (X-User-Email, X-User-Role)              │
│          │       └──┬──────┬──────┬──────┬──────┬──────┬──────┬──────┬────────────┘
└──────────┘          │      │      │      │      │      │      │      │
                      ▼      ▼      ▼      ▼      ▼      ▼      ▼      ▼
                  ┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐
                  │ USER ││ORDER ││ PAY  ││ CAR  ││ADMIN ││RATING││ INV  ││NOTIF │
                  │ 8081 ││ 8083 ││ 8084 ││ 8082 ││ 8088 ││ 8086 ││ 8087 ││ 8089 │
                  │      ││      ││      ││      ││      ││      ││      ││      │
                  │MySQL ││MySQL ││  No  ││MySQL ││MySQL ││MySQL ││MySQL ││MySQL │
                  │user_ ││order_││  DB  ││car_  ││admin_││rate_ ││inv_  ││notif_│
                  │ db   ││ db   ││      ││ db   ││ db   ││ db   ││ db   ││ db   │
                  └──────┘└──┬───┘└──────┘└──────┘└──────┘└──────┘└──────┘└──┬───┘
                             │                                                │
                             │    ┌──────────────────────────┐                │
                             └───►│  RabbitMQ (Async Events) │───────────────►│
                                  │  order.exchange          │
                                  │  order_queue             │
                                  └──────────────────────────┘
```

### Service Inventory - Complete Details

| Service | Port | Database | Feign Calls To | Called By | Key Tech |
|---------|------|----------|---------------|-----------|----------|
| **API Gateway** | 8080 | - | - | Client | WebFlux, Netty, Redis |
| **Eureka Server** | 8761 | - | - | All services | Netflix Eureka |
| **Config Server** | 8888 | - | - | All services | Git, Cloud Bus |
| **USER-SERVICE** | 8081 | user_db | - | Order, Admin, Rating, Invoice | Spring Security, BCrypt, JWT |
| **ORDER-SERVICE** | 8083 | order_db | User, Car, Payment | Client (via Gateway) | Feign, RabbitMQ, Resilience4j |
| **PAYMENT-SERVICE** | 8084 | - | - | Order | Stateless |
| **CAR-SERVICE** | 8082 | car_db | - | Order | JPA CRUD |
| **ADMIN-SERVICE** | 8088 | admin_db | User | Client (via Gateway) | Role-based auth, Resilience4j |
| **RATING-SERVICE** | 8086 | rating_db | User | Client (via Gateway) | Resilience4j |
| **INVOICE-SERVICE** | 8087 | invoice_db | User | Client (via Gateway) | OpenPDF, GST calc, Resilience4j |
| **NOTIFICATION-SERVICE** | 8089 | notif_db | - | RabbitMQ events | @RabbitListener, async consumer |

### Communication Map

```
ORDER-SERVICE ──── Feign (sync) ────► USER-SERVICE     (Get user ID from email)
ORDER-SERVICE ──── Feign (sync) ────► CAR-SERVICE      (Validate car ownership)
ORDER-SERVICE ──── Feign (sync) ────► PAYMENT-SERVICE  (Process payment)
ORDER-SERVICE ──── RabbitMQ (async) ► NOTIFICATION-SVC (Order created/completed events)

ADMIN-SERVICE ──── Feign (sync) ────► USER-SERVICE     (List all users)
RATING-SERVICE ─── Feign (sync) ────► USER-SERVICE     (Get user ID for review)
INVOICE-SERVICE ── Feign (sync) ────► USER-SERVICE     (Get user details for PDF)
```

---

## 3. Why Microservices Over Monolith

### Detailed Comparison Table

| Aspect | Monolithic Approach | Our Microservices Approach |
|--------|-------------------|--------------------------|
| **Codebase** | Single project, single .jar | 11 separate projects, 11 .jar files |
| **Deployment** | Deploy everything for any change | Deploy only the changed service |
| **Scaling** | Scale entire app (wasteful) | Scale only ORDER-SERVICE during peak |
| **Database** | Single shared database | 7 separate MySQL databases |
| **Failure Impact** | Payment bug crashes everything | Payment bug only affects payments |
| **Team Structure** | All developers on same code | Teams own individual services |
| **Technology** | Stuck with one stack | Each service can use different tech |
| **Build Time** | 15+ minutes for full build | 2-3 minutes for one service |
| **Testing** | Full regression for any change | Test only the changed service |
| **Startup Time** | 60+ seconds for monolith | Each service starts independently |

### Real Scenario to Explain

*"Imagine our Car Wash platform has a flash sale - 10x more orders than normal. In a monolith, I'd have to scale the ENTIRE application including user registration, admin panel, and invoicing - even though only the order module needs more capacity. That wastes 80% of the infrastructure cost.*

*With microservices, I scale only ORDER-SERVICE from 1 to 10 instances using Kubernetes HPA. USER-SERVICE stays at 2 instances, ADMIN-SERVICE stays at 1. I'm paying only for what I need.*

*Now imagine the PAYMENT-SERVICE has a bug and crashes. In a monolith, the entire platform is down - nobody can even register or browse. With microservices, payments fail but customers can still register, add cars, create orders (pending payment), and leave reviews. I have circuit breakers that return a graceful fallback message instead of a 500 error."*

---

## 4. API Gateway - Complete Explanation

### Why a Gateway is Essential

*"Without a gateway, our mobile app would need to know the addresses of 8 different services on 8 different ports. If any service moves or scales, the app breaks. The gateway provides a single stable URL (localhost:8080) and handles routing, authentication, rate limiting, and load balancing internally."*

### Request Processing Pipeline

```
Client Request Arrives (POST /order/create)
        │
        ▼
┌──────────────────────────────────────────┐
│  1. NETTY EVENT LOOP receives request    │  (Non-blocking I/O)
│     Unlike Tomcat, Netty uses a small    │
│     fixed thread pool (~4 threads) to    │
│     handle thousands of concurrent       │
│     connections                           │
└──────────────────┬───────────────────────┘
                   │
                   ▼
┌──────────────────────────────────────────┐
│  2. GLOBAL FILTER: JwtAuthenticationFilter│
│                                           │
│  a. Check if path is public:             │
│     /auth/** → YES → skip JWT            │
│     /order/** → NO → validate JWT        │
│                                           │
│  b. Extract "Bearer eyJ..." from header  │
│                                           │
│  c. Validate token:                      │
│     - Parse with HMAC-SHA256 key         │
│     - Verify signature (not tampered)    │
│     - Check expiration (not expired)     │
│                                           │
│  d. Extract claims:                      │
│     - email from "sub" claim             │
│     - role from custom "role" claim      │
│                                           │
│  e. Mutate request (add headers):        │
│     X-User-Email: sarthak@gmail.com     │
│     X-User-Role: CUSTOMER                │
│                                           │
│  f. If invalid → 401 UNAUTHORIZED        │
└──────────────────┬───────────────────────┘
                   │
                   ▼
┌──────────────────────────────────────────┐
│  3. RATE LIMITER FILTER                  │
│                                           │
│  a. KeyResolver extracts key:            │
│     - Authenticated: email               │
│     - Anonymous: IP address              │
│                                           │
│  b. Check Redis:                         │
│     GET rate_limit:sarthak@gmail.com     │
│     Tokens remaining: 15                 │
│     → Decrement to 14                    │
│     → ALLOW request                      │
│                                           │
│  c. If tokens = 0:                       │
│     → REJECT with 429 Too Many Requests  │
└──────────────────┬───────────────────────┘
                   │
                   ▼
┌──────────────────────────────────────────┐
│  4. ROUTE MATCHING                       │
│                                           │
│  Path=/order/** matches route:           │
│  uri=lb://ORDER-SERVICE                  │
│                                           │
│  "lb://" means load-balanced via Eureka  │
└──────────────────┬───────────────────────┘
                   │
                   ▼
┌──────────────────────────────────────────┐
│  5. SERVICE RESOLUTION                   │
│                                           │
│  Ask Eureka: "Where is ORDER-SERVICE?"   │
│  Eureka returns: [192.168.1.6:8083,      │
│                    192.168.1.7:8083]      │
│                                           │
│  LoadBalancer picks: 192.168.1.6:8083    │
│  (round-robin algorithm)                 │
└──────────────────┬───────────────────────┘
                   │
                   ▼
┌──────────────────────────────────────────┐
│  6. FORWARD REQUEST                      │
│                                           │
│  POST http://192.168.1.6:8083/order/create│
│  Headers:                                │
│    Authorization: Bearer eyJ...          │
│    X-User-Email: sarthak@gmail.com      │
│    X-User-Role: CUSTOMER                 │
│    Content-Type: application/json        │
│  Body: { "carId": 1, "serviceType": "PREMIUM" }│
└──────────────────────────────────────────┘
```

### Why Reactive (Netty) Instead of Servlet (Tomcat)

| Aspect | Tomcat (Blocking) | Netty (Non-Blocking) |
|--------|-------------------|---------------------|
| **Thread Model** | 1 thread per request (200 default) | Event loop (4-8 threads for thousands of requests) |
| **Memory** | ~1MB per thread × 200 = 200MB | Fixed small pool = ~10MB |
| **Concurrency** | 200 concurrent requests max | 10,000+ concurrent connections |
| **Waiting** | Thread blocks while calling downstream | Thread released while waiting |
| **Use Case** | Business logic services | I/O-heavy gateway/proxy |

*"The Gateway is purely I/O-bound - it receives requests, validates JWT, checks Redis, and forwards to services. It never does CPU-intensive work. The reactive model lets it handle thousands of concurrent connections with minimal resources."*

---

## 5. Service Discovery - Eureka Deep Dive

### Complete Lifecycle

```
PHASE 1: REGISTRATION (Service starts)
──────────────────────────────────────
ORDER-SERVICE starts on 192.168.1.6:8083
  └── Reads: eureka.client.service-url.defaultZone=http://eureka:8761/eureka
  └── Sends HTTP POST to Eureka: "Register ORDER-SERVICE at 192.168.1.6:8083"
  └── Eureka stores: { ORDER-SERVICE: [{host: 192.168.1.6, port: 8083, status: UP}] }

PHASE 2: HEARTBEAT (Every 30 seconds)
──────────────────────────────────────
ORDER-SERVICE sends HTTP PUT to Eureka: "I'm still alive"
  └── Eureka resets eviction timer
  └── If 3 heartbeats missed (90 seconds) → Eureka removes the instance

PHASE 3: DISCOVERY (When calling another service)
──────────────────────────────────────
ORDER-SERVICE needs USER-SERVICE:
  └── Checks local cache first (refreshed every 30s)
  └── If not cached: GET http://eureka:8761/eureka/apps/USER-SERVICE
  └── Eureka returns: [{host: 192.168.1.5, port: 8081, status: UP}]
  └── LoadBalancer picks an instance

PHASE 4: SELF-PRESERVATION (Network partition)
──────────────────────────────────────
If 85%+ of instances miss heartbeats simultaneously:
  └── Eureka thinks: "Network issue, not service failures"
  └── Activates self-preservation mode
  └── Stops evicting instances (keeps stale entries)
  └── Prevents cascading deregistration

PHASE 5: SHUTDOWN (Service stops gracefully)
──────────────────────────────────────
ORDER-SERVICE sends HTTP DELETE to Eureka: "Deregister me"
  └── Eureka removes instance immediately
  └── Other services discover the change on next cache refresh
```

---

## 6. Centralized Configuration - Config Server

### How Configuration Flows

```
STEP 1: Config Server starts
  └── Clones Git repo: github.com/sarthakpawar0912/Car-Wash-central-config-repo-
  └── Caches all property files locally

STEP 2: USER-SERVICE starts
  └── Reads bootstrap.properties: spring.cloud.config.uri=http://config-server:8888
  └── Makes HTTP GET: http://config-server:8888/USER-SERVICE/default
  └── Config Server returns merged properties:
      - application.properties (shared: jwt.secret, eureka URL, zipkin URL)
      - USER-SERVICE.properties (specific: server.port=8081, datasource URL)
  └── USER-SERVICE applies these as its configuration

STEP 3: Runtime config change (zero downtime)
  └── Developer changes jwt.secret in Git repo
  └── Calls POST http://config-server:8888/actuator/busrefresh
  └── Config Server publishes REFRESH event to RabbitMQ
  └── Spring Cloud Bus delivers event to ALL services
  └── Each service re-initializes @RefreshScope beans
  └── New jwt.secret is active everywhere. No restart!
```

### What's in the Git Config Repo

```
Car-Wash-central-config-repo/
├── application.properties          ← Shared by ALL services
│   jwt.secret=mysupersecretkeymysupersecretkey12345
│   eureka.client.service-url.defaultZone=http://localhost:8761/eureka
│   management.zipkin.tracing.endpoint=http://localhost:9411/api/v2/spans
│   management.tracing.sampling.probability=1.0
│
├── USER-SERVICE.properties         ← Only for User Service
│   server.port=8081
│   spring.datasource.url=jdbc:mysql://localhost:3306/user_db
│
├── ORDER-SERVICE.properties        ← Only for Order Service
│   server.port=8083
│   spring.datasource.url=jdbc:mysql://localhost:3306/order_db
│   resilience4j.circuitbreaker.instances.userService.*=...
│
├── API-GATEWAY.properties          ← Gateway routes
│   spring.cloud.gateway.routes[0].id=auth-service
│   spring.cloud.gateway.routes[0].uri=lb://USER-SERVICE
│   spring.cloud.gateway.routes[0].predicates[0]=Path=/auth/**
│   ...
└── ... (one file per service)
```

---

## 7. Inter-Service Communication - Complete Guide

### Synchronous Communication (OpenFeign)

**When to use:** The caller NEEDS the response to continue.

```
ORDER-SERVICE creating an order:
  1. "I need the user's ID" → Feign → USER-SERVICE → Returns { id: 1 }
     (Can't create order without knowing WHO placed it)

  2. "I need to verify the car" → Feign → CAR-SERVICE → Returns [cars]
     (Can't create order without validating the car)

  3. "Process payment" → Feign → PAYMENT-SERVICE → Returns { status: SUCCESS }
     (Can't complete order without payment confirmation)
```

**How Feign works internally:**
```
You write:     userClient.getUserByEmail("sarthak@gmail.com")
                    │
Spring creates:     A dynamic proxy implementation of UserClient interface
                    │
Proxy does:    1. Read @FeignClient(name = "USER-SERVICE")
               2. Ask Eureka: "Where is USER-SERVICE?" → 192.168.1.5:8081
               3. If 3 instances → LoadBalancer picks one (round-robin)
               4. Run FeignConfig RequestInterceptor:
                  - Copy X-User-Email header from current request
                  - Copy X-User-Role header from current request
               5. Construct: GET http://192.168.1.5:8081/user/email?email=sarthak@gmail.com
               6. Send HTTP request
               7. Receive response JSON
               8. Deserialize to User object (Jackson)
               9. Return User to caller
```

### Asynchronous Communication (RabbitMQ)

**When to use:** The caller does NOT need the response.

```
ORDER-SERVICE creating an order:
  Order saved to DB → Publish event → Continue (don't wait)
                            │
                     RabbitMQ stores message
                            │
                     NOTIFICATION-SERVICE picks up (whenever ready)
                            │
                     Saves notification + simulates email
```

**Why async for notifications:**
```
WRONG approach (sync):
  Order order = repo.save(newOrder);
  notificationClient.sendEmail(email, "Order Created");  // ← BLOCKS!
  return order;
  // Problem: If NotificationService is down, order creation FAILS
  // Customer gets error even though order was valid

RIGHT approach (async - what we do):
  Order order = repo.save(newOrder);
  try {
      publisher.sendOrderEvent(event);  // ← Fire and forget
  } catch (Exception e) {
      log.warn("RabbitMQ unavailable");  // Order still succeeds!
  }
  return order;
```

---

## 8. JWT Authentication - Complete Flow

### Token Structure (Decoded)

```
HEADER:
{
  "alg": "HS256",          ← Algorithm: HMAC-SHA256
  "typ": "JWT"             ← Token type
}

PAYLOAD:
{
  "sub": "sarthak@gmail.com",  ← Subject: who this token belongs to
  "role": "CUSTOMER",           ← Custom claim: user's role
  "iat": 1712400000,            ← Issued At: Unix timestamp
  "exp": 1712486400             ← Expiration: 24 hours later
}

SIGNATURE:
HMACSHA256(
  base64(header) + "." + base64(payload),
  "mysupersecretkeymysupersecretkey12345"   ← Secret key from Config Server
)
```

### Why JWT is Tamper-Proof

*"If an attacker intercepts the token and changes `role` from `CUSTOMER` to `ADMIN`, the signature will no longer match because the signature is computed from the payload + secret key. When the Gateway validates the token, `Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)` will throw a `SignatureException` and the request will be rejected with 401."*

### Complete Authentication Sequence

```
1. CLIENT → POST /auth/login { email: "sarthak@gmail.com", password: "1234" }
2. GATEWAY → Path /auth/** is PUBLIC → skip JWT → route to USER-SERVICE
3. USER-SERVICE →
   a. Find user by email in MySQL
   b. Compare BCrypt hash: encoder.matches("1234", "$2a$10$xyz...") → true
   c. Generate JWT: Jwts.builder().setSubject(email).claim("role", "CUSTOMER")
      .setExpiration(24h).signWith(key, HS256).compact()
   d. Return: { token: "Bearer eyJhbG...", email: "sarthak@gmail.com", role: "CUSTOMER" }
4. CLIENT stores token in localStorage/cookie

5. CLIENT → POST /order/create, Authorization: Bearer eyJhbG...
6. GATEWAY →
   a. JwtAuthenticationFilter: /order/** is PROTECTED
   b. Extract token from "Bearer eyJhbG..."
   c. Validate: parse with signing key → signature matches? yes. expired? no.
   d. Extract: email="sarthak@gmail.com", role="CUSTOMER"
   e. Mutate request: add X-User-Email and X-User-Role headers
   f. Route to ORDER-SERVICE via Eureka
7. ORDER-SERVICE →
   a. Read @RequestHeader("X-User-Email") → "sarthak@gmail.com"
   b. Trust the header (Gateway validated it)
   c. Process the order
```

### Gateway Trust Pattern - Why Services Don't Validate JWT

```
EXTERNAL WORLD (Untrusted)    │    INTERNAL NETWORK (Trusted)
                              │
                              │    ┌────────────┐
Client ── JWT Token ──► Gateway ──►│ ORDER-SVC  │ ← Reads X-User-Email header
                         │    │    │ (no JWT    │    Trusts it completely
                         │    │    │  parsing)  │
                    Validates  │    └────────────┘
                    JWT once   │
                              │    ┌────────────┐
                              │    │ CAR-SVC    │ ← Same: reads headers only
                              │    └────────────┘
                              │
                              │    Services are NOT exposed to internet
                              │    Only Gateway is publicly accessible
                              │    Internal traffic is trusted
```

**Benefits:**
1. JWT validated ONCE (not 8 times across services)
2. Services don't need JWT libraries or secret key
3. Simpler code in services - just read a header
4. Consistent user identity format across all services
5. Better performance - no crypto operations in services

---

## 9. Spring Security in Microservices

### The Problem We Faced

*"When I added `spring-boot-starter-security` to USER-SERVICE for BCrypt password hashing, Spring Security automatically secured ALL endpoints. Internal Feign calls from ORDER-SERVICE started getting 403 Forbidden because they didn't have a Spring Security authentication context."*

### Root Cause Analysis

```
1. Spring Security's default: ALL endpoints require authentication
2. Gateway sends X-User-Email header, NOT a Spring Security context
3. Spring Security doesn't recognize X-User-Email as authentication
4. Result: 403 Forbidden for ALL internal Feign calls
```

### Our Solution

```java
@Bean
public SecurityFilterChain security(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())           // No CSRF for REST APIs
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()            // Trust ALL internal traffic
        );
    return http.build();
}
```

*"We keep Spring Security ONLY for the `BCryptPasswordEncoder` bean and the `AuthenticationManager` bean. The actual security filter chain permits all requests because the Gateway has already validated the JWT."*

*"The JwtAuthFilter class in USER-SERVICE has its `@Component` annotation commented out - it's intentionally disabled. It exists as reference code showing how JWT validation would work if services validated tokens themselves."*

---

## 10. Resilience & Fault Tolerance

### The Cascading Failure Scenario

```
WITHOUT Resilience4j:

1. USER-SERVICE is slow (responding in 30 seconds instead of 50ms)
2. ORDER-SERVICE calls USER-SERVICE → thread BLOCKS for 30 seconds
3. More requests arrive → more threads block
4. ORDER-SERVICE's thread pool (200 threads) exhausted in 200 requests
5. ORDER-SERVICE starts returning 503 to ALL requests
6. ADMIN-SERVICE calls ORDER-SERVICE → also blocked
7. RATING-SERVICE calls USER-SERVICE → also blocked
8. ENTIRE SYSTEM IS DOWN because ONE service was slow!

WITH Resilience4j (our implementation):

1. USER-SERVICE is slow
2. ORDER-SERVICE calls USER-SERVICE → timeout after 3 seconds
3. Retry: 2nd attempt → timeout → 3rd attempt → timeout
4. Circuit breaker records 3 failures
5. After 50% failure rate → circuit OPENS
6. All subsequent calls → INSTANT fallback (no network call):
   { id: 0, email: "fallback@gmail.com" }
7. ORDER-SERVICE continues serving other requests normally!
8. After 60 seconds → circuit tests → if USER-SERVICE recovered → close circuit
```

### Code Explanation

```java
@Retry(name = "userService")
@CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
public User getUser(String email) {
    return userClient.getUserByEmail(email);  // Feign call
}

// Fallback: MUST have same return type + Exception param
public User userFallback(String email, Exception e) {
    System.out.println("USER SERVICE DOWN: " + e.getMessage());
    User u = new User();
    u.setId(0L);
    u.setEmail("fallback@gmail.com");
    return u;  // Graceful degradation
}
```

### Services Using Circuit Breaker

| Service | Protects Against | Fallback Returns |
|---------|-----------------|------------------|
| ORDER-SERVICE | USER-SERVICE down | Default user (id=0) |
| ORDER-SERVICE | CAR-SERVICE down | Empty car list |
| ORDER-SERVICE | PAYMENT-SERVICE down | Payment status: FAILED_FALLBACK |
| ADMIN-SERVICE | USER-SERVICE down | Empty user list |
| RATING-SERVICE | USER-SERVICE down | Default user (id=0) |
| INVOICE-SERVICE | USER-SERVICE down | User with email only |

---

## 11. Event-Driven Architecture - RabbitMQ

### Message Flow - Complete Lifecycle

```
1. ORDER-SERVICE creates order successfully
        │
2. OrderPublisher.sendOrderEvent():
   rabbitTemplate.convertAndSend("order.exchange", "order.routing", event)
        │
3. Jackson serializes OrderEvent to JSON:
   {"orderId":1,"email":"sarthak@gmail.com","amount":500.0,"status":"CREATED"}
        │
4. RabbitMQ receives message:
   a. Exchange "order.exchange" (Direct type) receives message
   b. Looks for binding with routing key "order.routing"
   c. Finds: order.exchange → order.routing → order_queue
   d. Routes message to "order_queue"
   e. Message is PERSISTED to disk (durable queue)
        │
5. NOTIFICATION-SERVICE's @RabbitListener:
   a. Spring AMQP pulls message from queue
   b. Jackson deserializes JSON → OrderEvent object
   c. Calls consume(event) method
        │
6. NotificationListener.consume():
   a. Builds message: "Order ID: 1 | Status: CREATED | Amount: 500.0"
   b. Saves Notification entity to MySQL (notification_db)
   c. Logs: "EMAIL SENT to: sarthak@gmail.com"
        │
7. Message ACKNOWLEDGED:
   a. Spring AMQP sends ACK to RabbitMQ
   b. Message removed from queue permanently
   c. If consume() threw exception → NO ACK → message stays in queue → retry
```

### RabbitMQ Components Explained

| Component | Name | Purpose |
|-----------|------|---------|
| **Exchange** | `order.exchange` | Receives messages from producer, routes to queues |
| **Queue** | `order_queue` | Stores messages until consumed |
| **Binding** | `order.exchange → order_queue` | Routing rule connecting exchange to queue |
| **Routing Key** | `order.routing` | The key used for routing decisions |
| **Converter** | `Jackson2JsonMessageConverter` | Serializes Java objects ↔ JSON |

---

## 12. Distributed Tracing - Zipkin

### How a Request is Traced

```
Trace ID: abc-123-def-456 (assigned at Gateway, propagated everywhere)

CLIENT → GATEWAY (Span 1: 0ms-250ms)
           │
           └──► ORDER-SERVICE (Span 2: 10ms-240ms, parent: Span 1)
                    │
                    ├──► USER-SERVICE (Span 3: 20ms-50ms, parent: Span 2)
                    │    SQL query: 5ms
                    │
                    ├──► CAR-SERVICE (Span 4: 55ms-100ms, parent: Span 2)
                    │    SQL query: 8ms
                    │
                    └──► PAYMENT-SERVICE (Span 5: 105ms-230ms, parent: Span 2)
                         Processing: 120ms

TOTAL: 250ms
BOTTLENECK: PAYMENT-SERVICE (125ms = 50% of total time)
```

### B3 Headers (Automatic Propagation)

When ORDER-SERVICE calls USER-SERVICE via Feign, these headers are added **automatically** by Micrometer:

```
X-B3-TraceId: abc123def456
X-B3-SpanId: span2
X-B3-ParentSpanId: span1
X-B3-Sampled: 1
```

USER-SERVICE reads these and creates a child span with the same Trace ID.

---

## 13. Rate Limiting - Redis

### Token Bucket Algorithm Visualization

```
Time 0s:  Bucket: [████████████████████] 20/20 tokens
Request:  Bucket: [███████████████████ ] 19/20 → ALLOWED ✓
Request:  Bucket: [██████████████████  ] 18/20 → ALLOWED ✓
...
20 req:   Bucket: [                    ]  0/20 → ALLOWED ✓
21st req: Bucket: [                    ]  0/20 → REJECTED ✗ (429)

Time 1s:  Bucket: [██████████          ] 10/20 (10 tokens refilled)
Request:  Bucket: [█████████           ]  9/20 → ALLOWED ✓

Time 2s:  Bucket: [███████████████████ ] 19/20 (10 more tokens)
```

### Redis Data Structure

```
Key: "request_rate_limiter.{sarthak@gmail.com}.tokens"
Value: 15
Type: String (atomic counter)
TTL: auto-managed by Spring

Key: "request_rate_limiter.{sarthak@gmail.com}.timestamp"
Value: 1712400000
TTL: auto-managed
```

---

## 14. Monitoring & Observability

### Three Pillars

```
┌───────────────────────────────────────────────────────────┐
│                    OBSERVABILITY                            │
│                                                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐       │
│  │   METRICS   │  │   TRACING   │  │   LOGGING   │       │
│  │             │  │             │  │             │       │
│  │ Prometheus  │  │   Zipkin    │  │  Structured │       │
│  │ + Grafana   │  │             │  │  Logs with  │       │
│  │             │  │             │  │  Trace ID   │       │
│  │ "How much?" │  │ "Where?"   │  │ "What?"     │       │
│  │             │  │             │  │             │       │
│  │ Request rate│  │ Which svc  │  │ Error msgs  │       │
│  │ Latency p99 │  │ is slow?   │  │ Stack trace │       │
│  │ CPU/Memory  │  │ Call chain │  │ Debug info  │       │
│  └─────────────┘  └─────────────┘  └─────────────┘       │
└───────────────────────────────────────────────────────────┘
```

### Key Prometheus Metrics

| Metric | What It Tells You |
|--------|-------------------|
| `http_server_requests_seconds_count` | Total requests processed |
| `http_server_requests_seconds_sum / count` | Average response time |
| `http_server_requests_seconds{quantile="0.99"}` | 99th percentile latency |
| `jvm_memory_used_bytes{area="heap"}` | Heap memory consumption |
| `resilience4j_circuitbreaker_state` | Is the circuit open or closed? |
| `process_cpu_usage` | CPU utilization (0.0 to 1.0) |

---

## 15. Docker & Containerization

### Docker Compose - 17 Container Stack

```
INFRASTRUCTURE (6):
  mysql          → MySQL 8.0 database
  rabbitmq       → RabbitMQ with management UI
  redis          → Redis for rate limiting
  zipkin         → Distributed tracing server
  prometheus     → Metrics collection
  grafana        → Metrics dashboards

DISCOVERY & CONFIG (2):
  eureka-server  → Service registry
  config-server  → Configuration server

GATEWAY (1):
  api-gateway    → Public entry point (:8080)

BUSINESS SERVICES (8):
  user-service, car-service, order-service, payment-service
  rating-service, invoice-service, admin-service, notification-service
```

### Key Docker Concepts Used

| Concept | Our Usage | Why |
|---------|-----------|-----|
| `depends_on: condition: service_healthy` | Config Server waits for Eureka | Correct startup order |
| Named volumes (`mysql-data`) | MySQL data persists across restarts | Data durability |
| Bridge network (`carwash-network`) | All containers communicate | Network isolation |
| Docker DNS | `jdbc:mysql://mysql:3306` | Service name → container IP |
| Multi-stage Dockerfile | Build with JDK, run with JRE | Smaller images (~200MB vs ~500MB) |
| Health checks | `mysqladmin ping`, `wget actuator/health` | Dependency readiness |

---

## 16. Kubernetes Deployment

### Service Types Explained

| Type | Used For | Accessible From |
|------|----------|----------------|
| **ClusterIP** | All business services, infrastructure | Only inside the cluster |
| **LoadBalancer** | API Gateway | External (public IP) |
| **NodePort** | Grafana (port 30300) | External via node IP |

### Health Probes Explained

```yaml
startupProbe:           # "Is the app done starting?"
  httpGet:
    path: /actuator/health
    port: 8081
  initialDelaySeconds: 30    # Wait 30s before first check
  periodSeconds: 10          # Check every 10s
  failureThreshold: 20       # Allow 200s total startup time

readinessProbe:         # "Can the app handle traffic?"
  httpGet:
    path: /actuator/health
    port: 8081
  periodSeconds: 10

livenessProbe:          # "Is the app still alive?"
  httpGet:
    path: /actuator/health
    port: 8081
  periodSeconds: 15
```

---

## 17. CI/CD Pipeline

### 3-Stage Pipeline

```
PUSH TO GITHUB
      │
      ▼
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│ STAGE 1: DETECT │     │ STAGE 2: BUILD  │     │ STAGE 3: DEPLOY │
│                 │     │                 │     │                 │
│ Which services  │────►│ Maven package   │────►│ kubectl apply   │
│ changed?        │     │ Docker build    │     │ (main only)     │
│                 │     │ Push to Hub     │     │                 │
│ paths-filter    │     │ Only changed!   │     │ In order:       │
│ action          │     │                 │     │ ns → infra →    │
│                 │     │                 │     │ discovery →     │
│                 │     │                 │     │ gateway → svc   │
└─────────────────┘     └─────────────────┘     └─────────────────┘
```

---

## 18. Database Design

### Complete Schema

```
user_db.user
├── id          BIGINT AUTO_INCREMENT PK
├── name        VARCHAR(255)
├── email       VARCHAR(255) UNIQUE
├── password    VARCHAR(255)  ← BCrypt hashed
├── phone       VARCHAR(255)
└── role        VARCHAR(255)  ← CUSTOMER | WASHER | ADMIN

order_db.orders                          (named "orders" because "order" is SQL keyword)
├── id          BIGINT AUTO_INCREMENT PK
├── user_id     BIGINT  ← logical FK to user_db.user.id
├── car_id      BIGINT  ← logical FK to car_db.car.id
├── service_type VARCHAR(255)
├── status       VARCHAR(255)  ← CREATED | ASSIGNED | COMPLETED
├── washer_id    BIGINT  ← logical FK to user_db.user.id (washer)
├── scheduled_at DATETIME
└── created_at   DATETIME

car_db.car
├── id           BIGINT AUTO_INCREMENT PK
├── user_id      BIGINT  ← logical FK to user_db.user.id
├── brand        VARCHAR(255)
├── model        VARCHAR(255)
├── color        VARCHAR(255)
└── number_plate VARCHAR(255)

rating_db.reviews
├── id           BIGINT AUTO_INCREMENT PK
├── user_id      BIGINT
├── order_id     BIGINT
├── rating       INTEGER  ← 1-5
├── comment      VARCHAR(255)
└── created_at   DATETIME

invoice_db.invoice
├── id             BIGINT AUTO_INCREMENT PK
├── user_id        BIGINT
├── order_id       BIGINT
├── amount         DOUBLE
├── cgst           DOUBLE  ← 9%
├── sgst           DOUBLE  ← 9%
├── total_amount   DOUBLE
├── invoice_number VARCHAR(255)  ← "INV-1712400000000"
├── file_path      VARCHAR(255)
└── created_at     DATETIME

admin_db.service_plan
├── id          BIGINT AUTO_INCREMENT PK
├── name        VARCHAR(255)
├── price       DOUBLE
└── description VARCHAR(255)

notification_db.notification
├── id          BIGINT AUTO_INCREMENT PK
├── email       VARCHAR(255)
├── message     VARCHAR(255)
├── type        VARCHAR(255)  ← EMAIL | SMS | PUSH
└── created_at  DATETIME
```

---

## 19. Design Patterns Used

| # | Pattern | Where | How |
|---|---------|-------|-----|
| 1 | **API Gateway** | Spring Cloud Gateway | Single entry point for all clients |
| 2 | **Service Discovery** | Netflix Eureka | Dynamic service resolution |
| 3 | **Circuit Breaker** | Resilience4j | Prevents cascading failures |
| 4 | **Retry** | Resilience4j | Handles transient network failures |
| 5 | **Event-Driven** | RabbitMQ | Async notification delivery |
| 6 | **Database-per-Service** | 7 MySQL databases | Data isolation and autonomy |
| 7 | **Externalized Config** | Config Server + Git | Central config, zero-downtime updates |
| 8 | **Gateway Trust** | JWT at Gateway only | Single security perimeter |
| 9 | **DTO Pattern** | Request/Response objects | Separate API contract from entities |
| 10 | **Repository Pattern** | Spring Data JPA | Clean data access abstraction |
| 11 | **Builder Pattern** | `Jwts.builder()` | Fluent JWT construction |
| 12 | **Proxy Pattern** | Feign clients | Runtime HTTP client generation |
| 13 | **Observer Pattern** | RabbitMQ pub/sub | Decoupled event notification |
| 14 | **Factory Pattern** | `@Bean` in `@Configuration` | Spring bean creation |
| 15 | **Singleton Pattern** | Spring default scope | One instance per bean |
| 16 | **Template Method** | `JpaRepository` | Generated query implementations |

---

## 20. Project Metrics

| Metric | Value |
|--------|-------|
| Total microservices | 11 (8 business + 3 infrastructure) |
| Docker containers | 17 in Compose stack |
| Source files | 224 |
| Kubernetes manifests | 7 files |
| CI/CD stages | 3 |
| Prometheus scrape targets | 9 services |
| User roles | 3 (CUSTOMER, WASHER, ADMIN) |
| Order states | 3 (CREATED → ASSIGNED → COMPLETED) |
| JWT expiration | 24 hours |
| Password hashing | BCrypt |
| GST rate | 18% (9% CGST + 9% SGST) |
| Platform fee | 15% |
| Design patterns | 16 |
| README lines | 5,689 |

---

## 21. Challenges Faced & Solutions

### Challenge 1: 403 Forbidden Errors
**Problem:** Spring Security blocked internal Feign calls
**Root Cause:** Default Spring Security requires authentication for all endpoints
**Solution:** Gateway Trust Pattern - `anyRequest().permitAll()` in services, JWT validated only at Gateway

### Challenge 2: Feign Losing Auth Headers
**Problem:** Inter-service Feign calls didn't carry X-User-Email header
**Root Cause:** Feign creates new HTTP requests without original headers
**Solution:** `FeignConfig` with `RequestInterceptor` copying headers from `RequestContextHolder`

### Challenge 3: ReadOnlyHttpHeaders in Gateway
**Problem:** Couldn't modify request headers in Spring Cloud Gateway
**Root Cause:** Gateway request headers are immutable by design
**Solution:** Used `exchange.getRequest().mutate()` to create new request with extra headers

### Challenge 4: RabbitMQ Deserialization Failure
**Problem:** NOTIFICATION-SERVICE couldn't deserialize OrderEvent messages
**Root Cause:** Different Jackson converters on producer vs consumer
**Solution:** Standardized `Jackson2JsonMessageConverter` + matching `@NoArgsConstructor` DTO

### Challenge 5: Service Startup Order
**Problem:** Services started before Config Server/Eureka were ready
**Root Cause:** Docker Compose starts containers in parallel
**Solution:** `depends_on: condition: service_healthy` with health checks and `start_period`

### Challenge 6: Circuit Breaker Not Working
**Problem:** Circuit breaker wasn't opening after failures
**Root Cause:** `@Retry` was running after `@CircuitBreaker` instead of before
**Solution:** Reordered: `@Retry` wraps `@CircuitBreaker` - retries first, then record failure

### Challenge 7: Redis Rate Limiter Bean Not Found
**Problem:** `RequestRateLimiter` failed at Gateway startup
**Root Cause:** Missing `KeyResolver` bean
**Solution:** Created `RateLimiterConfig` with `userKeyResolver()` bean returning email or IP

### Challenge 8: Spring Security Default Login Page
**Problem:** Adding Spring Security to Gateway showed a login page
**Root Cause:** Spring Security auto-configures form login
**Solution:** Removed `spring-boot-starter-security` from Gateway entirely - used custom `GlobalFilter` instead

---

## 22. Future Improvements

| Priority | Improvement | Benefit |
|----------|------------|---------|
| **High** | OAuth2 with Keycloak | Enterprise SSO, social login |
| **High** | Saga Pattern | Distributed transaction management |
| **High** | Redis Caching | 10x faster reads for hot data |
| **High** | API Versioning (`/v1/`) | Backward compatibility |
| **Medium** | Swagger/OpenAPI | Auto-generated API docs |
| **Medium** | Flyway Migrations | Database schema versioning |
| **Medium** | Testcontainers | Real integration tests |
| **Medium** | WebSocket Notifications | Real-time push to clients |
| **Low** | gRPC | 2-3x faster inter-service calls |
| **Low** | Event Sourcing | Complete audit trail |
| **Low** | Istio Service Mesh | mTLS, traffic management |
| **Low** | K8s HPA | Auto-scaling on metrics |

---

## 23. Complete End-to-End Request Flow

### Scenario: Customer Places Order and Pays

```
STEP 1: LOGIN
  Client → POST /auth/login { email, password }
  Gateway → /auth = public → USER-SERVICE
  USER-SERVICE → BCrypt verify → Generate JWT → Return token
  Client stores token

STEP 2: CREATE ORDER
  Client → POST /order/create + Bearer token + { carId: 1, serviceType: "PREMIUM" }
  Gateway → Validate JWT → Add X-User-Email → Route to ORDER-SERVICE
  ORDER-SERVICE →
    → Feign → USER-SERVICE: GET /user/email?email=sarthak@gmail.com → { id: 1 }
    → Feign → CAR-SERVICE: GET /car/user/1 → [{ id: 1, brand: "BMW" }]
    → Validate carId=1 belongs to userId=1 ✓
    → Save Order { status: CREATED }
    → RabbitMQ → Publish OrderEvent { status: "CREATED" }
                    └→ NOTIFICATION-SERVICE saves + logs email
  Return: { id: 1, status: "CREATED" }

STEP 3: PAY
  Client → POST /order/pay/1?amount=500 + Bearer token
  Gateway → Validate JWT → Route to ORDER-SERVICE
  ORDER-SERVICE →
    → Feign → USER-SERVICE: verify user
    → Feign → PAYMENT-SERVICE: POST /payment/pay → { status: "SUCCESS" }
    → Update Order { status: COMPLETED }
    → RabbitMQ → Publish OrderEvent { status: "COMPLETED" }
                    └→ NOTIFICATION-SERVICE saves + logs email
  Return: "Payment SUCCESS"
```

---

## 24. Tech Stack Summary

| Category | Technologies |
|----------|-------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.3.5, Spring Cloud 2023.0.3 |
| **Gateway** | Spring Cloud Gateway (WebFlux, Netty) |
| **Discovery** | Netflix Eureka |
| **Config** | Spring Cloud Config + Spring Cloud Bus |
| **Sync Communication** | OpenFeign + Spring Cloud LoadBalancer |
| **Async Communication** | RabbitMQ (AMQP) |
| **Security** | JWT (jjwt 0.11.5), BCrypt, Spring Security |
| **Resilience** | Resilience4j (Circuit Breaker, Retry, Timeout) |
| **Database** | MySQL 8.0, Spring Data JPA, Hibernate |
| **Rate Limiting** | Redis (Token Bucket) |
| **Tracing** | Zipkin + Micrometer Brave |
| **Metrics** | Prometheus + Grafana |
| **Containerization** | Docker, Docker Compose |
| **Orchestration** | Kubernetes |
| **CI/CD** | GitHub Actions |
| **Build** | Maven |
| **Code Gen** | Lombok |
| **PDF** | OpenPDF (iText) |

---

## 25. Resume Bullet Points

### One-Liner
> Built a microservices-based On-Demand Car Wash System with 11 services using Spring Boot, Spring Cloud, RabbitMQ, Docker, and Kubernetes.

### Detailed Bullets
- Architected **11 microservices** with Spring Boot 3.x and Spring Cloud following **Domain-Driven Design** and **12-Factor App** methodology
- Implemented **API Gateway** with JWT authentication, **Redis rate limiting**, and **Eureka-based service discovery** with client-side load balancing
- Built **event-driven notification system** using RabbitMQ AMQP messaging with guaranteed delivery and consumer acknowledgment
- Integrated **Resilience4j circuit breakers**, retry, and timeout patterns preventing **cascading failures** with graceful degradation
- Containerized all services with **Docker** (17-container Compose stack), orchestrated with **Kubernetes**, and automated CI/CD with **GitHub Actions**
- Implemented **observability stack**: Zipkin distributed tracing, Prometheus metrics, Grafana dashboards with custom panels

---

---

# ============================================================
# PART B: TOP 50 INTERVIEW QUESTIONS WITH ANSWERS
# ============================================================

> Every answer below is specific to the Car Wash System project. Use these exact answers in your interview.

---

## Category 1: Project Overview Questions (Q1-Q8)

### Q1. Tell me about your project.

**Answer:**
*"I built an On-Demand Car Wash System - a production-grade microservices backend using Java 21, Spring Boot 3.x, and Spring Cloud. It has 11 services: 8 business services (User, Order, Payment, Car, Admin, Rating, Invoice, Notification), an API Gateway for centralized authentication and rate limiting, a Eureka Server for service discovery, and a Config Server for centralized configuration.*

*Customers can register, add cars, place wash orders, pay, get PDF invoices, and leave reviews. The system uses OpenFeign for synchronous inter-service communication, RabbitMQ for asynchronous event-driven notifications, Resilience4j for fault tolerance, and is fully containerized with Docker Compose (17 containers) and Kubernetes.*

*I also implemented CI/CD with GitHub Actions, distributed tracing with Zipkin, and monitoring with Prometheus and Grafana."*

---

### Q2. Why did you choose microservices architecture for this project?

**Answer:**
*"I chose microservices for five specific reasons:*

*First, independent scalability - during peak hours, ORDER-SERVICE might need 10 instances while ADMIN-SERVICE needs only 1. With microservices, I scale only what's needed, reducing infrastructure costs by 60-80%.*

*Second, fault isolation - when PAYMENT-SERVICE has a bug and crashes, users can still register, browse, and create orders. I implemented circuit breakers that return graceful fallbacks instead of cascading failures.*

*Third, independent deployment - I can push a hotfix to ORDER-SERVICE without redeploying all 10 services. This reduces deployment risk and enables faster release cycles.*

*Fourth, team autonomy - in a real organization, the payments team can own PAYMENT-SERVICE with their own release cycle without coordinating with every other team.*

*Fifth, technology freedom - although I used Java everywhere, the architecture allows each service to use the best technology for its needs."*

---

### Q3. What was the most challenging part of building this project?

**Answer:**
*"The most challenging part was getting the authentication flow to work correctly across all services. Initially, I had intermittent 403 Forbidden errors because Spring Security's default configuration was blocking internal Feign calls between services.*

*The root cause was that when ORDER-SERVICE called USER-SERVICE via Feign, the request didn't have a Spring Security authentication context. Spring Security saw it as an unauthenticated request and blocked it.*

*I solved this by implementing what I call the Gateway Trust Pattern - I validate JWT only once at the API Gateway, extract the user's email and role, forward them as trusted HTTP headers (X-User-Email, X-User-Role), and configure all downstream services to permit all requests. The services trust these headers because they're on an internal network that only the Gateway can reach.*

*This also required building a Feign RequestInterceptor that propagates these headers through the entire inter-service call chain. It was a deep architectural decision that taught me how authentication works differently in distributed systems compared to monoliths."*

---

### Q4. Walk me through the complete flow when a user places an order.

**Answer:**
*"Sure. When a customer places an order, the request goes through 6 services:*

*Step 1: The client sends POST /order/create with a JWT token and the order details (carId, serviceType).*

*Step 2: The API Gateway intercepts the request. The JwtAuthenticationFilter validates the JWT - checks the signature with HMAC-SHA256, verifies it's not expired, extracts the email and role claims. It then adds X-User-Email and X-User-Role headers to the request.*

*Step 3: The Gateway routes to ORDER-SERVICE via Eureka service discovery with load balancing.*

*Step 4: ORDER-SERVICE receives the request. It reads the X-User-Email header and makes a synchronous Feign call to USER-SERVICE to get the user's ID from their email. This call has retry (3 attempts) and circuit breaker protection.*

*Step 5: ORDER-SERVICE then makes another Feign call to CAR-SERVICE to fetch all cars belonging to this user, and validates that the requested carId actually belongs to them.*

*Step 6: ORDER-SERVICE saves the order to MySQL with status CREATED, then publishes an OrderEvent to RabbitMQ asynchronously. The order is returned to the client immediately - it doesn't wait for the notification.*

*Step 7: NOTIFICATION-SERVICE, which has a @RabbitListener on the order_queue, consumes the event, saves a notification record, and simulates sending an email.*

*If USER-SERVICE or CAR-SERVICE is down at step 4 or 5, the circuit breaker activates and returns a fallback response. If RabbitMQ is down at step 6, the order still succeeds - the notification is a nice-to-have, not a requirement."*

---

### Q5. How does your system handle failures?

**Answer:**
*"I implemented a multi-layered resilience strategy:*

*Layer 1 - Retry: Every inter-service Feign call is wrapped with @Retry. If a call fails due to a transient network issue, it automatically retries 3 times with a 1-second delay between attempts.*

*Layer 2 - Circuit Breaker: If all retries fail, the Resilience4j circuit breaker records the failure. When the failure rate exceeds 50% in a sliding window of 10 calls, the circuit opens. In the open state, all subsequent calls are immediately short-circuited to a fallback method without making any network call. This prevents thread pool exhaustion and cascading failures.*

*Layer 3 - Fallback Methods: Every circuit breaker has a fallback that returns a degraded but functional response. For example, if USER-SERVICE is down, the fallback returns a default user with id=0, and the calling service can decide how to handle that.*

*Layer 4 - Async Decoupling: Non-critical operations like notifications use RabbitMQ. If NOTIFICATION-SERVICE is down, messages queue up in RabbitMQ and are processed when it recovers. Order creation never fails due to notification issues.*

*Layer 5 - Health Checks: In Kubernetes, startup, readiness, and liveness probes detect unhealthy instances and restart them automatically."*

---

### Q6. How do your services discover each other?

**Answer:**
*"I use Netflix Eureka for client-side service discovery. Every service registers itself with the Eureka Server on startup, providing its name, IP address, and port. When Service A needs to call Service B, it asks Eureka for Service B's address.*

*For example, when ORDER-SERVICE needs USER-SERVICE, Feign's underlying Spring Cloud LoadBalancer queries Eureka with the service name 'USER-SERVICE'. Eureka returns all registered instances - say 192.168.1.5:8081 and 192.168.1.6:8081. The load balancer picks one using round-robin.*

*Services send heartbeats every 30 seconds. If a service misses 3 heartbeats (90 seconds), Eureka removes it. Services also cache the registry locally and refresh every 30 seconds, so they don't query Eureka on every call.*

*Eureka also has a self-preservation mode - if too many services miss heartbeats simultaneously, it assumes a network partition rather than mass failures, and stops evicting services to prevent cascading deregistration."*

---

### Q7. Explain your API Gateway and what it does.

**Answer:**
*"The API Gateway is the single entry point for all client requests. It's built on Spring Cloud Gateway, which uses WebFlux and Netty for reactive, non-blocking request handling.*

*It performs five critical functions:*

*First, JWT authentication - a custom GlobalFilter validates the JWT token on every protected request. It checks the HMAC-SHA256 signature, verifies expiration, and extracts the user's email and role.*

*Second, header enrichment - after validating the JWT, it adds X-User-Email and X-User-Role headers to the request so downstream services know who the user is without parsing the JWT themselves.*

*Third, rate limiting - using Redis as a backing store with the Token Bucket algorithm. Each user (identified by email) is allowed a configured number of requests per second. If exceeded, the Gateway returns 429 Too Many Requests.*

*Fourth, routing - the Gateway matches URL patterns to service names. /order/** goes to ORDER-SERVICE, /auth/** goes to USER-SERVICE, etc. It resolves service names via Eureka.*

*Fifth, load balancing - if a service has multiple instances, the Gateway distributes requests using round-robin via Spring Cloud LoadBalancer.*

*The reason I chose Spring Cloud Gateway over Zuul or Nginx is its reactive architecture. The Gateway is purely I/O-bound, and the non-blocking model allows it to handle thousands of concurrent connections with only 4-8 threads."*

---

### Q8. How do you manage configuration across 11 services?

**Answer:**
*"I use Spring Cloud Config Server backed by a Git repository. All configuration properties are stored in a central Git repo - one properties file per service plus a shared application.properties.*

*When a service starts, it fetches its configuration from the Config Server via a REST call. Config Server clones the Git repo and serves the merged properties (shared + service-specific).*

*The key advantage is centralized management - for example, the JWT secret key is defined once in the shared application.properties and all services get it. I don't have to duplicate it across 11 services.*

*For dynamic updates, I use Spring Cloud Bus backed by RabbitMQ. When I change a property in Git and call POST /actuator/busrefresh on Config Server, it publishes a refresh event to RabbitMQ. All services receive the event and re-initialize their @RefreshScope beans with the new values - zero downtime, no restart required."*

---

## Category 2: Technical Deep Dive Questions (Q9-Q25)

### Q9. How does JWT authentication work in your system?

**Answer:**
*"The JWT flow has four phases:*

*Generation: When a user logs in, USER-SERVICE validates their BCrypt-hashed password, then generates a JWT using the JJWT library. The token contains the email as the subject claim, the role as a custom claim, and a 24-hour expiration. It's signed with HMAC-SHA256 using a secret key from Config Server.*

*Validation: For every protected request, the API Gateway's GlobalFilter extracts the token from the Authorization header, strips the 'Bearer ' prefix, and calls JwtUtil.validateToken(). This method parses the token with the signing key - if the signature doesn't match or the token is expired, it throws an exception and the Gateway returns 401.*

*Enrichment: After validation, the Gateway extracts the email and role from the token's claims and adds them as X-User-Email and X-User-Role headers to the downstream request.*

*Consumption: Backend services simply read these headers. They don't validate JWT themselves because the Gateway has already done it. This is the Gateway Trust Pattern - services trust headers from the internal network."*

---

### Q10. What is the difference between synchronous and asynchronous communication in your project?

**Answer:**
*"I use both patterns for different use cases:*

*Synchronous (OpenFeign): When ORDER-SERVICE creates an order, it needs the user's ID from USER-SERVICE immediately - it can't proceed without it. So it makes a synchronous Feign call, waits for the response, and continues. Similarly for CAR-SERVICE (validate car ownership) and PAYMENT-SERVICE (process payment). These are blocking calls where the response is required to continue the business flow.*

*Asynchronous (RabbitMQ): When an order is created, a notification should be sent. But the order creation shouldn't fail if the notification can't be sent. So ORDER-SERVICE publishes an OrderEvent to RabbitMQ and returns immediately. NOTIFICATION-SERVICE consumes it whenever ready. If NOTIFICATION-SERVICE is down, the message waits in the queue (durable storage) and gets processed when it recovers.*

*The key decision criterion is: does the caller need the response to continue? If yes, synchronous. If no, asynchronous."*

---

### Q11. Explain the circuit breaker pattern in your project.

**Answer:**
*"The circuit breaker has three states: CLOSED, OPEN, and HALF-OPEN.*

*In CLOSED state (normal), all requests pass through. The circuit monitors a sliding window of the last 10 calls. If the failure rate exceeds 50% (5 out of 10 fail), it trips to OPEN.*

*In OPEN state, ALL requests are immediately rejected without attempting the network call. Instead, the fallback method is called - for example, returning a default user with id=0. This prevents thread pool exhaustion because we're not wasting threads waiting for a dead service.*

*After 60 seconds, the circuit moves to HALF-OPEN. It allows 3 test requests through. If they succeed, the circuit closes (recovery). If they fail, it reopens.*

*In my project, I layer @Retry on top of @CircuitBreaker. So the flow is: attempt call → fail → retry (3 times) → all retries fail → circuit breaker records failure → after enough failures → circuit opens → fallback called instantly.*

*I use this on every inter-service Feign call in ORDER-SERVICE, ADMIN-SERVICE, RATING-SERVICE, and INVOICE-SERVICE."*

---

### Q12. How does RabbitMQ work in your system?

**Answer:**
*"I have a Direct Exchange called 'order.exchange', a Queue called 'order_queue', and a Binding that connects them with routing key 'order.routing'.*

*When ORDER-SERVICE creates an order or processes a payment, it uses RabbitTemplate to publish an OrderEvent (orderId, email, amount, status) to the exchange. The message is serialized to JSON using Jackson2JsonMessageConverter.*

*The exchange routes the message to order_queue based on the routing key match. The queue stores the message durably (on disk).*

*NOTIFICATION-SERVICE has a @RabbitListener annotation on a method that consumes from order_queue. When a message arrives, Spring AMQP deserializes the JSON back to an OrderEvent object, calls the listener method, which saves a notification to MySQL and logs a simulated email. After successful processing, the message is acknowledged and removed from the queue.*

*If the listener throws an exception, the message is NOT acknowledged and stays in the queue for retry."*

---

### Q13. Why did you use Redis for rate limiting?

**Answer:**
*"Redis is ideal for rate limiting because of three properties:*

*First, speed - Redis operates in-memory with sub-millisecond response times. Since the rate limiter runs on EVERY request, it must be extremely fast. A database lookup would add 5-10ms per request.*

*Second, atomic operations - Redis supports INCR (atomic increment) and EXPIRE (time-to-live). The Token Bucket algorithm needs to atomically check and decrement a counter. Redis guarantees this even under concurrent access, preventing race conditions.*

*Third, distributed state - if I scale the API Gateway to 3 instances behind a load balancer, all instances share the same Redis. A user's rate limit is consistent regardless of which Gateway instance handles their request.*

*The algorithm I use is Token Bucket with the Spring Cloud Gateway RequestRateLimiter filter. Each user gets a bucket of 20 tokens (burstCapacity) that refills at 10 tokens per second (replenishRate). Each request costs one token."*

---

### Q14. How does distributed tracing work with Zipkin?

**Answer:**
*"When a request enters the API Gateway, Micrometer (Brave library) generates a unique Trace ID and a Span ID. This Trace ID propagates through every service call via B3 HTTP headers (X-B3-TraceId, X-B3-SpanId).*

*Each service creates a child Span for its processing. For example, an order creation generates: Gateway span → Order Service span → User Service span → Car Service span → Payment Service span. All share the same Trace ID.*

*Each service reports its span timing to the Zipkin collector asynchronously. Zipkin assembles all spans into a complete trace.*

*In the Zipkin UI, I can see the full call chain, how long each service took, and identify bottlenecks. If PAYMENT-SERVICE takes 125ms out of a 250ms total request, I know where to optimize.*

*The beauty is that the instrumentation is automatic - just adding micrometer-tracing-bridge-brave and zipkin-reporter-brave dependencies. No code changes needed."*

---

### Q15. How do you handle database consistency across services?

**Answer:**
*"Since each service has its own database, I can't use traditional ACID transactions across services. Instead, I follow eventual consistency.*

*For example, when an order is created, ORDER-SERVICE saves to order_db and publishes an event to RabbitMQ. NOTIFICATION-SERVICE eventually processes the event and saves to notification_db. There's a brief window where the order exists but the notification doesn't - this is eventual consistency.*

*For the order-payment flow, ORDER-SERVICE calls PAYMENT-SERVICE synchronously and only updates the order status to COMPLETED if payment returns SUCCESS. This is a simple request-response pattern that maintains consistency.*

*If I needed stronger consistency guarantees, I would implement the Saga pattern - a sequence of local transactions where each step has a compensating transaction. If step 3 fails, steps 2 and 1 are rolled back via compensating actions."*

---

### Q16. Explain your Docker Compose setup.

**Answer:**
*"My docker-compose.yml defines 17 containers organized in layers:*

*Infrastructure layer: MySQL 8.0, RabbitMQ with management UI, Redis for rate limiting, Zipkin for tracing, Prometheus for metrics, Grafana for dashboards.*

*Discovery layer: Eureka Server and Config Server. Config Server has depends_on Eureka with condition: service_healthy.*

*Gateway layer: API Gateway with depends_on Config Server and Redis.*

*Service layer: All 8 business services, each with depends_on Config Server, MySQL, and RabbitMQ.*

*All containers share a bridge network called carwash-network. Inside this network, containers reference each other by service name - ORDER-SERVICE connects to mysql:3306, not localhost:3306. Docker's built-in DNS resolves these names.*

*MySQL data is stored on a named volume (mysql-data) for persistence. Health checks use mysqladmin ping, rabbitmq-diagnostics, redis-cli ping, and wget actuator/health to ensure readiness before dependent services start."*

---

### Q17. How would you deploy this to production?

**Answer:**
*"I have Kubernetes manifests ready for production deployment. The deployment order is:*

*1. Create namespace (carwash)*
*2. Apply secrets (MySQL credentials)*
*3. Deploy infrastructure (MySQL, RabbitMQ, Redis, Zipkin with PersistentVolumeClaims)*
*4. Deploy discovery services (Eureka, Config Server)*
*5. Deploy API Gateway (LoadBalancer type for external access)*
*6. Deploy all 8 business services (ClusterIP for internal-only access)*
*7. Deploy monitoring (Prometheus, Grafana)*

*Every pod has resource requests and limits (CPU: 250m-500m, Memory: 256Mi-768Mi), startup probes (allow 200 seconds for Spring Boot), readiness probes (check /actuator/health), and liveness probes (restart if unhealthy).*

*My CI/CD pipeline detects which services changed, builds only those, pushes to Docker Hub, and applies K8s manifests automatically on merge to main."*

---

### Q18. How do Feign clients forward authentication headers?

**Answer:**
*"When the Gateway forwards a request to ORDER-SERVICE, it includes X-User-Email and X-User-Role headers. But when ORDER-SERVICE then calls USER-SERVICE via Feign, Feign creates a completely new HTTP request that doesn't have those headers.*

*I solved this with a FeignConfig class that defines a RequestInterceptor bean. Before every Feign call, the interceptor runs. It accesses the current HTTP request from Spring's RequestContextHolder (which stores the original request in a ThreadLocal), reads the X-User-Email and X-User-Role headers, and copies them to the outgoing Feign request.*

*Every service that makes Feign calls (Order, Admin, Rating, Invoice) has this FeignConfig, and every @FeignClient specifies configuration = FeignConfig.class.*

*One caveat: RequestContextHolder is thread-bound. If you make Feign calls from an @Async method or a separate thread, it returns null. For async scenarios, you'd need to manually pass headers as method parameters."*

---

### Q19. What monitoring do you have in place?

**Answer:**
*"I have all three pillars of observability:*

*Metrics: Every service exposes Prometheus metrics via Spring Boot Actuator at /actuator/prometheus. Prometheus scrapes all 9 services every 15 seconds. I track HTTP request rate, response latency (p50, p95, p99), JVM memory usage, thread count, circuit breaker state, and database connection pool metrics. Grafana displays these on a custom dashboard.*

*Tracing: Zipkin collects spans from all services. I can trace any request across the entire microservice chain, see timing breakdowns, and identify bottlenecks. 100% of requests are sampled in development.*

*Logging: Services use SLF4J with Lombok's @Slf4j. Trace IDs are automatically included in log messages, allowing me to correlate logs across services for a single request."*

---

### Q20. How does your system handle a service being completely down?

**Answer:**
*"Let's trace what happens when USER-SERVICE goes completely down:*

*ORDER-SERVICE tries to call USER-SERVICE via Feign. The connection is refused. @Retry retries 3 times with 1-second delays. All fail. Circuit breaker records 3 failures.*

*After enough failures, the circuit opens. Now all calls to USER-SERVICE are instantly short-circuited to the fallback method, returning a default user {id: 0, email: 'fallback@gmail.com'}.*

*ORDER-SERVICE checks: is user.id == 0? If so, it knows USER-SERVICE is down and can either return a degraded response or throw a meaningful error like 'User service temporarily unavailable.'*

*ADMIN-SERVICE's getUsers() fallback returns an empty list. Its getReport() fallback returns 'Report unavailable: User service is down.'*

*Meanwhile, the API Gateway still works. Users can access any endpoint that doesn't depend on USER-SERVICE. They can view notifications, check service plans, etc.*

*After USER-SERVICE recovers, the circuit enters HALF-OPEN state, tests with 3 requests, and if successful, closes the circuit. Normal operation resumes automatically."*

---

### Q21. What is the difference between ClusterIP and LoadBalancer in your K8s setup?

**Answer:**
*"ClusterIP creates an internal-only IP that's accessible only from within the Kubernetes cluster. I use this for all 8 business services, the Eureka Server, Config Server, MySQL, RabbitMQ, Redis, and Zipkin. No external traffic can reach these directly.*

*LoadBalancer creates an external IP address (via the cloud provider). I use this only for the API Gateway because it's the only service that needs to be publicly accessible. The cloud provider provisions a load balancer that routes external traffic to the Gateway pods.*

*This is a security best practice - the Gateway is the only public entry point. It validates JWT, rate limits, and routes requests to internal services. No one can bypass the Gateway to reach services directly."*

---

### Q22. How does your CI/CD pipeline work?

**Answer:**
*"My GitHub Actions pipeline has 3 stages:*

*Stage 1 - Change Detection: Uses the paths-filter action to detect which service directories have changed in the commit. If only order-service/ files changed, only order-service is flagged.*

*Stage 2 - Build & Push: Uses a matrix strategy. For each flagged service, it sets up Java 21, runs Maven package, builds a Docker image, and pushes to Docker Hub. Services that didn't change are skipped entirely.*

*Stage 3 - Deploy: Only runs on pushes to main (not PRs). Applies Kubernetes manifests in dependency order: namespace → secrets → infrastructure → discovery → gateway → services → monitoring.*

*The smart build strategy means a typical change builds in 2-3 minutes instead of 15+ minutes for a full rebuild."*

---

### Q23. Why did you use BCrypt for password hashing?

**Answer:**
*"BCrypt is the industry standard for password hashing because of three properties:*

*First, it's deliberately slow - it uses a configurable cost factor (we use the default 10, meaning 2^10 rounds). This makes brute-force attacks impractical. Even with modern GPUs, testing billions of passwords takes years.*

*Second, it includes a random salt in every hash. The same password 'abc123' hashed twice produces different outputs. This defeats rainbow table attacks and ensures that even if two users have the same password, their hashes are different.*

*Third, it's adaptive - I can increase the cost factor as hardware gets faster, maintaining security over time.*

*In our system, encoder.encode(password) hashes during registration, and encoder.matches(rawPassword, hashedPassword) compares during login without ever decrypting the hash."*

---

### Q24. What is Spring Cloud Bus and how do you use it?

**Answer:**
*"Spring Cloud Bus links distributed services through a lightweight message broker - in our case, RabbitMQ. Its primary use in our system is dynamic configuration refresh.*

*When I change a property in the Config Server's Git repository and call POST /actuator/busrefresh on Config Server, it publishes a refresh event to a RabbitMQ topic. Every service that includes spring-cloud-starter-bus-amqp is subscribed to this topic.*

*When a service receives the refresh event, Spring re-initializes all beans marked with @RefreshScope. These beans are destroyed and recreated with the new configuration values. For example, if I change jwt.secret, all services get the new secret without any restart.*

*This is different from regular Spring Cloud Config, which only loads config at startup. With Cloud Bus, I get zero-downtime configuration updates."*

---

### Q25. How do you handle role-based authorization?

**Answer:**
*"Authorization is handled at two levels:*

*At the Gateway level, the JWT filter extracts the user's role from the token and forwards it as the X-User-Role header. The Gateway itself doesn't enforce role checks - it just passes the information.*

*At the service level, controllers that need role checks read the X-User-Role header and enforce access. For example, ADMIN-SERVICE has a checkAdmin() helper method:*

```java
private void checkAdmin(String role) {
    if (!"ADMIN".equals(role)) {
        throw new RuntimeException("Access Denied: Admin only");
    }
}
```

*Every admin endpoint calls this before processing. If a CUSTOMER tries to access /admin/users, they get an 'Access Denied' error even though they have a valid JWT.*

*I chose this approach over Spring Security's role-based authorization because it's simpler and more transparent. In a microservices architecture where Spring Security is intentionally disabled in services, a simple header check is more appropriate than configuring a full security filter chain."*

---

## Category 3: System Design Questions (Q26-Q35)

### Q26. How would you scale this system to handle 10x traffic?

**Answer:**
*"I'd scale at multiple levels. At the application level, increase ORDER-SERVICE replicas from 1 to 10 using Kubernetes HPA based on CPU or custom Prometheus metrics. At the database level, add MySQL read replicas for read-heavy queries and use Redis caching for frequently accessed data. At the messaging level, add more NOTIFICATION-SERVICE consumers to process events faster. At the gateway level, scale to 3-5 instances behind a cloud load balancer. The reactive Gateway handles thousands of connections per instance. At the infrastructure level, use managed services - AWS RDS for MySQL, Amazon ElastiCache for Redis, Amazon MQ for RabbitMQ."*

---

### Q27. How would you add a new service to this architecture?

**Answer:**
*"Five steps. Create a new Spring Boot project with the standard dependencies (web, jpa, eureka-client, config-client, actuator, micrometer). Add application.properties with spring.application.name and spring.config.import pointing to Config Server. Create the service's config file in the central Git repo. Add a route in API-GATEWAY.properties. Add the service to docker-compose.yml and k8s/services.yml. That's it - it auto-registers with Eureka, fetches config from Config Server, and is discoverable by other services."*

---

### Q28. What happens if RabbitMQ goes down?

**Answer:**
*"Order creation still works because the RabbitMQ publish is wrapped in a try-catch. The order is saved to the database successfully, and the RabbitMQ error is logged. The customer doesn't see any error. However, the notification won't be sent. When RabbitMQ comes back up, the notification for this specific order is lost (it was never published). To prevent this, I could add a transactional outbox pattern - save the event to a database table alongside the order, and have a separate process that reads the outbox and publishes to RabbitMQ. This guarantees at-least-once delivery."*

---

### Q29. How would you implement a distributed transaction across Order and Payment?

**Answer:**
*"Currently, I handle this with a simple synchronous call - ORDER-SERVICE calls PAYMENT-SERVICE and only marks the order as COMPLETED if payment returns SUCCESS. For more complex scenarios, I would implement the Saga pattern. Specifically, a choreography-based saga where each service publishes events and listens for events:*

*1. ORDER-SERVICE creates order (PENDING) → publishes OrderCreated event*
*2. PAYMENT-SERVICE receives event → processes payment → publishes PaymentCompleted or PaymentFailed*
*3. ORDER-SERVICE receives PaymentCompleted → updates order to COMPLETED*
*4. ORDER-SERVICE receives PaymentFailed → updates order to CANCELLED (compensating transaction)*

*This avoids distributed transactions (2PC) which are fragile in microservices."*

---

### Q30. How would you handle API versioning?

**Answer:**
*"I'd use URI versioning at the Gateway level: /v1/order/create routes to ORDER-SERVICE v1, /v2/order/create routes to ORDER-SERVICE v2. Both versions can run simultaneously. I'd maintain backward compatibility by keeping the v1 route active until all clients migrate. The Gateway's route configuration makes this straightforward - just add new routes pointing to different service instances or different path rewrites."*

---

### Q31. How would you implement caching in this architecture?

**Answer:**
*"I'd add Redis caching at the service level using Spring Cache. For example, USER-SERVICE's getUserByEmail() is called frequently by Order, Rating, and Invoice services. I'd cache the result with @Cacheable('users', key='#email') and set a TTL of 5 minutes. Cache invalidation happens via @CacheEvict when a user updates their profile. This reduces database load by 90% for read-heavy endpoints and cuts inter-service latency."*

---

### Q32. What if two users try to book the same washer at the same time?

**Answer:**
*"This is a concurrent access problem. I'd solve it with optimistic locking - add a @Version field to the Order entity. When an admin assigns a washer, JPA checks the version. If another request already assigned a different washer (version changed), JPA throws OptimisticLockException and the second request gets a meaningful error. For higher contention scenarios, I'd use a distributed lock with Redis using SET with NX (set-if-not-exists) and PX (expiry) flags."*

---

### Q33. How would you implement real-time notifications instead of simulated email?

**Answer:**
*"Three options depending on requirements. For email, integrate with SendGrid or AWS SES - replace the console.log in NotificationListener with an API call. For push notifications, integrate with Firebase Cloud Messaging. For real-time in-app notifications, add WebSocket support using Spring's STOMP over WebSocket. NOTIFICATION-SERVICE would maintain WebSocket connections with connected clients and push OrderEvents directly to the browser."*

---

### Q34. How would you secure inter-service communication?

**Answer:**
*"Currently, security relies on network isolation - services are on a Docker bridge network or K8s ClusterIP, not publicly accessible. For production with stricter requirements, I'd implement mutual TLS (mTLS) using an Istio service mesh. Every inter-service call would be encrypted with TLS, and each service would have a certificate verified by Istio's certificate authority. This achieves zero-trust networking where even internal traffic is authenticated and encrypted."*

---

### Q35. How do you handle logging across 11 services?

**Answer:**
*"Each service logs to stdout using SLF4J with Lombok's @Slf4j. Micrometer automatically injects the Trace ID into the MDC (Mapped Diagnostic Context), so every log line includes the trace ID. This means I can grep for a Trace ID and see all logs from all services for a single request. In production, I'd add the ELK stack (Elasticsearch, Logstash, Kibana) - services send logs to Logstash, Elasticsearch indexes them, and Kibana provides a search UI. With the Trace ID, I can filter all logs for a single customer's order flow."*

---

## Category 4: Spring Boot & Java Questions (Q36-Q42)

### Q36. What annotations are used in your controllers and what do they do?

**Answer:**
*"@RestController combines @Controller and @ResponseBody - makes every method return JSON. @RequestMapping('/order') sets the base URL. @PostMapping('/create') maps POST requests. @RequestBody deserializes JSON body to Java object. @RequestParam binds query parameters. @PathVariable extracts URL segments. @RequestHeader reads HTTP headers - I use this for X-User-Email and X-User-Role. @RequiredArgsConstructor from Lombok generates constructor injection for all final fields."*

---

### Q37. Explain how Spring Data JPA works in your repositories.

**Answer:**
*"I extend JpaRepository<Entity, ID> which gives me CRUD methods for free - save(), findById(), findAll(), delete(), count(). For custom queries, I use Spring Data's method naming convention. For example, findByUserId(Long userId) generates SELECT * FROM car WHERE user_id = ?. findByEmail(String email) generates SELECT * FROM user WHERE email = ?. Spring parses the method name at startup and generates the implementation. No SQL or JPQL needed."*

---

### Q38. What is @RefreshScope and where do you use it?

**Answer:**
*"@RefreshScope is a Spring Cloud annotation that marks a bean for dynamic refresh. When /actuator/busrefresh is called, Spring destroys and recreates all @RefreshScope beans. I use it on JwtService in multiple services - when I change jwt.secret in the Config Server's Git repo and trigger refresh, the JwtService bean is recreated with the new secret. Without @RefreshScope, the bean would keep using the old secret until the service restarts."*

---

### Q39. How does constructor injection work with Lombok in your services?

**Answer:**
*"@RequiredArgsConstructor generates a constructor for all final fields. So when I have:*

```java
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repo;
    private final UserClient userClient;
}
```

*Lombok generates: public OrderService(OrderRepository repo, UserClient userClient) { this.repo = repo; this.userClient = userClient; }*

*Spring Boot sees this single constructor and automatically injects the matching beans. This is constructor injection - the recommended approach because fields are final (immutable) and the dependency is explicit in the constructor."*

---

### Q40. What is the difference between @Entity and @Data?

**Answer:**
*"@Entity is a JPA annotation that marks a class as a database table mapping. Hibernate creates the table, manages persistence, and generates SQL. @Data is a Lombok annotation that generates getters, setters, toString, equals, and hashCode. They serve completely different purposes. I use both on entity classes - @Entity for persistence and @Data for boilerplate reduction. On DTOs, I use only @Data since they're not persisted."*

---

### Q41. Why do you use Enum for OrderStatus and Roles?

**Answer:**
*"Enums provide type safety. Instead of using strings like 'CREATED' (which could be misspelled as 'CRAETED'), I use OrderStatus.CREATED which is compile-time checked. I annotate with @Enumerated(EnumType.STRING) to store the string representation in MySQL. Without this, JPA stores the ordinal (0, 1, 2) which breaks if you reorder enum values. With STRING, the database column contains readable values: 'CREATED', 'ASSIGNED', 'COMPLETED'."*

---

### Q42. Explain the DTO pattern in your project.

**Answer:**
*"DTOs separate the API contract from the internal entity. For example, RegisterRequest has {name, email, phone, password, role} - this is what the client sends. The User entity has {id, name, email, password (hashed), phone, role (enum)}. The differences: RegisterRequest doesn't have id (database generates it), password is plain text (gets BCrypt hashed), role is a String (gets converted to enum). Using DTOs prevents clients from setting id, sending pre-hashed passwords, or manipulating internal fields."*

---

## Category 5: DevOps & Infrastructure Questions (Q43-Q50)

### Q43. What is the difference between Docker Compose and Kubernetes?

**Answer:**
*"Docker Compose is a local development tool - it defines and runs multi-container applications on a single machine. I use it to start all 17 containers locally with one command. Kubernetes is a production-grade container orchestration platform that manages containers across a cluster of machines. It provides auto-scaling, self-healing, rolling updates, service discovery, and secret management. Docker Compose is for development, Kubernetes is for production."*

---

### Q44. Explain the health check strategy in your Docker Compose.

**Answer:**
*"Every container has a health check that Docker runs periodically. MySQL uses mysqladmin ping, RabbitMQ uses rabbitmq-diagnostics check_port_connectivity, Redis uses redis-cli ping, and Spring Boot services use wget actuator/health. The key is the depends_on with condition: service_healthy - Config Server waits for Eureka to be healthy before starting, and all business services wait for Config Server. Spring Boot services have start_period: 40s to allow JVM startup time before the first health check."*

---

### Q45. How do you handle secrets in your deployment?

**Answer:**
*"In Docker Compose, secrets are in environment variables (not ideal for production). In Kubernetes, I use K8s Secrets - a separate YAML file with base64-encoded credentials. Pods reference secrets using valueFrom.secretKeyRef instead of hardcoding values. The JWT secret is in the Config Server's Git repo. In production, I'd use a vault like HashiCorp Vault or AWS Secrets Manager, and the Config Server would decrypt secrets on-the-fly using encryption keys."*

---

### Q46. What happens if a Kubernetes pod crashes?

**Answer:**
*"Kubernetes automatically restarts it. The Deployment controller monitors pod status. If a pod crashes, K8s creates a new one. The liveness probe detects if a pod is stuck (not crashed but unresponsive) and restarts it. The readiness probe removes unhealthy pods from the Service's endpoint list so they don't receive traffic. With replicas > 1, other pods continue serving while the crashed pod restarts. This is self-healing."*

---

### Q47. How does your multi-stage Dockerfile work?

**Answer:**
*"Two stages. Stage 1 uses the JDK image - copies pom.xml and source code, runs mvn clean package to build the JAR. Stage 2 uses the smaller JRE image - copies only the built JAR from stage 1 and runs it. The final image contains only the JRE and the JAR, not Maven, source code, or build tools. This reduces image size from ~500MB to ~200MB, improving pull times and reducing attack surface."*

---

### Q48. How do containers communicate in Docker Compose?

**Answer:**
*"All containers are on the same Docker bridge network (carwash-network). Docker provides built-in DNS resolution - containers reference each other by service name. When ORDER-SERVICE connects to jdbc:mysql://mysql:3306/car_wash, Docker DNS resolves 'mysql' to the MySQL container's IP (e.g., 172.18.0.2). This is why we override connection URLs in environment variables - localhost doesn't work in containers because each container has its own network namespace."*

---

### Q49. What is the difference between a Deployment and a StatefulSet in Kubernetes?

**Answer:**
*"A Deployment manages stateless pods - they're interchangeable, can be created and destroyed freely, and don't need stable network identities. I use Deployments for all my services because they're stateless (state is in MySQL). A StatefulSet manages stateful pods - each pod has a stable hostname, persistent storage, and ordered deployment. I would use a StatefulSet for MySQL if I needed replication, but currently I use a single-replica Deployment with a PersistentVolumeClaim for simplicity."*

---

### Q50. If you had to rebuild this project from scratch, what would you do differently?

**Answer:**
*"Five things:*

*First, I'd use OAuth2 with Keycloak from the start instead of custom JWT. Keycloak provides SSO, social login, user management UI, and token management out of the box.*

*Second, I'd implement the Saga pattern for the order-payment flow from day one, using a choreography approach with RabbitMQ events and compensating transactions.*

*Third, I'd add Flyway database migrations instead of relying on JPA's ddl-auto=update, which is not safe for production.*

*Fourth, I'd add comprehensive integration tests using Testcontainers - spinning up real MySQL, RabbitMQ, and Redis containers during tests.*

*Fifth, I'd implement an API documentation layer with SpringDoc OpenAPI (Swagger) for every service, making it easier for frontend developers to consume the APIs.*

*But I want to emphasize - the current architecture is solid. These are enhancements, not fixes. The core patterns (API Gateway, service discovery, circuit breaker, event-driven messaging, centralized config) are all production-ready."*

---

---

# ============================================================
# PART C: LIVE DEMO - READY-TO-USE CURL COMMANDS
# ============================================================

> Copy-paste these commands during your interview to demonstrate the system live. Replace `<token>` with the actual JWT from the login response.

---

## Demo Step 1: Register a Customer

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sarthak Pawar",
    "email": "sarthak@gmail.com",
    "phone": "9876543210",
    "password": "1234",
    "role": "CUSTOMER"
  }'
```

**Expected Response:**
```json
{
  "message": "User registered successfully",
  "token": "Bearer eyJhbGciOiJIUzI1NiJ9...",
  "email": "sarthak@gmail.com",
  "role": "CUSTOMER"
}
```

**What to say:** *"The request hits the API Gateway on port 8080. The Gateway sees /auth is a public path, skips JWT validation, and routes to USER-SERVICE. The password is hashed with BCrypt before storing in MySQL, and a JWT token is generated with the user's email and role as claims."*

---

## Demo Step 2: Register a Washer

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Washer One",
    "email": "washer@gmail.com",
    "phone": "1234567890",
    "password": "1234",
    "role": "WASHER"
  }'
```

---

## Demo Step 3: Register an Admin (Only One Allowed)

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Admin User",
    "email": "admin@gmail.com",
    "phone": "1111111111",
    "password": "1234",
    "role": "ADMIN"
  }'
```

**What to say:** *"The system enforces a business rule: only one ADMIN can exist. If someone tries to register a second admin, they get 'Admin already exists' error. This is validated at the application level using existsByRole(Roles.ADMIN) query."*

---

## Demo Step 4: Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "sarthak@gmail.com",
    "password": "1234"
  }'
```

**Copy the token from the response for the next commands.**

---

## Demo Step 5: Add a Car

```bash
curl -X POST http://localhost:8080/car/add \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "brand": "BMW",
    "model": "X5",
    "color": "Black",
    "numberPlate": "MH12AB1234"
  }'
```

**What to say:** *"Now the Gateway validates the JWT, extracts the email and role, adds X-User-Email and X-User-Role headers, and routes to CAR-SERVICE. The service reads the header to know who the user is."*

---

## Demo Step 6: Create an Order

```bash
curl -X POST http://localhost:8080/order/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "carId": 1,
    "serviceType": "PREMIUM"
  }'
```

**What to say:** *"This is where the magic happens. ORDER-SERVICE makes THREE inter-service calls: Feign to USER-SERVICE to get user ID, Feign to CAR-SERVICE to validate car ownership, then saves the order. It also publishes an OrderEvent to RabbitMQ asynchronously. NOTIFICATION-SERVICE picks it up and creates a notification. All Feign calls have retry and circuit breaker protection."*

---

## Demo Step 7: View My Orders

```bash
curl http://localhost:8080/order/my \
  -H "Authorization: Bearer <token>"
```

---

## Demo Step 8: Pay for the Order

```bash
curl -X POST "http://localhost:8080/order/pay/1?amount=500" \
  -H "Authorization: Bearer <token>"
```

**What to say:** *"ORDER-SERVICE calls PAYMENT-SERVICE via Feign. If payment returns SUCCESS, the order status updates to COMPLETED and another RabbitMQ event is published for the notification."*

---

## Demo Step 9: Leave a Review

```bash
curl -X POST http://localhost:8080/review/add \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "orderId": 1,
    "rating": 5,
    "comment": "Excellent service!"
  }'
```

---

## Demo Step 10: Generate Invoice PDF

```bash
curl "http://localhost:8080/invoice/pdf?orderId=1&amount=500" \
  -H "Authorization: Bearer <token>" \
  --output invoice.pdf
```

**What to say:** *"INVOICE-SERVICE calculates GST - 9% CGST plus 9% SGST plus a 15% platform fee. It generates a professional PDF using the OpenPDF library with seller details, buyer details, a service table, and a GST breakdown. The PDF is returned as binary data."*

---

## Demo Step 11: Admin - View All Users (Login as Admin first)

```bash
# Login as admin first
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@gmail.com", "password": "1234"}'

# Use admin token
curl http://localhost:8080/admin/users \
  -H "Authorization: Bearer <admin_token>"
```

**What to say:** *"ADMIN-SERVICE reads the X-User-Role header and calls checkAdmin(). If the role isn't ADMIN, it returns 'Access Denied'. This is application-level authorization. The actual user list comes from a Feign call to USER-SERVICE with circuit breaker protection."*

---

## Demo Step 12: View Notifications (Public)

```bash
curl http://localhost:8080/notification/all
```

**What to say:** *"This is a public endpoint. You can see all notifications created by the RabbitMQ consumer. Each notification was triggered by an OrderEvent published by ORDER-SERVICE."*

---

## Demo Step 13: Show Monitoring UIs

```
Eureka Dashboard:  http://localhost:8761    → Show all registered services
Zipkin:            http://localhost:9411    → Show distributed traces
RabbitMQ:          http://localhost:15672   → Show queues and messages (guest/guest)
Prometheus:        http://localhost:9090    → Show metrics targets
Grafana:           http://localhost:3000    → Show dashboards (admin/admin)
```

**What to say:** *"Here's Eureka showing all 9 services registered. Here's Zipkin showing the trace for that order creation - you can see it touched Gateway, ORDER-SERVICE, USER-SERVICE, CAR-SERVICE, and PAYMENT-SERVICE. Here's RabbitMQ showing 2 messages were consumed from order_queue. And here's Grafana showing real-time request rates and JVM memory across all services."*

---

## Demo: Showing Circuit Breaker in Action

```bash
# 1. Stop USER-SERVICE
docker stop user-service

# 2. Try creating an order (will use fallback)
curl -X POST http://localhost:8080/order/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{"carId": 1, "serviceType": "BASIC"}'

# 3. Check ORDER-SERVICE logs - should show "USER SERVICE DOWN"

# 4. Restart USER-SERVICE
docker start user-service

# 5. Wait 60 seconds (circuit half-open period)
# 6. Try again - should work normally
```

**What to say:** *"Watch - I'm stopping USER-SERVICE. Now when I create an order, the Feign call fails, retries 3 times, then the circuit breaker opens and the fallback kicks in. The system doesn't crash - it degrades gracefully. Now I restart USER-SERVICE, wait for the circuit to go half-open, and it recovers automatically."*

---

---

# ============================================================
# PART D: COMPLETE SPRING BOOT ANNOTATIONS GLOSSARY
# ============================================================

> Every annotation used in this project, organized by category.

---

## Core Spring Annotations

| Annotation | Location | What It Does |
|-----------|----------|-------------|
| `@SpringBootApplication` | Main class | Combines `@Configuration` + `@EnableAutoConfiguration` + `@ComponentScan`. Bootstraps the entire Spring application. |
| `@Configuration` | SecurityConfig, FeignConfig, RabbitMQConfig | Marks a class as a source of bean definitions. Methods annotated with `@Bean` inside this class produce Spring-managed objects. |
| `@Bean` | SecurityConfig, RateLimiterConfig | Declares a method that returns an object to be registered as a Spring bean. Spring calls this method once and manages the returned object's lifecycle. |
| `@Component` | JwtAuthenticationFilter | Generic Spring-managed component. Spring creates one instance and injects it wherever needed. |
| `@Service` | OrderService, AuthService, JwtService | Specialized `@Component` for business logic classes. Semantically identical to `@Component` but indicates the class holds business logic. |
| `@Repository` | (implicit via JpaRepository) | Specialized `@Component` for data access. Spring Data JPA auto-implements repository interfaces, so we don't use this annotation directly. |
| `@Value("${property}")` | JwtService, JwtUtil | Injects a property value from application.properties or Config Server. Example: `@Value("${jwt.secret}")` injects the JWT secret key. |

## Web/REST Annotations

| Annotation | Location | What It Does |
|-----------|----------|-------------|
| `@RestController` | All controllers | Combines `@Controller` + `@ResponseBody`. Every method's return value is serialized to JSON automatically (via Jackson). |
| `@RequestMapping("/path")` | All controllers | Sets the base URL path for all endpoints in the controller. Example: `@RequestMapping("/order")` means all endpoints start with `/order`. |
| `@GetMapping("/path")` | GET endpoints | Maps HTTP GET requests to this method. Example: `@GetMapping("/my")` handles `GET /order/my`. |
| `@PostMapping("/path")` | POST endpoints | Maps HTTP POST requests. Example: `@PostMapping("/create")` handles `POST /order/create`. |
| `@PutMapping("/path")` | PUT endpoints | Maps HTTP PUT requests. Example: `@PutMapping("/assign/{id}")` handles `PUT /order/assign/5`. |
| `@DeleteMapping("/path")` | DELETE endpoints | Maps HTTP DELETE requests. Example: `@DeleteMapping("/delete/{id}")` handles `DELETE /order/delete/5`. |
| `@RequestBody` | Controller methods | Deserializes the HTTP request body (JSON) into a Java object using Jackson. Example: `@RequestBody OrderRequest req` converts `{"carId":1}` to an OrderRequest object. |
| `@RequestParam` | Controller methods | Binds a query parameter. Example: `@RequestParam String email` extracts `email` from `?email=sarthak@gmail.com`. |
| `@PathVariable` | Controller methods | Extracts a value from the URL path. Example: `@PathVariable Long id` extracts `5` from `/order/assign/5`. |
| `@RequestHeader` | Controller methods | Reads an HTTP header value. Example: `@RequestHeader("X-User-Email") String email` reads the header set by the Gateway. |

## JPA/Database Annotations

| Annotation | Location | What It Does |
|-----------|----------|-------------|
| `@Entity` | User, Order, Car, Review, Invoice, Notification, ServicePlan | Marks the class as a JPA entity mapped to a database table. Hibernate creates the table automatically. |
| `@Table(name="x")` | Order (`@Table(name="orders")`) | Overrides the default table name. Used for `Order` because `order` is a SQL reserved keyword. |
| `@Id` | All entity ID fields | Marks the field as the primary key of the entity. |
| `@GeneratedValue(strategy=IDENTITY)` | All entity ID fields | The database auto-generates the ID using `AUTO_INCREMENT`. Each new record gets the next sequential number. |
| `@Column(unique=true)` | User.email | Adds a UNIQUE constraint on the column. Two users cannot have the same email. |
| `@Enumerated(EnumType.STRING)` | User.role, Order.status | Stores the enum as its string name (e.g., "CUSTOMER") instead of its ordinal (e.g., 0). STRING is safer because reordering enums doesn't break existing data. |

## Lombok Annotations

| Annotation | Location | What It Does |
|-----------|----------|-------------|
| `@Data` | All entities, DTOs | Generates `getters`, `setters`, `toString()`, `equals()`, `hashCode()` at compile time. Eliminates 100+ lines of boilerplate per class. |
| `@NoArgsConstructor` | Entities, DTOs | Generates empty constructor: `public User() {}`. Required by JPA (creates objects via reflection) and Jackson (JSON deserialization). |
| `@AllArgsConstructor` | AuthResponse, Payment | Generates constructor with ALL fields: `new AuthResponse(msg, token, email, role)`. |
| `@RequiredArgsConstructor` | All controllers, services | Generates constructor for `final` fields only. Spring uses this for dependency injection. `private final OrderRepository repo;` gets injected automatically. |
| `@Slf4j` | NotificationListener | Generates `private static final Logger log = LoggerFactory.getLogger(ClassName.class);`. Allows `log.info("message")`. |
| `@Builder` | (not used, but good to know) | Generates a builder pattern: `User.builder().name("X").email("Y").build()`. |

## Spring Cloud Annotations

| Annotation | Location | What It Does |
|-----------|----------|-------------|
| `@EnableEurekaServer` | EurekaServerApplication | Activates the Eureka service registry. Transforms the app into a discovery server. |
| `@EnableConfigServer` | ConfigServerApplication | Activates the Config Server. Serves configuration from a Git repository. |
| `@EnableDiscoveryClient` | UserServiceApplication | Enables Eureka client registration. Auto-registers the service with Eureka on startup. (Auto-enabled in Spring Boot 3.x if eureka-client is on classpath.) |
| `@EnableFeignClients` | Order, Admin, Rating, Invoice apps | Scans for `@FeignClient` interfaces and creates proxy implementations. Without this, Feign clients are NOT registered. |
| `@FeignClient(name="X")` | UserClient, CarClient, PaymentClient | Declares a Feign client interface. `name` matches the Eureka service name. Spring generates the HTTP client implementation at runtime. |
| `@RefreshScope` | JwtService in multiple services | Marks a bean for dynamic refresh via Spring Cloud Bus. When `/actuator/busrefresh` is called, this bean is destroyed and recreated with new config values. |

## Resilience4j Annotations

| Annotation | Location | What It Does |
|-----------|----------|-------------|
| `@CircuitBreaker(name="x", fallbackMethod="y")` | OrderService, AdminService, ReviewService, InvoiceService | Wraps the method with a circuit breaker. If the failure rate exceeds the threshold, subsequent calls go directly to the fallback method. |
| `@Retry(name="x")` | OrderService, AdminService, InvoiceService | Wraps the method with retry logic. Failed calls are automatically retried (default: 3 times with 1s delay). |

## RabbitMQ Annotations

| Annotation | Location | What It Does |
|-----------|----------|-------------|
| `@RabbitListener(queues="x")` | NotificationListener | Subscribes to a RabbitMQ queue. When a message arrives, Spring deserializes it and calls the annotated method. After successful return, the message is acknowledged. |

## Security Annotations

| Annotation | Location | What It Does |
|-----------|----------|-------------|
| `@EnableWebSecurity` | (implicit in Spring Boot) | Enables Spring Security's web security support. Auto-enabled when `spring-boot-starter-security` is on classpath. |

---

---

# ============================================================
# PART E: WHITEBOARD DRAWING GUIDE
# ============================================================

> When the interviewer says "Draw your architecture on the whiteboard," follow this 2-minute guide.

---

## Step 1: Draw the Client (10 seconds)

```
Draw a box on the LEFT:
┌──────────┐
│  CLIENT  │
│ (Mobile/ │
│  Browser)│
└──────────┘
```

Say: *"The client can be a mobile app, web browser, or Postman."*

## Step 2: Draw the API Gateway (15 seconds)

```
Draw a large box in the CENTER:
┌──────────────────────────────┐
│        API GATEWAY           │
│  - JWT Authentication        │
│  - Rate Limiting (Redis)     │
│  - Routing (Eureka)          │
│  - Load Balancing            │
└──────────────────────────────┘

Draw an arrow from Client → Gateway
```

Say: *"All requests go through the API Gateway. It validates JWT, rate limits via Redis, and routes to the correct service using Eureka."*

## Step 3: Draw the Services (30 seconds)

```
Draw 8 boxes on the RIGHT in two rows:

┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐
│ USER │ │ORDER │ │ PAY  │ │ CAR  │
└──────┘ └──────┘ └──────┘ └──────┘
┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐
│ADMIN │ │RATING│ │ INV  │ │NOTIF │
└──────┘ └──────┘ └──────┘ └──────┘

Draw arrows from Gateway → each service
```

Say: *"8 business services, each with its own database. They're independently deployable and scalable."*

## Step 4: Draw Infrastructure (30 seconds)

```
Draw boxes ABOVE or BELOW:

┌────────┐  ┌────────┐  ┌──────────┐
│ EUREKA │  │ CONFIG │  │ RabbitMQ │
│ (Disc.)│  │(Config)│  │ (Events) │
└────────┘  └────────┘  └──────────┘

┌────────┐  ┌────────┐  ┌──────────┐
│ Redis  │  │ Zipkin │  │Prometheus│
│ (Rate) │  │(Trace) │  │+ Grafana │
└────────┘  └────────┘  └──────────┘
```

Say: *"Infrastructure components: Eureka for service discovery, Config Server for centralized config, RabbitMQ for async messaging, Redis for rate limiting, Zipkin for distributed tracing, Prometheus and Grafana for monitoring."*

## Step 5: Draw Communication Lines (20 seconds)

```
Draw:
1. Solid arrows: ORDER → USER, ORDER → CAR, ORDER → PAYMENT (Feign sync)
2. Dashed arrow: ORDER ···→ RabbitMQ ···→ NOTIFICATION (async)
3. All services → Eureka (registration arrows)
```

Say: *"Solid lines are synchronous Feign calls with circuit breakers. The dashed line is asynchronous RabbitMQ messaging for notifications."*

## Step 6: Draw Auth Flow (15 seconds)

```
Write next to the Gateway:
Client → JWT Token → Gateway validates → X-User-Email header → Services trust
```

Say: *"JWT validated once at the Gateway. Services trust the forwarded headers. This is the Gateway Trust Pattern."*

**Total time: ~2 minutes. Clean, impressive, comprehensive.**

---

---

# ============================================================
# PART F: COMPANIES USING THE SAME TECHNOLOGY STACK
# ============================================================

> Drop these names during your interview to show industry awareness.

---

## Technology → Real Companies Using It

| Technology | Companies Using It | What They Use It For |
|-----------|-------------------|---------------------|
| **Microservices** | Netflix, Amazon, Uber, Spotify, Airbnb | All have 500+ microservices |
| **Spring Boot** | Netflix, Alibaba, Intuit, JPMorgan Chase | Backend services |
| **Netflix Eureka** | Netflix (created it), Flipkart, Swiggy | Service discovery at scale |
| **Spring Cloud Gateway** | Alibaba, many Spring-based startups | API gateway |
| **RabbitMQ** | Reddit, Mozilla, Uber, Indeed, Bloomberg | Message broker for async tasks |
| **Redis** | Twitter, GitHub, Pinterest, Snapchat, StackOverflow | Caching, rate limiting, sessions |
| **Resilience4j** | Deutsche Telekom (created it), many Spring apps | Circuit breaking |
| **JWT** | Auth0, Okta, every modern web app | Stateless authentication |
| **Docker** | Every modern tech company | Container runtime |
| **Kubernetes** | Google (created it), Spotify, Airbnb, Pinterest | Container orchestration |
| **Prometheus + Grafana** | SoundCloud (created Prometheus), DigitalOcean, GitLab | Monitoring |
| **Zipkin** | Twitter (created it), many Spring Cloud apps | Distributed tracing |
| **GitHub Actions** | Every GitHub-hosted project | CI/CD |
| **MySQL** | Facebook, Twitter, YouTube, Netflix | Relational database |

## How to Use This in an Interview

*"I chose Netflix Eureka for service discovery because it's battle-tested - Netflix runs hundreds of microservices with it in production. Similarly, RabbitMQ is used by companies like Reddit and Bloomberg for reliable async messaging. The Resilience4j library I used was created by Deutsche Telekom for their production systems. These aren't experimental technologies - they're proven at massive scale."*

---

---

# ============================================================
# PART G: MAVEN DEPENDENCY CHEAT SHEET
# ============================================================

> Explains what every dependency in your pom.xml files does.

---

## Core Dependencies

| Dependency | Artifact ID | What It Does |
|-----------|-------------|-------------|
| **Spring Web** | `spring-boot-starter-web` | Embedded Tomcat + Spring MVC + Jackson JSON serialization. Makes REST APIs possible. |
| **Spring Data JPA** | `spring-boot-starter-data-jpa` | Hibernate ORM + JPA + Spring Data repositories. Auto-generates CRUD operations from interface definitions. |
| **Spring Security** | `spring-boot-starter-security` | Authentication and authorization framework. Provides BCryptPasswordEncoder, SecurityFilterChain, CSRF protection. Used only in USER-SERVICE. |
| **Spring Actuator** | `spring-boot-starter-actuator` | Exposes `/health`, `/info`, `/metrics`, `/prometheus` endpoints. Essential for monitoring and K8s health probes. |
| **Spring Validation** | `spring-boot-starter-validation` | Bean validation with `@Valid`, `@NotNull`, `@Size`. Used in ORDER-SERVICE. |
| **Spring DevTools** | `spring-boot-devtools` | Auto-restart on code changes during development. NOT included in production builds. |

## Spring Cloud Dependencies

| Dependency | Artifact ID | What It Does |
|-----------|-------------|-------------|
| **Eureka Client** | `spring-cloud-starter-netflix-eureka-client` | Registers the service with Eureka Server and enables service discovery for Feign/LoadBalancer. |
| **Eureka Server** | `spring-cloud-starter-netflix-eureka-server` | Runs the Eureka registry. Only in eureka-server. |
| **Config Client** | `spring-cloud-starter-config` | Fetches configuration from Config Server on startup. |
| **Config Server** | `spring-cloud-config-server` | Serves configuration from a Git repository. Only in config-server. |
| **Gateway** | `spring-cloud-starter-gateway` | Spring Cloud Gateway with WebFlux + Netty. Only in api-gateway. |
| **OpenFeign** | `spring-cloud-starter-openfeign` | Declarative HTTP client for inter-service calls. |
| **LoadBalancer** | `spring-cloud-starter-loadbalancer` | Client-side load balancing. Auto-included with Eureka/Feign. |
| **Cloud Bus AMQP** | `spring-cloud-starter-bus-amqp` | Spring Cloud Bus via RabbitMQ for dynamic config refresh. |
| **Circuit Breaker** | `spring-cloud-starter-circuitbreaker-resilience4j` | Resilience4j integration with Spring Cloud. |

## Data & Messaging Dependencies

| Dependency | Artifact ID | What It Does |
|-----------|-------------|-------------|
| **MySQL Connector** | `mysql-connector-j` | JDBC driver for MySQL. Allows Java to connect to MySQL databases. |
| **RabbitMQ (AMQP)** | `spring-boot-starter-amqp` | RabbitMQ client + Spring AMQP. Enables publish/subscribe messaging with `@RabbitListener`. |
| **Redis Reactive** | `spring-boot-starter-data-redis-reactive` | Reactive Redis client. Used by API Gateway for rate limiting (non-blocking). |

## Observability Dependencies

| Dependency | Artifact ID | What It Does |
|-----------|-------------|-------------|
| **Micrometer Brave** | `micrometer-tracing-bridge-brave` | Distributed tracing instrumentation. Auto-generates trace/span IDs and propagates them via B3 headers. |
| **Zipkin Reporter** | `zipkin-reporter-brave` | Reports spans to Zipkin server. Works with Micrometer Brave to send timing data. |
| **Prometheus Registry** | `micrometer-registry-prometheus` | Exposes metrics in Prometheus format at `/actuator/prometheus`. |

## Security & JWT Dependencies

| Dependency | Artifact ID | What It Does |
|-----------|-------------|-------------|
| **JJWT API** | `jjwt-api` | JWT creation and parsing API. Provides `Jwts.builder()` and `Jwts.parserBuilder()`. |
| **JJWT Impl** | `jjwt-impl` | JWT implementation. Does the actual cryptographic operations (HMAC-SHA256 signing/verification). |
| **JJWT Jackson** | `jjwt-jackson` | Jackson integration for JWT. Serializes/deserializes JWT claims as JSON. |

## Other Dependencies

| Dependency | Artifact ID | What It Does |
|-----------|-------------|-------------|
| **Lombok** | `lombok` | Compile-time code generation. `@Data`, `@RequiredArgsConstructor`, `@Slf4j` etc. Eliminates boilerplate. |
| **OpenPDF** | `com.lowagie:itext:2.1.7` | PDF generation library. Used in INVOICE-SERVICE to create tax invoices. |
| **Jackson Databind** | `jackson-databind` | JSON serialization/deserialization. Auto-included with spring-boot-starter-web but explicitly added in some services. |
| **Resilience4j Spring Boot 3** | `resilience4j-spring-boot3` | Auto-configuration for Resilience4j in Spring Boot 3. Enables `@CircuitBreaker`, `@Retry` annotations. |

---

---

# ============================================================
# PART H: INTERVIEW DO'S AND DON'TS
# ============================================================

---

## DO's (Things That Impress Interviewers)

### 1. Use Technical Vocabulary Naturally
```
WEAK:  "Services talk to each other using HTTP"
STRONG: "Services communicate synchronously via OpenFeign with Eureka-based 
         service discovery and client-side load balancing"
```

### 2. Explain Trade-offs, Not Just Choices
```
WEAK:  "I used RabbitMQ for messaging"
STRONG: "I chose RabbitMQ over Kafka because RabbitMQ is ideal for task queues 
         with complex routing patterns. Kafka would be overkill for our 
         notification use case. Kafka shines for high-throughput event 
         streaming and log aggregation, which we don't need."
```

### 3. Give Specific Numbers
```
WEAK:  "The system has many services"
STRONG: "The system has 11 services, 17 Docker containers, 224 source files, 
         and the Docker Compose stack starts in about 3 minutes"
```

### 4. Explain the "Why" Before the "What"
```
WEAK:  "I added a circuit breaker to ORDER-SERVICE"
STRONG: "When USER-SERVICE goes down, without protection ORDER-SERVICE threads 
         block waiting for responses, the thread pool exhausts, and the entire 
         service crashes. I added Resilience4j circuit breakers that detect the 
         failure, open the circuit, and return a fallback - preventing the 
         cascading failure entirely."
```

### 5. Draw Architecture When Explaining
- Always offer to draw on the whiteboard
- It shows you truly understand the system, not just memorized answers
- Use the whiteboard guide from Part E

### 6. Mention Monitoring and Observability
- Most candidates skip this
- Saying "I have Prometheus metrics, Zipkin tracing, and Grafana dashboards" puts you ahead of 90% of candidates

### 7. Show Awareness of Production Concerns
```
"In production, I would add:
 - mTLS via Istio for zero-trust internal communication
 - Flyway for database migration versioning
 - Horizontal Pod Autoscaler based on Prometheus metrics
 - Centralized logging with ELK stack"
```

---

## DON'TS (Common Mistakes to Avoid)

### 1. Don't Say "I Just Followed a Tutorial"
```
WRONG: "I followed a YouTube tutorial to build this"
RIGHT: "I designed the architecture based on Spring Cloud best practices 
        and implemented it service by service. I faced several challenges 
        like 403 Forbidden errors and Feign header propagation issues 
        that I debugged and resolved."
```

### 2. Don't Be Unable to Explain Your Own Code
```
WRONG: "I'm not sure why I used @RefreshScope there"
RIGHT: "@RefreshScope marks the bean for dynamic config refresh via 
        Spring Cloud Bus. When I change jwt.secret in the Config Server 
        and trigger /actuator/busrefresh, this bean is recreated with 
        the new value - zero downtime."
```

### 3. Don't Ignore Error Handling Questions
```
WRONG: "It just works"
RIGHT: "If USER-SERVICE is down, the circuit breaker opens after 50% 
        failure rate. The fallback returns a default user. I also have 
        retry logic that attempts 3 times before recording a failure. 
        For async flows, RabbitMQ queues messages until the consumer 
        is available."
```

### 4. Don't Claim Perfection - Acknowledge Improvements
```
WRONG: "My project is perfect"
RIGHT: "It's production-ready for the current scope. For enterprise 
        scale, I'd add OAuth2 with Keycloak, the Saga pattern for 
        distributed transactions, and Redis caching."
```

### 5. Don't Use Jargon You Can't Explain
```
If you say "eventual consistency" - be ready to explain what it means.
If you say "backpressure" - be ready to give a concrete example.
If you say "12-Factor App" - be ready to name at least 5 factors.
```

---

---

# ============================================================
# PART I: HR/BEHAVIORAL QUESTIONS MAPPED TO THIS PROJECT
# ============================================================

> Interviewers often ask behavioral questions. Map every answer to your project.

---

### BQ1: "Tell me about a time you solved a complex technical problem."

*"When building the Car Wash System, I faced intermittent 403 Forbidden errors across services. The debugging was complex because the error happened only on certain API calls, not all.*

*I systematically traced the request flow: Gateway logs showed the JWT was valid, but ORDER-SERVICE logs showed the request was being blocked. I discovered that Spring Security's default configuration in USER-SERVICE was applying to internal Feign calls between services, not just external client requests.*

*The root cause was that Feign creates new HTTP requests that don't carry Spring Security's authentication context. I designed the Gateway Trust Pattern - validate JWT once at the Gateway, forward user identity as trusted headers, and configure services to permit all internal traffic. This required changes across all 8 services - their SecurityConfigs, FeignConfigs, and controllers. The fix was architectural, not just a code patch."*

---

### BQ2: "Tell me about a time you had to make a difficult technical decision."

*"I had to choose between validating JWT in every service vs only at the Gateway. Both approaches are valid in the industry.*

*Validating everywhere provides defense-in-depth but means every service needs the JWT library, the secret key, and validation logic. It adds latency (crypto operations on every service) and complexity (duplicated code).*

*Validating only at the Gateway is simpler and faster but requires trusting the internal network. After analyzing our deployment model (Docker network / Kubernetes ClusterIP where services aren't publicly accessible), I chose the Gateway Trust Pattern. Services are never exposed directly to the internet, so internal traffic is inherently trusted.*

*The decision saved significant complexity - 7 services didn't need JWT dependencies, and we eliminated redundant cryptographic operations on every request."*

---

### BQ3: "Tell me about a time you worked with a new technology you hadn't used before."

*"RabbitMQ was completely new to me. I needed to implement asynchronous notifications for order events.*

*I started by understanding the core concepts - exchanges, queues, bindings, routing keys. Then I implemented a minimal producer-consumer setup and tested it in isolation. The first challenge was message deserialization - the producer used JacksonJsonMessageConverter but the consumer expected Jackson2JsonMessageConverter. Messages were stuck in the queue because deserialization failed silently.*

*I debugged by checking RabbitMQ's management UI, which showed messages in the queue with no consumers. I examined the message format, realized the converter mismatch, standardized both sides, and verified end-to-end delivery. The experience taught me that in distributed systems, debugging requires checking the middleware (RabbitMQ UI), not just application logs."*

---

### BQ4: "Describe a project you're most proud of."

*"The Car Wash System. It's not just a CRUD app - it demonstrates real distributed systems engineering. I built 11 independently deployable services, implemented fault tolerance with circuit breakers, event-driven architecture with RabbitMQ, distributed tracing with Zipkin, and a complete DevOps pipeline with Docker, Kubernetes, and CI/CD.*

*What I'm most proud of is the resilience design. When I stop USER-SERVICE, the system doesn't crash. The circuit breaker opens, fallbacks kick in, and other services continue working. When I restart USER-SERVICE, the circuit automatically recovers. That's production-grade behavior.*

*I also wrote a 5,689-line README that serves as both documentation and a learning guide. The project is on GitHub with Docker Compose - anyone can clone it and run the entire 17-container system with one command."*

---

### BQ5: "Tell me about a time you had to debug a distributed system."

*"When Feign calls between ORDER-SERVICE and USER-SERVICE started failing silently - the order would be created but with incorrect user data.*

*I used three debugging tools: First, I checked Eureka to confirm USER-SERVICE was registered. Second, I checked Zipkin to trace the exact request - it showed ORDER-SERVICE was calling USER-SERVICE but the response was a fallback (user id=0). Third, I checked ORDER-SERVICE logs which showed the circuit breaker was open.*

*The root cause was that USER-SERVICE had a startup delay, and during that window, ORDER-SERVICE's retries exhausted and the circuit opened. The fix was twofold: I added a startupProbe in Kubernetes (allowing 200 seconds for Spring Boot startup) and configured the circuit breaker's wait-duration-in-open-state to 60 seconds instead of the default 30.*

*This experience taught me that in microservices, you need observability tools (Eureka, Zipkin, logs) to debug effectively - you can't just step through code with a debugger."*

---

### BQ6: "How do you handle pressure/tight deadlines?"

*"When building the Docker Compose setup, I needed all 17 containers to start correctly with proper dependency ordering. The initial setup had race conditions - services started before MySQL was ready, causing connection refused errors.*

*Instead of panicking, I broke the problem into layers: infrastructure first (MySQL, RabbitMQ, Redis), then discovery (Eureka, Config Server), then Gateway, then services. I added health checks to every container and used depends_on with condition: service_healthy. I also added start_period to allow Spring Boot's 30-40 second startup time.*

*By tackling it systematically layer by layer, I had the full stack running reliably within a day."*

---

### BQ7: "How do you stay updated with technology?"

*"I follow Spring's official blog for new releases, read Baeldung for Spring Boot tutorials, and watch conference talks from SpringOne. For microservices patterns, I reference Sam Newman's 'Building Microservices' and Martin Fowler's articles. For this project specifically, I studied the Spring Cloud documentation, Netflix OSS architecture blog posts, and the 12-Factor App methodology to ensure I was following industry best practices."*

---

### BQ8: "What would your ideal team look like for this project?"

*"In a production setting, I'd organize teams around services - a User/Auth team, an Orders team, a Payments team, and an Infrastructure team. Each team owns their service(s), their database, their CI/CD pipeline, and their on-call rotation.*

*The Infrastructure team manages shared components: API Gateway, Eureka, Config Server, RabbitMQ, Redis, and the monitoring stack. They also own the Kubernetes platform and CI/CD templates.*

*This aligns with Conway's Law - the architecture mirrors the team structure. Each team can deploy independently, use their preferred tools within the service boundary, and own their SLA."*

---

---

# ============================================================
# PART J: FINAL QUICK REVISION CHEAT SHEET
# ============================================================

> Read this 5 minutes before your interview.

---

## 1-Line Project Description
> "Production-grade microservices Car Wash System - 11 services, Spring Boot 3.x, Spring Cloud, RabbitMQ, Docker, K8s, CI/CD"

## 5 Services to Remember by Heart
1. **API Gateway** → JWT + Rate Limiting + Routing
2. **ORDER-SERVICE** → Core logic + Feign + RabbitMQ + Circuit Breaker
3. **USER-SERVICE** → Auth + JWT Generation + BCrypt
4. **NOTIFICATION-SERVICE** → @RabbitListener + Async Consumer
5. **EUREKA-SERVER** → Service Registry + Heartbeat + Self-Preservation

## 5 Keywords for Every Answer
1. **Stateless** (JWT, no sessions)
2. **Decoupled** (services independent, async messaging)
3. **Resilient** (circuit breaker, retry, fallback)
4. **Observable** (Zipkin tracing, Prometheus metrics, Grafana)
5. **Containerized** (Docker Compose, Kubernetes, CI/CD)

## 3 Challenges to Mention
1. 403 Forbidden → Gateway Trust Pattern
2. Feign Header Loss → FeignConfig RequestInterceptor
3. Cascading Failures → Resilience4j Circuit Breaker

## 3 Trade-offs to Mention
1. Monolith vs Microservices → Chose microservices for scaling & fault isolation
2. Sync vs Async → Feign for immediate needs, RabbitMQ for fire-and-forget
3. JWT per service vs Gateway only → Gateway Trust Pattern for simplicity & performance

## 3 Future Improvements
1. OAuth2 with Keycloak
2. Saga Pattern for distributed transactions
3. Redis Caching for performance

---

## Repository

**Source Code:** [github.com/sarthakpawar0912/On-Demand-Car-Wash-Backend](https://github.com/sarthakpawar0912/On-Demand-Car-Wash-Backend)

---

> *Built with Java 21, Spring Boot 3.x, and Spring Cloud 2023.x by [Sarthak Pawar](https://github.com/sarthakpawar0912)*
