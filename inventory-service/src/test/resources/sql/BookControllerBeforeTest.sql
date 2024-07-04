INSERT INTO inventory (id, article_number, warehouse, product_title, quantity, last_stock_update, product_type, purchase_price) VALUES
(1001, '65aa804d-b327-44a8-83bc-0862b5865278', 'WAREHOUSE_SEATTLE', 'Lord of the Rings', 10, '2020-01-01 00:00:00', 'BOOKS', 100.00),
(1002, '65aa804d-b327-44a8-83bc-0862b5865279', 'WAREHOUSE_SEATTLE', 'Hobbit', 10, '2020-01-01 00:00:00', 'BOOKS', 100.00);


INSERT INTO inventory_books (id, author, publisher, isbn) VALUES
(1001, 'J.R.R. Tolkien', 'Houghton Mifflin Harcourt', '978-1-56619-909-9'),
(1002, 'J.R.R. Tolkien', 'Houghton Mifflin Harcourt', '978-2-56619-909-9');