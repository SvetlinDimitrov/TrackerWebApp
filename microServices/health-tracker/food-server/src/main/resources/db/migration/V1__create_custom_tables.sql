CREATE TABLE custom_foods
(
    id              BINARY(16)   NOT NULL PRIMARY KEY,
    name            VARCHAR(500) NOT NULL,
    user_id         BINARY(16)   NOT NULL,
    calorie_amount  DOUBLE       NOT NULL,
    calorie_unit    VARCHAR(255) NOT NULL,
    food_info       VARCHAR(255),
    food_large_info TEXT,
    food_picture    VARCHAR(5000)
);
