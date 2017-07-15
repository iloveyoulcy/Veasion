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
.icon{width: 36px;height: 36px;vertical-align: -12px;}
a{text-decoration: none;}
</style>
</head>
<script type="text/javascript">

	function check(){
		return !hasNull(["name","bgimg","author"],true);
	}
	
	$(function(){
		var obj="${object}";
		if(obj!=""&&obj!="null"){
			$("#bgimg").val("${object.bgimg}");
			var ids="${object.cloumn_ids}";
			if(ids!=""){
				var idArr=ids.trim().split(",");
				for(var i=0,l=idArr.length;i<l; i++){
					$("#icon_"+idArr[i]).prop("checked",true);
				}
			}
		}else{
			$('#bgimg').trigger("change");
		}
	});
	
	function openURL(url,title){
		window.parent.window.f_addTab("browse_url", title, url);
	}
	
	function iconAll(id){
		var check=$("#"+id).prop("checked");
		$("input[id^='icon_']").each(function(){
			$(this).prop("checked",check);
		});
	}
	
	function iconChange(id){
		var checked=$("#"+id).prop("checked");
		if(!checked)
			$("#all_icon").prop("checked",false);
		else{
			var all=true;
			$("input[id^='icon_']").each(function(){
				var c=$(this).prop("checked");
				if(!c){
					all=false;
					return false;
				}
			});
			if(all)$("#all_icon").prop("checked",true);
		}
	}
	
	function imgIcon(id){
		var obj=$("#"+id);
		var c=obj.prop("checked");
		obj.prop("checked",!c);
		obj.trigger("change");
	}
	
</script>
<body>
<br/>
<div>
	<form action="${pageContext.request.contextPath}/admin/desktop/styleUpdate.vea" method="post" onsubmit="return check();">
	<c:if test="${object !=null }">
		<input type="hidden" name="id" value="${object.id }"/>
	</c:if>
	<table>
		<tbody>
			<tr>
				<th>标题：</th>
				<td>
					<input name="name" type="text" value="${object.name }" maxlength="10"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>作者：</th>
				<td>
					<input name="author" type="text" value="${object.author }" maxlength="10"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>背景：</th>
				<th>
					<script type="text/javascript">
						function bgimgChange(obj){
							var opt=$(obj).find("option:selected");
							document.bgimg_img.src=obj.value;
							$("input[name='bgimg']").val(opt.attr("data-id"));
						}
					</script>
					<select id="bgimg" onchange="bgimgChange(this);">
						<c:forEach items="${bgimgs }" var="bg">
							<option value="${bg.url }" data-id="${bg.id }">${bg.name }</option>
						</c:forEach>
					</select>
					<input type="hidden" value="0" name="bgimg" />
				</th>
				<th>
					<img name="bgimg_img" src="${object.bgimg }" style="width: 140px;height: 100px;">
				</th>
			</tr>
			<tr>
				<th>宽度：</th>
				<td>
					<input name="cloumn_width" type="text" value="${object.cloumn_width!=null?object.cloumn_width:36 }" maxlength="4" onkeydown="return inputInt(event);"/>
				</td>
				<td></td>
			</tr>
			<tr>
				<th>高度：</th>
				<td>
					<input name="cloumn_height" type="text" value="${object.cloumn_height!=null?object.cloumn_height:36 }" maxlength="4" onkeydown="return inputInt(event);"/>
				</td>
				<td></td>
			</tr>
		</tbody>
	</table>
	<h4>图标：</h4>
	<input id="all_icon" type="checkbox" onchange="iconAll(this.id);"/>全选
	<div style="border:1px solid #666;overflow-y:scroll;height: 140px;width: 600px;padding-top: 5px;padding-left: 5px;">
		<c:forEach items="${cloumn_ids }" var="ico">
			<div style="float: left;border:1px solid pink;text-align: center;margin-left: 5px;margin-top: 2px;" >
				<input id="icon_${ico.id }" type="checkbox" value="${ico.id }" name="cloumn_ids" onchange="iconChange(this.id);" />
				<a href="javascript:openURL('${ico.url}','预览${ico.title }');" title="预览${ico.title }">${ico.title}</a>
				<img src="${ico.icon }" class="icon" onclick="imgIcon('icon_${ico.id }');" id="img_${ico.id }"/>
			</div>
		</c:forEach>
	</div><br />
	<h4><input type="submit" value="保存"/></h4>
	</form>
</div>
<br />
</body>
</html>