# User Management Monorepo

This repository contains multiple Spring Boot microservices.

## Projects

- `authentication` – authentication and authorization service.
- `email` – email delivery service.

## Getting Started

1. **Build services**

   ```bash
   ./gradlew build
   ```

   Running the command from the repository root will build all modules.

2. **Run infrastructure**

   ```bash
   docker-compose up -d
   ```

   Starts PostgreSQL, Redis and Kafka used by the services.

3. **Run a service**
   ```bash
   ./gradlew :authentication:bootRun
   ./gradlew :email:bootRun
   ```

## Structure

This repo uses a simple Gradle multi-module setup. Common configuration lives in `build.gradle` and the included modules are declared in `settings.gradle`.

```
/           – root project
  authentication/ – Authentication microservice
  email/          – Email microservice
  docs/           – Additional documentation
```

Each service also contains a `Dockerfile` so that images can be built individually:

```bash
cd authentication && docker build -t authentication-service .
```

## Versioning

Use Git tags to mark releases of the entire repository or individual services
