<#include "/back/admin/header.ftl">
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">文章管理</h4>
    </div>
    <div class="col-md-12">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th width="35%">文章标题</th>
                <th width="15%">发布时间</th>
                <th width="8%">发布状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list contents?if_exists as content>
            <tr cid="${content.id}">
                <td>
                    <a href="${ctx}/admin/getContent/${content.id}">${content.title}</a>
                </td>
                <td>${content.recieveTime}</td>
                <td>
                   <#if content.status=="publish">
                    <span class="label label-success">已发布</span>
                    <#else>
                    <span class="label label-default">草稿</span>
                    </#if>
                </td>
                <td>
                    <a href="${ctx}/admin/updateContent/${content.id}"
                       class="btn btn-primary btn-sm waves-effect waves-light m-b-5"><i
                            class="fa fa-edit"></i> <span>编辑</span></a>
                    <a href="javascript:void(0)" onclick="delPost(${content.id});"
                       class="btn btn-danger btn-sm waves-effect waves-light m-b-5"><i
                            class="fa fa-trash-o"></i> <span>删除</span></a>
                    <#if content.status == "publish">
                    <a class="btn btn-warning btn-sm waves-effect waves-light m-b-5" href="${ctx}/content/show/${content.id}"
                       target="_blank"><i
                            class="fa fa-rocket"></i> <span>预览</span></a>
                    </#if>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    var tale = new $.tale();
    function delPost(id) {
        tale.alertConfirm({
            title:'确定删除该文章吗?',
            then: function () {
               tale.post({
                   url : '${ctx}/admin/deleteContent',
                   data: {id: id},
                   success: function (result) {
                       if(result && result.success){
                           tale.alertOkAndReload('文章删除成功');
                       } else {
                           tale.alertError(result.msg || '文章删除失败');
                       }
                   }
               });
           }
        });
    }
</script>

<#include "/back/admin/footer.ftl">