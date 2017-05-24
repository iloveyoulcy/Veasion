<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<br />
	上传文件：
	<form action="/Veasion/file/upFile.vea" method="post" enctype="multipart/form-data">  
        <input name="fileName" type="file" />
        <input type="submit" value="提交" />
    </form>
    ${result}
    <br />
         下载：  
     <form action="/Veasion/file/downloadFile.vea" method="post">  
        <input name="fileName" type="text" value="ws1.jpg"/>
        <input type="submit" value="下载" />
    </form>
    <span style="color: red;"> ${downloadFileErr }</span>
</body>
</html>