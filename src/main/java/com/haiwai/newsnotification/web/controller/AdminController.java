package com.haiwai.newsnotification.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	
	@RequestMapping(value="/index")
	public String index(){
		return "back/admin/index";
	}
	@RequestMapping(value="/contentEdit")
	public String contentEdit(){
		return "back/admin/content_edit";
	}

}
