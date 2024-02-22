--h2 is typically used to setup a test database, not a prod database.
--first, drop your tables (to reset your database for testing)
--then create your tables

DROP TABLE PRODUCTS IF EXISTS;
DROP TABLE SELLERS IF EXISTS;
CREATE TABLE SELLERS (
    seller_name varchar(255) primary key
);
CREATE TABLE PRODUCTS (
    product_id int auto_increment primary key,
    product_name varchar(255) unique not null,
    price double,
    sold_by varchar(255) references SELLERS(seller_name)
);
--INSERT INTO SELLERS (seller_name)
--VALUES
--('Apple');

--INSERT INTO PRODUCTS (product_id, product_name, price, sold_by)
--VALUES (999, 'iphone', 1000, 'Apple');