<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
</head>
<body>
<br/><br/>
	<c:if test="${param.type=='bgimg'}">
		上传背景：
	</c:if>
	<c:if test="${param.type=='icon'}">
		上传图标：
	</c:if>
	<br/>
	<c:if test="${param.type!=null}">
		<form action="${pageContext.request.contextPath}/admin/desktop/upFile.vea?type=${param.type }" method="post" enctype="multipart/form-data">
	        <input name="fileName" type="file" />
	        <input type="submit" value="上传" />
	    </form>
    </c:if>
</body>
</html>