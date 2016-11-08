package com.pdd.Manager;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pdd.Util.WeixinUtil;
import com.pdd.base.Button;
import com.pdd.domain.AccessToken;
import com.pdd.domain.CommonButton;
import com.pdd.domain.ComplexButton;
import com.pdd.domain.Menu;
import com.pdd.domain.ViewButton;
@Component
public class MenuManager implements InitializingBean {
	
	
	  private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
	  
	  public  final String appId = "wx3a7e46fd22c32055";
	  
	  public final String appSecret = "ba7989f012e15e24c8621f05e7824d16";
	  
	  private final String login_url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
			  +"appid=APPID&redirect_uri=REDIRECT_URI&response_type=code"
			  +"&scope=SCOPE&state=STATE#wechat_redirect";
	@Autowired
	private WeixinUtil weixinUtil;
	
	
	public void creatMenu()
	{
		AccessToken at = weixinUtil.getAccessToken(appId,appSecret);
		if(null != at){
			int result = weixinUtil.createMenu(getMenu(), at.getToken());
			
//			int result = weixinUtil.createMenu(getMenuT(),at.getToken());
			if(0 == result)
				log.info("create menu  successful");
			else 
				log.info("create menu faulse,error code:"+result);
		}
	}
//	private Menu getMenuT(){
//		ViewButton btnT = new ViewButton();
//		btnT.setName("Test");
//		btnT.setType("view");
//		btnT.setUrl("http://www.182.254.240.25/index.html");
//		Menu menu = new Menu();
//		CommonButton btn1 = new CommonButton();
//		btn1.setKey("Test");
//		btn1.setName("≤‚ ‘");
//		btn1.setType("click");
//		System.out.println("(Button btn1 : "+ JSON.toJSONString(new Button[]{btn1}));
//		System.out.println("btn1 : "+JSON.toJSONString(btn1));
//		ComplexButton btnT2 = new ComplexButton();
//		btnT2.setName("Test2");
//		btnT2.setSubButton(new Button[]{btn1});
//		System.out.println("complexButton : "+JSON.toJSONString(btnT2));
//		menu.setButton(new Button[]{btnT,btnT2});
//		System.out.println("menu : "+JSON.toJSONString(menu));
//		return menu;
//	}
	
	
	private Menu getMenu(){
		ViewButton btn21 = new ViewButton();
		btn21.setName("merchants in");
		btn21.setType("view");
		btn21.setUrl("http://www.baidu.com");
		
		ViewButton btn22 = new ViewButton();
		btn22.setName("My order");
		btn22.setUrl(login_url.replace("APPID",appId).replace("SCOPE","snsapi_base").replace("REDIRECT_URI","http://182.254.240.25/paylist").replace("STATE","test"));
		btn22.setType("view");
		
		ViewButton btn23 = new ViewButton();
		btn23.setName("Test");
		btn23.setType("view");
		String key = login_url.replace("APPID",appId).replace("SCOPE","snsapi_base").replace("REDIRECT_URI","http://182.254.240.25/hello").replace("STATE","test");
//		System.out.println(key);
		btn23.setUrl(key);
		
		ViewButton btn24 = new ViewButton();
		btn24.setUrl(login_url.replace("APPID",appId).replace("SCOPE","snsapi_userinfo").replace("REDIRECT_URI","http://182.254.240.25/out").replace("STATE","test"));
		btn24.setName("Example base");
		btn24.setType("view");
		
		ViewButton btn25 = new ViewButton();
		btn25.setName("Example userinfo");
		btn25.setType("view");
		btn25.setUrl(login_url.replace("APPID",appId).replace("SCOPE","snsapi_userinfo").replace("REDIRECT_URI","http://182.254.240.25/hello").replace("STATE","test"));
		
		ViewButton btn1 = new ViewButton();
		btn1.setName("mall");
		btn1.setUrl(login_url.replace("APPID",appId).replace("SCOPE","snsapi_base").replace("REDIRECT_URI","http://182.254.240.25/index").replace("STATE","test"));
		btn1.setType("view");
//		System.out.println(JSON.toJSON(btn1));
		ComplexButton mainbtn2 = new ComplexButton();
		mainbtn2.setName("Service");
		mainbtn2.setSub_button(new Button[]{btn21,btn22,btn23,btn24,btn25});
		Menu menu = new Menu();
		menu.setButton(new Button[]{btn1,mainbtn2});
//		System.out.println("menu : "+JSON.toJSONString(menu));
		return menu;
	}



	@Override
	public void afterPropertiesSet() throws Exception {
		//creatMenu();
	}
}
