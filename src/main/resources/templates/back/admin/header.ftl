<!DOCTYPE html>
<#assign ctx=request.contextPath />
<html>


<script type="text/javascript" src="${ctx}/jquery-3.1.1.min.js"></script>
<!--<script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>-->
<!--&lt;!&ndash; 最新版本的 Bootstrap 核心 CSS 文件 &ndash;&gt;-->
<!--<link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">-->
<!--&lt;!&ndash; 可选的 Bootstrap 主题文件（一般不用引入） &ndash;&gt;-->
<!--<link rel="stylesheet" href="../bootstrap/css/bootstrap-theme.min.css">-->
<!-- <script type="text/javascript" src="../semanticui/dist/semantic.min.js"></script> -->
<!-- <link rel="stylesheet" type="text/css" href="../semanticui/dist/semantic.min.css"> -->
<script type="text/javascript" src="${ctx}/semanticui/dist/components/modal.min.js"></script>
<script type="text/javascript" src="${ctx}/semanticui/dist/components/transition.min.js"></script>
<script type="text/javascript" src="${ctx}/semanticui/dist/components/dimmer.min.js"></script>
<!--分别导入semantic-UI的不同模块css文件，用到哪个模块导入哪个模块!-->
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/reset.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/site.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/label.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/header.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/menu.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/button.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/item.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/container.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/icon.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/divider.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/image.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/modal.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/popup.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/dimmer.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/transition.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/form.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/input.min.css">
<head>
<link rel="shortcut icon" href="${ctx}/image/favicon.png"/>
<meta charset="UTF-8">
<title>新闻提示</title>
<style type="text/css">
**div{border: 1px solid black;}
**body{border: 1px solid red;}
.top{position:fixed;top:0;width:100%;height:80px;z-index:100;border-bottom:2px solid #F0F0F0;background: #F9F9F9;}
.page-content{position:relative;}
.page-content div.left{position:fixed;top:80px;left:0;width:250px;height:100%;background: #F9F9F9} 
.page-content .main {position:absolute;left:250px;top:80px;width:100%;overflow:hidden;padding-left: 10px;padding-top:10px;}
.header-container{position:relative;text-align: center;display: inline-block;line-height: 80px;width:100%;}
.header-container .left{float: left;display:inline-block;line-height:80px;width:250px;margin-top:10px;}
.header-container .center{margin:0px auto;display:inline-block;line-height:80px;width:400px;}
.header-container .right{float:right;display:inline-block;line-height:80px;width:200px;margin-top:10px;}
.left .ui.menu{width:250px;}
.header-container .header.left a{font-size:24px;}
.header-container .header.center a{font-size: 36px;color: #EA6F5A;}
.header-container .header.right .resetPassword {cursor:pointer; }



.resetPasswordInput{text-align:center;}
.resetPasswordInput .input {width:80%;}
</style>
<script type="text/javascript">
//项目根目录全局变量
var pageContext="";
var pageNumberFlag="${pageNumberFlag!"0"}";

    //点击菜单激活事件
    $(document).ready(function () {
        $(".menu .item").click(function () {
            $(".menu .item").each(function () {
                $(this).removeClass("active");
            });
            $(this).addClass("active");

        });
    });
 //重置密码js   

 $(window).on('load',function(){
	 $(".menu .item").each(function(){
    if($(this).attr('pageFlag')==pageNumberFlag){
     $(this).addClass("active");
        }
		 });
	 });
function resetPassword(){
	$('.ui.modal').modal('show');	
}
	function onApprove() {
		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		//非空字符正则
		var patter = new RegExp("\\S", "g");
		if (!patter.test(oldPassword) || !patter.test(newPassword))
			return false;
		var password = {
			"oldPassword" : oldPassword,
			"newPassword" : newPassword
		};
		password=JSON.stringify(password);
		$.ajax({
			url : "${ctx}/user/resetPassword",
			type : "put",
			dataType : "json",
			contentType : "application/json",
			data : password,
			success : function(data) {
				if (data.meta.success == true) {
					return true;
				} else {
					alert("修改密码失败!");
				}

			},
			error : function(data) {
				alert("系统发生错误!");
			}
		});
	}
</script>
</head>
<div class="ui mini  modal">
    <div class="header">
     修改您的密码
    </div>
	<div class="content resetPasswordInput">
		<div class="ui input">
			<input type="password"  name="oldPassword" id="oldPassword" placeholder="原密码">
		</div>
		<div class="ui input">
			<input type="text" name="newPassword" id="newPassword" placeholder="新密码">
		</div>
	</div>
	<div class="actions">
      <div class="ui negative button">
        取消
      </div>
      <div class="ui positive right labeled icon button" onclick="onApprove()">
        确认
        <i class="checkmark icon"></i>
      </div>
    </div>
  </div>

<body>
<div class="top">
		<div class="header-container">
			<div class="header left"></div>
			<div class="header center">
				<a class="ui huge header">新闻提示</a>
			</div>
			<div class="header right">
				<a class="ui blue basic label resetPassword" onclick="resetPassword()">修改密码</a> 
				<a class="ui red basic label" href="${ctx}/user/logout"><i class="sign out icon"></i>注销</a>
			</div>
		</div>
	</div>
<div class="page-content">
    <div class="left">

        <div class="ui grey  inverted  vertical pointing menu">
            <a href="${ctx }/admin/index?pageNumberFlag=contentManage" class="grey item " pageFlag="contentManage">
                文章管理
            </a>
            <a href="${ctx}/admin/contentEdit?pageNumberFlag=contentNew" class="yellow item" pageFlag="contentNew">
                新建文章
            </a>
            <a href="${ctx}/admin/listTag?pageNumberFlag=tagManage" class="grey item " pageFlag="tagManage">
                标签管理
            </a>
        </div>

    </div>
  <!-- 正文容器分割线 -->
    <div class="main">