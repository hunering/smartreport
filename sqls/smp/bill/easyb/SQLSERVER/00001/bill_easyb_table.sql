/*���ݲ���*/
create table smp_danjutest (
/*����ID*/  billid varchar(50) NOT NULL,
/*���ݺ�*/  billno varchar(50) NULL,
/*������˾*/  pk_corp varchar(50) NULL,
/*ҵ������*/  busitype varchar(50) NULL,
/*�Ƶ���*/  billmarker varchar(50) NULL,
/*������*/  approver varchar(50) NULL,
/*����״̬*/  approvestatus int NULL,
/*��������*/  approvenote varchar(50) NULL,
/*��������*/  approvedate char(10) NULL,
/*��������*/  billtype varchar(50) NULL,
/*��������*/  name varchar(50) NULL,
CONSTRAINT PK_SMP_DANJUTEST PRIMARY KEY (billid),
ts char(19) null default convert(char(19),getdate(),20),
 dr smallint null default 0,
)
 go
