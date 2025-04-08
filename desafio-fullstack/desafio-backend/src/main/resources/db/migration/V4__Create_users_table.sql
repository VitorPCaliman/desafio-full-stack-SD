-- V4__create_users_table.sql

-- Criação da tabela "users"
-- Inserção do usuário admin inicial
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO users (name, email, password, role) VALUES (
    'Admin',
    'superadmin@simplesdental.com',
    '$2a$12$iUO6PC/V4Y4RFhx41nifOeBIKPx/uZ1U2W/Xpu9IaVn8kKkdQ3WiG',
    'ADMIN'
);
