insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (10, 'shmload', 'localhost', '/home/slt/svn/code/clean/trunk/code/main shmload.common_loader ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '加载共享内存', sysdate,sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (20, 'warehouse', 'localhost', '/home/slt/svn/code/clean/trunk/code/main warehouse.warehouse ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '仓库库存', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (30, 'storage', 'localhost', '/home/slt/svn/code/clean/trunk/code/main storage.storage ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '客户库存', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (40, 'spot', 'localhost', '/home/slt/svn/code/clean/trunk/code/main spot.spot ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '现货市场', sysdate, sysdate,  'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (50, 'forward', 'localhost', '/home/slt/svn/code/clean/trunk/code/main forward.forward ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '即期市场', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (60, 'defer', 'localhost', '/home/slt/svn/code/clean/trunk/code/main defer.defer ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '延期市场', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (70, 'invoice', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.invoice ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '交割发票', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (80, 'moneyio_and_insterest', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.moneyio_and_interest ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '出入金和利息', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (90, 'pt_frozen', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.pt_frozen ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '铂金冻结', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (100, 'hand_frozen', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.hand_frozen ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '手工冻结', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (110, 'base_fund', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.base_fund ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '基础保证金', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (120, 'etf_cash', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.etf_cash ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, 'ETF现金差值', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (130, 'capital_1', 'localhost', '/home/slt/svn/code/clean/trunk/code/main capital.capital ${CUR_DATE} 1', '/tmp/logs/clear-admin/clear.log', null, 1, '第一次费用汇总', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (140, 'spot_deliv', 'localhost', '/home/slt/svn/code/clean/trunk/code/main spot.deliv ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '现货交割', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (145, 'probe_defer_fee_adjust', 'localhost', '/home/slt/svn/code/clean/trunk/code/main test.probe ${CUR_DATE} defer_fee_adjust load', '/tmp/logs/clear-admin/clear.log', null, 0, '测试用：加载随机一分钱调整', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (150, 'breach_apply_gold', 'localhost', '/home/slt/svn/code/clean/trunk/code/main delivery.breach_apply ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '黄金买方违约申报', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (160, 'breach_apply_silver', 'localhost', '/home/slt/svn/code/clean/trunk/code/main delivery.breach_apply_silver ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '白银买方违约申报', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (170, 'silver_deliv_01', 'localhost', '/home/slt/svn/code/clean/trunk/code/main silver_deliv.deliv ${CUR_DATE} 01', '/tmp/logs/clear-admin/clear.log', null, 1, '白银即期交割', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (180, 'gold_deliv_02', 'localhost', '/home/slt/svn/code/clean/trunk/code/main delivery.defer_gold ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '延期黄金交割', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (190, 'silver_deliv_02', 'localhost', '/home/slt/svn/code/clean/trunk/code/main silver_deliv.deliv ${CUR_DATE} 02', '/tmp/logs/clear-admin/clear.log', null, 1, '白银延期交割', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (200, 'breach_fee', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.breach_fee ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '违约费用', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (210, 'deliv_invoice', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.deliv_invoice ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '交割发票生成', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (220, 'inquiry_spot', 'localhost', '/home/slt/svn/code/clean/trunk/code/main inquiry.inquiry_spot ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '询价现货市场清算', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (230, 'etf', 'localhost', '/home/slt/svn/code/clean/trunk/code/main etf.etf ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, 'ETF简易版过户', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (240, 'deliv_fee', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.deliv_fee ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '交割手续费', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (250, 'storage_fee', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.storage_fee ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '仓储费', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (260, 'silver_convey_fee', 'localhost', '/home/slt/svn/code/clean/trunk/code/main other.silver_convey_fee ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '白银运保费', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (270, 'capital_2', 'localhost', '/home/slt/svn/code/clean/trunk/code/main capital.capital ${CUR_DATE} 2', '/tmp/logs/clear-admin/clear.log', null, 1, '第二次费用汇总', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (280, 'gen_res', 'localhost', '/home/slt/svn/code/clean/trunk/code/main sync.gen_res ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '生成同步结果', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (290, 'finance_settle', 'localhost', '/home/slt/svn/code/clean/trunk/code/main report.finance_settle ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '资金结算单', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (300, 'member_fee', 'localhost', '/home/slt/svn/code/clean/trunk/code/main report.member_fee ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '会员费用明细单', sysdate, sysdate, 'admin');
insert into T_CLN_STEP_CONFIG (step, name, ip, url, logfile, param, is_enable, comt, gmt_created, gmt_modified, user_modified)
values (310, 'shmdump', 'localhost', '/home/slt/svn/code/clean/trunk/code/main shmdump.shmdump ${CUR_DATE}', '/tmp/logs/clear-admin/clear.log', null, 1, '生成结果文件', sysdate, sysdate, 'admin');
commit;