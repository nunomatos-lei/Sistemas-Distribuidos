CREATE TABLE payments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reservation_id UUID NOT NULL,
    restaurant_id UUID NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    tax_number VARCHAR(20) UNIQUE,
    price REAL NOT NULL,
    currency VARCHAR(7) NOT NULL
);