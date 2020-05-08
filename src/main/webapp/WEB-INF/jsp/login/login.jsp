<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>active4j-oa演示系统</title>
    <meta name="keywords" content="active4j-oa演示系统">
    <meta name="description" content="active4j-oa演示系统">
    <link rel="shortcut icon" href="static/bootstrap/image/favicon.ico"> 
    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/font-awesome.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/animate.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/style.min.css" rel="stylesheet">
    <link href="static/toastr/css/toastr.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>
<body class="gray-bg">
    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                <h3 class="logo-name">YC</h3>
            </div>
            <h3>active4j-oa演示系统</h3>
            <form class="form-horizontal m-t" id="signupForm" action="login" method="post">
                <div class="form-group">
                    <input type="text" name="userName" id="userName" value="admin" class="form-control" placeholder="用户名" required="">
                </div>
                <div class="form-group">
                    <input type="password" name="password" id="password" value="123456" class="form-control" placeholder="密码" required="">
                </div>
                <div class="form-group">
                    <input id="randCode" name="randCode" type="text" class="form-control" placeholder="验证码" required="" style="width: 60%">
                    <div style="float: right; margin-top: -3.4rem;">
                         <img id="randCodeImage" src="verCode" />
                    </div>
                </div>
                <button type="button" class="btn btn-primary block full-width m-b" onclick="doSubmit()">登 录</button>
                
                <p class="text-muted text-center"> <a href="https://github.com/yunchaoyun/active4j-oa">github</a> | <a href="https://gitee.com/active4j/active4j-oa">码云</a>
                </p>
            </form>
        </div>
    </div>
    <script src="static/jquery/js/jquery.min.js"></script>
    <script src="static/bootstrap/js/bootstrap.min.js"></script>
    <script src="static/bootstrap/js/qq.js"></script>
    <script src="static/validate/js/jquery.validate.min.js"></script>
    <script src="static/validate/js/messages_zh.min.js"></script>
    <script src="static/login/js/login.js"></script>
    <script src="static/toastr/js/toastr.min.js"></script>
</body>


</html>

