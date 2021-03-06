<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>search</title>
</head>
<body>
<jsp:include page="include/head.jsp"></jsp:include>
<c:if test="${imdbMovie != null}">
    <img src="/movie/cover/${imdbMovie.doubanId}" height="100px">
    <a href="/movie/${imdbMovie.doubanId}">${imdbMovie.nameZh} ${imdbMovie.nameO}</a>
</c:if>
<c:forEach items="${movieList}" var="movie">
    <img src="/movie/cover/${movie.doubanId}" height="100px">
    <a href="/movie/${movie.doubanId}">${movie.nameZh} ${movie.nameO}</a>
    <hr>
</c:forEach>
</body>
</html>