<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>人脸识别</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/favicon.ico">
<link href="${pageContext.request.contextPath}/jquery/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css"/>
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script> 
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<style type="text/css">
	#canvas,#video {
		background: #fff;
	}
	.box {
		margin-top: 20px;
		width: 100%;
		height: 100%;
	}
	.div13{
		width: 15%;
		height:500px;
		float: left;
		border: 1px solid #666;
	}
	.jtImg{
		display: none;
		width:100%;
		margin-top: 50px;
		cursor: pointer;
	}
</style>
</head>
<body>
<div class="box">
	<div class="div13" id="htmlDiv" style="width: 18%;padding-top: 15px;margin-left: 5px;">
		<div style="text-align: center;">左边</div>
	</div>
	<div style="width: 60%;height:80%;float: left;text-align: center;">
		<video id="video"></video>
	</div>
	<div class="div13" style="text-align: center;">
		<img alt="照片" src="" id="jtImg" class="jtImg" title="点击下载照片" onclick="downImg();"/>
		<img id="loading" src="${pageContext.request.contextPath}/images/loading.gif" style="display: none;margin-top: 10px;" alt="正在评估..." title="正在评估..." />
		<div style="margin-top: 30px;">
			<button id="pictures">拍照</button>
			<button style="margin-left: 5px;" onclick="upImgFile();" title="现在是看脸的时代，快来让我帮你评估一下长相吧~">长相评估</button>
		</div>
	</div>
</div>
<canvas id="canvas" style="display: none;"></canvas>
<script type="text/javascript">
	var video = document.getElementById('video');
	var canvas = document.getElementById('canvas');
	var ctx = canvas.getContext('2d');
	var jtBase64Url;//保存截图的base64编码url

	var error=function(){
		// 打开视频异常出现的回调
		var message="您浏览器没有摄像头或本站点没权限读取摄像头，建议用最新火狐浏览器访问~";
		 $.ligerDialog.warn(message);
		console.log(message);
	}
	
	//获取媒体对象(摄像头)
	navigator.getUserMedia= navigator.getUserMedia ||
						    navigator.webkitGetUserMedia ||
						    navigator.mozGetUserMedia ||
						    navigator.msGetUserMedia;
	// 打开摄像头获取视频流
	navigator.getUserMedia(
		{ "video": true }, 
		function(stream){
			var URL= window.URL || window.webkitURL;
			video.src = URL.createObjectURL(stream);
			video.play();
			useClick();
	}, error);
	
	/*
	if (navigator.getUserMedia) { // 标准的API
		navigator.getUserMedia({
			"video" : true
		}, function(stream) {
			video.src = window.URL.createObjectURL(stream);;
			video.play();
			useClick();
		}, error);
	} else if (navigator.webkitGetUserMedia) { // WebKit 核心的API
		navigator.webkitGetUserMedia({
			"video" : true
		}, function(stream) {
			video.src = window.webkitURL.createObjectURL(stream);
			video.play();
			useClick();
		}, error);
	}*/
	
	// 绑定截图点击事件
	function useClick(){
		//点击截图 
		$("#pictures").click(function(){
			var width = $("#video").width();
			var height = $("#video").height();
			canvas.width = width;
			canvas.height = height;
			ctx.drawImage(video, 0, 0, width, height);
			jtBase64Url = canvas.toDataURL('image/png');
			$("#jtImg").attr("src", jtBase64Url);
			$("#jtImg").show();
		});
	}
	
	// 下载截图
	function downImg() {
		var downloadA = $("<a></a>").attr("href", jtBase64Url).attr("download",
				"Veasion" + new Date().getTime() + ".jpg");
		downloadA[0].click();
	}
	
	// 异步上传图片
	var faceing = false;
	function upImgFile() {
		if (jtBase64Url == null || jtBase64Url == "") {
			$.ligerDialog.waitting('请先拍照哦~');
			setTimeout(function() {
				$.ligerDialog.closeWaitting();
			}, 2000);
			return;
		}
		if (faceing) {
			$.ligerDialog.waitting('正在评估,请稍后...');
			setTimeout(function() {
				$.ligerDialog.closeWaitting();
			}, 1000);
			return;
		}
		$("#loading").show();
		faceing = true;
		$.ajax({
			url : "${pageContext.request.contextPath}/photo/face/upImgFile.vea",
			data : { "jtBase64Url" : jtBase64Url },
			type : "post",
			success : function(data) {
				faceing = false;
				$("#loading").hide();
				if (data.message != null) {
					var manager = $.ligerDialog.waitting('评估失败！');
					setTimeout(function() {
						manager.close();
					}, 2000);
				} else {
					if (data.html == ""
							|| data.html == "<table></table>") {
						$.ligerDialog.waitting('请勿遮住你的脸！请重新拍照检测...');
						setTimeout(function() {
							$.ligerDialog.closeWaitting();
						}, 2000);
						$("#htmlDiv").html("请勿遮住你的脸！");
					} else {
						$.ligerDialog.waitting('评估完成！请在左边查看结果~');
						setTimeout(function() {
							$.ligerDialog.closeWaitting();
						}, 2000);
						$("#htmlDiv").html(data.html);
					}
				}
			},
			error : function(e) {
				faceing = false;
				$("#loading").hide();
				$.ligerDialog.waitting("发生错误！");
				setTimeout(function() {
					$.ligerDialog.closeWaitting();
				}, 2000);
			}
		});
	}
</script>
</body>
</html>