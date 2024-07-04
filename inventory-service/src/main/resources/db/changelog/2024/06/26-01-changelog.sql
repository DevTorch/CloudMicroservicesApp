-- liquibase formatted sql

-- changeset Torchez:1719415015003-1
CREATE SEQUENCE IF NOT EXISTS products_id_generator START WITH 1001 INCREMENT BY 1;

-- changeset Torchez:1719415015003-2
CREATE TABLE inventory
(
    id                BIGINT        NOT NULL,
    article_number    VARCHAR(255)  NOT NULL,
    warehouse         VARCHAR(255),
    product_title     VARCHAR(255),
    quantity          INTEGER,
    last_stock_update TIMESTAMP WITHOUT TIME ZONE,
    purchase_price    DECIMAL(6, 2) NOT NULL,
    CONSTRAINT pk_inventory PRIMARY KEY (id)
);

-- changeset Torchez:1719415015003-3
CREATE TABLE inventory_books
(
    id        BIGINT NOT NULL,
    author    VARCHAR(255),
    publisher VARCHAR(255),
    isbn      VARCHAR(255),
    CONSTRAINT pk_inventory_books PRIMARY KEY (id)
);

-- changeset Torchez:1719415015003-4
CREATE TABLE inventory_clothes
(
    id    BIGINT NOT NULL,
    type  VARCHAR(255),
    model VARCHAR(255),
    size  VARCHAR(255),
    CONSTRAINT pk_inventory_clothes PRIMARY KEY (id)
);

-- changeset Torchez:1719415015003-5
CREATE TABLE inventory_electronics
(
    id              BIGINT NOT NULL,
    type            VARCHAR(255),
    model           VARCHAR(255),
    characteristics VARCHAR(255),
    description     VARCHAR(255),
    CONSTRAINT pk_inventory_electronics PRIMARY KEY (id)
);

-- changeset Torchez:1719415015003-6
ALTER TABLE inventory
    ADD CONSTRAINT uc_inventory_article_number UNIQUE (article_number);

-- changeset Torchez:1719415015003-7
ALTER TABLE inventory_books
    ADD CONSTRAINT uc_inventory_books_isbn UNIQUE (isbn);

-- changeset Torchez:1719415015003-8
ALTER TABLE inventory_books
    ADD CONSTRAINT FK_INVENTORY_BOOKS_ON_ID FOREIGN KEY (id) REFERENCES inventory (id);

-- changeset Torchez:1719415015003-9
ALTER TABLE inventory_clothes
    ADD CONSTRAINT FK_INVENTORY_CLOTHES_ON_ID FOREIGN KEY (id) REFERENCES inventory (id);

-- changeset Torchez:1719415015003-10
ALTER TABLE inventory_electronics
    ADD CONSTRAINT FK_INVENTORY_ELECTRONICS_ON_ID FOREIGN KEY (id) REFERENCES inventory (id);

