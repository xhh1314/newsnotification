//重置密码js   
function resetPassword() {
	$('.ui.modal').modal('show');
}
function onApprove() {
	var oldPassword = $("#oldPassword").val();
	var newPassword = $("#newPassword").val();
	// 非空字符正则
	var patter = new RegExp("\\S", "g");
	if (!patter.test(oldPassword) || !patter.test(newPassword))
		return false;
	var password = {
		"oldPassword" : oldPassword,
		"newPassword" : newPassword
	};
	password = JSON.stringify(password);
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

function tagModify(tid) {
	$('.modifyTagModal').modal('show');
}
function newTag() {
	$('.addTagModal').modal('show');
}

// 新增标签访问后台操作
function addTagAction() {

}

// 修改标签访问后台操作
function tagModifyAction() {

}

// 删除标签操作
function tagDelete(tid) {

}