CREATE TABLE IF NOT EXISTS pm_rop_info (
    id                        SERIAL                CONSTRAINT  fls_pk PRIMARY KEY NOT NULL,
    node_name                 VARCHAR               NOT NULL,
    node_type                 VARCHAR               NOT NULL,
    data_type                 VARCHAR               NOT NULL,
    file_type                 VARCHAR               NOT NULL,
    file_size                 INTEGER               NOT NULL,
    file_location             VARCHAR               NOT NULL,
    file_creationtime_in_oss  TIMESTAMP             NOT NULL,
    start_roptime_in_oss      TIMESTAMP             NOT NULL,
    end_roptime_in_oss        TIMESTAMP             NOT NULL
);