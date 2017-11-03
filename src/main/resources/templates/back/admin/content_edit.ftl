<#include "/back/admin/header.ftl">

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
 div .page-title{display:inline-block;}
 .text-right{margin-left:550px;display:inline-block;}   
 .returnlist{line-height:30px;width:80px;color: black;border: none;margin-left:10px;}  
 .savebutton{line-height:30px;width:80px;margin-left:10px;}
 .publishbutton{line-height:30px;width:80px;margin-left:10px;}
 .newbutton{line-height:30px;width:80px;margin-left:10px;background:black !important;}
 .contentTitle{height:30px;width:1000px;margin-left:10px;}
 .contentDate{margin-left:10px;;height:30px;width:200px;}
 .contentTags{height:30px;width:400px;margin-left:50px;}
 .page-title{margin-left:5px;}
 .articleForm-second div{display:inline-block;}
</style>

<body class="fixed-left" onload="loadCalender()">
<div id="wrapper">
     <div class="topbar">
        <div class="topbar-left">
            <div class="text-center p-t-10" style="margin: 0 auto;">
                <div class="pull-left" style="padding-left: 10px;border: 0px solid black;">
                    <a href="/admin/index">
                        <img src="${ctx}/admin/images/unicorn.png" width="50" height="50"/>
                    </a>
                    <a style="margin-left:20px;vertical-align:50%;font-size:25px;font-weight:bold;color:#059AEC">海外网</a>
                   
                </div>
            </div>
        </div>
        <div class="navbar navbar-default" role="navigation" style="border: 0px solid black;">
               <span style="line-height: 50px;font-size:30px;margin-left:35%">新闻提示</span>
            <a style="line-height: 50px;margin-left:40%;text-decoration:none;color:#337AB7;" href="${ctx }/user/logout">注销</a>
        </div>
    </div>
    <div class="left side-menu">
        <div class="sidebar-inner slimscrollleft">
            <div id="sidebar-menu">
                <ul>
                <li #if(active=='article') class="active" #end>
                        <a href="${ctx }/admin/index" class="waves-effect #if(active=='article') active #end"><i class="fa fa-list" aria-hidden="true"></i><span> 文章管理 </span></a>
                 </li>
                    <li #if(active=='publish') class="active" #end>
                        <a href="${ctx}/admin/contentEdit" class="waves-effect #if(active=='publish') active #end"><i class="fa fa-pencil-square-o" aria-hidden="true"></i><span> 发布文章 </span></a>
                    </li>
              
                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="content-page">
        <div class="content">
            <div class="container">
<!-- 正文容器分割线 -->
<div class="row">
    <div class="col-sm-12 page-title" >
        <h4 class="page-title">
            发布文章
        </h4>
    </div>
    
            <div class="text-right">
               
                <button type="button" class="ui primary button savebutton" onclick="saveContent(0)">
                    保存
                </button>
               <button type="button" class="ui inverted red button savebutton" onclick="saveContent(1)">
                    发布
                </button>
                <button type="button" class="ui secondary basic button  newbutton" onclick="newContent()">
                    新建
                </button>
                 <button  type="button" class="  btn btn-default waves-effect waves-light  returnlist" onclick="returnList()">返回列表</button>
            </div>
    <div class="col-md-12">
        <input type="hidden" id="attach_url" value="" />
        <form id="articleForm" method="post">
        <!-- int 类型的值，后台在序列化的时候应该设置为0 -->
            <input type="hidden" name="cid" id="cid" value="${content?if_exists.cid!}" />
            <input type="hidden" name="status" value="${content?if_exists.status!}" id="status"/>
            <input type="hidden" name="content" id="content" value=""/>
            <div  style="padding: 0 10px 0 0;">
                <input class="form-control contentTitle" placeholder="请输入文章标题（必填）" name="title" id="title" required
                       value="${content?if_exists.title! }"/>
            </div>
            <div class="form-group articleForm-second">
             <div class="form-group col-md-6" style="padding: 0 10px 0 0;">
                <input name="receiveTime" id="receiveTime" type="text" class="form-control contentDate" placeholder="请选择日期（必填）"
                       value="${content?if_exists.receiveTime!}"/>
            </div>
            <div class="form-group col-md-6" style="padding: 0 10px 0 0;">
                <input name="tags" id="tag" list="tags" type="text" class="form-control contentTags" placeholder="文章标签"
                       value="${content?if_exists.tags!}"/>
                <datalist id="tags">
                <#list tags?if_exists as tag>
                <option>${tag.name}</option>
                </#list>
                </datalist>
            </div>
            </div>
            <div class="clearfix" ></div>

            <div id="md-container" class="form-group col-md-12">
                <!-- 加载uEdit编辑器的容器 -->
    <script id="container" name="uEdit" type="text/plain">

    </script>
            </div>
           
            
            <div class="clearfix"></div>

        </form>
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
<link href="${ctx}/admin/plugins/tagsinput/jquery.tagsinput.css" rel="stylesheet">
<link href="${ctx}/admin/plugins/select2/dist/css/select2-bootstrap.css" rel="stylesheet">
<link href="${ctx}/admin/plugins/toggles/toggles.css" rel="stylesheet">


<!-- 日期控件 -->
<script type="text/javascript" src="${ctx}/laydate/laydate.js"></script>
<script type="text/javascript" src="${ctx }/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx }/ueditor/ueditor.all.js"></script>
<script  src="${ctx}/myJSCSS/my.js"></script>

<script type="text/javascript" >
//执行一个laydate实例
laydate.render({
  elem: '.contentDate',calender:true //指定元素
});
//解决初次加载时显示不出来问题
function loadCalender(){
	laydate.render({
		  elem: '.contentDate',calender:true //指定元素
		});
}
//form序列化转换为json对象 
$.fn.serializeJson=function(){    
    var serializeObj={};    
    var array=this.serializeArray();    
    var str=this.serialize();    
    $(array).each(function(){    
        if(serializeObj[this.name]){    
            if($.isArray(serializeObj[this.name])){    
                serializeObj[this.name].push(this.value);    
            }else{    
                serializeObj[this.name]=[serializeObj[this.name],this.value];    
            }    
        }else{    
            serializeObj[this.name]=this.value;     
        }    
    });    
    return serializeObj;    
} 

//解决初次加载时显示不出来问题
function loadCalender(){
	laydate.render({
		  elem: '#input-newsTime',calender:true //指定元素
		});
} 


var ue = UE.getEditor('container');
//取出uEdit编辑器里content内容
function takeout() {
	var contents;
	// 对编辑器的操作最好在编辑器ready之后再做
	ue.ready(function() {
		// 获取html内容，返回: <p>hello</p>
		contents = ue.getContent();
		// 获取纯文本内容，返回: hello
		// var txt = ue.getContentTxt();
	});
	$("#content").val(contents);
}

//保存后弹出提示信息 实现
var flag=false;
	$('.savebutton').popup({
		content : '保存成功',
		target : $('#md-container'),
		position : 'top center',
		on : 'click',
		onShow : function() {
			if (flag==true)
				return true;
			else
			return false;
		}

	});

	function saveContent(status) {
		$("#status").val(status);
		takeout();
		//var form = document.getElementById("articleForm");
		var formData = $("#articleForm").serializeJson();
		$.ajax({
			async : true,
			url : "${ctx}/admin/saveContent",
			type : "POST",
			contentType : "application/json",
			data : JSON.stringify(formData),
			dataType : "json",
			success : function(data) {
				if (data.meta.success == true) {
					var cid = data.data.cid;
					$("#cid").val(cid);
					$("#status").val(data.data.status);
					flag = true;
				} else {
					flag=false;
					alert(data.meta.message);
				}
			},
			error : function(error) {
				flag=false;
				alert(data.meta.message);
			}
		});

	}

	function returnList() {
		window.location.href = "${ctx}/admin/index";
	}

	function newContent() {
		window.location.href = "${ctx}/admin/contentEdit";
	}
</script>
<#include "/back/admin/footer.ftl">

