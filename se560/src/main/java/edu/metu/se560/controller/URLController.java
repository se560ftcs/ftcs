package edu.metu.se560.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class URLController {

	@RequestMapping("/")
	public String gotoHomepage() {
        return "index";  
    }
	
	@RequestMapping("/chart")
	public String gotoBubleDiagramPage() {
        return "chart";  
    }
}
