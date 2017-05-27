<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>people</title>
</head>
<body>
<jsp:include page="include/head.jsp"></jsp:include>
<table class="table-hover" border="1">
<tr>
    <th>rating</th>
    <th>count</th>
</tr>
<c:forEach items="${ratingCountList}" var="rCount">
    <tr>
        <td>${rCount.rating}</td>
        <td>${rCount.count}</td>
    </tr>
</c:forEach>
</table>
<hr>
<table class="table-hover" border="1">
<tr>
    <th>genresId</th>
    <th>nameZh</th>
    <th>count</th>
</tr>
<c:forEach items="${genresCountList}" var="gCount">
    <tr>
        <td>${gCount.genresId}</td>
        <td>${gCount.genresNameZh}</td>
        <td>${gCount.count}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>