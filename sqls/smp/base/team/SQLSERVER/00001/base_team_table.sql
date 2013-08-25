/*团队*/
create table smp_team (
/*团队主键*/  pk_team char(20) NOT NULL,
/*团队名称*/  team_name varchar(50) NULL,
/*团队总监*/  team_director varchar(50) NULL,
CONSTRAINT PK_SMP_TEAM PRIMARY KEY (pk_team),
ts char(19) null default convert(char(19),getdate(),20),
 dr smallint null default 0,
)
 go
