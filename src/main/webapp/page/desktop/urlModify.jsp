<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="Shortcut Icon" href="${pageContext.request.contextPath}/favicon.ico">
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/util.js" type="text/javascript"></script>
</head>
<script type="text/javascript">

	function check(){
		return !hasNull(["name","type","url"],true);
	}
	
	$(function(){
		var obj="${object}";
		if(obj!="" && obj!="null"){
			$("#type").val("${object.type}");
		}
	});
	
	function openURL(url){
		window.parent.window.f_addTab("browse_url", "浏览", url);
	}
</script>
<body>
<br/>
<div style="text-align: center;">
	<form action="${pageContext.request.contextPath}/admin/desktop/urlUpdate.vea" method="post" onsubmit="return check();">
	<c:if test="${object !=null }">
		<input type="hidden" name="id" value="${object.id }"/>
	</c:if>
	<table>
		<tbody>
			<tr>
				<th>名称：</th>
				<td>
					<input name="name" type="text" value="${object.name }" maxlength="10"/>
				</td>
				<td></td>
			</tr>
			<c:if test="${object.type==2 || object.type==3 }">
				<tr>
					<th>图标：</th>
					<th>
						<img name="icon_img" src="${object.url }" style="width: ${object.type==3 ? '36px' : '140px'};height: ${object.type==3 ? '36px' : '100px'};">
					</th>
					<th>&nbsp;</th>
				</tr>
			</c:if>
			<tr>
				<th>URL：</th>
				<td>
					<input id="url" name="url" value="${object.url }" title="${object.url }" ${object !=null && object.type!=1 ? "disabled='disabled'" : "" } />
				</td>
				<td>
					<c:if test="${object.type==1 }">
						<a href="javascript:openURL($('#url').val());">浏览</a>
					</c:if>&nbsp;
				</td>
			</tr>
			<tr>
				<th>类型：</th>
				<td>
					<select name="type" ${object !=null && object.type!=1 ? "disabled='disabled'" : "" } >
						<option value="1">访问链接</option>
						<c:if test="${object !=null }">
							<option value="2" ${object.type==2?"selected":"" }>桌面背景</option>
							<option value="3" ${object.type==3?"selected":"" }>图标</option>
							<option value="4" ${object.type==4?"selected":"" }>文件链接</option>
						</c:if>
					</select>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th colspan="3">
					<input type="submit" value="保存"/>
				</th>
			</tr>
		</tbody>
	</table>
	</form>
</div>
</body>
</html>