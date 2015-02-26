CREATE TABLE T_CLN_STEP_CONFIG(
	STEP 			INTEGER NOT NULL PRIMARY KEY,
	NAME    		VARCHAR2(100),
	IP				VARCHAR2(100) DEFAULT 'localhost',
	URL				VARCHAR2(200),
	LOGFILE			VARCHAR2(200),
	PARAM			VARCHAR2(100),
	IS_ENABLE 		INTEGER  default 1 NOT NULL,
	COMT			VARCHAR2(200),
	GMT_CREATED 	date,
    GMT_MODIFIED 	date,
    USER_MODIFIED 	VARCHAR2(100)	
);

     comment on table  T_CLN_STEP_CONFIG is '清算场次配置表';
     comment on column T_CLN_STEP_CONFIG.STEP is '清算场次号,主键';
     comment on column T_CLN_STEP_CONFIG.NAME is '清算场次名称';
     comment on column T_CLN_STEP_CONFIG.IP is '应用IP,默认localhost';
     comment on column T_CLN_STEP_CONFIG.URL is '应用路径';
     comment on column T_CLN_STEP_CONFIG.LOGFILE is '日志路径';
     comment on column T_CLN_STEP_CONFIG.PARAM is '应用需要的额外入参';
     comment on column T_CLN_STEP_CONFIG.IS_ENABLE is '是否启用';
     comment on column T_CLN_STEP_CONFIG.COMT is '场次备注';
     comment on column T_CLN_STEP_CONFIG.GMT_CREATED is '创建时间';
     comment on column T_CLN_STEP_CONFIG.GMT_MODIFIED is '修改时间';
     comment on column T_CLN_STEP_CONFIG.USER_MODIFIED is '修改人员';
     
create public synonym T_CLN_STEP_CONFIG for clndb.T_CLN_STEP_CONFIG;

GRANT select,update,insert,delete ON T_CLN_STEP_CONFIG TO sge_cln;