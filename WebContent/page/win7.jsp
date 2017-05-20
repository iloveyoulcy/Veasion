<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <title></title>
    <link href="${pageContext.request.contextPath}/jquery/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" /> 
    <script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jquery/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jquery/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jquery/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jquery/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <style type="text/css">
    	body {
			-webkit-user-select: none;
			-moz-user-select: none;
			-ms-user-select: none;
			user-select: none;
		}
        .l-case-title
        {
            font-weight: bold;
            margin-top: 20px;
            margin-bottom: 20px;
        }
        body, html
        {
            width: 100%;
            height: 100%;
        }
        *
        {
            margin: 0;
            padding: 0;
        }
        #winlinks
        {
            position: absolute;
            left: 20px;
            top: 20px;
            width: 100%;
        }
        #winlinks ul
        {
            position: relative;
        }
        #winlinks li
        {
            width: 70px;
            cursor: pointer;
            height: 80px;
            position: absolute;
            z-index: 101;
            list-style: none;
            text-align: center;
        }
        #winlinks li img
        {
            width: 36px;
            height: 36px;
        }
        #winlinks li span
        {
            background: none repeat scroll 0 0 rgba(0, 0, 0, 0.3);
            border-radius: 10px 10px 10px 10px;
            display: block;
            font-size: 12px;
            margin-top: 1px;
            color: White;
            line-height: 18px;
            text-align: center;
        }
        #winlinks li.l-over div.bg
        {
            display: block;
        }
        #winlinks li div.bg
        {
            display: none;
            position: absolute;
            top: -2px;
            left: -2px;
            z-index: 0;
            width: 75px;
            height: 64px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            background: #000;
            opacity: 0.1;
            filter: alpha(opacity=10);
        }
        .l-taskbar-task-icon
        {
            top: 3px; left: 6px; background-image:none;
        } 
        .l-taskbar-task-content{ margin-left:30px;}
        .l-taskbar-task-icon img
        {
            width: 22px;
            height: 22px;
        }
    </style>
</head>
<body style="overflow: hidden; background: url(${pageContext.request.contextPath}/page/images/bgimg_lol2.jpg) no-repeat  center center;">
    <div id="winlinks">
        <ul>
        </ul>
    </div>
</body>
<script type="text/javascript">

    var LINKWIDTH = 90, LINKHEIGHT = 90, TASKBARHEIGHT = 43;
    var winlinksul =  $("#winlinks ul");
    

	//可拖动
	$(function(){
    	$.fn.Drag = function (did) {
            var M = false;
            var Rx, Ry;
            var t = $(this);
            var d=$("#"+did);
            var isDrag=false;
        t.mousedown(function (event) {
                Rx = event.pageX - (parseInt(t.css("left")) || 0);
                Ry = event.pageY - (parseInt(t.css("top")) || 0);
                M = true;
            })
        .mouseup(function (event) {
            M = false;
            t.css('cursor', 'default');
        });
        d.mouseout(function(event){
        	isDrag=false;
        	t.css('cursor', 'default');
        })
        .mouseover(function(event){
        	isDrag=true;
        });
            $(document).mousemove(function (event) {
                if (M && isDrag) {
                    t.css({ top: event.pageY - Ry, left: event.pageX - Rx ,cursor: "move"});
                }
            });
        }
    });
    
	function f_open(url, title, icon,width,height,showMax) {
		var win = $.ligerDialog.open({
			height : height!=null?height:500,
			url : url,
			width : width!=null?width:600,
			showMax : showMax,
			showMin : true,
			isResize : true,
			modal : false,
			title : title,
			slide : false
			/* ,buttons : [ {
				text : '确定',
				onclick : function(item, Dialog, index) {
					win.hide();
				}
			} ] */
		});
		var task = jQuery.ligerui.win.tasks[win.id];
		if (task) {
			$(".l-taskbar-task-icon:first", task).html('<img src="' + icon + '" />');
		}
		return win;
	}
	
	var links = [ 
		       {icon : '${pageContext.request.contextPath}/page/images/icon_pc.png',title : '我的主页',url : '${pageContext.request.contextPath}/index.jsp',width:900,height:700}, 
		       {icon : '${pageContext.request.contextPath}/page/images/icon_movie1.png',title : '电影',url : 'http://www.youku.com/',width:1200,height:600}, 
		       {icon : '${pageContext.request.contextPath}/page/images/icon_jishiben3.png',title : '记事本',url : '${pageContext.request.contextPath}/page/notepad.jsp',width:440,height:480}, 
		       {icon : '${pageContext.request.contextPath}/page/images/icon_liaotian2.png',title : '聊天',url : '#'},
		       {icon : '${pageContext.request.contextPath}/page/images/icon_music2.png',title : '音乐',url : 'http://web.kugou.com/',width:750,height:600},
		       {icon : '${pageContext.request.contextPath}/page/images/icon_qiu.png',title : '游戏',url : '#'},
		       {icon : '${pageContext.request.contextPath}/page/images/icon_qq.png',title : 'QQ',url : 'http://w.qq.com/',width:1100,height:600},
		       {icon : '${pageContext.request.contextPath}/page/images/icon_search.png',title : '搜索',url : 'http://www.baidu.com'},
		       {icon : '${pageContext.request.contextPath}/page/images/icon_love.png',title : 'Love',url : '#'}
	       ];
	
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
	
	function linksInit() {
		for (var i = 0, l = links.length; i < l; i++) {
			var link = links[i];
			var jlink;
			var jlink = $("<li></li>");
			jlink.attr("linkindex", i);
			jlink.attr("id","VeasionLi"+i);
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
				f_open(link.url, link.title, link.icon,link.width,link.height,link.showMax!=null?link.showMax:true);
			});
			jlink.appendTo(winlinksul);
		}
	}
	
	$(window).resize(onResize);
	$.ligerui.win.removeTaskbar = function() {
	}; //不允许移除
	$.ligerui.win.createTaskbar(); //页面加载时创建任务栏

	linksInit();
	onResize();
	
	//$("#VeasionLi1").Drag("VeasionImg1");
	
</script>
</html>
