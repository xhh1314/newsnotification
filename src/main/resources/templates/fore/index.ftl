<#include "/fore/header.ftl">
<!-- 正文容器 -->
	<div class="ui raised very padded text container segment notification-content">
		<h2 class="ui header">${message!""}</h2>
		<div class="ui divided items">
<#list contents!"" as content>
			<div class="item">
				<div class="content">
					<a class="header" href="${ctx}/content/${content.cid}">${content.title}</a>
					<div class="meta">
						<span class="cinema">HWW</span>
					</div>
					<div class="description">
						<p>${content.content}</p>
					</div>
					<div class="extra">
						<div class="ui label">${content.receiveTime}</div>
					</div>
				</div>
			</div>
			</#list>
		</div>

	</div>
</body>
</html>