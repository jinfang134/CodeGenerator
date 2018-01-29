<#list table.columnlist as item >
	<#if item.name="id">
		<!-- 用id属性来映射主键字段 -->
		<id property="id" column="id" />
	<#else>
		<result column="${item.columnname}"  property="${item.name}" />
	</#if>	
</#list>