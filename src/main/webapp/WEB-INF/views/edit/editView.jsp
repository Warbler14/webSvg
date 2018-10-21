<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/common.jsp" %>

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
<title>editView.jsp</title>

</head>

<body>
		<form id="form1" name="form1" enctype="multipart/form-data" >
		
		<div id="imgFile">
			<span>img file : </span>
			<input type='file' name="imgFile" onchange="readURL(this);" />
			
			 <a href="#this" class="btn" id="write">작성하기</a>
			 
			 &nbsp;&nbsp;
			 
			 <a href="<%=request.getContextPath()%>/edit/editList"  >목록</a>
			 
			 &nbsp;&nbsp;
			 
			 <a href="#this" class="btn" id="upload">ajax파일 보내기</a>
			 
		</div>
		
		<div id="imgView">
			
			<img id="blah" src=""	alt="your image" />
			
		</div>
		
	</form>
	1. 목록 페이지 생성<br/>
		>목록 테이블 추가<br/>
		>저장된 db 데이터 호출<br/>
		>저장된 db 데이터로 목록 출력<br/>
		>
		
	2. 등록/조회 버튼으로 페이지 호출<br/>
		>이미지 n개 동적 등록<br/>
		
	3. 각 순서에 따라 n개 파일 업로드<br/>
		>각 파일 업로드<br/>
		>각 파일 합성<br/>
		
	
		<span>testvalue :  ${testvalue}</span>
	<span> id : ${id}</span>
	
	<br/><br/>
	
	
	
	
</body>

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