--h2 is typically used to setup a test database, not a prod database.
--first, drop your tables (to reset your database for testing)
--then create your tables

DROP TABLE PRODUCTS IF EXISTS;
DROP TABLE SELLERS IF EXISTS;
CREATE TABLE SELLERS (
    seller_id int primary key,
    seller_name varchar(255) not null
);
CREATE TABLE PRODUCTS (
    product_id int primary key,
    product_name varchar(255) not null,
    price double,
    sold_by int references SELLER(seller_id)
);
INSERT INTO SELLER (seller_id, seller_name)
VALUES
(1, 'Apple'),
(2, 'Android');
INSERT INTO PRODUCTS (product_id, product_name, price, sold_by)
VALUES (1, 'iphone', 1000, 1);