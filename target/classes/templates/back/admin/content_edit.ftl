  <#include "/back/admin/header.ftl">
    <div class="formContainer" >
            <div class="text-right">
            <a class="header" >发布文章</a>
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
            <div  class="field" ">
			<input class="contentTitle" placeholder="请输入文章标题（必填）" name="title" id="title" value="${content?if_exists.title! }"/><a id="titleInfo" style="color:red"></a>
            </div>
            <div class="fields articleForm-second">
             <div class="field">
                <input name="receiveTime" id="receiveTime" type="text" class="contentDate" placeholder="请选择日期（必填）"
                       value="${content?if_exists.receiveTime!}"/><a id="receiveTimeInfo" style="color:red"></a>
            </div>
            <div class="field" >
                <input name="tags" id="tag" list="tags" type="text" class="contentTags" placeholder="文章标签"
                       value="${content?if_exists.tags!}"/><a id="tagInfo" style="color:red"></a>
                <datalist id="tags">
                <#list tags?if_exists as tag>
                <option>${tag.name}</option>
                </#list>
                </datalist>
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
<script type="text/javascript" src="${ctx}/laydate/laydate.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>

<script src="${ctx}/myJSCSS/content_edit.js"></script>

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
 .contentTitle{height:30px;width:1000px !important;}
 .contentDate{margin-left:10px;;height:30px;width:200px !important;}
 .contentTags{height:30px;width:400px !important;margin-left:50px ;}
 .page-title{margin-left:5px;}
</style>
</html>