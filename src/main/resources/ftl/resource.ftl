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

	<table class="gridtable" >
	
	<tr><th>id</th><th>name</th><th>url</th><th>description</th><th>icon</th><th>pid</th><th>seq</th><th>state</th><th>resourcetype</th><th>createdatetime</th> </tr>
	<#list tables as table>
	<tr><td>${table_index*5+100}</td> 
		<td>${table.comment!}</td>
		<td>/${table.lowername!}/manager</td>
		<td>${table.comment!}</td>
		<td>icon_dic</td>
		<td>35</td>
		<td>${table_index}</td>
		<td>0</td>
		<td>0</td>
		<td>${createtime?string("yyyy-MM-dd HH:mm:ss")}</td></tr>
	<tr><td>${table_index*5+101}</td> 
		<td>添加</td>
		<td>/${table.lowername!}/add</td>
		<td>添加${table.comment!}</td>
		<td>icon_add</td>
		<td>${table_index*5+100}</td>
		<td>0</td>
		<td>0</td>
		<td>1</td>
		<td>${createtime?string("yyyy-MM-dd HH:mm:ss")}</td></tr>	
	<tr><td>${table_index*5+102}</td> 
		<td>删除</td>
		<td>/${table.lowername!}/delete</td>
		<td>删除${table.comment!}</td>
		<td>icon_delete</td>
		<td>${table_index*5+100}</td>
		<td>0</td>
		<td>0</td>
		<td>1</td>
		<td>${createtime?string("yyyy-MM-dd HH:mm:ss")}</td></tr>	
	<tr><td>${table_index*5+103}</td> 
		<td>修改</td>
		<td>/${table.lowername!}/edit</td>
		<td>修改${table.comment!}</td>
		<td>icon_edit</td>
		<td>${table_index*5+100}</td>
		<td>0</td>
		<td>0</td>
		<td>1</td>
		<td>${createtime?string("yyyy-MM-dd HH:mm:ss")}</td></tr>	
	<tr><td>${table_index*5+104}</td> 
		<td>列表</td>
		<td>/${table.lowername!}/dataGrid</td>
		<td>显示${table.comment!}</td>
		<td>icon_dic</td>
		<td>${table_index*5+100}</td>
		<td>0</td>
		<td>0</td>
		<td>1</td>
		<td>${createtime?string("yyyy-MM-dd HH:mm:ss")}</td></tr>
		</#list>
	</table>
	<br/>


</body>
</html>

	