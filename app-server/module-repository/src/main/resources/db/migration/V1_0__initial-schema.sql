CREATE TABLE IF NOT EXISTS tags
(
    id   BIGSERIAL   NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE
) WITH (OIDS = FALSE);

CREATE TABLE IF NOT EXISTS certificates
(
    id                BIGSERIAL      NOT NULL PRIMARY KEY,
    name              VARCHAR(100)   NOT NULL,
    description       VARCHAR(2000)  NULL,
    price             NUMERIC(12, 2) NOT NULL,
    date_time_created TIMESTAMP      NOT NULL,
    date_time_updated TIMESTAMP      NULL,
    duration          INT            NOT NULL
) WITH (OIDS = FALSE);

CREATE TABLE IF NOT EXISTS certificates_has_tags
(
    certificates_id BIGINT NOT NULL
        CONSTRAINT certificates_fk REFERENCES certificates
            ON UPDATE CASCADE
            ON DELETE CASCADE,

    tags_id         BIGINT NOT NULL
        CONSTRAINT tags_fk REFERENCES tags
            ON UPDATE CASCADE
            ON DELETE CASCADE,

    CONSTRAINT certificates_has_tags_pk PRIMARY KEY (certificates_id, tags_id)
) WITH (OIDS = FALSE);
