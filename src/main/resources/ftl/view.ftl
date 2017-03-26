<%@ page language="java" contentType="text/html; charset=UTF-8"  	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${r"${pageContext.request.contextPath}"}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<!--   -->
<script type="text/javascript" charset="UTF-8">
	var datagrid;
	var userDialog;
	var userForm;
	var editurl='edit';
	var deleteurl='delete';
	var addurl='add';
	var gridurl='dataGrid';
	$(function() {	
		//自动完成搜索
		$(".tableForm").find("input").bind('input propertychange',function(){
				searchFun();
			});
		//绑定下拉菜单
		bindData();
		userForm = $('#userForm').form();			
		userDialog = $('#userDialog').show().dialog({
			modal : true,
			title : '${table.comment!}',
			buttons : [ {
				text : '确定',
				handler : function() {
					//编辑
					if (userForm.find('[name=id]').val() != '') {
						userForm.form('submit', {
							url : editurl,
							success : function(data) {
								userDialog.dialog('close');
								$.messager.show({
									msg : '修改成功！',
									title : '提示'
								});
								datagrid.datagrid('reload');
							}
						});
					} else {	//创建
						userForm.form('submit', {
							url : addurl,
							success : function(data) {
								try {
									var d = $.parseJSON(data);
									if (d.success) {
										userDialog.dialog('close');
										$.messager.show({
											msg : '创建成功',
											title : '提示'
										});
										datagrid.datagrid('reload');
									}
								} catch (e) {
									$.messager.show({
										msg : '用户名称已存在！',
										title : '提示'
									});
								}
							}
						});
					}
				}
			} ]
		}).dialog('close');
		//数据表
		datagrid = $('#datagrid').datagrid({
			url : gridurl,
			toolbar : '#toolbar',
		//	title : '${table.comment!}',
		//	iconCls : 'icon-save',
			pagination : true,
			singleSelect:true,
			rownumbers:true,
			striped:true,			
			pageSize : 50,
			pageList : [ 50,100,200,500 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'id',
//			frozenColumns : [ [ 
//			{	title : 'id',field : 'id',width : 50,checkbox : true},
//			 ] ],
			columns : [ [ 			
<#list table.columnlist as item>
		<#if item.name!="id">
			{	field : '${item.name!}',title : '${item.comment!}',width :100,sortable:true,align:'center' },
		</#if>
</#list>
			] ],
			
			//行右键菜单
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
		
		//搜索面板事件
		$("#searchpanel").panel({
			onCollapse:function(){
				$('#datagrid').datagrid('resize',{  
					height:$("#body").height()-$('#searchpanel').height(),
					width:$("#body").width()
				});
			},
			onExpand:function(){
				$('#datagrid').datagrid('resize',{  
					height:$("#body").height()-$('#searchpanel').height(),
					width:$("#body").width()
				});
			},
		});
		
	});	
	//导出excel或者word
	function exportExcelandWord(type){
			var tableString=getExcelData(datagrid);
		 	$("#data").val(tableString);
		 	$("#type").val(type);
   			document.getElementById("exportExcel").submit();  
	}
	
	// 添加
	function add() {
		userDialog.dialog('open');
		//userForm.find('[name=tvmcode]').removeAttr('readonly');
		//userForm.find('[name=stationcode]').removeAttr('readonly');		
		userForm.form('clear');
	}
	//编辑
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length != 1 && rows.length != 0) {			
			$.messager.show({
				msg : '只能择一个用户进行编辑！您已经选择了' + rows.length + '个用户',
				title :  '提示'
			});
		} else if (rows.length == 1) {		
		//	userForm.find('[name=stationcode]').attr('readonly', 'readonly');
		//	userForm.find('[name=stationcode]').attr('readonly', 'readonly');
			userDialog.dialog('open');
			userForm.form('clear');
			userForm.form('load', {
				<#list table.columnlist as item >							
					${item.name! } : rows[0].${item.name },  // ${item.comment! }
				</#list> 				
			});
		}
	}
	//删除
	function del() {
		var ids = [];
		var rows = datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			$.messager.confirm('请确认', '您要删除当前所选项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : deleteurl,
						data : {
							ids : ids.join(',')
						},
						cache : false,
						dataType : "json",
						success : function(response) {
							datagrid.datagrid('unselectAll');
							datagrid.datagrid('reload');
							$.messager.show({
								title : '提示',
								msg : '删除成功！'
							});
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的记录！', 'error');
		}
	}
	//搜索
	function searchFun() {
		datagrid.datagrid('load', {
<#list table.columnlist as item>
		<#if item.type="String">			
			${item.name! } : $('#toolbar input[name=${item.name! }]').val(), // ${item.comment! }	
		</#if>
		<#if item.type='Date'>
			${item.name! }Start : $('#toolbar input[comboname=${item.name! }Start]').datetimebox('getValue'),
			${item.name! }End : $('#toolbar input[comboname=${item.name! }End]').datetimebox('getValue')
		</#if>
</#list> 					
		});
	}
	//清除搜索条件
	function clearFun() {
		$('#toolbar input').val('');
		datagrid.datagrid('load', {});
	}
</script>
</head>
<body class="easyui-layout" fit="true">
	<div region="center" border="false" style="overflow: hidden;">
		<!-- 工具条开始 -->
		<div id="toolbar" class="datagrid-toolbar" style="height: auto;">
			<div id="searchpanel" class="easyui-panel" title="搜索" collapsible="true" >		
			<fieldset>
				<legend>搜索</legend>				
				<table class="tableForm">
			<#list table.columnlist as column>
				<#if (column_index%6=1)>
					<tr>
				</#if>
				<#if (column.name="id")>
				<#elseif column.type='Date'>
						<th>${column.comment!}</th>
						<td ><input name="${column.name!}Start" class="easyui-datetimebox" editable="false" style="width: 150px;"/>-
						<input name="${column.name!}End" class="easyui-datetimebox" editable="false" style="width: 150px;" /></td>
				<#elseif column.type='String'>
						<th>${column.comment!}</th>
						<td ><input name="${column.name!}" style="width: 105px;"/></td>	
				</#if>						
				<#if (column_index%6=0)>
					</tr>				
				</#if>
			</#list>
					<td colspan="2"><a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchFun();" href="javascript:void(0);">搜索</a>
						<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="clearFun();" href="javascript:void(0);">清空</a></td>					
				</table>
			</fieldset>
			</div>
			<div>
			<c:if test="${r"${"}fn:contains(sessionInfo.resourceList, '/${table.lowername}/add')}">
				<a class="easyui-linkbutton" iconCls="icon-add" onclick="add();" plain="true" href="javascript:void(0);">增加</a>
			</c:if>
			<c:if test="${r"${"}fn:contains(sessionInfo.resourceList, '/${table.lowername}/delete')}">
				<a class="easyui-linkbutton" iconCls="icon-remove" onclick="del();" plain="true" href="javascript:void(0);">删除</a>
			</c:if>
			<c:if test="${r"${"}fn:contains(sessionInfo.resourceList, '/${table.lowername}/edit')}">
				<a class="easyui-linkbutton" iconCls="icon-edit" onclick="edit();" plain="true" href="javascript:void(0);">编辑</a> 	
				<a class="easyui-linkbutton" iconCls="icon-undo" onclick="datagrid.datagrid('unselectAll');" plain="true" href="javascript:void(0);">取消选中</a>			
			</c:if>	
			<c:if test="${r"${"}fn:contains(sessionInfo.resourceList, '/${table.lowername}/export')}">
				<a class="easyui-linkbutton" iconCls="icon-print" onclick="exportExcelandWord('excel');" plain="true" href="javascript:void(0);">导出Excel</a> 
				<a class="easyui-linkbutton" iconCls="icon-print" onclick="exportExcelandWord('word');" plain="true" href="javascript:void(0);">导出Word</a> 
			</c:if>		
				
			</div>
		</div>
		<!-- 工具条结束-->
		<table id="datagrid"></table>
	</div>
	
	<!-- 导出excel或者word的表单 -->
	<div style="display:none">
	 <form id='exportExcel' action="${r"${ctx}"}/${table.lowername}/export"  method="post">        
        <input id="data" name='data'  type="hidden"/> 
        <input id="type" name="type" type="hidden"/>        
    </form>  
    </div>
    <!-- 导出结束 -->
    
    <!-- 编辑对话框  -->
	<div id="userDialog" style="display: none;width:600px;overflow: hidden;">
		<form id="userForm" method="post">
			<table class="addTable">
		<#list table.columnlist as column>
				<#if (column.name="id")>
				<#elseif (column_index%3=2)>
					<#switch column.type>
					<#case "Date">
						<tr><th>${column.comment!}</th><td><input name="${column.name!}" class="easyui-datetimebox" /></td>
						<#break>
					<#case "int">
						<tr><th>${column.comment!}</th><td><input name="${column.name!}" class="easyui-numberbox" /></td>
						<#break>
					<#default>
						<tr><th>${column.comment!}</th><td><input name="${column.name!}" class="easyui-validatebox" /></td>
					</#switch>	
				<#elseif (column_index%3=0)>
					<#switch column.type>
					<#case "Date">
						<th>${column.comment!}</th><td><input name="${column.name!}" class="easyui-datetimebox" /></td></tr>
						<#break>
					<#case "int">
						<th>${column.comment!}</th><td><input name="${column.name!}" class="easyui-numberbox" /></td></tr>
						<#break>
					<#default>
						<th>${column.comment!}</th><td><input name="${column.name!}" class="easyui-validatebox" /></td></tr>
					</#switch>	
				<#else>
					<#switch column.type>
					<#case "Date">
						<th>${column.comment!}</th><td><input name="${column.name!}" class="easyui-datetimebox" /></td>
						<#break>
					<#case "int">
						<<th>${column.comment!}</th><td><input name="${column.name!}" class="easyui-numberbox" /></td>
						<#break>
					<#default>
						<th>${column.comment!}</th><td><input name="${column.name!}" class="easyui-validatebox" /></td>
					</#switch>
				</#if>
		</#list>
		<#if (table.columnlist?size%2=0)>
						</tr>
		</#if>				
			<tr> <th hidden="hidden">id</th><td><input name="id" hidden="hidden"  class="easyui-validatebox" readonly="readonly"/></td>	</tr>						
			</table>
		</form>
	</div>
	<!-- 编辑对话框结束 -->
	
	<!--行右键菜单开始 -->
	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
	<c:if test="${r"${"}fn:contains(sessionInfo.resourceList, '/${table.lowername}/add')}">
		<div onclick="add();" iconCls="icon-add">添加</div>
	</c:if>
	<c:if test="${r"${"}fn:contains(sessionInfo.resourceList, '/${table.lowername}/delete')}">
		<div onclick="del();" iconCls="icon-remove">删除</div>
	</c:if>
	<c:if test="${r"${"}fn:contains(sessionInfo.resourceList, '/${table.lowername}/edit')}">
		<div onclick="edit();" iconCls="icon-edit">修改</div>
	</c:if>
	</div>
	<!--行右键菜单结束  -->
</body>
</html>


	