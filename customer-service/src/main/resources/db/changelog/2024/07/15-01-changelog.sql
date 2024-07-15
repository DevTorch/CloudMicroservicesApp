-- liquibase formatted sql

-- changeset Torchez:1721060013452-1
CREATE SEQUENCE IF NOT EXISTS customer_processed_events_seq START WITH 1 INCREMENT BY 50;

-- changeset Torchez:1721060013452-2
CREATE TABLE customer_processed_events
(
    id                  BIGINT       NOT NULL,
    message_id          VARCHAR(255) NOT NULL,
    account_id          BIGINT,
    event_type          VARCHAR(255),
    event_body          VARCHAR(255),
    received_time_stamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_customer_processed_events PRIMARY KEY (id)
);

-- changeset Torchez:1721060013452-3
ALTER TABLE customer_processed_events
    ADD CONSTRAINT uc_customer_processed_events_messageid UNIQUE (message_id);

