 
  /** 添加资源列表   */
 <#list tables as table>
 <#assign x=180>
 <#assign pid=35>
 <#assign y=8> 
 /** 插入 ${table.comment!} 的资源到资源表中*/
 INSERT INTO `sys_resource` (`id`,`name`,`url`,`description`,`icon`,`pid`,`seq`,`state`,`resourcetype`,`createdatetime`)  values 
 ('${table_index*y+x}','${table.comment!}','/${table.lowername!}/manager','${table.comment!}','icon_dic','${pid}','0','0','0','${createtime?string("yyyy-MM-dd HH:mm:ss.SSS")}');
  INSERT INTO `sys_resource` (`id`,`name`,`url`,`description`,`icon`,`pid`,`seq`,`state`,`resourcetype`,`createdatetime`)  values 
 ('${table_index*y+x+1}','添加','/${table.lowername!}/add','添加${table.comment!}','icon_add','${table_index*y+x}','1','0','1','${createtime?string("yyyy-MM-dd HH:mm:ss.SSS")}');
  INSERT INTO `sys_resource` (`id`,`name`,`url`,`description`,`icon`,`pid`,`seq`,`state`,`resourcetype`,`createdatetime`)  values
 ('${table_index*y+x+2}','删除','/${table.lowername!}/delete','删除${table.comment!}','icon_del','${table_index*y+x}','2','0','1','${createtime?string("yyyy-MM-dd HH:mm:ss.SSS")}');
  INSERT INTO `sys_resource` (`id`,`name`,`url`,`description`,`icon`,`pid`,`seq`,`state`,`resourcetype`,`createdatetime`)  values 
 ('${table_index*y+x+3}','修改','/${table.lowername!}/edit','修改${table.comment!}','icon_edit','${table_index*y+x}','3','0','1','${createtime?string("yyyy-MM-dd HH:mm:ss.SSS")}');
  INSERT INTO `sys_resource` (`id`,`name`,`url`,`description`,`icon`,`pid`,`seq`,`state`,`resourcetype`,`createdatetime`)  values 
 ('${table_index*y+x+4}','列表','/${table.lowername!}/dataGrid','显示${table.comment!}','icon_dic','${table_index*y+x}','4','0','1','${createtime?string("yyyy-MM-dd HH:mm:ss.SSS")}');
  INSERT INTO `sys_resource` (`id`,`name`,`url`,`description`,`icon`,`pid`,`seq`,`state`,`resourcetype`,`createdatetime`)  values 
 ('${table_index*y+x+5}','导出','/${table.lowername!}/export','导出${table.comment!}','icon-print','${table_index*y+x}','4','0','1','${createtime?string("yyyy-MM-dd HH:mm:ss.SSS")}');
 
 </#list>