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
		</div>
		
		<div id="imgView">
			<img id="blah" src="#"	alt="your image" />
		</div>
		
	</form>
</body>

<script type="text/javascript" src="./resources/js/jquery-2.1.1.min.js"></script>
<script src="<c:url value='./resources/js/common.js'/>" charset="utf-8"></script>
<script type="text/javascript">
	var console = window.console || {log : function() {}};

	$(document).ready(function() {
		console.log("localImgPreview load");

		$("#write").on("click", function(e) { //작성하기 버튼
			e.preventDefault();
			fn_insertBoard();
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
		comSubmit.setUrl("<c:url value='/sample/insertBoard.do' />");
		comSubmit.submit();
	}
</script>
</html>
