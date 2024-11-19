## Phantom Finance Admin App

Phantom Finance is a RESTful API built with Spring Boot, providing banking functionalities to admin users including customer and account management.

### Features

- Authentication: User registration, login, and logout functionalities with secure token-based authentication.
- Customer Management: Create and retrieve customer information.
- Account Management: Create accounts for customers, view account balances, and transaction history.
- Transactions: Transfer funds between accounts with transactional integrity.
- Exception Handling: Consistent and informative error responses across the API.
- Unit Tests: Unit tests covering critical services.

### Prerequisites

- Docker: Ensure you have Docker installed on your machine https://www.docker.com

### Getting Started

1. Clone the Repository
```
git clone git@github.com:raj-aayush/phantom-finance.git
cd phantom-finance
```
2. Start the Application using the following command:
```
docker compose up --build
```
This command will build the Docker images and start the containers defined in the docker-compose.yml file.

Access the Application
Once the application is running, you can access the App at http://localhost.

### API Endpoints

- Authentication
  - `POST /api/register/` - Register a new user.
  - `POST /api/login/` - Login and receive an authentication token.
  - `POST /api/logout/` - Logout the current user.
- Customers
  - `GET /api/customers/` - Retrieve all customers.
  - `POST /api/customers/` - Create a new customer.
  - `GET /api/customers/{customerId}/` - Get details of a specific customer.
  - `GET /api/customers/{customerId}/accounts/` - Get all accounts for a customer.
  - `POST /api/customers/{customerId}/accounts/` - Create a new account for a customer.
- Accounts
  - `GET /api/accounts/` - Retrieve all accounts.
  - `GET /api/accounts/{accountId}/` - Get the balance of an account.
  - `GET /api/accounts/{accountId}/history/` - Get the transaction history of an account.
- Transactions
  - `POST /api/transactions/` - Transfer funds between accounts.

The application includes unit tests for critical services to ensure robustness and reliability.

To run the tests, use the following command:
```
./gradlew test
```

### Technologies Used

Java,
Spring Boot,
Spring Security,
React,
Typescript,
Hibernate,
PostgreSQL,
Docker,
JUnit,
Mockito,