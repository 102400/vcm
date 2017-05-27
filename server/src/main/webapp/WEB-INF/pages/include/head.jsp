<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title></title>
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="/css/general.css" rel="stylesheet" type="text/css"/>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <style>
        body{
            padding-top: 0px;
        }
        .starter{
            padding: 40px 15px;
            text-align: center;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="/" class="navbar-brand">VCM</a>
        </div>
        
        <!-- 搜索框 -->
        <form action="/search" method="get" class="navbar-form navbar-left" role="search">
	        <div class="form-group">
	        	<!-- <input type="hidden" name="type" value="nameZh"> -->
	        	<input type="text" name="q" class="form-control" size="25" placeholder="nameZh | imdbId | doubanId">
	        </div>
	        <button type="submit" class="btn btn-default">O</button>
      	</form>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="${'/' == requestURI ? 'active' : ''}"><a href="/">首页</a></li>
	            <c:if test="${isLogin}">
	                <li class="${'/recommender' == requestURI ? 'active' : ''}"><a href="/recommender">推荐</a></li>
	            	<li class="${'/new' == requestURI ? 'active' : ''}"><a href="/new">导入</a></li>
                </c:if>
                <li class="${'/mobile' == requestURI ? 'active' : ''}"><a href="/mobile">移动端</a></li>
             </ul>
             <ul class="nav navbar-nav navbar-right">
             <c:choose>
                 <c:when test="${!isLogin}">
	                <li class="${'/register' == requestURI ? 'active' : ''}"><a href="/register" >注册</a></li>
	                <li class="${'/login' == requestURI ? 'active' : ''}"><a href="/login" >登陆</a></li>
                </c:when>
              	<c:otherwise>
	            	<li class="dropdown">
	          		<a href="#" class="dropdown-toggle" data-toggle="dropdown">${nickname}<span class="caret"></span></a>
		          		<ul class="dropdown-menu" role="menu">
		            	<li><a href="/people/${userId}">我的主页</a></li>
		            	<li><a href="/message">消息</a></li>
		            	<li><a href="/settings">设置</a></li>
		            	<li class="divider"></li>
		            	<li><a href="/logout">退出</a></li>
		          		</ul>
	       	 		</li>
            	</c:otherwise>
            </c:choose>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>