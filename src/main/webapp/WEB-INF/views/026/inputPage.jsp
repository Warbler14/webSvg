<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="colorpicker.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">

	#body01 {
		width: 1000px;
		height:1000px;
		background-color: #d6f0d6;
	
	}
	
	.area01 {
	    width: 490px;
	    height: 300px;
	    background-color: #d6f0d6;
	    border: 1px solid #48BAE4;
	    padding: 1px;
	    float: left;
	    margin-top: 4px;
	    margin-left: 4px;
	}

	#input_control {
		background-color: gray;
		width: 200px;
		height: 100px;
		margin-top: 20px;
	}

	#frm input[type=text] {
		width: 50px;
	}

	#matrix {
		margin-left: 10px;
		margin-top: 10px;
	}

	#matrix tr td{
		border: 1px solid black;
		text-align: center;
	}
	
	
	
	
	#widget01 {
		
		position: relative;
		margin-left: 50px;
		margin-top: 10px;
		

	}
	
	#widget01 span{
		/* 
		margin-left: 50px;
		 */
	}
	
	.selectedOne {
		
		background-color: black;
	}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Input Page</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="./resources/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="./resources/js/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">
	const x_limit = 800;
	const y_limit = 600;
	var x_pos1 = 100;
	var y_pos1 = 300;
	var x_pos2 = 500;
	var y_pos2 = 300;
	
	var r1 = 20;
	var r2 = 50;
	var angle1 = 0;
	var angle2 = 0;
	
	var add1 = 10;
	var add2 = 20;
	
	var selectedBoxId = "item_0_0";
	
	$(document).ready(function(){
		
		buildTable();
		
		//$("#" + selectedBoxId ).addClass("selectedOne");
		
	});
	
	
	
	
	function setThisOne( obj ){
		
		$("#" + selectedBoxId ).removeClass("selectedOne");
		
		selectedBoxId = $(obj).attr("id");
		
		//alert(selectedBoxId);
		
		$("#" + selectedBoxId ).addClass("selectedOne");
		
	}
	
	function setThisColor( obj ){
		
		getHexCode();
		
		var rgb = $("#widget01_color").css("background-color");
		var id = $(obj).attr("id");
		$("#" + id ).css( "background-color", rgb );
		
		console.log( "rgb : " + rgb );
		console.log( "id : " + id );
		
		var dataArr = id.split("_");
		
		if( dataArr.length == 3 ){
			var hex = rgb2hex(rgb);
			
			//console.log( hex );
			
			$("#data_" + dataArr[1] + "_" + dataArr[2] ).val( hex );
			
			console.log( $("#data_" + dataArr[1] + "_" + dataArr[2] ).val() );
			
		}else{
			
			console.log("========debug==========");
			for( var i = 0 , ii = dataArr.length ; i<ii ; i++ ){
				console.log( dataArr[i] );
			}
			
		}
		
	}
	
	function buildTable(){
		var boxWidth = parseInt( $("#boxWidth").val() );
		var boxHeight = parseInt($("#boxHeight").val() );
		var countX = parseInt($("#countX").val() );
		var countY =parseInt( $("#countY").val() );
		
		var html = "";
		
		html += "<table id='matrix'>";
		for( var i = 0 ; i < countY ; i++ ){
			html += "<tr>";
			for( var j = 0 ; j < countX ; j++ ){
				html += "<td style='width:"+ boxWidth +";height:"+ boxHeight +";' id='item_"+i+"_"+j+"' onclick='javascript:setThisColor(this);' >";
				
				html += "<input type='hidden' id = 'data_"+i+"_"+j+"' name='data_"+i+"_"+j+"' value='#ffffff' />"
				
				html += "<span>" + i +":" + j + "</span>"
				
				html += "</td>";
			}
			html += "</tr>";
		}
		html += "</table>";
		
		$("#tableArea").html(html);
	}
	
	
	function getImage(){
		var contextpath = getContextPath();
		
		var boxWidth = parseInt( $("#boxWidth").val() );
		var boxHeight = parseInt($("#boxHeight").val() );
		var countX = parseInt($("#countX").val() );
		var countY =parseInt( $("#countY").val() );
		
		var data = loadData();
		
		data = data.replace(/#/gi, "_");
		data = data.replace("_", "");
		
		//alert( contextpath );
		
		var img = contextpath + "/drawColorMatrix?cmd=drawImage"
				+ "&boxWidth="+ boxWidth +"&boxHeight=" + boxHeight
				+ "&countX="+ countX +"&countY=" + countY 
				+ "&data="+ data
				;
		
		alert(img);
		
		$( "#img01" ).attr( "src" , img );
	}
	
	function getHexCode(){
		var rgb = $("#widget01_color").css("background-color");
		var hex_code = rgb2hex( rgb );
		//alert( hex_code );
		$("#widget01 #hex_code").text( hex_code );
		
		//$("#" + selectedBoxId ).css("background-color", rgb);
		
		
	}
	
	function rgb2hex(rgb) {
	     if (  rgb.search("rgb") == -1 ) {
	          return rgb;
	     } else {
	          rgb = rgb.match(/^rgba?\((\d+),\s*(\d+),\s*(\d+)(?:,\s*(\d+))?\)$/);
	          function hex(x) {
	               return ("0" + parseInt(x).toString(16)).slice(-2);
	          }
	          return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
	     }
	}

	function loadData(){
		var countX = parseInt($("#countX").val() );
		var countY =parseInt( $("#countY").val() );
		var data = "";
		
		for( var i = 0 ; i < countY ; i++ ){
			for( var j = 0 ; j < countX ; j++ ){
				
				data += $("#data_"+i+"_"+j).val();
				
			}
		}
		
		return data;
	}
	
	function getContextPath() {
		return "<c:out value="${pageContext.request.contextPath}" />";
	}
	
</script>
</head>
<body>
	
	<div id="body01">
		<form id="frm" action="<%=request.getContextPath()%>/drawColorMatrix" method="POST">
		
		<div class="area01">
			<div id="tableArea"></div>
		</div>
		
		<div class="area01">
			<div id="input_control">
				<span>boxWidth : </span> <input type="text" id="boxWidth"  name = "boxWidth"  value="25" maxlength="3"/><br/>
				<span>boxHeight : </span><input type="text" id="boxHeight" name = "boxHeight" value="25" maxlength="3"/><br/>
				<span>countX : </span>   <input type="text" id="countX"    name = "countX"    value="10" maxlength="3"/><br/>
				<span>countY : </span>   <input type="text" id="countY"    name = "countY"    value="10" maxlength="3"/><br/>
				
				<input type="button" onclick="javascript:buildTable();" value="buildTable"/>
				<input type="button" onclick="javascript:getImage();" value="getImage"/>
			</div>
		</div>
		</form>
		
		<div class="area01">
			
			<div id="show_box">
			
				<img id = "img01" src="<%=request.getContextPath()%>/drawColorMatrix?cmd=drawImage&boxWidth=10&boxHeight=10&countX=10&countY=10&data="/>
			
			</div>
			
		</div>
		
		<div class="area01">
			
			<div id="widget01">
				<div id="colorSelector2"><div id="widget01_color" style="background-color: #00ff00"></div></div>
					<div id="colorpickerHolder2">
				</div>
				<span>hex code : </span><span id="hex_code">#00ff00</span>
				<input type="button" onclick="javascript:getHexCode();" value="getHexCode"/>
			</div>
			
		</div>
	</div>


</body>
</html>