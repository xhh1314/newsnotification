package com.haiwai.newsnotification.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/fore")
public class ForeController {

	@RequestMapping(value="/index")
	public String index(){
		
	return "fore/index";	
	}
	
}
