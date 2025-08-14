CREATE DATABASE canteenyy;
USE canteenyy;

CREATE TABLE menu (
    id INT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(100),
    price DOUBLE
);

INSERT INTO menu (item_name, price) VALUES
('Idli', 20.0),
('Dosa', 40.0),
('Vada', 15.0),
('Tea', 10.0),
('Coffee', 15.0);
