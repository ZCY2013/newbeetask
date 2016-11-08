package com.pdd.controller;

import java.util.Date;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class MainsiteErrorController implements ErrorController{
	private static final String ERROR_PATH = "/error";
	
	@RequestMapping(value = ERROR_PATH)
	public String handleError()
	{
		Date d = new Date();
		System.out.println(d);
		System.out.println("error page back!");
		return "404.html";
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
