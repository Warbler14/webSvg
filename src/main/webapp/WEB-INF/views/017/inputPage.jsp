<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
	#cube{
		width: 50px;
		height:50px;
		background-color: white;
	}
	
	#box{
		float:left;
		position:absolute;
		margin-left:50px;
		margin-top:50px;
		width: 500px;
		height:500px;
		background-color: black;
	}
	
	#box after{
		clear:both;
	}
	
	#show_box{
		float:left;
		position:absolute;
		margin-left:600px;
		margin-top:30px;
		width: 500px;
		height:500px;
	}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Input Page</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="./resources/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="./resources/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">

	$(document).ready(function(){
		
		$( "#cube" ).position({ left: 250, top: 250 });
		
		$( "#cube" ).draggable({
			drag: function(){
				
				var pos = $(this).position();
				var xPos = pos.left;
				var yPos = pos.top;
				var pageCoords = "( " + xPos + ", " + yPos + " )";
				$( "span:first" ).text( "( X, Y ) : " + pageCoords );
				$("#x_pos").val( xPos );
				$("#y_pos").val( yPos );
				
				if( $("#live").is(":checked") ){
					getImage();
				}
			}
			
			
			
			
		});
		
		$("#btn1").click(function(){
			getImage();
			
		});
		
	});
	 
	function getImage(){
		var contextpath = getContextPath();
		var x_pos = $("#x_pos").val();
		var y_pos = $("#y_pos").val();
		
		var img = contextpath +"/drawAjaxImage?cmd=drawImage&x_pos="+x_pos+"&y_pos=" + y_pos;
		$( "#img01" ).attr( "src" , img );
	}
	
	function getContextPath() {
		return "<c:out value='${pageContext.request.contextPath}' />";
	}
	
</script>
</head>
<body>
	
	<p>
		<span>Move the mouse over the div.</span>
		<span>&nbsp;</span>
	</p>
	
	<input type="checkbox" name="live" id="live" value=""> live call
	
	<form id="frm" action="<%=request.getContextPath()%>/drawAjaxImage" method="GET">
		<input type="hidden" value="drawImage" name = "cmd"/>
		<input type="hidden" value="0" name = "x_pos" id="x_pos"/>
		<input type="hidden" value="0" name = "y_pos" id="y_pos"/>
			
		<div id="box">
			<div id="cube">
			</div>
		</div>
		
		<!-- <input type="submit" value="Submit form "/> -->
		<input type="button" id="btn1" value="show" />
	</form>
	
	<div id="show_box">
	
		<img id = "img01" src="<%=request.getContextPath()%>/drawAjaxImage?cmd=drawImage&x_pos=0&y_pos=0"/>
	
	</div>
	
</body>
</html>