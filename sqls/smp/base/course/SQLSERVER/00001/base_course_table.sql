/*课程*/
create table smp_course (
/*课程主键*/  pk_course char(20) NOT NULL,
/*课程编码*/  course_code varchar(50) NULL,
/*课程名称*/  course_name varchar(50) NULL,
/*总部拆分比例*/  headerquarter_percentage decimal(28,8) NULL,
/*分公司拆分比例*/  branch_percentage decimal(28,8) NULL,
/*绩效拆分比例*/  jixiao_percentage decimal(28,8) NULL,
/*师道拆分比例*/  shidao_percentage decimal(28,8) NULL,
/*经销商拆分比例*/  agency_percentage decimal(28,8) NULL,
/*课程类别主键*/  pk_coursetype char(20) NULL,
/*备注*/  remark varchar(1024) NULL,
/*备用1*/  reserved1 varchar(50) NULL,
/*备用2*/  reserved2 varchar(50) NULL,
/*备用3*/  reserved3 varchar(50) NULL,
/*备用4*/  reserved4 varchar(50) NULL,
/*备用5*/  reserved5 varchar(50) NULL,
CONSTRAINT PK_SMP_COURSE PRIMARY KEY (pk_course),
ts char(19) null default convert(char(19),getdate(),20),
 dr smallint null default 0,
)
 go
