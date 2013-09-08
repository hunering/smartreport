/*单据测试*/
create table smp_danjutest (
/*单据ID*/  billid varchar(50) NOT NULL,
/*单据号*/  billno varchar(50) NULL,
/*所属公司*/  pk_corp varchar(50) NULL,
/*业务类型*/  busitype varchar(50) NULL,
/*制单人*/  billmarker varchar(50) NULL,
/*审批人*/  approver varchar(50) NULL,
/*审批状态*/  approvestatus int NULL,
/*审批批语*/  approvenote varchar(50) NULL,
/*审批日期*/  approvedate char(10) NULL,
/*单据类型*/  billtype varchar(50) NULL,
/*单据名称*/  name varchar(50) NULL,
CONSTRAINT PK_SMP_DANJUTEST PRIMARY KEY (billid),
ts char(19) null default convert(char(19),getdate(),20),
 dr smallint null default 0,
)
 go
