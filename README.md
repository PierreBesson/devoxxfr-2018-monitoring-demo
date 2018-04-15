# Steps

- Generate 1 gateway and 2 microservices
- Generate entites on gateway and microservice
    - `catalog/`: `jhipster import-jdl ../jdl/catalog.jdl`
    - `booking/`: `jhipster import-jdl ../jdl/booking.jdl`
    - `gateway/`: `jhipster import-jdl ../jdl/catalog.jdl && jhipster import-jdl ../jdl/booking.jdl`
- Start the all apps locally with:
    - `docker-compose -f src/main/docker/jhipster-registry.yml up -d`
    - `./mvnw` and `yarn start` for the gateway
    - `./mvnw` for microservices