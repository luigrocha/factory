/*==============================================================*/
/* Table: CATFAB                                                */
/*==============================================================*/
CREATE TABLE CATFAB
(
    ID_CATFAB_CODE    INT         NOT NULL AUTO_INCREMENT,
    CATFAB_NAME       VARCHAR(32) NOT NULL,
    CATFAB_VALID_FROM TIMESTAMP,
    CATFAB_VALID_TO   TIMESTAMP   NULL,
    CATFAB_CREATED_BY VARCHAR(16),
    CATFAB_UPDATED_BY VARCHAR(16),
    CATFAB_CREATED_AT TIMESTAMP,
    CATFAB_UPDATED_AT TIMESTAMP,
    PRIMARY KEY (ID_CATFAB_CODE)
);

ALTER TABLE CATFAB
    COMMENT 'FABRICANTE';

/*==============================================================*/
/* Index: CAIFAB_NAME                                           */
/*==============================================================*/
CREATE INDEX CAIFAB_NAME ON CATFAB (CATFAB_NAME);


/*==============================================================*/
/* Table: CATTRO                                                */
/*==============================================================*/
CREATE TABLE CATTRO
(
    ID_CATTRO_CODE      INT           NOT NULL AUTO_INCREMENT,
    XID_CATFAB_CODE     INT,
    XID_CATROSTA_CODE   VARCHAR(2)    NOT NULL,
    CATTRO_PTROQ        VARCHAR(8)    NOT NULL,
    CATTRO_NAME         VARCHAR(64)   NOT NULL,
    CATTRO_CREATED_DATE TIMESTAMP     NOT NULL,
    CATTRO_DESCRIPTION  VARCHAR(128)  NOT NULL,
    CATTRO_AREA         DECIMAL(8, 4) NOT NULL,
    CATTRO_LENGTH       DECIMAL(8, 4) NOT NULL,
    CATTRO_WIDTH        DECIMAL(8, 4) NOT NULL,
    CATTRO_GSMDIS       DECIMAL(8, 4) NOT NULL,
    CATTRO_DSB_MULTIPLE VARCHAR(32),
    CATTRO_OBSERVATIONS VARCHAR(128),
    CATTRO_QUANTITY     DECIMAL(8, 4) NOT NULL,
    CATTRO_QUANTITY_L   DECIMAL(8, 4) NOT NULL,
    CATTRO_SEPARATION_L DECIMAL(8, 4) NOT NULL,
    CATTRO_QUANTITY_W   DECIMAL(8, 4) NOT NULL,
    CATTRO_SEPARATION_W DECIMAL(8, 4) NOT NULL,
    CATTRO_BORDER_L     DECIMAL(8, 4) NOT NULL,
    CATTRO_BORDER_W     DECIMAL(8, 4) NOT NULL,
    CATTRO_LEAF_L       DECIMAL(8, 4) NOT NULL,
    CATTRO_LEAF_W       DECIMAL(8, 4) NOT NULL,
    CATTRO_VALID_FROM   TIMESTAMP,
    CATTRO_VALID_TO     TIMESTAMP     NULL,
    CATTRO_CREATED_BY   VARCHAR(16),
    CATRO_UPDATED_BY    VARCHAR(16),
    CATRO_CREATED_AT    TIMESTAMP,
    CATRO_UPDATED_AT    TIMESTAMP,
    PRIMARY KEY (ID_CATTRO_CODE)
);

ALTER TABLE CATTRO
    COMMENT 'TROQUEL';

/*==============================================================*/
/* Index: CAITRO_PTROQ                                          */
/*==============================================================*/
CREATE INDEX CAITRO_PTROQ ON CATTRO (CATTRO_PTROQ);

/*==============================================================*/
/* Table: CATMAQ                                                */
/*==============================================================*/
CREATE TABLE CATMAQ
(
    ID_CATMAQ_CODE    INT         NOT NULL AUTO_INCREMENT,
    CATMAQ_NAME       VARCHAR(32) NOT NULL,
    CATMAQ_HAS_DESB   BOOL        NOT NULL,
    CATMAQ_VALID_FROM TIMESTAMP,
    CATMAQ_VALID_TO   TIMESTAMP   NULL,
    CATMAQ_CREATED_BY VARCHAR(16),
    CATMAQ_UPDATED_BY VARCHAR(16),
    CATMAQ_CREATED_AT TIMESTAMP,
    CATMAQ_UPDATED_AT TIMESTAMP,
    PRIMARY KEY (ID_CATMAQ_CODE)
);

ALTER TABLE CATMAQ
    COMMENT 'MAQUINA';

/*==============================================================*/
/* Index: CAIMAQ_NAME                                           */
/*==============================================================*/
CREATE UNIQUE INDEX CAIMAQ_NAME ON CATMAQ (CATMAQ_NAME);

/*==============================================================*/
/* Table: CATMAQ_TRO                                            */
/*==============================================================*/
CREATE TABLE CATMAQ_TRO
(
    XID_CATTRO_CODE       INT       NOT NULL,
    XID_CATMAQ_CODE       INT       NOT NULL,
    CATMAQ_TRO_VALID_FROM TIMESTAMP,
    CATMAQ_TRO_VALID_TO   TIMESTAMP NULL,
    CATMAQ_TRO_CREATED_BY VARCHAR(16),
    CATMAQ_TRO_UPDATED_BY VARCHAR(16),
    CATMAQ_TRO_CREATED_AT TIMESTAMP,
    CATMAQ_TRO_UPDATED_AT TIMESTAMP,
    PRIMARY KEY (XID_CATTRO_CODE, XID_CATMAQ_CODE)
);

ALTER TABLE CATMAQ_TRO
    COMMENT 'TROQUEL MAQUINA';

/*==============================================================*/
/* Table: CATSTATUS                                             */
/*==============================================================*/
CREATE TABLE CATSTATUS
(
    ID_CATSTATUS_CODE    VARCHAR(4) NOT NULL,
    CATSTATUS_NAME       VARCHAR(16) NOT NULL,
    CATSTATUS_BACKGROUND_COLOR VARCHAR(8),
    CATSTATUS_COLOR      VARCHAR(8),
    CATSTATUS_TYPE       VARCHAR(2) NOT NULL,
    CATSTATUS_VALID_FROM TIMESTAMP,
    CATSTATUS_VALID_TO   TIMESTAMP NULL,
    CATSTATUS_CREATED_BY VARCHAR(16),
    CATSTATUS_UPDATED_BY VARCHAR(16),
    CATSTATUS_CREATED_AT TIMESTAMP,
    CATSTATUS_UPDATED_AT TIMESTAMP,
    PRIMARY KEY (ID_CATSTATUS_CODE)
);

ALTER TABLE CATSTATUS COMMENT 'CATALOGO DE ESTADOS';

ALTER TABLE CATMAQ_TRO
    ADD CONSTRAINT XID_CATMAQ_TRO_XID_CATMAQ_CODE FOREIGN KEY (XID_CATMAQ_CODE)
        REFERENCES CATMAQ (ID_CATMAQ_CODE) ON DELETE CASCADE;

ALTER TABLE CATMAQ_TRO
    ADD CONSTRAINT XID_CATMAQ_TRO_XID_CATTRO_CODE FOREIGN KEY (XID_CATTRO_CODE)
        REFERENCES CATTRO (ID_CATTRO_CODE) ON DELETE CASCADE;

ALTER TABLE CATTRO
    ADD CONSTRAINT XID_CATTRO_CODE_XID_CATFAB_CODE FOREIGN KEY (XID_CATFAB_CODE)
        REFERENCES CATFAB (ID_CATFAB_CODE) ON DELETE RESTRICT;

ALTER TABLE CATTRO ADD CONSTRAINT XID_CATTRO_CODE_XID_CATSTATUS_CODE FOREIGN KEY (XID_CATSTATUS_CODE)
    REFERENCES CATSTATUS (ID_CATSTATUS_CODE) ON DELETE RESTRICT;