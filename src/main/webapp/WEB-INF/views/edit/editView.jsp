<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/common.jsp" %>

<html>
<head>
<style type="text/css">

	#svgText {
		border: 2px dotted pink; 
		margin-left:10px;
		margin-top:10px;
		background-color: "orange"
	}
	
	#svgText  textarea {
		align-content:center;
		overflow:auto; 
	}

	#imgFile {
		border: 2px dotted red; 
		margin-left:10px;
		margin-top:10px;
		background-color: "orange"
	}
	
	#imgViewLeft {
		float: left;
		width : 48.5%;
		border: 2px dotted gold;
		margin-left:10px;
		margin-top:10px;
	}
	
	#imgViewRight {
		float: left;
		width : 48.5%;
		border: 2px dotted green;
		margin-left:10px;
		margin-top:10px;
	}
	
	#imgViewEnd{
		clear:both;
	}
	
	#footer {
		margin-left:10px;
		margin-top:10px;
		border: 2px dotted black;
	}
</style>
<title>editView.jsp</title>

</head>

<body>
	<form id="form1" name="form1" enctype="multipart/form-data" >
		
		<div id="svgText">
			<textarea id="svgTextarea"  name="svgText" rows="20" cols="100">

			</textarea>
			<br/>
			
			<span>svgPosX</span>&nbsp;&nbsp;<input type="text" id="svgPosX" name="svgPosX" value="10"/><br/>
			<span>svgPosY</span>&nbsp;&nbsp;<input type="text" id="svgPosY" name="svgPosY" value="10"/><br/>
		</div>
		
		<div id="imgFile">
			<span>img file : </span>
			<input type='file' name="imgFile"  id="imgFile" onchange="readURL(this);" />
			
			<%--  
			<a href="#this" class="btn" id="write">작성하기</a>
			&nbsp;&nbsp;
			
			<%--  
			<a href="<%=request.getContextPath()%>/edit/editList"  >목록</a>
			&nbsp;&nbsp;
			 --%>
			
			<a href="#this" class="btn" id="upload">전송</a>
			
		</div>
		
		<div id="imgViewLeft">
			
			<img id="blah"  src="" alt="upload image" />
			
		</div>
		<div id="imgViewRight">
			
			<img id="resultImg"  src=""  alt="result image" />
			<div id="resultLinkArea"></div>
			
		</div>
		<div id="imgViewEnd"></div>
	</form>
	
	<div id="footer">
	<%-- 	<span>testvalue :  ${testvalue}</span> --%>
		<span> id : ${id}</span>
		
	
	
	
	</div>
	
	
	
</body>

<script type="text/javascript">
	var console = window.console || {log : function() {}};

	$(document).ready(function() {
		console.log("localImgPreview load");

//		$("#write").on("click", function(e) {
//			e.preventDefault();
//			fn_insertBoard();
//		});
		
		$("#upload").on("click", function(e) {
			e.preventDefault();
			ajaxFormUpload();
		});
		
		console.log("appendSampleSvg");
		appendSampleSvg();
		
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
				//alert("업로드 성공!!");
				console.log( "업로드 성공!!" );
				
				var resultObj = JSON.parse(result);
				
				setImg( resultObj.fileName )
				
				$("#blah").attr("src", "");
			}
		});
	}
	
	function appendSampleSvg(){
		$("#svgTextarea").load( getContextPath() +"/resources/svg/sample.svg" );
	}
	
	function setImg( resource ){
		var path = getContextPath() +"/resources" + resource;
		
		//alert( path );
		console.log( path );
		
		$('#resultImg').attr('src', path );
		
		var link = "<span>" + resource + "</span>";
		console.log( link );
		
		$("#resultLinkArea").empty();
		$("#resultLinkArea").append( "<span>" + resource + "</span>" );
		
	}
	
	function clean() {
		$("#imgViewRight").empty();
	}
	
</script>
</html>