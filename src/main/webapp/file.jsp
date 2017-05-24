<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/Veasion/file/upFile.vea" method="post" enctype="multipart/form-data">  
        <input id="myfile" name="myfile" type="file" /> 
        <input type="submit" value="提交" />${result}
    </form>  
         下载：  
    <a href="/Veasion/file/downloadFile.vea?filename=ws1.jpg">ws1.jsp</a>  
       ${errorResult}
</body>
</html>