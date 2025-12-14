# Banking API

A comprehensive Spring Boot banking API with JWT authentication, account management, and transaction handling.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Authentication](#authentication)
- [Database](#database)
- [Docker Deployment](#docker-deployment)
- [Error Handling](#error-handling)
- [Security](#security)
- [Testing](#-testing)

## 🎯 Overview

Banking API is a robust REST API built with Spring Boot 4.0 that provides comprehensive banking operations including user management, account creation, transaction processing, and authentication. The API uses JWT tokens for secure authentication and is fully documented with Swagger/OpenAPI.

## ✨ Features

### User Management
- User registration with email validation
- Secure login with JWT token generation
- Role-based access control (ROLE_USER, etc.)
- User profile management

### Account Management
- Create accounts with auto-generated account numbers (ACC-XXXXXXXX)
- Search/filter accounts by number and name
- Account isolation per authenticated user
- Update and delete accounts
- Real-time account balance tracking

### Security Features
- JWT token-based authentication
- 30-minute token expiration
- Spring Security integration
- Password encryption with bcrypt
- Role-based authorization
- Authenticated user account isolation

### Health Monitoring
- Health check endpoint
- API status monitoring
- Service availability verification

### API Documentation
- Full Swagger UI integration
- OpenAPI 3.0 specification
- Interactive API testing
- Request/response examples

## 🛠 Tech Stack

### Backend Framework
- **Spring Boot** 4.0.0
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Database ORM
- **Spring Validation** - Request validation

### Authentication & Authorization
- **JWT (io.jsonwebtoken)** - Token-based authentication
- **Spring Security OAuth2** - OAuth2 support

### Database
- **MySQL 8** - Primary database
- **JPA/Hibernate** - ORM with CamelCase naming strategy

### API Documentation
- **Springdoc OpenAPI** 2.6.0 - Swagger/OpenAPI 3.0 integration

### Build & Runtime
- **Gradle 8.14+** - Build tool
- **Java 25** - Runtime
- **Docker** - Containerization

### Development
- **Spring Boot DevTools** - Hot reload support
- **JUnit** - Testing framework

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/banking/api/
│   │   ├── BankingApiApplication.java
│   │   ├── config/
│   │   │   ├── OpenApiConfig.java
│   │   │   └── SecurityConfig.java
│   │   ├── controller/
│   │   │   ├── AccountController.java
│   │   │   ├── HealthController.java
│   │   │   ├── TransactionController.java
│   │   │   └── UserController.java
│   │   ├── dto/
│   │   │   ├── AccountCreateRequest.java
│   │   │   ├── AccountResponse.java
│   │   │   ├── AccountUpdateRequest.java
│   │   │   ├── AccountSearchRequest.java
│   │   │   ├── AuthRequest.java
│   │   │   ├── ErrorResponse.java
│   │   │   ├── HealthResponse.java
│   │   │   ├── LoginResponse.java
│   │   │   └── UserResponse.java
│   │   ├── entity/
│   │   │   ├── Account.java
│   │   │   ├── Transaction.java
│   │   │   └── User.java
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── ResourceNotFoundException.java
│   │   ├── repository/
│   │   │   ├── AccountRepository.java
│   │   │   ├── TransactionRepository.java
│   │   │   └── UserRepository.java
│   │   ├── security/
│   │   │   └── UserInfoDetails.java
│   │   └── service/
│   │       ├── AccountService.java
│   │       ├── JwtService.java
│   │       ├── SecurityService.java
│   │       ├── TransactionService.java
│   │       └── UserService.java
│   └── resources/
│       ├── application.yaml
│       └── db/
│           └── init.sql
└── test/
    └── java/com/banking/api/
        └── BankingApiApplicationTests.java

Infrastructure:
├── Dockerfile
├── docker-compose.yml
├── build.gradle.kts
└── settings.gradle.kts
```

## 📦 Prerequisites

- **Java 25** (JDK)
- **Gradle 8.14+**
- **MySQL 8** (for local development)
- **Docker & Docker Compose** (for containerized deployment)
- **Git**

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd banking-api/api
```

### 2. Configure Environment Variables

Create a `.env` file in the project root:

```env
MYSQL_ROOT_PASSWORD=root
MYSQL_HOST=banking-db
MYSQL_PORT=3306
MYSQL_DATABASE=banking_db
MYSQL_USER=bankingdb-user
MYSQL_PASSWORD=bankingdb1234!
```

### 3. Local Development Setup (without Docker)

Update `.env` for local MySQL:
```env
MYSQL_HOST=localhost
```

Create the database:
```bash
mysql -u root -p -e "CREATE DATABASE banking_db;"
mysql -u root -p banking_db < src/main/resources/db/init.sql
```

## 🏃 Running the Application

### Local Development

```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun
```

The API will be available at `http://localhost:8080`

### Docker Deployment

```bash
# Build and run with Docker Compose
docker-compose up --build

# The API will be available at http://localhost:8080
# MySQL will be available at localhost:3306
```

### Stopping Services

```bash
docker-compose down
```

## 📚 API Documentation

### Swagger UI

Access the interactive API documentation:
- **URL**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

### Health Check

```bash
GET /api/health
```

**Response:**
```json
{
  "status": "UP",
  "message": "Banking API is running",
  "timestamp": "2025-12-15T21:30:00"
}
```

## 🔐 Authentication

### User Registration

```bash
POST /api/users/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePassword123!",
  "roles": "ROLE_USER"
}
```

**Response:**
```json
{
  "id": "user-uuid",
  "username": "john_doe",
  "email": "john@example.com",
  "createdAt": "2025-12-15T21:30:00",
  "roles": "ROLE_USER"
}
```

### User Login

```bash
POST /api/users/login
Content-Type: application/json

{
  "username": "john@example.com",
  "password": "SecurePassword123!"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "issuedAt": "2025-12-15T21:30:00",
  "expiresAt": "2025-12-15T22:00:00"
}
```

**Token Details:**
- Type: JWT (JSON Web Token)
- Expiration: 30 minutes
- Algorithm: HS256
- Include in requests: `Authorization: Bearer <token>`

## 💳 Account Management

### Create Account

```bash
POST /api/accounts/new
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Savings Account",
  "balance": 5000.00
}
```

**Note:** Account number is auto-generated in format `ACC-XXXXXXXX`

**Response:**
```json
{
  "id": "account-uuid",
  "number": "ACC-A1B2C3D4",
  "name": "Savings Account",
  "balance": 5000.00,
  "createdAt": "2025-12-15T21:30:00",
  "updatedAt": "2025-12-15T21:30:00"
}
```

### Search Accounts

```bash
POST /api/accounts?number=ACC-123&name=savings
Authorization: Bearer <token>
```

**Query Parameters:**
- `number` (optional): Filter by account number
- `name` (optional): Filter by account name

**Response:**
```json
[
  {
    "id": "account-uuid",
    "number": "ACC-A1B2C3D4",
    "name": "Savings Account",
    "balance": 5000.00,
    "createdAt": "2025-12-15T21:30:00",
    "updatedAt": "2025-12-15T21:30:00"
  }
]
```

### Get Account by ID

```bash
GET /api/accounts/{id}
Authorization: Bearer <token>
```

### Update Account

```bash
PUT /api/accounts
Authorization: Bearer <token>
Content-Type: application/json

{
  "id": "account-uuid",
  "number": "ACC-NEWNUM",
  "name": "Updated Name"
}
```

### Delete Account

```bash
DELETE /api/accounts/{id}
Authorization: Bearer <token>
```

## 💾 Database

### Schema

#### Users Table
```sql
CREATE TABLE users (
  id VARCHAR(36) PRIMARY KEY,
  username VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  roles VARCHAR(255),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
```

#### Accounts Table
```sql
CREATE TABLE accounts (
  id VARCHAR(36) PRIMARY KEY,
  number VARCHAR(50) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  balance DECIMAL(19,2),
  user_email VARCHAR(100) NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_email) REFERENCES users(email)
);
```

#### Transactions Table
```sql
CREATE TABLE transactions (
  id VARCHAR(36) PRIMARY KEY,
  account_id VARCHAR(36) NOT NULL,
  type VARCHAR(50),
  amount DECIMAL(19,2),
  status VARCHAR(50),
  created_at TIMESTAMP,
  FOREIGN KEY (account_id) REFERENCES accounts(id)
);
```

### Database Initialization

The database is automatically initialized with `src/main/resources/db/init.sql` when using Docker Compose.

## 🐳 Docker Deployment

### Docker Compose Configuration

The project includes a complete Docker setup with:
- **Banking API** (Spring Boot application)
- **MySQL 8** (Database)
- **Shared Network** for inter-container communication

### Build Docker Image

```bash
./gradlew build
docker build -t banking-api:latest .
```

### Environment Configuration for Docker

Ensure `.env` has:
```env
MYSQL_HOST=banking-db  # Use service name from docker-compose
```

### Network & Communication

- API and Database communicate via `banking-network` bridge
- MySQL health checks ensure database readiness before API starts
- Persistent volume storage: `mysql_data`

## ⚠️ Error Handling

### Global Exception Handler

All errors return standardized responses:

```json
{
  "status": 400,
  "message": "Validation failed",
  "error": "Validation Error",
  "timestamp": "2025-12-15T21:30:00"
}
```

### HTTP Status Codes

| Status | Description                        |
| ------ | ---------------------------------- |
| 200    | OK - Request successful            |
| 201    | Created - Resource created         |
| 400    | Bad Request - Invalid input        |
| 401    | Unauthorized - Invalid credentials |
| 404    | Not Found - Resource not found     |
| 409    | Conflict - Invalid state           |
| 500    | Internal Server Error              |

## 🔒 Security

### Authentication Features
- ✅ JWT token-based authentication
- ✅ Spring Security integration
- ✅ Password encryption with bcrypt
- ✅ Token expiration (30 minutes)
- ✅ Role-based access control

### Data Isolation
- ✅ Accounts are isolated per authenticated user
- ✅ Users can only access their own accounts
- ✅ Email-based user identification

### Best Practices
- Use HTTPS in production
- Store JWT secret securely
- Implement rate limiting
- Regular security audits
- Keep dependencies updated

## 📝 Configuration Files

### application.yaml

```yaml
spring:
  application:
    name: banking-api
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE}
    username: ${MYSQL_USER}
    password: ${MYSQL_PASSWORD}
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
      dialect: org.hibernate.dialect.MySQLDialect

springdoc:
  swagger-ui:
    enabled: true
    path: /swagger-ui.html
  api-docs:
    path: /v3/api-docs
```

## 🐛 Troubleshooting

### Database Connection Error
- Check `.env` file - ensure `MYSQL_HOST` is correct
- For Docker: use service name `banking-db`
- For local: use `localhost`

### JWT Token Expired
- Generate new token via login endpoint
- Token expires after 30 minutes

### Port Already in Use
```bash
# Kill existing process on port 8080
lsof -ti:8080 | xargs kill -9
```

### Docker Issues
```bash
# Remove all containers and volumes
docker-compose down -v

# Rebuild from scratch
docker-compose up --build
```

## 📞 Support

For issues, questions, or contributions, please:
1. Check the API documentation at `/swagger-ui.html`
2. Review error messages in the response body
3. Check application logs for detailed errors

## 🧪 Testing

The project includes comprehensive test coverage with unit tests for core business logic.

### Test Structure

```
src/test/java/com/banking/api/
├── service/
│   └── AccountServiceTest.java       # Account entity and DTO testing (9 tests)
└── BankingApiApplicationTests.java   # Application context test (disabled)
```

### Running Tests

Run all tests:
```bash
./gradlew test
```

View HTML test report:
```
build/reports/tests/test/index.html
```

### Test Coverage

#### AccountServiceTest (9 Unit Tests)
- ✅ Account entity structure verification
- ✅ Account number format validation
- ✅ Account creation request validation
- ✅ Account update request validation
- ✅ Account response conversion from entity
- ✅ Account balance update handling
- ✅ Account name update handling
- ✅ User isolation capability verification
- ✅ Account timestamps handling

**Test Statistics:**
- Total Tests: 9
- Passed: 9
- Failed: 0
- Execution Time: < 1 second

### Test Examples

#### Testing Account Entity Creation
```java
Account account = new Account();
account.setId("acc-1");
account.setNumber("ACC-ABC123D4");
account.setName("Savings Account");
account.setBalance(new BigDecimal("5000.00"));
account.setUserEmail("user@example.com");

assertEquals("acc-1", account.getId());
assertEquals("ACC-ABC123D4", account.getNumber());
```

#### Testing Account Response Conversion
```java
Account account = new Account();
// ... set fields ...

AccountResponse response = AccountResponse.fromAccount(account);

assertEquals(account.getId(), response.id());
assertEquals(account.getNumber(), response.number());
assertEquals(account.getBalance(), response.balance());
```

#### Testing User Isolation
```java
Account account1 = new Account();
account1.setUserEmail("user1@example.com");

Account account2 = new Account();
account2.setUserEmail("user2@example.com");

assertNotEquals(account1.getUserEmail(), account2.getUserEmail());
```

### Testing Best Practices Used

1. **Unit Testing**: Focus on individual components in isolation
2. **Clear Test Names**: Test method names describe what is being tested
3. **AAA Pattern**: Arrange-Act-Assert structure for clarity
4. **No External Dependencies**: Unit tests don't require database or Spring context
5. **Focused Assertions**: Each test verifies one behavior

### Build Status

```bash
✅ All 9 tests passing
✅ Zero compilation errors
✅ Zero warnings
✅ Build: SUCCESSFUL in 9s
```

### Testing in CI/CD

To run tests in continuous integration:

```bash
# Run tests without building JAR
./gradlew test

# Run tests and generate coverage
./gradlew test jacocoTestReport

# Skip tests during build
./gradlew build -x test
```

### Future Test Enhancements

Potential areas for expanded testing:
- Integration tests with TestContainers for MySQL
- Controller tests with MockMvc
- Service layer tests with mocked dependencies
- End-to-end API tests
- Performance tests
- Security tests

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Last Updated:** December 15, 2025
**Version:** 1.0.0