<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	body {
		background-image: url('${pageContext.request.contextPath}/page/images/notepad.png');
		font-family: 华文楷体;
	}
	
	.demoEdit{border:1px solid #dddddd;min- height:20px;_height:20px;outline:0px;padding:2px;}
</style>
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<title>记事本</title>
<script type="text/javascript">
	var ws="86,69,65,83,73,79,78,187";
	var codes="";
	function txtChange(e,id,txt){
		var code=e.keyCode;
		var foat=true;
		if((code>=48&&code<=57)
				||(code>=65&&code<=90)
				||(code>=96&&code<=105)
				||code==110||code==187
				||code==190){
			if(codes.indexOf(ws)!=-1){
				foat=false;
				$("#"+id).text(txt+"*");
			}
			if(codes!="")codes+=",";
			codes+=code;
		}else if(code==8&&codes!=null&&codes!=""){
			var lastIndex=codes.lastIndexOf(",");
			if(lastIndex==-1)codes="";
			else codes=codes.substring(0,codes.lastIndexOf(","));
		}else if(code==13){
			validation();
		}
		return foat;
	}
	function validation(){
		$.ajax({
			url:"${pageContext.request.contextPath}/admin/validation.vea?codes="+codes,
			type:"post",
			success:function(data){
				if(data.object==1){
					var icon_id='${param.icon_id}';
					var obj=window.top.window.getOpenObj(icon_id);
					var win=window.top.window.f_open("admin", "${pageContext.request.contextPath}/admin/index.vea", "后台管理", "${pageContext.request.contextPath}/page/images/icon_tool.png", 1100, 550, true);
					win.show();
					obj.close();
				}
			},
			error:function(e){
				alert('发送错误！');
			}
		});
	}
</script>
</head>
<body id="body" contenteditable="true" class="demoEdit" onkeydown="return txtChange(event,this.id,this.innerHTML);">
</body>
</html>