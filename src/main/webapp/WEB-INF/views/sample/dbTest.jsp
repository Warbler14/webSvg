<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/common.jsp" %>
<html>
<head>
	<title>Db test</title>
</head>
<body>
<h1>
	Db test
</h1>

<P>  DB count : ${count}. </P>


<span>
	<a href="<%=request.getContextPath()%>"  >home</a><br/>
</span>


</body>
</html>
