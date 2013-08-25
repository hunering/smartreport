/*课程类别*/
create table smp_coursetype (
/*课程类别主键*/  pk_coursetype char(20) NOT NULL,
/*课程类别名称*/  coursetype_name varchar(50) NULL,
CONSTRAINT PK_SMP_COURSETYPE PRIMARY KEY (pk_coursetype),
ts char(19) null default convert(char(19),getdate(),20),
 dr smallint null default 0,
)
 go
