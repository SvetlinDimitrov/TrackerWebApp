CREATE TABLE users
(
    id            BINARY(16)   NOT NULL PRIMARY KEY,
    username      VARCHAR(255) NOT NULL ,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    kilograms     DOUBLE,
    height        DOUBLE,
    age           INT,
    workout_state VARCHAR(255),
    gender        VARCHAR(255),
    role          VARCHAR(255) NOT NULL
);
