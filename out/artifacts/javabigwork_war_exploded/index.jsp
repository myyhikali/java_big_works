<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 2018/11/23
  Time: 下午4:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <h3>登录</h3>
        <form class="m-t" role="form">
            <div class="form-group">
                <input type="text" class="form-control" id="login" placeholder="用户名" required="">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" id="password" placeholder="密码" required="">
            </div>
            <button type="submit" class="btn btn-primary block full-width m-b" onclick="pass()">登录</button>
           <%--<a href="#"><small>忘记密码?</small></a> --%>
        </form>
    </div>
</div>

<!-- Mainly scripts -->
<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>

</html>
<script>
    function pass() {
        if(document.getElementById("login").value=="1" &&document.getElementById("password").value=="1"){
            // alert("1111");
            window.open('homepage.jsp');

        }else {
            alert("账号或密码错误");
        }
    }
</script>