-- liquibase formatted sql

-- changeset Torchez:1720360544414-1
ALTER TABLE inventory DROP COLUMN purchase_price;

-- changeset Torchez:1720360544414-2
ALTER TABLE inventory
    ADD purchase_price DECIMAL(6, 2) NOT NULL;

