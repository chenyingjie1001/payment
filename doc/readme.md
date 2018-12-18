## 扣款助手说明文档

### 1.打开
《创建oracle的用户和表空间命令》，一条条的执行里面的sql如果已经执行可忽略


### 2. 直接命令执行数据文件
@ ida_v1.0.sql 


表解释：

> ida_charge_bill 导入文件表

> ida_import_record 导入记录表

> ida_view 页面展示配置表

> ida_xls_view excel导入模板配置表

> ida_department_config 医院科室配置表

> ida_appealtemplate_config 申诉模板配置表

**其中ida_view 中name是中文名词；fieldname是对应的英文字段名（需要大写）；type是类型一般都是varchar2；sort是排序**

**ida_xls_view 中中name是中文名词；fieldname是对应的英文字段名；type是类型一般都是varchar2；iskey是标注1的是主键**


### 3. 执行
《现场环境如果缺少wm_concat函数则执行.md》文件


### 4. 仔细配置其中ida_view和ida_xls_view两个表
确保数据和excel对应
如果不想配置可直接命令执行

@ida_xls.sql