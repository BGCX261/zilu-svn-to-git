/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2010-11-21 22:06:36                          */
/*==============================================================*/


drop table if exists CP_COUPON;

drop table if exists CP_COUPON_ENTITY;

drop table if exists SYS_FUNC_RIGHT;

drop table if exists SYS_ROLE;

drop table if exists SYS_ROLE_RIGHT;

drop table if exists SYS_USER;

drop table if exists SYS_USER_ROLE;

drop table if exists ZILU_MERCHANT;

/*==============================================================*/
/* Table: CP_COUPON                                             */
/*==============================================================*/
create table CP_COUPON
(
   COUPON_ID            bigint not null,
   TITLE                varchar(50),
   COUPON_TYPE          char(3),
   ISSUE_QUANTITY       int,
   USED_QUANTITY        int,
   LEFT_QUANTITY        int,
   SOURCE               char(1),
   STATUS               char(1),
   DESCRIPTION          varchar(512),
   COUPON_IMAGE         varchar(100),
   VALID_TIME           timestamp,
   EXPIRE_TIME          timestamp,
   USE_SCOPE            varchar(100),
   ISSUE_TYPE           varchar(30),
   CREATOR              varchar(32),
   CREATOR_ID           bigint,
   CREATOR_TYPE         char(1),
   CREATE_TIME          timestamp,
   FEE                  int,
   CONSUME_LIMIT        char(1),
   MINIMUM_CUNSUME      int,
   primary key (COUPON_ID)
);

alter table CP_COUPON comment '优惠券';

/*==============================================================*/
/* Table: CP_COUPON_ENTITY                                      */
/*==============================================================*/
create table CP_COUPON_ENTITY
(
   ENTITY_ID            bigint not null,
   COUPON_ID            bigint,
   SEQ_NO               varchar(20),
   STATUS               char(1),
   ISSUE_STATUS         char(1),
   CARD_NO              varchar(32),
   CHECK_CODE           varchar(32),
   USER_PHONE           varchar(20),
   USE_CHANNEL          char(1),
   CUSTOMER_ID          bigint,
   LAST_USE_TIME        timestamp,
   USE_DESCRIPT         varchar(512),
   OTHER_VERIFY_INDICATOR char(1),
   VERIFY_KEY           varchar(32),
   VERIFY_KEY_TYPE      char(1),
   MERCHANT_ID          bigint,
   AGENT_MERCHANT_ID    bigint,
   CREATOR              varchar(32),
   CREATOR_ID           bigint,
   CREATOR_TYPE         char(1),
   CREAT_TIME           timestamp,
   primary key (ENTITY_ID)
);

alter table CP_COUPON_ENTITY comment '优惠券实体';

/*==============================================================*/
/* Table: SYS_FUNC_RIGHT                                        */
/*==============================================================*/
create table SYS_FUNC_RIGHT
(
   FUNC_ID              bigint not null,
   FUNC_CODE            varchar(32),
   PARENT_ID            bigint,
   FUNC_NAME            varchar(50),
   FUNC_URL             varchar(150),
   TYPE_GROUP           varchar(20),
   primary key (FUNC_ID)
);

alter table SYS_FUNC_RIGHT comment '系统功能权限';

/*==============================================================*/
/* Table: SYS_ROLE                                              */
/*==============================================================*/
create table SYS_ROLE
(
   ROLE_ID              bigint not null,
   ROLE_NAME            varchar(50),
   DESCRIPTION          varchar(512),
   ENABLED              char(1),
   MERCHANT_ID          bigint,
   TYPE                 char(1),
   CREATOR              varchar(32),
   CREATOR_ID           bigint,
   CREATOR_TYPE         char(1),
   CREATE_TIME          timestamp,
   primary key (ROLE_ID)
);

alter table SYS_ROLE comment '系统角色';

/*==============================================================*/
/* Table: SYS_ROLE_RIGHT                                        */
/*==============================================================*/
create table SYS_ROLE_RIGHT
(
   ROLE_ID              bigint not null,
   FUNC_ID              bigint not null,
   primary key (ROLE_ID, FUNC_ID)
);

alter table SYS_ROLE_RIGHT comment '角色权限';

/*==============================================================*/
/* Table: SYS_USER                                              */
/*==============================================================*/
create table SYS_USER
(
   USER_ID              bigint not null,
   LOGIN_NAME           varchar(32),
   PASSWORD             varchar(32),
   REAL_NAME            varchar(32),
   PHONE                varchar(20),
   MOBILE_PHONE         varchar(20),
   EMAIL                varchar(64),
   MERCHANT_ID          bigint,
   TYPE                 char(1),
   LAST_LOGIN_TIME      timestamp,
   CREATOR              varchar(32),
   CREATOR_ID           bigint,
   CREATOR_TYPE         char(1),
   CREATE_TIME          timestamp,
   primary key (USER_ID)
);

alter table SYS_USER comment '系统用户';

/*==============================================================*/
/* Table: SYS_USER_ROLE                                         */
/*==============================================================*/
create table SYS_USER_ROLE
(
   USER_ID              bigint not null,
   ROLE_ID              bigint not null,
   primary key (USER_ID, ROLE_ID)
);

alter table SYS_USER_ROLE comment '用户角色';

/*==============================================================*/
/* Table: ZILU_MERCHANT                                         */
/*==============================================================*/
create table ZILU_MERCHANT
(
   MERCHANT_ID          bigint not null,
   MERCHANT_NAME        varchar(100),
   PHONE                varchar(20),
   LINK_MAN             varchar(20),
   LINK_PHONE           varchar(20),
   ADDRESS              varchar(200),
   STATUS               char(1),
   SETTLEMENT_ACCOUNT   varchar(32),
   SETTLEMENT_ACCOUNT_BANK varchar(32),
   SETTLEMENT_ACCOUNT_NAME varchar(32),
   AGENT_INDICATOR      char(1),
   CREATOR              varchar(32),
   CREATOR_ID           bigint,
   CREATOR_TYPE         char(1),
   CREATE_TIME          timestamp,
   primary key (MERCHANT_ID)
);

alter table ZILU_MERCHANT comment '商家';

alter table CP_COUPON_ENTITY add constraint FK_list foreign key (COUPON_ID)
      references CP_COUPON (COUPON_ID) on delete restrict on update restrict;

alter table SYS_ROLE_RIGHT add constraint FK_SYS_ROLE_RIGHT foreign key (ROLE_ID)
      references SYS_ROLE (ROLE_ID) on delete restrict on update restrict;

alter table SYS_ROLE_RIGHT add constraint FK_SYS_ROLE_RIGHT2 foreign key (FUNC_ID)
      references SYS_FUNC_RIGHT (FUNC_ID) on delete restrict on update restrict;

alter table SYS_USER_ROLE add constraint FK_SYS_USER_ROLE foreign key (USER_ID)
      references SYS_USER (USER_ID) on delete restrict on update restrict;

alter table SYS_USER_ROLE add constraint FK_SYS_USER_ROLE2 foreign key (ROLE_ID)
      references SYS_ROLE (ROLE_ID) on delete restrict on update restrict;

