package com.haiwai.newsnotification.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haiwai.newsnotification.service.ContentBO;
import com.haiwai.newsnotification.service.ContentService;

@Controller
@RequestMapping
public class ForeController {
	@Autowired
	private ContentService cs;

	@RequestMapping(value="/index")
	public String index(ModelMap model){
	List<ContentBO> contents=cs.listContents();
	if(contents==null){
		model.addAttribute("message","最近7天没有数据，请查看其他日期！");
	}
	model.addAttribute("contents",contents);
	  return "fore/index";	
	}
	@RequestMapping(value="/content/{cid}")
	public String content(@PathVariable("cid") String cid,ModelMap model){
		ContentBO content=cs.getContent(Integer.parseInt(cid));
		if(content==null)
			return "admin/comm/error_404";
		model.addAttribute("content",content);
		return "fore/contentPage";
	}
	
}
