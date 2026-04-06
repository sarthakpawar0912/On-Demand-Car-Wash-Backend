# On-Demand Car Wash System - Microservices Backend

> A production-grade, enterprise-level microservices-based backend system for an On-Demand Car Wash platform, built with **Java 21**, **Spring Boot 3.x**, and **Spring Cloud 2023.x**. This project demonstrates real-world distributed systems architecture including service discovery, centralized configuration, API gateway pattern, event-driven communication, distributed tracing, circuit breakers, rate limiting, JWT authentication, containerization with Docker, orchestration with Kubernetes, and CI/CD with GitHub Actions.

---

## Table of Contents

1. [Project Overview](#1-project-overview)
   - [What is This Project](#11-what-is-this-project)
   - [Real-World Problem It Solves](#12-real-world-problem-it-solves)
   - [Why Microservices Architecture](#13-why-microservices-architecture)
   - [Technology Stack](#14-technology-stack)
2. [Complete Architecture Explanation](#2-complete-architecture-explanation)
   - [High-Level Architecture](#21-high-level-architecture)
   - [Request Flow](#22-request-flow-client--gateway--services)
   - [Synchronous vs Asynchronous Communication](#23-synchronous-vs-asynchronous-communication)
   - [Service-to-Service Communication](#24-service-to-service-communication)
   - [Service Registry Flow](#25-service-registry-flow)
3. [Core Concepts - Deep Explanation](#3-core-concepts---deep-explanation)
   - [What is Microservices Architecture](#31-what-is-microservices-architecture)
   - [Monolith vs Microservices](#32-monolith-vs-microservices---deep-comparison)
   - [API Gateway Pattern](#33-api-gateway-pattern)
   - [Service Discovery with Eureka](#34-service-discovery-with-eureka)
   - [Centralized Configuration with Config Server](#35-centralized-configuration-with-config-server)
   - [Inter-Service Communication with OpenFeign](#36-inter-service-communication-with-openfeign)
   - [Event-Driven Architecture with RabbitMQ](#37-event-driven-architecture-with-rabbitmq)
   - [Distributed Tracing with Zipkin](#38-distributed-tracing-with-zipkin)
   - [Rate Limiting with Redis](#39-rate-limiting-with-redis)
   - [Circuit Breaker, Retry, Timeout with Resilience4j](#310-circuit-breaker-retry-timeout-with-resilience4j)
   - [JWT Authentication](#311-jwt-authentication---deep-dive)
   - [Security in Microservices](#312-security-in-microservices)
4. [Service-by-Service Deep Dive](#4-service-by-service-deep-dive)
   - [USER-SERVICE](#41-user-service)
   - [ORDER-SERVICE](#42-order-service)
   - [PAYMENT-SERVICE](#43-payment-service)
   - [CAR-SERVICE](#44-car-service)
   - [ADMIN-SERVICE](#45-admin-service)
   - [RATING-SERVICE](#46-rating-service)
   - [INVOICE-SERVICE](#47-invoice-service)
   - [NOTIFICATION-SERVICE](#48-notification-service)
5. [API Gateway Deep Dive](#5-api-gateway-deep-dive)
   - [Routing](#51-routing)
   - [JWT Authentication Filter](#52-jwt-authentication-filter)
   - [Header Forwarding](#53-header-forwarding)
   - [Rate Limiting with Redis](#54-rate-limiting-with-redis)
   - [Common Gateway Issues and Solutions](#55-common-gateway-issues-and-solutions)
6. [Authentication Flow - Very Detailed](#6-authentication-flow---very-detailed)
   - [Registration Flow](#61-registration-flow)
   - [Login Flow](#62-login-flow)
   - [Token Structure](#63-token-structure)
   - [Token Validation at Gateway](#64-token-validation-at-gateway)
   - [Header Propagation to Services](#65-header-propagation-to-services)
   - [How Services Trust the Request](#66-how-services-trust-the-request)
7. [Resilience and Fault Tolerance](#7-resilience-and-fault-tolerance)
   - [Circuit Breaker Usage](#71-circuit-breaker-usage)
   - [Retry Logic](#72-retry-logic)
   - [Timeout Handling](#73-timeout-handling)
   - [Fallback Methods](#74-fallback-methods)
8. [Event-Driven Flow with RabbitMQ](#8-event-driven-flow-with-rabbitmq)
   - [Order to Notification Flow](#81-order-to-notification-flow)
   - [How Events are Published and Consumed](#82-how-events-are-published-and-consumed)
   - [Why Async is Used](#83-why-async-is-used)
9. [Rate Limiting with Redis](#9-rate-limiting-with-redis)
   - [Why Rate Limiting is Needed](#91-why-rate-limiting-is-needed)
   - [How Redis Works with Gateway](#92-how-redis-works-with-gateway)
   - [Configuration Explanation](#93-configuration-explanation)
10. [Distributed Tracing with Zipkin](#10-distributed-tracing-with-zipkin)
    - [How Tracing Works](#101-how-tracing-works)
    - [How Requests are Tracked Across Services](#102-how-requests-are-tracked-across-services)
    - [Setting Up Zipkin](#103-setting-up-zipkin)
11. [Monitoring with Prometheus and Grafana](#11-monitoring-with-prometheus-and-grafana)
    - [Prometheus Configuration](#111-prometheus-configuration)
    - [Grafana Dashboards](#112-grafana-dashboards)
    - [Metrics Exposed](#113-metrics-exposed)
12. [Complete Request Flow Walkthrough](#12-complete-request-flow-walkthrough)
    - [Scenario: User Registers, Creates Order, Pays, Gets Notification](#121-scenario-user-registers-creates-order-pays-gets-notification)
13. [Common Errors and Debugging Guide](#13-common-errors-and-debugging-guide)
    - [403 Forbidden Issues](#131-403-forbidden-issues)
    - [JWT Issues](#132-jwt-issues)
    - [Feign Header Forwarding Issues](#133-feign-header-forwarding-issues)
    - [Gateway Errors](#134-gateway-errors)
    - [Redis Rate Limiter Errors](#135-redis-rate-limiter-errors)
    - [RabbitMQ Connection Issues](#136-rabbitmq-connection-issues)
    - [Step-by-Step Debugging Guide](#137-step-by-step-debugging-guide)
14. [Setup and Run Guide](#14-setup-and-run-guide)
    - [Prerequisites](#141-prerequisites)
    - [Docker Setup](#142-docker-setup)
    - [Running with Docker Compose](#143-running-with-docker-compose-recommended)
    - [Running Locally](#144-running-locally-without-docker-compose)
    - [Order of Startup](#145-order-of-startup)
    - [Verifying the Setup](#146-verifying-the-setup)
15. [Docker and Containerization](#15-docker-and-containerization)
    - [Docker Compose Architecture](#151-docker-compose-architecture)
    - [Service Dependencies](#152-service-dependencies)
    - [Health Checks](#153-health-checks)
16. [Kubernetes Deployment](#16-kubernetes-deployment)
    - [Namespace](#161-namespace)
    - [Infrastructure Components](#162-infrastructure-components)
    - [Service Discovery in K8s](#163-service-discovery-in-k8s)
    - [Deployment Order](#164-deployment-order)
    - [Manifest Structure](#165-manifest-structure)
17. [CI/CD with GitHub Actions](#17-cicd-with-github-actions)
    - [Pipeline Overview](#171-pipeline-overview)
    - [Change Detection](#172-change-detection)
    - [Build and Push](#173-build-and-push)
    - [Kubernetes Deployment](#174-kubernetes-deployment)
18. [Project Structure](#18-project-structure)
    - [Overall Folder Structure](#181-overall-folder-structure)
    - [Individual Service Structure](#182-individual-service-structure)
    - [Naming Conventions](#183-naming-conventions)
19. [API Reference](#19-api-reference)
    - [Authentication APIs](#191-authentication-apis)
    - [User APIs](#192-user-apis)
    - [Car APIs](#193-car-apis)
    - [Order APIs](#194-order-apis)
    - [Payment APIs](#195-payment-apis)
    - [Rating APIs](#196-rating-apis)
    - [Invoice APIs](#197-invoice-apis)
    - [Admin APIs](#198-admin-apis)
    - [Notification APIs](#199-notification-apis)
20. [Best Practices Used](#20-best-practices-used)
21. [Future Improvements](#21-future-improvements)
22. [Contributing](#22-contributing)
23. [License](#23-license)

---

## 1. Project Overview

### 1.1 What is This Project

The **On-Demand Car Wash System** is a full-stack backend platform that allows customers to book car wash services on demand. Think of it like an "Uber for car washing" - customers register, add their cars, place wash orders, make payments, receive invoices, and leave reviews. Admins manage service plans and monitor the platform. Washers get assigned to orders and complete the work.

The entire backend is built using a **microservices architecture**, meaning the application is split into multiple small, independent services that communicate with each other over the network. Each service is responsible for one specific domain of the business logic:

- **User Service** handles authentication (login/register) and user management
- **Car Service** manages customer vehicles
- **Order Service** handles the core business logic of creating and managing wash orders
- **Payment Service** processes payments
- **Invoice Service** generates tax invoices and PDFs
- **Rating Service** manages customer reviews
- **Admin Service** provides administrative features
- **Notification Service** sends email/push notifications asynchronously

All these services are tied together by infrastructure components:
- **API Gateway** acts as the single entry point for all client requests
- **Eureka Server** enables services to discover each other dynamically
- **Config Server** provides centralized configuration management
- **RabbitMQ** enables asynchronous event-driven communication
- **Redis** provides rate limiting at the gateway
- **Zipkin** enables distributed tracing across services
- **Prometheus + Grafana** provide monitoring and dashboards

### 1.2 Real-World Problem It Solves

In the real world, car wash businesses face several challenges:

1. **Manual Scheduling**: Customers call or visit to book appointments, leading to inefficiency
2. **No Real-Time Tracking**: Customers cannot track the status of their service
3. **Payment Friction**: Cash-only payments, no digital invoicing
4. **No Feedback Loop**: No system for reviews or ratings
5. **Scalability Issues**: A traditional monolithic application cannot handle growing demand

This system solves all these problems by providing a digital platform where:
- Customers can register, add cars, and place orders through a REST API
- Orders are tracked through states: `CREATED` → `ASSIGNED` → `COMPLETED`
- Payments are processed digitally
- GST-compliant PDF invoices are generated automatically
- Customers can rate and review their experience
- Admins can manage service plans, view reports, and monitor all users
- The system scales horizontally - each service can be scaled independently

### 1.3 Why Microservices Architecture

A monolithic architecture would put all the code (users, orders, payments, notifications, etc.) into a single application. While simpler to build initially, it creates serious problems at scale:

**Problem 1: Tight Coupling**
In a monolith, if the payment module has a bug and crashes, the entire application goes down - including user registration, order management, and everything else. In our microservices architecture, if PAYMENT-SERVICE crashes, users can still register, create orders, and browse. The circuit breaker kicks in and provides a graceful fallback.

**Problem 2: Independent Scaling**
During peak hours (say, Sunday mornings), the ORDER-SERVICE might get 100x more traffic than the ADMIN-SERVICE. In a monolith, you'd have to scale the entire application. With microservices, you scale only ORDER-SERVICE (e.g., from 1 instance to 10 instances) while keeping ADMIN-SERVICE at 1 instance. This saves significant cloud infrastructure costs.

**Problem 3: Technology Lock-in**
In a monolith, the entire application must use the same language, framework, and database. With microservices, each service can use whatever technology fits best. In our case, all services use Spring Boot and MySQL, but we could easily rewrite NOTIFICATION-SERVICE in Node.js or switch PAYMENT-SERVICE to PostgreSQL without affecting other services.

**Problem 4: Deployment Independence**
In a monolith, every change requires redeploying the entire application. If a developer adds a new field to the user profile, the entire application (including payments, orders, etc.) must be redeployed. With microservices, you deploy only USER-SERVICE. Other services keep running undisturbed.

**Problem 5: Team Autonomy**
Different teams can own different services. The payments team can work on PAYMENT-SERVICE independently, with their own release cycle, without coordinating with every other team for every deployment.

### 1.4 Technology Stack

| Category | Technology | Version | Purpose |
|----------|-----------|---------|---------|
| Language | Java | 21 | Primary programming language |
| Framework | Spring Boot | 3.3.5 | Application framework |
| Cloud | Spring Cloud | 2023.0.3 | Microservices infrastructure |
| Gateway | Spring Cloud Gateway | - | API Gateway (reactive) |
| Discovery | Netflix Eureka | - | Service registry and discovery |
| Config | Spring Cloud Config | - | Centralized configuration |
| Communication | OpenFeign | - | Declarative REST client |
| Messaging | RabbitMQ | 3-management | Async event-driven messaging |
| Tracing | Zipkin + Micrometer Brave | - | Distributed tracing |
| Rate Limiting | Redis | latest | API rate limiting |
| Resilience | Resilience4j | - | Circuit breaker, retry, timeout |
| Auth | JWT (jjwt 0.11.5) | - | Token-based authentication |
| Database | MySQL | 8.0 | Relational database |
| PDF | OpenPDF (lowagie) | - | Invoice PDF generation |
| Monitoring | Prometheus + Grafana | - | Metrics and dashboards |
| Containerization | Docker | - | Container runtime |
| Orchestration | Kubernetes | - | Container orchestration |
| CI/CD | GitHub Actions | - | Automated build and deployment |
| Build Tool | Maven | - | Dependency management and build |
| Code Generation | Lombok | - | Boilerplate code reduction |

---

## 2. Complete Architecture Explanation

### 2.1 High-Level Architecture

```
                                    ┌─────────────────────────────────────────────────────────────────────┐
                                    │                        INFRASTRUCTURE                                │
                                    │                                                                       │
                                    │   ┌──────────────┐    ┌──────────────┐    ┌──────────────┐          │
                                    │   │   Eureka      │    │   Config     │    │   RabbitMQ   │          │
                                    │   │   Server      │    │   Server     │    │   (Messaging)│          │
                                    │   │   (8761)      │    │   (8888)     │    │   (5672)     │          │
                                    │   └──────────────┘    └──────────────┘    └──────────────┘          │
                                    │                                                                       │
                                    │   ┌──────────────┐    ┌──────────────┐    ┌──────────────┐          │
                                    │   │   Redis       │    │   Zipkin     │    │  Prometheus  │          │
                                    │   │ (Rate Limit)  │    │  (Tracing)   │    │ + Grafana    │          │
                                    │   │   (6379)      │    │   (9411)     │    │ (9090/3000)  │          │
                                    │   └──────────────┘    └──────────────┘    └──────────────┘          │
                                    └─────────────────────────────────────────────────────────────────────┘

                                                              │
                                                              │ Service Discovery
                                                              │ + Config Fetch
                                                              ▼
┌──────────┐         ┌──────────────────────────────────────────────────────────────────────────┐
│          │  HTTP   │                         API GATEWAY (8080)                                │
│  Client  │ ──────► │  ┌────────────────┐  ┌─────────────────┐  ┌────────────────────────┐    │
│ (Browser/│         │  │ JWT Validation  │  │ Rate Limiting   │  │ Route to Services      │    │
│  Mobile/ │         │  │ Filter          │  │ (Redis)         │  │ via Eureka Discovery   │    │
│  Postman)│         │  └────────────────┘  └─────────────────┘  └────────────────────────┘    │
│          │         │                                                                          │
└──────────┘         │  Forwards Headers: X-User-Email, X-User-Role                            │
                     └───────┬────────┬────────┬────────┬────────┬────────┬────────┬────────┬──┘
                             │        │        │        │        │        │        │        │
                             ▼        ▼        ▼        ▼        ▼        ▼        ▼        ▼
                     ┌────────┐┌────────┐┌────────┐┌────────┐┌────────┐┌────────┐┌────────┐┌────────┐
                     │ USER   ││ ORDER  ││PAYMENT ││  CAR   ││ ADMIN  ││RATING  ││INVOICE ││NOTIF.  │
                     │SERVICE ││SERVICE ││SERVICE ││SERVICE ││SERVICE ││SERVICE ││SERVICE ││SERVICE │
                     │ (8081) ││ (8083) ││ (8084) ││ (8082) ││ (8088) ││ (8086) ││ (8087) ││ (8089) │
                     └────────┘└───┬────┘└────────┘└────────┘└────────┘└────────┘└────────┘└───┬────┘
                                   │                                                           │
                                   │         RabbitMQ (Async Events)                           │
                                   └──────────────────────────────────────────────────────────►│
                                              OrderEvent published                   NotificationListener
                                              on order create/complete               consumes & saves
```

### 2.2 Request Flow: Client → Gateway → Services

Every single request in the system follows this flow:

```
Step 1: Client sends HTTP request
        POST http://localhost:8080/order/create
        Headers: Authorization: Bearer <JWT_TOKEN>
        Body: { "carId": 1, "serviceType": "PREMIUM" }

Step 2: API Gateway receives the request
        - JwtAuthenticationFilter intercepts BEFORE routing
        - Checks if the path is public (/auth, /user/email, /user/all, etc.)
        - If public → skip JWT validation, forward directly
        - If protected → extract JWT from Authorization header

Step 3: Gateway validates JWT
        - Extracts token from "Bearer <token>"
        - Uses JwtUtil.validateToken() to verify signature and expiration
        - Extracts email (subject) and role (custom claim) from token
        - If invalid → returns 401 UNAUTHORIZED immediately

Step 4: Gateway mutates the request
        - Adds two new headers to the request:
          X-User-Email: sarthak@gmail.com
          X-User-Role: CUSTOMER
        - Original Authorization header is also forwarded

Step 5: Gateway routes to the correct service
        - Uses Eureka to discover ORDER-SERVICE instances
        - Applies load balancing if multiple instances exist
        - Forwards the mutated request to ORDER-SERVICE

Step 6: ORDER-SERVICE receives the request
        - OrderController reads X-User-Email from header
        - Uses email to fetch user details via Feign (from USER-SERVICE)
        - No JWT validation happens in ORDER-SERVICE
        - Trusts the Gateway's headers completely

Step 7: ORDER-SERVICE calls other services via Feign
        - Calls USER-SERVICE to get user ID from email
        - Calls CAR-SERVICE to validate the car belongs to the user
        - FeignConfig interceptor forwards X-User-Email and X-User-Role headers

Step 8: Response flows back
        - ORDER-SERVICE returns the created Order entity
        - Gateway forwards the response to the client
        - Client receives the response
```

### 2.3 Synchronous vs Asynchronous Communication

This system uses **both** synchronous and asynchronous communication patterns:

#### Synchronous Communication (OpenFeign)

Used when the calling service **needs a response immediately** to continue processing.

```
ORDER-SERVICE ──── Feign GET /user/email?email=X ──── USER-SERVICE
                   (Waits for response)
                   Returns: { id: 1, email: "sarthak@gmail.com" }

ORDER-SERVICE ──── Feign GET /car/user/1 ──── CAR-SERVICE
                   (Waits for response)
                   Returns: [{ id: 1, brand: "BMW", ... }]

ORDER-SERVICE ──── Feign POST /payment/pay ──── PAYMENT-SERVICE
                   (Waits for response)
                   Returns: { status: "SUCCESS" }
```

**Why Sync here?** Because ORDER-SERVICE cannot create an order without knowing the user's ID. It cannot validate the car without fetching the car list. It cannot confirm payment without waiting for the payment response.

#### Asynchronous Communication (RabbitMQ)

Used when the calling service **doesn't need to wait** for a response. It's a "fire and forget" pattern.

```
ORDER-SERVICE ──── Publishes OrderEvent ──── RabbitMQ Queue ──── NOTIFICATION-SERVICE
                   (Does NOT wait)            (Buffered)          (Processes later)
```

**Why Async here?** When an order is created or a payment is completed, the customer should receive a notification. But the order creation should NOT fail if the notification service is temporarily down. The order is already created successfully - the notification is a separate concern. RabbitMQ holds the event until NOTIFICATION-SERVICE is ready to process it.

### 2.4 Service-to-Service Communication

Services communicate with each other using **OpenFeign** - a declarative HTTP client provided by Spring Cloud. Instead of manually writing RestTemplate calls, you define a Java interface with annotations:

```java
@FeignClient(name = "USER-SERVICE", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/user/email")
    User getUserByEmail(@RequestParam("email") String email);
}
```

This interface tells Spring Cloud:
1. The target service is registered in Eureka as "USER-SERVICE"
2. Use FeignConfig for interceptors (to forward headers)
3. When `getUserByEmail("sarthak@gmail.com")` is called, make an HTTP GET to `http://USER-SERVICE/user/email?email=sarthak@gmail.com`
4. Eureka resolves "USER-SERVICE" to the actual IP and port (e.g., `192.168.1.5:8081`)
5. If multiple instances exist, the load balancer picks one

The **FeignConfig** is critical because it forwards the `X-User-Email` and `X-User-Role` headers from the original request to inter-service calls:

```java
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String email = request.getHeader("X-User-Email");
                String role = request.getHeader("X-User-Role");
                if (email != null) template.header("X-User-Email", email);
                if (role != null) template.header("X-User-Role", role);
            }
        };
    }
}
```

### 2.5 Service Registry Flow

```
On Startup:
┌──────────────┐      1. Register        ┌──────────────┐
│ ORDER-SERVICE │  ──────────────────►    │    EUREKA     │
│  (8083)       │                         │    SERVER     │
│               │  ◄──────────────────    │   (8761)     │
│               │      2. Acknowledge     │              │
└──────────────┘                          │  Registry:   │
                                          │  USER: 8081  │
┌──────────────┐      3. Register         │  ORDER: 8083 │
│ USER-SERVICE  │  ──────────────────►    │  CAR: 8082   │
│  (8081)       │                         │  PAYMENT:8084│
│               │      4. Heartbeat       │  ...         │
│               │  ──────────────────►    └──────────────┘
└──────────────┘      (every 30 sec)

When ORDER-SERVICE needs USER-SERVICE:
┌──────────────┐   5. Where is USER-SERVICE?  ┌──────────────┐
│ ORDER-SERVICE │  ──────────────────────►     │    EUREKA     │
│               │                              │    SERVER     │
│               │  ◄──────────────────────     │              │
│               │   6. It's at 192.168.1.5:8081│              │
│               │                              └──────────────┘
│               │   7. Direct HTTP call
│               │  ──────────────────────►     ┌──────────────┐
│               │                              │ USER-SERVICE  │
│               │  ◄──────────────────────     │ (8081)        │
└──────────────┘   8. Response                 └──────────────┘
```

---

## 3. Core Concepts - Deep Explanation

### 3.1 What is Microservices Architecture

**Microservices architecture** is a software design approach where a single application is composed of many small, independent services. Each service:

1. **Runs in its own process** - Each service is a separate Java application with its own JVM
2. **Has its own database** - USER-SERVICE has `user_db`, ORDER-SERVICE has `order_db`, etc.
3. **Communicates over the network** - Services talk to each other via HTTP/REST or messaging
4. **Is independently deployable** - You can update ORDER-SERVICE without touching USER-SERVICE
5. **Is organized around business capabilities** - Each service handles one domain (users, orders, payments)

In our Car Wash System:
```
┌─────────────────────────────────────────────────────────────┐
│                     CAR WASH SYSTEM                          │
│                                                              │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐          │
│  │ USER    │ │ ORDER   │ │ PAYMENT │ │ CAR     │          │
│  │ SERVICE │ │ SERVICE │ │ SERVICE │ │ SERVICE │          │
│  │         │ │         │ │         │ │         │          │
│  │ user_db │ │ order_db│ │ (no db) │ │ car_db  │          │
│  └─────────┘ └─────────┘ └─────────┘ └─────────┘          │
│                                                              │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐          │
│  │ ADMIN   │ │ RATING  │ │ INVOICE │ │ NOTIF.  │          │
│  │ SERVICE │ │ SERVICE │ │ SERVICE │ │ SERVICE │          │
│  │         │ │         │ │         │ │         │          │
│  │ admin_db│ │rating_db│ │invoice_db│ │notif_db│          │
│  └─────────┘ └─────────┘ └─────────┘ └─────────┘          │
└─────────────────────────────────────────────────────────────┘
```

Each service is a fully independent Spring Boot application. They don't share code, don't share databases, and don't share deployments. The only way they interact is through well-defined APIs (REST endpoints) and events (RabbitMQ messages).

### 3.2 Monolith vs Microservices - Deep Comparison

| Aspect | Monolith | Microservices (Our System) |
|--------|----------|---------------------------|
| **Codebase** | Single project, single repository | Multiple projects (10 services + infrastructure) |
| **Deployment** | Deploy entire app for any change | Deploy only the changed service |
| **Database** | Single shared database | Each service has its own database |
| **Scaling** | Scale the entire application | Scale individual services independently |
| **Technology** | Single tech stack for everything | Each service can use different tech |
| **Team Structure** | All developers work on same codebase | Teams own individual services |
| **Failure Impact** | One bug can crash everything | One service failure is isolated |
| **Complexity** | Simple to build, complex to scale | Complex to build, simple to scale |
| **Communication** | In-process method calls (fast) | Network calls - HTTP/messaging (slower) |
| **Data Consistency** | Easy (single database transactions) | Hard (distributed transactions) |
| **Testing** | Simple end-to-end testing | Complex integration testing needed |
| **Debugging** | Single log file, easy to trace | Distributed logs, need Zipkin for tracing |
| **DevOps** | Simple deployment pipeline | Complex CI/CD, need Docker/K8s |

#### When to Choose Monolith:
- Small team (1-5 developers)
- Simple business logic
- Early-stage startup (MVP)
- No need for independent scaling

#### When to Choose Microservices (Our Choice):
- Large team working on different features
- Complex business domains that can be separated
- Need for independent scaling and deployment
- Production system that needs fault isolation
- Learning/portfolio project to demonstrate architecture skills

### 3.3 API Gateway Pattern

#### What is an API Gateway?

An API Gateway is a server that acts as the **single entry point** for all client requests. Instead of clients calling 8 different services on 8 different ports, they call one gateway on one port.

**Without API Gateway:**
```
Client → http://localhost:8081/auth/login    (User Service)
Client → http://localhost:8083/order/create  (Order Service)
Client → http://localhost:8084/payment/pay   (Payment Service)
Client → http://localhost:8082/car/add       (Car Service)

Problems:
- Client needs to know all service addresses
- No centralized authentication
- No rate limiting
- No single point for logging/monitoring
- CORS issues with multiple origins
```

**With API Gateway (Our System):**
```
Client → http://localhost:8080/auth/login
Client → http://localhost:8080/order/create
Client → http://localhost:8080/payment/pay
Client → http://localhost:8080/car/add

Benefits:
- Single entry point (port 8080)
- JWT validated once at the gateway
- Rate limiting applied at the gateway
- Headers forwarded to all services
- Easy to add CORS, logging, etc.
```

#### How Our Gateway Works Internally

Our API Gateway uses **Spring Cloud Gateway**, which is a reactive, non-blocking gateway built on Spring WebFlux and Netty. It processes requests through a pipeline of filters:

```
Client Request
       │
       ▼
┌──────────────────────┐
│  JwtAuthenticationFilter  │  (GlobalFilter - runs on EVERY request)
│                            │
│  1. Check if path is public │
│  2. Extract JWT token       │
│  3. Validate token          │
│  4. Extract email + role    │
│  5. Add X-User-Email header │
│  6. Add X-User-Role header  │
└──────────┬───────────────┘
           │
           ▼
┌──────────────────────┐
│  Rate Limiter Filter  │  (Per-route filter via Redis)
│                        │
│  1. Get user key       │
│     (email or IP)      │
│  2. Check Redis counter│
│  3. Allow or reject    │
└──────────┬────────────┘
           │
           ▼
┌──────────────────────┐
│  Route Matching       │
│                        │
│  /auth/** → USER-SERVICE  │
│  /order/** → ORDER-SERVICE│
│  /car/** → CAR-SERVICE    │
│  /payment/** → PAYMENT-SVC│
│  ...                      │
└──────────┬────────────┘
           │
           ▼
┌──────────────────────┐
│  Load Balancer        │  (Eureka + Spring Cloud LoadBalancer)
│                        │
│  Resolve service name  │
│  to actual IP:port     │
└──────────┬────────────┘
           │
           ▼
     Target Service
```

### 3.4 Service Discovery with Eureka

#### The Problem Without Service Discovery

In a microservices world, services run on dynamic IP addresses and ports. In Kubernetes or Docker, containers get random IPs. You can't hardcode `http://192.168.1.5:8081` because:
- The IP changes when the container restarts
- Multiple instances may run on different IPs
- New instances are added/removed dynamically (auto-scaling)

#### How Eureka Solves This

Eureka is a **service registry** - a phonebook for microservices. Every service registers itself with Eureka on startup and sends heartbeats every 30 seconds to say "I'm alive."

**Registration:**
```
USER-SERVICE starts → Sends registration to Eureka
  "Hi, I'm USER-SERVICE, running at 192.168.1.5:8081"

ORDER-SERVICE starts → Sends registration to Eureka
  "Hi, I'm ORDER-SERVICE, running at 192.168.1.6:8083"
```

**Discovery:**
```
ORDER-SERVICE needs USER-SERVICE:
  ORDER-SERVICE asks Eureka: "Where is USER-SERVICE?"
  Eureka responds: "USER-SERVICE is at 192.168.1.5:8081"
  ORDER-SERVICE makes HTTP call to 192.168.1.5:8081
```

**Load Balancing:**
```
If USER-SERVICE has 3 instances:
  Instance 1: 192.168.1.5:8081
  Instance 2: 192.168.1.6:8081
  Instance 3: 192.168.1.7:8081

Eureka returns all three. Spring Cloud LoadBalancer picks one (round-robin).
```

Our Eureka Server configuration is minimal:

```properties
spring.application.name=EUREKA-SERVER
server.port=8761
eureka.client.register-with-eureka=false   # Don't register yourself
eureka.client.fetch-registry=false          # Don't fetch others (you ARE the registry)
```

The `@EnableEurekaServer` annotation on the main class activates the Eureka server functionality. All other services include the `spring-cloud-starter-netflix-eureka-client` dependency and automatically register with Eureka using the `spring.application.name` property.

### 3.5 Centralized Configuration with Config Server

#### The Problem Without Centralized Config

Each service needs configuration (database URLs, JWT secrets, RabbitMQ credentials, etc.). Without centralized config:

```
user-service/application.properties:
  spring.datasource.url=jdbc:mysql://localhost:3306/user_db
  jwt.secret=mysupersecretkey...

order-service/application.properties:
  spring.datasource.url=jdbc:mysql://localhost:3306/order_db
  jwt.secret=mysupersecretkey...   ← DUPLICATED!

car-service/application.properties:
  spring.datasource.url=jdbc:mysql://localhost:3306/car_db
  jwt.secret=mysupersecretkey...   ← DUPLICATED AGAIN!
```

Problems:
- JWT secret is duplicated in every service
- Changing any shared config requires redeploying all services
- Different environments (dev/staging/prod) need different config files

#### How Config Server Solves This

Spring Cloud Config Server fetches configuration from a **central Git repository** and serves it to all services at startup.

```
┌─────────────────┐                    ┌──────────────────┐
│  GitHub Repo     │                    │   CONFIG SERVER   │
│                  │  git clone/pull    │     (8888)        │
│  USER-SERVICE.   │ ◄────────────────  │                   │
│   properties     │                    │  Serves configs   │
│  ORDER-SERVICE.  │                    │  via REST API     │
│   properties     │                    │                   │
│  APPLICATION.    │                    └────────┬──────────┘
│   properties     │                             │
└─────────────────┘                              │
                                                 │ HTTP GET /USER-SERVICE/default
                                    ┌────────────┼────────────┐
                                    │            │            │
                                    ▼            ▼            ▼
                             ┌──────────┐ ┌──────────┐ ┌──────────┐
                             │ USER-SVC │ │ ORDER-SVC│ │ CAR-SVC  │
                             └──────────┘ └──────────┘ └──────────┘
```

Our Config Server points to a GitHub repository:
```properties
spring.cloud.config.server.git.uri=https://github.com/sarthakpawar0912/Car-Wash-central-config-repo-.git
spring.cloud.config.server.git.clone-on-start=true
spring.cloud.config.server.git.default-label=main
```

Each service fetches its config on startup:
```properties
# In each service's bootstrap.properties
spring.application.name=ORDER-SERVICE
spring.cloud.config.uri=http://localhost:8888
```

Config Server looks for a file named `ORDER-SERVICE.properties` in the Git repo and serves its contents.

#### Dynamic Configuration Refresh with Spring Cloud Bus

When you change a config value in the Git repo, you don't need to restart services. Spring Cloud Bus (backed by RabbitMQ) broadcasts a refresh event to all services:

```
1. Developer changes jwt.secret in Git repo
2. Developer calls POST /actuator/busrefresh on Config Server
3. Config Server publishes "refresh" event to RabbitMQ
4. All services subscribed via Spring Cloud Bus receive the event
5. All services reload their @RefreshScope beans
6. New configuration is active without restart!
```

### 3.6 Inter-Service Communication with OpenFeign

#### What is OpenFeign?

OpenFeign is a **declarative HTTP client**. Instead of writing manual HTTP calls with RestTemplate or WebClient, you define a Java interface that describes the remote API, and Spring generates the implementation automatically.

#### Traditional RestTemplate Approach (NOT recommended):
```java
// Verbose, error-prone, hard to maintain
RestTemplate restTemplate = new RestTemplate();
String url = "http://USER-SERVICE/user/email?email=" + email;
User user = restTemplate.getForObject(url, User.class);
```

#### OpenFeign Approach (What we use):
```java
// Clean, declarative, type-safe
@FeignClient(name = "USER-SERVICE", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/user/email")
    User getUserByEmail(@RequestParam("email") String email);
}

// Usage in service:
User user = userClient.getUserByEmail("sarthak@gmail.com");
```

#### How Feign Resolves Service Names

1. `@FeignClient(name = "USER-SERVICE")` tells Feign the target service name
2. Feign asks Eureka: "Where is USER-SERVICE?"
3. Eureka returns: `192.168.1.5:8081`
4. Feign makes HTTP call to `http://192.168.1.5:8081/user/email?email=sarthak@gmail.com`
5. Response is automatically deserialized into `User` object

#### Feign Interceptor for Header Forwarding

A critical piece is the `FeignConfig` that forwards authentication headers. Without this, when ORDER-SERVICE calls USER-SERVICE via Feign, the `X-User-Email` and `X-User-Role` headers would be lost:

```java
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // Get the current HTTP request context
            ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                // Copy headers from original request to Feign request
                String email = request.getHeader("X-User-Email");
                String role = request.getHeader("X-User-Role");
                if (email != null) template.header("X-User-Email", email);
                if (role != null) template.header("X-User-Role", role);
            }
        };
    }
}
```

### 3.7 Event-Driven Architecture with RabbitMQ

#### What is RabbitMQ?

RabbitMQ is a **message broker** - a middleman that receives messages from producers (senders) and delivers them to consumers (receivers). It's like a post office for your microservices.

#### Why Use Messaging?

Consider this scenario: When a customer creates an order, we want to send them a notification email. Two approaches:

**Approach 1: Synchronous (Bad)**
```java
// In OrderService.createOrder()
Order order = repo.save(newOrder);
notificationClient.sendEmail(email, "Order Created");  // ← Feign call
return order;

// Problem: If NotificationService is down, order creation FAILS!
// The customer gets an error even though the order was valid.
```

**Approach 2: Asynchronous with RabbitMQ (Good - Our approach)**
```java
// In OrderService.createOrder()
Order order = repo.save(newOrder);
publisher.sendOrderEvent(new OrderEvent(order.getId(), email, 500.0, "CREATED"));
return order;

// The OrderEvent goes to RabbitMQ queue.
// If NotificationService is down, the message waits in the queue.
// When NotificationService comes back up, it processes all pending messages.
// Order creation NEVER fails due to notification issues.
```

#### Components in Our RabbitMQ Setup

```
                      Exchange: order.exchange
                      Type: Direct
                      Routing Key: order.routing
                      
ORDER-SERVICE ──publish──► [Exchange] ──route──► [Queue: order_queue] ──consume──► NOTIFICATION-SERVICE
  (Producer)                                                                         (Consumer)
```

- **Exchange**: Receives messages and routes them to queues based on routing rules
- **Queue**: Holds messages until a consumer processes them
- **Routing Key**: Determines which queue receives which messages
- **Producer**: ORDER-SERVICE publishes events
- **Consumer**: NOTIFICATION-SERVICE listens and processes events

### 3.8 Distributed Tracing with Zipkin

#### The Problem

When a user creates an order, the request flows through multiple services:

```
Client → Gateway → ORDER-SERVICE → USER-SERVICE
                                 → CAR-SERVICE
                                 → PAYMENT-SERVICE
                                 → RabbitMQ → NOTIFICATION-SERVICE
```

If something fails or is slow, which service caused the problem? Without distributed tracing, you'd have to check logs of every single service manually.

#### How Zipkin Solves This

Zipkin assigns a unique **Trace ID** to every incoming request. This ID is propagated through all service calls. Each service reports its timing data (called "spans") to the Zipkin server.

```
Trace ID: abc-123-def
├── Span 1: API-GATEWAY (0ms - 250ms)
│   └── Span 2: ORDER-SERVICE (10ms - 240ms)
│       ├── Span 3: USER-SERVICE (20ms - 50ms)
│       ├── Span 4: CAR-SERVICE (55ms - 100ms)
│       └── Span 5: PAYMENT-SERVICE (105ms - 230ms)
```

You can visualize this in the Zipkin UI at `http://localhost:9411`, seeing exactly:
- How long each service took
- Which service calls happened in parallel vs sequential
- Where the bottleneck is
- If any service call failed

Each service includes these dependencies to enable tracing:
```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
```

### 3.9 Rate Limiting with Redis

#### What is Rate Limiting?

Rate limiting restricts the number of requests a client can make in a given time window. It protects your APIs from:

1. **DDoS attacks** - Malicious actors flooding your server with requests
2. **Accidental abuse** - A buggy client making thousands of requests per second
3. **Fair usage** - Ensuring one user doesn't monopolize resources

#### How Redis Enables Rate Limiting

Redis is an in-memory data store that supports atomic operations. It's perfect for rate limiting because:
- It's extremely fast (sub-millisecond operations)
- It supports TTL (time-to-live) for automatic key expiration
- Atomic increment operations prevent race conditions

Our Gateway uses the **Token Bucket** algorithm:
```
For each user (identified by email or IP):
  - Redis stores a counter: "rate_limit:sarthak@gmail.com" = 5
  - Each request decrements the counter
  - Every second, the counter replenishes by a fixed amount
  - If counter reaches 0, request is rejected with 429 Too Many Requests
```

The `KeyResolver` bean determines how to identify users:

```java
@Bean
public KeyResolver userKeyResolver() {
    return exchange -> {
        String email = exchange.getRequest().getHeaders().getFirst("X-User-Email");
        if (email != null) {
            return Mono.just(email);  // Rate limit per user
        }
        return Mono.just(
            exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
        );  // Fallback: rate limit per IP
    };
}
```

### 3.10 Circuit Breaker, Retry, Timeout with Resilience4j

#### The Problem: Cascading Failures

In a microservices system, if USER-SERVICE goes down, every service that depends on it (ORDER, ADMIN, RATING, INVOICE) will also start failing. Requests pile up, threads get exhausted, and the entire system crashes. This is called a **cascading failure**.

```
USER-SERVICE is DOWN
     ↓
ORDER-SERVICE keeps calling USER-SERVICE
     ↓
ORDER-SERVICE threads are waiting for response (timeout)
     ↓
ORDER-SERVICE thread pool exhausted
     ↓
ORDER-SERVICE starts rejecting ALL requests
     ↓
API Gateway gets errors from ORDER-SERVICE
     ↓
Client gets errors for EVERYTHING (not just user-related)
```

#### How Circuit Breaker Solves This

A circuit breaker works exactly like an electrical circuit breaker:

```
CLOSED (Normal):
  All requests flow through to USER-SERVICE
  If 50%+ of recent requests fail → switch to OPEN

OPEN (Tripped):
  ALL requests are immediately rejected (no attempt to call USER-SERVICE)
  Returns fallback response instantly
  After 60 seconds → switch to HALF_OPEN

HALF_OPEN (Testing):
  Allow 3 test requests through
  If they succeed → switch to CLOSED
  If they fail → switch back to OPEN
```

Our implementation:

```java
@Retry(name = "userService")                                         // Try 3 times
@CircuitBreaker(name = "userService", fallbackMethod = "userFallback") // Then circuit break
public User getUser(String email) {
    return userClient.getUserByEmail(email);
}

// Fallback: returns dummy user instead of throwing error
public User userFallback(String email, Exception e) {
    System.out.println("USER SERVICE DOWN");
    User u = new User();
    u.setId(0L);
    u.setEmail("fallback@gmail.com");
    return u;
}
```

The retry happens **before** the circuit breaker. If all retries fail, the circuit breaker records the failure. After enough failures, it opens the circuit and calls the fallback directly without even attempting the HTTP call.

### 3.11 JWT Authentication - Deep Dive

#### What is JWT?

JWT (JSON Web Token) is a compact, URL-safe way to represent claims between two parties. It's used for **stateless authentication** - the server doesn't need to store session data. Everything the server needs to know about the user is embedded in the token itself.

#### JWT Structure

A JWT has three parts separated by dots: `xxxxx.yyyyy.zzzzz`

```
HEADER.PAYLOAD.SIGNATURE

HEADER (Base64 encoded):
{
  "alg": "HS256",     ← Algorithm used for signing
  "typ": "JWT"        ← Token type
}

PAYLOAD (Base64 encoded):
{
  "sub": "sarthak@gmail.com",  ← Subject (user's email)
  "role": "CUSTOMER",          ← Custom claim (user's role)
  "iat": 1712400000,           ← Issued at (timestamp)
  "exp": 1712486400            ← Expiration (24 hours later)
}

SIGNATURE:
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret_key                    ← "mysupersecretkeymysupersecretkey12345"
)
```

#### How JWT Works in Our System

**Token Generation (USER-SERVICE):**
```java
public String generateToken(String email, String role) {
    return Jwts.builder()
        .setSubject(email)                                    // Who is this user?
        .claim("role", role)                                  // What role do they have?
        .setIssuedAt(new Date())                             // When was it created?
        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expires in 24h
        .signWith(getKey(), SignatureAlgorithm.HS256)        // Sign with secret key
        .compact();
}
```

**Token Validation (API Gateway):**
```java
public void validateToken(String token) {
    Jwts.parserBuilder()
        .setSigningKey(getKey())    // Use same secret key
        .build()
        .parseClaimsJws(token);    // Throws exception if invalid/expired
}
```

**Complete JWT Flow:**
```
1. User sends POST /auth/login with email + password
2. USER-SERVICE validates credentials against database
3. USER-SERVICE generates JWT with email + role
4. JWT is returned to client: "Bearer eyJhbGciOiJIUzI1NiJ9..."
5. Client stores the token (localStorage, cookie, etc.)
6. For every subsequent request, client sends:
   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
7. API Gateway validates the token
8. Gateway extracts email + role from token
9. Gateway forwards X-User-Email + X-User-Role headers
10. Backend services read these headers (no JWT parsing needed)
```

### 3.12 Security in Microservices

Our security architecture follows the **Gateway Trust Pattern**:

```
EXTERNAL WORLD (Untrusted)          │          INTERNAL NETWORK (Trusted)
                                    │
Client ──── JWT Token ────► API Gateway ──── X-User-Email ────► Services
                                    │        X-User-Role
                                    │
                                    │  The Gateway is the ONLY service
                                    │  that validates JWT tokens.
                                    │
                                    │  All internal services TRUST the
                                    │  headers set by the Gateway.
```

**Why this approach?**
1. **Performance**: JWT validation happens once at the Gateway, not 8 times across services
2. **Simplicity**: Services don't need JWT libraries or validation logic
3. **Consistency**: All services get user info in the same format (headers)
4. **Security**: Services are not exposed directly to the internet; only the Gateway is

**USER-SERVICE SecurityConfig:**
```java
@Bean
public SecurityFilterChain security(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()  // Trust ALL internal traffic
        );
    return http.build();
}
```

The `JwtAuthFilter` in USER-SERVICE is intentionally **disabled** (the `@Component` annotation is commented out). The Gateway handles authentication; services trust the forwarded headers.

---

## 4. Service-by-Service Deep Dive

### 4.1 USER-SERVICE

**Port:** 8081
**Database:** user_db (MySQL)
**Purpose:** Handles user registration, login (authentication), and user data management. This is the **identity provider** of the system - it generates JWT tokens and stores user credentials.

#### Responsibilities:
- User registration with role assignment (CUSTOMER, WASHER, ADMIN)
- User login with password verification
- JWT token generation
- Providing user data to other services via Feign
- Enforcing "only one ADMIN" business rule

#### Key Classes:

**AuthController.java** - Handles registration and login endpoints:
```java
@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) { ... }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) { ... }
}
```

**UserController.java** - Provides user lookup for other services:
```java
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/email")        // Used by ORDER, RATING, INVOICE via Feign
    public User getUserByEmail(@RequestParam String email) { ... }

    @GetMapping("/all")          // Used by ADMIN-SERVICE via Feign
    public Object getAllUsers() { ... }
}
```

**AuthService.java** - Core business logic:
- Registration: Validates email uniqueness, enforces single-admin rule, hashes password with BCrypt, generates JWT
- Login: Finds user by email, verifies password with BCrypt, generates JWT

**JwtService.java** - Token operations:
- `generateToken(email, role)` - Creates a JWT with 24-hour expiry
- `extractEmail(token)` - Reads the subject claim
- `extractRole(token)` - Reads the role custom claim
- `isTokenValid(token)` - Validates signature and expiry

**SecurityConfig.java** - Spring Security configuration:
- CSRF disabled (stateless API)
- Session management set to STATELESS
- All requests permitted (Gateway handles auth)
- BCrypt password encoder bean
- AuthenticationManager bean

**User Entity:**
```java
@Entity
public class User {
    private Long id;
    private String name;
    private String password;      // BCrypt hashed
    private String phone;
    private String email;         // Unique constraint
    private Roles role;           // CUSTOMER, WASHER, or ADMIN
}
```

**Roles Enum:**
```java
public enum Roles {
    CUSTOMER,   // Regular user who books car washes
    WASHER,     // Service provider who washes cars
    ADMIN       // Platform administrator
}
```

#### Internal Flow - Registration:
```
1. POST /auth/register { name, email, phone, password, role }
2. AuthService.register():
   a. Check if email already exists → throw if duplicate
   b. If role is ADMIN, check if admin already exists → throw if yes
   c. Create User entity with BCrypt-hashed password
   d. Save to database
   e. Generate JWT token
   f. Return AuthResponse { message, token, email, role }
```

#### Internal Flow - Login:
```
1. POST /auth/login { email, password }
2. AuthService.login():
   a. Find user by email → throw if not found
   b. Compare password with BCrypt → throw if mismatch
   c. Generate JWT token
   d. Return AuthResponse { message, token, email, role }
```

#### How Other Services Use USER-SERVICE:
```
ORDER-SERVICE calls:   GET /user/email?email=sarthak@gmail.com
                       Returns: { id: 1, email: "sarthak@gmail.com" }

ADMIN-SERVICE calls:   GET /user/all
                       Returns: [{ id: 1, ... }, { id: 2, ... }]
```

### 4.2 ORDER-SERVICE

**Port:** 8083
**Database:** order_db (MySQL)
**Purpose:** Core business service that manages the entire lifecycle of car wash orders - from creation to completion. It orchestrates communication between multiple services (User, Car, Payment) and publishes events to RabbitMQ.

#### Responsibilities:
- Create new wash orders
- Assign washers to orders
- Update order status (CREATED → ASSIGNED → COMPLETED)
- Process payments via Feign call to PAYMENT-SERVICE
- Delete orders (with authorization check)
- Publish order events to RabbitMQ for notifications

#### Key Classes:

**OrderController.java** - REST endpoints:
```java
@RestController
@RequestMapping("/order")
public class OrderController {
    @PostMapping("/create")              // Create new order
    @GetMapping("/my")                   // Get user's orders
    @PutMapping("/assign/{id}")          // Admin assigns washer
    @PutMapping("/status/{id}")          // Update order status
    @DeleteMapping("/delete/{id}")       // Delete order (owner only)
    @GetMapping("/{id}")                 // Track specific order
    @PostMapping("/pay/{orderId}")       // Pay for order
}
```

Every endpoint reads `@RequestHeader("X-User-Email")` to identify the logged-in user.

**OrderService.java** - Core business logic with resilience patterns:
```java
@Service
public class OrderService {
    // Circuit Breaker + Retry for USER-SERVICE
    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    public User getUser(String email) { ... }

    // Circuit Breaker + Retry for CAR-SERVICE
    @Retry(name = "carService")
    @CircuitBreaker(name = "carService", fallbackMethod = "carFallback")
    public List<Car> getCars(Long userId) { ... }

    // Circuit Breaker + Retry for PAYMENT-SERVICE
    @Retry(name = "paymentService")
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public Payment createPayment(Long orderId, Double amount) { ... }
}
```

**Feign Clients:**
- `UserClient` → Calls USER-SERVICE to get user ID from email
- `CarClient` → Calls CAR-SERVICE to get user's cars
- `PaymentClient` → Calls PAYMENT-SERVICE to process payment

**OrderPublisher.java** - RabbitMQ event publishing:
```java
@Service
public class OrderPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void sendOrderEvent(OrderEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            RabbitMQConfig.ROUTING_KEY,
            event
        );
    }
}
```

**Order Entity:**
```java
@Entity
@Table(name = "orders")
public class Order {
    private Long id;
    private Long userId;           // Foreign key to User
    private Long carId;            // Foreign key to Car
    private String serviceType;    // e.g., "BASIC", "PREMIUM"
    private OrderStatus status;    // CREATED, ASSIGNED, COMPLETED
    private Long washerId;         // Assigned washer's ID
    private LocalDateTime scheduledAt;
    private LocalDateTime createdAt;
}
```

**OrderStatus Enum:**
```java
public enum OrderStatus {
    CREATED,    // Initial state - order placed by customer
    ASSIGNED,   // Washer has been assigned to the order
    COMPLETED   // Wash is done, payment processed
}
```

#### Internal Flow - Create Order:
```
1. POST /order/create + X-User-Email: sarthak@gmail.com
   Body: { carId: 1, serviceType: "PREMIUM" }

2. OrderService.createOrder():
   a. Call USER-SERVICE via Feign to get user (with retry + circuit breaker)
      GET /user/email?email=sarthak@gmail.com → { id: 1 }
   b. Call CAR-SERVICE via Feign to get user's cars (with retry + circuit breaker)
      GET /car/user/1 → [{ id: 1, brand: "BMW" }, { id: 2, brand: "Audi" }]
   c. Validate that carId=1 belongs to this user → Found in list, valid!
   d. Create Order entity (status=CREATED, createdAt=now)
   e. Save to database
   f. Publish OrderEvent to RabbitMQ:
      { orderId: 1, email: "sarthak@gmail.com", amount: 500.0, status: "CREATED" }
   g. Return saved Order

3. NOTIFICATION-SERVICE receives the event from RabbitMQ
   Saves notification: "Order ID: 1 | Status: CREATED | Amount: 500.0"
   Simulates email send
```

#### Internal Flow - Pay for Order:
```
1. POST /order/pay/1?amount=500.0 + X-User-Email: sarthak@gmail.com

2. OrderService.payForOrder():
   a. Call USER-SERVICE to get user
   b. Find order by ID
   c. Verify order belongs to this user (authorization check)
   d. Call PAYMENT-SERVICE via Feign (with retry + circuit breaker)
      POST /payment/pay?orderId=1&amount=500.0 → { status: "SUCCESS" }
   e. If payment SUCCESS:
      - Update order status to COMPLETED
      - Publish OrderEvent to RabbitMQ with status "COMPLETED"
   f. Save order
   g. Return "Payment SUCCESS"
```

### 4.3 PAYMENT-SERVICE

**Port:** 8084
**Database:** None (stateless, dummy implementation)
**Purpose:** Processes payment transactions for orders. Currently implements a dummy payment processor that always returns SUCCESS.

#### Responsibilities:
- Accept payment requests with orderId and amount
- Process the payment (currently always succeeds)
- Return payment status

#### Key Classes:

**PaymentController.java:**
```java
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @PostMapping("/pay")
    public Payment pay(@RequestParam Long orderId, @RequestParam Double amount) {
        return service.processPayment(orderId, amount);
    }
}
```

**PaymentService.java:**
```java
@Service
public class PaymentService {
    public Payment processPayment(Long orderId, Double amount) {
        // Currently a dummy implementation - always succeeds
        return new Payment(orderId, amount, "SUCCESS");
    }
}
```

**Payment DTO:**
```java
public class Payment {
    private Long orderId;
    private Double amount;
    private String status;    // "SUCCESS" or "FAILED"
}
```

#### Internal Flow:
```
1. ORDER-SERVICE calls via Feign: POST /payment/pay?orderId=1&amount=500.0
2. PaymentService.processPayment():
   a. Logs the payment details
   b. Returns Payment(orderId=1, amount=500.0, status="SUCCESS")
3. ORDER-SERVICE receives the response and updates order status
```

> **Note:** In a real production system, this service would integrate with payment gateways like Stripe, Razorpay, or PayPal. The current dummy implementation makes it easy to test the end-to-end flow.

### 4.4 CAR-SERVICE

**Port:** 8082
**Database:** car_db (MySQL)
**Purpose:** Manages customer vehicles. Customers can add, update, and delete their cars. The car data is used by ORDER-SERVICE to validate that a car belongs to the user creating an order.

#### Responsibilities:
- Add new cars for users
- List cars by user ID
- Update car details
- Delete cars

#### Key Classes:

**CarController.java:**
```java
@RestController
@RequestMapping("/car")
public class CarController {
    @PostMapping("/add")               // Add a new car
    @GetMapping("/user/{userId}")      // Get all cars for a user
    @PutMapping("/update/{id}")        // Update car details
    @DeleteMapping("/delete/{id}")     // Delete a car
}
```

**Car Entity:**
```java
@Entity
public class Car {
    private Long id;
    private Long userId;           // Owner's user ID
    private String brand;          // e.g., "BMW"
    private String model;          // e.g., "X5"
    private String color;          // e.g., "Black"
    private String numberPlate;    // e.g., "MH12AB1234"
}
```

**CarService.java:**
```java
@Service
public class CarService {
    public Car addCar(CarRequest carRequest, Long userId) {
        Car car = new Car();
        car.setUserId(userId);
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setColor(carRequest.getColor());
        car.setNumberPlate(carRequest.getNumberPlate());
        return carRepository.save(car);
    }

    public List<Car> getCarsByUser(Long userId) {
        return carRepository.findByUserId(userId);
    }
}
```

#### How ORDER-SERVICE Uses CAR-SERVICE:
```
ORDER-SERVICE: "I need all cars for user ID 1"
               GET /car/user/1

CAR-SERVICE:   Returns [
                 { id: 1, userId: 1, brand: "BMW", model: "X5", numberPlate: "MH12AB1234" },
                 { id: 2, userId: 1, brand: "Audi", model: "A4", numberPlate: "MH14CD5678" }
               ]

ORDER-SERVICE: "User wants carId=1, let me verify it's in the list... Yes, found!"
```

### 4.5 ADMIN-SERVICE

**Port:** 8088
**Database:** admin_db (MySQL)
**Purpose:** Provides administrative features for platform management. Only users with the ADMIN role can access these endpoints. Manages service plans and provides system reports.

#### Responsibilities:
- List all registered users (via Feign to USER-SERVICE)
- Create and manage service plans (wash packages with pricing)
- Generate platform reports
- Role-based access control (ADMIN only)

#### Key Classes:

**AdminController.java:**
```java
@RestController
@RequestMapping("/admin")
public class AdminController {

    // Role check - throws exception if not ADMIN
    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied: Admin only");
        }
    }

    @GetMapping("/users")      // List all users
    @PostMapping("/plan")      // Create service plan
    @GetMapping("/plans")      // List all service plans
    @GetMapping("/report")     // Get system report
}
```

Every endpoint reads `X-User-Role` from the header and calls `checkAdmin()` to verify the user is an admin. This is **application-level authorization** - the Gateway handles authentication (who you are), and the service handles authorization (what you can do).

**ServicePlan Entity:**
```java
@Entity
public class ServicePlan {
    private Long id;
    private String name;           // e.g., "Premium Wash"
    private Double price;          // e.g., 999.0
    private String description;    // e.g., "Full interior + exterior"
}
```

**AdminService.java** - With resilience patterns:
```java
@Service
public class AdminService {
    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "usersFallback")
    public List<?> getUsers() {
        return userClient.getAllUsers();   // Feign call to USER-SERVICE
    }

    public List<?> usersFallback(Exception e) {
        return List.of();   // Return empty list if USER-SERVICE is down
    }
}
```

### 4.6 RATING-SERVICE

**Port:** 8086
**Database:** rating_db (MySQL)
**Purpose:** Manages customer reviews and ratings for completed car wash orders. Customers can rate their experience from 1-5 stars and leave comments.

#### Responsibilities:
- Add reviews (rating 1-5 + comment) for orders
- Get reviews by order ID (public)
- Get user's own reviews

#### Key Classes:

**ReviewController.java:**
```java
@RestController
@RequestMapping("/review")
public class ReviewController {
    @PostMapping("/add")              // Submit a review
    @GetMapping("/my")                // Get my reviews
    @GetMapping("/order/{orderId}")   // Get reviews for an order (public)
}
```

**Review Entity:**
```java
@Entity
@Table(name = "reviews")
public class Review {
    private Long id;
    private Long userId;
    private Long orderId;
    private Integer rating;        // 1-5
    private String comment;
    private LocalDateTime createdAt;
}
```

**ReviewService.java:**
```java
@Service
public class ReviewService {
    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    public User getUser(String email) {
        return userClient.getUserByEmail(email);
    }

    public Review addReview(Review review, String email) {
        User user = getUser(email);
        review.setUserId(user.getId());

        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1-5");
        }

        review.setCreatedAt(LocalDateTime.now());
        return repo.save(review);
    }
}
```

### 4.7 INVOICE-SERVICE

**Port:** 8087
**Database:** invoice_db (MySQL)
**Purpose:** Generates GST-compliant tax invoices and downloadable PDF documents for completed orders. Uses OpenPDF library for PDF generation.

#### Responsibilities:
- Generate invoice records with CGST/SGST calculations
- Generate downloadable PDF invoices
- Store invoice metadata in database

#### Key Classes:

**InvoiceController.java:**
```java
@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @PostMapping("/generate")    // Generate invoice record
    @GetMapping("/pdf")          // Generate and download PDF
}
```

**Invoice Entity:**
```java
@Entity
public class Invoice {
    private Long id;
    private Long userId;
    private Long orderId;
    private Double amount;          // Base amount
    private Double cgst;            // Central GST (9%)
    private Double sgst;            // State GST (9%)
    private Double totalAmount;     // amount + cgst + sgst
    private String invoiceNumber;   // e.g., "INV-1712400000000"
    private String filePath;
    private LocalDateTime createdAt;
}
```

**InvoiceService.java** - GST Calculation:
```java
public Invoice generateInvoice(Long orderId, Double amount, String email) {
    User user = getUser(email);

    double cgst = amount * 0.09;     // 9% CGST
    double sgst = amount * 0.09;     // 9% SGST
    double total = amount + cgst + sgst;

    Invoice invoice = new Invoice();
    invoice.setAmount(amount);
    invoice.setCgst(cgst);
    invoice.setSgst(sgst);
    invoice.setTotalAmount(total);
    invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
    return repo.save(invoice);
}
```

**PDF Generation** uses OpenPDF to create professional invoices with:
- Title: "TAX INVOICE"
- Invoice number and date
- Seller details (Car Wash Pvt Ltd, GSTIN)
- Buyer details (email, phone)
- Service table (description, days, rate, amount)
- Bill breakdown (service amount, platform fee 15%, CGST 9%, SGST 9%, total)

### 4.8 NOTIFICATION-SERVICE

**Port:** 8089
**Database:** notification_db (MySQL)
**Purpose:** Asynchronous service that consumes order events from RabbitMQ and creates notifications. Currently simulates email sending by logging to console.

#### Responsibilities:
- Listen to RabbitMQ queue for order events
- Create notification records in database
- Simulate email sending (log to console)
- Provide API to view all notifications

#### Key Classes:

**NotificationListener.java** - RabbitMQ Consumer:
```java
@Component
public class NotificationListener {
    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consume(OrderEvent event) {
        log.info("Event Received: {}", event);

        String message = "Order ID: " + event.getOrderId()
                + " | Status: " + event.getStatus()
                + " | Amount: " + event.getAmount();

        // Save notification to database
        service.save(event.getEmail(), message);

        // Simulate email (would use JavaMail/SendGrid in production)
        System.out.println("EMAIL SENT to: " + event.getEmail());
    }
}
```

**RabbitConfig.java** - Queue/Exchange/Binding configuration:
```java
@Configuration
public class RabbitConfig {
    public static final String QUEUE = "order_queue";
    public static final String EXCHANGE = "order.exchange";
    public static final String ROUTING_KEY = "order.routing";

    @Bean public Queue queue() { return new Queue(QUEUE); }
    @Bean public DirectExchange exchange() { return new DirectExchange(EXCHANGE); }
    @Bean public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
    @Bean public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
```

**Notification Entity:**
```java
@Entity
public class Notification {
    private Long id;
    private String email;
    private String message;
    private String type;          // EMAIL, SMS, PUSH
    private LocalDateTime createdAt;
}
```

**NotificationController.java:**
```java
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @GetMapping("/all")
    public List<Notification> getAll() { return service.getAll(); }
}
```

---

## 5. API Gateway Deep Dive

### 5.1 Routing

The API Gateway uses Spring Cloud Gateway's routing mechanism. Routes are configured via the Config Server (centralized properties file). The Gateway uses Eureka for service discovery, so routes reference service names (not hardcoded URLs):

```properties
# Route: /auth/** → USER-SERVICE
spring.cloud.gateway.routes[0].id=auth-service
spring.cloud.gateway.routes[0].uri=lb://USER-SERVICE
spring.cloud.gateway.routes[0].predicates[0]=Path=/auth/**

# Route: /order/** → ORDER-SERVICE
spring.cloud.gateway.routes[1].id=order-service
spring.cloud.gateway.routes[1].uri=lb://ORDER-SERVICE
spring.cloud.gateway.routes[1].predicates[0]=Path=/order/**

# Route: /car/** → CAR-SERVICE
spring.cloud.gateway.routes[2].id=car-service
spring.cloud.gateway.routes[2].uri=lb://CAR-SERVICE
spring.cloud.gateway.routes[2].predicates[0]=Path=/car/**
```

The `lb://` prefix means "load balanced via Eureka." Spring Cloud Gateway resolves `USER-SERVICE` to an actual IP:port using the Eureka registry, and balances across multiple instances if available.

### 5.2 JWT Authentication Filter

The `JwtAuthenticationFilter` is a `GlobalFilter` - it runs on **every single request** that passes through the Gateway.

```java
@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // PUBLIC ENDPOINTS - No JWT required
        if (path.startsWith("/auth") ||          // Login/Register
            path.startsWith("/user/email") ||    // Feign calls
            path.startsWith("/user/all") ||      // Feign calls
            path.startsWith("/review/order") ||  // Public reviews
            path.startsWith("/notification")) {  // Public notifications
            return chain.filter(exchange);       // Skip JWT validation
        }

        // EXTRACT TOKEN
        String header = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return onError(exchange);            // 401 UNAUTHORIZED
        }

        String token = header.substring(7);      // Remove "Bearer " prefix

        try {
            jwtUtil.validateToken(token);         // Verify signature + expiry

            String email = jwtUtil.extractEmail(token);  // Get subject
            String role = jwtUtil.extractRole(token);    // Get role claim

            // MUTATE REQUEST - Add user info as headers
            ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(headers -> {
                    headers.set("X-User-Email", email);
                    headers.set("X-User-Role", role);
                })
                .build();

            // Forward mutated request to downstream service
            return chain.filter(exchange.mutate().request(request).build());

        } catch (Exception e) {
            return onError(exchange);            // 401 UNAUTHORIZED
        }
    }
}
```

### 5.3 Header Forwarding

The Gateway's most important job (after JWT validation) is adding trusted headers:

```
BEFORE Gateway:
  Headers: { Authorization: "Bearer eyJ..." }

AFTER Gateway's JwtAuthenticationFilter:
  Headers: {
    Authorization: "Bearer eyJ...",
    X-User-Email: "sarthak@gmail.com",
    X-User-Role: "CUSTOMER"
  }
```

These headers are then read by service controllers:
```java
@PostMapping("/create")
public Order createOrder(@RequestBody OrderRequest req,
                         @RequestHeader("X-User-Email") String email) {
    // 'email' contains "sarthak@gmail.com"
    return service.createOrder(req, email);
}
```

### 5.4 Rate Limiting with Redis

Rate limiting is configured per-route in the Gateway's properties:

```properties
spring.cloud.gateway.routes[1].filters[0]=RequestRateLimiter=
    redis-rate-limiter.replenishRate=10,
    redis-rate-limiter.burstCapacity=20
```

- `replenishRate=10`: 10 tokens are added per second
- `burstCapacity=20`: Maximum 20 tokens can be stored

The `KeyResolver` determines the rate limit key:

```java
@Bean
public KeyResolver userKeyResolver() {
    return exchange -> {
        // If user is authenticated, rate limit by email
        String email = exchange.getRequest().getHeaders().getFirst("X-User-Email");
        if (email != null) return Mono.just(email);

        // Otherwise, rate limit by IP address
        return Mono.just(
            exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
        );
    };
}
```

### 5.5 Common Gateway Issues and Solutions

#### Issue 1: ReadOnlyHttpHeaders Error
**Problem:** Cookie header caused `ReadOnlyHttpHeaders` exception when the Gateway tried to modify headers.
**Solution:** The Gateway's `JwtAuthenticationFilter` uses `exchange.getRequest().mutate()` to create a new request with modified headers, rather than trying to modify the original read-only headers.

#### Issue 2: Spring Security Default Login Page
**Problem:** Adding `spring-boot-starter-security` to the Gateway caused a default login page to appear.
**Solution:** Don't add Spring Security to the Gateway at all. The Gateway uses a custom `GlobalFilter` for JWT validation, not Spring Security.

#### Issue 3: RedisRateLimiter Configuration Error
**Problem:** `RequestRateLimiter` failed because Redis wasn't running or the `KeyResolver` bean wasn't found.
**Solution:** 
1. Ensure Redis is running (`docker run -d -p 6379:6379 redis`)
2. Add `spring-boot-starter-data-redis-reactive` dependency
3. Define the `KeyResolver` bean in `RateLimiterConfig`

---

## 6. Authentication Flow - Very Detailed

### 6.1 Registration Flow

```
Step-by-step: Customer Registration

1. CLIENT sends:
   POST http://localhost:8080/auth/register
   Content-Type: application/json
   {
     "name": "Sarthak Pawar",
     "email": "sarthak@gmail.com",
     "phone": "9876543210",
     "password": "1234",
     "role": "CUSTOMER"
   }

2. API GATEWAY receives the request
   - JwtAuthenticationFilter checks path: /auth → PUBLIC route
   - Skips JWT validation
   - Routes to USER-SERVICE via Eureka

3. USER-SERVICE receives:
   POST /auth/register
   - AuthController.register() called
   - Delegates to AuthService.register()

4. AuthService.register():
   a. repo.findByEmail("sarthak@gmail.com")
      → Not found (Optional.empty) → OK, proceed
   b. Role check: "CUSTOMER" is not "ADMIN" → skip admin check
   c. Create User entity:
      - name: "Sarthak Pawar"
      - email: "sarthak@gmail.com"
      - phone: "9876543210"
      - password: BCrypt.encode("1234") → "$2a$10$xyz..." (hashed!)
      - role: Roles.CUSTOMER
   d. repo.save(user) → saved with id=1
   e. jwt.generateToken("sarthak@gmail.com", "CUSTOMER")
      → "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYXJ0aGFrQGdtYWlsLmNvbSIs..."
   f. Return AuthResponse:
      {
        "message": "User registered successfully",
        "token": "Bearer eyJhbG...",
        "email": "sarthak@gmail.com",
        "role": "CUSTOMER"
      }

5. Response flows back through Gateway to Client
```

### 6.2 Login Flow

```
Step-by-step: Customer Login

1. CLIENT sends:
   POST http://localhost:8080/auth/login
   Content-Type: application/json
   {
     "email": "sarthak@gmail.com",
     "password": "1234"
   }

2. API GATEWAY: /auth → PUBLIC → skip JWT → route to USER-SERVICE

3. AuthService.login():
   a. repo.findByEmail("sarthak@gmail.com")
      → Found: User { id=1, password="$2a$10$xyz...", role=CUSTOMER }
   b. encoder.matches("1234", "$2a$10$xyz...") → true (password correct!)
   c. jwt.generateToken("sarthak@gmail.com", "CUSTOMER")
   d. Return AuthResponse:
      {
        "message": "Login successful",
        "token": "Bearer eyJhbG...",
        "email": "sarthak@gmail.com",
        "role": "CUSTOMER"
      }

4. CLIENT stores the token for future requests
```

### 6.3 Token Structure

The JWT token generated by our system:

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYXJ0aGFrQGdtYWlsLmNvbSIsInJvbGUiOiJDVVNUT01FUiIsImlhdCI6MTcxMjQwMDAwMCwiZXhwIjoxNzEyNDg2NDAwfQ.signature

Decoded:
HEADER: { "alg": "HS256" }
PAYLOAD: {
  "sub": "sarthak@gmail.com",     ← Email (subject)
  "role": "CUSTOMER",              ← User role
  "iat": 1712400000,               ← Issued at timestamp
  "exp": 1712486400                ← Expiry (24 hours later)
}
SIGNATURE: HMACSHA256(header.payload, "mysupersecretkeymysupersecretkey12345")
```

### 6.4 Token Validation at Gateway

When the client sends a protected request:
```
GET http://localhost:8080/order/my
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

The Gateway's `JwtAuthenticationFilter`:
1. Extracts `eyJhbGciOiJIUzI1NiJ9...` from the header
2. Calls `jwtUtil.validateToken(token)`:
   - Parses the token with the signing key
   - Verifies the HMAC-SHA256 signature matches
   - Checks that `exp` is after the current time
   - If any check fails → throws exception → 401 UNAUTHORIZED
3. Extracts `email` from `sub` claim → "sarthak@gmail.com"
4. Extracts `role` from custom claim → "CUSTOMER"

### 6.5 Header Propagation to Services

After validation, the Gateway adds headers:

```java
ServerHttpRequest request = exchange.getRequest().mutate()
    .headers(headers -> {
        headers.set("X-User-Email", "sarthak@gmail.com");
        headers.set("X-User-Role", "CUSTOMER");
    })
    .build();
```

The request forwarded to ORDER-SERVICE now has:
```
GET /order/my
X-User-Email: sarthak@gmail.com
X-User-Role: CUSTOMER
Authorization: Bearer eyJhbG...   (original header also forwarded)
```

### 6.6 How Services Trust the Request

Services don't validate JWT themselves. They simply read the headers:

```java
@GetMapping("/my")
public List<Order> myOrders(@RequestHeader("X-User-Email") String email) {
    // 'email' is trusted because the Gateway validated the JWT
    return service.getUserOrders(email);
}
```

**Why is this safe?**
- Services are NOT exposed to the internet directly
- Only the API Gateway (port 8080) is externally accessible
- Internal services communicate only through the internal network
- A client cannot forge `X-User-Email` because:
  - If they call the Gateway, the Gateway overwrites the header with the JWT-extracted value
  - If they try to call services directly, services are on internal ports (not publicly accessible)

---

## 7. Resilience and Fault Tolerance

### 7.1 Circuit Breaker Usage

Circuit breakers are implemented in services that call other services via Feign. The following services use circuit breakers:

| Service | Protects Against | Fallback Behavior |
|---------|-----------------|-------------------|
| ORDER-SERVICE | USER-SERVICE failure | Returns dummy user (id=0) |
| ORDER-SERVICE | CAR-SERVICE failure | Returns empty car list |
| ORDER-SERVICE | PAYMENT-SERVICE failure | Returns "FAILED_FALLBACK" status |
| ADMIN-SERVICE | USER-SERVICE failure | Returns empty user list |
| RATING-SERVICE | USER-SERVICE failure | Returns dummy user (id=0) |
| INVOICE-SERVICE | USER-SERVICE failure | Returns dummy user with email only |

### 7.2 Retry Logic

Retry is configured to attempt 3 times before giving up:

```java
@Retry(name = "userService")    // Retry 3 times with backoff
@CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
public User getUser(String email) {
    return userClient.getUserByEmail(email);
}
```

The retry configuration (via Config Server properties):
```properties
resilience4j.retry.instances.userService.max-attempts=3
resilience4j.retry.instances.userService.wait-duration=1s
```

Execution order: Retry wraps the CircuitBreaker. So the flow is:
```
1st attempt → fails → retry (wait 1s)
2nd attempt → fails → retry (wait 1s)
3rd attempt → fails → circuit breaker records failure → fallback called
```

### 7.3 Timeout Handling

Timeout prevents requests from hanging indefinitely:
```properties
resilience4j.timelimiter.instances.userService.timeout-duration=3s
```

If USER-SERVICE doesn't respond within 3 seconds, the call is cancelled and the fallback is invoked.

### 7.4 Fallback Methods

Every circuit breaker has a fallback method that returns a graceful degraded response:

```java
// ORDER-SERVICE: User fallback
public User userFallback(String email, Exception e) {
    User u = new User();
    u.setId(0L);
    u.setEmail("fallback@gmail.com");
    return u;
}

// ORDER-SERVICE: Car fallback
public List<Car> carFallback(Long userId, Exception e) {
    return List.of();    // Empty list
}

// ORDER-SERVICE: Payment fallback
public Payment paymentFallback(Long orderId, Double amount, Exception e) {
    Payment p = new Payment();
    p.setStatus("FAILED_FALLBACK");
    return p;
}

// ADMIN-SERVICE: Users fallback
public List<?> usersFallback(Exception e) {
    return List.of();    // Empty list
}

// ADMIN-SERVICE: Report fallback
public String reportFallback(Exception e) {
    return "Report unavailable: User service is down";
}
```

---

## 8. Event-Driven Flow with RabbitMQ

### 8.1 Order to Notification Flow

```
┌──────────────┐    Publish Event      ┌──────────────┐    Consume Event     ┌──────────────┐
│ ORDER-SERVICE │ ──────────────────►   │   RabbitMQ   │ ──────────────────►  │ NOTIFICATION │
│              │                        │              │                      │   SERVICE    │
│ createOrder()│  OrderEvent:           │ order.exchange│ Queue: order_queue   │              │
│ payForOrder()│  {                     │              │                      │ consume():   │
│              │    orderId: 1,         │ Routing Key: │                      │ - Save to DB │
│              │    email: "x@y.com",   │ order.routing│                      │ - Log email  │
│              │    amount: 500.0,      │              │                      │              │
│              │    status: "CREATED"   │              │                      │              │
│              │  }                     │              │                      │              │
└──────────────┘                        └──────────────┘                      └──────────────┘
```

### 8.2 How Events are Published and Consumed

**Publishing (ORDER-SERVICE):**

When an order is created or a payment completes, ORDER-SERVICE publishes an event:

```java
// In OrderService.createOrder()
OrderEvent event = new OrderEvent(
    savedOrder.getId(),      // orderId
    email,                   // customer email
    500.0,                   // amount
    "CREATED"               // status
);
publisher.sendOrderEvent(event);

// In OrderPublisher
public void sendOrderEvent(OrderEvent event) {
    rabbitTemplate.convertAndSend(
        "order.exchange",     // Exchange name
        "order.routing",      // Routing key
        event                 // Message (serialized to JSON)
    );
}
```

The `RabbitMQConfig` sets up:
- A **DirectExchange** named `order.exchange`
- A **Queue** named `order_queue`
- A **Binding** that routes messages from the exchange to the queue using routing key `order.routing`
- A `Jackson2JsonMessageConverter` for JSON serialization

**Consuming (NOTIFICATION-SERVICE):**

The `NotificationListener` uses `@RabbitListener` to consume messages from the queue:

```java
@RabbitListener(queues = "order_queue")
public void consume(OrderEvent event) {
    // Build notification message
    String message = "Order ID: " + event.getOrderId()
            + " | Status: " + event.getStatus()
            + " | Amount: " + event.getAmount();

    // Save to database
    service.save(event.getEmail(), message);

    // Simulate email (log to console)
    System.out.println("EMAIL SENT to: " + event.getEmail());
}
```

### 8.3 Why Async is Used

1. **Decoupling**: ORDER-SERVICE doesn't know or care about NOTIFICATION-SERVICE. It just publishes events. Any number of consumers can listen.

2. **Resilience**: If NOTIFICATION-SERVICE is down, messages wait in the RabbitMQ queue. When it comes back up, it processes all pending messages. No notifications are lost.

3. **Performance**: ORDER-SERVICE doesn't wait for the notification to be sent. The order is created immediately, and the notification happens asynchronously in the background.

4. **Scalability**: If notifications become a bottleneck, you can spin up more NOTIFICATION-SERVICE instances. RabbitMQ distributes messages across consumers automatically.

---

## 9. Rate Limiting with Redis

### 9.1 Why Rate Limiting is Needed

Without rate limiting, a single user or bot could:
- Make 10,000 requests per second, overloading the system
- Perform a DDoS attack by flooding the API
- Scrape all data by making rapid successive requests
- Monopolize resources, degrading performance for other users

### 9.2 How Redis Works with Gateway

Spring Cloud Gateway's `RequestRateLimiter` filter uses Redis as a backing store:

```
Request arrives at Gateway
        │
        ▼
KeyResolver extracts key (email or IP)
        │
        ▼
Check Redis: "rate_limit:{key}" → current token count
        │
        ├── tokens > 0 → Allow request, decrement counter
        │
        └── tokens = 0 → Reject with 429 Too Many Requests
```

Redis stores rate limit data as key-value pairs:
```
Key: "request_rate_limiter.{email}.tokens"
Value: 15 (remaining tokens)
TTL: auto-expiring

Key: "request_rate_limiter.{email}.timestamp"
Value: 1712400000
```

### 9.3 Configuration Explanation

The Gateway's `RateLimiterConfig`:

```java
@Bean
public KeyResolver userKeyResolver() {
    return exchange -> {
        // Authenticated users → rate limit by email
        String email = exchange.getRequest().getHeaders().getFirst("X-User-Email");
        if (email != null) return Mono.just(email);

        // Anonymous users → rate limit by IP
        return Mono.just(
            exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
        );
    };
}
```

Route-level rate limit configuration:
```properties
# 10 requests per second, burst up to 20
redis-rate-limiter.replenishRate=10
redis-rate-limiter.burstCapacity=20
```

---

## 10. Distributed Tracing with Zipkin

### 10.1 How Tracing Works

Every service includes Micrometer Tracing (Brave) dependencies:
```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
```

Configuration (via Config Server):
```properties
management.tracing.sampling.probability=1.0    # Trace 100% of requests
management.zipkin.tracing.endpoint=http://localhost:9411/api/v2/spans
```

### 10.2 How Requests are Tracked Across Services

1. Client sends request to Gateway
2. Brave generates a **Trace ID** (e.g., `abc123`) and **Span ID** (e.g., `span1`)
3. Gateway processes the request (Span 1)
4. Gateway forwards to ORDER-SERVICE with trace headers:
   ```
   X-B3-TraceId: abc123
   X-B3-SpanId: span1
   X-B3-ParentSpanId: (none)
   ```
5. ORDER-SERVICE creates a new Span (Span 2) with the same Trace ID
6. ORDER-SERVICE calls USER-SERVICE via Feign - Feign automatically propagates trace headers
7. USER-SERVICE creates Span 3 with Trace ID `abc123`
8. All spans are reported to Zipkin server

In Zipkin UI, you see the complete trace:
```
Trace: abc123
├── Gateway (0-250ms)
│   └── ORDER-SERVICE (10-240ms)
│       ├── USER-SERVICE (20-50ms)
│       ├── CAR-SERVICE (55-100ms)
│       └── PAYMENT-SERVICE (105-230ms)
```

### 10.3 Setting Up Zipkin

```bash
# Run Zipkin via Docker
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin

# Access UI
open http://localhost:9411
```

In the Zipkin UI:
1. Select a service from the dropdown
2. Click "Run Query" to find traces
3. Click on a trace to see the full call chain
4. Identify bottlenecks by examining span durations

---

## 11. Monitoring with Prometheus and Grafana

### 11.1 Prometheus Configuration

Prometheus scrapes metrics from all services at regular intervals. Our configuration:

```yaml
global:
  scrape_interval: 15s    # Scrape every 15 seconds

scrape_configs:
  - job_name: 'api-gateway'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']

  - job_name: 'user-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8081']

  - job_name: 'order-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8083']

  # ... similar config for all 9 services
```

Each service exposes metrics via the Actuator endpoint:
```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

```properties
management.endpoints.web.exposure.include=*
management.endpoint.prometheus.enabled=true
```

### 11.2 Grafana Dashboards

Grafana connects to Prometheus as a data source and visualizes metrics. A custom dashboard (`grafana-carwash-dashboard.json`) is provided with panels for:

- Request rate per service
- Response time percentiles (p50, p95, p99)
- Error rate
- JVM memory usage
- Thread count
- HTTP status code distribution

### 11.3 Metrics Exposed

Each service exposes standard Spring Boot Actuator + Micrometer metrics:

- `http_server_requests_seconds` - Request duration histogram
- `jvm_memory_used_bytes` - JVM memory usage
- `jvm_threads_live_threads` - Active thread count
- `process_cpu_usage` - CPU utilization
- `spring_data_repository_invocations` - Database query metrics
- `resilience4j_circuitbreaker_state` - Circuit breaker status
- `rabbitmq_consumed_total` - RabbitMQ message consumption count

---

## 12. Complete Request Flow Walkthrough

### 12.1 Scenario: User Registers, Creates Order, Pays, Gets Notification

This is the complete end-to-end flow of a customer using the system:

#### Step 1: User Registers

```http
POST http://localhost:8080/auth/register
Content-Type: application/json

{
  "name": "Sarthak Pawar",
  "email": "sarthak@gmail.com",
  "phone": "9876543210",
  "password": "1234",
  "role": "CUSTOMER"
}
```

**Flow:**
```
Client → Gateway (/auth = public, no JWT check)
       → USER-SERVICE (8081)
       → AuthService.register()
       → Password hashed with BCrypt
       → User saved to MySQL (user_db)
       → JWT generated with email + role
       ← Response: { token: "Bearer eyJ...", email: "sarthak@gmail.com", role: "CUSTOMER" }
```

#### Step 2: User Adds a Car

```http
POST http://localhost:8080/car/add
Authorization: Bearer eyJhbG...
Content-Type: application/json

{
  "brand": "BMW",
  "model": "X5",
  "color": "Black",
  "numberPlate": "MH12AB1234"
}
```

**Flow:**
```
Client → Gateway (JWT validated, X-User-Email + X-User-Role headers added)
       → CAR-SERVICE (8082)
       → CarController.addCar(req, email="sarthak@gmail.com")
       → CarService.addCar(req, userId=1)
       → Car saved to MySQL (car_db)
       ← Response: { id: 1, userId: 1, brand: "BMW", model: "X5", ... }
```

#### Step 3: User Creates a Wash Order

```http
POST http://localhost:8080/order/create
Authorization: Bearer eyJhbG...
Content-Type: application/json

{
  "carId": 1,
  "serviceType": "PREMIUM"
}
```

**Flow:**
```
Client → Gateway (JWT validated)
       → ORDER-SERVICE (8083)
       → OrderController.createOrder(req, email="sarthak@gmail.com")
       → OrderService.createOrder():
           │
           ├── Feign → USER-SERVICE: GET /user/email?email=sarthak@gmail.com
           │   ← { id: 1, email: "sarthak@gmail.com" }
           │
           ├── Feign → CAR-SERVICE: GET /car/user/1
           │   ← [{ id: 1, brand: "BMW", ... }]
           │
           ├── Validates carId=1 belongs to userId=1 ✓
           │
           ├── Saves Order to MySQL (order_db):
           │   { id: 1, userId: 1, carId: 1, serviceType: "PREMIUM", status: CREATED }
           │
           └── Publishes to RabbitMQ:
               OrderEvent { orderId: 1, email: "sarthak@gmail.com", amount: 500.0, status: "CREATED" }
               │
               └── NOTIFICATION-SERVICE consumes event
                   → Saves: "Order ID: 1 | Status: CREATED | Amount: 500.0"
                   → Logs: "EMAIL SENT to: sarthak@gmail.com"

       ← Response: { id: 1, userId: 1, carId: 1, serviceType: "PREMIUM", status: "CREATED" }
```

#### Step 4: Admin Assigns a Washer

```http
PUT http://localhost:8080/order/assign/1?washerId=2&scheduledAt=2026-04-07T10:00:00
Authorization: Bearer eyJhbG... (admin token)
```

**Flow:**
```
Client → Gateway (JWT validated, X-User-Role: ADMIN)
       → ORDER-SERVICE (8083)
       → OrderService.assignOrder(1, 2, "2026-04-07T10:00:00")
       → Order updated: status=ASSIGNED, washerId=2, scheduledAt=2026-04-07T10:00:00
       ← Response: { id: 1, status: "ASSIGNED", washerId: 2, scheduledAt: "2026-04-07T10:00:00" }
```

#### Step 5: User Pays for the Order

```http
POST http://localhost:8080/order/pay/1?amount=500.0
Authorization: Bearer eyJhbG...
```

**Flow:**
```
Client → Gateway (JWT validated)
       → ORDER-SERVICE (8083)
       → OrderService.payForOrder(1, 500.0, "sarthak@gmail.com"):
           │
           ├── Feign → USER-SERVICE: verify user
           │
           ├── Feign → PAYMENT-SERVICE: POST /payment/pay?orderId=1&amount=500.0
           │   ← { orderId: 1, amount: 500.0, status: "SUCCESS" }
           │
           ├── Payment SUCCESS → Order status updated to COMPLETED
           │
           └── Publishes to RabbitMQ:
               OrderEvent { orderId: 1, email: "sarthak@gmail.com", amount: 500.0, status: "COMPLETED" }
               │
               └── NOTIFICATION-SERVICE consumes event
                   → Saves: "Order ID: 1 | Status: COMPLETED | Amount: 500.0"
                   → Logs: "EMAIL SENT to: sarthak@gmail.com"

       ← Response: "Payment SUCCESS"
```

#### Step 6: User Leaves a Review

```http
POST http://localhost:8080/review/add
Authorization: Bearer eyJhbG...
Content-Type: application/json

{
  "orderId": 1,
  "rating": 5,
  "comment": "Excellent service!"
}
```

**Flow:**
```
Client → Gateway (JWT validated)
       → RATING-SERVICE (8086)
       → ReviewService.addReview(review, "sarthak@gmail.com")
       → Feign → USER-SERVICE: get user ID
       → Validates rating is 1-5
       → Saves review to MySQL (rating_db)
       ← Response: { id: 1, userId: 1, orderId: 1, rating: 5, comment: "Excellent service!" }
```

#### Step 7: User Gets Invoice PDF

```http
GET http://localhost:8080/invoice/pdf?orderId=1&amount=500.0
Authorization: Bearer eyJhbG...
```

**Flow:**
```
Client → Gateway (JWT validated)
       → INVOICE-SERVICE (8087)
       → InvoiceService.generatePdf(1, 500.0, "sarthak@gmail.com")
       → Feign → USER-SERVICE: get user details
       → Calculates: base=500, platform fee=75, CGST=51.75, SGST=51.75, total=678.50
       → Generates PDF using OpenPDF
       ← Response: PDF binary (Content-Type: application/pdf)
```

---

## 13. Common Errors and Debugging Guide

### 13.1 403 Forbidden Issues

**Root Causes:**

1. **Missing Authorization header**: Client forgot to include `Authorization: Bearer <token>`
   ```
   Fix: Add the header to every request (except public endpoints)
   ```

2. **Expired JWT**: Token has expired (after 24 hours)
   ```
   Fix: Login again to get a fresh token
   ```

3. **Wrong JWT secret**: Gateway and USER-SERVICE use different secrets
   ```
   Fix: Ensure jwt.secret in Config Server is the same for all services
   ```

4. **Spring Security blocking requests in services**: If Spring Security is active in a service, it may require authentication
   ```
   Fix: Set .anyRequest().permitAll() in SecurityFilterChain
         OR remove spring-boot-starter-security from the service
   ```

5. **ADMIN-SERVICE role check**: Non-admin user trying to access admin endpoints
   ```
   Fix: Use admin credentials to access /admin/** endpoints
   ```

### 13.2 JWT Issues

**Problem: "JWT signature does not match"**
```
Cause: The signing key used to create the token differs from the key used to validate it.
Fix: Ensure all services use the same jwt.secret from Config Server.
     Check that Config Server is running and the property exists in the Git repo.
```

**Problem: "JWT expired"**
```
Cause: Token was issued more than 24 hours ago.
Fix: Call POST /auth/login to get a fresh token.
```

**Problem: "Malformed JWT"**
```
Cause: Token format is wrong (missing parts, extra characters).
Fix: Ensure the client sends exactly "Bearer <token>" with no extra spaces.
     Copy the token directly from the login response.
```

### 13.3 Feign Header Forwarding Issues

**Problem: Feign calls fail with 401/403**
```
Cause: FeignConfig is not forwarding X-User-Email / X-User-Role headers.
Fix: Ensure FeignConfig bean is defined in the service.
     Ensure @FeignClient specifies configuration = FeignConfig.class.
     Ensure RequestContextHolder has the original request attributes.
```

**Problem: "RequestContextHolder.getRequestAttributes() returns null"**
```
Cause: Feign call is made from a non-web thread (e.g., async or scheduled task).
Fix: If calling Feign from an async context, manually set the request context
     OR pass headers as parameters.
```

### 13.4 Gateway Errors

**Problem: 404 Not Found for valid endpoints**
```
Cause: Route not configured in Config Server for this path.
Fix: Check API-GATEWAY.properties in the config Git repo.
     Ensure the route pattern matches (e.g., /order/** not /order).
```

**Problem: 503 Service Unavailable**
```
Cause: Target service is not registered in Eureka.
Fix: Check Eureka dashboard (http://localhost:8761).
     Ensure the target service is running and registered.
     Verify spring.application.name matches the route's lb:// target.
```

### 13.5 Redis Rate Limiter Errors

**Problem: "Unable to connect to Redis"**
```
Cause: Redis is not running.
Fix: docker run -d -p 6379:6379 --name redis redis
```

**Problem: "No qualifying bean of type KeyResolver"**
```
Cause: RateLimiterConfig class is missing or not scanned.
Fix: Ensure RateLimiterConfig is in the same package as ApiGatewayApplication.
     Ensure it has @Configuration annotation.
```

### 13.6 RabbitMQ Connection Issues

**Problem: "Connection refused to localhost:5672"**
```
Cause: RabbitMQ is not running.
Fix: docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:3-management
```

**Problem: "Queue not found"**
```
Cause: Queue hasn't been created yet.
Fix: Ensure both ORDER-SERVICE (producer) and NOTIFICATION-SERVICE (consumer)
     have matching queue names ("order_queue") in their RabbitMQ configs.
     Start both services - the queue is auto-created on first connection.
```

### 13.7 Step-by-Step Debugging Guide

When something goes wrong, follow this systematic approach:

```
Step 1: Check the Gateway logs
  - Is the request reaching the Gateway?
  - Is JWT validation passing?
  - What headers are being set?
  - Which service is it routing to?

Step 2: Check Eureka (http://localhost:8761)
  - Is the target service registered?
  - Is it showing as UP?
  - How many instances are running?

Step 3: Check the target service logs
  - Is the request reaching the service?
  - What headers are present?
  - Is there a stack trace?

Step 4: Check Zipkin (http://localhost:9411)
  - Find the trace by time range
  - See which service calls succeeded/failed
  - Identify the failing span

Step 5: Check RabbitMQ (http://localhost:15672)
  - Are messages being published?
  - Are messages stuck in the queue?
  - Is the consumer connected?

Step 6: Check Config Server
  - GET http://localhost:8888/ORDER-SERVICE/default
  - Are the properties correct?
  - Is the Git repo accessible?
```

**Useful Log Lines to Add for Debugging:**
```java
// In any controller - log incoming headers
@GetMapping("/my")
public List<Order> myOrders(@RequestHeader("X-User-Email") String email) {
    System.out.println(">>> X-User-Email: " + email);
    System.out.println(">>> X-User-Role: " + request.getHeader("X-User-Role"));
    return service.getUserOrders(email);
}

// In FeignConfig - log header forwarding
if (email != null) {
    System.out.println(">>> Feign forwarding X-User-Email: " + email);
    template.header("X-User-Email", email);
}

// In Gateway filter - log JWT validation
System.out.println(">>> Path: " + path);
System.out.println(">>> Token valid for: " + email + " [" + role + "]");
```

---

## 14. Setup and Run Guide

### 14.1 Prerequisites

| Software | Version | Purpose |
|----------|---------|---------|
| Java JDK | 21+ | Runtime for all services |
| Maven | 3.9+ | Build tool |
| Docker Desktop | Latest | Run infrastructure containers |
| MySQL | 8.0+ | Database (or use Docker) |
| Git | Latest | Clone the repository |
| Postman | Latest | API testing (optional) |

### 14.2 Docker Setup

Start the required infrastructure containers:

```bash
# Pull and run RabbitMQ (message broker)
docker run -d --hostname rabbitmq -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:3-management

# Pull and run Zipkin (distributed tracing)
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin

# Pull and run Redis (rate limiting)
docker run -d -p 6379:6379 --name redis redis
```

Verify containers are running:
```bash
docker ps
# Should show: rabbitmq, zipkin, redis
```

### 14.3 Running with Docker Compose (Recommended)

The project includes a `docker-compose.yml` that starts **everything** - infrastructure and all 10 services:

```bash
# Navigate to project directory
cd "C:\Users\Sarthak\OneDrive\Desktop\Car Wash System"

# Stop local MySQL if running (Docker Compose includes its own MySQL)
# On Windows:
net stop MySQL80

# Start all 17 containers
docker compose up -d

# Verify all containers are running
docker compose ps
```

This starts:
- **Infrastructure:** MySQL, RabbitMQ, Redis, Zipkin, Prometheus, Grafana
- **Discovery & Config:** Eureka Server, Config Server
- **Gateway:** API Gateway
- **Business Services:** User, Car, Order, Payment, Rating, Invoice, Admin, Notification

Wait about 2-3 minutes for all services to start and register with Eureka.

### 14.4 Running Locally (Without Docker Compose)

If you prefer running services from your IDE:

**Step 1: Start Infrastructure (Docker)**
```bash
docker run -d --hostname rabbitmq -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:3-management
docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin
docker run -d -p 6379:6379 --name redis redis
```

**Step 2: Start MySQL**
Ensure MySQL is running on port 3306 (Windows service or Docker).

**Step 3: Create Databases**
```sql
CREATE DATABASE user_db;
CREATE DATABASE car_db;
CREATE DATABASE order_db;
CREATE DATABASE rating_db;
CREATE DATABASE invoice_db;
CREATE DATABASE admin_db;
CREATE DATABASE notification_db;
```

**Step 4: Start Services in Order**
```bash
# Terminal 1: Eureka Server (must start first)
cd eureka-server && mvn spring-boot:run

# Terminal 2: Config Server (must start after Eureka)
cd config-server && mvn spring-boot:run

# Terminal 3: API Gateway
cd api-gateway && mvn spring-boot:run

# Terminals 4-11: All other services (any order)
cd user-service && mvn spring-boot:run
cd car-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
cd payment-service && mvn spring-boot:run
cd rating-service && mvn spring-boot:run
cd invoice-service && mvn spring-boot:run
cd admin-service && mvn spring-boot:run
cd notification-service && mvn spring-boot:run
```

### 14.5 Order of Startup

```
1. Eureka Server (8761)     ← MUST start first (service registry)
2. Config Server (8888)     ← MUST start second (provides config to all)
3. API Gateway (8080)       ← Start after Eureka + Config are ready
4. All other services       ← Any order (they register with Eureka)

Wait 30-60 seconds between steps 1-3 to ensure each is fully started.
```

### 14.6 Verifying the Setup

**Check Eureka Dashboard:**
```
Open: http://localhost:8761
All 9 services should appear under "Instances currently registered with Eureka"
```

**Check Config Server:**
```bash
curl http://localhost:8888/USER-SERVICE/default
# Should return JSON with configuration properties
```

**Test Login:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"sarthak@gmail.com","password":"1234"}'
# Should return { message, token, email, role }
```

**Test Protected Endpoint:**
```bash
curl http://localhost:8080/order/my \
  -H "Authorization: Bearer <token_from_login>"
# Should return user's orders (empty array if none)
```

**Access Monitoring:**
```
Zipkin:    http://localhost:9411
RabbitMQ:  http://localhost:15672  (guest/guest)
Prometheus: http://localhost:9090
Grafana:   http://localhost:3000   (admin/admin)
```

---

## 15. Docker and Containerization

### 15.1 Docker Compose Architecture

The `docker-compose.yml` defines 17 containers organized in a single network:

```
┌─────────────── Docker Network: carwash-network ───────────────┐
│                                                                │
│  ┌────────┐  ┌──────────┐  ┌───────┐  ┌────────┐            │
│  │ MySQL  │  │ RabbitMQ │  │ Redis │  │ Zipkin │            │
│  │ :3306  │  │ :5672    │  │ :6379 │  │ :9411  │            │
│  └────────┘  └──────────┘  └───────┘  └────────┘            │
│                                                                │
│  ┌────────────┐  ┌──────────────┐                            │
│  │ Eureka     │  │ Config Server│                            │
│  │ :8761      │  │ :8888        │                            │
│  └────────────┘  └──────────────┘                            │
│                                                                │
│  ┌─────────────┐                                              │
│  │ API Gateway │  ← Only externally exposed service           │
│  │ :8080       │                                              │
│  └─────────────┘                                              │
│                                                                │
│  ┌──────┐ ┌───────┐ ┌─────────┐ ┌───────┐ ┌───────┐        │
│  │ User │ │ Order │ │ Payment │ │  Car  │ │ Admin │        │
│  │ :8081│ │ :8083 │ │  :8084  │ │ :8082 │ │ :8088 │        │
│  └──────┘ └───────┘ └─────────┘ └───────┘ └───────┘        │
│                                                                │
│  ┌────────┐ ┌─────────┐ ┌──────────────┐                    │
│  │ Rating │ │ Invoice │ │ Notification │                    │
│  │ :8086  │ │  :8087  │ │    :8089     │                    │
│  └────────┘ └─────────┘ └──────────────┘                    │
│                                                                │
│  ┌────────────┐  ┌─────────┐                                 │
│  │ Prometheus │  │ Grafana │                                 │
│  │ :9090      │  │ :3001   │                                 │
│  └────────────┘  └─────────┘                                 │
└────────────────────────────────────────────────────────────────┘
```

### 15.2 Service Dependencies

Docker Compose `depends_on` with health checks ensure services start in the right order:

```yaml
eureka-server:
  # Starts first, no dependencies

config-server:
  depends_on:
    eureka-server:
      condition: service_healthy    # Wait for Eureka to be healthy

api-gateway:
  depends_on:
    config-server:
      condition: service_healthy    # Wait for Config Server
    redis:
      condition: service_healthy    # Wait for Redis (rate limiting)

user-service:
  depends_on:
    config-server:
      condition: service_healthy
    mysql:
      condition: service_healthy
    rabbitmq:
      condition: service_healthy

order-service:
  depends_on:
    config-server:
      condition: service_healthy
    mysql:
      condition: service_healthy
    rabbitmq:
      condition: service_healthy
```

### 15.3 Health Checks

Each container has a health check defined:

```yaml
mysql:
  healthcheck:
    test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
    interval: 10s
    timeout: 5s
    retries: 5

rabbitmq:
  healthcheck:
    test: ["CMD", "rabbitmq-diagnostics", "check_running"]
    interval: 10s
    timeout: 5s
    retries: 5

eureka-server:
  healthcheck:
    test: ["CMD", "curl", "-f", "http://localhost:8761/actuator/health"]
    interval: 15s
    timeout: 10s
    retries: 10
```

---

## 16. Kubernetes Deployment

### 16.1 Namespace

All resources are deployed in the `carwash` namespace:

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: carwash
  labels:
    app: carwash-system
```

### 16.2 Infrastructure Components

The `k8s/infrastructure.yml` deploys stateful infrastructure:

- **MySQL**: 1 replica with PersistentVolumeClaim for data persistence
- **RabbitMQ**: 1 replica with AMQP (5672) and management (15672) ports
- **Redis**: 1 replica with health checks
- **Zipkin**: 1 replica for distributed tracing

Each component has:
- Deployment with resource requests/limits
- ClusterIP Service for internal access
- Health probes (startup, readiness, liveness)

### 16.3 Service Discovery in K8s

In Kubernetes, services discover each other using Kubernetes DNS instead of Eureka:

```
user-service.carwash.svc.cluster.local → User Service pods
order-service.carwash.svc.cluster.local → Order Service pods
```

However, our architecture still uses Eureka for discovery because:
1. It allows the same code to work locally and in K8s
2. Eureka provides health monitoring and metadata
3. Spring Cloud LoadBalancer integrates with Eureka

### 16.4 Deployment Order

```bash
# 1. Create namespace
kubectl apply -f k8s/namespace.yml

# 2. Create secrets
kubectl apply -f k8s/secrets.yml

# 3. Deploy infrastructure (MySQL, RabbitMQ, Redis, Zipkin)
kubectl apply -f k8s/infrastructure.yml

# 4. Wait for infrastructure to be ready
kubectl get pods -n carwash --watch

# 5. Deploy discovery services (Eureka, Config Server)
kubectl apply -f k8s/discovery-config.yml

# 6. Deploy API Gateway
kubectl apply -f k8s/gateway.yml

# 7. Deploy all business services
kubectl apply -f k8s/services.yml

# 8. Deploy monitoring (Prometheus, Grafana)
kubectl apply -f k8s/monitoring.yml
```

### 16.5 Manifest Structure

```
k8s/
├── namespace.yml          # carwash namespace
├── secrets.yml            # MySQL credentials
├── infrastructure.yml     # MySQL, RabbitMQ, Redis, Zipkin
├── discovery-config.yml   # Eureka Server, Config Server
├── gateway.yml            # API Gateway (LoadBalancer type)
├── services.yml           # All 8 business services
└── monitoring.yml         # Prometheus, Grafana
```

The API Gateway uses `LoadBalancer` service type (externally accessible), while all other services use `ClusterIP` (internal only).

---

## 17. CI/CD with GitHub Actions

### 17.1 Pipeline Overview

The `.github/workflows/build-and-push.yml` defines a three-stage pipeline:

```
┌─────────────────┐     ┌──────────────────┐     ┌─────────────────┐
│ detect-changes   │ ──► │ build-and-push   │ ──► │ deploy-k8s      │
│                  │     │                  │     │                  │
│ Which services   │     │ Build Docker     │     │ kubectl apply    │
│ changed?         │     │ images for       │     │ manifests        │
│                  │     │ changed services │     │ (main branch     │
│                  │     │ Push to DockerHub│     │  only)           │
└─────────────────┘     └──────────────────┘     └─────────────────┘
```

### 17.2 Change Detection

Uses `paths-filter` action to detect which service directories changed:

```yaml
- uses: dorny/paths-filter@v2
  with:
    filters: |
      eureka-server: 'eureka-server/**'
      config-server: 'config-server/**'
      api-gateway: 'api-gateway/**'
      user-service: 'user-service/**'
      order-service: 'order-service/**'
      # ... all services
```

Only changed services are built and pushed, saving CI time and Docker Hub bandwidth.

### 17.3 Build and Push

For each changed service:
1. Set up Java 21
2. Run `mvn clean package -DskipTests`
3. Build Docker image using Dockerfile
4. Push to Docker Hub with `latest` tag

### 17.4 Kubernetes Deployment

On pushes to `main` branch only:
1. Configure kubectl with cluster credentials
2. Apply Kubernetes manifests in order:
   - namespace → secrets → infrastructure → discovery-config → gateway → services → monitoring

---

## 18. Project Structure

### 18.1 Overall Folder Structure

```
Car Wash System/
│
├── .github/
│   └── workflows/
│       └── build-and-push.yml          # CI/CD pipeline
│
├── k8s/                                 # Kubernetes manifests
│   ├── namespace.yml
│   ├── secrets.yml
│   ├── infrastructure.yml
│   ├── discovery-config.yml
│   ├── gateway.yml
│   ├── services.yml
│   └── monitoring.yml
│
├── eureka-server/                       # Service Registry
│   ├── src/main/java/com/eurekaserver/
│   │   └── EurekaServerApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── Dockerfile
│   └── pom.xml
│
├── config-server/                       # Centralized Configuration
│   ├── src/main/java/com/configserver/
│   │   └── ConfigServerApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── Dockerfile
│   └── pom.xml
│
├── api-gateway/                         # API Gateway
│   ├── src/main/java/com/apigateway/
│   │   ├── ApiGatewayApplication.java
│   │   ├── JwtAuthenticationFilter.java # JWT validation
│   │   ├── JwtUtil.java                 # JWT utilities
│   │   └── RateLimiterConfig.java       # Redis rate limiting
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── Dockerfile
│   └── pom.xml
│
├── user-service/                        # Authentication & Users
│   ├── src/main/java/com/userservice/
│   │   ├── CONFIGS/
│   │   │   └── SecurityConfig.java      # Spring Security config
│   │   ├── Controller/
│   │   │   ├── AuthController.java      # Login/Register
│   │   │   ├── UserController.java      # User lookup
│   │   │   └── TestController.java      # Test endpoints
│   │   ├── DTO/
│   │   │   ├── AuthResponse.java
│   │   │   ├── LoginRequest.java
│   │   │   └── RegisterRequest.java
│   │   ├── ENtity/
│   │   │   └── User.java
│   │   ├── Enum/
│   │   │   └── Roles.java
│   │   ├── Repository/
│   │   │   └── UserRepository.java
│   │   ├── Security/
│   │   │   ├── AuthService.java         # Registration/Login logic
│   │   │   ├── CustomUserDetailsService.java
│   │   │   ├── JwtAuthFilter.java       # Disabled (Gateway handles)
│   │   │   └── JwtService.java          # Token generation
│   │   └── UserServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── Dockerfile
│   └── pom.xml
│
├── order-service/                       # Core Business Logic
│   ├── src/main/java/com/carwash/orderservice/
│   │   ├── configs/
│   │   │   ├── FeignConfig.java         # Header forwarding
│   │   │   ├── RabbitMQConfig.java      # Queue/Exchange setup
│   │   │   └── RequestContextHolderUtil.java
│   │   ├── Controller/
│   │   │   └── OrderController.java
│   │   ├── DTO/
│   │   │   ├── Car.java
│   │   │   ├── OrderEvent.java          # RabbitMQ event
│   │   │   ├── OrderRequest.java
│   │   │   ├── Payment.java
│   │   │   └── User.java
│   │   ├── Entity/
│   │   │   ├── CarClient.java           # Feign client
│   │   │   ├── Order.java
│   │   │   ├── PaymentClient.java       # Feign client
│   │   │   └── UserClient.java          # Feign client
│   │   ├── Enum/
│   │   │   └── OrderStatus.java
│   │   ├── Repository/
│   │   │   └── OrderRepository.java
│   │   ├── Service/
│   │   │   ├── JwtService.java
│   │   │   ├── OrderPublisher.java      # RabbitMQ publisher
│   │   │   └── OrderService.java        # Core logic + resilience
│   │   └── OrderServiceApplication.java
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── bootstrap.properties
│   ├── Dockerfile
│   └── pom.xml
│
├── payment-service/                     # Payment Processing
│   ├── src/main/java/com/paymentservice/
│   │   ├── Payment.java
│   │   ├── PaymentClient.java
│   │   ├── PaymentController.java
│   │   ├── PaymentService.java
│   │   └── PaymentServiceApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── car-service/                         # Vehicle Management
│   ├── src/main/java/com/carservice/
│   │   ├── Controller/CarController.java
│   │   ├── DTO/CarRequest.java
│   │   ├── Entity/Car.java
│   │   ├── Repository/CarRepository.java
│   │   ├── Service/CarService.java
│   │   ├── Service/JwtService.java
│   │   └── CarServiceApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── admin-service/                       # Admin Management
│   ├── src/main/java/com/adminservice/
│   │   ├── Controller/AdminController.java
│   │   ├── DTO/
│   │   │   ├── FeignConfig.java
│   │   │   ├── User.java
│   │   │   └── UserClient.java
│   │   ├── Entity/ServicePlan.java
│   │   ├── Repository/ServicePlanRepository.java
│   │   ├── Service/
│   │   │   ├── AdminService.java
│   │   │   └── JwtService.java
│   │   └── AdminServiceApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── rating-service/                      # Reviews & Ratings
│   ├── src/main/java/com/ratingservice/
│   │   ├── Controller/ReviewController.java
│   │   ├── DTO/
│   │   │   ├── FeignConfig.java
│   │   │   ├── User.java
│   │   │   └── UserClient.java
│   │   ├── Entity/Review.java
│   │   ├── Repository/ReviewRepository.java
│   │   ├── Services/
│   │   │   ├── JwtService.java
│   │   │   └── ReviewService.java
│   │   └── RatingServiceApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── invoice-service/                     # Invoice Generation
│   ├── src/main/java/com/invoiceservice/
│   │   ├── Controller/InvoiceController.java
│   │   ├── DTO/
│   │   │   ├── FeignConfig.java
│   │   │   ├── JwtContextHolder.java
│   │   │   ├── User.java
│   │   │   └── UserClient.java
│   │   ├── Entity/Invoice.java
│   │   ├── Repository/InvoiceRepository.java
│   │   ├── Service/
│   │   │   ├── InvoiceService.java
│   │   │   └── JwtService.java
│   │   └── InvoiceServiceApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── notification-service/                # Async Notifications
│   ├── src/main/java/com/notification_service/
│   │   ├── Controller/NotificationController.java
│   │   ├── DTO/
│   │   │   ├── OrderEvent.java
│   │   │   └── RabbitConfig.java
│   │   ├── Entity/Notification.java
│   │   ├── listener/
│   │   │   └── NotificationListener.java  # RabbitMQ consumer
│   │   ├── Repository/NotificationRepository.java
│   │   ├── Service/NotificationService.java
│   │   └── NotificationServiceApplication.java
│   ├── Dockerfile
│   └── pom.xml
│
├── docker-compose.yml                   # Full system Docker Compose
├── docker-compose-monitoring.yml        # Prometheus + Grafana
├── prometheus.yml                       # Prometheus config
├── prometheus-docker.yml                # Prometheus Docker config
├── grafana-carwash-dashboard.json       # Grafana dashboard
├── push-to-dockerhub.sh               # Docker Hub push script
├── SETUP-GUIDE.md                      # Setup instructions
└── README.md                           # This file
```

### 18.2 Individual Service Structure

Every service follows a consistent layered architecture:

```
service-name/
├── src/main/java/com/servicename/
│   ├── Controller/          # REST endpoints (thin layer)
│   ├── Service/             # Business logic
│   ├── Repository/          # Data access (JPA)
│   ├── Entity/              # Database entities (JPA)
│   ├── DTO/                 # Data transfer objects
│   ├── Enum/                # Enumerations
│   ├── configs/             # Configuration classes (Feign, RabbitMQ)
│   └── ServiceNameApplication.java   # Main class
├── src/main/resources/
│   ├── application.properties    # Service config
│   └── bootstrap.properties      # Config Server bootstrap
├── Dockerfile
└── pom.xml
```

### 18.3 Naming Conventions

| Convention | Example | Used For |
|-----------|---------|----------|
| PascalCase | `OrderService`, `CarController` | Class names |
| camelCase | `createOrder`, `getUserByEmail` | Method names |
| SCREAMING_SNAKE | `ORDER-SERVICE`, `EXCHANGE` | Service names, constants |
| kebab-case | `api-gateway`, `user-service` | Directory names |
| camelCase | `orderId`, `serviceType` | Field names |
| PascalCase | `OrderStatus`, `Roles` | Enum names |
| SCREAMING_SNAKE | `CREATED`, `CUSTOMER` | Enum values |

---

## 19. API Reference

### 19.1 Authentication APIs

| Method | Endpoint | Auth | Body | Response |
|--------|----------|------|------|----------|
| POST | `/auth/register` | None | `{ name, email, phone, password, role }` | `{ message, token, email, role }` |
| POST | `/auth/login` | None | `{ email, password }` | `{ message, token, email, role }` |

**Register Example:**
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

**Login Example:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "sarthak@gmail.com",
    "password": "1234"
  }'
```

### 19.2 User APIs

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | `/user/email?email=x` | None (public) | Get user by email |
| GET | `/user/all` | None (public) | Get all users |

### 19.3 Car APIs

| Method | Endpoint | Auth | Body | Description |
|--------|----------|------|------|-------------|
| POST | `/car/add` | JWT | `{ brand, model, color, numberPlate }` | Add a car |
| GET | `/car/user/{userId}` | JWT | - | Get user's cars |
| PUT | `/car/update/{id}` | JWT | `{ brand, model, color, numberPlate }` | Update car |
| DELETE | `/car/delete/{id}` | JWT | - | Delete car |

### 19.4 Order APIs

| Method | Endpoint | Auth | Body/Params | Description |
|--------|----------|------|------------|-------------|
| POST | `/order/create` | JWT | `{ carId, serviceType }` | Create order |
| GET | `/order/my` | JWT | - | Get my orders |
| GET | `/order/{id}` | JWT | - | Get order by ID |
| PUT | `/order/assign/{id}` | JWT | `?washerId=X&scheduledAt=Y` | Assign washer |
| PUT | `/order/status/{id}` | JWT | `?status=COMPLETED` | Update status |
| DELETE | `/order/delete/{id}` | JWT | - | Delete order |
| POST | `/order/pay/{orderId}` | JWT | `?amount=X` | Pay for order |

### 19.5 Payment APIs

| Method | Endpoint | Auth | Params | Description |
|--------|----------|------|--------|-------------|
| POST | `/payment/pay` | Internal | `?orderId=X&amount=Y` | Process payment |

### 19.6 Rating APIs

| Method | Endpoint | Auth | Body | Description |
|--------|----------|------|------|-------------|
| POST | `/review/add` | JWT | `{ orderId, rating, comment }` | Add review |
| GET | `/review/my` | JWT | - | Get my reviews |
| GET | `/review/order/{orderId}` | None | - | Get reviews by order |

### 19.7 Invoice APIs

| Method | Endpoint | Auth | Params | Description |
|--------|----------|------|--------|-------------|
| POST | `/invoice/generate` | JWT | `?orderId=X&amount=Y` | Generate invoice |
| GET | `/invoice/pdf` | JWT | `?orderId=X&amount=Y` | Download PDF |

### 19.8 Admin APIs

| Method | Endpoint | Auth | Body | Description |
|--------|----------|------|------|-------------|
| GET | `/admin/users` | JWT (ADMIN) | - | List all users |
| POST | `/admin/plan` | JWT (ADMIN) | `{ name, price, description }` | Create service plan |
| GET | `/admin/plans` | JWT (ADMIN) | - | List all plans |
| GET | `/admin/report` | JWT (ADMIN) | - | Get system report |

### 19.9 Notification APIs

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | `/notification/all` | None (public) | Get all notifications |

---

## 20. Best Practices Used

### Architecture
- **Single Responsibility**: Each service handles one domain
- **Database per Service**: No shared databases between services
- **API Gateway Pattern**: Single entry point with centralized concerns
- **Event-Driven Architecture**: Async communication for non-critical flows
- **Service Discovery**: Dynamic service registration and lookup
- **Centralized Configuration**: One source of truth for all configs

### Security
- **Gateway Trust Pattern**: JWT validated once at the gateway
- **Stateless Authentication**: No server-side sessions
- **BCrypt Password Hashing**: Industry-standard password security
- **Header-Based Identity**: Services receive user info via trusted headers
- **Role-Based Access Control**: Admin endpoints check X-User-Role

### Resilience
- **Circuit Breaker**: Prevents cascading failures
- **Retry with Backoff**: Handles transient failures
- **Timeout**: Prevents thread exhaustion
- **Fallback Methods**: Graceful degradation when services are down
- **Async Messaging**: Notification failures don't affect core flows

### Code Quality
- **Lombok**: Reduces boilerplate (getters, setters, constructors)
- **DTO Pattern**: Separates API contracts from internal entities
- **Repository Pattern**: Clean data access layer
- **Configuration as Code**: All config in version-controlled properties
- **Consistent Package Structure**: Every service follows the same layout

### DevOps
- **Docker Compose**: One-command full system startup
- **Kubernetes Manifests**: Production-ready orchestration
- **CI/CD Pipeline**: Automated build, test, deploy
- **Health Checks**: Readiness and liveness probes
- **Resource Limits**: Memory and CPU constraints in K8s

### Observability
- **Distributed Tracing**: Zipkin tracks requests across services
- **Metrics Collection**: Prometheus scrapes all services
- **Dashboards**: Grafana visualizes system health
- **Actuator Endpoints**: Health, metrics, info exposed per service

---

## 21. Future Improvements

### Short-Term
- **Real Payment Gateway Integration**: Replace dummy payment with Stripe/Razorpay
- **Email Integration**: Replace console logging with JavaMail/SendGrid
- **Input Validation**: Add `@Valid` annotations and proper error responses
- **Global Exception Handler**: `@ControllerAdvice` for consistent error formats
- **Swagger/OpenAPI**: Auto-generated API documentation per service
- **CORS Configuration**: Proper cross-origin setup for frontend integration

### Medium-Term
- **OAuth2/OpenID Connect**: Replace custom JWT with Keycloak/Auth0
- **API Versioning**: `/v1/order/create` for backward compatibility
- **Caching**: Redis caching for frequently accessed data (user profiles, service plans)
- **Database Migrations**: Flyway/Liquibase for schema version control
- **Integration Tests**: End-to-end test suites with Testcontainers
- **WebSocket Notifications**: Real-time push notifications to clients

### Long-Term
- **Event Sourcing**: Store events instead of current state for audit trail
- **CQRS Pattern**: Separate read and write models for performance
- **Service Mesh**: Istio for advanced traffic management and security
- **Multi-Region Deployment**: Geographic distribution for global users
- **Auto-Scaling**: Kubernetes HPA based on metrics
- **gRPC**: Replace REST with gRPC for inter-service communication (performance)
- **GraphQL Gateway**: Single flexible API for frontend queries
- **Saga Pattern**: Distributed transactions for multi-service operations

---

## 22. Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 23. License

This project is open source and available under the [MIT License](LICENSE).

---

## Service Ports Quick Reference

| Service | Port | URL |
|---------|------|-----|
| API Gateway | 8080 | http://localhost:8080 |
| User Service | 8081 | Internal |
| Car Service | 8082 | Internal |
| Order Service | 8083 | Internal |
| Payment Service | 8084 | Internal |
| Rating Service | 8086 | Internal |
| Invoice Service | 8087 | Internal |
| Admin Service | 8088 | Internal |
| Notification Service | 8089 | Internal |
| Eureka Server | 8761 | http://localhost:8761 |
| Config Server | 8888 | http://localhost:8888 |
| Zipkin | 9411 | http://localhost:9411 |
| RabbitMQ | 5672/15672 | http://localhost:15672 |
| Redis | 6379 | - |
| Prometheus | 9090 | http://localhost:9090 |
| Grafana | 3000/3001 | http://localhost:3000 |

## Test Credentials

| Email | Password | Role |
|-------|----------|------|
| sarthak@gmail.com | 1234 | CUSTOMER |
| washer@gmail.com | 1234 | WASHER |
| admin@gmail.com | 1234 | ADMIN |

---

> Built with Java 21, Spring Boot 3.x, and Spring Cloud 2023.x by [Sarthak Pawar](https://github.com/sarthakpawar0912)
