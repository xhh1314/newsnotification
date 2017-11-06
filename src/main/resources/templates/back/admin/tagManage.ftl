<#include "/back/admin/header.ftl">

<div class="tagManageContainer">
 <div>
 <a class="tagHeader ui header">标签管理</a> <button type="button" class="ui button  newbutton" onclick="newTag()">新增</button>
 </div>
  <table class="ui  fixed  single line selectable celled table contentTable" >
            <thead>
            <tr>
                <th style="width:40%;">标签名称</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list tags?if_exists as tag>
            <tr tid="${tag.id}">
                <td class="contentTable-frist">
                 <a class="tagName" tid="${tag.id}">${tag.name}</a>
                </td>
                <td class="contentTable-third">
						<div class="small  ui buttons content-conduct ">
							<a  class="ui image label " onclick="tagModify(${tag.id})" ><i class="edit icon"></i>编辑</a> 
							<a class="ui image label"  onclick="tagDelete(${tag.id})"><i class="remove icon"></i>删除</a> 
						</div>
					</td>
            </tr>
            </#list>
            </tbody>
        </table>

</div>


</div>
</body>
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/table.min.css">
<script src="${ctx}/myJSCSS/tagManage.js"></script>
<style>
.tagHeader{font-size:18px;color:black;margin-right: 10px;}
.tagName{color:black;}
.tagName :HOVER {
	color:black !important;
}
</style>
<script type="text/javascript">


</script>

<div class="ui mini modal addTagModal">
    <div class="header">
    新增标签
    </div>
	<div class="content resetPasswordInput">
		<div class="ui input">
			<input type="text" name="tagName" id="addTagName" placeholder="输入标签名称">
		</div>
	</div>
	<div class="actions">
      <div class="ui negative button">
        取消
      </div>
      <div class="ui positive right labeled icon button" onclick="addTagAction()">
        确认
        <i class="checkmark icon"></i>
      </div>
    </div>
  </div>
  
  <div class="ui mini modal modifyTagModal">
    <div class="header">
    修改标签
    </div>
	<div class="content resetPasswordInput">
		<div class="ui input">
			<input type="text" name="newTagName" id="newTagName" tid="">
		</div>
	</div>
	<div class="actions">
      <div class="ui negative button">
        取消
      </div>
      <div class="ui positive right labeled icon button" onclick="tagModifyAction()">
        确认
        <i class="checkmark icon"></i>
      </div>
    </div>
  </div>

</html>
