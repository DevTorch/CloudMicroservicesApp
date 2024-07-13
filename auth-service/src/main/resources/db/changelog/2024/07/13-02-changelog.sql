-- liquibase formatted sql

-- changeset Torchez:1720859077229-1
ALTER TABLE account ALTER COLUMN password TYPE VARCHAR(255) USING (password::VARCHAR(255));

