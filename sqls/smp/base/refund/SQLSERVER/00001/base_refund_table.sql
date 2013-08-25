/*业绩明细*/
create table smp_refund (
/*业绩主键*/  pk_refund varchar(50) NOT NULL,
/*日期*/  performance_date char(10) NULL,
/*课程*/  pk_course varchar(50) NULL,
/*汇款公司名*/  remit_company varchar(50) NULL,
/*汇款名*/  remit_name varchar(50) NULL,
/*汇款金额*/  remit_amount varchar(50) NULL,
/*服务伙伴*/  pk_parter char(20) NULL,
/*团队*/  pk_team char(20) NULL,
/*总监*/  pk_director varchar(50) NULL,
/*CRM打款号*/  crm_remit_number varchar(50) NULL,
CONSTRAINT PK_SMP_REFUND PRIMARY KEY (pk_refund),
ts char(19) null default convert(char(19),getdate(),20),
 dr smallint null default 0,
)
 go
