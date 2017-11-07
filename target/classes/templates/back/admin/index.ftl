<#include "/back/admin/header.ftl">
<div class="row">
    <div class="col-sm-12">
        <h3 class="page-title">内容管理</h3>
    </div>
        <table class="ui  fixed  single line selectable celled table contentTable" >
            <thead>
            <tr>
                <th width="25%">标题</th>
                <th width="25%" >标签</th>
                <th width="15%">发布时间</th>
                <th width="35%" >操作</th>
            </tr>
            </thead>
            <tbody>
            <#list contents?if_exists as content>
            <tr cid="${content.cid}">
                <td class="contentTable-frist">
                    <a href="${ctx}/content/${content.cid!}" target="_blank">${content.title}</a>
                </td>
                <td>${content.tags!}</td>
                <td class="contentTable-second">${content.receiveTime!}</td>
                <td class="contentTable-third">
						<div class="small  ui buttons content-conduct ">
							<a href="${ctx}/admin/updateContent/${content.cid!}" target="_blank" class="ui image label "><i class="edit icon"></i>编辑</a> 
							<a href="javascript:void(0)" class="ui image label"  onclick="contentDelete(${content.cid!})"><i class="remove icon"></i>删除</a> 
							<a  onclick="updateStatus(this)"  cid="${content.cid!}" status="${content.status!}" class="ui image label publishbutton"><i class="check circle icon"></i>发布</a>
						</div>
					</td>
            </tr>
            </#list>
            </tbody>
            <tfoot id="pageMenu" class="full-width">
				<tr>
					<th colspan="4">
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
</body>
<script type="text/javascript">
var currentPage=${page?if_exists.currentPage};
var beginPage=${page?if_exists.beginPage};
var endPage=${page?if_exists.endPage};
var totalPage=${page?if_exists.totalPage};
</script>
<script src="${ctx}/myJSCSS/back_index.js"></script>
</html>

<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/table.min.css">
<style type="text/css">
#div{border:1px solid black}
.contentTable{width:1000px !important;font-size:16px;}
.contentTable-frist{}
.contentTable-second{text-align: center !important;}
.contentTable-third{text-align:center !important;}
div .content-conduct{text-align: center !important;}
.ui.table td{padding:5px;}
div .footPageModule{font-size:12px !important;}
.indexfooter{position:absolute;left:250px;bottom: 10px;}
</style>

