<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/common.jsp" %>

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
	<a href="<%=request.getContextPath()%>/viewSvgImg"  >viewSvgImg</a><br/>
	<a href="<%=request.getContextPath()%>/viewLocalImg"  >viewLocalImg</a><br/>
	<a href="<%=request.getContextPath()%>/viewMixSvgJpgImg"  >viewMixSvgJpgImg</a><br/>
	<a href="<%=request.getContextPath()%>/viewMixSvgPngImg"  >viewMixSvgPngImg</a><br/>
	
	<br/>
	
	<a href="<%=request.getContextPath()%>/edit/editList"  >editList</a><br/>
	<a href="<%=request.getContextPath()%>/edit/editView"  >editView</a><br/>
	<a href="<%=request.getContextPath()%>/dbTest"  >dbTest</a><br/>
	<a href="<%=request.getContextPath()%>/jsonTest"  >jsonTest</a><br/>
	<a href="#this" class="btn" id="ajaxTest">ajaxTest</a>
	
	
	
</span>

<br/>

<hr>
<span>
info
</span>
<hr>


<span>context path : <%=request.getContextPath()%></span>
<hr>

<br/>
<br/>

<span>
할 일 : ajax 로 파일 업로드 후 이미지 표시 주소 획득
</span>

</body>

<script type="text/javascript">
	var console = window.console || {log : function() {}};

	$(document).ready(function() {
		console.log("localImgPreview load");
		
		$("#ajaxTest").on("click", function(e) {
			e.preventDefault();
			ajaxTest();
		});
	});
	
	function ajaxTest(){
		/* var id = $("#id").val(); */
		
		var ajaxUrl = "<c:url value='/jsonTest' />";
		console.log("test start");
		
		
		$.ajax({
			url:ajaxUrl,
			dataType:"json",	
			Type:"post",
			data:{
				send:"123a"
			},
			success : function(data) {
				console.log("name : " + data.name ) ;
				console.log("str : " + data.str ) ;
				console.log("number : " + data.number ) ;
			}
		});
		
		console.log("test end");
	}
	
</script>
</html>
