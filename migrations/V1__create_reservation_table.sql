CREATE TABLE reservations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    restaurant_id UUID NOT NULL,
    availability_slot_id UUID NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    seats_reserved INT NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'PENDING',
    currency VARCHAR(7) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    scheduled_day DATE NOT NULL,
    scheduled_time TIME NOT NULL
);