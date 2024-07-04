-- liquibase formatted sql

-- changeset Torchez:1717339828088-1
CREATE SEQUENCE IF NOT EXISTS customer_id_seq START WITH 101 INCREMENT BY 20;

-- changeset Torchez:1717339828088-2
CREATE TABLE customers
(
    id                BIGINT NOT NULL,
    dtype             VARCHAR(31),
    account_id        BIGINT NOT NULL,
    nickname          VARCHAR(255),
    full_name         VARCHAR(255),
    registration_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

-- changeset Torchez:1717339828088-3
ALTER TABLE customers
    ADD CONSTRAINT uc_customers_account UNIQUE (account_id);

-- changeset Torchez:1717339828088-4
INSERT INTO customers
(id, dtype, account_id, nickname, full_name, registration_date)
VALUES
(100, 'customer', 100, 'John', 'John Smith', '2024-06-02 00:00:00.000000'),
(101, 'customer', 101, 'Jane', 'Jane Doe', '2024-06-03 00:00:00.000000'),
(102, 'customer', 102, 'John123', 'John Doe', '2024-06-04 00:00:00.000000'),
(103, 'customer', 103, 'Alex', 'Alex Burton', '2024-06-05 00:00:00.000000'),
(104, 'customer', 104, 'DD', 'Donald Dull', '2024-06-06 00:00:00.000000');