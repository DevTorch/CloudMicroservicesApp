-- liquibase formatted sql

-- changeset Torchez:1721023162516-1
CREATE SEQUENCE IF NOT EXISTS orders_id_sequence START WITH 101 INCREMENT BY 1;

-- changeset Torchez:1721023162516-2
CREATE TABLE account_order
(
    id                 BIGINT NOT NULL,
    account_id         BIGINT NOT NULL,
    created_time_stamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_account_order PRIMARY KEY (id)
);

-- changeset Torchez:1721023162516-3
CREATE TABLE account_order_order_completes
(
    account_order_id             BIGINT       NOT NULL,
    order_completes_order_number VARCHAR(255) NOT NULL,
    CONSTRAINT pk_account_order_ordercompletes PRIMARY KEY (account_order_id, order_completes_order_number)
);

-- changeset Torchez:1721023162516-4
CREATE TABLE complete_orders
(
    order_number VARCHAR(255) NOT NULL,
    total_cost   DECIMAL,
    order_date   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_complete_orders PRIMARY KEY (order_number)
);

-- changeset Torchez:1721023162516-5
CREATE TABLE order_items
(
    article_no VARCHAR(255) NOT NULL,
    title      VARCHAR(255) NOT NULL,
    price      DECIMAL      NOT NULL,
    quantity   INTEGER      NOT NULL,
    product_id VARCHAR(255),
    CONSTRAINT pk_order_items PRIMARY KEY (article_no)
);

-- changeset Torchez:1721023162516-6
CREATE TABLE order_received_events
(
    message_id          VARCHAR(255) NOT NULL,
    account_id          BIGINT       NOT NULL,
    event_body          VARCHAR(255),
    event_type          VARCHAR(255),
    received_time_stamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_order_received_events PRIMARY KEY (message_id)
);

-- changeset Torchez:1721023162516-7
ALTER TABLE account_order_order_completes
    ADD CONSTRAINT uc_account_order_order_completes_ordercompletes_ordernumber UNIQUE (order_completes_order_number);

-- changeset Torchez:1721023162516-8
ALTER TABLE order_received_events
    ADD CONSTRAINT uc_order_received_events_accountid UNIQUE (account_id);

-- changeset Torchez:1721023162516-9
ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES complete_orders (order_number);

-- changeset Torchez:1721023162516-10
ALTER TABLE account_order_order_completes
    ADD CONSTRAINT fk_accordordcom_on_account_order FOREIGN KEY (account_order_id) REFERENCES account_order (id);

-- changeset Torchez:1721023162516-11
ALTER TABLE account_order_order_completes
    ADD CONSTRAINT fk_accordordcom_on_order_complete FOREIGN KEY (order_completes_order_number) REFERENCES complete_orders (order_number);

-- changeset Torchez:1721023162516-12
-- DROP TABLE orders_prev_version CASCADE;

