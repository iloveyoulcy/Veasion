<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>Veasion管理后台</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/favicon.ico">
<link href="${pageContext.request.contextPath}/jquery/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/jquery.cookie.js"></script>
<script src="${pageContext.request.contextPath}/jquery/json2.js"></script>
<style type="text/css">
body, html {height: 100%;}
body {padding: 0px;margin: 0;overflow: hidden;}
.l-link2 {text-decoration: underline;color: white;margin-left: 2px;margin-right: 2px;}
.l-layout-top {background: #102A49;color: White;}
.l-layout-bottom {background: #E5EDEF;text-align: center;}
#pageloading {position: absolute;left: 0px;top: 0px;background: white url('${pageContext.request.contextPath}/images/loading.gif') no-repeat center;width: 100%;height: 100%;z-index: 99999;}
.l-winbar {background: #2B5A76;height: 30px;position: absolute;left: 0px;bottom: 0px;width: 100%;z-index: 99999;}
.space {color: #E7E7E7;}
/* 顶部 */
.l-topmenu {margin: 0;padding: 0;height: 31px;line-height: 31px;background-color: #1f448b;position: relative;border-top: 1px solid #1D438B;}
.l-topmenu-logo {color: #E7E7E7;padding-left: 35px;line-height: 26px;}
.l-topmenu-welcome {position: absolute;height: 24px;line-height: 24px;right: 30px;top: 2px;color: #070A0C;}
.l-topmenu-welcome a {color: #E7E7E7;text-decoration: underline;}
.body-gray2014 #framecenter {margin-top: 3px;}
.viewsourcelink {background: #B3D9F7;display: block;position: absolute;right: 10px;top: 3px;padding: 6px 4px;color: #333;text-decoration: underline;}
.viewsourcelink-over {background: #81C0F2;}
.l-topmenu-welcome label {color: white;}
</style>
<script type="text/javascript">
	var indexdata = [ {
		text : 'desktop',
		isexpand : false,
		children : [ {
			text : "style管理",
			url : "#"
		}, {
			text : "icon管理",
			url : "${pageContext.request.contextPath}/admin/icon.vea"
		} ]
	} ];

	var tab = null;
	var accordion = null;
	var tree = null;
	var tabItems = [];
	$(function() { //布局
		$("#layout1").ligerLayout({
			leftWidth : 190,
			height : '100%',
			heightDiff : -34,
			space : 4,
			onHeightChanged : f_heightChanged,
			onLeftToggle : function() {
				tab && tab.trigger('sysWidthChange');
			},
			onRightToggle : function() {
				tab && tab.trigger('sysWidthChange');
			}
		});
		var height = $(".l-layout-center").height();

		//Tab
		tab = $("#framecenter").ligerTab({
			height : height,
			showSwitchInTab : true,
			showSwitch : true,
			onAfterAddTabItem : function(tabdata) {
				tabItems.push(tabdata);
			},
			onAfterRemoveTabItem : function(tabid) {
				for (var i = 0; i < tabItems.length; i++) {
					var o = tabItems[i];
					if (o.tabid == tabid) {
						tabItems.splice(i, 1);
						break;
					}
				}
			},
			onReload : function(tabdata) {
				var tabid = tabdata.tabid;
			}
		});

		//面板
		$("#accordion1").ligerAccordion({
			height : height - 24,
			speed : null
		});

		//树
		$("#tree1").ligerTree(
				{
					data : indexdata,
					checkbox : false,
					slide : false,
					nodeWidth : 120,
					attribute : [ 'nodename', 'url' ],
					render : function(a) {
						if (!a.isnew)
							return a.text;
						return '<a href="' + a.url + '" target="_blank">'
								+ a.text + '</a>';
					},
					onSelect : function(node) {
						if (!node.data.url)
							return;
						if (node.data.isnew) {
							return;
						}
						var tabid = $(node.target).attr("tabid");
						if (!tabid) {
							tabid = new Date().getTime();
							$(node.target).attr("tabid", tabid)
						}
						f_addTab(tabid, node.data.text, node.data.url);
					}
				});

		function openNew(url) {
			var jform = $('#opennew_form');
			if (jform.length == 0) {
				jform = $('<form method="post" />').attr('id', 'opennew_form')
						.hide().appendTo('body');
			} else {
				jform.empty();
			}
			jform.attr('action', url);
			jform.attr('target', '_blank');
			jform.trigger('submit');
		}
		;

		tab = liger.get("framecenter");
		accordion = liger.get("accordion1");
		tree = liger.get("tree1");
		$("#pageloading").hide();
		
	});
	function f_heightChanged(options) {
		if (tab)
			tab.addHeight(options.diff);
		if (accordion && options.middleHeight - 24 > 0)
			accordion.setHeight(options.middleHeight - 24);
	}
	function f_addTab(tabid, text, url) {
		tab.addTabItem({
			tabid : tabid,
			text : text,
			url : url,
			callback : function() {
				//打开页面回调
			}
		});
	}
</script>
</head>
<body style="padding: 0px; background: #EAEEF5;">
	<div id="pageloading"></div>
	<div id="topmenu" class="l-topmenu">
		<div class="l-topmenu-logo">Veasion管理后台</div>
		<div class="l-topmenu-welcome">
			<a href="#" style="margin-right: 6px;">退出系统</a>
		</div>
	</div>
	<div id="layout1"
		style="width: 99.2%; margin: 0 auto; margin-top: 4px;">
		<div position="left" title="主要菜单" id="accordion1">
			<div title="管理模块" class="l-scroll">
				<ul id="tree1" style="margin-top: 3px;">
			</div>
		</div>
		<div position="center" id="framecenter">
			<div tabid="home" title="我的主页" style="height: 300px">
				<iframe frameborder="0" name="home" id="home" src="${pageContext.request.contextPath}/index.jsp"></iframe>
			</div>
		</div>
	</div>
	<div style="display: none"></div>
</body>
</html>
