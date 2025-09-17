# Investor Portfolio Management (Spring Boot)

A mini project for managing investors, portfolios, and transactions.  
Built with Spring Boot, JPA, MySQL.

## Features
- Investor CRUD
- Portfolio CRUD
- Transactions (add/list by portfolio)

## Tech Stack
- Spring Boot 3
- Spring Data JPA
- MySQL
- Lombok

## How to Run
1. Clone repo
2. Create MySQL DB `investor_db`
3. Update `application.properties`
4. Run with `mvn spring-boot:run`

## API Endpoints
- POST /investors
- GET /investors
- POST /investors/{id}/portfolios
- GET /portfolios/{id}/transactions

## Postman Collection
See: [Postman Collection](docs/SpringBoot_Investor_API.postman_collection.json)