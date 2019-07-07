<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html >
<html>
<head>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/webgl/css/cube.css">
<style type="text/css">

#cube {
	width: 50px;
	height: 50px;
	background-color: orange;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Input Page</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">


	$(document).ready(function(){
		
		$( "#cube" ).position({ left: 250, top: 250 });
		
	});
	
	function test(){
		alert("a");
	}
	 
	
</script>
</head>
<body>

	

	<div>
		<canvas id="canvas"></canvas>
	
	</div>

	<span onclick="javascript:test()">hello</span>

	<div id="box">
		<div id="cube"></div>
	</div>

	<!--
	for most samples webgl-utils only provides shader compiling/linking and
	canvas resizing because why clutter the examples with code that's the same in every sample.
	See http://webgl2fundamentals.org/webgl/lessons/webgl-boilerplate.html
	and http://webgl2fundamentals.org/webgl/lessons/webgl-resizing-the-canvas.html
	for webgl-utils, m3, m4, and webgl-lessons-ui.
	-->
	<script src="https://webgl2fundamentals.org/webgl/resources/webgl-utils.js"></script>
	<script src="https://webgl2fundamentals.org/webgl/resources/m4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/webgl/js/cube.js"></script>

</body>
</html>