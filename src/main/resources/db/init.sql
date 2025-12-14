CREATE DATABASE IF NOT EXISTS banking_db;
FLUSH PRIVILEGES;
CREATE USER IF NOT EXISTS 'bankingdb-user'@'%' IDENTIFIED BY 'bankingdb1234!';
GRANT ALL PRIVILEGES ON banking_db.* TO 'bankingdb-user'@'%';
FLUSH PRIVILEGES;
USE banking_db;

-- User table
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    roles VARCHAR(255)
);

-- Account table
CREATE TABLE IF NOT EXISTS accounts (
    id VARCHAR(255) PRIMARY KEY,
    number VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Transaction table
CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    from_id VARCHAR(255),
    to_id VARCHAR(255),
    amount DECIMAL(19, 2) NOT NULL,
    transaction_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (from_id) REFERENCES accounts(id) ON DELETE SET NULL,
    FOREIGN KEY (to_id) REFERENCES accounts(id) ON DELETE SET NULL
);

-- Indexes for better performance
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_account_number ON accounts(number);
CREATE INDEX idx_transaction_from ON transactions(from_id);
CREATE INDEX idx_transaction_to ON transactions(to_id);
CREATE INDEX idx_transaction_date ON transactions(transaction_date);
CREATE INDEX idx_transaction_status ON transactions(status);
