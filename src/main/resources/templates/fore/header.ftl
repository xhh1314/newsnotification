<!DOCTYPE html>
<html>
<#assign ctx=request.contextPath />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="shortcut icon" href="${ctx}/admin/images/favicon.png"/>
<script type="text/javascript" src="${ctx}/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${ctx}/laydate/laydate.js"></script>
<%-- <script type="text/javascript" src="${ctx}/semanticui/dist/semantic.min.js"></script> --%>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/semantic.min.css"> --%>
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/reset.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/site.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/container.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/input.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/icon.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/label.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/item.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/menu.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/segment.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/components/divider.min.css">

<script type="text/javascript">
//禁止复制
document.oncontextmenu=new Function("event.returnValue=false"); 
document.onselectstart=new Function("event.returnValue=false"); 

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
			$("title").html("新闻提示");
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
			alert("不符的日期格式");
			return false;
		}
		location.href = "${ctx}/listByDate/" + date;
	}
	//非空字符正则
	var patter2 = new RegExp("\\S", "g");
	function searchByKey() {
		var key = $(".keyinput").val();
		if (!patter2.test(key)) {
			alert("请输入有效关键字!");
			return false;
		}

		location.href = "${ctx}/listByKey/" + key;
	}
</script>
<style type="text/css">
.notification-nav {position: fixed;top: 0px;left: 0px;height: 60px;margin-bottom: 0px;z-index: 2;min-width:1200px!important;}
.notification-content {position: fixed;top: 46px;margin-left: auto;margin-right: auto;margin-top: 0px !important;min-width: 750px;z-index: 1;
	border-top: none !important;border-bottom:none !important;}
.notification-nav .nav-left{display:inline-block;line-height:56px;width:125px;}
.notification-nav .nav-left a{color:#EA6F5A;font-size:20px;padding:10px!important;border-right: none;}
.notification-nav .nav-left a{margin-top:5px;}
.notification-nav .nav-middle{display:block;min-width:750px;margin-left:65px;height:56px;}
.nav-middle .calender{display:inline-block;line-height:56px;width:200px;}
.nav-middle .keysearch{display:inline-block;line-height:56px;margin-left:50px;}
.keyinput{outline:none !important;}
.search-button{pointer-events:auto !important;cursor:pointer;}
.ui .divided .items{margin-top:0px !important}
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
				<a > <i class="home icon"></i>新闻提示
				</a>
			</div>

			<div class="nav-middle">
				<div class="ui input calender">
					<input type="text" placeholder="日期查询" id="input-newsTime" onchange="selectByDate()" value="${dateTemp?if_exists}">
				</div>
				<div class="ui search keysearch">
					<div class="ui icon input">
						<input class="prompt keyinput" type="text" placeholder="关键字搜索">
						<i class="search icon search-button" onclick="searchByKey()"></i>
					</div>
					<div class="results"></div>
				</div>

			</div>
		</div>
	</div>