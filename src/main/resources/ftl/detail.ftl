<%@ page language="java" contentType="text; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/shared/taglib.jsp"%>

<html >
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8"/>
<script>
</script>
</head>

<body>
<form action="http://jinfang-pc:8080/TVMLog/test" >
<h1>${table.comment}测试</h1>
<table>
<c:forEach var="item" items="${table.columnlist}" >

<tr><td>${item.comment}</td><td><c:out value="${" /> ${table.lowername}.${item.name} <c:out value="}" /></td></tr>
</c:forEach>
<tr><td></td><td><input type="submit" value="提交" /></td></tr>
</form>
</body>

</html>
	