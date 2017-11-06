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
//非空字符正则
var patter = new RegExp("\\S", "g");
var patter2 = new RegExp("\\s+", "g");
function tagModify(tid) {
	var tagNameHtml=$(".tagName[tid="+tid+"]").html();
	$("#newTagName").val(tagNameHtml);
	$("#newTagName").attr("tid",tid);
	$('.modifyTagModal').modal('show');
}
function newTag() {
	$('.addTagModal').modal('show');
}

// 新增标签访问后台操作
function addTagAction() {
	var tagNameValue=$("#addTagName").val();
	if(!patter.test(tagNameValue)){
		alert("标签名称不能为空！");
		return false;
	}
	
   window.location.href=pageContext+"/admin/addTag?name="+tagNameValue;

}

// 修改标签访问后台操作
function tagModifyAction() {
	var tagValue=$("#newTagName").val();
	var tid=$("#newTagName").attr('tid');
	if(!patter.test(tagValue)){
		alert("标签不能为空！");
		return false;
	}
	if(patter2.test(tagValue)){
		alert("单个标签不能包含空格！");
		return false;
	}
	$.ajax({
		url:pageContext+"/admin/updateTag/"+tid+"?name="+tagValue,
		type:"put",
		dataType:'json',
		success:function(data){
			if(data.meta.success==true){
				$(".tagName[tid="+tid+"]").html(tagValue);
			}else{
				alert("更新失败！"+data.meta.message);
			}
		},
		error:function(data){
			alert("系统发生错误!"+data.object);
		}
	});
	

}

// 删除标签操作
function tagDelete(tid) {
	//数字正则
	var patter = new RegExp("[0-9]+", "g");
  if(!patter.test(tid)){
	  alert("删除失败，id不能为空！");
	  return false;
  }
  $.ajax({
	  url:pageContext+"/admin/deleteTag/"+tid,
	  type:"delete",
	  dataType:"json",
	  success:function(data){
		  if(data.meta.success==true){
			  $("tr[tid="+tid+"]").remove();
		  }else{
			  alert(data.meta.message);
		  }
		  
	  },
	  error:function(data){
		  alert("系统发生错误！");
	  }
	  
  });
	
}