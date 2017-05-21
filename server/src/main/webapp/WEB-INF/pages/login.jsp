<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
boolean isLogin = (boolean)request.getAttribute("isLogin");
if(isLogin) {
	response.sendRedirect("/");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
<script type="text/javascript">
function changeimg(){
	
	document.getElementById("img").src="/images/captcha?" + new Date().getMilliseconds();
}
</script>
</head>
<body>
<jsp:include page="include/head.jsp"></jsp:include>

<div style="color:#F00">${message}</div>
 <form action="/login" method="post" class="form-horizontal" role="form">
    <div class="form-group">
	    <div class="col-sm-10">
	    	<input type="text" name="username" class="form-control" placeholder="用户名或者邮箱" />
	    </div>
	</div>
	<div class="form-group">
	    <div class="col-sm-10">
	    	<input type="password" name="password" class="form-control" placeholder="密码" />
	    </div>
  	</div>
  	<!-- 
  	<div class="form-group">
	    <div class="col-sm-10">
    		<img src="/images/captcha" style="cursor: pointer;" onclick="changeimg();" id="img"/>
    	 </div>
  	</div>
  	<div class="form-group">
	    <div class="col-sm-10">
    		<input type="text" name="captcha" class="form-control" placeholder="验证码" />
    	 </div>
  	</div>
  	-->
  	<div class="form-group">
    <div class="col-sm-10">
    	<div class="checkbox">
        <label>
        	<input type="checkbox" name="remember"> 记住我(42 days)
        </label>
      	</div>
    </div>
  	</div>
    <div class="form-group">
    <div class="col-sm-10">
    	<button type="submit" class="btn btn-default">登录</button>
	</div>
	</div>
</form>
login.JSP
</body>
</html>