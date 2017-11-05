$(window).on('load',function(){
	initialPage();
	initialStatus();
	});

function initialPage() {
	// var a=document.getElementById("pageItem");
	// 如果总页数小于等于1就不显示页码
	if (totalPage <= 1) {
		$("#pageMenu").hide();
		$("#pagePreviousImg").hide();
		$("#pageNextImg").hide();
		return false;
	}
	for (var i = beginPage; i <= endPage; i++) {
		var a = document.createElement('a');
		a.href = pageContext+"/admin/contentPage?action=assign&currentPage=" + i
				+ "&beginPage=" + beginPage + "&endPage=" + endPage;
		a.innerHTML = i;
		a.className = "item";
		if (i == currentPage) {
			a.style.border = "none";
			a.style.pointerEvents = "none";
			a.style.background = "#337AB7";
			a.style.color = "white";
		}
		$("#pageNext").before(a);
	}

	if (currentPage == 1) {
		$("#pagePrevious").addClass("disabled");
		$("#pagePrevious").css("cursor", "not-allowed");
	}
	if (currentPage == totalPage) {
		$("#pageNext").addClass("disabled");
		$("#pageNext").css("cursor", "not-allowed");
	}
}

function initialStatus() {
   $(".publishbutton").each(function(){
   var status=$(this).attr('status');
   if(status==1){
	   $(this).addClass("disabled");
       $(this).removeClass("red");
   }else if(status==0){
	   $(this).addClass("red");
   }
	   });
}


// 下一页
function pageNext() {
	if (currentPage == totalPage)
		return;
	document.location.href = pageContext+"/admin/contentPage?action=next&currentPage="
			+ currentPage + "&beginPage=" + beginPage + "&endPage=" + endPage;
}
// 上一页
function pagePrevious() {
	if (currentPage == 1)
		return;
	document.location.href = pageContext+"/admin/contentPage?action=previous&currentPage="
			+ currentPage + "&beginPage=" + beginPage + "&endPage=" + endPage;
}
//删除函数
function contentDelete(cid) {
	var delMessage = "您确定要删除么！";
	if (confirm(delMessage) != true)
		return;
	$.ajax({
				url : pageContext+"/admin/deleteContent/" + cid,
				type : "delete",
				dataType : "json",
				success : function(data) {
					if (data.meta.success == true) {
						$("tr[cid='+cid+']").remove();
						window.location.href = pageContext+"/admin/contentPage?action=assign&currentPage="
								+ currentPage
								+ "&beginPage="
								+ beginPage
								+ "&endPage=" + endPage;
					} else {
						alert("删除失败！");
					}
				},
				error : function() {
					alert("系统错误！");
				}

			});

}

function updateStatus(tag){
	var cid=$(tag).attr('cid');
	var status=$(tag).attr('status');
 if(status==1)
	 return false;
 $.ajax({
url:pageContext+"/admin/updateStatus?cid="+cid+"&status=1",
type:"put",
success:function(data){
         if(data.meta.success==true){
      $(tag).attr('status',1)
      $(tag).addClass("disabled");
      $(tag).removeClass("red");
             }
         else{
       alert("更新文章状态失败！"+data.meta.message);
             }
			},
error:function(data){
       alert("系统忙！");
			}
	 });
	
}