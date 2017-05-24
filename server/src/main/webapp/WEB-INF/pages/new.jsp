<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>new</title>
</head>
<body>
<jsp:include page="include/head.jsp"></jsp:include>
<c:if test="${userId == 1}">
	<h3>批量新建(正在执行的任务有:${batchNewThreadCount}个)</h3>
	<p>尽量单个线程</p>
	当前任务状态:<br />
	<c:forEach items="${batchNewThreadProgressMap}" var="entry">
		${entry.key}:${entry.value}%<br />
    </c:forEach> 
	<h6>强行终止所有执行任务(并不会瞬间停止)</h6>
		<form class="form-inline">
	    <div class="form-group">
	        <button type="button" id="stopBatchNewButton" class="btn btn-default">强行停止</button>
	    </div>
	    </form>
	<p>输入地址，抓取此页最近看过的用户及用户所有看过电影和评分</p>
	
	<form class="form-inline">
    <div class="form-group">
        <input type="text" id="doubanId" class="form-control" placeholder="doubanId">
        <button type="button" id="batchNewButton" class="btn btn-default">批量增加</button>
    </div>
    </form>
    <span id="batchMessage" style="color:#F00"></span>
	<hr>
</c:if>
<h3>输入doubanId抓取新建</h3>
<form class="form-inline">
    <div class="form-group">
        <input type="text" id="doubanId" class="form-control" placeholder="doubanId">
        <button type="button" id="newButton" class="btn btn-default">增加</button>
    </div>
</form>
<span id="message" style="color:#F00"></span>


new.jsp
<script type="text/javascript">
$(function () {
    
    $("#newButton").click(function() {
        var doubanId = $("#doubanId").val();
        var html = $(this).html();
        
        if(doubanId!=null&&doubanId!="") {
            var sendData = {"doubanId":doubanId};
            
            $.ajax({
                type: "POST",
                url: "/new",
                dataType: "json",
                contentType: "application/json",               
                data: JSON.stringify(sendData),
                success : function(data) {
                    var json = JSON.parse(JSON.stringify(data));
                    if(json.isSuccess) {
                        $("#message").html("增加成功 movie:<a href=\"/movie/" + json.object.doubanId + "\">" + json.object.nameZh + "</a>");
                    }
                    else {
                    	$("#message").html("增加失败");
                    }
                }
            });
        }
        else {
            return;
        }
    });

    $("#batchNewButton").click(function() {
        var doubanId = $("#doubanId").val();
        var html = $(this).html();
        
        if(doubanId!=null&&doubanId!="") {
            var sendData = {"doubanId":doubanId};
            
            $.ajax({
                type: "POST",
                url: "/new/batch",
                dataType: "json",
                contentType: "application/json",               
                data: JSON.stringify(sendData),
                success : function(data) {
                    var json = JSON.parse(JSON.stringify(data));
                    if(json.isSuccess) {
                        $("#batchMessage").html("成功开启线程");
                    }
                    else {
                        $("#batchMessage").html("线程启动失败");
                    }
                }
            });
        }
        else {
            return;
        }
    });

    $("#stopBatchNewButton").click(function() {
        var html = $(this).html();
        
        if(true) {
            var sendData = {"stop":true};
            
            $.ajax({
                type: "POST",
                url: "/new/batch/stop",
                dataType: "json",
                contentType: "application/json",               
                data: JSON.stringify(sendData),
                success : function(data) {
                    var json = JSON.parse(JSON.stringify(data));
                    if(json.isSuccess) {
                        $("#batchMessage").html("成功停止线程");
                    }
                    else {
                        $("#batchMessage").html("停止失败");
                    }
                }
            });
        }
    });
    
});
</script>
</body>
</html>