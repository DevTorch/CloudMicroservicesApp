-- liquibase formatted sql

-- changeset Torchez:1720357993431-1
CREATE TABLE inventory
(
    article_number    VARCHAR(255)  NOT NULL,
    warehouse         VARCHAR(255),
    product_type      VARCHAR(255)  NOT NULL,
    product_title     VARCHAR(255),
    quantity          INTEGER,
    last_stock_update TIMESTAMP WITHOUT TIME ZONE,
    purchase_price    DECIMAL(6, 2) NOT NULL,
    CONSTRAINT pk_inventory PRIMARY KEY (article_number)
);

-- changeset Torchez:1720357993431-2
CREATE TABLE inventory_books
(
    article_number VARCHAR(255) NOT NULL,
    author         VARCHAR(255),
    publisher      VARCHAR(255),
    isbn           VARCHAR(255),
    CONSTRAINT pk_inventory_books PRIMARY KEY (article_number)
);

-- changeset Torchez:1720357993431-3
CREATE TABLE inventory_clothes
(
    article_number VARCHAR(255) NOT NULL,
    type           VARCHAR(255),
    model          VARCHAR(255),
    size           VARCHAR(255),
    CONSTRAINT pk_inventory_clothes PRIMARY KEY (article_number)
);

-- changeset Torchez:1720357993431-4
CREATE TABLE inventory_electronics
(
    article_number  VARCHAR(255) NOT NULL,
    category        VARCHAR(255),
    model           VARCHAR(255),
    characteristics VARCHAR(255),
    description     VARCHAR(255),
    CONSTRAINT pk_inventory_electronics PRIMARY KEY (article_number)
);

-- changeset Torchez:1720357993431-5
ALTER TABLE inventory_books
    ADD CONSTRAINT uc_inventory_books_isbn UNIQUE (isbn);

-- changeset Torchez:1720357993431-6
ALTER TABLE inventory_books
    ADD CONSTRAINT FK_INVENTORY_BOOKS_ON_ARTICLE_NUMBER FOREIGN KEY (article_number) REFERENCES inventory (article_number);

-- changeset Torchez:1720357993431-7
ALTER TABLE inventory_clothes
    ADD CONSTRAINT FK_INVENTORY_CLOTHES_ON_ARTICLE_NUMBER FOREIGN KEY (article_number) REFERENCES inventory (article_number);

-- changeset Torchez:1720357993431-8
ALTER TABLE inventory_electronics
    ADD CONSTRAINT FK_INVENTORY_ELECTRONICS_ON_ARTICLE_NUMBER FOREIGN KEY (article_number) REFERENCES inventory (article_number);

