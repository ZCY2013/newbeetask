package com.pdd.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pdd.Service.HttpControllerService;
import com.pdd.Util.SignUtil;

@Controller
@RequestMapping(value = "/")
public class HttpController extends HttpServlet  {
	private static final long serialVersionUID = 1L;

	@Autowired
	private HttpControllerService httpControllerService;
	
	
	@RequestMapping(value = "/Get",method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		System.out.println("DoGet success!  "+echostr);
		PrintWriter out = response.getWriter();
		boolean judge = SignUtil.checkSignature(signature, timestamp, nonce);
		if (judge) {
			out.print(echostr);
		}
//		System.out.println("judge: "+judge);
		//out.print(echostr);
		out.close();
		out = null;
	}
	@RequestMapping(value = "/Get",method =RequestMethod.POST )
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println("DoPost Susscess!");
		String respMessage = httpControllerService.processRequest(request);

		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}
	
	@RequestMapping(value = "index")
	public String showIndex(){
		return "/module/index1.html";
	}
	@RequestMapping(value = "paylist")
	public String showPayList()
	{
		return "/module/paylist.html";
	}
	@RequestMapping(value = "hello")
	public String sayHello(){
		return "/module/hello.html";
	}
	@RequestMapping(value ="/out", method = RequestMethod.GET)
	@ResponseBody
	public String printUserInfo(HttpServletRequest request)
	{
//		System.out.println("RequestBody"+JSON.toJSONString(json));
		return httpControllerService.printUserInfo(JSON.parseObject(null));
	}
}
