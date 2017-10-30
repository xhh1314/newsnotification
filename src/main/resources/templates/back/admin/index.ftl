
<#include "/back/admin/header.ftl">
<div class="row">
    <div class="col-sm-12">
        <h3 class="page-title">文章管理</h3>
    </div>
    <div class="col-md-12">
        <table class="ui celled table contentTable" >
            <thead>
            <tr>
                <th width="35%">文章标题</th>
                <th width="15%">发布时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list contents?if_exists as content>
            <tr cid="${content.cid}">
                <td class="contentTable-frist">
                    <a href="${ctx}/content/${content.cid}">${content.title}</a>
                </td>
                <td class="contentTable-second">${content.receiveTime}</td>
                <td class="contentTable-third">
						<div class="content-conduct">
							<a href="${ctx}/admin/updateContent/${content.cid}" target="_blank" class="btn btn-primary btn-sm waves-effect waves-light m-b-5">
							<i class="fa fa-edit"></i> <span>编辑</span></a> 
								<a href="javascript:void(0)" 
								class="btn btn-danger btn-sm waves-effect waves-light m-b-5"  onclick="contentDelete(${content.cid})"> <i
								class="fa fa-trash-o"></i> <span>删除</span></a> 
						</div>
					</td>
            </tr>
            </#list>
            </tbody>
            <tfoot id="pageMenu">
				<tr>
					<th colspan="3">
						<div class="ui right floated pagination menu footPageModule" >
							<a class="icon item" id="pagePrevious" onclick="pagePrevious()">
								<i id="pagePreviousImg" class="left chevron icon"></i>
							</a> <a class="icon item" id="pageNext" onclick="pageNext()"> <i id="pageNextImg"
								class="right chevron icon"></i>
							</a>
						</div>
					</th>
				</tr>
			</tfoot>
        </table>
    </div>
</div>
<!-- 正文容器底线 -->
<footer class="footer text-right">
    2017 © <a href="http://www.haiwainet.cn" target="_blank" style="text-decoration:none;color:#58666E">海外网</a>.
</footer>
</div>
</div>
</div>
</div>
</body>
<style type="text/css">
.contentTable{width:1000px !important;font-size:16px;}
.contentTable-frist{width:50%; !important;}
.contentTable-second{width:25% !important;text-align: center !important;}
.contentTable-third{width:25% !important; text-align:center !important;}
td div .content-conduct{text-align: center !important;}
td div .content-conduct a{display:inline-block;line-height: 90%;font-size:16px;}
div .footPageModule{font-size:12px !important;}
</style>
<script type="text/javascript" src="${ctx}/jquery-3.1.1.min.js"></script>
<link rel="stylesheet" href="${ctx}/semanticui/dist/semantic.min.css">
<script src="${ctx}/semanticui/dist/semantic.min.js"> </script> 
<script type="text/javascript">
var currentPage=${page?if_exists.currentPage};
var beginPage=${page?if_exists.beginPage};
var endPage=${page?if_exists.endPage};
var totalPage=${page?if_exists.totalPage};

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
		a.href = "${ctx}/admin/contentPage?action=assign&currentPage=" + i
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
// 下一页
function pageNext() {
	if (currentPage == totalPage)
		return;
	document.location.href = "${ctx}/admin/contentPage?action=next&currentPage="
			+ currentPage + "&beginPage=" + beginPage + "&endPage=" + endPage;
}
// 上一页
function pagePrevious() {
	if (currentPage == 1)
		return;
	document.location.href = "${ctx}/admin/contentPage?action=previous&currentPage="
			+ currentPage + "&beginPage=" + beginPage + "&endPage=" + endPage;
}
//删除函数
function contentDelete(cid) {
	var delMessage = "您确定要删除么！";
	if (confirm(delMessage) != true)
		return;
	$.ajax({
				url : "${ctx}/admin/deleteContent/" + cid,
				type : "delete",
				dataType : "json",
				success : function(data) {
					if (data.meta.success == true) {
						$("tr[cid='+cid+']").remove();
						window.location.href = "${ctx}/admin/contentPage?action=assign&currentPage="
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
</script>
<#include "/back/admin/footer.ftl">