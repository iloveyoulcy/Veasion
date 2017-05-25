<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ICON管理</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/favicon.ico">
<link href="${pageContext.request.contextPath}/jquery/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<style>
td,th{width:100px; height:40px;}
div img{width: 36px; height: 36px;}
a{text-decoration:none}

.icon {
    width: 16px;
    height: 16px;
    cursor: pointer;
}
</style>
<script type="text/javascript">

	var grid = null;
	
	$(function() {
		loadData(null);
	});
	
	function loadData(parms){
		grid = $("#maingrid4").ligerGrid({
			url:"${pageContext.request.contextPath}/admin/desktop/iconSearch.vea",
			columns : [ {
				display : 'id',
				name : 'id',
				width : 35
			}, {
				display : '图标',
				name : 'icon'
			}, {
				display : '标题',
				name : 'title'
			}, {
				display : '链接',
				name : 'url',
				width : 180
			}, {
				display : '宽度',
				name : 'width'
			}, {
				display : '高度',
				name : 'height'
			}, {
				display : '显示类型',
				name : 'show_type'
			}, {
				display : '状态',
				name : 'status'
			}, {
				display : '创建时间',
				name : 'create_date',
				width : 80
			}, {
				display : '编辑',
				name : 'edit'
			}, {
				display : '删除',
				name : 'del'
			} ],
			pageSize : 10,
			width : '100%',
			height : '100%',
			rowAttrRender: function (rowdata,rowid){
				var id=rowdata.id;
				var url=rowdata.url;
				var title=rowdata.title;
				var date=rowdata.create_date;
				rowdata.icon="<img src='"+rowdata.icon+"'/>";
				rowdata.title="<span title='"+title+"'>"+title+"</span>";
				rowdata.create_date="<span title='"+date+"'>"+date+"</span>";
				rowdata.url="<a href=\"javascript:openUrl('"+id+"','"+title+"','"+url+"');\" title='"+url+"'>"+url+"</a>";
				rowdata.edit="<a href=\"javascript:update('"+id+"','"+title+"');\">修改</a>";
				rowdata.del="<a href=\"javascript:del('"+id+"','"+title+"');\" style='color:red;'>删除</a>";
				return null;
			},
			parms : parms
		});
		$("#pageloading").hide();
	}
	
	function openUrl(tabid, text, url){
		window.parent.window.f_addTab(tabid, text, url);
	}
	
	function add(){
		window.parent.window.f_addTab("addIcon", "新增Icon", "${pageContext.request.contextPath}/admin/desktop/goIconModify.vea");
	}
	function update(id,title){
		window.parent.window.f_addTab("updateIcon", title, "${pageContext.request.contextPath}/admin/desktop/goIconModify.vea?id="+id);
	}
	function del(id,title){
		if(confirm("确定要删除“"+title+"”?")){
			location.href="${pageContext.request.contextPath}/admin/desktop/iconDelete.vea?id="+id;
		}
	}
	
	//查询
	function search() {
		loadData({"title":$("#title").val()});
	}
	
</script>
</head>
<body style="padding: 6px; overflow: hidden;">
	<div id="searchbar">
		标题：<input id="title" type="text" value=""/> 
		<input id="btnOK" type="button" value="搜索" onclick="search();" />
		<img class="icon" style="float: right;margin-left: 6px;margin-right: 3px;" onclick="openUrl('upfile_icon','上传图标','${pageContext.request.contextPath}/page/desktop/upFile.jsp?type=icon');" src="${pageContext.request.contextPath}/jquery/ligerUI/skins/icons/up.gif" title="上传图标" alt="上传图标" />
		<img class="icon" style="float: right;" onclick="add();" src="${pageContext.request.contextPath}/jquery/ligerUI/skins/icons/add.gif" title="新增" alt="新增">
	</div>
	<div id="maingrid4" style="margin: 0; padding: 0"></div>
	<div style="display: none;"></div>
</body>
</html>