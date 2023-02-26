# Demo for Java Microservices Training

* By [Maxym Mykhalchuk](https://blog.maxym.dp.ua)
* Link to course [Java Microservices: CQRS & Event Sourcing with Kafka](https://ciklum.udemy.com/course/java-microservices-cqrs-event-sourcing-with-kafka/learn/lecture/26302590#overview)
* Built on Spring Boot 3 / Spring Framework 6
* Using no framework for CQRS & Event-Sourcing
* Using MongoDB for Event Store, MySQL or PostgreSQL for Read (Query) Database, and Kafka for communication between Command and Query services

### To run
* Start PostgreSQL on port 5432 with password `sa`, e.g. `docker run --env=POSTGRESQL_PASSWORD=sa -p 5432:5432 -d bitnami/postgresql:latest`
** Then in PostgreSQL console run `CREATE DATABASE "bankAccount";`
* _Or_ start MySQL on port 3306 with password `sa`, e.g.: `docker run --env=MYSQL_ROOT_PASSWORD=sa -p 3306:3306 -p 33060:33060 -d mysql:latest`
** And edit (account-query/src/main/resources/application.yml) by commenting out PostgreSQL configs and uncommenting MySQL configs
* Start MongoDB on port 27017 with no password, e.g. `docker run --env=MONGODB_REPLICA_SET_MODE=primary --env=ALLOW_EMPTY_PASSWORD=yes -p 27017:27017 -d bitnami/mongodb:latest`
* Run Kafka in PLAINTEXT listener mode on port 9092, e.g. see (kafkadoc/) for docker-compose.yml,  change directory to `kafkadoc/` and run `docker-compose up`
* Start all the services `./gradlew bootRun --parallel --max-workers 2`
* Open [Command service](http://localhost:8081/swagger-ui.html), [Query service](http://localhost:8082/swagger-ui.html)
