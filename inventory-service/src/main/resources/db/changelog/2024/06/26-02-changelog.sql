-- liquibase formatted sql

-- changeset Torchez:1719417858370-3
ALTER TABLE inventory RENAME COLUMN purchase_price TO product_type;

-- changeset Torchez:1719417858370-2
ALTER TABLE inventory
    ADD purchase_price DECIMAL(6, 2) NOT NULL;

