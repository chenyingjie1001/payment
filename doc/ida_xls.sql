prompt PL/SQL Developer import file
prompt Created on 2018年12月5日 by yingjie.chen
set feedback off
set define off
prompt Disabling triggers for IDA_VIEW...
alter table IDA_VIEW disable all triggers;
prompt Disabling triggers for IDA_XLS_VIEW...
alter table IDA_XLS_VIEW disable all triggers;
prompt Deleting IDA_XLS_VIEW...
delete from IDA_XLS_VIEW;
commit;
prompt Deleting IDA_VIEW...
delete from IDA_VIEW;
commit;
prompt Loading IDA_VIEW...
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('1', '扣款科室', 'DEPARTMENT_NAME', ' varchar2', '1         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('2', '扣款核算项目', 'DEDUCTEPROJECT', ' varchar2', '2         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('3', '交易流水号', 'TRANSACTION_NO', ' varchar2', '3         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('4', '单据号', 'DOCUMENT_NO', ' varchar2', '4         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('5', '审核意见书编码', 'AUDITCOMMENT_CODE', ' varchar2', '5         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('6', '项目编号', 'ITEM_CODE', ' varchar2', '6         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('7', '项目名称', 'ITEM_NAME', ' varchar2', '7         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('8', '扣除金额', 'CHARGE_MONEY', ' varchar2', '8         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('10', '填写状态', 'ISWRITE', ' varchar2', '10        ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('11', '申诉状态', 'ISAPPEAL', ' varchar2', '11        ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('9', '反馈申诉', 'FEEDBACK_COMPLAINT', ' varchar2', '9         ', null, null, null, null, null);
commit;
prompt 11 records loaded
prompt Loading IDA_XLS_VIEW...
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('1', '序号', 'varchar2', 'order_id', to_date('23-11-2018', 'dd-mm-yyyy'), null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('2', '交易流水号', 'varchar2', 'transaction_no', to_date('23-11-2018', 'dd-mm-yyyy'), null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('3', '审核意见书编码', 'varchar2', 'auditcomment_code', to_date('23-11-2018', 'dd-mm-yyyy'), null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('4', '项目编码', 'varchar2', 'item_code', null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('5', '项目名称', 'varchar2', 'item_name', null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('6', '医保内金额', 'varchar2', 'medicalinsurance_money', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('7', '扣除金额', 'varchar2', 'charge_money', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('8', '规则名称', 'varchar2', 'rule_name', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('9', '扣款原因', 'varchar2', 'charge_reason', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('10', '还款原因', 'varchar2', 'pay_reason', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('11', '操作人', 'varchar2', 'operator', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('12', '医生姓名', 'varchar2', 'doctor_name', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('13', '社保卡号', 'varchar2', 'socialsecurecard_no', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('14', '参保类型', 'varchar2', 'insure_type', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('15', '科室编码', 'varchar2', 'department_code', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('16', '科室名称', 'varchar2', 'department_name', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('17', '入院日期', 'varchar2', 'inhospital_time', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('18', '出院日期', 'varchar2', 'outhospital_time', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('19', '费用日期', 'varchar2', 'fee_time', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('20', '数量', 'varchar2', 'num', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('21', '住院号', 'varchar2', 'hospitalization_no', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('22', '就医方式', 'varchar2', 'medicalattention_way', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('23', '结算日期', 'varchar2', 'balance_time', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('24', '个人编号', 'varchar2', 'person_no', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('25', '参保人姓名', 'varchar2', 'insureperson_name', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('26', '反馈申诉', 'varchar2', 'feedback_complaint', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('27', '单据号', 'varchar2', 'document_no', null, null, null);
commit;
prompt 27 records loaded
prompt Enabling triggers for IDA_VIEW...
alter table IDA_VIEW enable all triggers;
prompt Enabling triggers for IDA_XLS_VIEW...
alter table IDA_XLS_VIEW enable all triggers;
set feedback on
set define on
prompt Done.
