package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/api")
public class TestController {
	@GetMapping(value = "/hello") 
	public String sayHello(
	@RequestParam String nom, 
	@RequestParam String prenom, 
	ModelMap model) {
	model.addAttribute("nom", nom); 
	model.addAttribute("prenom", prenom); 
	return "hello";
	}
}
