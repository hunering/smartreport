/*�Ŷ�*/
create table smp_team (
/*�Ŷ�����*/  pk_team char(20) NOT NULL,
/*�Ŷ�����*/  team_name varchar(50) NULL,
/*�Ŷ��ܼ�*/  team_director varchar(50) NULL,
CONSTRAINT PK_SMP_TEAM PRIMARY KEY (pk_team),
ts char(19) null default convert(char(19),getdate(),20),
 dr smallint null default 0,
)
 go
