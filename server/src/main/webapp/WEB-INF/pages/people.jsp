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
<h3>people(p) | mine(m)</h3>
<hr>
<table class="table-hover float_left" border="1">
<tr>
    <th>ratingCount</th>
    <th>avgRating(s)</th>
</tr>
<tr>
    <td>${pRatingStats.ratingCount}</td>
    <td>${pRatingStats.avgRating}</td>
</tr>
</table>
<table class="table-hover float_left" border="1">
<tr>
    <th>ratingCount</th>
    <th>avgRating(s)</th>
</tr>
<tr>
    <td>${mRatingStats.ratingCount}</td>
    <td>${mRatingStats.avgRating}</td>
</tr>
</table>
<table class="table-hover float_left" border="1">
	<tr>
	    <th>mpRatingRatio(mpr){[m]s/[p]s}</th>
	</tr>
	<tr>
	   <td>${mpRatingRatio}</td>
	</tr>
</table>
<hr class="float_clear_both">
<table class="table-hover float_left" border="1">
<tr>
    <th>rating</th>
    <th>count</th>
</tr>
<c:forEach items="${pRatingCountList}" var="rCount">
    <tr>
        <td>${rCount.rating}</td>
        <td>${rCount.count}</td>
    </tr>
</c:forEach>
</table>
<table class="table-hover float_left" border="1">
<tr>
    <th>rating</th>
    <th>count</th>
</tr>
<c:forEach items="${mRatingCountList}" var="rCount">
    <tr>
        <td>${rCount.rating}</td>
        <td>${rCount.count}</td>
    </tr>
</c:forEach>
</table>
<hr class="float_clear_both">
<h3>[p]original | [m]original | [p]weighted</h3>
<p>[p]y * r... 对[p]进行加权</p>
<hr class="float_clear_both">
<table class="table-hover float_left" border="1">
<tr>
    <th>genresId</th>
    <th>nameZh</th>
    <th>count</th>
    <th>ratio(r)</th>
    <th>avgRating(y)</th>
    <th>avgDifference{y-s}</th>
    <th>avgRatio(ar){y/s}</th>
    <th>weight{(r + ar) * y}</th>
</tr>
<c:forEach items="${pGenresStatsList}" var="gStats">
    <c:choose>
        <c:when test="${gStats.count >= 10 && gStats.avgDifference > 0}">
            <tr bgcolor="#00FF00">
        </c:when>
        <c:otherwise>
            <tr>
        </c:otherwise>
    </c:choose>
        <td>${gStats.genresId}</td>
        <td>${gStats.genresNameZh}</td>
        <td>${gStats.count}</td>
        <td>${gStats.ratio}</td>
        <td>${gStats.avgRating}</td>
        <td>${gStats.avgDifference}</td>
        <td>${gStats.avgRatio}</td>
        <td>${(gStats.ratio + gStats.avgRatio) * gStats.avgRating}</td>
    </tr>
</c:forEach>
</table>
<table class="table-hover float_left" border="1">
<tr>
    <th>genresId</th>
    <th>nameZh</th>
    <th>count</th>
    <th>ratio(r)</th>
    <th>avgRating(y)</th>
    <th>avgDifference{y-s}</th>
    <th>avgRatio(ar){y/s}</th>
    <th>weight{(r + ar) * y}</th>
</tr>
<c:forEach items="${mGenresStatsList}" var="gStats">
    <c:choose>
        <c:when test="${gStats.count >= 10 && gStats.avgDifference > 0}">
            <tr bgcolor="#00FF00">
        </c:when>
        <c:otherwise>
            <tr>
        </c:otherwise>
    </c:choose>
        <td>${gStats.genresId}</td>
        <td>${gStats.genresNameZh}</td>
        <td>${gStats.count}</td>
        <td>${gStats.ratio}</td>
        <td>${gStats.avgRating}</td>
        <td>${gStats.avgDifference}</td>
        <td>${gStats.avgRatio}</td>
        <td>${(gStats.ratio + gStats.avgRatio) * gStats.avgRating}</td>
    </tr>
</c:forEach>
</table>
<table class="table-hover float_left" border="1">
<tr>
    <th>genresId</th>
    <th>nameZh</th>
    <th>count</th>
    <th>ratio(r)</th>
    <th>avgRating(y)</th>
    <th>avgDifference{y-s}</th>
    <th>avgRatio(ar){y/s}</th>
    <th>weight{(r + ar) * y}</th>
</tr>
<c:forEach items="${wpGenresStatsList}" var="gStats">
    <c:choose>
        <c:when test="${gStats.count >= 10 && gStats.avgDifference > 0}">
            <tr bgcolor="#00FF00">
        </c:when>
        <c:otherwise>
            <tr>
        </c:otherwise>
    </c:choose>
        <td>${gStats.genresId}</td>
        <td>${gStats.genresNameZh}</td>
        <td>${gStats.count}</td>
        <td>${gStats.ratio}</td>
        <td>${gStats.avgRating}</td>
        <td>${gStats.avgDifference}</td>
        <td>${gStats.avgRatio}</td>
        <td>${(gStats.ratio + gStats.avgRatio) * gStats.avgRating}</td>
    </tr>
</c:forEach>
</table>
<hr class="float_clear_both">
</body>
</html>