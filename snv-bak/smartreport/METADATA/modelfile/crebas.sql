/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 7.x                     */
/* Created on:     2013/9/10 22:35:49                           */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('SMP_AREA')
            and   type = 'U')
   drop table SMP_AREA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SMP_BILLTEST')
            and   type = 'U')
   drop table SMP_BILLTEST
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SMP_CLSEXE')
            and   type = 'U')
   drop table SMP_CLSEXE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SMP_PROJECT')
            and   type = 'U')
   drop table SMP_PROJECT
go

/*==============================================================*/
/* Table: SMP_AREA                                              */
/*==============================================================*/
create table SMP_AREA (
   pk_area              varchar(50)          not null,
   dapprovedate         char(10)             null,
   vapprovenote         varchar(50)          null,
   vapproveid           varchar(50)          null,
   vbillstatus          numeric(38)          null,
   voperatorid          varchar(50)          null,
   vbillno              varchar(50)          null,
   pk_busitype          varchar(50)          null,
   vbusicode            varchar(50)          null,
   dr                   numeric(10)          null default 0,
   pk_corp              varchar(50)          null,
   ts                   char(19)             null default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
   name                 varchar(50)          null,
   constraint PK_SMP_AREA primary key nonclustered (pk_area)
)
go

/*==============================================================*/
/* Table: SMP_BILLTEST                                          */
/*==============================================================*/
create table SMP_BILLTEST (
   pk_billtest          varchar(50)          not null,
   dapprovedate         char(10)             null,
   vapprovenote         varchar(50)          null,
   vapproveid           varchar(50)          null,
   vbillstatus          numeric(38)          null,
   voperatorid          varchar(50)          null,
   vbillno              varchar(50)          null,
   pk_busitype          varchar(50)          null,
   vbusicode            varchar(50)          null,
   dr                   numeric(10)          null default 0,
   pk_corp              varchar(50)          null,
   ts                   char(19)             null default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
   constraint PK_SMP_BILLTEST primary key nonclustered (pk_billtest)
)
go

/*==============================================================*/
/* Table: SMP_CLSEXE                                            */
/*==============================================================*/
create table SMP_CLSEXE (
   pk_clsexe            varchar(50)          not null,
   dapprovedate         char(10)             null,
   vapprovenote         varchar(50)          null,
   vapproveid           varchar(50)          null,
   vbillstatus          numeric(38)          null,
   voperatorid          varchar(50)          null,
   vbillno              varchar(50)          null,
   pk_busitype          varchar(50)          null,
   vbusicode            varchar(50)          null,
   dr                   numeric(10)          null default 0,
   pk_corp              varchar(50)          null,
   ts                   char(19)             null default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
   classdate            char(10)             null,
   name                 varchar(50)          null,
   sequence             numeric              null,
   studentnum           numeric              null,
   money                numeric              null,
   pk_area              varchar(50)          null,
   pk_project           varchar(50)          null,
   constraint PK_SMP_CLSEXE primary key nonclustered (pk_clsexe)
)
go

/*==============================================================*/
/* Table: SMP_PROJECT                                           */
/*==============================================================*/
create table SMP_PROJECT (
   pk_project           varchar(50)          not null,
   dapprovedate         char(10)             null,
   vapprovenote         varchar(50)          null,
   vapproveid           varchar(50)          null,
   vbillstatus          numeric(38)          null,
   voperatorid          varchar(50)          null,
   vbillno              varchar(50)          null,
   pk_busitype          varchar(50)          null,
   vbusicode            varchar(50)          null,
   dr                   numeric(10)          null default 0,
   pk_corp              varchar(50)          null,
   ts                   char(19)             null default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
   pk_area              varchar(50)          null,
   name                 varchar(50)          null,
   constraint PK_SMP_PROJECT primary key nonclustered (pk_project)
)
go

