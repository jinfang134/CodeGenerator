 
  /** 创建表sql  */
 <#list tables as table>
/* 创建${table.comment!} */
CREATE TABLE `${table.name!}`
(
<#list table.columnlist as col>
`${col.columnName!}` <#if col.type=='date'>${col.type!}<#elseif col.type=='String'||col.type=='string'>varchar(${col.length!})<#else> ${col.type!}(${col.length!})</#if> DEFAULT NULL COMMENT '${col.comment!}',
  </#list>
  PRIMARY KEY (`id`)
  )
   ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${table.comment!}';

 </#list>

