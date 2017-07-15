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
			url:"${pageContext.request.contextPath}/admin/desktop/urlSearch.vea",
			columns : [ {
				display : 'id',
				name : 'id',
				width : 35
			}, {
				display : '名称',
				name : 'name'
			}, {
				display : 'url',
				name : 'url'
			}, {
				display : '类型',
				name : 'type'
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
				var name=rowdata.name;
				var type=rowdata.type;
				var date=rowdata.create_date;
				rowdata.name="<span title='"+name+"'>"+name+"</span>";
				rowdata.create_date="<span title='"+date+"'>"+date+"</span>";
				if(type==2 || type==3){
					rowdata.type=type==2 ? "桌面背景" : "图标";
					rowdata.url="<img src='"+rowdata.url+"'/>";
				}else if(type==1 || type==4){
					rowdata.type=type==1 ? "访问链接" : "文件链接";
					rowdata.url="<a href=\"javascript:openUrl('"+id+"','"+name+"','"+url+"');\" title='"+url+"'>"+url+"</a>";
				}
				rowdata.edit="<a href=\"javascript:update('"+id+"','"+name+"');\">修改</a>";
				rowdata.del="<a href=\"javascript:del('"+id+"','"+name+"');\" style='color:red;'>删除</a>";
				return null;
			},
			parms : parms
		});
		$("#pageloading").hide();
	}
	
	function openUrl(tabid, name, url){
		window.parent.window.f_addTab(tabid, name, url);
	}
	
	function add(){
		window.parent.window.f_addTab("addUrl", "新增Url", "${pageContext.request.contextPath}/admin/desktop/goUrlModify.vea");
	}
	function update(id,name){
		window.parent.window.f_addTab("updateUrl", name, "${pageContext.request.contextPath}/admin/desktop/goUrlModify.vea?id="+id);
	}
	function del(id,name){
		if(confirm("确定要删除“"+name+"”?")){
			location.href="${pageContext.request.contextPath}/admin/desktop/urlDelete.vea?id="+id;
		}
	}
	
	//查询
	function search() {
		loadData({"name":$("#name").val(),"type":$("#type").val()});
	}
	
</script>
</head>
<body style="padding: 6px; overflow: hidden;">
	<div id="searchbar">
		名称：<input id="name" type="text" value=""/>
		类型：
		<select id="type">
			<option value="0">全部</option>
			<option value="1">访问链接</option>
			<option value="2">桌面背景</option>
			<option value="3">图标</option>
			<option value="4">文件链接</option>
		</select>
		<input id="btnOK" type="button" value="搜索" onclick="search();" />
		<img class="icon" style="float: right;" onclick="add();" src="${pageContext.request.contextPath}/jquery/ligerUI/skins/icons/add.gif" title="新增" alt="新增">
	</div>
	<div id="maingrid4" style="margin: 0; padding: 0"></div>
	<div style="display: none;"></div>
</body>
</html>