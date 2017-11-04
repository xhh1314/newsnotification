<!DOCTYPE html>
<html>
<#assign ctx=request.contextPath />
<head>
    <meta charset="utf-8"/>
    <title>新闻提示</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta content="biezhi" name="author"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="shortcut icon" href="${ctx}/admin/images/favicon.png"/>
    <link href="${ctx}/admin/css/style.min.css?v=v1.0" rel="stylesheet" type="text/css">
<style type="text/css">
.resetPasswordInput{text-align:center;}
.resetPasswordInput .input {width:80%;}

</style>
</head>
<script type="text/javascript" src="${ctx}/jquery.min.js"></script>
<link rel="stylesheet" href="${ctx}/semanticui/dist/semantic.min.css">
<script src="${ctx}/semanticui/dist/semantic.min.js"> </script> 
<script type="text/javascript">
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

<div class="ui mini test modal">
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