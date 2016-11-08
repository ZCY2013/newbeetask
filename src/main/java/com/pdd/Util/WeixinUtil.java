package com.pdd.Util;



import com.pdd.domain.Menu;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pdd.Manager.MyTrustManager;
import com.pdd.domain.AccessToken;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Component
public class WeixinUtil {  
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
    
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    
    public final static String create_menu_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
    
    public static AccessToken accesstoken = null;
    
//    public static AccessToken accesstoken = new AccessToken("cx9y5teiqEt74DKX1raP-MPpZFfmmIjHycfpGVMDV7zi1ZC3u9FdeBVf1C_zEMmy44trqZq8OeTJooU0DHbdIv1-p7V3i0g1VlRw7h3vRPsSNxjCzqhqGB8tjA3hfuQwQKZdAGAHBQ",7200);
    
    public  JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            TrustManager[] tm = { new MyTrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
           
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
    
    public AccessToken getAccessToken(String appid,String appsecret){
    	if(accesstoken != null)
    		return accesstoken;
    	String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET",appsecret);
    	JSONObject jsonObject = httpRequest(requestUrl,"GET",null);
    	System.out.println("jsonObject : "+JSON.toJSONString(jsonObject));
    	if(null != jsonObject){
    		try{
    			accesstoken = new AccessToken();
    			accesstoken.setExpiresIn(jsonObject.getInt("expires_in"));
    			accesstoken.setToken(jsonObject.getString("access_token"));
    		}catch (JSONException e){
    			accesstoken = null;
    			log.error("get token failed  errcode:{} errmsg:{}",jsonObject.getInt("errcode"),jsonObject.getString("errmsg"));
    			System.out.println("failed");
    		}
    	}
    	return accesstoken;
    }
    
    public  int createMenu(Menu menu,String accessToken){
    	int result = 0;
    	String url = create_menu_url.replace("ACCESS_TOKEN",accessToken);
    	String jsonMenu = JSONObject.fromObject(menu).toString();
    	JSONObject jsonObject = httpRequest(url,"POST",jsonMenu);
    	
    	if(null != jsonObject){
    		if( 0 != jsonObject.getInt("errcode")){
    			result = jsonObject.getInt("errcode");
    			log.error("create menu faulse errcode:{} errmsg:{}",jsonObject.getInt("errcode"),jsonObject.getString("errmsg"));
    		}
    	}
    	return result;
    }
    
    public String getOpenId(){
    
    	return null;
    }
    
}  