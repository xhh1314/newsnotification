package cn.haiwai.newsnotification.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.haiwai.newsnotification.service.ContentBO;
import cn.haiwai.newsnotification.service.ContentService;

@Controller
@RequestMapping
public class ForeController {
	@Autowired
	private ContentService cs;

	/**
	 * @param model
	 * @return 返回到主页
	 */
	@RequestMapping(value="/index")
	public String index(ModelMap model){
	List<ContentBO> contents=cs.listContents();
	if(contents==null){
		model.addAttribute("message","最近7天没有数据，请查看其他日期！");
	}
	model.addAttribute("contents",contents);
	  return "fore/index";	
	}
	/**
	 * 所有的没有后缀地址的请求都转发到主页
	 * @return 转发到主页
	 */
	@RequestMapping(value="/")
	public String everyPath(){
		return "forward:index";
	}
	
	@RequestMapping(value="/content/{cid}")
	public String content(@PathVariable("cid") String cid,ModelMap model){
		ContentBO content=cs.getContent(Integer.parseInt(cid));
		if(content==null)
			return "admin/comm/error_404";
		model.addAttribute("content",content);
		return "fore/contentPage";
	}
	@RequestMapping(value="/listByDate/{date}")
	public String listByDate(@PathVariable("date") String date,ModelMap model){
		List<ContentBO> contents=cs.listContentsByDate(date);
		if(contents==null){
			model.addAttribute("message",date+"没有信息，请查看其他日期！");
		}
		model.addAttribute("contents",contents);
		model.addAttribute("dateTemp",date);
		return "fore/index";
	}
	
	@RequestMapping(value="/listByKey/{key}")
	public String listBykeys(@PathVariable("key") String key,ModelMap model){
		List<ContentBO> contents=cs.listContentsByKey(key);
		if(contents==null){
			model.addAttribute("message",key+",没有找到相关信息，搜索其他关键词试试！");
		}
		model.addAttribute("contents",contents);
		return "fore/index";
	}
	@RequestMapping(value="/exceptionTest")
	public String exceptionTest(){	
		int i=1/0;
		throw new RuntimeException("exceptionTest");
	}
	
}
