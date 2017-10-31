<#include "/fore/header.ftl">
<!-- 正文容器 -->
	<div class="ui raised very padded text container segment notification-content">
		<h3 class="ui header">${message!""}</h3>
		<div class="ui divided items">
<#list contents?if_exists as content>
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