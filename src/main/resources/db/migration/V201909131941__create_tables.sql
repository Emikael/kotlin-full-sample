CREATE TABLE address
(
    id           bigserial   NOT NULL,
    cep          varchar(50) NOT NULL,
    street       varchar(50) NULL,
    complement   varchar(50) NULL,
    neighborhood varchar(50) NULL,
    city         varchar(50) NULL,
    state        varchar(50) NULL,
    ibge         int8        NULL,
    created_at   timestamp   NOT NULL,
    updated_at   timestamp   NULL,
    CONSTRAINT address_cep_unique UNIQUE (cep),
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         bigserial   NOT NULL,
    "name"     varchar(50) NOT NULL,
    email      varchar(50) NOT NULL,
    phone      varchar(15) NULL,
    cep        varchar(50) NULL,
    created_at timestamp   NOT NULL,
    updated_at timestamp   NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT fk_users_cep_cep FOREIGN KEY (cep) REFERENCES address (cep) ON UPDATE RESTRICT ON DELETE RESTRICT;
