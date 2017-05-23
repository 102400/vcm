<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${movie.nameZh}</title>
</head>
<body>
<jsp:include page="include/head.jsp"></jsp:include>
<div>
<h2>${movie.nameZh} ${movie.nameO}</h2>
<img src="/movie/cover/${movie.doubanId}" class="float_left">
<div class="float_right">
<form class="form-inline">
               评分:<input type="text" id="rating" maxlength="2" size="2" placeholder="评分" value="${rating.rating}" class="form-control"/><br />
    <textarea id="comment" rows="4" cols="22" maxlength="128" placeholder="评价" class="form-control">${rating.comment}</textarea><br />
    <button type="button" id="changeRatingAndCommentButton" class="btn btn-default">更改</button>
    <span id="ratingAndCommentMessage"></span>
</form>
</div>
<div class="float_clear_both">
评分:${movie.ratings}(${movie.users}人评价)<br />
类型:<c:forEach items="${genresList}" var="genres">${genres.nameZh} </c:forEach><br />
imdb:<a href="http://www.imdb.com/title/tt${movie.imdbId}">tt${movie.imdbId}</a><br />
douban:<a href="https://movie.douban.com/subject/${movie.doubanId}">tt${movie.doubanId}</a><br />
上映时间:${movie.releaseDate}<br />
片长:${movie.runtime}分钟<br />
剧情简介:<br />${movie.storyline}<br />
</div>
</div>
<script type="text/javascript">
var movieId = ${movie.movieId};

$(function () {
    
    $("#changeRatingAndCommentButton").click(function() {
        var rating = $("#rating").val();
        var comment = $("#comment").val();
        var html = $(this).html();
        
        if(true) {
            var sendData = {"movieId":movieId, "rating":rating, "comment":comment};
            
            $.ajax({
                type: "POST",
                url: "/movie/rating",
                dataType: "json",
                contentType: "application/json",               
                data: JSON.stringify(sendData),
                success : function(data) {
                    var json = JSON.parse(JSON.stringify(data));
                    if(json.isSuccess) {
                        //alert("success!");
                    }
                    else {
                        //alert("fail!");
                    }
                    location.reload();
                }
            });
        }
    });
});
</script>
</body>
</html>