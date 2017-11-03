<!DOCTYPE html>
<html>
<#assign ctx=request.contextPath />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="shortcut icon" href="${ctx}/admin/images/favicon.png"/>
<script type="text/javascript" src="${ctx}/jquery-3.1.0.js"></script>
<script type="text/javascript" src="${ctx}/semanticui/dist/semantic.min.js"></script>
<script type="text/javascript" src="${ctx}/laydate/laydate.js"></script>


<link rel="stylesheet" type="text/css" href="${ctx}/semanticui/dist/semantic.min.css">

<meta charset="UTF-8">
<title></title>
</head>

<body >

    <div id="md-container" class="form-group col-md-12">
                <!-- 加载uEdit编辑器的容器 -->
    <script id="container" name="uEdit" type="text/plain">
    </script>
     </div>
</body>
<script type="text/javascript" src="${ctx }/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx }/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
var ue = UE.getEditor('container');
</script>
</html>
