  <#include "/back/admin/header.ftl">
    <div class="formContainer" >
            <div class="text-right">
            <a class="header" >发布内容</a>
               <button  type="button" class="ui button  returnlist" onclick="returnList()">返回列表</button>
                <button type="button" class="ui button  newbutton" onclick="newContent()">新建 </button>
                <button type="button" class="ui inverted red button savebutton" onclick="saveContent(1)">发布</button>
                <button type="button" class="ui  primary button savebutton" onclick="saveContent(0)">保存</button>        
            </div>
    
        <form  class="ui fluid form" id="articleForm" method="post">
        <!-- int 类型的值，后台在序列化的时候应该设置为0 -->
            <input type="hidden" name="cid" id="cid" value="${content?if_exists.cid!}" />
            <input type="hidden" name="status" value="${content?if_exists.status!}" id="status"/>
            <input type="hidden" name="content" id="content" value=""/>
            <input type="hidden" name="tags"  id="tags" value="${content?if_exists.tags!}"/>
            <div  class="field">
			<input class="contentTitle" placeholder="请输入文章标题（必填）" name="title" id="title" value="${content?if_exists.title! }"/><a id="titleInfo" style="color:red"></a>
            </div>
            <div class="fields articleForm-second">
             <div class="field">
                <input name="receiveTime" id="receiveTime" type="text" class="contentDate" placeholder="请选择日期（必填）"
                       value="${content?if_exists.receiveTime!}"/><a id="receiveTimeInfo" style="color:red"></a>
            </div>
            <div class="field" >
                <select  name="tagArrayInt" id="tagArrayInt" class="ui search selection dropdown contentTags" multiple="">
                <option value="">请选择标签</option>
                <#list tags?if_exists as tag>
                <option value="${tag.name}">${tag.name}</option>
                </#list>
                </select>
                <a id="tagInfo" style="color:red"></a>
            </div>
            <div class="ui ignored info message saveSuccessMessage">
			<a>保存成功</a>
		</div>
            </div>
		
		<div id="md-container">
                <!-- 加载uEdit编辑器的容器 -->
    <script id="container" name="uEdit" type="text/plain">
${content?if_exists.content!}
    </script>
            </div>
           
            

        </form>
    </div>
  

 

</div>
</body>
<script type="text/javascript" src="${ctx}/semanticui/dist/components/form.min.js"></script>
<script type="text/javascript" src="${ctx}/semanticui/dist/components/popup.min.js"></script>
<script type="text/javascript" src="${ctx}/semanticui/dist/components/dropdown.min.js"></script>
<script type="text/javascript" src="${ctx}/laydate/laydate.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
</script>
<script src="${ctx}/myJSCSS/content_edit.js"></script>
<script type="text/javascript">

</script>

<style>
#div {border: 1px solid black;}
 div .page-title{display:inline-block;}
 .text-right{margin-left:2px;display:inline-block;width:1000px !important;}
 .text-right .header{float:left;font-size: 18px;font-weight: bolder;margin-top:5px;color:black;}   
 .text-right button{float:right;}
 .returnlist{line-height:30px;color: black;border: none;}  
 .savebutton{line-height:30px;width:80px;margin-left:10px;}
 .publishbutton{line-height:30px;width:80px;margin-left:10px;}
 .newbutton{line-height:30px;width:80px;margin-left:10px;}
 .contentTitle{width:1000px !important;}
 .contentDate{width:200px !important;}
 .contentTags{margin-left:10px;min-width:400px !important;z-index: 1000 !important;}
 .page-title{margin-left:5px;}
 .saveSuccessMessage{font-size:16px;background:#D9EDF7;color:#82CDF3;display:none;margin-left:25px;padding:5px;}
</style>
</html>