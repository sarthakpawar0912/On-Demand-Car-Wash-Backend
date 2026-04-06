# Central Config Repo Updates Required

Add these properties to your GitHub central config repo:
https://github.com/sarthakpawar0912/Car-Wash-central-config-repo-

## 1. ADMIN-SERVICE.properties — ADD these lines at the end:

```
# ===============================
# RESILIENCE4J CIRCUIT BREAKER
# ===============================
resilience4j.circuitbreaker.instances.userService.slidingWindowSize=5
resilience4j.circuitbreaker.instances.userService.failureRateThreshold=50
resilience4j.circuitbreaker.instances.userService.waitDurationInOpenState=10000

resilience4j.retry.instances.userService.maxAttempts=3
resilience4j.retry.instances.userService.waitDuration=2000

resilience4j.timelimiter.instances.userService.timeoutDuration=3s
```

## 2. INVOICE-SERVICE.properties — ADD these lines at the end:

```
# ===============================
# RESILIENCE4J CIRCUIT BREAKER
# ===============================
resilience4j.circuitbreaker.instances.userService.slidingWindowSize=5
resilience4j.circuitbreaker.instances.userService.failureRateThreshold=50
resilience4j.circuitbreaker.instances.userService.waitDurationInOpenState=10000

resilience4j.retry.instances.userService.maxAttempts=3
resilience4j.retry.instances.userService.waitDuration=2000

resilience4j.timelimiter.instances.userService.timeoutDuration=3s
```

## 3. ALL .properties files — ADD this line (for Prometheus metrics endpoint):

```
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true
```

(Note: Since you already have management.endpoints.web.exposure.include=* in most configs, the prometheus endpoint will be auto-exposed. The above is for explicit clarity.)
