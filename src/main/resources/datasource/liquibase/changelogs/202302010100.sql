--liquibase formatted sql

--changeset dmitry.krivenko:202302010100-1
CREATE TABLE IF NOT EXISTS public.customer(
    id          BIGINT          NOT NULL,
    login       VARCHAR         NOT NULL,
    password    VARCHAR         NOT NULL,
    balance     BIGINT          NOT NULL,
    role        VARCHAR         NOT NULL,

    CONSTRAINT customer_id              PRIMARY KEY (id),
    CONSTRAINT customer_unique_fields   UNIQUE      (login)
);

--changeset dmitry.krivenko:202302010100-2
CREATE TABLE IF NOT EXISTS public.parking(
    id              BIGINT          NOT NULL,
    name            VARCHAR         NOT NULL,
    coordinates     VARCHAR         NOT NULL,
    allowed_radius  BIGINT          NOT NULL,
    type            VARCHAR         NOT NULL,
    status          VARCHAR         NOT NULL,

    CONSTRAINT parking_id   PRIMARY KEY (id)
);

--changeset dmitry.krivenko:202302010100-3
CREATE TABLE IF NOT EXISTS public.transport(
    id                      BIGINT      NOT NULL,
    id_parking              BIGINT,
    type                    VARCHAR     NOT NULL,
    identification_number   VARCHAR,
    coordinates             VARCHAR,
    condition               VARCHAR     NOT NULL,
    status                  VARCHAR     NOT NULL,
    charge_percentage       INTEGER,
    max_speed               INTEGER,

    CONSTRAINT transport_id PRIMARY KEY (id),
    FOREIGN KEY (id_parking) REFERENCES parking(id)
);

--changeset dmitry.krivenko:202302010100-4
CREATE TABLE IF NOT EXISTS public.rent(
    id                  BIGINT      NOT NULL,
    id_customer         BIGINT      NOT NULL,
    id_transport        BIGINT      NOT NULL,
    id_begin_parking    BIGINT      NOT NULL,
    id_end_parking      BIGINT,
    begin_time_rent     TIMESTAMP   NOT NULL,
    end_time_rent       TIMESTAMP,
    status              VARCHAR     NOT NULL,
    amount              BIGINT,

    CONSTRAINT  rent_id             PRIMARY KEY (id),
    FOREIGN KEY (id_customer)       REFERENCES  customer(id),
    FOREIGN KEY (id_transport)      REFERENCES  transport(id),
    FOREIGN KEY (id_begin_parking)  REFERENCES  parking(id),
    FOREIGN KEY (id_end_parking)    REFERENCES  parking(id)
)