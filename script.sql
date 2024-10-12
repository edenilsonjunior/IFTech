DROP DATABASE iftech;

CREATE DATABASE iftech;
USE iftech;

CREATE TABLE customer (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  phone VARCHAR(30) NOT NULL,
  cpf VARCHAR(14) NOT NULL,
  street VARCHAR(50) NOT NULL,
  number VARCHAR(10) NOT NULL,
  complement VARCHAR(30) NOT NULL,
  district VARCHAR(40) NOT NULL,
  zip_code VARCHAR(40) NOT NULL,
  city VARCHAR(40) NOT NULL,
  state VARCHAR(40) NOT NULL,

  CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE service_order (

  id BIGINT NOT NULL AUTO_INCREMENT,
  customer_id BIGINT NOT NULL,
  description VARCHAR(50) NOT NULL,
  status VARCHAR(20) NOT NULL,
  payment_name VARCHAR(50) NOT NULL,
  price DECIMAL(19,2) NOT NULL,
  issue_date DATETIME NOT NULL,
  end_date DATETIME NOT NULL,
  observation VARCHAR(50) NOT NULL,
  
  CONSTRAINT pk_service_order PRIMARY KEY (id),
  CONSTRAINT fk_service_order_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);


