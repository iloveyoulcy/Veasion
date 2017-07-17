<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	
	var urlBase64;
	
	function faceImage(){
		var img = $("#img").val();
    	var pattern = /\.*.(jpg|png|gif)$/;
    	if(img=="" || !pattern.test(img)){
    		alert("图片不能为空或格式错误！（只能上传jpg/png/gif格式）");
    		return false;
    	}
    	$("#loading").show();
    	$.ajax({
			url:"${pageContext.request.contextPath}/photo/text/textFile.vea",
			data:{"base64Url":urlBase64},
			type:"post",
			//dataType:"json",
			success:function(data) {
				$("#loading").hide();
				//alert(data.object);
				$("#textHtml").html(data.object);
			},
			error:function(e){
				$("#loading").hide();
				alert("发生异常！");
			}
    	});
	}
	function getBase64Image(img) {
        var canvas = document.createElement("canvas");
        canvas.width = img.width;
        canvas.height = img.height;
        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0, img.width, img.height);
        var dataURL = canvas.toDataURL("image/png");
        return dataURL;
    }
	
	function imgChange(obj){
		var file=obj.files[0];
		var img = new Image();
		var url=URL.createObjectURL(file);
		img.src=url;
		img.onload = function(){
			var width=img.width,height=img.height;
			if((width<48||height<48) || (width>800||height>800)){
				$("#message").text("你的图片("+width+"*"+height+")不合适！图片像素尺寸：最小48*48像素，最大800*800像素");
			}else{
				$("#message").text("");
			}
			urlBase64=getBase64Image(img);
			$("#imgurl").attr("src",urlBase64);
		}
	}
</script>
</head>
<body>
<div style="text-align: center;margin-top: 50px;">
	<div style="float: left;width: 600px;min-height: 600px;border: 1px solid #666;margin-left: 400px;">
		<br/><br/>
		<span>文字识别图片</span>
		<div id="div">
	    	<img alt="预览" src="" id="imgurl" style="margin-top: 20px;border:2px solid pink;"/><br/>
	    	<span style="color: #666;">（图片像素尺寸：最小48*48像素，最大800*800像素）</span><br/>
	    	<span style="color:red;" id="message"></span>
	    	<input id="img" name="file" type="file" onchange="imgChange(this);"/>
	    </div>
	</div>
	<div style="float: left;width: 20%;min-height: 600px;border: 1px solid #666;margin-left: 20px;text-align: center;">
		<h3>识别结果</h3>
		<div id="textHtml" style="margin-top: 10px;min-width: 200px;min-height: 300px;border: 1px solid #666;" contenteditable="true"></div>
		<img alt="正在识别..." src="${pageContext.request.contextPath}/images/loading.gif" style="display: none;" id="loading"/>
		<br/>
		<button onclick="faceImage();" style="margin-top: 10px;">开始识别</button>
	</div>
</div>
</body>
</html>