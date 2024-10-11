DROP DATABASE iftech;

CREATE DATABASE iftech;
USE iftech;

CREATE TABLE customer (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  phone varchar(30) NOT NULL,
  cpf varchar(11) NOT NULL
);
