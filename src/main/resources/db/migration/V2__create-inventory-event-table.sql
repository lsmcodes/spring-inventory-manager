CREATE TABLE inventory_events(
    id BINARY(16),
    event_type VARCHAR(9) NOT NULL,
    product_id BINARY(16) NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    created_at TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);