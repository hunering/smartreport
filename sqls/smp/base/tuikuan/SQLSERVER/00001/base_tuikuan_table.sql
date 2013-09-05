/*退款明细表*/
create table smp_tuikuan (
/*退款主键*/  pk_tuikuan char(20) NOT NULL,
/*业绩主键*/  pk_refund char(20) NULL,
/*退款金额*/  amount decimal(28,8) NULL,
/*退款原因*/  reason varchar(50) NULL,
/*审核人*/  approved_by varchar(50) NULL,
/*审核状态*/  approved_status varchar(50) NULL,
/*审核日期*/  approve_date char(19) NULL,
CONSTRAINT PK_SMP_TUIKUAN PRIMARY KEY (pk_tuikuan),
ts char(19) null default convert(char(19),getdate(),20),
 dr smallint null default 0,
)
 go
