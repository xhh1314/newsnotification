<#include "/fore/header.ftl">
<!-- 正文容器 -->
	<div class="ui  very padded text container segment notification-content">
		<h3 class="ui header">${message!""}</h3>
		<div class="ui divided items">
<#list contents?if_exists as content>
			<div class="item">
				<div class="content">
					<a class="header" href="${ctx}/content/${content.cid?c}" target="_blank">${content.title}</a>
					<div class="meta">
						<span class="cinema">${content.createTime}</span>
					</div>
					<div class="description">
						<p>${content.content}</p>
					</div>
					<div class="extra">
					<#list content.tags?if_exists as tag>
						<div class="ui label">${tag.name}</div>
						</#list>
					</div>
				</div>
			</div>
			</#list>
		</div>

	</div>
</body>

</html>