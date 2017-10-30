<#include "/fore/header.ftl">
<!-- 正文容器 -->
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
<div
	class="ui raised very padded text container segment notification-content">

		<div class="ui divided items">
			<div class="item">
				<div class="content">
					<a class="header contentTitle">${content.title}</a>
					<div class="meta">
						<span class="cinema">${content.receiveTime}</span>
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
</html>