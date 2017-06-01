<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>recommender</title>
</head>
<body>
<jsp:include page="include/head.jsp"></jsp:include>
<table class="table-hover" border="1">
    <thead>FavoriteGenres</thead>
    <tr>
        <th>genresId</th>
        <th>nameZh</th>
    </tr>
    <c:forEach items="${genresList}" var="genres">
        <tr>
	        <th>${genres.genresId}</th>
	        <th>${genres.nameZh}</th>
        </tr>
    </c:forEach>
</table>
<table class="table-hover" border="1">
    <thead>NearestNeighbor</thead>
    <tr>
        <th>neighborId</th>
        <th>distance</th>
    </tr>
    <c:forEach items="${nearestNeighborList}" var="nearestNeighbor">
        <tr>
            <th><a href="/people/${nearestNeighbor.neighborId}">${nearestNeighbor.neighborId}</a></th>
            <th>${nearestNeighbor.distance}</th>
        </tr>
    </c:forEach>
</table>
<table class="table-hover">
	<tr>
	    <th>movieId</th>
	    <th>doubanId</th>
	    <th>ratings(users)</th>
	    <th>nameZh</th>
	    <th>nameO</th>
	    <th>releaseDate</th>
	    <th>runtime</th>
	</tr>
	<c:forEach items="${goodMovieList}" var="movie">
	    <tr>
	        <td><a href="/movie/${movie.doubanId}">${movie.movieId}</a></td>
	        <td>${movie.doubanId}</td>
	        <td>${movie.ratings}(${movie.users})</td>
	        <td>${movie.nameZh}</td>
	        <td>${movie.nameO}</td>
	        <td>${movie.releaseDate}</td>
	        <td>${movie.runtime}</td>
	    </tr>
	</c:forEach>
</table>
recommender.jsp
</body>
</html>