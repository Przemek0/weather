CREATE SEQUENCE IF NOT EXISTS city_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS country_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS weather_data_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE city
(
    id         BIGINT       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    zip_code   VARCHAR(10)  NOT NULL,
    country_id INTEGER      NOT NULL,
    CONSTRAINT pk_city PRIMARY KEY (id)
);

CREATE TABLE country
(
    id   INTEGER      NOT NULL,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(2)   NOT NULL,
    CONSTRAINT pk_country PRIMARY KEY (id)
);

CREATE TABLE weather_data
(
    id                   BIGINT                 NOT NULL,
    date                 date                   NOT NULL,
    time                 TIME WITHOUT TIME ZONE NOT NULL,
    temperature          FLOAT                  NOT NULL,
    atmospheric_pressure FLOAT                  NOT NULL,
    wind_direction_deg   INTEGER                NOT NULL,
    wind_speed           INTEGER                NOT NULL,
    cloud_cover          VARCHAR(255)           NOT NULL,
    city_id              BIGINT                 NOT NULL,
    CONSTRAINT pk_weather_data PRIMARY KEY (id)
);

ALTER TABLE country
    ADD CONSTRAINT uc_country_code UNIQUE (code);

ALTER TABLE country
    ADD CONSTRAINT uc_country_name UNIQUE (name);

ALTER TABLE city
    ADD CONSTRAINT FK_CITY_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE weather_data
    ADD CONSTRAINT FK_WEATHER_DATA_ON_CITY FOREIGN KEY (city_id) REFERENCES city (id);