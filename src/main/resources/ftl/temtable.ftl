
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
<tr><td colspan="7">${table.comment!}(${table.classname?upper_case})</td></tr>
<tr>
<#list table.columnlist as item>
<th>${item.comment!}</th>
</#list>
</tr>

<#--数据项 -->
<#list 1..10 as x>
<tr>
<#list table.columnlist as item>
<td>
<#if item_index=0>
${x}
</#if>
</td>
</#list>
</tr>
</#list>

</table>
<br/>
</#list>

</body>

</html>

	