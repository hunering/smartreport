/*ҵ����ϸ*/
create table smp_refund (
/*ҵ������*/  pk_refund varchar(50) NOT NULL,
/*����*/  performance_date char(10) NULL,
/*�γ�*/  pk_course varchar(50) NULL,
/*��˾��*/  remit_company varchar(50) NULL,
/*�����*/  remit_name varchar(50) NULL,
/*�����*/  remit_amount varchar(50) NULL,
/*������*/  pk_parter char(20) NULL,
/*�Ŷ�*/  pk_team char(20) NULL,
/*�ܼ�*/  pk_director varchar(50) NULL,
/*CRM����*/  crm_remit_number varchar(50) NULL,
CONSTRAINT PK_SMP_REFUND PRIMARY KEY (pk_refund),
ts char(19) null default convert(char(19),getdate(),20),
 dr smallint null default 0,
)
 go
