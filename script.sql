DROP DATABASE IF EXISTS iftech;

CREATE DATABASE iftech;
USE iftech;

CREATE TABLE customer (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  cpf VARCHAR(14) NOT NULL UNIQUE,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  phone VARCHAR(30) NOT NULL,
  active BIT NOT NULL,
  
  CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE address (
  id BIGINT NOT NULL,
  street VARCHAR(50) NOT NULL,
  number VARCHAR(10) NOT NULL,
  complement VARCHAR(30) NOT NULL,
  district VARCHAR(40) NOT NULL,
  zip_code VARCHAR(40) NOT NULL,
  city VARCHAR(40) NOT NULL,
  state VARCHAR(40) NOT NULL,

  CONSTRAINT pk_address PRIMARY KEY (id),
  CONSTRAINT fk_address_customer FOREIGN KEY (id) REFERENCES customer(id)
);



CREATE TABLE payment_method (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,

  CONSTRAINT pk_payment_method PRIMARY KEY (id)
);

CREATE TABLE service_order (
  id BIGINT NOT NULL AUTO_INCREMENT,
  customer_id BIGINT NOT NULL,
  description VARCHAR(50) NOT NULL,
  status VARCHAR(20) NOT NULL,
  price DECIMAL(19,2) NOT NULL,
  issue_date DATETIME NOT NULL,
  end_date DATETIME NOT NULL,
  observation VARCHAR(50) NOT NULL,
  payment_method_id BIGINT NULL,

  CONSTRAINT pk_service_order PRIMARY KEY (id),
  CONSTRAINT fk_service_order_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
  CONSTRAINT fk_service_order_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_method(id)
);


/*
    DADOS PARA TESTE
*/


-- Inserir dados na tabela customer
INSERT INTO customer (name, cpf, email, password, phone, active) VALUES
('João da Silva', '12345678900', 'joao@email.com', 'E7D80FFEEFA212B7C5C55700E4F7193E', '(11) 91234-5678', 1); --senha123

-- Inserir dados na tabela address
INSERT INTO address (id, street, number, complement, district, zip_code, city, state) VALUES
(1, 'Rua das Flores', '123', 'Apto 101', 'Centro', '12345-678', 'São Paulo', 'SP');

-- Inserir dados na tabela payment_method
INSERT INTO payment_method (name) VALUES
('Cartão de Crédito'),
('Cartão de Débito'),
('Dinheiro'),
('Transferência Bancária'),
('Pix');

-- Inserir dados na tabela service_order
INSERT INTO service_order (customer_id, description, status, price, issue_date, end_date, observation, payment_method_id) VALUES
(1, 'Serviço de Limpeza', 'APPROVED', 150.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), 'Limpeza geral', 1),
(1, 'Reparo de Eletrodoméstico', 'IN_PROGRESS', 200.00, NOW(), DATE_ADD(NOW(), INTERVAL 4 DAY), 'Aguardando peça', 2),
(1, 'Manutenção de Jardim', 'APPROVED', 100.00, NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY), 'Inclusão de plantas', 3),
(1, 'Pintura de Parede', 'PENDING_APPROVAL', 300.00, NOW(), DATE_ADD(NOW(), INTERVAL 3 DAY), 'Cor azul', 4),
(1, 'Reforma de Cozinha', 'APPROVED', 800.00, NOW() - INTERVAL 1 WEEK, NOW() - INTERVAL 2 DAY, 'Mudança de layout', 5);