package com.pdd.Service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pdd.RespMsg.TextMessage;
import com.pdd.Util.MessageUtil;
import com.pdd.Util.WeixinUtil;
@Service
public class HttpControllerService {

	@Autowired
	private MessageUtil mu;
	
	@Autowired
	private WeixinUtil weixinUtil;
	
	public  String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try{
			String respContent = "System error,please try again!";
			
			Map<String,String> requestMap = mu.parseXml(request);
			
			String fromUserName = requestMap.get("FromUser");
			
			String toUserName = requestMap.get("ToUserName");
			System.out.println("FromUser : "+fromUserName);
			System.out.println("ToUserName : " + toUserName);
			String msgType = requestMap.get("MsgType");
			
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(toUserName);
			textMessage.setFromUserName(fromUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(msgType);
			textMessage.setFuncFlag(0);
			System.out.println("msgType : " +msgType);
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				respContent = "text Message push success";
			}
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
				respContent = " link Message push success";
			}else if(msgType.equals(MessageUtil.REQMESSAGE_TYPE_EVENT)){
				String eventType = requestMap.get("Event");
				System.out.println("event type : "+ eventType);
				
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					respContent = " ![CDATA[thanks for your subscribe!]]>";
				}
				else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRUBE)){
					
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
					String eventKey = requestMap.get("EventKey");
					System.out.println("event key : "+eventKey);
					System.out.println("eventkey judge 24: "+eventKey.equals("24"));
					if(eventKey.equals("23")){
						respContent = "<![CDATA[<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f41"
												+"7810387&redirect_uri=http%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%"
												+"3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D"
												+"4_2030_5_1194_60&response_type=code&scope=snsapi_base&state=123#wecha"
												+"t_redirect\"></a>]]>";
					}else if(eventKey.equals("24")){
						respContent = "<![CDATA[<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect\">testScope</a>]]>";
					}else if(eventKey.equals("25")){
						respContent = "<![CDATA[Caveat emptor!]]>";
					}
				}
			}
			textMessage.setContent(respContent);
			
			respMessage = mu.textMessageToXml(textMessage);
			
//			System.out.println("respMessage : "+respMessage);
		}catch(Exception e){
			e.printStackTrace();
		}
		return respMessage;
	}

	public String printUserInfo(JSONObject json) {
		StringBuffer out = new StringBuffer();
		System.out.println("json : "+json);
		JSONObject jsonObject = json;
//		JSONObject jsonObject = JSONObject.parseObject(json);
		String openid = jsonObject.getString("openid");
		String nickname = jsonObject.getString("nickname");
		String sex = jsonObject.getString("sex");
		String city = jsonObject.getString("city");
		String country = jsonObject.getString("country");
		String province = jsonObject.getString("province");
		String headimgurl = jsonObject.getString("headimgurl");
		
		out.append("<!DOCTYPE HTML PUBLIC  \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.append("<HTML>");
		out.append("<HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.append("<BODY>");
		out.append(", using the POST method \n");
        out.append("openid:" + openid + "\n\n");
        out.append("nickname:" + nickname + "\n\n");
        out.append("sex:" + sex + "\n\n");
        out.append("province:" + province + "\n\n");
        out.append("city:" + city + "\n\n");
        out.append("country:" + country + "\n\n");
        out.append("<img src=/" + headimgurl + "/");
        out.append(">");
        out.append(" </BODY>");
        out.append("</HTML>");

		System.out.println(out.toString());
		return out.toString();
		
	}

}
