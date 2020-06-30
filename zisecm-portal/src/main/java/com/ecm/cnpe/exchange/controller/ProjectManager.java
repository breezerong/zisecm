package com.ecm.cnpe.exchange.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange/project")
public class ProjectManager {
	
	@PostMapping("myproject")
	@ResponseBody
	public Map<String,Object> getMyProject(){
		Map<String,Object> result = new HashMap<String,Object>();
		
		return result;
	}
	
}
