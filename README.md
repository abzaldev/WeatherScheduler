# Weather Scheduler Application backend + frontend
----


## Setup

### Datasource


Install and run a Postgresql with Docker compose

```shell script
docker compose up db
```

Run flyway postgresql migrate to create tables:

```shell script
mvn flyway:migrate 
```


### Running Back End App

To run the application, run the following command in a terminal window (in the complete) directory:

```shell
mvn spring-boot:run
```

###  API endpoints listed by Swagger UI

URL: [swagger ui](http://localhost:8080/swagger-ui/).

### Running Front End App


Initial run yarn

```shell
yarn
```

Run the react

```shell
yarn start
```



