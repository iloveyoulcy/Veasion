<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>Veasion</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/favicon.ico">
<link href="${pageContext.request.contextPath}/jquery/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" /> 
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/MouseClick.js"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
<style type="text/css">
body {-webkit-user-select: none;-moz-user-select: none;-ms-user-select: none;user-select: none;}
.l-case-title {font-weight: bold;margin-top: 20px;margin-bottom: 20px;}
body, html {width: 100%;height: 100%;}
* {margin: 0;padding: 0;}
#winlinks {position: absolute;left: 20px;top: 20px;width: 100%;}
#winlinks ul {position: relative;}
#winlinks li {width: 70px;cursor: pointer;height: 80px;position: absolute;z-index: 101;list-style: none;text-align: center;}
#winlinks li span {background: none repeat scroll 0 0 rgba(0, 0, 0, 0.3);border-radius: 10px 10px 10px 10px;display: block;font-size: 12px;margin-top: 1px;color: White;line-height: 18px;text-align: center;}
#winlinks li.l-over div.bg {display: block;}
#winlinks li div.bg {display: none;position: absolute;top: -2px;left: -2px;z-index: 0;width: 75px;height: 64px;-webkit-border-radius: 5px;-moz-border-radius: 5px;border-radius: 5px;background: #000;opacity: 0.1;filter: alpha(opacity = 10);}
.l-taskbar-task-icon {top: 3px;left: 6px;background-image: none;}
.l-taskbar-task-content {margin-left: 30px;}
.l-taskbar-task-icon img {width: 22px;height: 22px;}
</style>
</head>
<!-- background: url(#) no-repeat  center center; -->
<body style="overflow: hidden;">
    <div id="winlinks">
        <ul></ul>
    </div>
</body>
<script type="text/javascript">
	var LINKWIDTH = 90, LINKHEIGHT = 90, TASKBARHEIGHT = 43;
	var winlinksul = $("#winlinks ul");
	
	//存放对应的id,win对象
	var opens=new Array();
	
	//根据id获取open对象
	function getOpenObj(id){
		for(var i=0,l=opens.length;i<l;i++){
			if(opens[i].id==id){
				return opens[i].obj;
			}
		}
		return null;
	}
	
	function f_open(id, url, title, icon, width, height, showMax, openMax, openMin) {
		if(url.indexOf('?')!=-1){
			url+="&icon_id="+id;
		}else{
			url+="?icon_id="+id;
		}
		var win = $.ligerDialog.open({
			id : id,
			height : height != null ? height : 500,
			url : url,
			width : width != null ? width : 600,
			showMax : showMax,
			showMin : true,
			isResize : true,
			modal : false,
			title : title,
			slide : false,
			onLoaded :function(){}
		});
		
		if(openMax!=null){
			win.max();
		}else if(openMin!=null){
			win.min();
		}
		
		var task = jQuery.ligerui.win.tasks[win.id];
		if (task) {
			$(".l-taskbar-task-icon:first", task).html('<img src="' + icon + '" />');
		}
		opens.push({"id":id,"obj":win});
		return win;
	}
	
	var links = [];
	
	function onResize() {
		var linksHeight = $(window).height() - TASKBARHEIGHT;
		var winlinks = $("#winlinks");
		winlinks.height(linksHeight);
		var colMaxNumber = parseInt(linksHeight / LINKHEIGHT);//一列最多显示几个快捷方式
		for (var i = 0, l = links.length; i < l; i++) {
			var link = links[i];
			var jlink = $("li[linkindex=" + i + "]", winlinks);
			var top = (i % colMaxNumber) * LINKHEIGHT, left = parseInt(i
					/ colMaxNumber)
					* LINKWIDTH;
			if (isNaN(top) || isNaN(left))
				continue;
			jlink.css({
				top : top,
				left : left
			});
		}
	}
	
	function createIcons(){
		for (var i = 0, l = links.length; i < l; i++) {
			var link = links[i];
			var jlink;
			var jlink = $("<li></li>");
			jlink.attr("linkindex", i);
			jlink.attr("id", "VeasionLi" + i);
			jlink.append("<img src='" + link.icon + "' id='VeasionImg'"+i+"/>");
			jlink.append("<span>" + link.title + "</span>");
			jlink.append("<div class='bg'></div>");
			jlink.hover(function() {
				$(this).addClass("l-over");
			}, function() {
				$(this).removeClass("l-over");
			}).click(function() {
				var linkindex = $(this).attr("linkindex");
				var link = links[linkindex];
				if(link.open){
					window.open(link.url);
				}else{
					f_open("icon_"+link.id, link.url, link.title, link.icon, link.width,link.height,link.showMax != null ? link.showMax : true , link.openMax , link.openMin);
				}
			});
			jlink.appendTo(winlinksul);
		}
	}
	
	$(window).resize(onResize);
	$.ligerui.win.removeTaskbar = function() {}; //不允许移除
	$.ligerui.win.createTaskbar(); //页面加载时创建任务栏
		
	//请求桌面数据
	$(function() {
		$.ajax({
			url : "${pageContext.request.contextPath}/index/desktopData.vea",
			type : "post",
			success : function(data) {
				if(data!=null)links = data.icons;
				if(data==null || links==null || links.length<1){
					links=[{id:"icon_jsb",url:"/Veasion/page/notepad.jsp",icon:"/Veasion/page/images/icon_jishiben3.png",title:"记事本",showMax:false}];
				}
				
				createIcons();
				
				if(data!=null && data.style!=null){
					$("body").css("background","url("+ data.style.bgimg+") no-repeat center center");
					//$("body").css("background-image","url("+ data.style.bgimg+")");
					$("#winlinks li img").css("width",data.style.cloumn_width).css("height",data.style.cloumn_height);
				}else{
					$("#winlinks li img").css("width",36).css("height",36);
					$("body").css("background-color","black");
				}
				
				onResize();
			},
			error : function(e) {
				alert('初始化数据错误！');
			}
		});
	});
	
</script>
</html>
