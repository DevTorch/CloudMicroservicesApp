CREATE TABLE IF NOT EXISTS inventory (
    id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    sku_code VARCHAR(255) DEFAULT NULL,
    quantity INTEGER DEFAULT NULL
);