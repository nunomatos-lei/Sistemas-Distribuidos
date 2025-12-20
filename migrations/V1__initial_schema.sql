CREATE TABLE restaurant(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    city VARCHAR(120) NOT NULL,
    country VARCHAR(120) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(120) NOT NULL
);




CREATE TABLE menu_item (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price decimal NOT NULL,
    currency VARCHAR(7),
    CONSTRAINT fk_restaurant FOREIGN KEY (restaurant_id)
    REFERENCES restaurant(id)
);




CREATE TABLE availability_slot (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    capacity INT NOT NULL,
    seats_available INT NOT NULL,
    CONSTRAINT fk_restaurant FOREIGN KEY (restaurant_id)
        REFERENCES restaurant(id)
);