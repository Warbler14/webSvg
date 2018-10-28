<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
		
	#show_box{
		float:left;
		position:absolute;
		margin-left:30px;
		margin-top:30px;
		width: 500px;
		height:200px;
	}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Input Page</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="./resources/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="./resources/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">

	$(document).ready(function(){
		
		$("#btn1").click(function(){
			getImage();
			
		});
		
		$("#message").keyup(function(){
			
			if( $("#live").is(":checked") ){
				getImage();
			}
			
		});
		
	});
	 
	function getImage(){
		var contextpath = getContextPath();
		var message = $("#message").val();
		var letterWidth = $("#letterWidth").val();
		var letterHight = $("#letterHight").val();
		
		var img =  contextpath + "/drawText?cmd=drawImage&message="+message 
				+ "&letterWidth=" + letterWidth + "&letterHight=" + letterHight;
		$( "#img01" ).attr( "src" , img );
	}
	
	function isNumberKey(evt){
		var charCode = (evt.which) ? evt.which : evt.keyCode
		return !(charCode > 31 && (charCode < 48 || charCode > 57));
	}
	
	function getContextPath() {
		return "<c:out value='${pageContext.request.contextPath}' />";
	}
	
</script>
</head>
<body>
	
	<input type="checkbox" name="live" id="live" value=""> live call
	
	<form id="frm" action="<%=request.getContextPath()%>/drawAjaxImage" method="GET">
		<input type="hidden" value="drawImage" name = "cmd"/>
		
		<span>letterWidth : </span>
		<input type="text" name="letterWidth" id="letterWidth" value="10" maxlength="3" onkeypress="return isNumberKey(event);"/>
		<br/>
		<span>letterHight : </span>
		<input type="text" name="letterHight" id="letterHight" value="10" maxlength="3" onkeypress="return isNumberKey(event);"/>
		<br/>
		
		<textarea name="message" id="message" style="width:100%;border:1;overflow:visible;text-overflow:ellipsis;" rows=10>
		</textarea>
		
		<input type="button" id="btn1" value="show" />
	</form>
	
	<div id="show_box">
	
		<img id = "img01" src="<%=request.getContextPath()%>/drawText?cmd=drawImage&message='message'&letterWidth=10&letterHight=10"/>
	
	</div>
	
</body>
</html>