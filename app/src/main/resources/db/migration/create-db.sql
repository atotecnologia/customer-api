create database customer_api_db character set UTF8;

use customer_api_db;

CREATE TABLE customer (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(500) NOT NULL,
  cpf VARCHAR(20) NOT NULL,
  gender VARCHAR(15),
  birth_date DATE,
  created_at DATETIME,
  update_at DATETIME,
  uuid VARCHAR(100),

  PRIMARY KEY (id),
  UNIQUE INDEX Index_2(cpf)
)
ENGINE = InnoDB;

CREATE TABLE address (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id INTEGER UNSIGNED NOT NULL,
  street VARCHAR(100),
  state VARCHAR(20),
  city VARCHAR(100),
  neighborhood VARCHAR(50),
  zipCode VARCHAR(20),
  number INT(10),
  additionalInformation VARCHAR(100),
  major boolean,

    PRIMARY KEY (id),
  CONSTRAINT FK_adresses_customer FOREIGN KEY FK_adresses_customer (customer_id)
    REFERENCES customer (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


