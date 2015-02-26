CREATE TABLE T_USER(
	USERNAME    	VARCHAR2(100) NOT NULL PRIMARY KEY,
	PASSWORD    	VARCHAR2(500),
	REALNAME    	VARCHAR2(100),
	IS_ENABLE 		INTEGER  default 1 NOT NULL,
	ROLE   	 		VARCHAR2(100),
	GMT_CREATED 	date,
    GMT_MODIFIED 	date,
    USER_MODIFIED 	VARCHAR2(100)
);


     comment on table  T_USER is '用户权限表';
     comment on column T_USER.USERNAME is '用户名,主键';
     comment on column T_USER.PASSWORD is '加密后的密码';
     comment on column T_USER.REALNAME is '用户全名';
     comment on column T_USER.IS_ENABLE is '是否启用';
     comment on column T_USER.ROLE is '用户角色';
     comment on column T_USER.GMT_CREATED is '创建时间';
     comment on column T_USER.GMT_MODIFIED is '修改时间';
     comment on column T_USER.USER_MODIFIED is '修改人员';
     
create public synonym T_USER for clndb.T_USER;

GRANT select,update,insert,delete ON T_USER TO sge_cln;
     
insert into T_USER values('admin','21232f297a57a5a743894a0e4a801fc3','admin',1,'ROLE_ADMIN',sysdate,sysdate,'admin');
insert into T_USER values('user','ee11cbb19052e40b07aac0ca060c23ee','user',1,'ROLE_USER',sysdate,sysdate,'admin');


     