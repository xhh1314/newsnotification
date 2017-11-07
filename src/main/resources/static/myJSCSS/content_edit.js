$(document).ready(function(){
	//标签选择下拉效果js
	$('#tagArrayInt').dropdown({
		on:'hover',
			forceSelection:false,
			fullTextSearch:true,
			match:'value'
	});
	
	
});

$(window).on('load',function(){
    var tagArray=getTagArray();
	$('#tagArrayInt').dropdown('set selected',tagArray);
	
});

//执行一个laydate实例
laydate.render({
  elem: '.contentDate',
  calender:true, //指定元素
  done:function(value, date, endDate){
	  $("#receiveTimeInfo").html("");
  }
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
		content : '',
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

	//把标签的select数组值转换成一个以空格分割的字符串，并赋给隐藏的input(下策)
	function transferTag(){
		var tagIntValue=$("#tagArrayInt").val();
		var tagString="";
		for(var i=0;i<tagIntValue.length;i++){
			tagString+=tagIntValue[i]+" ";
		}
		$("#tags").val(tagString);
	}
	
	function getTagArray(){
		var tagString=$("#tags").val();
		var tagArray=tagString.split(/\s+/g);
		return tagArray;
		
	}

	function saveContent(status) {
		$("#status").val(status);
		takeout();
		if(!checkParam()){
			flag=false;
			return false;
		}
		transferTag();
		//var form = document.getElementById("articleForm");
		var formData = $("#articleForm").serializeJson();
		$.ajax({
			async : true,
			url : pageContext+"/admin/saveContent",
			type : "POST",
			contentType : "application/json",
			data : JSON.stringify(formData),
			dataType : "json",
			success : function(data) {
				if(data.meta.message=="permission"){
					alert("会话失效！请重新登录！")
					return false;
				}
				if (data.meta.success == true) {
					var cid = data.data.cid;
					$("#cid").val(cid);
					$("#status").val(data.data.status);
					flag = true;
					$(".saveSuccessMessage").css("display","inline-block");
					setTimeout("saveSuccessAction()",2000);
				} else {
					flag=false;
					alert(data.meta.message);
				}
			},
			error : function(error) {
				flag=false;
				alert(error);
			}
		});

	}
	function  saveSuccessAction(){
		$(".saveSuccessMessage").css("display","none");
	}
	

	


	
$(document).ready(function(){

	$("#title").click(function(){$("#titleInfo").html("");});
	$("#tagArrayInt").click(function(){$("#tagInfo").html("");});


});

function checkParam(){
	//非空字符正则
	var patter = new RegExp("\\S", "g");

	if(!patter.test($("#title").val())){
		$("#titleInfo").html("标题不能为空！");
		return false;
	}
	var tagsValue=$("#tagArrayInt").val();
	var tagsTextValue=$("#tagArrayInt").text();
	if(!patter.test(tagsValue) || tagsValue.length==0){
		$("#tagInfo").html("标签不能为空！");
	    return false;
	}
	if(!patter.test($("#receiveTime").val())){
		$("#receiveTimeInfo").html("日期不能为空！");
		return false;
	}
	//check通过返回true
	return true;
		}

	function returnList() {
		window.location.href = pageContext+"/admin/index";
	}

	function newContent() {
		window.location.href = pageContext+"/admin/contentEdit";
	}