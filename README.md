# On-Demand Car Wash System - Complete Microservices Backend

> A production-grade, enterprise-level microservices-based backend system for an On-Demand Car Wash platform, built with **Java 21**, **Spring Boot 3.x**, and **Spring Cloud 2023.x**. This project demonstrates real-world distributed systems architecture including service discovery, centralized configuration, API gateway pattern, event-driven communication, distributed tracing, circuit breakers, rate limiting, JWT authentication, containerization with Docker, orchestration with Kubernetes, and CI/CD with GitHub Actions.

---

# ========================================================================
# PART 1: DEEP CONCEPTS - THEORY, ARCHITECTURE & TECHNOLOGY EXPLANATION
# ========================================================================

---

# Table of Contents - Part 1: Concepts

- [Chapter 1: Microservices Architecture - Complete Guide](#chapter-1-microservices-architecture---complete-guide)
- [Chapter 2: Spring Boot - The Foundation](#chapter-2-spring-boot---the-foundation)
- [Chapter 3: Spring Cloud - The Microservices Toolkit](#chapter-3-spring-cloud---the-microservices-toolkit)
- [Chapter 4: API Gateway Pattern - Spring Cloud Gateway](#chapter-4-api-gateway-pattern---spring-cloud-gateway)
- [Chapter 5: Service Discovery - Netflix Eureka](#chapter-5-service-discovery---netflix-eureka)
- [Chapter 6: Centralized Configuration - Spring Cloud Config](#chapter-6-centralized-configuration---spring-cloud-config)
- [Chapter 7: Inter-Service Communication - OpenFeign](#chapter-7-inter-service-communication---openfeign)
- [Chapter 8: Event-Driven Architecture - RabbitMQ](#chapter-8-event-driven-architecture---rabbitmq)
- [Chapter 9: JWT Authentication - Complete Deep Dive](#chapter-9-jwt-authentication---complete-deep-dive)
- [Chapter 10: Spring Security in Microservices](#chapter-10-spring-security-in-microservices)
- [Chapter 11: Resilience4j - Circuit Breaker, Retry, Timeout](#chapter-11-resilience4j---circuit-breaker-retry-timeout)
- [Chapter 12: Distributed Tracing - Zipkin & Micrometer](#chapter-12-distributed-tracing---zipkin--micrometer)
- [Chapter 13: Rate Limiting - Redis](#chapter-13-rate-limiting---redis)
- [Chapter 14: Monitoring - Prometheus & Grafana](#chapter-14-monitoring---prometheus--grafana)
- [Chapter 15: Docker - Containerization Deep Dive](#chapter-15-docker---containerization-deep-dive)
- [Chapter 16: Kubernetes - Container Orchestration Deep Dive](#chapter-16-kubernetes---container-orchestration-deep-dive)
- [Chapter 17: CI/CD - GitHub Actions](#chapter-17-cicd---github-actions)
- [Chapter 18: Maven - Build Tool Deep Dive](#chapter-18-maven---build-tool-deep-dive)
- [Chapter 19: MySQL - Database in Microservices](#chapter-19-mysql---database-in-microservices)
- [Chapter 20: Lombok - Code Generation](#chapter-20-lombok---code-generation)

---

# Table of Contents - Part 2: Project Implementation

- [Chapter 21: Project Overview & Architecture](#chapter-21-project-overview--architecture)
- [Chapter 22: USER-SERVICE - File by File](#chapter-22-user-service---file-by-file)
- [Chapter 23: ORDER-SERVICE - File by File](#chapter-23-order-service---file-by-file)
- [Chapter 24: CAR-SERVICE - File by File](#chapter-24-car-service---file-by-file)
- [Chapter 25: PAYMENT-SERVICE - File by File](#chapter-25-payment-service---file-by-file)
- [Chapter 26: ADMIN-SERVICE - File by File](#chapter-26-admin-service---file-by-file)
- [Chapter 27: RATING-SERVICE - File by File](#chapter-27-rating-service---file-by-file)
- [Chapter 28: INVOICE-SERVICE - File by File](#chapter-28-invoice-service---file-by-file)
- [Chapter 29: NOTIFICATION-SERVICE - File by File](#chapter-29-notification-service---file-by-file)
- [Chapter 30: API-GATEWAY - File by File](#chapter-30-api-gateway---file-by-file)
- [Chapter 31: EUREKA-SERVER - File by File](#chapter-31-eureka-server---file-by-file)
- [Chapter 32: CONFIG-SERVER - File by File](#chapter-32-config-server---file-by-file)
- [Chapter 33: Docker Compose - Line by Line](#chapter-33-docker-compose---line-by-line)
- [Chapter 34: Kubernetes Manifests - File by File](#chapter-34-kubernetes-manifests---file-by-file)
- [Chapter 35: CI/CD Pipeline - Line by Line](#chapter-35-cicd-pipeline---line-by-line)
- [Chapter 36: Prometheus & Grafana Configuration](#chapter-36-prometheus--grafana-configuration)
- [Chapter 37: Complete Request Flow Walkthrough](#chapter-37-complete-request-flow-walkthrough)
- [Chapter 38: Common Errors & Debugging Guide](#chapter-38-common-errors--debugging-guide)
- [Chapter 39: Setup & Run Guide](#chapter-39-setup--run-guide)
- [Chapter 40: API Reference](#chapter-40-api-reference)
- [Chapter 41: Best Practices & Future Improvements](#chapter-41-best-practices--future-improvements)

---

# ========================================================================
# PART 1: DEEP CONCEPTS
# ========================================================================

---

## Chapter 1: Microservices Architecture - Complete Guide

### 1.1 What is Software Architecture?

Before understanding microservices, let's understand what software architecture means.

**Software architecture** is the high-level structure of a software system. It defines how the system is organized, how components communicate, and how data flows through the system. Think of it like the blueprint of a building - it shows the rooms, corridors, plumbing, and electrical wiring, but not the furniture inside each room.

There are several architectural styles:
1. **Monolithic Architecture** - Single deployable unit
2. **Layered Architecture** - Presentation → Business → Data layers
3. **Microservices Architecture** - Multiple independent services
4. **Event-Driven Architecture** - Components communicate via events
5. **Serverless Architecture** - Functions as a Service (FaaS)

### 1.2 What is Monolithic Architecture?

A **monolith** is an application where all the code lives in a single project, builds into a single artifact (like a .jar or .war file), and deploys as a single unit.

```
┌─────────────────────────────────────────────────────┐
│                 MONOLITHIC APPLICATION               │
│                                                      │
│  ┌──────────────┐  ┌──────────────┐  ┌────────────┐ │
│  │ User Module  │  │ Order Module │  │ Payment    │ │
│  │              │  │              │  │ Module     │ │
│  │ - AuthCtrl   │  │ - OrderCtrl  │  │ - PayCtrl  │ │
│  │ - UserSvc    │  │ - OrderSvc   │  │ - PaySvc   │ │
│  │ - UserRepo   │  │ - OrderRepo  │  │ - PayRepo  │ │
│  └──────┬───────┘  └──────┬───────┘  └──────┬─────┘ │
│         │                 │                  │       │
│  ┌──────▼─────────────────▼──────────────────▼─────┐ │
│  │            SINGLE SHARED DATABASE               │ │
│  │  users | orders | payments | cars | ratings ... │ │
│  └─────────────────────────────────────────────────┘ │
│                                                      │
│  Single .jar file → Single deployment → Single server│
└─────────────────────────────────────────────────────┘
```

**Advantages of Monolith:**
- Simple to develop initially
- Simple to test (one application)
- Simple to deploy (one artifact)
- Simple to debug (one process, one log file)
- In-process communication (method calls are nanoseconds, not milliseconds)

**Disadvantages of Monolith (why we moved to microservices):**
- **Tight Coupling**: Changing the payment logic might break the order logic
- **Single Point of Failure**: One bug crashes everything
- **Scaling Nightmare**: To handle more orders, you scale the ENTIRE app (including user, payment, etc.)
- **Deployment Risk**: Every deployment redeploys everything
- **Technology Lock-in**: Stuck with one language, one framework, one database
- **Team Bottleneck**: All developers work on the same codebase, causing merge conflicts

### 1.3 What is Microservices Architecture?

**Microservices architecture** decomposes a single application into a collection of small, autonomous services. Each service:

1. **Is a separate application** with its own codebase, build, and deployment
2. **Runs in its own process** (its own JVM in Java world)
3. **Owns its data** (has its own database)
4. **Communicates over the network** (HTTP REST, gRPC, or messaging)
5. **Can be developed by a separate team**
6. **Can be deployed independently**
7. **Can be scaled independently**
8. **Can use different technologies** (different languages, databases, frameworks)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     MICROSERVICES ARCHITECTURE                          │
│                                                                         │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  │
│  │ USER-SERVICE │  │ORDER-SERVICE│  │PAYMENT-SVC  │  │ CAR-SERVICE │  │
│  │             │  │             │  │             │  │             │  │
│  │ Own JVM     │  │ Own JVM     │  │ Own JVM     │  │ Own JVM     │  │
│  │ Port 8081   │  │ Port 8083   │  │ Port 8084   │  │ Port 8082   │  │
│  │ user_db     │  │ order_db    │  │ (no db)     │  │ car_db      │  │
│  └──────┬──────┘  └──────┬──────┘  └─────────────┘  └──────┬──────┘  │
│         │                │                                   │         │
│         ▼                ▼                                   ▼         │
│  ┌──────────┐     ┌──────────┐                        ┌──────────┐   │
│  │ MySQL    │     │ MySQL    │                        │ MySQL    │   │
│  │ user_db  │     │ order_db │                        │ car_db   │   │
│  └──────────┘     └──────────┘                        └──────────┘   │
│                                                                        │
│  Each service has its OWN database - no shared databases!              │
│  Services communicate via HTTP (Feign) or messaging (RabbitMQ)        │
└─────────────────────────────────────────────────────────────────────────┘
```

### 1.4 Monolith vs Microservices - Detailed Comparison

| Aspect | Monolith | Microservices |
|--------|----------|---------------|
| **Architecture** | Single deployable unit | Multiple independent services |
| **Codebase** | One large codebase | Multiple small codebases |
| **Database** | Single shared database | Database per service |
| **Deployment** | All-or-nothing | Independent deployment |
| **Scaling** | Vertical (bigger server) | Horizontal (more instances) |
| **Technology** | Single tech stack | Polyglot (multiple technologies) |
| **Team Structure** | All developers on same code | Teams own services |
| **Communication** | In-process (method calls) | Network (HTTP/messaging) |
| **Failure** | One failure crashes all | Failures are isolated |
| **Development Speed** | Fast initially, slow later | Slower initially, fast later |
| **Testing** | Simple unit + integration | Complex distributed testing |
| **Debugging** | Easy (single log) | Hard (distributed logs, need Zipkin) |
| **Data Consistency** | ACID transactions | Eventual consistency |
| **Complexity** | Low (one app) | High (many moving parts) |
| **DevOps** | Simple CI/CD | Complex CI/CD, Docker, K8s needed |
| **Cost** | Low infrastructure | Higher infrastructure |
| **Latency** | Nanoseconds (method calls) | Milliseconds (network calls) |

### 1.5 Key Principles of Microservices

#### Principle 1: Single Responsibility
Each service should do ONE thing and do it well.
```
USER-SERVICE    → Only handles users and authentication
ORDER-SERVICE   → Only handles order lifecycle
PAYMENT-SERVICE → Only handles payment processing
```

#### Principle 2: Autonomy
Each service should be independent. It should be able to:
- Be developed without waiting for other teams
- Be deployed without deploying other services
- Be scaled without scaling other services
- Fail without bringing down other services

#### Principle 3: Decentralized Data Management
Each service owns its data. No direct database access between services.
```
WRONG:  ORDER-SERVICE directly queries the users table in user_db
RIGHT:  ORDER-SERVICE calls USER-SERVICE API to get user data
```

#### Principle 4: Design for Failure
Services WILL fail. Your architecture must handle it gracefully.
```
What if USER-SERVICE is down?
→ Circuit breaker activates
→ Fallback returns a default response
→ ORDER-SERVICE doesn't crash
```

#### Principle 5: Smart Endpoints, Dumb Pipes
Services should have smart logic internally, but communicate through simple protocols (HTTP, AMQP).

### 1.6 The 12-Factor App Methodology

Microservices follow the 12-Factor App methodology, which defines 12 best practices for building cloud-native applications:

1. **Codebase**: One codebase per service, tracked in version control (Git)
2. **Dependencies**: Explicitly declare and isolate dependencies (pom.xml)
3. **Config**: Store configuration in the environment (Config Server)
4. **Backing Services**: Treat databases, message brokers as attached resources
5. **Build, Release, Run**: Strictly separate build (Maven), release (Docker), run (K8s) stages
6. **Processes**: Execute the app as stateless processes (no session state)
7. **Port Binding**: Export services via port binding (each service has its own port)
8. **Concurrency**: Scale out via the process model (horizontal scaling)
9. **Disposability**: Maximize robustness with fast startup and graceful shutdown
10. **Dev/Prod Parity**: Keep development and production as similar as possible
11. **Logs**: Treat logs as event streams (stdout → Zipkin/ELK)
12. **Admin Processes**: Run admin tasks as one-off processes

### 1.7 Challenges of Microservices

Building microservices introduces new challenges that don't exist in monoliths:

#### Challenge 1: Service Discovery
How does ORDER-SERVICE know where USER-SERVICE is running?
**Solution**: Netflix Eureka (Chapter 5)

#### Challenge 2: Configuration Management
How do you manage configuration across 10+ services?
**Solution**: Spring Cloud Config Server (Chapter 6)

#### Challenge 3: API Gateway
How does the client communicate with 10+ services?
**Solution**: Spring Cloud Gateway (Chapter 4)

#### Challenge 4: Inter-Service Communication
How do services talk to each other?
**Solution**: OpenFeign for sync, RabbitMQ for async (Chapters 7 & 8)

#### Challenge 5: Authentication & Authorization
How do you authenticate users across services?
**Solution**: JWT at the Gateway (Chapter 9)

#### Challenge 6: Fault Tolerance
What happens when a service goes down?
**Solution**: Resilience4j Circuit Breaker (Chapter 11)

#### Challenge 7: Distributed Tracing
How do you trace a request across multiple services?
**Solution**: Zipkin + Micrometer (Chapter 12)

#### Challenge 8: Data Consistency
How do you maintain consistency across databases?
**Solution**: Event-driven architecture, eventual consistency (Chapter 8)

#### Challenge 9: Deployment
How do you deploy 10+ services?
**Solution**: Docker + Kubernetes (Chapters 15 & 16)

#### Challenge 10: Monitoring
How do you monitor 10+ services?
**Solution**: Prometheus + Grafana (Chapter 14)

---

## Chapter 2: Spring Boot - The Foundation

### 2.1 What is Spring Boot?

**Spring Boot** is a framework that makes it easy to create stand-alone, production-grade Spring applications. It provides:

1. **Auto-configuration**: Automatically configures your application based on dependencies
2. **Embedded Server**: Includes Tomcat/Netty, so you don't need external servers
3. **Starter Dependencies**: Pre-configured dependency bundles (e.g., `spring-boot-starter-web`)
4. **Production-Ready Features**: Health checks, metrics, externalized configuration
5. **No XML Configuration**: Everything is configured via Java annotations or properties

### 2.2 How Spring Boot Works

When you create a Spring Boot application, several things happen automatically:

```java
@SpringBootApplication   // This single annotation does 3 things:
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
```

`@SpringBootApplication` is a combination of:
- `@SpringBootConfiguration` → This class provides Spring configuration
- `@EnableAutoConfiguration` → Auto-configure based on classpath dependencies
- `@ComponentScan` → Scan this package and sub-packages for Spring components

### 2.3 Key Spring Boot Annotations Used in This Project

| Annotation | Purpose | Example |
|-----------|---------|---------|
| `@SpringBootApplication` | Main class marker | `UserServiceApplication` |
| `@RestController` | Marks a class as REST API controller | `AuthController` |
| `@RequestMapping` | Base URL mapping for a controller | `@RequestMapping("/auth")` |
| `@GetMapping` | HTTP GET endpoint | `@GetMapping("/email")` |
| `@PostMapping` | HTTP POST endpoint | `@PostMapping("/login")` |
| `@PutMapping` | HTTP PUT endpoint | `@PutMapping("/assign/{id}")` |
| `@DeleteMapping` | HTTP DELETE endpoint | `@DeleteMapping("/delete/{id}")` |
| `@RequestBody` | Deserializes JSON body to Java object | `@RequestBody LoginRequest req` |
| `@RequestParam` | Query parameter binding | `@RequestParam String email` |
| `@PathVariable` | URL path variable binding | `@PathVariable Long id` |
| `@RequestHeader` | HTTP header binding | `@RequestHeader("X-User-Email") String email` |
| `@Service` | Marks a class as business logic component | `AuthService` |
| `@Repository` | Marks a data access interface | `UserRepository` |
| `@Component` | Generic Spring-managed component | `JwtAuthenticationFilter` |
| `@Configuration` | Java-based configuration class | `SecurityConfig` |
| `@Bean` | Defines a Spring bean in a configuration class | `PasswordEncoder bean` |
| `@Value` | Injects a property value | `@Value("${jwt.secret}")` |
| `@Entity` | JPA entity (maps to database table) | `User`, `Order`, `Car` |
| `@Id` | Primary key field | `private Long id` |
| `@GeneratedValue` | Auto-generate primary key | `GenerationType.IDENTITY` |
| `@Column` | Column-level configuration | `@Column(unique = true)` |
| `@Enumerated` | Map enum to database column | `@Enumerated(EnumType.STRING)` |
| `@Table` | Custom table name | `@Table(name = "orders")` |
| `@RequiredArgsConstructor` | Lombok: Constructor injection | All controllers and services |
| `@Data` | Lombok: Getters + Setters + toString + equals + hashCode | All DTOs and entities |
| `@AllArgsConstructor` | Lombok: Constructor with all fields | `AuthResponse` |
| `@NoArgsConstructor` | Lombok: No-args constructor | `AuthResponse` |

### 2.4 Spring Boot Starter Dependencies

Spring Boot uses "starter" dependencies that bundle related libraries:

| Starter | What It Includes | Used In |
|---------|-----------------|---------|
| `spring-boot-starter-web` | Tomcat + Spring MVC + Jackson JSON | All services (except Gateway) |
| `spring-boot-starter-data-jpa` | Hibernate + JPA + Spring Data JPA | Services with databases |
| `spring-boot-starter-security` | Spring Security framework | USER-SERVICE only |
| `spring-boot-starter-actuator` | Health checks, metrics endpoints | All services |
| `spring-boot-starter-amqp` | RabbitMQ client + Spring AMQP | ORDER, NOTIFICATION services |
| `spring-boot-starter-data-redis-reactive` | Reactive Redis client | API Gateway (rate limiting) |
| `spring-boot-starter-test` | JUnit + Mockito + TestContainers | All services |

### 2.5 Spring Boot Auto-Configuration

When you add `spring-boot-starter-data-jpa` to pom.xml, Spring Boot automatically:
1. Detects MySQL driver on classpath → configures DataSource
2. Detects Hibernate on classpath → configures EntityManagerFactory
3. Detects Spring Data JPA → enables JPA repositories
4. Reads `spring.datasource.url` from properties → connects to MySQL

You don't write ANY configuration code for database connectivity! Just add the dependency and the property:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

### 2.6 Spring Boot Layers Architecture

Every Spring Boot service follows a layered architecture:

```
┌─────────────────────────────────────────────────────┐
│                   CLIENT REQUEST                     │
│              (HTTP from Gateway)                     │
└───────────────────────┬─────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│               CONTROLLER LAYER                       │
│                                                      │
│  @RestController                                     │
│  Receives HTTP requests, delegates to Service        │
│  Returns HTTP responses                              │
│  Should NOT contain business logic                   │
│                                                      │
│  Example: OrderController.java                       │
└───────────────────────┬─────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│                 SERVICE LAYER                        │
│                                                      │
│  @Service                                            │
│  Contains ALL business logic                         │
│  Coordinates between repositories and other services │
│  Handles transactions, validations                   │
│                                                      │
│  Example: OrderService.java                          │
└───────────────────────┬─────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│              REPOSITORY LAYER                        │
│                                                      │
│  extends JpaRepository<Entity, ID>                   │
│  Data access operations (CRUD)                       │
│  Spring Data JPA generates implementation            │
│  Custom queries via method naming convention         │
│                                                      │
│  Example: OrderRepository.java                       │
└───────────────────────┬─────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│                DATABASE (MySQL)                      │
│                                                      │
│  Tables auto-created by Hibernate (ddl-auto=update)  │
│  Each service has its own database                   │
└─────────────────────────────────────────────────────┘
```

---

## Chapter 3: Spring Cloud - The Microservices Toolkit

### 3.1 What is Spring Cloud?

**Spring Cloud** is a collection of tools and frameworks that solve common microservices challenges. It builds on top of Spring Boot and provides solutions for:

| Problem | Spring Cloud Solution | Our Implementation |
|---------|----------------------|-------------------|
| Service Discovery | Spring Cloud Netflix Eureka | Eureka Server |
| Configuration Management | Spring Cloud Config | Config Server |
| API Gateway | Spring Cloud Gateway | API Gateway |
| Client-side HTTP | Spring Cloud OpenFeign | Feign clients |
| Circuit Breaker | Spring Cloud Circuit Breaker | Resilience4j |
| Distributed Tracing | Micrometer Tracing | Zipkin integration |
| Event Bus | Spring Cloud Bus | RabbitMQ-backed bus |
| Load Balancing | Spring Cloud LoadBalancer | Auto with Eureka |

### 3.2 Spring Cloud Version Compatibility

Spring Cloud versions MUST match Spring Boot versions. Using incompatible versions causes cryptic errors:

| Spring Boot | Spring Cloud | Release Train |
|-------------|-------------|---------------|
| 3.3.x | 2023.0.x | Leyton |
| 3.2.x | 2023.0.x | Leyton |
| 3.1.x | 2022.0.x | Kilburn |
| 3.0.x | 2022.0.x | Kilburn |

Our project uses: **Spring Boot 3.3.5** + **Spring Cloud 2023.0.3**

This is configured in pom.xml:
```xml
<parent>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.5</version>
</parent>

<properties>
    <spring-cloud.version>2023.0.3</spring-cloud.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 3.3 How Spring Cloud Components Work Together

```
                      ┌─────────────────────┐
                      │    CONFIG SERVER     │ Stores all properties in Git
                      │    (8888)            │ Serves config via REST API
                      └──────────┬──────────┘
                                 │
                    Fetch config on startup
                                 │
┌─────────┐        ┌────────────▼────────────┐        ┌─────────────┐
│ CLIENT  │───────►│     API GATEWAY          │───────►│  EUREKA     │
│         │  HTTP  │     (8080)               │ Lookup │  SERVER     │
│         │        │                          │        │  (8761)     │
│         │        │ 1. JWT Validation        │        │             │
│         │        │ 2. Rate Limiting (Redis) │        │ Registry of │
│         │        │ 3. Route to Service      │        │ all services│
│         │        │ 4. Load Balancing        │        └─────────────┘
│         │        └────────────┬─────────────┘              │
│         │                     │                              │
│         │        ┌────────────▼─────────────┐               │
│         │        │     SERVICES              │               │
│         │        │                          │               │
│         │        │ - Register with Eureka   │───────────────┘
│         │        │ - Fetch config on start  │
│         │        │ - Call other services     │
│         │        │   via Feign + Eureka     │
│         │        │ - Report traces to Zipkin│
│         │        │ - Expose metrics for     │
│         │        │   Prometheus             │
│         │        └──────────────────────────┘
└─────────┘
```

---

## Chapter 4: API Gateway Pattern - Spring Cloud Gateway

### 4.1 What is an API Gateway?

An **API Gateway** is a server that sits between clients and backend services. It acts as a reverse proxy, routing requests to the appropriate service. It's the single entry point for all client requests.

Think of it as the **reception desk of a hospital**:
- You (the patient/client) go to the reception
- You tell them what you need
- They direct you to the right department (service)
- They check your ID (JWT authentication)
- They control how many patients per hour (rate limiting)

### 4.2 Why Do We Need an API Gateway?

Without a gateway, clients need to know the address of every service:

```
WITHOUT GATEWAY (Bad):
Client → http://192.168.1.5:8081/auth/login     (User Service)
Client → http://192.168.1.6:8083/order/create   (Order Service)
Client → http://192.168.1.7:8084/payment/pay    (Payment Service)
Client → http://192.168.1.8:8082/car/add        (Car Service)

Problems:
1. Client needs to know all service addresses
2. Addresses change when services restart
3. No centralized authentication
4. No rate limiting
5. CORS issues with multiple origins
6. No centralized logging
7. If services scale (multiple instances), client can't load balance
```

```
WITH GATEWAY (Our approach):
Client → http://localhost:8080/auth/login
Client → http://localhost:8080/order/create
Client → http://localhost:8080/payment/pay
Client → http://localhost:8080/car/add

All requests go to ONE address: localhost:8080
Gateway handles EVERYTHING else:
1. Routes to correct service via Eureka
2. Validates JWT
3. Rate limits via Redis
4. Forwards user identity headers
5. Load balances across instances
```

### 4.3 Spring Cloud Gateway Architecture

Spring Cloud Gateway is built on **Spring WebFlux** (reactive, non-blocking) and **Project Reactor**. This is different from regular Spring MVC (which is blocking). The Gateway uses **Netty** as its embedded server instead of Tomcat.

```
WHY REACTIVE?
Traditional (Blocking):
  Thread 1 → Request A → Calls Service → WAITS → Response → Free
  Thread 2 → Request B → Calls Service → WAITS → Response → Free
  Thread 3 → Request C → Calls Service → WAITS → Response → Free
  (Each request holds a thread while waiting)

Reactive (Non-blocking):
  Thread 1 → Request A → Calls Service → DOES NOT WAIT → handles Request B
           → Request C → Gets Response for A → sends to client
  (One thread can handle many requests simultaneously)

For a Gateway that handles thousands of concurrent requests,
reactive is MUCH more efficient.
```

### 4.4 Gateway Core Concepts

**Route**: A route defines which URL patterns go to which service
```properties
spring.cloud.gateway.routes[0].id=auth-service
spring.cloud.gateway.routes[0].uri=lb://USER-SERVICE       # lb:// = load balanced
spring.cloud.gateway.routes[0].predicates[0]=Path=/auth/**  # URL pattern
```

**Predicate**: A condition that must be true for the route to match
```
Path=/auth/**     → Match any URL starting with /auth/
Path=/order/**    → Match any URL starting with /order/
Method=GET        → Match only GET requests
Header=X-Custom   → Match only if header exists
```

**Filter**: A function that modifies the request or response
```
GlobalFilter     → Runs on ALL routes (our JwtAuthenticationFilter)
GatewayFilter    → Runs on specific routes (RequestRateLimiter)
```

### 4.5 Request Lifecycle in Our Gateway

```
1. CLIENT sends: POST http://localhost:8080/order/create
   Headers: Authorization: Bearer eyJhbG...

2. NETTY SERVER receives the request

3. ROUTE MATCHING:
   - Check route predicates
   - /order/** matches order-service route
   - Selected route: lb://ORDER-SERVICE

4. GLOBAL FILTER CHAIN:
   a. JwtAuthenticationFilter (our custom filter):
      - Is /order/create a public path? NO
      - Extract Authorization header → "Bearer eyJhbG..."
      - Extract token → "eyJhbG..."
      - Validate token (signature + expiry)
      - Extract email → "sarthak@gmail.com"
      - Extract role → "CUSTOMER"
      - Mutate request: Add X-User-Email and X-User-Role headers
   
   b. RequestRateLimiter (if configured on route):
      - Get key from KeyResolver → "sarthak@gmail.com"
      - Check Redis: tokens remaining? YES
      - Decrement counter
      - Allow request

5. LOAD BALANCER:
   - Ask Eureka: "Where is ORDER-SERVICE?"
   - Eureka says: "192.168.1.6:8083"
   - (If multiple instances, pick one via round-robin)

6. FORWARD REQUEST to http://192.168.1.6:8083/order/create
   With all original headers + X-User-Email + X-User-Role

7. RECEIVE RESPONSE from ORDER-SERVICE

8. RETURN RESPONSE to client
```

### 4.6 GlobalFilter vs GatewayFilter

```java
// GLOBAL FILTER - runs on EVERY request
@Component
public class JwtAuthenticationFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // This code runs for EVERY request
    }
}

// GATEWAY FILTER - runs on SPECIFIC routes only
// Configured in properties:
// spring.cloud.gateway.routes[0].filters[0]=RequestRateLimiter=...
```

---

## Chapter 5: Service Discovery - Netflix Eureka

### 5.1 What is Service Discovery?

In a microservices world, services run on dynamic addresses. They can start on different ports, different machines, and can have multiple instances. **Service discovery** solves the problem of "how does Service A find Service B?"

There are two patterns:

**Client-Side Discovery (What we use with Eureka):**
```
1. All services register with the registry (Eureka)
2. When Service A needs Service B, it asks the registry
3. Registry returns Service B's address
4. Service A calls Service B directly
```

**Server-Side Discovery (What Kubernetes does natively):**
```
1. Services register with the platform (K8s DNS)
2. When Service A calls Service B, the request goes through a load balancer
3. The load balancer routes to the correct instance
4. Service A doesn't know Service B's actual address
```

### 5.2 How Eureka Works - Detailed

Eureka has two components:

1. **Eureka Server**: The registry that stores all service locations
2. **Eureka Client**: A library embedded in each service that handles registration and discovery

```
LIFECYCLE OF A EUREKA CLIENT:

1. SERVICE STARTS
   └── Reads eureka.client.service-url.defaultZone property
   └── Sends POST to Eureka: "Register me as ORDER-SERVICE at 192.168.1.6:8083"
   
2. EUREKA SERVER STORES IT
   └── Registry: { ORDER-SERVICE: [192.168.1.6:8083] }

3. HEARTBEAT (every 30 seconds)
   └── Service sends PUT to Eureka: "I'm still alive"
   └── If heartbeat is missed 3 times, Eureka removes the service

4. SERVICE DISCOVERY
   └── Another service asks: "Where is ORDER-SERVICE?"
   └── Eureka returns: [192.168.1.6:8083]
   └── Client caches this for 30 seconds (doesn't ask Eureka every time)

5. SERVICE SHUTDOWN
   └── Sends DELETE to Eureka: "Remove me from the registry"
   └── (If it crashes without sending DELETE, heartbeat timeout removes it)
```

### 5.3 Eureka Self-Preservation Mode

Eureka has a safety feature called **self-preservation mode**. If Eureka notices that too many services are missing heartbeats simultaneously, it assumes the network is having issues (not that all services actually died). In this mode, Eureka stops removing services from the registry to prevent a cascade of deregistrations.

```
Normal: 10 services registered, 1 misses heartbeat → Eureka removes it ✓
Self-Preservation: 10 services registered, 6 miss heartbeat → 
  Eureka thinks: "The network must be broken, not the services"
  Eureka does NOT remove any services (keeps stale entries)
  This prevents a network partition from causing all services to be deregistered
```

### 5.4 Eureka Dashboard

Eureka provides a web UI at `http://localhost:8761` showing:
- All registered services
- Number of instances per service
- Instance status (UP/DOWN)
- Self-preservation mode status
- Lease renewal and eviction metrics

---

## Chapter 6: Centralized Configuration - Spring Cloud Config

### 6.1 The Configuration Problem

Each microservice needs configuration properties:
- Database URL, username, password
- JWT secret key
- RabbitMQ host and port
- Eureka server URL
- Server port
- Logging levels

Without centralized config:
```
user-service/application.properties:     jwt.secret=abc123
order-service/application.properties:    jwt.secret=abc123   ← DUPLICATED
car-service/application.properties:      jwt.secret=abc123   ← DUPLICATED
admin-service/application.properties:    jwt.secret=abc123   ← DUPLICATED

Problem: If you change jwt.secret, you must update ALL services and redeploy ALL of them!
```

### 6.2 How Config Server Solves This

Spring Cloud Config Server provides:
1. **Centralized Storage**: All configs in one Git repository
2. **Version Control**: Git history tracks every config change
3. **Environment-Specific**: Different configs for dev, staging, prod
4. **Dynamic Refresh**: Update configs without restarting services

```
Git Repository (Car-Wash-central-config-repo):
├── application.properties           ← Shared by ALL services
├── USER-SERVICE.properties          ← Only for User Service
├── ORDER-SERVICE.properties         ← Only for Order Service
├── CAR-SERVICE.properties           ← Only for Car Service
├── PAYMENT-SERVICE.properties
├── ADMIN-SERVICE.properties
├── RATING-SERVICE.properties
├── INVOICE-SERVICE.properties
├── NOTIFICATION-SERVICE.properties
└── API-GATEWAY.properties

application.properties (shared):
  jwt.secret=mysupersecretkeymysupersecretkey12345
  eureka.client.service-url.defaultZone=http://localhost:8761/eureka
  management.zipkin.tracing.endpoint=http://localhost:9411/api/v2/spans

USER-SERVICE.properties (specific):
  server.port=8081
  spring.datasource.url=jdbc:mysql://localhost:3306/user_db
```

### 6.3 Config Server Flow

```
1. Config Server starts → clones Git repository
2. User Service starts → asks Config Server: "Give me USER-SERVICE config"
   GET http://config-server:8888/USER-SERVICE/default
3. Config Server reads USER-SERVICE.properties + application.properties from Git
4. Returns merged properties to User Service
5. User Service uses these properties for its configuration
```

### 6.4 Dynamic Configuration Refresh (Spring Cloud Bus)

The most powerful feature is **live config updates** without restarting services:

```
1. Developer changes jwt.secret in Git repository
2. Developer calls POST http://config-server:8888/actuator/busrefresh
3. Config Server publishes REFRESH event to RabbitMQ
4. ALL services receive the event via Spring Cloud Bus
5. ALL @RefreshScope beans are re-initialized with new values
6. New jwt.secret is active across ALL services!

No service restart required!
```

The `@RefreshScope` annotation marks a bean for refresh:
```java
@RefreshScope  // This bean will be recreated when /actuator/busrefresh is called
@Service
public class JwtService {
    @Value("${jwt.secret}")  // This value will be refreshed
    private String secret;
}
```

---

## Chapter 7: Inter-Service Communication - OpenFeign

### 7.1 Why Services Need to Communicate

In microservices, business operations often span multiple services:

```
Creating an Order requires:
1. ORDER-SERVICE: Creates the order record
2. USER-SERVICE:  Gets the user ID from email
3. CAR-SERVICE:   Validates the car belongs to the user
4. PAYMENT-SERVICE: Processes the payment

These 4 services must communicate to complete ONE business operation.
```

### 7.2 Communication Patterns

There are two fundamental patterns:

**Synchronous (Request-Response):**
```
Service A ──── HTTP Request ────► Service B
         ◄──── HTTP Response ────
Service A WAITS for Service B to respond.
Used when: You NEED the response to continue processing.
Example: ORDER-SERVICE needs user ID from USER-SERVICE before creating the order.
```

**Asynchronous (Event-Driven):**
```
Service A ──── Publish Event ────► Message Broker ────► Service B
Service A DOES NOT WAIT. It continues immediately.
Used when: The response is NOT needed immediately.
Example: ORDER-SERVICE publishes "order created" event. 
         NOTIFICATION-SERVICE processes it whenever ready.
```

### 7.3 OpenFeign - Declarative HTTP Client

OpenFeign makes synchronous HTTP calls between services incredibly simple. Instead of manually constructing HTTP requests, you declare a Java interface:

```java
// TRADITIONAL WAY (without Feign):
RestTemplate restTemplate = new RestTemplate();
String url = "http://USER-SERVICE/user/email?email=" + email;
ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
User user = response.getBody();

// Problems:
// 1. Hardcoded URL construction
// 2. Manual error handling
// 3. Manual deserialization
// 4. No load balancing
// 5. No header forwarding
// 6. Verbose and error-prone

// OPENFEIGN WAY (what we use):
@FeignClient(name = "USER-SERVICE", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/user/email")
    User getUserByEmail(@RequestParam("email") String email);
}

// Usage:
User user = userClient.getUserByEmail("sarthak@gmail.com");

// Benefits:
// 1. Declarative interface - no boilerplate
// 2. Automatic service discovery via Eureka
// 3. Automatic load balancing
// 4. Automatic error handling
// 5. Header forwarding via FeignConfig
// 6. Type-safe
```

### 7.4 How Feign Works Internally

```
Step 1: You call userClient.getUserByEmail("sarthak@gmail.com")

Step 2: Spring creates a PROXY implementation of the UserClient interface

Step 3: The proxy:
   a. Reads @FeignClient(name = "USER-SERVICE")
   b. Asks Eureka: "Where is USER-SERVICE?"
   c. Eureka returns: "192.168.1.5:8081"
   d. If multiple instances → Load balancer picks one

Step 4: The proxy constructs an HTTP request:
   GET http://192.168.1.5:8081/user/email?email=sarthak@gmail.com

Step 5: FeignConfig interceptor runs:
   - Copies X-User-Email header from original request
   - Copies X-User-Role header from original request
   - Adds both to the Feign request

Step 6: HTTP call is made

Step 7: Response is deserialized:
   JSON { "id": 1, "email": "sarthak@gmail.com" } → User object

Step 8: User object is returned to the caller
```

### 7.5 Feign Header Forwarding (FeignConfig) - Critical Concept

When ORDER-SERVICE receives a request from the Gateway, it has these headers:
```
X-User-Email: sarthak@gmail.com
X-User-Role: CUSTOMER
```

When ORDER-SERVICE calls USER-SERVICE via Feign, these headers must be forwarded. Without `FeignConfig`, the Feign call would NOT include these headers, and USER-SERVICE wouldn't know who the user is.

```java
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // Get the current HTTP request (the one ORDER-SERVICE received from Gateway)
            ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();

                // Copy headers to the Feign request
                String email = request.getHeader("X-User-Email");
                String role = request.getHeader("X-User-Role");

                if (email != null) template.header("X-User-Email", email);
                if (role != null) template.header("X-User-Role", role);
            }
        };
    }
}
```

**How `RequestContextHolder` works:**
- Spring MVC stores the current request in a `ThreadLocal` variable
- `RequestContextHolder.getRequestAttributes()` retrieves it
- This works because Feign calls happen on the same thread as the incoming request
- WARNING: If you use `@Async` or a thread pool, `RequestContextHolder` returns null!

---

## Chapter 8: Event-Driven Architecture - RabbitMQ

### 8.1 What is a Message Broker?

A **message broker** is middleware that translates messages between different systems. It receives messages from producers (senders) and delivers them to consumers (receivers). Popular message brokers:

| Broker | Type | Best For |
|--------|------|----------|
| **RabbitMQ** | Message Queue (AMQP) | Complex routing, reliability |
| Apache Kafka | Event Streaming | High throughput, event sourcing |
| AWS SQS | Cloud Queue | Simple queue, AWS ecosystem |
| Redis Pub/Sub | In-memory | Simple, fast, fire-and-forget |

We use **RabbitMQ** because it:
- Supports message acknowledgment (guaranteed delivery)
- Has built-in routing (exchanges, queues, bindings)
- Provides a management UI (port 15672)
- Integrates seamlessly with Spring Boot (spring-boot-starter-amqp)

### 8.2 RabbitMQ Core Concepts

```
PRODUCER ──► EXCHANGE ──► QUEUE ──► CONSUMER

Producer:   Sends messages (ORDER-SERVICE)
Exchange:   Receives messages and routes them to queues
Queue:      Holds messages until consumed
Consumer:   Receives and processes messages (NOTIFICATION-SERVICE)
Binding:    Rule that connects an exchange to a queue
Routing Key: Used by the exchange to decide which queue gets the message
```

**Exchange Types:**

| Type | Routing Logic | Our Usage |
|------|-------------|-----------|
| **Direct** | Routes to queue with matching routing key | Yes (order.routing) |
| Fanout | Routes to ALL bound queues | No |
| Topic | Routes based on pattern matching | No |
| Headers | Routes based on message headers | No |

### 8.3 Our RabbitMQ Architecture

```
ORDER-SERVICE                    RabbitMQ                    NOTIFICATION-SERVICE
┌──────────────┐    ┌───────────────────────────────┐    ┌──────────────────┐
│              │    │                               │    │                  │
│ OrderService │    │  Exchange: order.exchange      │    │ NotifListener    │
│              │    │  Type: Direct                 │    │                  │
│ sendEvent()──┼───►│  Routing Key: order.routing   │───►│ @RabbitListener  │
│              │    │       │                       │    │ consume(event)   │
│              │    │       ▼                       │    │                  │
│              │    │  Queue: order_queue           │    │ save to DB       │
│              │    │  [msg1] [msg2] [msg3] ...     │    │ simulate email   │
│              │    │                               │    │                  │
└──────────────┘    └───────────────────────────────┘    └──────────────────┘
```

### 8.4 Message Flow - Step by Step

```
1. ORDER-SERVICE creates an order successfully

2. ORDER-SERVICE creates an OrderEvent:
   {
     "orderId": 1,
     "email": "sarthak@gmail.com",
     "amount": 500.0,
     "status": "CREATED"
   }

3. ORDER-SERVICE publishes to RabbitMQ:
   rabbitTemplate.convertAndSend("order.exchange", "order.routing", event);

4. RabbitMQ receives the message:
   - Looks at exchange "order.exchange"
   - Finds binding with routing key "order.routing"
   - Routes message to queue "order_queue"

5. Message sits in queue until consumed

6. NOTIFICATION-SERVICE has a listener:
   @RabbitListener(queues = "order_queue")
   public void consume(OrderEvent event) {
       // Process the event
   }

7. Spring AMQP deserializes JSON → OrderEvent object

8. NotificationListener processes:
   - Builds notification message
   - Saves to notification_db
   - Logs simulated email

9. Message is ACKNOWLEDGED (removed from queue)
   If processing fails before acknowledgment, message stays in queue for retry
```

### 8.5 Why We Use Async Here

The order creation flow WITHOUT async:
```java
public Order createOrder(OrderRequest req, String email) {
    Order order = repo.save(newOrder);
    
    // SYNC: If notification service is down, this FAILS
    // And the order creation FAILS even though the order is valid!
    notificationClient.sendNotification(email, "Order Created");
    
    return order;  // Never reached if notification fails!
}
```

The order creation flow WITH async (our approach):
```java
public Order createOrder(OrderRequest req, String email) {
    Order order = repo.save(newOrder);
    
    // ASYNC: Publish event to RabbitMQ
    // This is fire-and-forget. ORDER-SERVICE doesn't wait.
    try {
        publisher.sendOrderEvent(new OrderEvent(...));
    } catch (Exception e) {
        // Even if RabbitMQ is down, the order is still created!
        System.out.println("RabbitMQ Error: " + e.getMessage());
    }
    
    return order;  // Always succeeds!
}
```

---

## Chapter 9: JWT Authentication - Complete Deep Dive

### 9.1 What is Authentication?

**Authentication** = Verifying WHO you are (proving your identity)
**Authorization** = Verifying WHAT you can do (checking your permissions)

```
Authentication: "I am sarthak@gmail.com" → Prove it! → Here's my password → OK, you're verified
Authorization:  "I want to access /admin/users" → What's your role? → CUSTOMER → Access DENIED
```

### 9.2 Authentication Approaches in Web Applications

| Approach | How It Works | Pros | Cons |
|---------|-------------|------|------|
| **Session-Based** | Server stores session in memory/DB, client sends session cookie | Simple, server can invalidate | Doesn't work well in distributed systems |
| **Token-Based (JWT)** | Server generates token, client sends token in header | Stateless, works in distributed systems | Can't invalidate easily |
| **OAuth2** | Delegated auth via third-party (Google, GitHub) | Secure, industry standard | Complex to implement |
| **API Key** | Client sends a static key | Simple | Not suitable for user auth |

We use **JWT (JSON Web Token)** because:
1. **Stateless**: The server doesn't store any session data
2. **Distributed**: Works perfectly across multiple microservices
3. **Self-contained**: The token itself contains user information
4. **Scalable**: No shared state between services

### 9.3 JWT Structure - Deep Explanation

A JWT is a string with three parts separated by dots:

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYXJ0aGFrQGdtYWlsLmNvbSIsInJvbGUiOiJDVVNUT01FUiIsImlhdCI6MTcxMjQwMDAwMCwiZXhwIjoxNzEyNDg2NDAwfQ.abc123signature

Part 1: eyJhbGciOiJIUzI1NiJ9                    ← HEADER (Base64 encoded)
Part 2: eyJzdWIiOiJzYXJ0aGFr...                  ← PAYLOAD (Base64 encoded)
Part 3: abc123signature                           ← SIGNATURE (cryptographic)
```

**HEADER** (Decoded):
```json
{
  "alg": "HS256",    // Hashing algorithm used
  "typ": "JWT"       // Token type
}
```

`HS256` = HMAC using SHA-256. This means:
- The signature is created using a secret key (symmetric)
- Both the creator and validator must know the secret key
- This is why all services share the same `jwt.secret`

**PAYLOAD** (Decoded):
```json
{
  "sub": "sarthak@gmail.com",     // Subject: WHO this token belongs to
  "role": "CUSTOMER",              // Custom claim: user's role
  "iat": 1712400000,               // Issued At: when the token was created
  "exp": 1712486400                // Expiration: when the token expires
}
```

Standard claims:
- `sub` (Subject): User identifier (we use email)
- `iat` (Issued At): Unix timestamp of creation
- `exp` (Expiration): Unix timestamp of expiry
- `iss` (Issuer): Who created this token (not used in our project)
- `aud` (Audience): Who should accept this token (not used)

Custom claims:
- `role`: Our custom claim to store the user's role

**SIGNATURE**:
```
HMACSHA256(
    Base64(header) + "." + Base64(payload),
    "mysupersecretkeymysupersecretkey12345"
)
```

The signature ensures:
1. The token was not tampered with (integrity)
2. The token was created by someone who knows the secret key (authenticity)

If anyone modifies the payload (e.g., changes role from CUSTOMER to ADMIN), the signature will not match, and validation will fail.

### 9.4 JWT Security - Important Concepts

**Why is the secret key so important?**
```
If an attacker knows the secret key, they can:
1. Create valid tokens for ANY user
2. Assign ANY role (e.g., ADMIN)
3. Set ANY expiration (e.g., 100 years)

This is why the secret key should be:
- Long (at least 32 characters for HS256)
- Random (not guessable)
- Stored securely (Config Server, not in code)
- Rotated periodically
```

**Why can't JWT be invalidated easily?**
```
With sessions: Server deletes the session → user is logged out
With JWT: The token is self-contained. The server doesn't store it.
          You can't "delete" a JWT from the server.
          
Workarounds:
1. Short expiration time (24 hours in our project)
2. Token blacklist (store invalidated tokens in Redis)
3. Token versioning (change the secret key → all old tokens become invalid)
```

### 9.5 JWT Flow in Our System - Complete Walkthrough

```
┌──────────┐     ┌──────────┐     ┌──────────────┐     ┌──────────────┐
│  CLIENT  │     │ GATEWAY  │     │ USER-SERVICE │     │ ORDER-SERVICE│
└────┬─────┘     └────┬─────┘     └──────┬───────┘     └──────┬───────┘
     │                │                   │                     │
     │  1. Login      │                   │                     │
     │ POST /auth/login                   │                     │
     │ {email,password}│                  │                     │
     │───────────────►│                   │                     │
     │                │  2. Route to      │                     │
     │                │  USER-SERVICE     │                     │
     │                │  (path /auth/**   │                     │
     │                │   is PUBLIC)      │                     │
     │                │──────────────────►│                     │
     │                │                   │  3. Validate        │
     │                │                   │  credentials        │
     │                │                   │  email + BCrypt     │
     │                │                   │                     │
     │                │                   │  4. Generate JWT    │
     │                │                   │  sub=email          │
     │                │                   │  role=CUSTOMER      │
     │                │                   │  exp=24h            │
     │                │  5. Return token  │                     │
     │                │◄──────────────────│                     │
     │  6. Token      │                   │                     │
     │◄───────────────│                   │                     │
     │                │                   │                     │
     │  7. Create order                   │                     │
     │  POST /order/create                │                     │
     │  Authorization: Bearer <token>     │                     │
     │───────────────►│                   │                     │
     │                │  8. JWT Filter:   │                     │
     │                │  - Extract token  │                     │
     │                │  - Validate       │                     │
     │                │  - Extract email  │                     │
     │                │  - Extract role   │                     │
     │                │  - Add headers    │                     │
     │                │                   │                     │
     │                │  9. Forward with headers                │
     │                │  X-User-Email: sarthak@gmail.com       │
     │                │  X-User-Role: CUSTOMER                 │
     │                │────────────────────────────────────────►│
     │                │                   │                     │
     │                │                   │                     │  10. Read headers
     │                │                   │                     │  Trust Gateway
     │                │                   │                     │  Process order
     │                │                   │                     │
     │                │  11. Order response                     │
     │                │◄────────────────────────────────────────│
     │  12. Response  │                   │                     │
     │◄───────────────│                   │                     │
```

---

## Chapter 10: Spring Security in Microservices

### 10.1 What is Spring Security?

**Spring Security** is a framework that provides authentication, authorization, and protection against common attacks. When you add `spring-boot-starter-security`, it automatically:

1. Adds a login page at `/login`
2. Generates a random password (printed in console)
3. Requires authentication for ALL endpoints
4. Adds CSRF protection
5. Adds session management

This default behavior is great for monoliths but BREAKS microservices!

### 10.2 Why Spring Security Causes 403 in Microservices

```
THE PROBLEM:
1. Add spring-boot-starter-security to USER-SERVICE
2. Spring Security automatically secures ALL endpoints
3. Internal Feign calls from ORDER-SERVICE get 403 Forbidden
4. Even requests from Gateway get 403 (no Spring Security context)

WHY?
Spring Security expects either:
a. A session cookie (we don't use sessions → STATELESS)
b. Basic Auth credentials (we don't use this)
c. A JWT validated by Spring Security (Gateway doesn't set this)

The Gateway sets X-User-Email headers, but Spring Security doesn't know about them.
Spring Security sees no authentication → 403 Forbidden
```

### 10.3 Our Solution: Gateway Trust Pattern

We use Spring Security in USER-SERVICE ONLY for:
1. `BCryptPasswordEncoder` bean (password hashing)
2. `AuthenticationManager` bean (if needed)

But we DISABLE all security checks:

```java
@Bean
public SecurityFilterChain security(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())        // No CSRF (we're a stateless API)
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // No sessions
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()         // Allow ALL requests (Gateway handles auth)
        );
    return http.build();
}
```

And the `JwtAuthFilter` is DISABLED (the `@Component` annotation is commented out):
```java
// @Component   ← COMMENTED OUT! This filter is NOT active.
public class JwtAuthFilter extends OncePerRequestFilter {
    // This class exists for reference but is not registered as a Spring bean
}
```

**Key Insight:** In the Gateway Trust Pattern:
- The Gateway is the ONLY component that validates JWT
- All backend services trust headers set by the Gateway
- Backend services do NOT run Spring Security filters for JWT
- Backend services may still use Spring Security for password hashing

---

## Chapter 11: Resilience4j - Circuit Breaker, Retry, Timeout

### 11.1 What is Fault Tolerance?

**Fault tolerance** is the ability of a system to continue operating when some of its components fail. In microservices, services WILL fail - it's not a question of "if" but "when." Your system must be designed to handle these failures gracefully.

### 11.2 The Cascading Failure Problem

Without fault tolerance, one failing service can bring down the entire system:

```
Scenario: USER-SERVICE is overloaded (slow responses)

1. ORDER-SERVICE calls USER-SERVICE → waits 30 seconds → timeout
2. During those 30 seconds, ORDER-SERVICE's thread is BLOCKED
3. More requests come to ORDER-SERVICE → more threads blocked
4. ORDER-SERVICE runs out of threads (thread pool exhausted)
5. ORDER-SERVICE starts rejecting ALL requests (even ones that don't need USER-SERVICE)
6. ADMIN-SERVICE calls ORDER-SERVICE → gets rejected
7. RATING-SERVICE calls USER-SERVICE → also blocked
8. The entire system is DOWN because ONE service was slow!

This is a CASCADING FAILURE.
```

### 11.3 Resilience4j - Overview

**Resilience4j** is a lightweight fault tolerance library. It provides:

| Pattern | Purpose | Analogy |
|---------|---------|---------|
| **Circuit Breaker** | Stop calling a failing service | Electrical circuit breaker |
| **Retry** | Retry failed calls | Hitting the elevator button again |
| **Timeout** | Don't wait too long | Giving up on a slow pizza delivery |
| **Rate Limiter** | Limit call frequency | Traffic light |
| **Bulkhead** | Isolate resources | Ship compartments |

### 11.4 Circuit Breaker - Deep Explanation

A circuit breaker has three states:

```
┌──────────────┐                      ┌──────────────┐
│              │   Failure threshold   │              │
│    CLOSED    │─────── exceeded ─────►│    OPEN      │
│  (Normal)    │                      │  (Tripped)   │
│              │                      │              │
│ All requests │                      │ All requests │
│ flow through │                      │ REJECTED     │
│              │                      │ immediately  │
│ Successes    │                      │              │
│ and failures │                      │ Fallback is  │
│ are counted  │                      │ called       │
│              │                      │              │
└──────────────┘                      └──────┬───────┘
       ▲                                     │
       │                                     │ After wait duration
       │                                     │ (e.g., 60 seconds)
       │                                     ▼
       │                              ┌──────────────┐
       │                              │              │
       │    If test calls succeed     │  HALF-OPEN   │
       └──────────────────────────────│  (Testing)   │
                                      │              │
              If test calls fail      │ A few test   │
              ──────────────────────► │ requests are │
              (back to OPEN)          │ allowed      │
                                      └──────────────┘
```

**CLOSED (Normal Operation):**
- All requests pass through to the target service
- A sliding window counts successes and failures
- If the failure rate exceeds the threshold (e.g., 50%), the circuit OPENS

**OPEN (Tripped):**
- ALL requests are immediately rejected without calling the target service
- The fallback method is called instead
- After a configured wait time (e.g., 60 seconds), the circuit moves to HALF-OPEN

**HALF-OPEN (Testing):**
- A limited number of test requests are allowed through
- If these succeed → circuit moves to CLOSED (recovered!)
- If these fail → circuit moves back to OPEN (still down)

**Configuration (via properties):**
```properties
# Sliding window of 10 calls
resilience4j.circuitbreaker.instances.userService.sliding-window-size=10

# Open circuit if 50% of calls fail
resilience4j.circuitbreaker.instances.userService.failure-rate-threshold=50

# Wait 60 seconds before trying again
resilience4j.circuitbreaker.instances.userService.wait-duration-in-open-state=60s

# In HALF-OPEN, allow 3 test calls
resilience4j.circuitbreaker.instances.userService.permitted-number-of-calls-in-half-open-state=3
```

### 11.5 Retry - Deep Explanation

Retry automatically retries failed calls. This handles transient failures like network glitches.

```
Without Retry:
  Call → Fails → Error returned to user

With Retry (max-attempts=3):
  Call → Fails → Wait 1s → Retry → Fails → Wait 1s → Retry → Succeeds!
  
  Or:
  Call → Fails → Wait 1s → Retry → Fails → Wait 1s → Retry → Fails → Error
```

**Configuration:**
```properties
resilience4j.retry.instances.userService.max-attempts=3
resilience4j.retry.instances.userService.wait-duration=1s
```

### 11.6 How Retry + Circuit Breaker Work Together

In our code:
```java
@Retry(name = "userService")                                         // Layer 1
@CircuitBreaker(name = "userService", fallbackMethod = "userFallback") // Layer 2
public User getUser(String email) {
    return userClient.getUserByEmail(email);
}
```

Execution flow:
```
1. Retry decorator wraps the CircuitBreaker decorator
2. First call: Feign → USER-SERVICE → SUCCESS → return result
3. First call: Feign → USER-SERVICE → FAIL → Retry waits 1s
4. Second call: Feign → USER-SERVICE → FAIL → Retry waits 1s  
5. Third call: Feign → USER-SERVICE → FAIL → All retries exhausted
6. CircuitBreaker records the failure
7. If enough failures → Circuit OPENS
8. Next call: Circuit is OPEN → Immediately calls userFallback()
9. Fallback returns dummy User { id: 0, email: "fallback@gmail.com" }
```

### 11.7 Fallback Methods - Deep Explanation

A fallback method is called when the primary method fails (after retries are exhausted or circuit is open):

```java
// Primary method
@CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
public User getUser(String email) {
    return userClient.getUserByEmail(email);  // May fail
}

// Fallback method - MUST have same return type + Exception parameter
public User userFallback(String email, Exception e) {
    System.out.println("USER SERVICE DOWN: " + e.getMessage());
    User u = new User();
    u.setId(0L);
    u.setEmail("fallback@gmail.com");
    return u;
}
```

Rules for fallback methods:
1. **Same return type** as the primary method
2. **Same parameters** as the primary method PLUS an `Exception` parameter at the end
3. Must be in the **same class** as the primary method
4. Should return a **sensible default** value (not null)

---

## Chapter 12: Distributed Tracing - Zipkin & Micrometer

### 12.1 The Observability Problem

In a monolith, debugging is simple: one log file, one stack trace. In microservices, a single request might touch 5 different services. If something goes wrong:

```
Client → Gateway → ORDER-SERVICE → USER-SERVICE
                                 → CAR-SERVICE
                                 → PAYMENT-SERVICE → ???

Where did it fail? Gateway? ORDER? USER? CAR? PAYMENT?
How long did each service take?
Which calls happened in parallel vs sequential?
```

### 12.2 What is Distributed Tracing?

**Distributed tracing** tracks a request as it flows through multiple services. It assigns a unique ID to each request and collects timing data from every service.

Key concepts:
- **Trace**: The entire journey of a request (across all services)
- **Span**: A single operation within a trace (one service call)
- **Trace ID**: Unique identifier shared by all spans in a trace
- **Span ID**: Unique identifier for each span
- **Parent Span ID**: The span that started this span

```
Trace ID: abc-123
├── Span 1: API-GATEWAY (0ms - 250ms, duration: 250ms)
│   Parent: none (this is the root span)
│   └── Span 2: ORDER-SERVICE (10ms - 240ms, duration: 230ms)
│       Parent: Span 1
│       ├── Span 3: USER-SERVICE (20ms - 50ms, duration: 30ms)
│       │   Parent: Span 2
│       ├── Span 4: CAR-SERVICE (55ms - 100ms, duration: 45ms)
│       │   Parent: Span 2
│       └── Span 5: PAYMENT-SERVICE (105ms - 230ms, duration: 125ms)
│           Parent: Span 2
```

### 12.3 How Zipkin Works

**Zipkin** is a distributed tracing system. It has four components:

1. **Collector**: Receives trace data from services
2. **Storage**: Stores trace data (in-memory by default)
3. **API**: Query interface for trace data
4. **UI**: Web interface for visualizing traces

```
┌──────────┐     ┌──────────┐     ┌──────────┐
│ Gateway  │     │ ORDER-SVC│     │ USER-SVC │
│          │     │          │     │          │
│ Brave    │     │ Brave    │     │ Brave    │
│ Library  │     │ Library  │     │ Library  │
└────┬─────┘     └────┬─────┘     └────┬─────┘
     │                │                │
     │ Report spans   │ Report spans   │ Report spans
     │                │                │
     ▼                ▼                ▼
┌─────────────────────────────────────────────┐
│                ZIPKIN SERVER                 │
│                (Port 9411)                   │
│                                              │
│  Collector → Storage → API → UI             │
│                                              │
│  http://localhost:9411                       │
└─────────────────────────────────────────────┘
```

### 12.4 Micrometer Tracing (Brave)

**Micrometer** is a metrics facade (like SLF4J for metrics). **Brave** is a distributed tracing library. Together, `micrometer-tracing-bridge-brave` provides:

1. **Automatic instrumentation**: HTTP calls, Feign calls, RabbitMQ messages are traced automatically
2. **Trace context propagation**: Trace ID is propagated via HTTP headers (B3 format)
3. **Span reporting**: Spans are sent to Zipkin automatically

The trace context is propagated via HTTP headers:
```
When ORDER-SERVICE calls USER-SERVICE via Feign, these headers are added automatically:
X-B3-TraceId: abc123
X-B3-SpanId: span2
X-B3-ParentSpanId: span1
X-B3-Sampled: 1
```

---

## Chapter 13: Rate Limiting - Redis

### 13.1 What is Rate Limiting?

**Rate limiting** controls how many requests a client can make in a given time window. It protects your APIs from abuse.

### 13.2 Token Bucket Algorithm

Our Gateway uses the **Token Bucket** algorithm:

```
Imagine a bucket that holds tokens:
- Bucket capacity: 20 tokens (burstCapacity)
- Refill rate: 10 tokens per second (replenishRate)

Each request "costs" 1 token:
Request 1: Take 1 token → Bucket has 19 → ALLOWED
Request 2: Take 1 token → Bucket has 18 → ALLOWED
...
Request 20: Take 1 token → Bucket has 0 → ALLOWED
Request 21: No tokens left → REJECTED (429 Too Many Requests)

After 1 second: 10 tokens added back → Bucket has 10
Request 22: Take 1 token → Bucket has 9 → ALLOWED
```

### 13.3 Why Redis for Rate Limiting?

Redis is used because:
1. **Speed**: Sub-millisecond operations (in-memory storage)
2. **Atomic operations**: `INCR` and `EXPIRE` prevent race conditions
3. **TTL support**: Keys automatically expire (no cleanup needed)
4. **Shared state**: Multiple Gateway instances share the same Redis (consistent limits)

---

## Chapter 14: Monitoring - Prometheus & Grafana

### 14.1 What is Prometheus?

**Prometheus** is a time-series database and monitoring system. It:
1. **Scrapes** metrics from services at regular intervals (pull model)
2. **Stores** metrics as time-series data
3. **Queries** metrics using PromQL (Prometheus Query Language)
4. **Alerts** when metrics cross thresholds

### 14.2 What is Grafana?

**Grafana** is a visualization platform. It:
1. Connects to data sources (Prometheus)
2. Displays metrics as graphs, charts, tables
3. Provides dashboards for system health
4. Supports alerting and notifications

### 14.3 How They Work Together

```
Services expose metrics ──► Prometheus scrapes them ──► Grafana visualizes them

┌────────────┐
│ USER-SVC   │ GET /actuator/prometheus
│ :8081      │ ─────────────────────────────┐
├────────────┤                              │
│ ORDER-SVC  │ GET /actuator/prometheus     │
│ :8083      │ ────────────────────────┐    │
├────────────┤                         │    │
│ GATEWAY    │ GET /actuator/prometheus│    │
│ :8080      │ ───────────────────┐    │    │
└────────────┘                    │    │    │
                                  ▼    ▼    ▼
                          ┌───────────────────┐
                          │    PROMETHEUS      │
                          │    (Port 9090)     │
                          │                    │
                          │  Scrapes every 15s │
                          │  Stores time-series│
                          └────────┬──────────┘
                                   │
                                   │ Data source
                                   ▼
                          ┌───────────────────┐
                          │    GRAFANA         │
                          │    (Port 3000)     │
                          │                    │
                          │  Dashboards        │
                          │  Graphs            │
                          │  Alerts            │
                          └───────────────────┘
```

### 14.4 Metrics Types

| Metric Type | Description | Example |
|-------------|-------------|---------|
| **Counter** | Always increases | Total HTTP requests: 15000 |
| **Gauge** | Can go up or down | Current active threads: 42 |
| **Histogram** | Distribution of values | Request duration: p50=10ms, p95=50ms, p99=200ms |
| **Summary** | Similar to histogram with percentiles | Same as histogram but calculated client-side |

---

## Chapter 15: Docker - Containerization Deep Dive

### 15.1 What is Docker?

**Docker** is a platform for building, shipping, and running applications in containers. A **container** is a lightweight, standalone package that includes everything needed to run an application: code, runtime, libraries, and system tools.

### 15.2 Container vs Virtual Machine

```
VIRTUAL MACHINE:                          CONTAINER:
┌────────────────┐                        ┌────────────────┐
│  Application   │                        │  Application   │
│  Libraries     │                        │  Libraries     │
│  Guest OS      │ ← Full operating       ├────────────────┤
│  (Ubuntu/CentOS)│   system copy!        │  Docker Engine │ ← Shared OS kernel
├────────────────┤                        ├────────────────┤
│  Hypervisor    │                        │  Host OS       │
├────────────────┤                        ├────────────────┤
│  Host OS       │                        │  Hardware      │
├────────────────┤                        └────────────────┘
│  Hardware      │
└────────────────┘

VM: Heavy (~GB), slow startup (~minutes), full OS isolation
Container: Light (~MB), fast startup (~seconds), process-level isolation
```

### 15.3 Docker Key Concepts

| Concept | Description | Our Usage |
|---------|-------------|-----------|
| **Image** | Read-only template with application + dependencies | `sarthakpawar09/carwash-user-service:latest` |
| **Container** | Running instance of an image | `user-service` container |
| **Dockerfile** | Instructions to build an image | Each service has a Dockerfile |
| **Registry** | Storage for images | Docker Hub |
| **Volume** | Persistent storage | `mysql-data` for MySQL data |
| **Network** | Communication channel between containers | `carwash-network` |
| **Docker Compose** | Tool for defining multi-container apps | `docker-compose.yml` |

### 15.4 Dockerfile Anatomy

A typical Dockerfile for our services:
```dockerfile
# Stage 1: Build stage (compile Java)
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run stage (smaller image)
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Line-by-line:
- `FROM eclipse-temurin:21-jdk AS build` → Use Java 21 JDK as base for building
- `WORKDIR /app` → Set working directory inside container
- `COPY pom.xml .` → Copy Maven config
- `COPY src ./src` → Copy source code
- `RUN mvn clean package -DskipTests` → Build the JAR
- `FROM eclipse-temurin:21-jre` → Use smaller JRE image for running
- `COPY --from=build /app/target/*.jar app.jar` → Copy JAR from build stage
- `EXPOSE 8081` → Document the port (doesn't actually open it)
- `ENTRYPOINT ["java", "-jar", "app.jar"]` → Command to run the app

### 15.5 Docker Compose - Deep Explanation

Docker Compose allows you to define and run multiple containers with a single command.

Key `docker-compose.yml` concepts:
```yaml
services:           # Define all containers
  mysql:            # Service name (also used as hostname)
    image: mysql:8.0    # Docker image to use
    container_name: mysql   # Custom container name
    ports:
      - "3306:3306"     # host_port:container_port
    environment:        # Environment variables
      MYSQL_ROOT_PASSWORD: 1234567890
    volumes:            # Persistent storage
      - mysql-data:/var/lib/mysql
    healthcheck:        # Health verification
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 10
    networks:           # Network attachment
      - carwash-network

  user-service:
    build: ./user-service   # Build from Dockerfile in this directory
    depends_on:             # Start order dependencies
      config-server:
        condition: service_healthy   # Wait for config-server to be healthy
      mysql:
        condition: service_healthy
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/car_wash
      # "mysql" here is the SERVICE NAME from docker-compose
      # Docker DNS resolves "mysql" to the container's IP

networks:
  carwash-network:
    driver: bridge    # Default Docker network driver

volumes:
  mysql-data:         # Named volume for persistent data
  grafana-data:
```

### 15.6 Docker Networking

Inside Docker Compose, containers communicate using **service names** as hostnames:

```
user-service wants to reach mysql:
  URL: jdbc:mysql://mysql:3306/car_wash
  "mysql" → Docker DNS resolves to → 172.18.0.2 (MySQL container's IP)

order-service wants to reach rabbitmq:
  URL: amqp://guest:guest@rabbitmq:5672
  "rabbitmq" → Docker DNS resolves to → 172.18.0.3

All containers on "carwash-network" can reach each other by name.
```

---

## Chapter 16: Kubernetes - Container Orchestration Deep Dive

### 16.1 What is Kubernetes?

**Kubernetes (K8s)** is a container orchestration platform. While Docker runs containers on a single machine, Kubernetes manages containers across a **cluster** of machines. It handles:

1. **Deployment**: Deploy containers across multiple nodes
2. **Scaling**: Scale up/down based on demand
3. **Load Balancing**: Distribute traffic across containers
4. **Self-Healing**: Restart crashed containers automatically
5. **Rolling Updates**: Update without downtime
6. **Service Discovery**: Internal DNS for container communication
7. **Secret Management**: Secure storage for passwords and keys

### 16.2 Kubernetes Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                     KUBERNETES CLUSTER                           │
│                                                                  │
│  ┌─────────────────────────────┐                                │
│  │      CONTROL PLANE          │                                │
│  │                             │                                │
│  │  API Server (kubectl talks  │                                │
│  │    to this)                 │                                │
│  │  etcd (distributed KV store)│                                │
│  │  Scheduler (places pods)    │                                │
│  │  Controller Manager         │                                │
│  └─────────────────────────────┘                                │
│                                                                  │
│  ┌──────────────────┐  ┌──────────────────┐                    │
│  │   WORKER NODE 1   │  │   WORKER NODE 2   │                   │
│  │                    │  │                    │                   │
│  │  ┌─────────────┐  │  │  ┌─────────────┐  │                  │
│  │  │ Pod: user-svc│  │  │  │ Pod: order-svc│ │                  │
│  │  │ Container    │  │  │  │ Container    │  │                  │
│  │  └─────────────┘  │  │  └─────────────┘  │                  │
│  │  ┌─────────────┐  │  │  ┌─────────────┐  │                  │
│  │  │ Pod: car-svc │  │  │  │ Pod: payment │  │                  │
│  │  │ Container    │  │  │  │ Container    │  │                  │
│  │  └─────────────┘  │  │  └─────────────┘  │                  │
│  │                    │  │                    │                   │
│  │  kubelet (agent)   │  │  kubelet (agent)   │                  │
│  │  kube-proxy        │  │  kube-proxy        │                  │
│  └──────────────────┘  └──────────────────┘                    │
└─────────────────────────────────────────────────────────────────┘
```

### 16.3 Kubernetes Key Concepts

| Concept | Description | Our Usage |
|---------|-------------|-----------|
| **Pod** | Smallest deployable unit (1+ containers) | Each service runs in a Pod |
| **Deployment** | Manages Pod replicas and updates | `Deployment` for each service |
| **Service** | Stable network endpoint for Pods | `ClusterIP` for internal, `LoadBalancer` for Gateway |
| **Namespace** | Logical grouping of resources | `carwash` namespace |
| **ConfigMap** | Non-sensitive configuration | Prometheus config |
| **Secret** | Sensitive data (passwords) | MySQL credentials |
| **PersistentVolumeClaim** | Persistent storage | MySQL data |
| **Probe** | Health checks | Startup, readiness, liveness probes |

### 16.4 Service Types in Kubernetes

| Type | Description | Who Can Access | Our Usage |
|------|-------------|---------------|-----------|
| **ClusterIP** | Internal-only IP | Only pods in the cluster | All business services |
| **NodePort** | Exposes on each node's IP at a static port | External via node IP | Grafana (30300) |
| **LoadBalancer** | Cloud provider's load balancer | External via public IP | API Gateway |

---

## Chapter 17: CI/CD - GitHub Actions

### 17.1 What is CI/CD?

**CI (Continuous Integration)**: Automatically build and test code when changes are pushed
**CD (Continuous Delivery)**: Automatically deploy tested code to production

```
Developer pushes code → GitHub Actions triggers:

┌──────────────────────────────────────────────────────┐
│                    CI/CD PIPELINE                      │
│                                                        │
│  ┌──────────┐   ┌──────────┐   ┌──────────────────┐  │
│  │ DETECT   │   │ BUILD    │   │ DEPLOY            │  │
│  │ CHANGES  │──►│ & PUSH   │──►│ TO KUBERNETES     │  │
│  │          │   │          │   │                    │  │
│  │ Which    │   │ Maven    │   │ kubectl apply     │  │
│  │ services │   │ build    │   │ (main branch only)│  │
│  │ changed? │   │ Docker   │   │                    │  │
│  │          │   │ push to  │   │                    │  │
│  │          │   │ DockerHub│   │                    │  │
│  └──────────┘   └──────────┘   └──────────────────┘  │
└──────────────────────────────────────────────────────┘
```

---

## Chapter 18: Maven - Build Tool Deep Dive

### 18.1 What is Maven?

**Maven** is a build automation tool for Java projects. It manages:
1. **Dependencies**: Downloads libraries from Maven Central
2. **Build Lifecycle**: Compile → Test → Package → Deploy
3. **Project Structure**: Standard directory layout
4. **Plugins**: Extend build capabilities

### 18.2 pom.xml Structure

```xml
<project>
    <!-- 1. PARENT: Inherits Spring Boot defaults -->
    <parent>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.5</version>
    </parent>

    <!-- 2. PROJECT IDENTITY -->
    <groupId>com.carwash</groupId>           <!-- Organization -->
    <artifactId>order-service</artifactId>    <!-- Project name -->
    <version>0.0.1-SNAPSHOT</version>         <!-- Version -->

    <!-- 3. PROPERTIES: Variables used in the POM -->
    <properties>
        <java.version>21</java.version>
        <spring-cloud.version>2023.0.3</spring-cloud.version>
    </properties>

    <!-- 4. DEPENDENCIES: Libraries this project needs -->
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <!-- No version needed! Managed by parent -->
        </dependency>
    </dependencies>

    <!-- 5. DEPENDENCY MANAGEMENT: Controls versions of transitive deps -->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <!-- 6. BUILD: Build configuration -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## Chapter 19: MySQL - Database in Microservices

### 19.1 Database Per Service Pattern

Each microservice has its own database. This ensures:
1. **Loose coupling**: Services don't share tables
2. **Independent schema changes**: One service can alter its tables without affecting others
3. **Independent scaling**: Each database can be scaled separately
4. **Technology freedom**: Each service can use a different database type

### 19.2 JPA (Java Persistence API)

JPA is a specification for object-relational mapping (ORM). **Hibernate** is the implementation used by Spring Boot.

```java
// Entity class maps to a database table
@Entity                    // This class represents a database table
@Table(name = "orders")    // Table name (avoid "order" which is a SQL keyword)
public class Order {
    @Id                                        // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment
    private Long id;

    private Long userId;                       // Regular column
    private String serviceType;                // Regular column

    @Enumerated(EnumType.STRING)               // Store enum as string, not ordinal
    private OrderStatus status;                // Stored as "CREATED", "ASSIGNED", etc.

    private LocalDateTime createdAt;           // Java 8 date/time
}
```

### 19.3 Spring Data JPA Repository

```java
// Repository interface - Spring generates the implementation!
public interface OrderRepository extends JpaRepository<Order, Long> {
    // JpaRepository provides: save(), findById(), findAll(), delete(), count()

    // Custom query methods (Spring generates SQL from method name):
    List<Order> findByUserId(Long userId);
    // Generated SQL: SELECT * FROM orders WHERE user_id = ?
}
```

---

## Chapter 20: Lombok - Code Generation

### 20.1 What is Lombok?

**Lombok** is a Java library that generates boilerplate code at compile time. It reduces the amount of code you write significantly.

### 20.2 Lombok Annotations Used

| Annotation | What It Generates | Example |
|-----------|------------------|---------|
| `@Data` | Getters + Setters + toString + equals + hashCode | Entity classes, DTOs |
| `@AllArgsConstructor` | Constructor with ALL fields | `new AuthResponse(msg, token, email, role)` |
| `@NoArgsConstructor` | Empty constructor | Required by JPA for entities |
| `@RequiredArgsConstructor` | Constructor for `final` fields | Used for dependency injection |
| `@Getter` | Only getters | When you want read-only access |
| `@Setter` | Only setters | When you want write-only access |
| `@Builder` | Builder pattern | Complex object construction |
| `@Slf4j` | Logger field `log` | `log.info("message")` |

```java
// WITHOUT Lombok (100+ lines):
public class User {
    private Long id;
    private String name;
    private String email;

    public User() {}
    public User(Long id, String name, String email) { ... }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    @Override public String toString() { ... }
    @Override public boolean equals(Object o) { ... }
    @Override public int hashCode() { ... }
}

// WITH Lombok (5 lines):
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String email;
}
```

### 20.3 @RequiredArgsConstructor for Dependency Injection

This is the most important Lombok annotation for Spring:

```java
// WITHOUT Lombok:
@Service
public class OrderService {
    private final OrderRepository repo;
    private final UserClient userClient;

    // You must write this constructor manually:
    @Autowired
    public OrderService(OrderRepository repo, UserClient userClient) {
        this.repo = repo;
        this.userClient = userClient;
    }
}

// WITH Lombok:
@Service
@RequiredArgsConstructor  // Generates constructor for ALL final fields
public class OrderService {
    private final OrderRepository repo;      // Injected via constructor
    private final UserClient userClient;     // Injected via constructor
    // Constructor is generated automatically!
}
```

---

# ========================================================================
# PART 2: PROJECT IMPLEMENTATION - FILE BY FILE
# ========================================================================

---

## Chapter 21: Project Overview & Architecture

### 21.1 System Architecture Diagram

```
                                    ┌─────────────────────────────────────────────────────────┐
                                    │                    INFRASTRUCTURE                        │
                                    │                                                          │
                                    │  ┌────────────┐  ┌────────────┐  ┌──────────┐          │
                                    │  │ Eureka     │  │ Config     │  │ RabbitMQ │          │
                                    │  │ Server     │  │ Server     │  │ (5672)   │          │
                                    │  │ (8761)     │  │ (8888)     │  └──────────┘          │
                                    │  └────────────┘  └────────────┘                        │
                                    │                                                          │
                                    │  ┌────────────┐  ┌────────────┐  ┌──────────────────┐  │
                                    │  │ Redis      │  │ Zipkin     │  │ Prometheus+      │  │
                                    │  │ (6379)     │  │ (9411)     │  │ Grafana          │  │
                                    │  └────────────┘  └────────────┘  └──────────────────┘  │
                                    └────────────────────────────┬────────────────────────────┘
                                                                 │
┌──────────┐         ┌───────────────────────────────────────────▼──────────────────────────┐
│          │  HTTP   │                    API GATEWAY (8080)                                 │
│  CLIENT  │────────►│  JwtAuthFilter → RateLimiter(Redis) → Route(Eureka) → LoadBalance   │
│          │         └───┬─────┬──────┬──────┬──────┬──────┬──────┬──────┬─────────────────┘
└──────────┘             │     │      │      │      │      │      │      │
                         ▼     ▼      ▼      ▼      ▼      ▼      ▼      ▼
                     ┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐┌──────┐
                     │USER  ││ORDER ││PAY   ││CAR   ││ADMIN ││RATE  ││INV   ││NOTIF │
                     │8081  ││8083  ││8084  ││8082  ││8088  ││8086  ││8087  ││8089  │
                     └──────┘└──┬───┘└──────┘└──────┘└──────┘└──────┘└──────┘└──┬───┘
                                │              RabbitMQ (async events)           │
                                └──────────────────────────────────────────────►│
```

### 21.2 Service Inventory

| Service | Port | Database | Dependencies | Key Feature |
|---------|------|----------|--------------|-------------|
| Eureka Server | 8761 | None | None | Service registry |
| Config Server | 8888 | None | Eureka, RabbitMQ | Centralized config |
| API Gateway | 8080 | None | Config Server, Redis, Eureka | JWT, rate limiting |
| USER-SERVICE | 8081 | user_db | Config Server, MySQL | Auth + JWT generation |
| CAR-SERVICE | 8082 | car_db | Config Server, MySQL | Vehicle CRUD |
| ORDER-SERVICE | 8083 | order_db | Config Server, MySQL, RabbitMQ | Core business logic |
| PAYMENT-SERVICE | 8084 | None | Config Server | Payment processing |
| RATING-SERVICE | 8086 | rating_db | Config Server, MySQL | Reviews |
| INVOICE-SERVICE | 8087 | invoice_db | Config Server, MySQL | PDF invoices |
| ADMIN-SERVICE | 8088 | admin_db | Config Server, MySQL | Admin management |
| NOTIFICATION-SERVICE | 8089 | notif_db | Config Server, MySQL, RabbitMQ | Async notifications |

---

## Chapter 22: USER-SERVICE - File by File

### 22.1 UserServiceApplication.java

```java
package com.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
```

**Annotation Breakdown:**
- `@SpringBootApplication`: Combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`. This tells Spring Boot to auto-configure the application based on classpath dependencies and scan the `com.userservice` package for components.
- `@EnableDiscoveryClient`: Enables this service to register with Eureka Server and discover other services. On startup, the service sends its name (`USER-SERVICE`) and address (`localhost:8081`) to Eureka.
- `SpringApplication.run()`: Bootstraps the Spring application. It creates the ApplicationContext, triggers auto-configuration, starts the embedded Tomcat server, and begins listening for HTTP requests.

### 22.2 Entity: User.java

```java
package com.userservice.ENtity;

import com.userservice.Enum.Roles;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String phone;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Roles role;
}
```

**Annotation Breakdown:**
- `@Entity`: Marks this class as a JPA entity. Hibernate will create a table called `user` (or `users` depending on dialect) in MySQL.
- `@Data` (Lombok): Generates `getId()`, `setId()`, `getName()`, `setName()`, etc., plus `toString()`, `equals()`, `hashCode()`.
- `@NoArgsConstructor` (Lombok): Generates `public User() {}`. Required by JPA/Hibernate which creates objects via reflection.
- `@AllArgsConstructor` (Lombok): Generates `public User(Long id, String name, String password, ...)`.
- `@Id`: Marks `id` as the primary key of this entity.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: The database auto-generates the `id` (MySQL's `AUTO_INCREMENT`). Each new user gets the next sequential ID.
- `@Column(unique = true)`: Adds a UNIQUE constraint on the `email` column. Two users cannot have the same email.
- `@Enumerated(EnumType.STRING)`: Stores the enum value as a string in the database. Without this, JPA stores the ordinal (0, 1, 2) which is fragile if you reorder enums.

**Database Table Generated:**
```sql
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(255),
    phone VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    role VARCHAR(255)    -- Stores "CUSTOMER", "WASHER", or "ADMIN"
);
```

### 22.3 Enum: Roles.java

```java
package com.userservice.Enum;

public enum Roles {
    CUSTOMER,
    WASHER,
    ADMIN
}
```

**Purpose:** Defines the three roles in the system:
- `CUSTOMER`: Regular user who books car washes
- `WASHER`: Service provider who performs car washes
- `ADMIN`: Platform administrator (only one allowed)

### 22.4 Repository: UserRepository.java

```java
package com.userservice.Repository;

import com.userservice.ENtity.User;
import com.userservice.Enum.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByRole(Roles role);
}
```

**How Spring Data JPA Repository Works:**

`JpaRepository<User, Long>`:
- First type parameter `User`: The entity class this repository manages
- Second type parameter `Long`: The type of the entity's ID field

**Inherited Methods (FREE - you don't write these):**
| Method | SQL Generated | Purpose |
|--------|--------------|---------|
| `save(User user)` | `INSERT INTO user ...` or `UPDATE user ...` | Create or update |
| `findById(Long id)` | `SELECT * FROM user WHERE id = ?` | Find by primary key |
| `findAll()` | `SELECT * FROM user` | Get all users |
| `deleteById(Long id)` | `DELETE FROM user WHERE id = ?` | Delete by ID |
| `count()` | `SELECT COUNT(*) FROM user` | Count all records |
| `existsById(Long id)` | `SELECT COUNT(*) FROM user WHERE id = ?` | Check existence |

**Custom Methods (Spring generates implementation from method name):**
| Method | SQL Generated |
|--------|--------------|
| `findByEmail(String email)` | `SELECT * FROM user WHERE email = ?` |
| `existsByRole(Roles role)` | `SELECT COUNT(*) > 0 FROM user WHERE role = ?` |

`Optional<User>`: A container that may or may not contain a User. Prevents NullPointerException. You use it like:
```java
Optional<User> result = repo.findByEmail("sarthak@gmail.com");
if (result.isPresent()) {
    User user = result.get();
} else {
    throw new RuntimeException("User not found");
}
// Or more concisely:
User user = repo.findByEmail("sarthak@gmail.com")
    .orElseThrow(() -> new RuntimeException("User not found"));
```

### 22.5 DTO: RegisterRequest.java

```java
package com.userservice.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;
}
```

**What is a DTO?**
DTO (Data Transfer Object) is a plain object used to transfer data between layers. It separates the API contract (what the client sends) from the internal entity (what's stored in the database).

Why not use the `User` entity directly?
```
User entity has:  id, name, password (hashed), phone, email, role (Roles enum)
RegisterRequest:  name, email, phone, password (plain text), role (String)

Differences:
1. RegisterRequest has password as plain text, User has it BCrypt-hashed
2. RegisterRequest has role as String, User has it as Roles enum
3. RegisterRequest doesn't have id (database generates it)
4. Using DTOs prevents exposing internal structure
```

### 22.6 DTO: LoginRequest.java

```java
package com.userservice.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
```

Simple DTO with just email and password for login.

### 22.7 DTO: AuthResponse.java

```java
package com.userservice.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String token;
    private String email;
    private String role;
}
```

This is what the client receives after login/register:
```json
{
    "message": "Login successful",
    "token": "Bearer eyJhbGciOiJIUzI1NiJ9...",
    "email": "sarthak@gmail.com",
    "role": "CUSTOMER"
}
```

### 22.8 Security: JwtService.java

```java
package com.userservice.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) getClaims(token).get("role");
    }

    public boolean isTokenValid(String token) {
        try { getClaims(token); return true; }
        catch (Exception e) { return false; }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
```

**Line-by-Line Explanation:**

`@Service`: Marks this as a Spring-managed service bean. Spring creates one instance and injects it wherever needed.

`@Value("${jwt.secret}")`: Injects the value of `jwt.secret` from configuration. This property is stored in the Config Server's Git repo (not hardcoded). The value is something like `mysupersecretkeymysupersecretkey12345`.

`Keys.hmacShaKeyFor(secret.getBytes())`: Creates an HMAC-SHA key from the secret string. The key must be at least 256 bits (32 bytes) for HS256.

`Jwts.builder()`: Creates a JWT builder using the JJWT library.
- `.setSubject(email)`: Sets the `sub` claim (who this token is for)
- `.claim("role", role)`: Adds a custom claim `role` (CUSTOMER/WASHER/ADMIN)
- `.setIssuedAt(new Date())`: Sets `iat` to current time
- `.setExpiration(new Date(System.currentTimeMillis() + 86400000))`: Sets `exp` to 24 hours from now (86400000ms = 24h)
- `.signWith(getKey(), SignatureAlgorithm.HS256)`: Signs with HMAC-SHA256
- `.compact()`: Builds the final JWT string

`Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token)`: Parses and validates the token. This will throw:
- `ExpiredJwtException` if the token has expired
- `SignatureException` if the signature doesn't match
- `MalformedJwtException` if the token format is invalid

### 22.9 Security: AuthService.java

```java
package com.userservice.Security;

import com.userservice.DTO.*;
import com.userservice.ENtity.User;
import com.userservice.Enum.Roles;
import com.userservice.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthResponse register(RegisterRequest req) {
        if (repo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        if (req.getRole().equalsIgnoreCase("ADMIN")) {
            if (repo.existsByRole(Roles.ADMIN)) {
                throw new RuntimeException("Admin already exists");
            }
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(Roles.valueOf(req.getRole().toUpperCase()));
        repo.save(user);

        String token = jwt.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse("User registered successfully", "Bearer " + token, user.getEmail(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest req) {
        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwt.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse("Login successful", "Bearer " + token, user.getEmail(), user.getRole().name());
    }
}
```

**Key Concepts:**

`@RequiredArgsConstructor`: Generates a constructor that takes `repo`, `encoder`, and `jwt` as parameters. Spring injects the concrete implementations automatically (constructor injection).

`encoder.encode(req.getPassword())`: Hashes the plain-text password using BCrypt. The result looks like `$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy`. BCrypt includes a random salt in every hash, so the same password produces different hashes.

`encoder.matches(req.getPassword(), user.getPassword())`: Compares a plain-text password with a BCrypt hash. Returns `true` if they match.

`Roles.valueOf(req.getRole().toUpperCase())`: Converts a String like "customer" to the `Roles.CUSTOMER` enum value. `.toUpperCase()` ensures case-insensitive matching.

### 22.10 Security: SecurityConfig.java

```java
package com.userservice.CONFIGS;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
```

**Detailed Explanation:**

`@Configuration`: Marks this as a Spring configuration class. Spring processes it at startup and creates the defined beans.

`@Bean public PasswordEncoder passwordEncoder()`: Creates a `BCryptPasswordEncoder` bean. This is used by `AuthService` for encoding and matching passwords. BCrypt is the industry standard for password hashing.

`@Bean public SecurityFilterChain security(HttpSecurity http)`:
- `.csrf(csrf -> csrf.disable())`: Disables CSRF (Cross-Site Request Forgery) protection. CSRF is needed for browser-based forms but NOT for REST APIs that use JWT.
- `.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))`: Tells Spring Security to NEVER create HTTP sessions. JWT is stateless - no server-side session needed.
- `.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())`: Allows ALL requests without authentication. The Gateway handles auth; this service trusts internal traffic.

### 22.11 Security: JwtAuthFilter.java (DISABLED)

```java
// @Component   ← COMMENTED OUT! This filter is NOT registered as a Spring bean.
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.contains("/auth") || path.contains("/user/email") || path.contains("/user/all");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // ... JWT validation logic ...
        chain.doFilter(request, response);
    }
}
```

**Why is this disabled?**
This filter was originally used when USER-SERVICE validated JWT itself. Now that the Gateway handles JWT validation, this filter is unnecessary. The `@Component` annotation is commented out so Spring doesn't register it, but the class is kept for reference.

`OncePerRequestFilter`: A Spring filter that guarantees it runs exactly once per request (even if the request is forwarded internally).

### 22.12 Security: CustomUserDetailsService.java

```java
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
```

`UserDetailsService`: A Spring Security interface. When Spring Security needs to look up a user (for authentication), it calls `loadUserByUsername()`. This method loads user data from our database and converts it to Spring Security's `UserDetails` format.

`SimpleGrantedAuthority("ROLE_" + user.getRole().name())`: Creates a Spring Security authority. The "ROLE_" prefix is a Spring Security convention for role-based access control.

### 22.13 Controller: AuthController.java

```java
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) {
        return service.register(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return service.login(req);
    }
}
```

**Annotation Breakdown:**
- `@RestController`: Combines `@Controller` + `@ResponseBody`. Every method's return value is automatically serialized to JSON.
- `@RequestMapping("/auth")`: All endpoints in this controller start with `/auth/`.
- `@PostMapping("/register")`: Maps `POST /auth/register` to this method.
- `@RequestBody RegisterRequest req`: Spring deserializes the JSON request body into a `RegisterRequest` object using Jackson.
- Return `AuthResponse`: Spring serializes this to JSON and sends it as the HTTP response body.

### 22.14 Controller: UserController.java

```java
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repo;

    @GetMapping("/email")
    public User getUserByEmail(@RequestParam String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/all")
    public Object getAllUsers() {
        return repo.findAll();
    }
}
```

`@RequestParam String email`: Binds the `email` query parameter from the URL.
Example: `GET /user/email?email=sarthak@gmail.com` → `email = "sarthak@gmail.com"`

These endpoints are called by other services via Feign:
- ORDER-SERVICE calls `GET /user/email?email=X` to get user ID
- ADMIN-SERVICE calls `GET /user/all` to list all users

---

## Chapter 23: ORDER-SERVICE - File by File

### 23.1 OrderServiceApplication.java

```java
@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
```

`@EnableFeignClients`: Scans for `@FeignClient` interfaces and creates proxy implementations. Without this, Feign clients won't be registered as Spring beans.

### 23.2 Entity: Order.java

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long carId;
    private String serviceType;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long washerId;
    private LocalDateTime scheduledAt;
    private LocalDateTime createdAt;
}
```

`@Table(name = "orders")`: The table is named `orders` (not `order`) because `ORDER` is a reserved SQL keyword. Using it as a table name would cause SQL syntax errors.

### 23.3 Enum: OrderStatus.java

```java
public enum OrderStatus {
    CREATED,     // Order placed by customer
    ASSIGNED,    // Washer assigned by admin
    COMPLETED    // Wash done, payment processed
}
```

### 23.4 DTO: OrderRequest.java

```java
@Data
public class OrderRequest {
    private Integer carId;
    private String serviceType;
    private LocalDateTime scheduledAt;
}
```

### 23.5 DTO: OrderEvent.java (RabbitMQ Message)

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private Long orderId;
    private String email;
    private Double amount;
    private String status;
}
```

This is the message published to RabbitMQ. The `@NoArgsConstructor` is required for Jackson deserialization on the consumer side (NOTIFICATION-SERVICE).

### 23.6 Feign Client: UserClient.java

```java
@FeignClient(name = "USER-SERVICE", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/user/email")
    User getUserByEmail(@RequestParam("email") String email);
}
```

`@FeignClient(name = "USER-SERVICE")`: The `name` matches the service name registered in Eureka. Feign resolves this via Eureka to get the actual host:port.

`configuration = FeignConfig.class`: Applies the custom FeignConfig that forwards X-User-Email and X-User-Role headers.

### 23.7 Feign Client: CarClient.java

```java
@FeignClient(name = "CAR-SERVICE", configuration = FeignConfig.class)
public interface CarClient {
    @GetMapping("/car/user/{userId}")
    List<Car> getCars(@PathVariable Long userId);
}
```

### 23.8 Feign Client: PaymentClient.java

```java
@FeignClient(name = "PAYMENT-SERVICE", configuration = FeignConfig.class)
public interface PaymentClient {
    @PostMapping("/payment/pay")
    Payment pay(@RequestParam Long orderId, @RequestParam Double amount);
}
```

### 23.9 Config: FeignConfig.java

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

`RequestInterceptor`: A Feign interface. It's called before every Feign HTTP request, allowing you to modify the request (add headers, parameters, etc.).

`RequestContextHolder.getRequestAttributes()`: Gets the current HTTP request from Spring MVC's ThreadLocal storage. This is the request that ORDER-SERVICE received from the Gateway.

`template.header(...)`: Adds a header to the outgoing Feign request. This ensures that when ORDER-SERVICE calls USER-SERVICE, the X-User-Email header is forwarded.

### 23.10 Config: RabbitMQConfig.java

```java
@Configuration
public class RabbitMQConfig {
    public static final String QUEUE = "order_queue";
    public static final String EXCHANGE = "order.exchange";
    public static final String ROUTING_KEY = "order.routing";

    @Bean public Queue queue() { return new Queue(QUEUE); }
    @Bean public DirectExchange exchange() { return new DirectExchange(EXCHANGE); }
    @Bean public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
    @Bean public MessageConverter messageConverter() { return new JacksonJsonMessageConverter(); }
    @Bean public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
```

**Detailed Explanation:**

`Queue`: A RabbitMQ queue named `order_queue`. Messages wait here until consumed.

`DirectExchange`: An exchange that routes messages based on exact routing key match.

`Binding`: Connects the exchange to the queue. When a message arrives at `order.exchange` with routing key `order.routing`, it goes to `order_queue`.

`JacksonJsonMessageConverter`: Converts Java objects (OrderEvent) to JSON when sending, and JSON back to Java objects when receiving.

`RabbitTemplate`: The main class for sending messages to RabbitMQ. Configured with the JSON converter.

### 23.11 Service: OrderPublisher.java

```java
@Service
@RequiredArgsConstructor
public class OrderPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void sendOrderEvent(OrderEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,    // "order.exchange"
            RabbitMQConfig.ROUTING_KEY, // "order.routing"
            event                       // OrderEvent object → serialized to JSON
        );
    }
}
```

`convertAndSend()`: Converts the OrderEvent to JSON using the configured converter, then sends it to the exchange with the routing key.

### 23.12 Service: OrderService.java

```java
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repo;
    private final PaymentClient paymentClient;
    private final CarClient carClient;
    private final UserClient userClient;
    private final OrderPublisher publisher;

    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    public User getUser(String email) {
        return userClient.getUserByEmail(email);
    }

    public User userFallback(String email, Exception e) {
        User u = new User(); u.setId(0L); u.setEmail("fallback@gmail.com");
        return u;
    }

    // ... similar for getCars() and createPayment()

    public Order createOrder(OrderRequest req, String email) {
        User user = getUser(email);
        List<Car> cars = getCars(user.getId());

        Car selectedCar = cars.stream()
            .filter(c -> c.getId().equals(Long.valueOf(req.getCarId())))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Invalid car for this user"));

        Order order = new Order();
        order.setCarId(selectedCar.getId());
        order.setUserId(user.getId());
        order.setServiceType(req.getServiceType());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = repo.save(order);

        try {
            publisher.sendOrderEvent(new OrderEvent(savedOrder.getId(), email, 500.0, "CREATED"));
        } catch (Exception e) {
            System.out.println("RabbitMQ Error: " + e.getMessage());
        }

        return savedOrder;
    }
}
```

**Key Patterns:**

`@Retry(name = "userService")` + `@CircuitBreaker(name = "userService", fallbackMethod = "userFallback")`: This combination means:
1. Try the call up to 3 times (retry)
2. If all retries fail, record the failure in the circuit breaker
3. If enough failures accumulate, open the circuit and call `userFallback()` directly

`cars.stream().filter(...).findFirst()`: Java Streams API. Filters the user's car list to find the one matching the requested carId. This validates that the car belongs to the user.

`try { publisher.sendOrderEvent(...) } catch (Exception e) { ... }`: The RabbitMQ event is wrapped in try-catch because order creation should succeed even if RabbitMQ is down. The notification is a nice-to-have, not a requirement.

### 23.13 Controller: OrderController.java

```java
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderRequest req,
                             @RequestHeader("X-User-Email") String email) {
        return service.createOrder(req, email);
    }

    @GetMapping("/my")
    public List<Order> myOrders(@RequestHeader("X-User-Email") String email) {
        return service.getUserOrders(email);
    }

    @PutMapping("/assign/{id}")
    public Order assignOrder(@PathVariable Long id,
                             @RequestParam Long washerId,
                             @RequestParam String scheduledAt,
                             @RequestHeader("X-User-Email") String email) {
        return service.assignOrder(id, washerId, scheduledAt);
    }

    @PutMapping("/status/{id}")
    public Order updateStatus(@PathVariable Long id,
                              @RequestParam OrderStatus status,
                              @RequestHeader("X-User-Email") String email) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id,
                              @RequestHeader("X-User-Email") String email) {
        service.deleteOrder(id, email);
        return "Order deleted successfully";
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return service.getOrderById(id);
    }

    @PostMapping("/pay/{orderId}")
    public String pay(@PathVariable Long orderId,
                      @RequestParam Double amount,
                      @RequestHeader("X-User-Email") String email) {
        return service.payForOrder(orderId, amount, email);
    }
}
```

`@RequestHeader("X-User-Email") String email`: Extracts the `X-User-Email` header that was set by the Gateway after JWT validation. This is how the service knows WHO is making the request.

`@PathVariable Long id`: Extracts `{id}` from the URL path. Example: `PUT /order/assign/5` → `id = 5`.

`@RequestParam OrderStatus status`: Spring automatically converts the query parameter string to the `OrderStatus` enum. Example: `?status=COMPLETED` → `OrderStatus.COMPLETED`.

---

## Chapter 24: CAR-SERVICE - File by File

**Port:** 8082 | **Database:** car_db | **Dependencies:** Eureka, Config Server, MySQL

CAR-SERVICE is a simple CRUD service for managing customer vehicles. It does NOT call any other service via Feign - it is only CALLED BY other services (ORDER-SERVICE fetches car lists from here).

### 24.1 CarServiceApplication.java

```java
package com.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarServiceApplication.class, args);
    }
}
```

**Key Observation:** No `@EnableFeignClients` here because CAR-SERVICE doesn't call other services via Feign. It only receives incoming requests.

### 24.2 Entity: Car.java

```java
package com.carservice.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String brand;
    private String model;
    private String color;
    private String numberPlate;
}
```

**Field Breakdown:**
- `id` → Auto-incremented primary key. Database generates this.
- `userId` → The ID of the user who owns this car. This is a **logical foreign key** - it references the `User` table in `user_db`, but since microservices have separate databases, it's not an actual SQL foreign key constraint. It's maintained through application logic.
- `brand` → e.g., "BMW", "Audi", "Toyota"
- `model` → e.g., "X5", "A4", "Camry"
- `color` → e.g., "Black", "White", "Red"
- `numberPlate` → e.g., "MH12AB1234" (Indian format)

**Database Table Generated:**
```sql
CREATE TABLE car (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    brand VARCHAR(255),
    model VARCHAR(255),
    color VARCHAR(255),
    number_plate VARCHAR(255)
);
```

Note: JPA automatically converts `camelCase` Java field names to `snake_case` column names (`userId` → `user_id`, `numberPlate` → `number_plate`).

### 24.3 DTO: CarRequest.java

```java
package com.carservice.DTO;

import lombok.Data;

@Data
public class CarRequest {
    private String brand;
    private String model;
    private String color;
    private String numberPlate;
}
```

**Why a DTO instead of the Entity?**
The `CarRequest` DTO doesn't include `id` (database generates it) or `userId` (extracted from the authenticated user). The client only sends car details, not identity information. This separation prevents clients from setting their own `id` or assigning cars to other users.

### 24.4 Repository: CarRepository.java

```java
package com.carservice.Repository;

import com.carservice.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUserId(Long userId);
}
```

**Custom Query Method:**
`findByUserId(Long userId)` → Spring Data JPA parses the method name and generates:
```sql
SELECT * FROM car WHERE user_id = ?
```

This is called by ORDER-SERVICE (via Feign) to get all cars belonging to a specific user.

### 24.5 Service: CarService.java

```java
package com.carservice.Service;

import com.carservice.DTO.CarRequest;
import com.carservice.Entity.Car;
import com.carservice.Repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

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

    public Car updateCar(Long id, CarRequest req) {
        Car car = carRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("car not found"));
        car.setBrand(req.getBrand());
        car.setModel(req.getModel());
        car.setColor(req.getColor());
        car.setNumberPlate(req.getNumberPlate());
        return carRepository.save(car);
    }

    public void deletecar(Long id) {
        Car car = carRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Car not found"));
        carRepository.delete(car);
    }
}
```

**Method-by-Method Explanation:**

`addCar(CarRequest, Long userId)`:
- Creates a new `Car` entity from the DTO
- Sets the `userId` (passed from the controller)
- `carRepository.save(car)` → Hibernate generates: `INSERT INTO car (user_id, brand, model, color, number_plate) VALUES (?, ?, ?, ?, ?)`
- Returns the saved entity (now has the auto-generated `id`)

`getCarsByUser(Long userId)`:
- Delegates to the repository's `findByUserId()` method
- Returns all cars belonging to this user

`updateCar(Long id, CarRequest req)`:
- Finds the existing car by ID. `findById()` returns `Optional<Car>`, `.orElseThrow()` throws if not found.
- Updates all fields from the request
- `carRepository.save(car)` → Since the entity already has an `id`, Hibernate generates: `UPDATE car SET brand=?, model=?, color=?, number_plate=? WHERE id=?`
- **Key JPA Concept:** `save()` does INSERT for new entities (no `id`) and UPDATE for existing entities (has `id`).

`deletecar(Long id)`:
- Finds the car first (to throw a meaningful error if not found)
- `carRepository.delete(car)` → Hibernate generates: `DELETE FROM car WHERE id=?`

### 24.6 Controller: CarController.java

```java
package com.carservice.Controller;

import com.carservice.DTO.CarRequest;
import com.carservice.Entity.Car;
import com.carservice.Service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/add")
    public Car addCar(@RequestBody CarRequest req,
                      @RequestHeader("X-User-Email") String email) {
        Long userid = 1L;  // TODO: look up userId from email via Feign
        return carService.addCar(req, userid);
    }

    @GetMapping("/user/{userId}")
    public List<Car> getCars(@PathVariable Long userId) {
        return carService.getCarsByUser(userId);
    }

    @PutMapping("/update/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody CarRequest req) {
        return carService.updateCar(id, req);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deletecar(id);
        return "Car deleted Successfully";
    }
}
```

**Important Note:** The `addCar` endpoint currently hardcodes `userId = 1L`. In a production system, it should call USER-SERVICE via Feign to get the user ID from the email. This is a known TODO.

`@GetMapping("/user/{userId}")`: This endpoint is called by ORDER-SERVICE's `CarClient`:
```java
@FeignClient(name = "CAR-SERVICE")
public interface CarClient {
    @GetMapping("/car/user/{userId}")
    List<Car> getCars(@PathVariable Long userId);
}
```

### 24.7 Service: JwtService.java (Car Service)

```java
package com.carservice.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import java.security.Key;

@RefreshScope
@Service
public class JwtService {
    private final String SECRET = "mysupersecretkeymysupersecretkey12345";

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getKey()).build()
            .parseClaimsJws(token).getBody().getSubject();
    }
}
```

**Note:** This JwtService exists in CAR-SERVICE but is **not actively used** in the current architecture because the Gateway handles JWT validation. It's kept as a utility in case direct JWT parsing is needed.

`@RefreshScope`: This bean will be re-created when Spring Cloud Bus sends a refresh event. If the JWT secret changes in Config Server, this bean picks up the new value.

---

## Chapter 25: PAYMENT-SERVICE - File by File

**Port:** 8084 | **Database:** None (stateless) | **Dependencies:** Eureka, Config Server

PAYMENT-SERVICE is the simplest service. It processes payments and returns a status. Currently it's a **dummy implementation** that always returns SUCCESS - in production, you'd integrate with Stripe, Razorpay, or PayPal.

### 25.1 PaymentServiceApplication.java

```java
package com.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
```

No `@EnableFeignClients` (doesn't call other services) and no `@EnableDiscoveryClient` (it's auto-enabled by having `spring-cloud-starter-netflix-eureka-client` on the classpath in Spring Boot 3.x).

### 25.2 Payment.java (DTO - No Entity, No Database)

```java
package com.paymentservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Long orderId;
    private Double amount;
    private String status;
}
```

**Key Observation:** This is NOT an `@Entity`. There's no `@Id`, no `@GeneratedValue`, no database table. Payment data is not persisted - it's returned as a response and ORDER-SERVICE decides what to do with it.

`@AllArgsConstructor`: Allows `new Payment(orderId, amount, "SUCCESS")` - used in PaymentService.
`@NoArgsConstructor`: Required for Jackson JSON deserialization when ORDER-SERVICE receives the response.

### 25.3 PaymentService.java

```java
package com.paymentservice;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class PaymentService {
    public Payment processPayment(Long orderId, Double amount) {
        System.out.println("Processing payment...");
        System.out.println("Order: " + orderId);
        return new Payment(orderId, amount, "SUCCESS");
    }
}
```

**Current Behavior:** Always returns `"SUCCESS"`. In a real system, this would:
1. Call Stripe/Razorpay API with the amount
2. Wait for payment confirmation
3. Return `"SUCCESS"` or `"FAILED"` based on the actual result
4. Store the transaction in a database for audit

### 25.4 PaymentController.java

```java
package com.paymentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/pay")
    public Payment pay(@RequestParam Long orderId,
                       @RequestParam Double amount) {
        return service.processPayment(orderId, amount);
    }
}
```

**Note:** This endpoint uses `@RequestParam` (query parameters) not `@RequestBody` (JSON body). ORDER-SERVICE's Feign client calls it like:
```
POST /payment/pay?orderId=1&amount=500.0
```

### 25.5 PaymentClient.java (Self-referencing Feign - NOT used internally)

```java
package com.paymentservice;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {
    @PostMapping("/payment/pay")
    String pay(@RequestParam Long orderId, @RequestParam Double amount);
}
```

**Note:** This Feign client is defined in PAYMENT-SERVICE itself but is **not used here**. It's a reference for how other services (ORDER-SERVICE) should call this service. The actual PaymentClient used by ORDER-SERVICE is in the `order-service` module.

### 25.6 pom.xml Key Dependencies

```xml
<!-- No spring-boot-starter-data-jpa → No database! -->
<!-- No MySQL connector → This service is stateless -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

Notice: No JPA dependency and no MySQL connector. PAYMENT-SERVICE is fully stateless.

---

## Chapter 26: ADMIN-SERVICE - File by File

**Port:** 8088 | **Database:** admin_db | **Dependencies:** Eureka, Config Server, MySQL, Feign (USER-SERVICE)

ADMIN-SERVICE provides administrative features. It enforces role-based access - only users with `ADMIN` role can access these endpoints.

### 26.1 AdminServiceApplication.java

```java
package com.adminservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }
}
```

`@EnableFeignClients`: Required because ADMIN-SERVICE uses `UserClient` Feign interface to call USER-SERVICE.

### 26.2 Entity: ServicePlan.java

```java
package com.adminservice.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String description;
}
```

**Purpose:** Represents a car wash service package, e.g.:
```json
{ "name": "Premium Wash", "price": 999.0, "description": "Full interior + exterior + wax" }
{ "name": "Basic Wash", "price": 299.0, "description": "Exterior only" }
```

### 26.3 Repository: ServicePlanRepository.java

```java
package com.adminservice.Repository;

import com.adminservice.Entity.ServicePlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePlanRepository extends JpaRepository<ServicePlan, Long> {
}
```

No custom methods - only uses inherited `save()`, `findAll()`.

### 26.4 DTO: User.java

```java
package com.adminservice.DTO;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String role;
}
```

**Important:** This is NOT the same `User` entity from USER-SERVICE. This is a DTO used by ADMIN-SERVICE to represent user data received from USER-SERVICE via Feign. Each service defines its own DTOs for the data it receives from other services.

### 26.5 Feign Client: UserClient.java

```java
package com.adminservice.DTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "USER-SERVICE", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/user/all")
    List<?> getAllUsers();
}
```

`List<?>`: Uses wildcard because the response contains User objects with fields that may change. `List<?>` is flexible.

### 26.6 Config: FeignConfig.java

```java
package com.adminservice.DTO;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                String email = request.getHeader("X-User-Email");
                String role = request.getHeader("X-User-Role");
                if (email != null) template.header("X-User-Email", email);
                if (role != null) template.header("X-User-Role", role);
            }
        };
    }
}
```

This is the **same FeignConfig pattern** used across ORDER-SERVICE, RATING-SERVICE, INVOICE-SERVICE. Every service that makes Feign calls needs this interceptor to forward the identity headers.

### 26.7 Service: AdminService.java

```java
package com.adminservice.Service;

import com.adminservice.DTO.UserClient;
import com.adminservice.Entity.ServicePlan;
import com.adminservice.Repository.ServicePlanRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ServicePlanRepository repo;
    private final UserClient userClient;

    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "usersFallback")
    public List<?> getUsers() {
        return userClient.getAllUsers();
    }

    public List<?> usersFallback(Exception e) {
        System.out.println("USER SERVICE DOWN (Admin): " + e.getMessage());
        return List.of();
    }

    public ServicePlan createPlan(ServicePlan plan) {
        return repo.save(plan);
    }

    public List<ServicePlan> getPlans() {
        return repo.findAll();
    }

    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "reportFallback")
    public String getReport() {
        return "Total Users: " + userClient.getAllUsers().size();
    }

    public String reportFallback(Exception e) {
        return "Report unavailable: User service is down";
    }
}
```

**Key Patterns:**

`getUsers()` and `getReport()` both have `@Retry` + `@CircuitBreaker` because they call USER-SERVICE via Feign. If USER-SERVICE is down:
1. Retry 3 times with 1-second delay
2. If all retries fail → circuit breaker records failure
3. Fallback returns empty list or error message

`createPlan()` and `getPlans()` have NO resilience annotations because they only access the local database - no inter-service call that could fail.

### 26.8 Controller: AdminController.java

```java
package com.adminservice.Controller;

import com.adminservice.Entity.ServicePlan;
import com.adminservice.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied: Admin only");
        }
    }

    @GetMapping("/users")
    public List<?> users(@RequestHeader("X-User-Role") String role) {
        checkAdmin(role);
        return service.getUsers();
    }

    @PostMapping("/plan")
    public ServicePlan create(@RequestBody ServicePlan plan,
                              @RequestHeader("X-User-Role") String role) {
        checkAdmin(role);
        return service.createPlan(plan);
    }

    @GetMapping("/plans")
    public List<ServicePlan> plans(@RequestHeader("X-User-Role") String role) {
        checkAdmin(role);
        return service.getPlans();
    }

    @GetMapping("/report")
    public String report(@RequestHeader("X-User-Role") String role) {
        checkAdmin(role);
        return service.getReport();
    }
}
```

**Authorization Pattern:**

`@RequestHeader("X-User-Role") String role`: Reads the role header set by the Gateway.

`checkAdmin(String role)`: Throws `RuntimeException("Access Denied: Admin only")` if the role is not `ADMIN`. This is **application-level authorization** - the Gateway handles authentication (verifying identity via JWT), and the service handles authorization (checking permissions via role).

**Why not use Spring Security for this?** Spring Security would require a full security filter chain, which adds unnecessary complexity when you just need a simple role check. A helper method is simpler, more readable, and sufficient for this use case.

### 26.9 Service: JwtService.java (Admin)

```java
package com.adminservice.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@RefreshScope
@Service
public class JwtService {
    private final String SECRET = "mysupersecretkeymysupersecretkey12345";

    public Claims extractAllClaims(String token) {
        Key key = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
        return Jwts.parserBuilder()
            .setSigningKey(key).build()
            .parseClaimsJws(token).getBody();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
}
```

**Note:** Uses `SecretKeySpec` instead of `Keys.hmacShaKeyFor()` - both achieve the same thing (creating an HMAC key). This JwtService is not actively used since the Gateway validates JWT.

---

## Chapter 27: RATING-SERVICE - File by File

**Port:** 8086 | **Database:** rating_db | **Dependencies:** Eureka, Config Server, MySQL, Feign (USER-SERVICE)

### 27.1 RatingServiceApplication.java

```java
package com.ratingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RatingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RatingServiceApplication.class, args);
    }
}
```

### 27.2 Entity: Review.java

```java
package com.ratingservice.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long orderId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
```

`@Table(name = "reviews")`: Explicitly names the table `reviews` instead of the default `review`.

`Integer rating`: Rating from 1 to 5 (validated in the service layer).

`Long orderId`: References which order this review is for. Multiple reviews can exist for the same order.

### 27.3 Repository: ReviewRepository.java

```java
package com.ratingservice.Repository;

import com.ratingservice.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
    List<Review> findByOrderId(Long orderId);
}
```

**Two Custom Queries:**
- `findByUserId(Long userId)` → `SELECT * FROM reviews WHERE user_id = ?` → Used for "my reviews"
- `findByOrderId(Long orderId)` → `SELECT * FROM reviews WHERE order_id = ?` → Used for "reviews for this order" (public endpoint)

### 27.4 DTO: User.java + UserClient.java

```java
// User.java
@Data
public class User {
    private Long id;
    private String email;
}

// UserClient.java
@FeignClient(name = "USER-SERVICE", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/user/email")
    User getUserByEmail(@RequestParam("email") String email);
}
```

Same pattern as other services - calls USER-SERVICE to convert email to user ID.

### 27.5 Service: ReviewService.java

```java
package com.ratingservice.Services;

import com.ratingservice.DTO.User;
import com.ratingservice.DTO.UserClient;
import com.ratingservice.Entity.Review;
import com.ratingservice.Repository.ReviewRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repo;
    private final UserClient userClient;

    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    public User getUser(String email) {
        return userClient.getUserByEmail(email);
    }

    public User userFallback(String email, Exception e) {
        System.out.println("USER SERVICE DOWN (Review)");
        User user = new User();
        user.setId(0L);
        user.setEmail("fallback@gmail.com");
        return user;
    }

    public Review addReview(Review review, String email) {
        User user = getUser(email);
        if (user.getId() == 0L) {
            throw new RuntimeException("User service unavailable");
        }
        review.setUserId(user.getId());
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1-5");
        }
        review.setCreatedAt(LocalDateTime.now());
        return repo.save(review);
    }

    public List<Review> getByOrder(Long orderId) {
        return repo.findByOrderId(orderId);
    }

    public List<Review> getMyReviews(String email) {
        User user = getUser(email);
        if (user.getId() == 0L) {
            throw new RuntimeException("User service unavailable");
        }
        return repo.findByUserId(user.getId());
    }
}
```

**Key Logic:**

`addReview()`:
1. Gets user from USER-SERVICE (with circuit breaker)
2. Checks if fallback was triggered (`id == 0L`) → throws error because you can't create a review without a valid user
3. Validates rating is 1-5 → throws if out of range
4. Sets `createdAt` to current timestamp
5. Saves and returns

`getByOrder()`: Returns all reviews for a specific order. This is a **public endpoint** (no authentication required) - anyone can view reviews for an order.

### 27.6 Controller: ReviewController.java

```java
package com.ratingservice.Controller;

import com.ratingservice.Entity.Review;
import com.ratingservice.Services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping("/add")
    public Review addReview(@RequestBody Review review,
                            @RequestHeader("X-User-Email") String email) {
        return service.addReview(review, email);
    }

    @GetMapping("/my")
    public List<Review> myReviews(@RequestHeader("X-User-Email") String email) {
        return service.getMyReviews(email);
    }

    @GetMapping("/order/{orderId}")
    public List<Review> getByOrder(@PathVariable Long orderId) {
        return service.getByOrder(orderId);
    }
}
```

**Note:** The `@PostMapping("/add")` accepts a `Review` entity directly as `@RequestBody`. Ideally, this should use a DTO (like `ReviewRequest`) to prevent clients from setting `id`, `userId`, or `createdAt`. The service overrides these fields, but a DTO would be cleaner.

`@GetMapping("/order/{orderId}")`: This is public - no `X-User-Email` header required. The Gateway skips JWT validation for `/review/order/**`.

---

## Chapter 28: INVOICE-SERVICE - File by File

**Port:** 8087 | **Database:** invoice_db | **Dependencies:** Eureka, Config Server, MySQL, Feign (USER-SERVICE), OpenPDF

INVOICE-SERVICE generates GST-compliant tax invoices. It can create invoice records in the database and generate downloadable PDF documents.

### 28.1 InvoiceServiceApplication.java

```java
package com.invoiceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InvoiceServiceApplication {
    // ... standard main class
}
```

### 28.2 Entity: Invoice.java

```java
package com.invoiceservice.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long orderId;
    private Double amount;
    private Double cgst;
    private Double sgst;
    private Double totalAmount;
    private String invoiceNumber;
    private String filePath;
    private LocalDateTime createdAt;
}
```

**Field Breakdown:**
- `amount`: Base service amount (e.g., 500.0)
- `cgst`: Central Goods and Services Tax at 9% (e.g., 45.0)
- `sgst`: State Goods and Services Tax at 9% (e.g., 45.0)
- `totalAmount`: amount + cgst + sgst (e.g., 590.0)
- `invoiceNumber`: Unique invoice identifier like `"INV-1712400000000"` (timestamp-based)
- `filePath`: Path where the PDF is stored (currently not used - PDF is returned directly)

### 28.3 Repository: InvoiceRepository.java

```java
package com.invoiceservice.Repository;

import com.invoiceservice.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUserId(Long userId);
}
```

### 28.4 DTO: User.java (Invoice Service version)

```java
package com.invoiceservice.DTO;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String phone;
}
```

**Important:** This User DTO has MORE fields than the User DTOs in ORDER-SERVICE or RATING-SERVICE (which only have `id` and `email`). INVOICE-SERVICE needs `phone` to include it on the invoice PDF. Each service defines the DTO with only the fields it needs.

### 28.5 DTO: JwtContextHolder.java

```java
package com.invoiceservice.DTO;

public class JwtContextHolder {
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

    public static void setToken(String token) { tokenHolder.set(token); }
    public static String getToken() { return tokenHolder.get(); }
    public static void clear() { tokenHolder.remove(); }
}
```

**What is ThreadLocal?**

`ThreadLocal` stores a separate value for each thread. In a web server, each request is handled by a different thread. So `JwtContextHolder` stores the JWT token for the current request's thread.

```
Thread 1 (Request A): JwtContextHolder.setToken("tokenA")
Thread 2 (Request B): JwtContextHolder.setToken("tokenB")

Thread 1: JwtContextHolder.getToken() → "tokenA"  (not "tokenB"!)
Thread 2: JwtContextHolder.getToken() → "tokenB"  (not "tokenA"!)
```

This is useful if you need to pass the JWT to Feign interceptors without going through method parameters. However, in the current architecture with X-User-Email headers, this is not actively used.

### 28.6 Service: InvoiceService.java

```java
package com.invoiceservice.Service;

import com.invoiceservice.DTO.User;
import com.invoiceservice.DTO.UserClient;
import com.invoiceservice.Entity.Invoice;
import com.invoiceservice.Repository.InvoiceRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repo;
    private final UserClient userClient;

    @Retry(name = "userService")
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    public User getUser(String email) {
        return userClient.getUserByEmail(email);
    }

    public User getUserFallback(String email, Exception e) {
        User u = new User();
        u.setId(0L);
        u.setEmail(email);
        return u;
    }

    public Invoice generateInvoice(Long orderId, Double amount, String email) {
        User user = getUser(email);
        double cgst = amount * 0.09;
        double sgst = amount * 0.09;
        double total = amount + cgst + sgst;

        Invoice invoice = new Invoice();
        invoice.setUserId(user.getId());
        invoice.setOrderId(orderId);
        invoice.setAmount(amount);
        invoice.setCgst(cgst);
        invoice.setSgst(sgst);
        invoice.setTotalAmount(total);
        invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
        invoice.setCreatedAt(LocalDateTime.now());
        return repo.save(invoice);
    }

    public byte[] generatePdf(Long orderId, Double amount, String email) {
        User user = getUser(email);
        double platformFee = amount * 0.15;
        double taxable = amount + platformFee;
        double cgst = taxable * 0.09;
        double sgst = taxable * 0.09;
        double total = taxable + cgst + sgst;

        try {
            Document doc = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, out);
            doc.open();

            // Title
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("TAX INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph(" "));

            // Invoice details
            doc.add(new Paragraph("Invoice No: INV-" + System.currentTimeMillis()));
            doc.add(new Paragraph("Date: " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))));
            doc.add(new Paragraph(" "));

            // Seller details
            doc.add(new Paragraph("SELLER DETAILS", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            doc.add(new Paragraph("Car Wash Pvt Ltd"));
            doc.add(new Paragraph("GSTIN: 27ABCDE1234F1Z5"));
            doc.add(new Paragraph("Pune, Maharashtra (Code: 27)"));
            doc.add(new Paragraph(" "));

            // Buyer details
            doc.add(new Paragraph("BUYER DETAILS", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            doc.add(new Paragraph("Email: " + user.getEmail()));
            doc.add(new Paragraph("Phone: " + user.getPhone()));
            doc.add(new Paragraph(" "));

            // Service table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell("Description"); table.addCell("Days");
            table.addCell("Rate"); table.addCell("Amount");
            table.addCell("Car Wash Service"); table.addCell("1");
            table.addCell("Rs. " + amount); table.addCell("Rs. " + amount);
            doc.add(table);
            doc.add(new Paragraph(" "));

            // Bill breakdown
            doc.add(new Paragraph("Bill Breakdown", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            doc.add(new Paragraph("Service Amount: Rs. " + amount));
            doc.add(new Paragraph("Platform Fee (15%): Rs. " + platformFee));
            doc.add(new Paragraph("Taxable Value: Rs. " + taxable));
            doc.add(new Paragraph("CGST (9%): Rs. " + cgst));
            doc.add(new Paragraph("SGST (9%): Rs. " + sgst));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("TOTAL: Rs. " + total,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("This is a computer-generated invoice."));

            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

**PDF Generation Explained:**

This uses **OpenPDF** (iText fork, version 2.1.7) for PDF generation:

1. `Document doc = new Document()` → Creates a new PDF document
2. `PdfWriter.getInstance(doc, out)` → Connects the document to a byte array output stream
3. `doc.open()` → Opens the document for writing
4. `Paragraph`, `Font`, `PdfPTable` → Content elements added to the document
5. `doc.close()` → Finalizes the PDF
6. `out.toByteArray()` → Returns the PDF as bytes

**GST Calculation:**
```
Base amount:     Rs. 500.00
Platform Fee:    Rs. 500 * 15% = Rs. 75.00
Taxable Value:   Rs. 500 + 75 = Rs. 575.00
CGST (9%):       Rs. 575 * 9% = Rs. 51.75
SGST (9%):       Rs. 575 * 9% = Rs. 51.75
Total:           Rs. 575 + 51.75 + 51.75 = Rs. 678.50
```

### 28.7 Controller: InvoiceController.java

```java
package com.invoiceservice.Controller;

import com.invoiceservice.Entity.Invoice;
import com.invoiceservice.Service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService service;

    @PostMapping("/generate")
    public Invoice generate(@RequestParam Long orderId,
                            @RequestParam Double amount,
                            @RequestHeader("X-User-Email") String email) {
        return service.generateInvoice(orderId, amount, email);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> pdf(@RequestParam Long orderId,
                                      @RequestParam Double amount,
                                      @RequestHeader("X-User-Email") String email) {
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=invoice.pdf")
            .header("Content-Type", "application/pdf")
            .body(service.generatePdf(orderId, amount, email));
    }
}
```

**The PDF endpoint returns a downloadable file:**

`ResponseEntity<byte[]>`: Instead of returning a JSON object, this returns raw bytes.
- `Content-Disposition: attachment; filename=invoice.pdf` → Tells the browser to download the file
- `Content-Type: application/pdf` → Tells the browser it's a PDF file
- `.body(service.generatePdf(...))` → The actual PDF bytes

---

## Chapter 29: NOTIFICATION-SERVICE - File by File

**Port:** 8089 | **Database:** notification_db | **Dependencies:** Eureka, Config Server, MySQL, RabbitMQ

NOTIFICATION-SERVICE is an **event-driven** service. It does NOT receive HTTP requests from the Gateway for its core functionality. Instead, it listens to RabbitMQ for order events and creates notifications.

### 29.1 NotificationServiceApplication.java

```java
package com.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
```

No `@EnableFeignClients` - this service doesn't call other services via Feign.

### 29.2 Entity: Notification.java

```java
package com.notification_service.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String message;
    private String type;          // EMAIL, SMS, PUSH
    private LocalDateTime createdAt;
}
```

`String type`: Categorizes the notification channel. Currently only `"EMAIL"` is used. In a production system, you could have SMS (via Twilio) or PUSH (via Firebase).

### 29.3 Repository: NotificationRepository.java

```java
package com.notification_service.Repository;

import com.notification_service.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
```

Only uses inherited `save()` and `findAll()`.

### 29.4 DTO: OrderEvent.java

```java
package com.notification_service.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private Long orderId;
    private String email;
    private Double amount;
    private String status;
}
```

**Critical:** This class MUST have `@NoArgsConstructor` and the same field names/types as the `OrderEvent` in ORDER-SERVICE. When Jackson deserializes the JSON message from RabbitMQ, it creates an instance using the no-args constructor and sets fields via reflection.

### 29.5 DTO: RabbitConfig.java

```java
package com.notification_service.DTO;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.*;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RefreshScope
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

**Why is the same RabbitMQ config in both ORDER-SERVICE and NOTIFICATION-SERVICE?**

Both services need the queue, exchange, and binding definitions. When either service starts, it declares these resources in RabbitMQ (if they don't exist). This is **idempotent** - declaring an existing queue is a no-op.

**Different MessageConverter names:**
- ORDER-SERVICE uses `JacksonJsonMessageConverter` (older name)
- NOTIFICATION-SERVICE uses `Jackson2JsonMessageConverter` (newer name)
- Both work the same way - they serialize/deserialize Java objects to/from JSON

### 29.6 Listener: NotificationListener.java

```java
package com.notification_service.listener;

import com.notification_service.DTO.OrderEvent;
import com.notification_service.DTO.RabbitConfig;
import com.notification_service.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService service;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consume(OrderEvent event) {
        log.info("Event Received: {}", event);

        String message = "Order ID: " + event.getOrderId()
                + " | Status: " + event.getStatus()
                + " | Amount: " + event.getAmount();

        service.save(event.getEmail(), message);

        System.out.println("=================================");
        System.out.println("EMAIL SENT");
        System.out.println("To: " + event.getEmail());
        System.out.println("Message: " + message);
        System.out.println("=================================");
    }
}
```

**Annotation Breakdown:**

`@Slf4j` (Lombok): Generates `private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);`. You can then use `log.info()`, `log.error()`, etc.

`@RabbitListener(queues = RabbitConfig.QUEUE)`: This is the MAGIC annotation. It tells Spring AMQP:
1. Connect to RabbitMQ
2. Subscribe to queue `"order_queue"`
3. When a message arrives, deserialize it to `OrderEvent`
4. Call `consume(event)` with the deserialized object
5. After `consume()` returns successfully, acknowledge the message (remove from queue)
6. If `consume()` throws an exception, the message is NOT acknowledged (stays in queue for retry)

**Message Lifecycle:**
```
ORDER-SERVICE publishes:
  { "orderId": 1, "email": "sarthak@gmail.com", "amount": 500.0, "status": "CREATED" }
  
RabbitMQ receives and stores in queue

NOTIFICATION-SERVICE's @RabbitListener picks up the message:
  1. Jackson deserializes JSON → OrderEvent object
  2. consume() is called
  3. Builds message string
  4. Saves to database (Notification entity)
  5. Logs simulated email
  6. Message acknowledged and removed from queue
```

### 29.7 Service: NotificationService.java

```java
package com.notification_service.Service;

import com.notification_service.Entity.Notification;
import com.notification_service.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repo;

    public Notification save(String email, String message) {
        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setMessage(message);
        notification.setType("EMAIL");
        notification.setCreatedAt(LocalDateTime.now());
        return repo.save(notification);
    }

    public List<Notification> getAll() {
        return repo.findAll();
    }
}
```

### 29.8 Controller: NotificationController.java

```java
package com.notification_service.Controller;

import com.notification_service.Entity.Notification;
import com.notification_service.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @GetMapping("/all")
    public List<Notification> getAll() {
        return service.getAll();
    }
}
```

This is a **public endpoint** (no JWT required - the Gateway skips `/notification` paths). It allows viewing all notifications in the system.

---

## Chapter 30: API-GATEWAY - File by File

### 30.1 JwtAuthenticationFilter.java

```java
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // PUBLIC APIs - no JWT needed
        if (path.startsWith("/auth") ||
            path.startsWith("/user/email") ||
            path.startsWith("/user/all") ||
            path.startsWith("/review/order") ||
            path.startsWith("/notification")) {
            return chain.filter(exchange);
        }

        String header = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return onError(exchange);
        }

        String token = header.substring(7);
        try {
            jwtUtil.validateToken(token);
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(headers -> {
                    headers.set("X-User-Email", email);
                    headers.set("X-User-Role", role);
                })
                .build();

            return chain.filter(exchange.mutate().request(request).build());
        } catch (Exception e) {
            return onError(exchange);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
```

**Critical Annotations and Classes:**

`GlobalFilter`: A Spring Cloud Gateway interface. Unlike `GatewayFilter` (route-specific), `GlobalFilter` runs on ALL requests. Our JWT filter must check EVERY request.

`ServerWebExchange`: The reactive equivalent of `HttpServletRequest` + `HttpServletResponse`. Contains the full request and response context.

`GatewayFilterChain`: The filter chain. `chain.filter(exchange)` passes the request to the next filter in the chain.

`Mono<Void>`: A reactive type from Project Reactor. It represents a potentially asynchronous operation that completes with no value (void). The Gateway is reactive (non-blocking), so filters must return `Mono`.

`exchange.getRequest().mutate()`: Creates a MUTABLE copy of the request. The original request headers are read-only, so you must create a new request with the additional headers.

### 30.2 JwtUtil.java

```java
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    public void validateToken(String token) { getClaims(token); }
    public String extractEmail(String token) { return getClaims(token).getSubject(); }
    public String extractRole(String token) { return (String) getClaims(token).get("role"); }
}
```

### 30.3 RateLimiterConfig.java

```java
@Configuration
public class RateLimiterConfig {
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String email = exchange.getRequest().getHeaders().getFirst("X-User-Email");
            if (email != null) return Mono.just(email);
            return Mono.just(
                exchange.getRequest().getRemoteAddress() != null
                    ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
                    : "anonymous"
            );
        };
    }
}
```

`KeyResolver`: Determines the rate limiting key. Authenticated users are rate-limited by email; anonymous users by IP address. This prevents one user from exhausting the rate limit for everyone.

---

## Chapter 31: EUREKA-SERVER - File by File

**Port:** 8761 | **Database:** None | **Dependencies:** None

### 31.1 EurekaServerApplication.java

```java
package com.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

`@EnableEurekaServer`: This single annotation transforms a regular Spring Boot application into a Eureka service registry. It:
1. Starts the Eureka server on the configured port (8761)
2. Provides a REST API for service registration/discovery
3. Serves the Eureka web dashboard at `http://localhost:8761`
4. Manages service heartbeats and eviction
5. Handles replication (in a multi-Eureka setup)

### 31.2 application.properties

```properties
spring.application.name=EUREKA-SERVER
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

**Line-by-Line:**
- `spring.application.name=EUREKA-SERVER` → The service name shown in logs
- `server.port=8761` → Standard Eureka port (convention)
- `eureka.client.register-with-eureka=false` → Eureka should NOT register itself as a client. It IS the server. Without this, Eureka would try to register with itself and throw errors.
- `eureka.client.fetch-registry=false` → Eureka should NOT try to fetch the registry from another Eureka server. It IS the source of truth. (In a multi-Eureka cluster, you'd set this to true for peer replication.)

### 31.3 pom.xml Key Dependencies

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

This is the ONLY Spring Cloud dependency needed for the Eureka server. It includes:
- Eureka server core
- Jersey (JAX-RS) for the REST API
- Jackson for JSON serialization
- Embedded web server

---

## Chapter 32: CONFIG-SERVER - File by File

**Port:** 8888 | **Database:** None | **Dependencies:** Eureka, RabbitMQ (for Cloud Bus)

### 32.1 ConfigServerApplication.java

```java
package com.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```

`@EnableConfigServer`: Transforms this application into a Spring Cloud Config Server. It:
1. Exposes REST endpoints for serving configuration
2. Connects to a Git repository to fetch property files
3. Caches configuration locally for performance
4. Supports multiple profiles (dev, prod, default)

**Config Server REST API:**
```
GET /USER-SERVICE/default        → Returns USER-SERVICE.properties + application.properties
GET /ORDER-SERVICE/default       → Returns ORDER-SERVICE.properties + application.properties
GET /APPLICATION/default         → Returns application.properties (shared config)
```

### 32.2 application.properties

```properties
spring.application.name=CONFIG-SERVER
server.port=8888

# Git repository containing all config files
spring.cloud.config.server.git.uri=https://github.com/sarthakpawar0912/Car-Wash-central-config-repo-.git
spring.cloud.config.server.git.clone-on-start=true
spring.cloud.config.server.git.default-label=main
spring.cloud.config.server.git.skip-ssl-validation=true

# Register with Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# Enable debug logging for config issues
logging.level.org.springframework.cloud.config=DEBUG

# Expose all actuator endpoints
management.endpoints.web.exposure.include=*

# RabbitMQ for Spring Cloud Bus (dynamic config refresh)
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

**Line-by-Line:**
- `spring.cloud.config.server.git.uri=...` → The GitHub repository URL containing all service config files
- `spring.cloud.config.server.git.clone-on-start=true` → Clone the Git repo when Config Server starts (faster first request)
- `spring.cloud.config.server.git.default-label=main` → Use the `main` branch
- `spring.cloud.config.server.git.skip-ssl-validation=true` → Skip SSL certificate validation for Git (useful for self-signed certs or corporate proxies)
- `spring.rabbitmq.*` → RabbitMQ connection for Spring Cloud Bus, which broadcasts config refresh events to all services

---

## Chapter 33: Docker Compose - Line by Line

The `docker-compose.yml` file defines all 17 containers for the entire system.

### 33.1 Infrastructure Section

```yaml
services:
  mysql:
    image: mysql:8.0                    # Official MySQL 8.0 Docker image
    container_name: mysql               # Fixed container name for easy reference
    ports:
      - "3306:3306"                     # Map host port 3306 to container port 3306
    environment:
      MYSQL_ROOT_PASSWORD: 1234567890   # Root password for MySQL
      MYSQL_DATABASE: car_wash          # Create this database on startup
    volumes:
      - mysql-data:/var/lib/mysql       # Persist data in a named volume
    healthcheck:                        # Docker health check
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s                     # Check every 10 seconds
      timeout: 5s                       # Timeout after 5 seconds
      retries: 10                       # Retry 10 times before marking unhealthy
    networks:
      - carwash-network                 # Attach to the shared network
```

**Key Concepts:**
- `image: mysql:8.0` → Pulls the official MySQL 8.0 image from Docker Hub
- `ports: "3306:3306"` → Format is `host:container`. This maps port 3306 on your machine to port 3306 inside the container
- `volumes: mysql-data:/var/lib/mysql` → A **named volume** that persists MySQL data even when the container is destroyed. Without this, all data would be lost on `docker compose down`.
- `healthcheck` → Docker periodically runs `mysqladmin ping` to check if MySQL is ready. Other services use `depends_on: mysql: condition: service_healthy` to wait for MySQL.

```yaml
  rabbitmq:
    image: rabbitmq:3-management        # RabbitMQ with management UI
    container_name: rabbitmq
    ports:
      - "5672:5672"                     # AMQP protocol port
      - "15672:15672"                   # Management UI port
    healthcheck:
      test: ["CMD", "rabbitmq-diagnostics", "check_port_connectivity"]
      interval: 10s
      timeout: 5s
      retries: 10
```

`rabbitmq:3-management` includes the management plugin which provides a web UI at `http://localhost:15672` (login: guest/guest).

```yaml
  redis:
    image: redis:latest
    container_name: redis
    ports:
      - "6379:6379"                     # Redis default port
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]   # Redis responds "PONG" if healthy
```

```yaml
  zipkin:
    image: openzipkin/zipkin
    container_name: zipkin
    ports:
      - "9411:9411"                     # Zipkin UI and API port
```

Zipkin has no health check - it starts quickly and rarely fails.

### 33.2 Discovery & Config Section

```yaml
  eureka-server:
    build: ./eureka-server              # Build from local Dockerfile
    image: sarthakpawar09/carwash-eureka-server:latest  # Tag for Docker Hub
    container_name: eureka-server
    ports:
      - "8761:8761"
    environment:
      - EUREKA_CLIENT_REGISTER-WITH-EUREKA=false  # Override property via env var
      - EUREKA_CLIENT_FETCH-REGISTRY=false
    healthcheck:
      test: ["CMD", "wget", "--spider", "-q", "http://localhost:8761/actuator/health"]
      start_period: 40s                 # Wait 40s before first health check
      interval: 15s
      timeout: 5s
      retries: 10
```

`build: ./eureka-server` → Docker builds the image from `./eureka-server/Dockerfile`
`start_period: 40s` → Spring Boot takes time to start. Don't check health for 40 seconds.
`EUREKA_CLIENT_REGISTER-WITH-EUREKA=false` → Spring Boot resolves environment variables: `EUREKA_CLIENT_REGISTER-WITH-EUREKA` becomes `eureka.client.register-with-eureka`

```yaml
  config-server:
    build: ./config-server
    image: sarthakpawar09/carwash-config-server:latest
    depends_on:
      eureka-server:
        condition: service_healthy      # Wait for Eureka to be healthy
      rabbitmq:
        condition: service_healthy      # Wait for RabbitMQ (Cloud Bus)
    environment:
      - EUREKA_CLIENT_SERVICE-URL_DEFAULTZONE=http://eureka-server:8761/eureka
      - SPRING_RABBITMQ_HOST=rabbitmq   # Use Docker service name as hostname
```

`depends_on: condition: service_healthy` → Docker Compose waits until the dependency's health check passes before starting this service.

`http://eureka-server:8761/eureka` → Inside Docker's network, services reference each other by **service name** (not localhost). Docker's built-in DNS resolves `eureka-server` to the Eureka container's IP.

### 33.3 API Gateway Section

```yaml
  api-gateway:
    build: ./api-gateway
    image: sarthakpawar09/carwash-api-gateway:latest
    ports:
      - "8080:8080"                     # The only externally-accessible port
    environment:
      - SPRING_CONFIG_IMPORT=configserver:http://config-server:8888
      - EUREKA_CLIENT_SERVICE-URL_DEFAULTZONE=http://eureka-server:8761/eureka
      - SPRING_DATA_REDIS_HOST=redis    # Redis for rate limiting
    depends_on:
      config-server:
        condition: service_healthy
      redis:
        condition: service_healthy
```

`SPRING_CONFIG_IMPORT=configserver:http://config-server:8888` → Tells the Gateway to fetch its config from Config Server at the Docker hostname `config-server`.

### 33.4 Business Services Section

All 8 business services follow the same pattern:

```yaml
  user-service:
    build: ./user-service
    image: sarthakpawar09/carwash-user-service:latest
    ports:
      - "8081:8081"
    environment:
      - SPRING_CONFIG_IMPORT=configserver:http://config-server:8888
      - EUREKA_CLIENT_SERVICE-URL_DEFAULTZONE=http://eureka-server:8761/eureka
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/car_wash
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=1234567890
      - SPRING_RABBITMQ_HOST=rabbitmq
      - MANAGEMENT_ZIPKIN_TRACING_ENDPOINT=http://zipkin:9411/api/v2/spans
    depends_on:
      config-server:
        condition: service_healthy
      mysql:
        condition: service_healthy
      rabbitmq:
        condition: service_healthy
```

**Environment Variable Pattern:**
Each service overrides connection properties to use Docker service names:
- `mysql` instead of `localhost` for database
- `rabbitmq` instead of `localhost` for messaging
- `zipkin` instead of `localhost` for tracing
- `config-server` instead of `localhost` for configuration
- `eureka-server` instead of `localhost` for discovery

### 33.5 Networks and Volumes

```yaml
networks:
  carwash-network:
    driver: bridge                      # Default Docker network driver

volumes:
  mysql-data:                           # Persistent MySQL data
  grafana-data:                         # Persistent Grafana dashboards
```

`bridge` network: All containers on this network can communicate using service names. Containers NOT on this network cannot reach them. This provides network isolation.

---

## Chapter 34: Kubernetes Manifests - File by File

### 34.1 namespace.yml

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: carwash
  labels:
    app: carwash-system
```

**Purpose:** Creates an isolated namespace `carwash` for all resources. Namespaces provide:
- Resource isolation (don't interfere with other apps in the cluster)
- Access control (RBAC per namespace)
- Resource quotas (limit CPU/memory per namespace)

### 34.2 secrets.yml

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: carwash-secrets
  namespace: carwash
type: Opaque
stringData:
  mysql-root-password: "1234567890"
  mysql-username: "root"
```

**Kubernetes Secrets** store sensitive data (passwords, API keys). They're base64-encoded and can be mounted as environment variables in Pods. `stringData` accepts plain text (K8s encodes it automatically).

### 34.3 infrastructure.yml (Key Sections)

```yaml
# MySQL Deployment
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql
  namespace: carwash
spec:
  replicas: 1                           # Only one MySQL instance
  selector:
    matchLabels:
      app: mysql
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
      - name: mysql
        image: mysql:8.0
        ports:
        - containerPort: 3306
        env:
        - name: MYSQL_ROOT_PASSWORD
          valueFrom:
            secretKeyRef:
              name: carwash-secrets      # Reference the Secret
              key: mysql-root-password
        volumeMounts:
        - name: mysql-storage
          mountPath: /var/lib/mysql       # Where MySQL stores data
        resources:
          requests:                       # Minimum resources guaranteed
            memory: "256Mi"
            cpu: "250m"
          limits:                         # Maximum resources allowed
            memory: "512Mi"
            cpu: "500m"
        readinessProbe:                   # Is the pod ready to receive traffic?
          exec:
            command: ["mysqladmin", "ping", "-h", "localhost"]
          initialDelaySeconds: 30
          periodSeconds: 10
        livenessProbe:                    # Is the pod still alive?
          exec:
            command: ["mysqladmin", "ping", "-h", "localhost"]
          initialDelaySeconds: 60
          periodSeconds: 15
      volumes:
      - name: mysql-storage
        persistentVolumeClaim:
          claimName: mysql-pvc            # Persistent storage
```

**Key Kubernetes Concepts:**

`replicas: 1` → Only one Pod runs. For stateful services like MySQL, you typically run 1 replica (or use StatefulSet for clustering).

`resources.requests` → The minimum CPU/memory the pod needs. The scheduler uses this to place the pod on a node with enough resources.
- `memory: "256Mi"` → 256 MiB of RAM guaranteed
- `cpu: "250m"` → 250 millicores (0.25 CPU cores) guaranteed

`resources.limits` → The maximum CPU/memory the pod can use. If the pod exceeds memory limits, it's killed (OOMKilled).

`readinessProbe` → K8s checks if the pod is ready to serve traffic. Until ready, it's removed from the Service's endpoint list.
`livenessProbe` → K8s checks if the pod is still alive. If it fails, the pod is restarted.

**Service Exposure:**
```yaml
apiVersion: v1
kind: Service
metadata:
  name: mysql
  namespace: carwash
spec:
  selector:
    app: mysql
  ports:
  - port: 3306
    targetPort: 3306
  type: ClusterIP                        # Internal only
```

`ClusterIP` → The default service type. Only accessible within the cluster. Other pods reach MySQL via `mysql.carwash.svc.cluster.local:3306` or simply `mysql:3306`.

### 34.4 gateway.yml

```yaml
spec:
  type: LoadBalancer                     # Externally accessible!
  ports:
  - port: 8080
    targetPort: 8080
```

The API Gateway uses `LoadBalancer` type - in a cloud environment (AWS, GCP, Azure), this creates a public IP address that routes external traffic to the Gateway pods.

### 34.5 services.yml Pattern

All 8 business services follow this pattern:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-service
  namespace: carwash
spec:
  replicas: 1
  template:
    spec:
      containers:
      - name: user-service
        image: sarthakpawar09/carwash-user-service:latest
        ports:
        - containerPort: 8081
        env:
        - name: SPRING_CONFIG_IMPORT
          value: "configserver:http://config-server:8888"
        - name: EUREKA_CLIENT_SERVICE-URL_DEFAULTZONE
          value: "http://eureka-server:8761/eureka"
        - name: SPRING_DATASOURCE_URL
          value: "jdbc:mysql://mysql:3306/car_wash"
        - name: SPRING_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: carwash-secrets
              key: mysql-root-password
        resources:
          requests: { memory: "256Mi", cpu: "250m" }
          limits: { memory: "512Mi", cpu: "500m" }
        startupProbe:
          httpGet:
            path: /actuator/health
            port: 8081
          initialDelaySeconds: 30
          periodSeconds: 10
          failureThreshold: 20           # Allow up to 200 seconds to start
---
apiVersion: v1
kind: Service
metadata:
  name: user-service
  namespace: carwash
spec:
  selector:
    app: user-service
  ports:
  - port: 8081
    targetPort: 8081
  type: ClusterIP                        # Internal only
```

`startupProbe` → Used instead of `readinessProbe.initialDelaySeconds`. Spring Boot services take 30-60 seconds to start. The startup probe keeps checking until the service is ready, without being killed by the liveness probe.

`failureThreshold: 20` with `periodSeconds: 10` → Allows up to 200 seconds for startup before K8s gives up.

---

## Chapter 35: CI/CD Pipeline - Line by Line

The `.github/workflows/build-and-push.yml` defines the CI/CD pipeline.

### 35.1 Pipeline Structure

```yaml
name: Build and Push Docker Images

on:
  push:
    branches: [ main ]                   # Run on pushes to main
  pull_request:
    branches: [ main ]                   # Run on PRs to main

jobs:
  # JOB 1: Detect which services changed
  detect-changes:
    runs-on: ubuntu-latest
    outputs:
      eureka-server: ${{ steps.filter.outputs.eureka-server }}
      config-server: ${{ steps.filter.outputs.config-server }}
      # ... all services
    steps:
      - uses: actions/checkout@v3
      - uses: dorny/paths-filter@v2
        id: filter
        with:
          filters: |
            eureka-server: 'eureka-server/**'
            config-server: 'config-server/**'
            api-gateway: 'api-gateway/**'
            user-service: 'user-service/**'
            # ... all services
```

`dorny/paths-filter` → Detects which directories changed in the commit. If only `user-service/` files changed, only `user-service` output is `true`.

```yaml
  # JOB 2: Build and push changed services
  build-and-push:
    needs: detect-changes                # Wait for job 1
    runs-on: ubuntu-latest
    strategy:
      matrix:
        include:
          - service: eureka-server
            context: ./eureka-server
            changed: ${{ needs.detect-changes.outputs.eureka-server }}
          - service: user-service
            context: ./user-service
            changed: ${{ needs.detect-changes.outputs.user-service }}
          # ... all services
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 21
        if: matrix.changed == 'true'     # Only if this service changed
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Build with Maven
        if: matrix.changed == 'true'
        run: cd ${{ matrix.context }} && mvn clean package -DskipTests
      - name: Build and push Docker image
        if: matrix.changed == 'true'
        run: |
          docker build -t sarthakpawar09/carwash-${{ matrix.service }}:latest ${{ matrix.context }}
          docker push sarthakpawar09/carwash-${{ matrix.service }}:latest
```

**Smart Build Strategy:** Only changed services are built. If you change ORDER-SERVICE, only ORDER-SERVICE is built and pushed. This saves CI time and Docker Hub bandwidth.

```yaml
  # JOB 3: Deploy to Kubernetes (main branch only)
  deploy-k8s:
    needs: build-and-push
    if: github.event_name == 'push' && github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Deploy manifests
        run: |
          kubectl apply -f k8s/namespace.yml
          kubectl apply -f k8s/secrets.yml
          kubectl apply -f k8s/infrastructure.yml
          kubectl apply -f k8s/discovery-config.yml
          kubectl apply -f k8s/gateway.yml
          kubectl apply -f k8s/services.yml
          kubectl apply -f k8s/monitoring.yml
```

Deployment only happens on pushes to `main` (not on PRs). Manifests are applied in dependency order.

---

## Chapter 36: Prometheus & Grafana Configuration

### 36.1 Prometheus Configuration (prometheus.yml)

```yaml
global:
  scrape_interval: 15s                   # Scrape all targets every 15 seconds

scrape_configs:
  - job_name: 'api-gateway'
    metrics_path: '/actuator/prometheus'  # Spring Boot Actuator endpoint
    static_configs:
      - targets: ['localhost:8080']      # Gateway address

  - job_name: 'user-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8081']

  - job_name: 'car-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8082']

  - job_name: 'order-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8083']

  - job_name: 'payment-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8084']

  - job_name: 'rating-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8086']

  - job_name: 'invoice-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8087']

  - job_name: 'admin-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8088']

  - job_name: 'notification-service'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8089']
```

**How It Works:**
1. Prometheus runs on port 9090
2. Every 15 seconds, it sends GET requests to each service's `/actuator/prometheus` endpoint
3. Each service responds with metrics in Prometheus format:
   ```
   http_server_requests_seconds_count{method="GET",uri="/order/my",status="200"} 42
   http_server_requests_seconds_sum{method="GET",uri="/order/my",status="200"} 1.234
   jvm_memory_used_bytes{area="heap"} 134217728
   process_cpu_usage 0.05
   ```
4. Prometheus stores these metrics as time-series data
5. You can query them at `http://localhost:9090` using PromQL

### 36.2 Key Metrics Available

Each service exposes these metrics via `micrometer-registry-prometheus`:

| Metric | Type | Description |
|--------|------|-------------|
| `http_server_requests_seconds_count` | Counter | Total number of HTTP requests |
| `http_server_requests_seconds_sum` | Counter | Total time spent processing requests |
| `http_server_requests_seconds_max` | Gauge | Max request duration |
| `jvm_memory_used_bytes` | Gauge | JVM heap/non-heap memory usage |
| `jvm_memory_max_bytes` | Gauge | Maximum JVM memory |
| `jvm_threads_live_threads` | Gauge | Number of live threads |
| `jvm_threads_daemon_threads` | Gauge | Number of daemon threads |
| `process_cpu_usage` | Gauge | CPU usage (0.0 to 1.0) |
| `system_cpu_count` | Gauge | Number of CPU cores |
| `jdbc_connections_active` | Gauge | Active database connections |
| `resilience4j_circuitbreaker_state` | Gauge | Circuit breaker state (0=closed, 1=open) |
| `resilience4j_circuitbreaker_calls_seconds_count` | Counter | Circuit breaker call count |

### 36.3 PromQL Query Examples

```promql
# Request rate per service (requests per second)
rate(http_server_requests_seconds_count{job="order-service"}[5m])

# Average response time
rate(http_server_requests_seconds_sum{job="order-service"}[5m]) 
  / rate(http_server_requests_seconds_count{job="order-service"}[5m])

# Error rate (5xx responses)
rate(http_server_requests_seconds_count{job="order-service",status=~"5.."}[5m])

# JVM memory usage
jvm_memory_used_bytes{job="order-service",area="heap"}

# Circuit breaker open?
resilience4j_circuitbreaker_state{job="order-service",name="userService"}
```

### 36.4 Grafana Dashboard Setup

1. Open Grafana: `http://localhost:3000` (admin/admin)
2. Add Data Source → Prometheus → URL: `http://localhost:9090` (or `http://prometheus:9090` in Docker)
3. Import Dashboard → Upload `grafana-carwash-dashboard.json`
4. The dashboard shows panels for:
   - Request rate per service
   - Response time percentiles (p50, p95, p99)
   - Error rate
   - JVM memory usage
   - Active threads
   - Circuit breaker status
   - HTTP status code distribution

### 36.5 Docker Compose Monitoring Stack

```yaml
  prometheus:
    image: prom/prometheus:latest
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus-docker.yml:/etc/prometheus/prometheus.yml
      # The Docker version uses service names instead of localhost

  grafana:
    image: grafana/grafana:latest
    ports:
      - "3001:3000"                      # Map to 3001 to avoid conflict with local Grafana
    environment:
      - GF_SECURITY_ADMIN_USER=admin
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - grafana-data:/var/lib/grafana    # Persist dashboards
    depends_on:
      - prometheus
```

`prometheus-docker.yml` differs from `prometheus.yml` only in the target addresses - it uses Docker service names (e.g., `api-gateway:8080`) instead of `localhost:8080`.

---

## Chapter 37: Complete Request Flow Walkthrough

### Full Scenario: Register → Add Car → Create Order → Pay → Review → Invoice

```
STEP 1: Register
POST http://localhost:8080/auth/register
Body: { "name": "Sarthak", "email": "sarthak@gmail.com", "password": "1234", "role": "CUSTOMER" }
→ Gateway: /auth = public, skip JWT → USER-SERVICE → BCrypt hash → Save → Generate JWT
← Response: { "token": "Bearer eyJ...", "role": "CUSTOMER" }

STEP 2: Add Car
POST http://localhost:8080/car/add
Authorization: Bearer eyJ...
Body: { "brand": "BMW", "model": "X5", "color": "Black", "numberPlate": "MH12AB1234" }
→ Gateway: Validate JWT → Add X-User-Email header → CAR-SERVICE → Save car
← Response: { "id": 1, "brand": "BMW" }

STEP 3: Create Order
POST http://localhost:8080/order/create
Authorization: Bearer eyJ...
Body: { "carId": 1, "serviceType": "PREMIUM" }
→ Gateway → ORDER-SERVICE
  → Feign → USER-SERVICE: Get user ID
  → Feign → CAR-SERVICE: Get user's cars, validate carId
  → Save order (status: CREATED)
  → RabbitMQ → NOTIFICATION-SERVICE: "Order CREATED"
← Response: { "id": 1, "status": "CREATED" }

STEP 4: Pay
POST http://localhost:8080/order/pay/1?amount=500
Authorization: Bearer eyJ...
→ Gateway → ORDER-SERVICE
  → Feign → USER-SERVICE: Verify user
  → Feign → PAYMENT-SERVICE: Process payment → SUCCESS
  → Update order status to COMPLETED
  → RabbitMQ → NOTIFICATION-SERVICE: "Order COMPLETED"
← Response: "Payment SUCCESS"

STEP 5: Review
POST http://localhost:8080/review/add
Authorization: Bearer eyJ...
Body: { "orderId": 1, "rating": 5, "comment": "Excellent!" }
→ Gateway → RATING-SERVICE
  → Feign → USER-SERVICE: Get user ID
  → Save review
← Response: { "id": 1, "rating": 5 }

STEP 6: Invoice PDF
GET http://localhost:8080/invoice/pdf?orderId=1&amount=500
Authorization: Bearer eyJ...
→ Gateway → INVOICE-SERVICE
  → Feign → USER-SERVICE: Get user details
  → Calculate GST (CGST 9% + SGST 9%)
  → Generate PDF with OpenPDF
← Response: PDF binary download
```

---

## Chapter 38: Common Errors & Debugging Guide

### 38.1 Error: 403 Forbidden

| Cause | Solution |
|-------|----------|
| Missing `Authorization` header | Add `Authorization: Bearer <token>` |
| Expired JWT (24h) | Login again to get fresh token |
| Spring Security blocking in service | Set `.anyRequest().permitAll()` in SecurityConfig |
| Wrong JWT secret key | Ensure Config Server has consistent `jwt.secret` |
| Non-admin accessing `/admin/**` | Use admin credentials |

### 38.2 Error: 401 Unauthorized

| Cause | Solution |
|-------|----------|
| Invalid JWT signature | Ensure same `jwt.secret` in Gateway and USER-SERVICE |
| Malformed token | Copy token exactly from login response |
| Missing "Bearer " prefix | Token must be `Bearer <token>` with space |

### 38.3 Error: 503 Service Unavailable

| Cause | Solution |
|-------|----------|
| Target service not registered in Eureka | Check Eureka dashboard at :8761 |
| Service still starting up | Wait for service to register |
| Wrong `spring.application.name` | Must match route's `lb://NAME` |

### 38.4 Error: Feign Connection Refused

| Cause | Solution |
|-------|----------|
| Target service is down | Start the service |
| Eureka hasn't synced yet | Wait 30 seconds for registry sync |
| Wrong service name in @FeignClient | Match with Eureka registered name |

### 38.5 Debug Checklist

```
1. Check Gateway logs → Is request reaching Gateway?
2. Check Eureka (:8761) → Is target service registered?
3. Check target service logs → Is request reaching the service?
4. Check Zipkin (:9411) → What's the full trace?
5. Check RabbitMQ (:15672) → Are messages stuck?
6. Check Config Server → GET :8888/SERVICE-NAME/default
```

---

## Chapter 39: Setup & Run Guide

### Prerequisites
- Java 21+, Maven 3.9+, Docker Desktop, MySQL 8.0+

### Quick Start (Docker Compose)
```bash
cd "C:\Users\Sarthak\OneDrive\Desktop\Car Wash System"
docker compose up -d
# Wait 2-3 minutes for all 17 containers
docker compose ps
```

### Manual Start (IDE)
```
1. Start: Eureka Server (8761)     ← Must be first
2. Start: Config Server (8888)     ← Must be second
3. Start: API Gateway (8080)       ← After Eureka + Config
4. Start: All other services       ← Any order
```

### Service Ports

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
| Zipkin | 9411 |
| RabbitMQ | 5672/15672 |
| Redis | 6379 |
| Prometheus | 9090 |
| Grafana | 3000 |

### Test Credentials

| Email | Password | Role |
|-------|----------|------|
| sarthak@gmail.com | 1234 | CUSTOMER |
| washer@gmail.com | 1234 | WASHER |
| admin@gmail.com | 1234 | ADMIN |

---

## Chapter 40: API Reference

### Auth APIs
```
POST /auth/register    Body: { name, email, phone, password, role }
POST /auth/login       Body: { email, password }
```

### User APIs
```
GET /user/email?email=X     (public, used by Feign)
GET /user/all               (public, used by Admin)
```

### Car APIs
```
POST   /car/add             JWT required, Body: { brand, model, color, numberPlate }
GET    /car/user/{userId}    JWT required
PUT    /car/update/{id}      JWT required, Body: { brand, model, color, numberPlate }
DELETE /car/delete/{id}      JWT required
```

### Order APIs
```
POST   /order/create         JWT required, Body: { carId, serviceType }
GET    /order/my              JWT required
GET    /order/{id}            JWT required
PUT    /order/assign/{id}     JWT required, Params: washerId, scheduledAt
PUT    /order/status/{id}     JWT required, Param: status
DELETE /order/delete/{id}     JWT required
POST   /order/pay/{orderId}   JWT required, Param: amount
```

### Payment APIs
```
POST /payment/pay    Internal only, Params: orderId, amount
```

### Rating APIs
```
POST /review/add              JWT required, Body: { orderId, rating, comment }
GET  /review/my               JWT required
GET  /review/order/{orderId}  Public
```

### Invoice APIs
```
POST /invoice/generate   JWT required, Params: orderId, amount
GET  /invoice/pdf         JWT required, Params: orderId, amount → PDF download
```

### Admin APIs (ADMIN role required)
```
GET  /admin/users     List all users
POST /admin/plan      Body: { name, price, description }
GET  /admin/plans     List all service plans
GET  /admin/report    System report
```

### Notification APIs
```
GET /notification/all    Public
```

---

## Chapter 41: Best Practices & Future Improvements

### Best Practices Used
- Gateway Trust Pattern for JWT
- Database per service
- Circuit Breaker + Retry for inter-service calls
- Event-driven for non-critical operations
- Centralized configuration with Git
- Docker Compose for development
- Kubernetes for production
- CI/CD for automated deployment
- Prometheus + Grafana for monitoring
- Zipkin for distributed tracing

### Future Improvements
- Real payment gateway (Stripe/Razorpay)
- Real email (JavaMail/SendGrid)
- OAuth2 with Keycloak
- API versioning (v1/v2)
- Redis caching
- Database migrations (Flyway)
- Integration tests (Testcontainers)
- WebSocket notifications
- gRPC for inter-service communication
- Saga pattern for distributed transactions
- API documentation (Swagger/OpenAPI)

---

> Built with Java 21, Spring Boot 3.x, and Spring Cloud 2023.x by [Sarthak Pawar](https://github.com/sarthakpawar0912)
