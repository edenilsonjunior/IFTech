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
('João da Silva', '12345678900', 'joao@email.com', 'senha123', '(11) 91234-5678', 1),
('Maria Oliveira', '98765432100', 'maria@email.com', 'senha456', '(11) 98765-4321', 1),
('Carlos Santos', '45678912300', 'carlos@email.com', 'senha789', '(11) 99876-5432', 1),
('Ana Costa', '32165498700', 'ana@email.com', 'senha321', '(11) 94567-1234', 1),
('Fernanda Lima', '65432198700', 'fernanda@email.com', 'senha654', '(11) 93456-7890', 1);

-- Inserir dados na tabela address
INSERT INTO address (id, street, number, complement, district, zip_code, city, state) VALUES
(1, 'Rua das Flores', '123', 'Apto 101', 'Centro', '12345-678', 'São Paulo', 'SP'),
(2, 'Avenida Paulista', '456', 'Sala 202', 'Bela Vista', '87654-321', 'São Paulo', 'SP'),
(3, 'Rua do Comércio', '789', '', 'Jardins', '11223-334', 'São Paulo', 'SP'),
(4, 'Praça da Sé', '101', 'Lado Norte', 'Sé', '99887-665', 'São Paulo', 'SP'),
(5, 'Rua Augusta', '202', 'Casa', 'Consolação', '55443-221', 'São Paulo', 'SP');


-- Inserir dados na tabela payment_method
INSERT INTO payment_method (name) VALUES
('Cartão de Crédito'),
('Cartão de Débito'),
('Dinheiro'),
('Transferência Bancária'),
('Pix');

-- Inserir dados na tabela service_order
INSERT INTO service_order (customer_id, description, status, price, issue_date, end_date, observation, payment_method_id) VALUES
(1, 'Serviço de Limpeza', 'Concluído', 150.00, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), 'Limpeza geral', 1),
(2, 'Reparo de Eletrodoméstico', 'Em Andamento', 200.00, NOW(), DATE_ADD(NOW(), INTERVAL 4 DAY), 'Aguardando peça', 2),
(3, 'Manutenção de Jardim', 'Concluído', 100.00, NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY), 'Inclusão de plantas', 3),
(4, 'Pintura de Parede', 'Agendado', 300.00, NOW(), DATE_ADD(NOW(), INTERVAL 3 DAY), 'Cor azul', 4),
(5, 'Reforma de Cozinha', 'Concluído', 800.00, NOW() - INTERVAL 1 WEEK, NOW() - INTERVAL 2 DAY, 'Mudança de layout', 5);





/*
Customer:
  -login
  -register
  -logout

ServiceOrder:
  -register
  -getByCustomer
*/