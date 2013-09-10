/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 7.x                     */
/* Created on:     2013/9/10 22:35:02                           */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('SMP_BILLTEST')
            and   type = 'U')
   drop table SMP_BILLTEST
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SMP_COURSE')
            and   type = 'U')
   drop table SMP_COURSE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SMP_COURSETYPE')
            and   type = 'U')
   drop table SMP_COURSETYPE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SMP_PARTNER')
            and   type = 'U')
   drop table SMP_PARTNER
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SMP_PERFORMANCE')
            and   type = 'U')
   drop table SMP_PERFORMANCE
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SMP_REFUND')
            and   type = 'U')
   drop table SMP_REFUND
go

if exists (select 1
            from  sysobjects
           where  id = object_id('smp_team')
            and   type = 'U')
   drop table smp_team
go

/*==============================================================*/
/* Table: SMP_BILLTEST                                          */
/*==============================================================*/
create table SMP_BILLTEST (
   pk_billtest          varchar(20)          not null,
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
/* Table: SMP_COURSE                                            */
/*==============================================================*/
create table SMP_COURSE (
   pk_course            varchar(50)          not null,
   course_name          varchar(50)          not null,
   voperatorid          varchar(50)          null,
   vbusicode            varchar(50)          null,
   dr                   numeric(10)          null default 0,
   pk_corp              varchar(50)          null,
   ts                   char(19)             null default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
   pk_coursetype        varchar(50)          null,
   reserved2            varchar(100)         null,
   reserved1            varchar(100)         null,
   reserved3            varchar(100)         null,
   reserved4            varchar(100)         null,
   reserverd5           varchar(100)         null,
   remark               varchar(200)         null,
   agency_percentage    numeric(10)          null,
   shidao_percentage    numeric(10)          null,
   jixiao_percentage    numeric(10)          null,
   branch_percentage    numeric(10)          null,
   headerquarter_percentage numeric(10)          null,
   course_code          char(20)             not null,
   constraint PK_SMP_COURSE primary key (pk_course)
)
go

/*==============================================================*/
/* Table: SMP_COURSETYPE                                        */
/*==============================================================*/
create table SMP_COURSETYPE (
   pk_coursetype        varchar(50)          not null,
   coursetypename       varchar(50)          not null,
   voperatorid          varchar(50)          null,
   vbusicode            varchar(50)          null,
   dr                   numeric(10)          null default 0,
   pk_corp              varchar(50)          null,
   ts                   char(19)             null default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
   constraint PK_SMP_COURSETYPE primary key nonclustered (pk_coursetype)
)
go

/*==============================================================*/
/* Table: SMP_PARTNER                                           */
/*==============================================================*/
create table SMP_PARTNER (
   pk_partner           char(20)             not null,
   partner_name         varchar(100)         null,
   ts                   char(19)             null default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
   dr                   numeric(10)          null default 0,
   pk_corp              varchar(50)          null,
   voperatorid          varchar(50)          null,
   pk_team              varchar(50)          null,
   constraint PK_SMP_PARTNER primary key nonclustered (pk_partner)
)
go

/*==============================================================*/
/* Table: SMP_PERFORMANCE                                       */
/*==============================================================*/
create table SMP_PERFORMANCE (
   pk_performance       char(20)             not null,
   performance_date     varchar(100)         null,
   pk_course            varchar(50)          null,
   remit_name           varchar(100)         null,
   remit_company        varchar(100)         null,
   remit_amount         numeric(20,8)        null,
   pk_team              varchar(50)          null,
   director_id          varchar(100)         null,
   crm_remit_number     varchar(100)         null,
   pk_partner           varchar(100)         null,
   ts                   char(19)             null default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
   dr                   numeric(10)          null default 0,
   pk_corp              varchar(50)          null,
   voperatorid          varchar(50)          null,
   achievetype          numeric(10)          null,
   constraint PK_SMP_PERFORMANCE primary key nonclustered (pk_performance)
)
go

/*==============================================================*/
/* Table: SMP_REFUND                                            */
/*==============================================================*/
create table SMP_REFUND (
   refundid             varchar(20)          not null,
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
   pk_performance       varchar(50)          null,
   refundamount         varchar(50)          null,
   refundreason         varchar(50)          null,
   constraint PK_SMP_REFUND primary key (refundid)
)
go

/*==============================================================*/
/* Table: smp_team                                              */
/*==============================================================*/
create table smp_team (
   pk_team              char(20)             not null,
   team_name            varchar(100)         null,
   pk_director          char(20)             null,
   team_director_name   varchar(100)         null,
   ts                   char(19)             null default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
   dr                   numeric(10)          null default 0,
   pk_corp              varchar(50)          null,
   voperatorid          varchar(50)          null,
   constraint PK_SMP_TEAM primary key nonclustered (pk_team)
)
go

