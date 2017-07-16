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
		var obj="${object}";
		if(obj!=""&&obj!="null"){
			$("#icon").val("${object.icon}");
			$("#url").val($("option[data-url='${object.url}']").val());
			$("#show_type").val("${object.show_type}");
		}
		$('#icon').trigger("change");
	});
	
	function openURL(url){
		window.parent.window.f_addTab("browse_url", "浏览", url);
	}
</script>
<body>
<br/>
<div style="text-align: center;">
	<form action="${pageContext.request.contextPath}/admin/desktop/iconUpdate.vea" method="post" onsubmit="return check();">
	<c:if test="${object !=null }">
		<input type="hidden" name="id" value="${object.id }"/>
	</c:if>
	<table>
		<tbody>
			<tr>
				<th>标题：</th>
				<td>
					<input name="title" type="text" value="${object.title }" maxlength="10"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>图标：</th>
				<th>
					<script type="text/javascript">
						function iconChange(){
							var opt=$("#icon").find("option:selected");
							document.icon_img.src=opt.val();
							$("input[name='icon']").val(opt.attr("data-id"));
						}
					</script>
					<select id="icon" onchange="iconChange();">
						<c:forEach items="${icons }" var="ico">
							<option value="${ico.url }" data-id="${ico.id }">${ico.name }</option>
						</c:forEach>
					</select>
					<input type="hidden" value="0" name="icon" />
				</th>
				<th>
					<img name="icon_img" src="${object.icon }" style="width: 36px;height: 36px;">
				</th>
			</tr>
			<tr>
				<th>URL：</th>
				<td>
					<select id="url" name="url">
						<option value="0" data-id="#">--请选择--</option>
						<c:forEach items="${urls }" var="url">
							<option value="${url.id }" data-url="${url.url }">${url.name }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<a href="javascript:openURL($('#url').find('option:selected').attr('data-url'));">浏览</a>
				</td>
			</tr>
			<tr>
				<th>宽度：</th>
				<td>
					<input name="width" type="text" value="${object.width!=null?object.width:500 }" maxlength="4" onkeydown="return inputInt(event);"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>高度：</th>
				<td>
					<input name="height" type="text" value="${object.height!=null?object.height:500 }" maxlength="4" onkeydown="return inputInt(event);"/>
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