prompt PL/SQL Developer import file
prompt Created on 2018��12��5�� by yingjie.chen
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
values ('1', '�ۿ����', 'DEPARTMENT_NAME', ' varchar2', '1         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('2', '�ۿ������Ŀ', 'DEDUCTEPROJECT', ' varchar2', '2         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('3', '������ˮ��', 'TRANSACTION_NO', ' varchar2', '3         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('4', '���ݺ�', 'DOCUMENT_NO', ' varchar2', '4         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('5', '�����������', 'AUDITCOMMENT_CODE', ' varchar2', '5         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('6', '��Ŀ���', 'ITEM_CODE', ' varchar2', '6         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('7', '��Ŀ����', 'ITEM_NAME', ' varchar2', '7         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('8', '�۳����', 'CHARGE_MONEY', ' varchar2', '8         ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('10', '��д״̬', 'ISWRITE', ' varchar2', '10        ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('11', '����״̬', 'ISAPPEAL', ' varchar2', '11        ', null, null, null, null, null);
insert into IDA_VIEW (ID, NAME, FIELDNAME, TYPE, SORT, CREATEDATE, UPDATEDATE, BAK, BAK1, BAK2)
values ('9', '��������', 'FEEDBACK_COMPLAINT', ' varchar2', '9         ', null, null, null, null, null);
commit;
prompt 11 records loaded
prompt Loading IDA_XLS_VIEW...
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('1', '���', 'varchar2', 'order_id', to_date('23-11-2018', 'dd-mm-yyyy'), null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('2', '������ˮ��', 'varchar2', 'transaction_no', to_date('23-11-2018', 'dd-mm-yyyy'), null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('3', '�����������', 'varchar2', 'auditcomment_code', to_date('23-11-2018', 'dd-mm-yyyy'), null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('4', '��Ŀ����', 'varchar2', 'item_code', null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('5', '��Ŀ����', 'varchar2', 'item_name', null, null, '1');
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('6', 'ҽ���ڽ��', 'varchar2', 'medicalinsurance_money', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('7', '�۳����', 'varchar2', 'charge_money', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('8', '��������', 'varchar2', 'rule_name', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('9', '�ۿ�ԭ��', 'varchar2', 'charge_reason', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('10', '����ԭ��', 'varchar2', 'pay_reason', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('11', '������', 'varchar2', 'operator', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('12', 'ҽ������', 'varchar2', 'doctor_name', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('13', '�籣����', 'varchar2', 'socialsecurecard_no', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('14', '�α�����', 'varchar2', 'insure_type', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('15', '���ұ���', 'varchar2', 'department_code', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('16', '��������', 'varchar2', 'department_name', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('17', '��Ժ����', 'varchar2', 'inhospital_time', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('18', '��Ժ����', 'varchar2', 'outhospital_time', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('19', '��������', 'varchar2', 'fee_time', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('20', '����', 'varchar2', 'num', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('21', 'סԺ��', 'varchar2', 'hospitalization_no', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('22', '��ҽ��ʽ', 'varchar2', 'medicalattention_way', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('23', '��������', 'varchar2', 'balance_time', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('24', '���˱��', 'varchar2', 'person_no', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('25', '�α�������', 'varchar2', 'insureperson_name', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('26', '��������', 'varchar2', 'feedback_complaint', null, null, null);
insert into IDA_XLS_VIEW (ID, NAME, TYPE, FIELDNAME, CREATEDATE, CREATENAME, ISKEY)
values ('27', '���ݺ�', 'varchar2', 'document_no', null, null, null);
commit;
prompt 27 records loaded
prompt Enabling triggers for IDA_VIEW...
alter table IDA_VIEW enable all triggers;
prompt Enabling triggers for IDA_XLS_VIEW...
alter table IDA_XLS_VIEW enable all triggers;
set feedback on
set define on
prompt Done.
