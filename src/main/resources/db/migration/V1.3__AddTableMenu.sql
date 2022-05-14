create table CBTMEN
(
    CBTMEN_CODE       int auto_increment
        primary key,
    CBT_CBTMEN_CODE   int                                     null,
    CBTMEN_LABEL      varchar(32)                             not null,
    CBTMEN_DATA       varchar(32)                             not null,
    CBTMEN_ICON       varchar(32)                             not null,
    CBTMEN_URL        varchar(32)                             null,
    CBTMEN_ROLE       varchar(64)                             not null,
    CBTMEN_CONDITION  tinyint(1)                              null,
    CBTMEN_ORDER      int                                     null,
    CBTMEN_VALID_FROM timestamp default current_timestamp()   not null on update current_timestamp(),
    CBTMEN_VALID_TO   timestamp                               null,
    CBTMEN_CREATED_BY varchar(16)                             null,
    CBTMEN_UPDATED_BY varchar(16)                             null,
    CBTMEN_CREATED_AT timestamp default '0000-00-00 00:00:00' null,
    CBTMEN_UPDATED_AT timestamp default '0000-00-00 00:00:00' null,
    constraint XID_CBTMEN_CODE_XID_CBT_CBTMEN_CODE
        foreign key (CBT_CBTMEN_CODE) references CBTMEN (CBTMEN_CODE)
)
    comment 'MENU';

create index CBTMEN_INDEX
    on CBTMEN (CBTMEN_CODE);

INSERT INTO CBTMEN (CBTMEN_CODE, CBT_CBTMEN_CODE, CBTMEN_LABEL, CBTMEN_DATA, CBTMEN_ICON, CBTMEN_URL, CBTMEN_ROLE, CBTMEN_VALID_FROM, CBTMEN_VALID_TO, CBTMEN_CREATED_BY, CBTMEN_UPDATED_BY, CBTMEN_CREATED_AT, CBTMEN_UPDATED_AT, CBTMEN_CONDITION, CBTMEN_ORDER) VALUES (1, null, 'Administración', 'Administración', 'pi pi-fw pi-home', '', 'realm-admin', '2022-05-07 17:20:38', null, null, null, '2022-05-06 00:14:06', null, 1, 1);
INSERT INTO CBTMEN (CBTMEN_CODE, CBT_CBTMEN_CODE, CBTMEN_LABEL, CBTMEN_DATA, CBTMEN_ICON, CBTMEN_URL, CBTMEN_ROLE, CBTMEN_VALID_FROM, CBTMEN_VALID_TO, CBTMEN_CREATED_BY, CBTMEN_UPDATED_BY, CBTMEN_CREATED_AT, CBTMEN_UPDATED_AT, CBTMEN_CONDITION, CBTMEN_ORDER) VALUES (2, null, 'Diseño', 'Diseño', 'pi pi-fw pi-star-fill', '', 'realm-admin', '2022-05-07 17:20:38', null, null, null, '2022-05-06 00:39:39', null, 1, 4);
INSERT INTO CBTMEN (CBTMEN_CODE, CBT_CBTMEN_CODE, CBTMEN_LABEL, CBTMEN_DATA, CBTMEN_ICON, CBTMEN_URL, CBTMEN_ROLE, CBTMEN_VALID_FROM, CBTMEN_VALID_TO, CBTMEN_CREATED_BY, CBTMEN_UPDATED_BY, CBTMEN_CREATED_AT, CBTMEN_UPDATED_AT, CBTMEN_CONDITION, CBTMEN_ORDER) VALUES (8, 1, 'Gestión de Usuarios', 'Gestión de Usuarios', 'pi pi-fw pi-users', null, 'realm-admin', '2022-05-07 17:20:38', null, null, null, '2022-05-06 02:51:57', null, 1, 1);
INSERT INTO CBTMEN (CBTMEN_CODE, CBT_CBTMEN_CODE, CBTMEN_LABEL, CBTMEN_DATA, CBTMEN_ICON, CBTMEN_URL, CBTMEN_ROLE, CBTMEN_VALID_FROM, CBTMEN_VALID_TO, CBTMEN_CREATED_BY, CBTMEN_UPDATED_BY, CBTMEN_CREATED_AT, CBTMEN_UPDATED_AT, CBTMEN_CONDITION, CBTMEN_ORDER) VALUES (9, 2, 'Troqueles', 'Troqueles', 'pi pi-fw pi-th-large', '/home/troqueles', 'realm-admin', '2022-05-07 17:33:30', null, null, null, '2022-05-06 02:53:05', '2022-05-10 18:17:44', 1, 1);
INSERT INTO CBTMEN (CBTMEN_CODE, CBT_CBTMEN_CODE, CBTMEN_LABEL, CBTMEN_DATA, CBTMEN_ICON, CBTMEN_URL, CBTMEN_ROLE, CBTMEN_VALID_FROM, CBTMEN_VALID_TO, CBTMEN_CREATED_BY, CBTMEN_UPDATED_BY, CBTMEN_CREATED_AT, CBTMEN_UPDATED_AT, CBTMEN_CONDITION, CBTMEN_ORDER) VALUES (16, 8, 'Usuarios', 'Usuarios', 'pi pi-fw pi-user', '/home/usuarios', 'realm-admin', '2022-05-07 17:33:30', null, null, null, '2022-05-07 15:06:06', null, 1, 1);
INSERT INTO CBTMEN (CBTMEN_CODE, CBT_CBTMEN_CODE, CBTMEN_LABEL, CBTMEN_DATA, CBTMEN_ICON, CBTMEN_URL, CBTMEN_ROLE, CBTMEN_VALID_FROM, CBTMEN_VALID_TO, CBTMEN_CREATED_BY, CBTMEN_UPDATED_BY, CBTMEN_CREATED_AT, CBTMEN_UPDATED_AT, CBTMEN_CONDITION, CBTMEN_ORDER) VALUES (17, 8, 'Roles', 'Roles', 'pi pi-fw pi-user-edit', '/home/roles', 'realm-admin', '2022-05-07 17:33:30', null, null, null, '2022-05-07 15:38:17', null, 1, 2);
INSERT INTO CBTMEN (CBTMEN_CODE, CBT_CBTMEN_CODE, CBTMEN_LABEL, CBTMEN_DATA, CBTMEN_ICON, CBTMEN_URL, CBTMEN_ROLE, CBTMEN_VALID_FROM, CBTMEN_VALID_TO, CBTMEN_CREATED_BY, CBTMEN_UPDATED_BY, CBTMEN_CREATED_AT, CBTMEN_UPDATED_AT, CBTMEN_CONDITION, CBTMEN_ORDER) VALUES (25, 1, 'Menú', 'Menú', 'pi pi-fw pi-bars', '/home/menu', 'realm-admin', '2022-05-09 18:39:41', null, null, null, '2022-05-09 18:39:41', null, null, 2);
INSERT INTO CBTMEN (CBTMEN_CODE, CBT_CBTMEN_CODE, CBTMEN_LABEL, CBTMEN_DATA, CBTMEN_ICON, CBTMEN_URL, CBTMEN_ROLE, CBTMEN_VALID_FROM, CBTMEN_VALID_TO, CBTMEN_CREATED_BY, CBTMEN_UPDATED_BY, CBTMEN_CREATED_AT, CBTMEN_UPDATED_AT, CBTMEN_CONDITION, CBTMEN_ORDER) VALUES (28, 2, 'Cireles', 'Cireles', 'pi pi-fw pi-print', '/home/cireles', 'realm-admin', '2022-05-10 18:19:24', null, null, null, '2022-05-10 18:19:24', '2022-05-10 18:19:40', null, 2);