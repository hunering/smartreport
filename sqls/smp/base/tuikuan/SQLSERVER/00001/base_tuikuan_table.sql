/*�˿���ϸ��*/
create table smp_tuikuan (
/*�˿�����*/  pk_tuikuan char(20) NOT NULL,
/*ҵ������*/  pk_refund char(20) NULL,
/*�˿���*/  amount decimal(28,8) NULL,
/*�˿�ԭ��*/  reason varchar(50) NULL,
/*�����*/  approved_by varchar(50) NULL,
/*���״̬*/  approved_status varchar(50) NULL,
/*�������*/  approve_date char(19) NULL,
CONSTRAINT PK_SMP_TUIKUAN PRIMARY KEY (pk_tuikuan),
ts char(19) null default convert(char(19),getdate(),20),
 dr smallint null default 0,
)
 go
