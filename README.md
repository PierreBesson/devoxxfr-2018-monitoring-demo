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

- Package for prod and build the docker image: `./mvnw package -Pprod dockerfile:build`
- Generate docker-compose files: `cd docker && jhipster docker-compose`
- Start the stack: `docker-compose up -d`