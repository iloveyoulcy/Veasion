<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<style type="text/css">
.icon{width: 36px;height: 36px;}
.bgimg{width: 420px;}
</style>
<script type="text/javascript">
	
	var type="${param.type}";
	
	function check(){
		var img = $("#img").val();
    	var pattern = /\.*.(jpg|png|gif)$/;
    	if(img=="" || !pattern.test(img)){
    		alert("图片不能为空或格式错误！（只能上传jpg/png/gif格式）");
    		return false;
    	}
    	$("#loading").show();
    	return true;
	}
	function imgChange(obj){
		var file=obj.files[0];
		var img = new Image();
		var url=URL.createObjectURL(file);
		img.src=url;
		img.onload = function(){
			var width=img.width,height=img.height;
			//1900 1200
			if(type=="bgimg" && (width<1900||height<1200)){
				$("#message").text("背景图片("+width+"*"+height+")不是最合适的！建议1920*1200");
			}else if(type=="icon" && (width<36||height<36)){
				$("#message").text("图标("+width+"*"+height+")不是最合适的！建议36*36");
			}else{
				$("#message").text("");
			}
		}
		$("#imgurl").prop("src",url);
		$("#imgurl").prop("class",type);
		$("#filePath").val($("#img").val());
	}
</script>
</head>
<body>
<br/><br/>
	<c:if test="${param.type=='bgimg'}">
		上传背景：
	</c:if>
	<c:if test="${param.type=='icon'}">
		上传图标：
	</c:if>
	<br/>
	 <div id="div" style="border:2px solid pink;display: inline-block;margin-top: 5px;margin-bottom: 5px;padding: 2px;">
    	<img alt="预览" src="" id="imgurl"/>
    </div>
    <br/>
    <span style="color:red;" id="message"></span>
	<br/>
	<img alt="正在上传..." src="${pageContext.request.contextPath}/images/loading.gif" style="display: none;" id="loading"/>
	<br/>
	<c:if test="${param.type!=null}">
		<form action="${pageContext.request.contextPath}/admin/desktop/upFile.vea?type=${param.type }" method="post" enctype="multipart/form-data" onsubmit="return check();">
	        <input id="img" name="file" type="file" onchange="imgChange(this);"/><br/>
	                   文件命名：
	        <input name="name" type="text" value="" title="默认文件名"/><br/>
	        <input id="filePath" name="filePath" type="hidden" value=""/>
	        <input type="submit" value="开始上传" />
	    </form>
    </c:if>
    <br/>
    
</body>
</html>