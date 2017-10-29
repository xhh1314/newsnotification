<!DOCTYPE html>
<html>
<#assign ctx=request.contextPath />
<head>
    <meta charset="utf-8"/>
    <title>${title!} - Tale</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta content="biezhi" name="author"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="shortcut icon" href="${ctx}/admin/images/favicon.png"/>
    <link href="${ctx}/admin/css/style.min.css?v=v1.0" rel="stylesheet" type="text/css">

</head>
<body class="fixed-left" onload="initialPage()">
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
               <span style="line-height: 50px;font-size:30px;margin-left:35%">新闻主题</span>
            <span style="line-height: 50px;margin-left:40%">注销</span>
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