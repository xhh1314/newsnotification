<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#assign ctx=request.contextPath />
<head>
<link rel="shortcut icon" href="${ctx}/admin/images/favicon.png"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台登录</title>
<!-- 引入bootstrap文件 和jquery -->
<script type="text/javascript" src="${ctx}/jquery-3.1.0.js"></script>
<link rel="stylesheet" href="${ctx }/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="${ctx }/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
//无法登陆提示
function failHint(){
	alert("无法找回密码哦！");
}


</script>
<style type="text/css">
body{background:#F7FAFC;}
xxxx{border: 1px solid black;}
.loginBody{width:500px;position:relative;margin:auto auto;margin-top:10%;border:1px solid #F7F7F9;}
.login{width:70%;position:relative;margin:auto auto;margin-top:5%;border:1px solid #F7F7F9;}
.loginGroup input{display:block;height:40px;width:100%;border:1px solid #D5D5D5;}
button{width:100%;margin-top:20px;font-size:20px !important;}
.login-fault{text-align:right;margin-top:10px;}
.title{margin-top:10%;text-align:center;}
.title h1{font-size:50px;color:#0F88EB;}
.message{color:red;}
.failHint{text-decoration:none;cursor:pointer;}
</style>

</head>
<body>
<div class="loginBody">
<div class="title">
<h1>报道提示</h1>
</div>
<div class="login" >
<form action="${ctx}/user/login"  method="post" >
<div class="loginGroup">
<input type="text"  name="name" placeholder="用户名"  /><span class="message" >${message!}</span>
<input type="password" name="password" placeholder="密码" />
</div>
<button type="submit" class="btn btn-primary">登录</button>
<!-- previousUri 用于记录登录之前的页面 -->
<input type="hidden"  name="previousUri" value="${previousUri!}">
<input type="hidden" name ="webToken" value="${webToken?if_exists}">
</form>
<div class="login-fault">
<a onclick="failHint()" class="failHint">无法登陆？</a>
</div>
</div>
</div>
</body>
</html>