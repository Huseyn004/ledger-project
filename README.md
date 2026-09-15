# Ledger Application

A core banking ledger system written in Java 21, supporting JDBC storage, password hashing, and console management.

## Setup Instructions

1. Start database: `docker-compose up -d`
2. Build application: `mvn clean package`
3. Run main: `java -jar target/ledger-app-1.0-SNAPSHOT.jar`