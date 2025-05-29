CREATE TABLE meals
(
    id        BINARY(16)   NOT NULL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    record_id BINARY(16),
    CONSTRAINT fk_meal_record FOREIGN KEY (record_id)
        REFERENCES records (id) ON DELETE CASCADE
);