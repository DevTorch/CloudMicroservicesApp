-- liquibase formatted sql

-- changeset Torchez:1721059559626-1
CREATE SEQUENCE IF NOT EXISTS order_processed_events_seq START WITH 1 INCREMENT BY 50;

-- changeset Torchez:1721059559626-2
CREATE TABLE order_processed_events
(
    id                  BIGINT       NOT NULL,
    message_id          VARCHAR(255) NOT NULL,
    account_id          BIGINT       NOT NULL,
    event_body          VARCHAR(255),
    event_type          VARCHAR(255),
    received_time_stamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_order_processed_events PRIMARY KEY (id)
);

-- changeset Torchez:1721059559626-3
ALTER TABLE order_processed_events
    ADD CONSTRAINT uc_order_processed_events_accountid UNIQUE (account_id);

-- changeset Torchez:1721059559626-4
ALTER TABLE order_processed_events
    ADD CONSTRAINT uc_order_processed_events_messageid UNIQUE (message_id);

-- changeset Torchez:1721059559626-6
DROP TABLE order_received_events CASCADE;

-- changeset Torchez:1721059559626-7
DROP TABLE orders_prev_version CASCADE;

