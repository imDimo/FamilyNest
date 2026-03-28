# FamilyNest

Semester project for COMP SCI 372 Software Engineering at the University of Wisconsin - Green Bay

FamilyNest is a locally-hosted group calendar server with a web interface

## Prerequisites

- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)

- [Maven](https://maven.apache.org/download.cgi)

- [Docker Compose](https://docs.docker.com/compose/install/)

## Configuration

General application settings are stored in `/src/main/resources/application.properties`

Changes to database settings should also be reflected in `/compose.yml`

## Building and Running

Launch the calendar server by running `mvn spring-boot:run` in the project's root directory

The required databases will automatically launch with the server

Connect to the server through a web browser using the host's address and port (specified in application.properties, default: 8080); example: `http://localhost:8080/`
