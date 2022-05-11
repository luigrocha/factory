create table CBTPRE
(
    CBTPRE_CODE        int auto_increment
        primary key,
    CBTPRE_COLOR_MODE  varchar(16)                             not null,
    CBTPRE_MENU_MODE   varchar(16)                             not null,
    CBTPRE_MENU_THEME  varchar(16)                             not null,
    CBTPRE_TOPBAR_MODE varchar(16)                             not null,
    CBTPRE_COLOR       varchar(16)                             not null,
    CBTPRE_VALID_FROM  timestamp default current_timestamp()   not null on update current_timestamp(),
    CBTPRE_VALID_TO    timestamp                               null,
    CBTPRE_CREATED_BY  varchar(16)                             null,
    CBTPRE_UPDATED_BY  varchar(16)                             null,
    CBTPRE_CREATED_AT  timestamp default '0000-00-00 00:00:00' not null,
    CBTPRE_UPDATED_AT  timestamp default '0000-00-00 00:00:00' not null
)
    comment 'PREFERENCIAS USUARIO';

create index CBTPRE_INDEX
    on CBTPRE (CBTPRE_CODE);

INSERT INTO CBTPRE (CBTPRE_CODE, CBTPRE_COLOR_MODE, CBTPRE_MENU_MODE, CBTPRE_MENU_THEME, CBTPRE_TOPBAR_MODE, CBTPRE_COLOR, CBTPRE_VALID_FROM, CBTPRE_VALID_TO, CBTPRE_CREATED_BY, CBTPRE_UPDATED_BY, CBTPRE_CREATED_AT, CBTPRE_UPDATED_AT) VALUES (3, 'dim', 'static', 'dim', 'dim', '#30A059', '2022-05-03 00:15:12', '2022-05-03 00:15:12', null, 'prueba', '2022-05-03 00:15:12', '2022-05-03 00:40:32');
INSERT INTO CBTPRE (CBTPRE_CODE, CBTPRE_COLOR_MODE, CBTPRE_MENU_MODE, CBTPRE_MENU_THEME, CBTPRE_TOPBAR_MODE, CBTPRE_COLOR, CBTPRE_VALID_FROM, CBTPRE_VALID_TO, CBTPRE_CREATED_BY, CBTPRE_UPDATED_BY, CBTPRE_CREATED_AT, CBTPRE_UPDATED_AT) VALUES (4, 'light', 'static', 'light', 'light', '#30A059', '2022-05-03 00:45:23', '2022-05-03 00:45:23', null, null, '2022-05-03 00:45:23', '2022-05-03 00:45:23');
INSERT INTO CBTPRE (CBTPRE_CODE, CBTPRE_COLOR_MODE, CBTPRE_MENU_MODE, CBTPRE_MENU_THEME, CBTPRE_TOPBAR_MODE, CBTPRE_COLOR, CBTPRE_VALID_FROM, CBTPRE_VALID_TO, CBTPRE_CREATED_BY, CBTPRE_UPDATED_BY, CBTPRE_CREATED_AT, CBTPRE_UPDATED_AT) VALUES (5, 'dark', 'overlay', 'dark', 'dark', 'sea-green', '2022-05-03 00:46:11', '2022-05-03 00:46:11', null, 'cuser', '2022-05-03 00:46:11', '2022-05-04 00:51:00');
INSERT INTO CBTPRE (CBTPRE_CODE, CBTPRE_COLOR_MODE, CBTPRE_MENU_MODE, CBTPRE_MENU_THEME, CBTPRE_TOPBAR_MODE, CBTPRE_COLOR, CBTPRE_VALID_FROM, CBTPRE_VALID_TO, CBTPRE_CREATED_BY, CBTPRE_UPDATED_BY, CBTPRE_CREATED_AT, CBTPRE_UPDATED_AT) VALUES (6, 'light', 'static', 'light', 'light', 'amber', '2022-05-03 00:46:48', '2022-05-03 00:46:48', null, 'cadmin', '2022-05-03 00:46:48', '2022-05-05 00:03:57');



create table CBTUSE
(
    CBTUSE_CODE       int auto_increment
        primary key,
    CBTPRE_CODE       int                                     null,
    CBTUSE_USERNAME   varchar(16)                             not null,
    CBTUSE_PHOTO      varchar(64)                             null,
    CBTUSE_PHONE      varchar(16)                             null,
    CBTUSE_ADDRESS    varchar(64)                             null,
    CBTUSE_VALID_FROM timestamp default current_timestamp()   not null on update current_timestamp(),
    CBTUSE_VALID_TO   timestamp                               null,
    CBTUSE_CREATED_BY varchar(16)                             null,
    CBTUSE_UPDATED_BY varchar(16)                             null,
    CBTUSE_CREATED_AT timestamp default '0000-00-00 00:00:00' not null,
    CBTUSE_UPDATED_AT timestamp default '0000-00-00 00:00:00' not null,
    constraint XID_CBTPRE_CODE_XID_CBTPRE_CODE
        foreign key (CBTPRE_CODE) references CBTPRE (CBTPRE_CODE)
)
    comment 'USUARIO';

create index CBTUSE_INDEX
    on CBTUSE (CBTUSE_USERNAME);

INSERT INTO CBTUSE (CBTUSE_CODE, CBTPRE_CODE, CBTUSE_USERNAME, CBTUSE_PHOTO, CBTUSE_PHONE, CBTUSE_ADDRESS, CBTUSE_VALID_FROM, CBTUSE_VALID_TO, CBTUSE_CREATED_BY, CBTUSE_UPDATED_BY, CBTUSE_CREATED_AT, CBTUSE_UPDATED_AT) VALUES (3, 3, 'prueba', '', '', '', '2022-05-03 00:15:27', '2022-05-03 00:15:13', 'prueba', null, '2022-05-03 00:15:27', '2022-05-03 00:15:13');
INSERT INTO CBTUSE (CBTUSE_CODE, CBTPRE_CODE, CBTUSE_USERNAME, CBTUSE_PHOTO, CBTUSE_PHONE, CBTUSE_ADDRESS, CBTUSE_VALID_FROM, CBTUSE_VALID_TO, CBTUSE_CREATED_BY, CBTUSE_UPDATED_BY, CBTUSE_CREATED_AT, CBTUSE_UPDATED_AT) VALUES (4, 4, 'csupervisor', '', '', '', '2022-05-03 00:45:38', '2022-05-03 00:45:24', 'csupervisor', null, '2022-05-03 00:45:38', '2022-05-03 00:45:24');
INSERT INTO CBTUSE (CBTUSE_CODE, CBTPRE_CODE, CBTUSE_USERNAME, CBTUSE_PHOTO, CBTUSE_PHONE, CBTUSE_ADDRESS, CBTUSE_VALID_FROM, CBTUSE_VALID_TO, CBTUSE_CREATED_BY, CBTUSE_UPDATED_BY, CBTUSE_CREATED_AT, CBTUSE_UPDATED_AT) VALUES (5, 5, 'cuser', '', '', '', '2022-05-03 00:46:26', '2022-05-03 00:46:11', 'cuser', null, '2022-05-03 00:46:26', '2022-05-03 00:46:11');
INSERT INTO CBTUSE (CBTUSE_CODE, CBTPRE_CODE, CBTUSE_USERNAME, CBTUSE_PHOTO, CBTUSE_PHONE, CBTUSE_ADDRESS, CBTUSE_VALID_FROM, CBTUSE_VALID_TO, CBTUSE_CREATED_BY, CBTUSE_UPDATED_BY, CBTUSE_CREATED_AT, CBTUSE_UPDATED_AT) VALUES (6, 6, 'cadmin', '', '', '', '2022-05-03 00:47:03', '2022-05-03 00:46:49', 'cadmin', null, '2022-05-03 00:47:03', '2022-05-03 00:46:49');