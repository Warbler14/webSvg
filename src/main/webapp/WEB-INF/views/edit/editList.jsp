<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/common.jsp" %>

<html>
<head>
<style type="text/css">

	#top {
		background-color:gray;
		height: 50px;
	}
	
	#middle {
		background-color:yellow;
		
	}
	
	#bottom {
		background-color:pink;
		height: 50px;
	}

	#form1 {
		margin-bottom: 0em;
	}

	#tableSvgList {
		position: relative;
		margin-left: 30px;
	}
	
	#tableSvgList th{
		/* height: 30px; */
	}
	
	#tableSvgList  tr th{
		border: 1px solid black;
	}
	
	#tableSvgList  tr td{
		border: 1px solid black;
	}
	
	#tableSvgList  tr td:nth-child(1) {
		 width: 150px;
		background-color:red;
	}
	
	#tableSvgList  tr td:nth-child(2) {
		width: 300px;
		background-color:green;
	}
	#tableSvgList  tr td:nth-child(3) {
		width: 200px;
		background-color:orange;
	}
	#tableSvgList  tr td:nth-child(4) {
		width: 100px;
		background-color:green;
	}
	
</style>
<title>editList.jsp</title>

</head>

<body>
	
	<div id="top">
		<a href="#this" class="btn" id="ajaxTest">ajax</a>
	</div>
	
	<div id="middle">
		
		<form id="form1" name="form1" enctype="multipart/form-data" >
	
		
			<table  id="tableSvgList" >
				<tr>
					<th>이름</th>
					<th>경로</th>
					<th>저장 시간</th>
					<th>수정</th>
				</tr>
				<c:forEach var="item" items="${imgFileList}">
					<tr align="center" >
						<td>${item.fileName}</td>
						<td>${item.savePath}</td>
						<td>${item.saveDate}</td>
						<td><a href="#this" class="btn" id="goEditview" onclick="javascript:goEditview('${item.fileName}')">작성하기</a>
						
					</tr>
				</c:forEach>
			</table>
			
			
		</form>
		
		<form id="form2" name="form2" enctype="multipart/form-data" ></form>
		
	</div>
	
	
	
	
	<div id="bottom">
		<span>  testvalue ${testvalue}. </span>
		<span>
			<a href="<%=request.getContextPath()%>"  >home</a><br/>
		</span>
	
	</div>
	


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

	function goEditview( fileName ) {
		var comSubmit = new ComSubmit("form2");
		comSubmit.setUrl("<c:url value='/edit/editView' />");
		comSubmit.addParam("id", fileName);
		comSubmit.submit();
	}
	
	function ajaxTest(){
		
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
