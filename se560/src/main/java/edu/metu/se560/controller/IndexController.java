package edu.metu.se560.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	
	@RequestMapping("/list/cluster")
	@ResponseBody
	public String login(HttpSession session) { 
		
        return ""; 
    }
	
}
