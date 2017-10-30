<!DOCTYPE html>
<html>
<#assign ctx=request.contextPath />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/jquery-3.1.0.js"></script>
<script type="text/javascript" src="${ctx}/semanticui/dist/semantic.min.js"></script>
<script type="text/javascript" src="${ctx}/laydate/laydate.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/semantic.min.css">
<script type="text/javascript">
//执行一个laydate实例
laydate.render({
  elem: '#input-newsTime',calender:true //指定元素
});
//解决初次加载时显示不出来问题
function loadCalender(){
	laydate.render({
		  elem: '#input-newsTime',calender:true //指定元素
		});
}
</script>
<style type="text/css">
.notification-nav {position: fixed;top: 0px;left: 0px;height: 60px;margin-bottom: 0px;z-index: 2;min-width:1200px!important;}
.notification-content {position: fixed;top: 46px;margin-left: auto;margin-right: auto;margin-top: 0px;min-width: 750px;z-index: 1;
	border-top: none !important;}
.notification-nav .nav-left{display:inline-block;line-height:56px;width:125px;}
.notification-nav .nav-left a{color:#EA6F5A;font-size:20px;padding:10px!important;border-right: none;}
.notification-nav .nav-left a{margin-top:5px;}
.notification-nav .nav-middle{display:block;min-width:750px;margin-left:65px;height:56px;}
.nav-middle .calender{display:inline-block;line-height:56px;width:200px;}
.nav-middle .keysearch{display:inline-block;line-height:56px;margin-left:50px;}
.keyinput{outline:none !important;}
.search-button{pointer-events:auto !important;cursor:pointer;}
</style>
<meta charset="UTF-8">
<title>新闻主题</title>
</head>
<body onload="loadCalender()">
	<div class="ui attached stackable menu notification-nav">

		<div class="ui container nav-middle">
			<div class="nav-left">
				<a > <i class="home icon"></i>新闻主题
				</a>
			</div>

			<div class="nav-middle">
				<div class="ui input calender">
					<input type="text" placeholder="日期" id="input-newsTime">
				</div>
				<div class="ui search keysearch">
					<div class="ui icon input">
						<input class="prompt keyinput" type="text" placeholder="关键字">
						<i class="search icon search-button"></i>
					</div>
					<div class="results"></div>
				</div>

			</div>
		</div>
	</div>