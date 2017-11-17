<!DOCTYPE html>
<html>
<#assign ctx=request.contextPath />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="shortcut icon" href="${ctx}/admin/images/favicon.png"/>
<script type="text/javascript" src="${ctx}/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${ctx}/laydate/laydate.js"></script>
<script type="text/javascript" src="${ctx}/semanticui/dist/components/dropdown.min.js"></script>
<script type="text/javascript" src="${ctx}/semanticui/dist/components/transition.min.js"></script>
<script type="text/javascript" src="${ctx}/semanticui/dist/semantic.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/reset.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/site.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/container.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/header.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/input.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/icon.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/label.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/item.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/button.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/menu.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/segment.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/divider.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/dropdown.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/transition.min.css">
<script type="text/javascript">
//项目目录全局变量
var pageContext="${ctx!""}";
//定义三个变量记住查询条件
var keyDate="${keyDate!""}";
var keyWord="";
var keyTag="";
//记住刷新页面后的keyTag
var keyTagInitial="${keyTag!""}";
//禁止复制
//document.oncontextmenu=new Function("event.returnValue=false"); 
//document.onselectstart=new Function("event.returnValue=false"); 

	//执行一个laydate实例
	laydate.render({
		elem : '#input-newsTime',
		calender : true
	//指定元素
	});


	function pageInitial() {
		showTitle();
		loadCalender();
	}

	function showTitle() {
		var title = $("title").html();
		if (title == null || title == "")
			$("title").html("报道提示");
	}
	//解决初次加载时显示不出来问题
	function loadCalender() {
		laydate.render({
			elem : '#input-newsTime',
			calender : true, //指定元素
			done : function(value, date, endDate) {
				selectByDate(value);
			}
		});
	}

	//js也需要转义\

	//日期正则

	var patter1 = new RegExp("^[2][0]\\d{2}\-\\d{2}\-\\d{2}$", "g");
	function selectByDate(date) {
		if (date == null || date == "" || date == undefined)
			return false;
		if (!patter1.test(date)) {
			keyDate="";
			alert("日期格式不正确!请录入正确的格式：YYYY-MM-DD");
			return false;
		}
		keyDate=date;
	}
	//非空字符正则
	var patter2 = new RegExp("\\S", "g");
	function doSearch() {
		var key = $(".keyinput").val();
		if (patter2.test(key)) {
			keyWord = key;
		} else {
			keyWord = "";
		}

		location.href = pageContext + "/search?keyDate=" + keyDate
				+ "&keyWord=" + keyWord + "&keyTag=" + keyTag;
	}

	$(document).ready(function() {
		$(".dropdown").dropdown({
			on : 'click',
			allowCategorySelection : true,
			onChange : function(value, text, $choice) {
				keyTag = value;
			}
		});

	});
</script>
<style type="text/css">
*div{border:1px solid black}
.notification-nav {position: fixed;top: 0px;left: 0px;height: 60px;margin-bottom: 0px;z-index: 2;min-width:1200px!important;}
.notification-content {position: fixed;top: 46px;margin-left: auto;margin-right: auto;margin-top: 0px !important;min-width: 750px;z-index: 1;
	border-top: none !important;border-bottom:none !important;}
.notification-nav .nav-left{display:inline-block;line-height:56px;width:125px;}
.notification-nav .nav-left a{color:#EA6F5A;font-size:20px;padding:10px!important;border-right: none;}
.notification-nav .nav-left a{margin-top:5px;}
.notification-nav .nav-middle{display:block;min-width:750px;margin-left:65px;height:56px;}
.nav-middle .tagSelect{display:inline-block;min-width:180px;height:56px;margin-left:20px;}
.nav-middle .tagSelect .dropdown{min-width:100px;}
.nav-middle .calender{line-height:56px;}
.nav-middle .keysearch{display:inline-block;line-height:56px;margin-left:5px;}
.keyinput{outline:none !important;}
.search-button{pointer-events:auto !important;cursor:pointer;text-align: center;margin-top:10px !important;}
.search-button i{display:inline-block !important; }
.ui .divided .items{margin-top:0px !important}
.nav-right{}
</style>
<meta charset="UTF-8">
<title>${(content.title)!""}</title>
</head>
<body onload="pageInitial()">
<noscript> 
<iframe src="*.htm"></iframe> 
</noscript> 
	<div class="ui attached stackable menu notification-nav">

		<div class="ui container nav-middle">
			<div class="nav-left">
				<a > <i class="home icon"></i>报道提示
				</a>
			</div>

			<div class="nav-middle">
				<div class="ui  labeled input calender">
				    <label for="input-newsTime" class="ui label"><i class="calendar icon"></i></label>
					<input type="text" placeholder="日期查询" id="input-newsTime"
						value="${dateTemp?if_exists}">
				</div>
				<div class="ui search keysearch">
					<div class="ui labeled input">
					<label for="kerword" class="ui label"><i class="keyboard icon"></i></label>
						<input class="prompt keyinput" type="text"  id="keyword" placeholder="关键字搜索">
					</div>
					<div class="results"></div>
					
				</div>
				<div class="tagSelect">
					<div class="ui labeled icon dropdown ">
						<i class="tags icon"></i> <span class="text">选择标签</span>
						<div class="menu">
							<div class="header">
								<i class="filter icon"></i>标签过滤
							</div>
							<div class="divider"></div>
							<div class="item" data-value="1">指示</div>
							<div class="item" data-value="2">禁止</div>
							<div class="item" data-value="3">鼓励</div>
							<div class="item" data-value="0">无分类...</div>
						</div>
					</div>
				</div>
			</div>
			<div class="nav-right">
			<div class="ui  icon button search-button" onclick="doSearch()"><i class="search icon"></i>查询</div>
			</div>
		</div>
	</div>