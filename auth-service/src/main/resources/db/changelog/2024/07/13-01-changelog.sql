-- liquibase formatted sql

-- changeset Torchez:1720857649122-1
CREATE SEQUENCE IF NOT EXISTS account_id_seq START WITH 101 INCREMENT BY 1;

-- changeset Torchez:1720857649122-2
CREATE TABLE account
(
    id       BIGINT      NOT NULL,
    email    VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    active   BOOLEAN     NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

-- changeset Torchez:1720857649122-3
CREATE TABLE account_roles
(
    account_id BIGINT NOT NULL,
    role_id    BIGINT NOT NULL,
    CONSTRAINT pk_account_roles PRIMARY KEY (account_id, role_id)
);

-- changeset Torchez:1720857649122-4
CREATE TABLE role
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(20)                             NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

-- changeset Torchez:1720857649122-5
ALTER TABLE account
    ADD CONSTRAINT uc_account_email UNIQUE (email);

-- changeset Torchez:1720857649122-6
ALTER TABLE account_roles
    ADD CONSTRAINT fk_accrol_on_account FOREIGN KEY (account_id) REFERENCES account (id);

-- changeset Torchez:1720857649122-7
ALTER TABLE account_roles
    ADD CONSTRAINT fk_accrol_on_role FOREIGN KEY (role_id) REFERENCES role (id);

