CREATE TABLE records
(
    id      BINARY(16) NOT NULL PRIMARY KEY,
    user_id BINARY(16) NOT NULL,
    daily_calories DOUBLE NOT NULL,
    date    DATE   NOT NULL
);

CREATE TABLE user_details
(
    record_id     BINARY(16)   NOT NULL PRIMARY KEY,
    kilograms     DOUBLE       NOT NULL,
    height        DOUBLE       NOT NULL,
    age           INT          NOT NULL,
    workout_state VARCHAR(255) NOT NULL,
    gender        VARCHAR(255) NOT NULL,
    FOREIGN KEY (record_id) REFERENCES records (id) ON DELETE CASCADE
);