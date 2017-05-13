<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>欢迎来到Veasion专属网站</title>
<style type="text/css">
	body{
	    -webkit-user-select:none;
	    -moz-user-select:none;
	    -ms-user-select:none;
	    user-select:none;
	}
	#canvas,body div{
	  width:100%;
	}
	img{width:350px;}
	.white{color:white;}
	.black{color: black;}
	.headDiv{
		float: left;
		width: 100%;
	}
	#h1{
	    font-size: 2em;
	    -webkit-margin-before: 0.67em;
	    -webkit-margin-after: 0.67em;
	    font-weight: bold;
	}
	.font{
		font-size:18px;
	}
	.headDiv div{
		float:left;
		font-family:微软雅黑;
		z-index: 200;
	}
	/* 自适应设备 */
	@media ( max-width:900px ){
	  #canvas,body div{
	 	width: 900px;
	  }
	  img{width:350px;}
	  #h1{
	     font-size: 2em;
	     -webkit-margin-before: 0.67em;
	     -webkit-margin-after: 0.67em;
	     font-weight: bold;
	  }
	  .font{
		font-size:18px;
	  }
	}
	@media ( max-width:600px ){
	  #canvas,body div{
	  	width: 600px;
	  }
	  img{width:280px;}
	  #h1{
	    font-size: 1.6em;
    	-webkit-margin-before: 0.83em;
   		-webkit-margin-after: 0.83em;
   		font-weight: bold;
	  }
	  .font{
		font-size:15px;
	  }
	}
	@media ( max-width:400px ){
	  #canvas,body div{
	  	width: 400px;
	  }
	  img{width:220px;}
	  #h1{
	    font-size: 1.3em;
    	-webkit-margin-before: 0.9em;
   		-webkit-margin-after: 0.9em;
   		font-weight: bold;
	  }
	  .font{
		font-size:12px;
	  }
	}
</style>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/favicon.ico">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/MouseClick.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/bgStyle1.js"></script><!-- 白天，蒲花英 -->
<script type="text/JavaScript">
	var bgsoundArr=["${pageContext.request.contextPath }/static/bgsound.mp3"];
	var start=1,img=1,end=25;
	var time=2000;
	$(function(){
		$("#bgsound").attr("src",bgsoundArr[0]);
		changeImage();
		
		var top=$("#h1").position().top;
		if(isNaN(top))
			top=40;
		else if(top>80)
			top=40;
		$("#canvas").css('top',top);
		
		var h=new Date().getHours();
		if(h>=6 && h<=18){
			baitian();
		}else{
			wanshang();
		}
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
		$("#bgChang").text("晚上");
		$("#bgChang").attr("title","晚上");
		$(".white").each(function(i){
			$(this).attr("class","black");
		});
	}
	function wanshang(){
		$("body").css("background-color","black");
		$("#canvas").show();
		$("#huaMazz").hide();
		$("#bgChang").text("白天");
		$("#bgChang").attr("title","白天");
		$(".black").each(function(i){
			$(this).attr("class","white");
		});
	}
	function bgChange(){
		var bg=$("#bgChang").text();
		if(bg=='晚上'){
			wanshang();
		}else{
			baitian();
		}
	}
</script>
</head>
<body>
	<div>
		
		<!-- 背景音乐 -->
		<audio controls loop="loop" autoplay="autoplay" hidden="true">
	    	<source id="bgsound" src="${pageContext.request.contextPath }/static/bgsound.mp3" />
		</audio>
		
		<!-- 白天，蒲花英 -->
		<div class="snow-container" id="huaMazz" style="position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:100;"></div>
		
		<br />
		
		<div class="headDiv">
			
			<!-- 黑白切换 -->
			<div class="black" style="width: 50%;text-align: left;">
				&nbsp;&nbsp;
				<button id="bgChang" onclick="bgChange();" style="cursor: pointer;" title="晚上">
					晚上
				</button>
			</div>
			
			<!-- 导航 -->
			<div style="width: 50%;text-align: right;">
				<a href="http://59.110.241.52/solo" title="Veasion的博客" class="font">
					我的博客
				</a>
				&nbsp;|&nbsp;
				<a href="https://github.com/veasion" title="My Github." class="font">
					Github
				</a>
				&nbsp;&nbsp;
			</div>
		</div>
		
		<div style="text-align: center;">
			<br />
			<div id="h1" class="black">
				欢迎来到我的首个专属网站页面！
			</div>
			<div class="font">
				<a href="https://github.com/veasion" title="My Github."
					style="text-decoration:none" target="_blank" >
					===Github===
				</a>
				<br/>
				<span style="color: red;">
					非常抱歉！我的个人网站还没写好，欢迎下次光临。
				</span>
			</div>
			<br/>
			
			<!-- 图片 -->
			<img id="ws" src="${pageContext.request.contextPath}/images/ws1.jpg" ondragstart="return false;"/>
		</div>
		
		<!-- 晚上，烟花 -->
		<canvas id="canvas" style="position: absolute;z-index:100;display:none;"></canvas>
	</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/bgStyle2.js"></script><!-- 晚上，烟花 -->
</html>
<!--Veasion -->
