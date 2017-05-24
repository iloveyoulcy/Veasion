<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>STYLE管理</title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/favicon.ico">
<link href="${pageContext.request.contextPath}/jquery/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<style>
td,th{width:100px; height:66px;}
div img{width: 36px; height: 36px;cursor: pointer; }
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
			url:"${pageContext.request.contextPath}/admin/styleSearch.vea",
			columns : [ {
				display : 'id',
				name : 'id'
			}, {
				display : '背景',
				name : 'bgimg',
				align: 'center'
			}, {
				display : '标题',
				name : 'name'
			}, {
				display : '作者',
				name : 'author',
			}, {
				display : '宽度',
				name : 'cloumn_width'
			}, {
				display : '高度',
				name : 'cloumn_height'
			}, {
				display : 'Icons',
				name : 'cloumn_ids',
				width : 200
			}, {
				display : '创建时间',
				name : 'create_date',
				width : 80
			}, {
				display : "使用",
				name : 'status'
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
				var name=rowdata.name;
				var date=rowdata.create_date;
				var icons=rowdata.cloumn_ids;
				var status=(1==rowdata.status);
				rowdata.status="<a href=\"javascript:switchStatus('"+id+"',"+status+");\" title=\""+(status?"正在使用！":"使用")+"\" style=\""+(status?"color:green;":"")+"\">"+status+"</a>";
				rowdata.bgimg="<img src='"+rowdata.bgimg+"' title='查看大图' onclick='openUrl(\""+id+"\",\"查看大图\",\""+rowdata.bgimg+"\");'/>";
				rowdata.name="<span title='"+name+"'>"+name+"</span>";
				rowdata.cloumn_ids="<span title='"+icons+"'>"+icons+"</span>";
				rowdata.create_date="<span title='"+date+"'>"+date+"</span>";
				rowdata.edit="<a href=\"javascript:update('"+id+"','"+name+"');\">修改</a>";
				rowdata.del="<a href=\"javascript:del('"+id+"','"+name+"');\" style='color:red;'>删除</a>";
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
		window.parent.window.f_addTab("addStyle", "新增Style", "${pageContext.request.contextPath}/admin/goStyleModify.vea");
	}
	function update(id,title){
		window.parent.window.f_addTab("updateStyle", title, "${pageContext.request.contextPath}/admin/goStyleModify.vea?id="+id);
	}
	function del(id,title){
		if(confirm("确定要删除“"+title+"”?")){
			location.href="${pageContext.request.contextPath}/admin/styleDelete.vea?id="+id;
		}
	}
	function switchStatus(id,status){
		if(status)return;
		$.ajax({
			url:"${pageContext.request.contextPath}/admin/styleSwitchStatus.vea?id="+id,
			type:"post",
			success:function(data){
				if(data.object>0){
					loadData({"name":$("#name").val()});
				}else{
					alert("修改失败！");
				}
			},
			error:function(e){
				alert('发送错误！');
			}
		});	
	}	
	
	//查询
	function search() {
		loadData({"name":$("#name").val()});
	}
	
</script>
</head>
<body style="padding: 6px; overflow: hidden;">
	<div id="searchbar">
		标题：<input id="name" type="text" value="" /> 
		<input id="btnOK" type="button" value="搜索" onclick="search();" />
		<img class="icon" style="float: right;margin-left: 6px;margin-right: 3px;" onclick="openUrl('home_yl','预览桌面','/Veasion/index/index.vea');" src="${pageContext.request.contextPath}/jquery/ligerUI/skins/icons/home.gif" title="预览桌面" alt="预览" />
		<img class="icon" style="float: right;" onclick="add();" src="${pageContext.request.contextPath}/jquery/ligerUI/skins/icons/add.gif" title="新增" alt="新增" />
	</div>
	<div id="maingrid4" style="margin: 0; padding: 0"></div>
	<div style="display: none;"></div>
</body>
</html>