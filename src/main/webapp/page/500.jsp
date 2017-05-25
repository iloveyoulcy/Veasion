<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Veasion - 500 Internal Server Error!</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/default-init.css" charset="utf-8" />
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.ico" />
    </head>
    <body>
        <div class="wrapper">
            <div class="wrap">
                <div class="content">
                    <div class="logo">
                        <a href="${pageContext.request.contextPath}/index/index.vea" target="_blank">
                            <img border="0" width="153" height="56" alt="Veasion" title="Veasion" src="${pageContext.request.contextPath}/images/logo.jpg"/>
                        </a>
                    </div>
                    <div class="main">
                        <h2>500 Internal Server Error!</h2>
                        <img class="img-500" src="${pageContext.request.contextPath}/images/500.png" title="500: internal error" alt="500: internal error" />
                        <div class="a-500">
                           Please 
                            <a href="https://github.com/veasion/">report</a> it to help us.
                            Return to Veasion Index</a>.
                        </div>
                        <a href="${pageContext.request.contextPath}/index/index.vea" target="_blank">
                            <img border="0" class="icon" alt="Veasion" title="Veasion" src="${pageContext.request.contextPath}/favicon.ico"/>
                        </a>
                    </div>
                    <span class="clear"></span>
                </div>
            </div>
        </div>
        <div class="footerWrapper">
            <div class="footer">
                &copy; 2017
                Powered by <a href="${pageContext.request.contextPath}/index/index.vea" target="_blank">Veasion</a>, ver 1.0
            </div>
        </div>
    </body>
</html>