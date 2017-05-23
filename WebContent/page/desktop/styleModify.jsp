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
<style type="text/css">
.icon{width: 36px;height: 36px;cursor: pointer;}
</style>
</head>
<script type="text/javascript">

	function check(){
		return !hasNull(["name","bgimg","author"],true);
	}
	
	$(function(){
		var obj="${obj}";
		if(obj!=""&&obj!="null"){
			$("#bgimg").val("${obj.bgimg}");
			var ids="${obj.cloumn_ids}";
			if(ids!=""){
				var idArr=ids.trim().split(",");
				for(var i=0,l=idArr.length;i<l; i++){
					$("#icon_"+idArr[i]).attr("checked",true);
				}
			}
		}else{
			$('#bgimg').trigger("change");
		}
	});
	
	function openURL(url,title){
		window.parent.window.f_addTab("browse_url", title, url);
	}
</script>
<body>
<br/>
<div>
	<form action="${pageContext.request.contextPath}/admin/styleUpdate.vea" method="post" onsubmit="return check();">
	<c:if test="${obj !=null }">
		<input type="hidden" name="id" value="${obj.id }"/>
	</c:if>
	<table>
		<tbody>
			<tr>
				<th>标题：</th>
				<td>
					<input name="name" type="text" value="${obj.name }" maxlength="10"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>作者：</th>
				<td>
					<input name="author" type="text" value="${obj.author }" maxlength="10"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>背景：</th>
				<th>
					<select id="bgimg" name="bgimg" onchange="document.bgimg_img.src=options[selectedIndex].value">
						<c:forEach items="${bgimgs }" var="bg">
							<option value="${pageContext.request.contextPath}/page/images/${bg }">${bg }</option>
						</c:forEach>
					</select>
				</th>
				<th>
					<img name="bgimg_img" src="${obj.bgimg }" style="width: 140px;height: 100px;">
				</th>
			</tr>
			<tr>
				<th>宽度：</th>
				<td>
					<input name="cloumn_width" type="text" value="${obj.cloumn_width!=null?obj.cloumn_width:36 }" maxlength="4" onkeydown="return inputInt(event);"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>高度：</th>
				<td>
					<input name="cloumn_height" type="text" value="${obj.cloumn_height!=null?obj.cloumn_height:36 }" maxlength="4" onkeydown="return inputInt(event);"/>
				</td>
				<td></td>
			</tr>
		</tbody>
	</table>
	<h4>图标：</h4>
	<div style="border:1px solid #666;overflow-y:scroll;height: 140px;width: 600px;">
		<c:forEach items="${cloumn_ids }" var="ico">
			<div style="float: left;border:1px solid pink;text-align: center;">
				<input id="icon_${ico.id }" type="checkbox" value="${ico.id }" name="cloumn_ids"/>
				<a href="javascript:openURL('${ico.url}','预览${ico.title }');" title="预览${ico.title }">${ico.title}</a>
				<img src="${ico.icon }" class="icon"/>
			</div>
		</c:forEach>
	</div><br />
	<h4><input type="submit" value="保存"/></h4>
	</form>
</div>
<br />
</body>
</html>