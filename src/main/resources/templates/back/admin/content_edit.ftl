  <#include "/back/admin/header.ftl">
  <!-- 正文容器分割线 -->
    <div class="main">

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

</div>


</body>
<script type="text/javascript" src="../laydate/laydate.js"></script>
<script type="text/javascript" src="../ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="../ueditor/ueditor.all.js"></script>
<script type="text/javascript">

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
</html>