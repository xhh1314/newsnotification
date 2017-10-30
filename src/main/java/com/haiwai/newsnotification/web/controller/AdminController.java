package com.haiwai.newsnotification.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haiwai.newsnotification.manage.AbstractPage;
import com.haiwai.newsnotification.manage.response.Response;
import com.haiwai.newsnotification.service.ContentBO;
import com.haiwai.newsnotification.service.ContentService;

/**
 * 后台管理控制类 所有文章操作都在这里转发
 * 
 * @author lh
 * @date 2017年10月27日
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private ContentService cs;

	/**
	 * 后台主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap model) {
		AbstractPage page = AbstractPage.getPageInstance(1, 0, 0);
		List<ContentBO> contents = cs.listContentsByPage(page, "assign");
		model.addAttribute("contents", contents);
		model.addAttribute("page", page);
		return "back/admin/index";
	}

	/**
	 * 分页查询content
	 * 
	 * @param model
	 * @param action
	 * @param beginPage
	 * @param endPage
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value = "/contentPage")
	public String contentPage(ModelMap model, @RequestParam("action") String action,
			@RequestParam("beginPage") int beginPage, @RequestParam("endPage") int endPage,
			@RequestParam("currentPage") int currentPage) {
		AbstractPage page = AbstractPage.getPageInstance(currentPage, beginPage, endPage);
		List<ContentBO> contents = cs.listContentsByPage(page, action);
		model.addAttribute("contents", contents);
		model.addAttribute("page", page);
		return "back/admin/index";
	}

	/**
	 * 进入新增content视图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/contentEdit")
	public String contentEdit() {
		return "back/admin/content_edit";
	}

	/**
	 * 匹配日期的正则
	 */
	private final String datePatter = "^[2][0]\\d{2}\\-\\d{2}\\-\\d{2}$";

	/**
	 * 保存content
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/saveContent", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Response saveContent(@RequestBody ContentBO content) {
		StringBuilder message = new StringBuilder();
		if (!content.getReceiveTime().matches(datePatter)) {
			message.append("日期格式不正确;");
		}
		if (!StringUtils.hasText(content.getTitle())) {
			message.append("标题不能为空;");
		}
		if (!StringUtils.hasText(content.getContent())) {
			message.append("文章正文不能为空;");
		}
		if (message.length() > 1)
			return Response.failure(message.toString());
		ContentBO b = cs.saveContent(content);
		if (b == null) {
			return Response.failure("保存失败！请稍后重试");
		}
		b.setContent(null);
		return Response.success(b, "保存成功！");
	}

	/**
	 * 查看一条content
	 * 
	 * @param cid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getContent/{cid}")
	public String getContent(@PathVariable("cid") String cid, ModelMap model) {
		ContentBO contentBo = cs.getContent(Integer.parseInt(cid));
		if (contentBo == null)
			return "back/comm/error_404";
		model.addAttribute("content", contentBo);
		return "content";
	}

	/**
	 * 更新content之前渲染视图
	 * 
	 * @param cid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateContent/{cid}")
	public String updateContent(@PathVariable("cid") String cid, ModelMap model) {
		ContentBO contentBo = cs.getContent(Integer.parseInt(cid));
		if (contentBo == null)
			return "back/comm/error_404";
		model.addAttribute("contents", contentBo);
		return "back/admin/content_update";
	}

	/**
	 * 根据cid删除一条content记录
	 * 
	 * @param cid
	 * @return
	 */
	@RequestMapping(value = "/deleteContent/{cid}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response deleteContent(@PathVariable("cid") String cid) {
		if (cs.deleteContent(cid))
			return Response.success();
		else
			return Response.failure();
	}

}
