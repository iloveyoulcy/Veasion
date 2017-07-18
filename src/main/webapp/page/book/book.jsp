<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推荐书籍 | Veasion | 一个后端程序员的笔记</title>
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.ico" />
<style>
.container {
	position: relative;
	width: 880px;
	margin: 0 auto;
	padding: 20px 16px;
}
.body-wrap {
	padding: 0px 0 40px;
	margin-left: 30px;
	min-height: calc(100vh - 340px)
}
.post-card {
	margin-top: -150px;
	min-height: 100px;
	padding: 5px 35px 35px 35px;
	background: #fff;
	border-radius: 4px;
	box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
	margin-top: 8px;
}
.post-card-title {
	font-size: 32px
}
.post-content {
	padding-bottom: 20px;
	line-height: 1.8;
}
.post-content p a {
	margin-right: 35px;
}
</style>
<script src="${pageContext.request.contextPath}/jquery/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
</head>

<body>
	<div class="container body-wrap">
		<div class="post-card">
			<h1 class="post-card-title">推荐书籍</h1>
			<div class="post-content">
				<h2 id="Java">
					<a href="#Java" class="headerlink" title="Java"></a> Java
				</h2>
				<p>
					<a target="_blank" title="Effective Java"
						href="https://book.douban.com/subject/3360807/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s3479802.jpg"
						class="book-cover">
					</a> <a target="_blank" title="Thinking in Java"
						href="https://book.douban.com/subject/3187833/"> <img
						width="96" height="128"
						src="https://img1.doubanio.com/lpic/s3229017.jpg"
						class="book-cover">
					</a> <a target="_blank" title="编写高质量代码"
						href="https://book.douban.com/subject/7059903/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s7015915.jpg"
						class="book-cover">
					</a> <a target="_blank" title="深入分析Java Web技术内幕"
						href="https://book.douban.com/subject/25953851/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s29237536.jpg"
						class="book-cover">
					</a> <a target="_blank" title="Java 8函数式编程"
						href="https://book.douban.com/subject/26346017/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s28028344.jpg"
						class="book-cover">
					</a>
				</p>
				<h2 id="架构">
					<a href="#架构" class="headerlink" title="架构"></a>架构
				</h2>
				<p>
					<a target="_blank" title="Netty权威指南（第2版）"
						href="https://book.douban.com/subject/26373138/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s28055322.jpg"
						class="book-cover">
					</a> <a target="_blank" title="大型网站系统与Java中间件开发实践"
						href="https://book.douban.com/subject/25867042/"> <img
						width="96" height="128"
						src="https://img1.doubanio.com/lpic/s27269837.jpg"
						class="book-cover">
					</a> <a target="_blank" title="大型网站技术架构"
						href="https://book.douban.com/subject/25723064/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s27250675.jpg"
						class="book-cover">
					</a> <a target="_blank" title="Java并发编程的艺术"
						href="https://book.douban.com/subject/26591326/"> <img
						width="96" height="128"
						src="https://img1.doubanio.com/lpic/s28275418.jpg"
						class="book-cover">
					</a> <a target="_blank" title="深入理解Java虚拟机（第2版）"
						href="https://book.douban.com/subject/24722612/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s27458236.jpg"
						class="book-cover">
					</a>
				</p>
				<h2 id="其他语言">
					<a href="#其他语言" class="headerlink" title="其他语言"></a>其他语言
				</h2>
				<p>
					<a target="_blank" title="Go Web编程"
						href="https://book.douban.com/subject/24316255/"> <img
						width="96" height="128"
						src="https://img1.doubanio.com/lpic/s26376659.jpg"
						class="book-cover">
					</a> <a target="_blank" title="Redis实战"
						href="https://book.douban.com/subject/26612779/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s28296984.jpg"
						class="book-cover">
					</a> <a target="_blank" title="七周七语言"
						href="https://book.douban.com/subject/10555435/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s9046771.jpg"
						class="book-cover">
					</a> <a target="_blank" title="Flask Web开发"
						href="https://book.douban.com/subject/26274202/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s27906700.jpg"
						class="book-cover">
					</a> <a target="_blank" title="自己动手写Java虚拟机"
						href="https://book.douban.com/subject/26802084/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s28926306.jpg"
						class="book-cover">
					</a> <a target="_blank" title="可爱的Python"
						href="https://book.douban.com/subject/3884108/"> <img
						width="96" height="128"
						src="https://img1.doubanio.com/lpic/s3901817.jpg"
						class="book-cover">
					</a>
				</p>
				<h2 id="有点意思">
					<a href="#有点意思" class="headerlink" title="有点意思"></a>有点意思
				</h2>
				<p>
					<a target="_blank" title="黑客与画家"
						href="https://book.douban.com/subject/6021440/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s4669554.jpg"
						class="book-cover">
					</a> <a target="_blank" title="图解HTTP"
						href="https://book.douban.com/subject/25863515/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s27283822.jpg"
						class="book-cover">
					</a> <a target="_blank" title="图解密码技术（第3版）"
						href="https://book.douban.com/subject/26822106/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s28830003.jpg"
						class="book-cover">
					</a> <a target="_blank" title="图解TCP/IP : 第5版"
						href="https://book.douban.com/subject/24737674/"> <img
						width="96" height="128"
						src="https://img1.doubanio.com/lpic/s26676928.jpg"
						class="book-cover">
					</a> <a target="_blank" title="编写可读代码的艺术"
						href="https://book.douban.com/subject/10797189/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s10328621.jpg"
						class="book-cover">
					</a>
				</p>
				<p>
					<a target="_blank" title="简约之美"
						href="https://book.douban.com/subject/20445258/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s24642460.jpg"
						class="book-cover">
					</a> <a target="_blank" title="学习vi和Vim编辑器"
						href="https://book.douban.com/subject/6126937/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s6637926.jpg"
						class="book-cover">
					</a> <a target="_blank" title="算法（第4版）"
						href="https://book.douban.com/subject/19952400/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s28322244.jpg"
						class="book-cover">
					</a> <a target="_blank" title="世界是数字的"
						href="https://book.douban.com/subject/24749903/"> <img
						width="96" height="128"
						src="https://img1.doubanio.com/lpic/s26711017.jpg"
						class="book-cover">
					</a> <a target="_blank" title="软件框架设计的艺术"
						href="https://book.douban.com/subject/6003832/"> <img
						width="96" height="128"
						src="https://img1.doubanio.com/lpic/s4656028.jpg"
						class="book-cover">
					</a>
				</p>
				<h2 id="其他">
					<a href="#其他" class="headerlink" title="其他"></a>其他
				</h2>
				<p>
					<a target="_blank" title="人性的弱点全集"
						href="https://book.douban.com/subject/1056295/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s27266511.jpg"
						class="book-cover">
					</a> <a target="_blank" title="羊皮卷全集"
						href="https://book.douban.com/subject/1129995/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s1223340.jpg"
						class="book-cover">
					</a> <a target="_blank" title="乔布斯传"
						href="https://book.douban.com/subject/6723066/"> <img
						width="96" height="128"
						src="https://img1.doubanio.com/lpic/s6783948.jpg"
						class="book-cover">
					</a> <a target="_blank" title="浪潮之巅（第2版）"
						href="https://book.douban.com/subject/24738302/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s27932441.jpg"
						class="book-cover">
					</a> <a target="_blank" title="演讲之禅"
						href="https://book.douban.com/subject/4760725/"> <img
						width="96" height="128"
						src="https://img3.doubanio.com/lpic/s4279952.jpg"
						class="book-cover">
					</a>
				</p>
			</div>
		</div>
	</div>
</body>