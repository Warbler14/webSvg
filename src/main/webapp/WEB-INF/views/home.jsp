<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>


<span>
	<a href="/websvg/localImgPreview"  >localImgPreview</a><br/>
	<a href="/websvg/dbTest"  >dbTest</a><br/>
	<a href="/websvg/jsonTest"  >jsonTest</a><br/>
</span>

<br/>

<span>
할 일 : ajax 로 파일 업로드 후 이미지 표시 주소 획득
</span>

</body>
</html>
