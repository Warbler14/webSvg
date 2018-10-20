<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<style type="text/css">
	#imgFile {
		border: 2px dotted red; 
		margin-left:10px;
		margin-top:10px;
		background-color: "orange"
	}
	
	#imgView {
		border: 2px dotted green;
		margin-left:10px;
		margin-top:10px;
	}
</style>
<title>localImgPreview</title>

</head>

<body>
	<form id="form1" name="form1" enctype="multipart/form-data" >
		<div id="imgFile">
			<span>img file : </span>
			<input type='file' name="imgFile" onchange="readURL(this);" />
			
			 <a href="#this" class="btn" id="write">작성하기</a>
			 
			 &nbsp;&nbsp;
			 
			 <a href="#this" class="btn" id="ajaxTest">ajax</a>
			 
			 &nbsp;&nbsp;
			 
			 <a href="#this" class="btn" id="upload">ajax파일 보내기</a>
			 
		</div>
		
		<div id="imgView">
			<img id="blah" src="#"	alt="your image" />
		</div>
		
	</form>
	
	
	<c:forEach var="item" items="${imgFileList}">
		<tr align="center" background="" onmouseover="this.style.backgroundColor='SkyBlue'" onmouseout="this.style.backgroundColor=''" >
			<td>${item.fileName}</td>
			<td>${item.savePath}</td>
<%-- 
			<td><fn:formatDate value="${item.bdate}" pattern="yy-MM-dd aahh:mm:ss" /></td>
 --%>	
		</tr>
	</c:forEach>
	
	
	<P>  testvalue ${testvalue}. </P>
	
</body>

<script type="text/javascript" src="./resources/js/jquery-2.1.1.min.js"></script>
<script src="<c:url value='./resources/js/common.js'/>" charset="utf-8"></script>
<script type="text/javascript">
	var console = window.console || {log : function() {}};

	$(document).ready(function() {
		console.log("localImgPreview load");

		$("#write").on("click", function(e) {
			e.preventDefault();
			fn_insertBoard();
		});
		
		$("#upload").on("click", function(e) {
			e.preventDefault();
			ajaxFormUpload();
		});
		
		$("#ajaxTest").on("click", function(e) {
			e.preventDefault();
			ajaxJS();
		});
	});

	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#blah').attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	function getContextPath() {
		var hostIndex = location.href.indexOf( location.host ) + location.host.length;
		return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
	}
	
	function fn_insertBoard() {
		var comSubmit = new ComSubmit("form1");
		comSubmit.setUrl("<c:url value='/imgUploadTest' />");
		comSubmit.submit();
	}
	
	function ajaxJS(){
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
	

	function ajaxFormUpload() {
		var form = $('#form1')[0];
		var formData = new FormData(form);
		var formUrl = "<c:url value='/imgUploadTestAjax' />";
		
		$.ajax({
			url : formUrl,
			processData : false,
			contentType : false,
			data : formData,
			type : 'POST',
			success : function(result) {
				alert("업로드 성공!!");
			}
		});
	}
</script>
</html>
