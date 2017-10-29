
<#include "/back/admin/header.ftl">
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">文章管理</h4>
    </div>
    <div class="col-md-12">
        <table class="table table-striped table-bordered" >
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
                <td>
                    <a href="${ctx}/admin/getContent/${content.cid}">${content.title}</a>
                </td>
                <td>${content.receiveTime}</td>
                <td>
                    <a href="${ctx}/admin/updateContent/${content.cid}"
                       class="btn btn-primary btn-sm waves-effect waves-light m-b-5"><i
                            class="fa fa-edit"></i> <span>编辑</span></a>
                    <a href="javascript:void(0)" onclick="delContent(${content.cid})"
                       class="btn btn-danger btn-sm waves-effect waves-light m-b-5"><i
                            class="fa fa-trash-o"></i> <span>删除</span></a>
                    <a class="btn btn-warning btn-sm waves-effect waves-light m-b-5" href="${ctx}/admin/getContent/${content.cid}"
                       target="_blank"><i
                            class="fa fa-rocket"></i> <span>预览</span></a>
                </td>
            </tr>
            </#list>
            </tbody>
            <tfoot id="pageMenu">
				<tr>
					<th colspan="3">
						<div class="ui right floated pagination menu" >
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
<#include "/back/admin/footer.ftl">
<script type="text/javascript" src="${ctx}/jquery-3.1.1.min.js"></script>
<link rel="stylesheet" href="${ctx}/semanticui/dist/semantic.min.css">
<script src="${ctx}/semanticui/dist/semantic.min.js"> </script> 
<script type="text/javascript">
var currentPage=${page?if_exists.currentPage};
var beginPage=${page?if_exists.beginPage};
var endPage=${page?if_exists.endPage};
var totalPage=${page?if_exists.totalPage};

function initialPage(){
//var a=document.getElementById("pageItem");
//如果总页数小于等于1就不显示页码
if(totalPage<=1){
	$("#pageMenu").hide();
	$("#pagePreviousImg").hide();
	$("#pageNextImg").hide();
	return false;
}
for(var i=beginPage;i<=endPage;i++){
var a=document.createElement('a');
a.href="${ctx}/admin/contentPage?action=assign&currentPage="+i+"&beginPage="+beginPage+"&endPage="+endPage;
a.innerHTML=i;
a.className="item";
if(i==currentPage){
a.style.border="none";
a.style.pointerEvents="none";
a.style.background="#337AB7";
a.style.color="white";
}
$("#pageNext").before(a);
}

if(beginPage==1){
$("#pagePrevious").addClass("disabled");
$("#pagePrevious").css("cursor","not-allowed");
}
if(endPage==totalPage)
	{
	$("#pageNext").addClass("disabled");
$("#pageNext").css("cursor","not-allowed");
	}
}
//下一页
function pageNext(){
	if(currentPage==totalPage) return;
	document.location.href="${ctx}/admin/contentPage?action=next&currentPage="+currentPage+"&beginPage="+beginPage+"&endPage="+endPage;
	}
//上一页
	function pagePrevious(){
		if(currentPage==1) return;
		document.location.href="${ctx}/admin/contentPage?action=previous&currentPage="+currentPage+"&beginPage="+beginPage+"&endPage="+endPage;
		}
</script>