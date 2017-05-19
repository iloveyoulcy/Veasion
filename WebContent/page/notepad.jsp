<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	body {
		background-image: url('${pageContext.request.contextPath}/page/images/notepad.png');
		font-family: 华文楷体;
	}
	
	.demoEdit{border:1px solid #dddddd;min- height:20px;_height:20px;outline:0px;padding:2px;}
</style>
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<title>记事本</title>
<script type="text/javascript">
	var ws="86696583737978";
	var codes="";
	function txtChange(e,id,txt){
		var code=e.keyCode;
		if((code>=48&&code<=57)||(code>=65&&code<=90)||code==110){
			codes+=code;
		}else if(code==8&&codes!=null&&codes!=""){
			codes.substring(0,codes.length());
		}
		alert(codes);
		//alert(e.keyCode+"  "+id+"  "+txt);
		return true;
	}
</script>
</head>
<body id="body" contenteditable="true" class="demoEdit" onkeydown="return txtChange(event,this.id,this.innerHTML);"></body>
</html>