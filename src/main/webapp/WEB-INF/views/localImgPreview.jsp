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

<script type="text/javascript" src="./resources/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	var console = window.console || {log : function() {}};

	$(document).ready(function() {
		console.log("localImgPreview load");
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
</script>
</head>
<!-- 
<form id="form1" runat="server">
 -->
 
<body>
	<form id="form1" >
		<div id="imgFile">
			<span>img file : </span>
			<input type='file' onchange="readURL(this);" /> 
		</div>
		
		<div id="imgView">
			<img id="blah" src="#"	alt="your image" />
		</div>
		
	</form>
</body>
</html>
