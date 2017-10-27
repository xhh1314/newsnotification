package com.haiwai.newsnotification.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haiwai.newsnotification.manage.response.Response;
import com.haiwai.newsnotification.service.ContentBO;
import com.haiwai.newsnotification.service.ContentService;

/**
 * 后台管理控制类 所有文章操作都在这里转发
 * @author lh
 * @date 2017年10月27日
 * @version 1.0
 */
@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private ContentService cs;
	
	@RequestMapping(value="/index")
	public String index(){
		return "back/admin/index";
	}
	@RequestMapping(value="/contentEdit")
	public String contentEdit(){
		return "back/admin/content_edit";
	}
	
	/**
	 * 匹配日期的正则
	 */
	private final String datePatter="^[2][0]\\d{2}\\-\\d{2}\\-\\d{2}$";
	@RequestMapping(value="/saveContent",method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public Response saveContent(@RequestBody ContentBO content){
		StringBuilder message=new StringBuilder();
		if(!content.getReceiveTime().matches(datePatter)) {
			message.append("日期格式不正确;");
		}
		if(!StringUtils.hasText(content.getTitle())) {
			message.append("标题不能为空;");
		}
		if(!StringUtils.hasText(content.getContent())) {
			message.append("文章正文不能为空;");
		}
		if(message.length()>1)
			return Response.failure(message.toString());
		ContentBO b=cs.saveContent(content);
		if(b==null){
			return Response.failure("保存失败！请稍后重试");
		}
		b.setContent(null);
		return Response.success(b,"保存成功！");
	}

}
