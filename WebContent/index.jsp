<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype>
<html>
<head>
<title>欢迎来到Veasion专属网站</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/favicon.ico">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/MouseClick.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/bgStyle1.js"></script><!-- 白天，蒲花英 -->
<style type="text/css">
	img{width:350px;z-index: 1000;}
	.white{color:white;}
	.black{color: black;}
</style>
<script type="text/JavaScript">
	var bgsoundArr=["${pageContext.request.contextPath }/static/bgsound.mp3"];
	var start=1,img=1,end=25;
	var time=2000;
	$(function(){
		$("#bgsound").attr("src",bgsoundArr[0]);
		changeImage();
	});
	function changeImage(){
		do{
			img=Math.round(Math.random()*end);
		}while(img==0);
		$("#ws").attr("src","${pageContext.request.contextPath}/images/ws"+img+".jpg");
		setTimeout("changeImage()",time);
	}
	function baitian(){
		$("body").css("background-color","pink");
		$("#huaMazz").show();
		$("#canvas").hide();
	}
	function wanshang(){
		$("body").css("background-color","black");
		$("#canvas").show();
		$("#huaMazz").hide();
	}
	function bgChange(id){
		var bg=$("#"+id).text();
		if(bg=='晚上'){
			wanshang();
			$("#"+id).text("白天");
			$("#"+id).attr("title","白天");
			$(".black").each(function(i){
				$(this).attr("class","white");
			});
		}else{
			baitian();
			$("#"+id).text("晚上");
			$("#"+id).attr("title","晚上");
			$(".white").each(function(i){
				$(this).attr("class","black");
			});
		}
	}
</script>
</head>
<body bgcolor="pink">
	<!-- 背景音乐 -->
	<audio controls loop="loop" autoplay="autoplay" hidden="true">
    	<source id="bgsound" src="${pageContext.request.contextPath }/static/bgsound.mp3" />
	</audio>
	<!-- 白天，蒲花英 -->
	<div class="snow-container" id="huaMazz" style="position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:100;"></div>
	<!-- 黑白切换 -->
	<div class="black" style="float:left;font-family:微软雅黑;z-index: 200;">
		&nbsp;&nbsp;
		<button id="bgChang" onclick="bgChange(this.id);" style="cursor: pointer;" title="晚上">晚上</button>
	</div>
	<!-- 导航 -->
	<div style="float:right;z-index: 200;">
		<a href="http://59.110.241.52/solo" title="Veasion的博客">我的博客${count }</a>
		&nbsp;|&nbsp;
		<a href="https://github.com/veasion" title="My Github.">Github</a>
		&nbsp;&nbsp;
	</div>
	<div>
		<center><h1 class="black">欢迎来到我的首个专属网站页面！</h1>
		<a href="https://github.com/veasion" title="My Github."
			style="text-decoration:none" target="_blank"
		>===Github===</a><br/>
		<font color="red">非常抱歉！我的个人网站还没写好，欢迎下次光临。</font>
		<br/><br/>
		<!-- 图片 -->
		<img id="ws" src="${pageContext.request.contextPath}/images/ws1.jpg" />
		</center>
		<br/><br/><br/>
		<!-- 晚上，烟花 -->
		<canvas id="canvas" style="position: absolute;top:10%;z-index:100;display:none;"></canvas>
	</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/bgStyle2.js"></script><!-- 晚上，烟花 -->
</html>
<!-- Veasion -->
