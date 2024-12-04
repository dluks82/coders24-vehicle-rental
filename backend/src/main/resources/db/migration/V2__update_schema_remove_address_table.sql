DROP TABLE rentals;
DROP TABLE customers;
DROP TABLE agencies;
DROP TABLE addresses;
DROP TABLE vehicles;

CREATE TABLE vehicles
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    plate      VARCHAR(10)    NOT NULL UNIQUE,
    name       VARCHAR(100)   NOT NULL,
    type       VARCHAR(20)    NOT NULL,
    status     VARCHAR(20)    NOT NULL,
    daily_rate DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP      NOT NULL,
    updated_at TIMESTAMP      NOT NULL
);

CREATE TABLE agencies
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(100) NOT NULL,
    document     VARCHAR(20)  NOT NULL UNIQUE,
    phone        VARCHAR(20),
    email        VARCHAR(100),
    street       VARCHAR(200) NOT NULL,
    number       VARCHAR(20)  NOT NULL,
    complement   VARCHAR(100),
    neighborhood VARCHAR(100) NOT NULL,
    city         VARCHAR(100) NOT NULL,
    state        VARCHAR(2)   NOT NULL,
    zip_code     VARCHAR(10)  NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    updated_at   TIMESTAMP    NOT NULL
);

CREATE TABLE customers
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(100) NOT NULL,
    document     VARCHAR(20)  NOT NULL UNIQUE,
    type         VARCHAR(20)  NOT NULL,
    phone        VARCHAR(20),
    email        VARCHAR(100),
    street       VARCHAR(200) NOT NULL,
    number       VARCHAR(20)  NOT NULL,
    complement   VARCHAR(100),
    neighborhood VARCHAR(100) NOT NULL,
    city         VARCHAR(100) NOT NULL,
    state        VARCHAR(2)   NOT NULL,
    zip_code     VARCHAR(10)  NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    updated_at   TIMESTAMP    NOT NULL
);

CREATE TABLE rentals
(
    id                   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vehicle_id           UUID           NOT NULL REFERENCES vehicles (id),
    customer_id          UUID           NOT NULL REFERENCES customers (id),
    pickup_agency_id     UUID           NOT NULL REFERENCES agencies (id),
    return_agency_id     UUID           NOT NULL REFERENCES agencies (id),
    start_date           TIMESTAMP      NOT NULL,
    expected_return_date TIMESTAMP      NOT NULL,
    actual_return_date   TIMESTAMP,
    status               VARCHAR(20)    NOT NULL,
    daily_rate           DECIMAL(10, 2) NOT NULL,
    total_amount         DECIMAL(10, 2),
    discount             DECIMAL(10, 2),
    final_amount         DECIMAL(10, 2),
    created_at           TIMESTAMP      NOT NULL,
    updated_at           TIMESTAMP      NOT NULL
);

CREATE INDEX idx_vehicle_plate ON vehicles (plate);
CREATE INDEX idx_vehicle_status ON vehicles (status);
CREATE INDEX idx_agency_document ON agencies (document);
CREATE INDEX idx_customer_document ON customers (document);
CREATE INDEX idx_rental_status ON rentals (status);
CREATE INDEX idx_rental_dates ON rentals (start_date, expected_return_date, actual_return_date);