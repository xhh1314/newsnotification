<#include "/fore/header.ftl">
<!-- 正文容器 -->
<div
	class="ui  very padded text container segment notification-content">

		<div class="ui divided items">
			<div class="item">
				<div class="content">
					<a class="header contentTitle">${content.title}</a>
					<div class="meta">
						<span class="right floated time">${content.receiveTime}</span>
						<#list content.tags?if_exists as tag>
						<span class="ui label">${tag.name}</span>
						</#list>
					</div>
					<div class="description">
						${content.content}
					</div>
					
				</div>
			</div>
			
		</div>
				
</div>
</div>
</body>
<style>
.contentTitle {
	font-family: -apple-system, SF UI Display, Arial, PingFang SC,
		Hiragino Sans GB, Microsoft YaHei, WenQuanYi Micro Hei, sans-serif;
	font-size: 34px !important;
	font-weight: 700 !important;
	line-height: 1.3 !important;
	pointer-events:none;
}
</style>
</html>