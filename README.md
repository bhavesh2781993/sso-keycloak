# Keycloak Implementation with Dynamic Multi-Tenant Support

## Prerequisites:
* JDK 17+
* Docker

## Getting Started
* Run Keycloak on docker:
  - `docker run -p 9191:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.3 start-dev`
* Verify Installation of keycloak by visiting `localhost:9191` and login with `username: admin, password: admin`
* Create DB and DB User provided as per `application.properties` file
* Start the application, and check DB for auto created table (through flyway).