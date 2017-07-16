<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>真人秀</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/favicon.ico">
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
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
		text-align: center;
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
	<div class="div13" id="htmlDiv">
		左边
	</div>
	<div style="width: 60%;height:80%;float: left;text-align: center;">
		<video id="video"></video>
	</div>
	<div class="div13">
		<img alt="照片" src="" id="jtImg" class="jtImg" title="点击下载照片" onclick="downImg();"/>
		
		<div style="margin-top: 30px;">
			<button id="snap">拍照</button>
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
	function liveVideo() {
	    var URL = window.URL || window.webkitURL; // 获取到window.URL对象
	    navigator.getUserMedia({
	        video: true
	    },
	    function(stream) {
	        video.src = URL.createObjectURL(stream); // 将获取到的视频流对象转换为地址
	        video.play(); // 播放
	        //点击截图 
	        document.getElementById("snap").addEventListener('click',
	        function() {
	        	var width = $("#video").width();
	        	var height = $("#video").height();
	        	canvas.width = width;
	        	canvas.height = height;
	            ctx.drawImage(video, 0, 0, width, height);
	            jtBase64Url = canvas.toDataURL('image/png');
	            $("#jtImg").attr("src",jtBase64Url);
	            $("#jtImg").show();
	        });
	    },
	    function(error) {
	        console.log(error.name || error);
	    });
	}
	
	// 下载截图
	function downImg(){
		var $a = $("<a></a>").attr("href", jtBase64Url).attr("download", "Veasion"+new Date().getTime()+".jpg");
	    $a[0].click();
	}
	
	// 异步上传图片
	function upImgFile(){
		if(jtBase64Url==null || jtBase64Url==""){
			alert("请先拍照哦~");
			return;
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/photo/face/upImgFile.vea",
			data:{"jtBase64Url":jtBase64Url},
			type:"post",
			success:function(data){
				if(data.message!=null){
					alert("评估失败！");
				}else{
					alert("评估完成！请在左边查看结果~");
					$("#htmlDiv").html(data.html);
				}
			},
			error:function(e){
				alert("发生错误！");
			}
		});
	}
	
	$(function(){
		// 播放
		liveVideo();
	});
	
</script>
</body>
</html>