CREATE SEQUENCE IF NOT EXISTS admin_user_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE admin_user
(
    id       BIGINT       NOT NULL,
    username VARCHAR(25)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(15)  NOT NULL,
    CONSTRAINT pk_admin_user PRIMARY KEY (id)
);

ALTER TABLE admin_user
    ADD CONSTRAINT uc_admin_user_username UNIQUE (username);