<#include "/back/admin/header.ftl">
<link href="${ctx}/admin/plugins/tagsinput/jquery.tagsinput.css" rel="stylesheet">
<link href="${ctx}/admin/plugins/select2/dist/css/select2-bootstrap.css" rel="stylesheet">
<link href="${ctx}/admin/plugins/toggles/toggles.css" rel="stylesheet">
<style rel="stylesheet">
    #tags_tagsinput {
        background-color: #fafafa;
        border: 1px solid #eeeeee;
    }

    #tags_addTag input {
        width: 100%;
    }

    #tags_addTag {
        margin-top: -5px;
    }

    .mditor .editor{
        font-size: 14px;
        font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    }
    .mditor .backdrop, .mditor .textarea, .mditor .viewer{
        font-size: 14px;
    }
    .markdown-body{
        font-size: 14px;
    }
    .note-toolbar {
        text-align: center;
    }

    .note-editor.note-frame {
        border: none;
    }

    .note-editor .note-toolbar {
        background-color: #f5f5f5;
        padding-bottom: 10px;
    }

    .note-toolbar .note-btn-group {
        margin: 0;
    }

    .note-toolbar .note-btn {
        border: none;
    }

    #articleForm #dropzone {
        min-height: 200px;
        background-color: #dbdde0;
        line-height:200px;
        margin-bottom: 10px;
    }
    #articleForm .dropzone {
        border: 1px dashed #8662c6;
        border-radius: 5px;
        background: white;
    }
    #articleForm .dropzone .dz-message {
        font-weight: 400;
    }
    #articleForm .dropzone .dz-message .note {
        font-size: 0.8em;
        font-weight: 200;
        display: block;
        margin-top: 1.4rem;
    }
 .xxxx{border: 1px solid black;}   
 .returnlist{line-height:30px;weidth:50px;color: black;border: none;}  
 .savebutton{line-height:30px;weidth:50px;}
 .saveDraft{line-height:30px;weidth:50px;}
 .contentTitle{height:30px;width:1000px;margin-left:5px;}
 .contentTags{height:30px;width:1000px;margin-left:5px;}
 .page-title{margin-left:5px;}
</style>
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">
            发布文章
        </h4>
    </div>
    <div class="col-md-12">

        <input type="hidden" id="attach_url" value="" />

        <form id="articleForm">
            <input type="hidden" name="id" value="" id="cid"/>
            <input type="hidden" name="status" value="" id="status"/>
            <input type="hidden" name="content" id="content-editor"/>
            <div  style="padding: 0 10px 0 0;">
                <input class="form-control contentTitle" placeholder="请输入文章标题（必须）" name="title" required
                       value=""/>
            </div>

            <div class="form-group col-md-6" style="padding: 0 10px 0 0;">
                <input name="tags" id="tags" type="text" class="form-control contentTags" placeholder="请填写文章标签"
                       value=""/>
            </div>
            <div class="clearfix"></div>

            <div id="md-container" class="form-group col-md-12">
                <!-- 加载编辑器的容器 -->
    <script id="container" name="content" type="text/plain">
        这里写你的初始化内容
    </script>
            </div>
           
            
            <div class="clearfix"></div>

            <div class="text-right">
                <button class="btn btn-default waves-effect waves-light  returnlist" href="/admin/article" >返回列表</button>
                <button type="button" class="btn btn-primary waves-effect waves-light savebutton" onclick="subArticle('publish');">
                    保存文章
                </button>
                <button type="button" class="btn btn-warning waves-effect waves-light saveDraft" onclick="subArticle('draft');">
                    存为草稿
                </button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${ctx }/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx }/ueditor/ueditor.all.js"></script>
<script type="text/javascript" >
var ue = UE.getEditor('container',{autoHeight: true});
</script>


<script src="${ctx }/admin/plugins/tagsinput/jquery.tagsinput.min.js"></script>
<script src="${ctx }/admin/plugins/jquery-multi-select/jquery.quicksearch.js"></script>
<script src="${ctx }/admin/js/article.js?v=v1.0" type="text/javascript"></script>
<#include "/back/admin/footer.ftl">

