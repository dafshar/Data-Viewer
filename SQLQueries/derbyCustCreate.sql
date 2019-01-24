CREATE TABLE customer (
  customer_id int NOT NULL,
  first_name varchar(45) DEFAULT NULL,
  last_name varchar(45) DEFAULT NULL,
  ssn varchar(45) DEFAULT NULL,
  tax_base float DEFAULT NULL,
  tax_amount float DEFAULT NULL,
  calc_amount float DEFAULT NULL
 );