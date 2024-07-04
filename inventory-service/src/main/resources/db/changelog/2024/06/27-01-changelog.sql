-- liquibase formatted sql

-- changeset Torchez:1719498789950-1
ALTER TABLE inventory DROP COLUMN product_type;
ALTER TABLE inventory DROP COLUMN purchase_price;

-- changeset Torchez:1719498789950-2
ALTER TABLE inventory
    ADD product_type VARCHAR(255) NOT NULL;

-- changeset Torchez:1719498789950-4
ALTER TABLE inventory
    ADD purchase_price DECIMAL(6, 2) NOT NULL;

