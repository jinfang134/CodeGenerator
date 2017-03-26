<html >
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
<script>
</script>
<style type="text/css">
	table.gridtable {
		font-family: verdana,arial,sans-serif;
		font-size:11px;
		color:#333333;
		border-width: 1px;
		border-color: #666666;
		border-collapse: collapse;
	}
	table.gridtable th {
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #666666;
		background-color: #dedede;
	}
	table.gridtable td {
		border-width: 1px;
		padding: 8px;
		border-style: solid;
		border-color: #666666;
		background-color: #ffffff;
	}
</style>
</head>

<body>
<#list tables as table>
	<table class="gridtable" >
	<tr><td colspan="7">${table.comment!} 表名：${table.name!}</td></tr>
	<tr><th>序号</th><th>字段名</th><th>字段描述</th><th>数据类型</th><th>是否允许为空</th><th>约束类型</th><th>其它</th> </tr>
	<#list table.columnlist as item>
	<tr><td>${item_index}</td> 
		<td>${item.columnName!} </td>
		<td>${item.comment!}</td>
		<td>${item.columntype!}</td>
	<#if (item.isnull="NO")>
		<td style="color: #F00">NO</td>
	<#else>
		<td>${item.isnull!}</td>
	</#if>
		<td>${item.columnkey!}</td>
		<td>${item.extra!}</td>	
		</tr>
	</#list>
	</table>
	<br/>
</#list>

</body>
</html>

	