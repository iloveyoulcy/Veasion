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
		return !hasNull(["title","icon","url"],true);
	}
	
	$(function(){
		var obj="${obj}";
		if(obj!=""&&obj!="null"){
			$("#icon").val("${obj.icon}");
			$("#show_type").val("${obj.show_type}");
		}else{
			$('#icon').trigger("change");
		}
	});
	
	function openURL(url){
		window.parent.window.f_addTab("browse_url", "浏览", url);
	}
</script>
<body>
<br/>
<div style="text-align: center;">
	<form action="${pageContext.request.contextPath}/admin/desktop/iconUpdate.vea" method="post" onsubmit="return check();">
	<c:if test="${obj !=null }">
		<input type="hidden" name="id" value="${obj.id }"/>
	</c:if>
	<table>
		<tbody>
			<tr>
				<th>标题：</th>
				<td>
					<input name="title" type="text" value="${obj.title }" maxlength="10"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>图标：</th>
				<th>
					<select id="icon" name="icon" onchange="document.icon_img.src=options[selectedIndex].value">
						<c:forEach items="${icons }" var="ico">
							<option value="${pageContext.request.contextPath}/page/images/${ico }">${ico }</option>
						</c:forEach>
					</select>
				</th>
				<th>
					<img name="icon_img" src="${obj.icon }" style="width: 36px;height: 36px;">
				</th>
			</tr>
			<tr>
				<th>URL：</th>
				<td>
					<input id="url" name="url" type="text" value="${obj.url }" />
				</td>
				<td>
					<a href="javascript:openURL($('#url').val());">浏览</a>
				</td>
			</tr>
			<tr>
				<th>宽度：</th>
				<td>
					<input name="width" type="text" value="${obj.width!=null?obj.width:500 }" maxlength="4" onkeydown="return inputInt(event);"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>高度：</th>
				<td>
					<input name="height" type="text" value="${obj.height!=null?obj.height:500 }" maxlength="4" onkeydown="return inputInt(event);"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>类型：</th>
				<td>
					<select id="show_type" name="show_type">
						<c:forEach items="${showTypes }" var="type" varStatus="s">
							<option value="${s.count-1 }">${type }</option>
						</c:forEach>
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