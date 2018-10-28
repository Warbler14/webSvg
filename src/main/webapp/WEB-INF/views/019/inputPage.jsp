<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	#checkBox tr td{
		border: 1px solid orange;
		background-color: black;
	}
	
	.box{
		width: 20px;
		height:20px;

	}
	
	/* 
	#show_box{
		float:left;
		position:absolute;
		margin-left:30px;
		margin-top:30px;
		width: 500px;
		height:200px;
	}
	 */
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
		var size = 20;
		for(var i = 0 , ii = 20 ; i<ii ; i++ ){
			for(var j = 0 , jj = 20 ; j<jj ; j++ ){
				
				
				var img = contextpath + "/drawRandomColor?cmd=drawImage&color=" + getRandomColor() + "&size=" + size;
				$( "#img_" + i + "_" + j ).attr( "src" , img );
				
			}
		}
	}
	
	function getRandomColor(){
		var color = "";
		for( var i = 3 ; i>0 ; i-- ){
			var rgbNum = Math.floor(Math.random() * 255) + 1;
			
			color = color + rgbNum;
			
			if( i > 1 ){
				color  = color + "_";
			}
		}
		return color;
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
	
	<form id="frm" action="<%=request.getContextPath()%>/drawRandomColor" method="GET">
		<input type="hidden" value="drawImage" name = "cmd"/>
		
		<input type="button" id="btn1" value="load" />
	</form>
	
	<div id="show_box">
		<div>
			<table id="checkBox">
			<c:forEach var="i" begin="0" end="19" step="1">
				<tr>
				<c:forEach var="j" begin="0" end="19" step="1">
					<td id="div_${i}_${j}">
						<div class="box">
							<c:set var="rand1"><%= java.lang.Math.floor(java.lang.Math.random() * 255) + 1 %></c:set>
							<c:set var="rand2"><%= java.lang.Math.floor(java.lang.Math.random() * 255) + 1 %></c:set>
							<c:set var="rand3"><%= java.lang.Math.floor(java.lang.Math.random() * 255) + 1 %></c:set>
							
							<img id = "img_${i}_${j}" src="<%=request.getContextPath()%>/drawRandomColor?cmd=drawImage&color=${rand1}_${rand2}_${rand3}&size=20"/>
						</div>
					</td>
				</c:forEach>
				</tr>
			</c:forEach>
			</table>
		</div>
		
	
	</div>
	
</body>
</html>