# Banking API

A Spring Boot REST API for banking operations with JWT authentication, account management, and transactions.

## Quick Start

### Prerequisites
- Java 25
- Gradle 8.14+
- MySQL 8 (local) or Docker

### Local Setup
```bash
git clone <repository-url>
cd banking-api/api

# Create .env file
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_DATABASE=banking_db
MYSQL_USER=bankingdb-user
MYSQL_PASSWORD=bankingdb1234!

# Build and run
./gradlew build
./gradlew bootRun
```

### Docker Setup
```bash
docker-compose up --build
```

The API will be available at `http://localhost:8080`

## Features

- **User Management**: Registration and JWT-based login
- **Account Management**: Create, search, update, and delete accounts with auto-generated numbers
- **Transactions**: Transfer money between accounts and view transaction history
- **Security**: JWT authentication with 30-minute expiration, role-based access control
- **API Documentation**: Swagger UI at `/swagger-ui.html`
- **Health Check**: `GET /api/health`

## Tech Stack

- Spring Boot 4.0
- Spring Security with JWT
- MySQL 8 + JPA/Hibernate
- Swagger/OpenAPI 3.0
- Docker & Docker Compose

## API Endpoints

### Authentication
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - Login and get JWT token

### Accounts
- `POST /api/accounts` - Create account
- `GET /api/accounts` - Search accounts (filters: number, name)
- `GET /api/accounts/{id}` - Get account by ID
- `PUT /api/accounts/{id}` - Update account
- `DELETE /api/accounts/{id}` - Delete account

### Transactions
- `POST /api/transactions/transfer` - Transfer money between accounts
- `GET /api/transactions/account/{accountId}` - View transaction history

### Health
- `GET /api/health` - API health status

## Example Requests

### Register User
```bash
POST /api/users/register
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123!",
  "roles": "ROLE_USER"
}
```

### Login
```bash
POST /api/users/login
{
  "username": "john@example.com",
  "password": "SecurePass123!"
}
```

### Create Account
```bash
POST /api/accounts
Authorization: Bearer <token>
{
  "name": "Savings Account",
  "balance": 5000.00
}
```

### Transfer Money
```bash
POST /api/transactions/transfer
Authorization: Bearer <token>
{
  "fromAccountId": "account-1-id",
  "toAccountId": "account-2-id",
  "amount": 100.00
}
```

### Get Transaction History
```bash
GET /api/transactions/account/{accountId}
Authorization: Bearer <token>
```

## Error Handling

All errors return standardized JSON responses:
```json
{
  "status": 400,
  "message": "Error description",
  "error": "Error Type",
  "timestamp": "2025-12-15T21:30:00"
}
```

## Security

- JWT token-based authentication
- Password encryption with bcrypt
- Account isolation per user
- Role-based access control
- 30-minute token expiration

## Project Structure

```
src/main/java/com/banking/api/
├── controller/     # REST endpoints
├── service/        # Business logic
├── repository/     # Data access
├── entity/         # Database models
├── dto/            # Request/Response objects
├── exception/      # Exception handling
└── config/         # Security & API configuration
```

## Running Tests

```bash
./gradlew test
```

View test report: `build/reports/tests/test/index.html`

## Configuration

Environment variables in `.env` or Docker:
- `MYSQL_HOST` - Database host
- `MYSQL_PORT` - Database port
- `MYSQL_DATABASE` - Database name
- `MYSQL_USER` - Database user
- `MYSQL_PASSWORD` - Database password

## Troubleshooting

| Issue                     | Solution                                                                |
| ------------------------- | ----------------------------------------------------------------------- |
| Database connection error | Check `.env` file, use `localhost` for local or `banking-db` for Docker |
| Token expired             | Login again to get new token                                            |
| Port 8080 in use          | Kill process: `lsof -ti:8080 \| xargs kill -9`                          |
| Docker issues             | Run `docker-compose down -v && docker-compose up --build`               |

---

**Version:** 1.0.0 | **Last Updated:** December 15, 2025