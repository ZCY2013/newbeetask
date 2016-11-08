package com.pdd.Util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pdd.RespMsg.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
@Component
public class MessageUtil {
	
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	
	public static final String REQMESSAGE_TYPE_EVENT = "event";
	
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	
	public static final String EVENT_TYPE_UNSUBSCRUBE = "unsubscribe";
	
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	protected static String PREFIX_CDATA    = "<![CDATA[";   
	
	protected static String SUFFIX_CDATA    = "]]>";   
	private static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWirter(Writer out)
		{
			return new PrettyPrintWriter(out){
				boolean cdata = true;
				
				@SuppressWarnings("unchecked")
				@Override
				public void startNode(String name,Class clazz){
					super.startNode(name,clazz);
				}
				@Override
				protected void writeText(QuickWriter writer,String text){
					if(cdata){
						System.out.println("CDATA add successful");
						writer.write(PREFIX_CDATA);
						writer.write(text);
						writer.write(SUFFIX_CDATA);
					}else{
						writer.write(text);
					}
				}
			};
		}
	});
//	 public static XStream initXStream(boolean isAddCDATA){   
//	        XStream xstream = null;   
//	        if(isAddCDATA){   
//	            xstream =  new XStream(   
//	               new XppDriver() {   
//	                  public HierarchicalStreamWriter createWriter(Writer out) {   
//	                     return new PrettyPrintWriter(out) {   
//	                     protected void writeText(QuickWriter writer, String text) {   
//	                                      if(text.startsWith(PREFIX_CDATA)    
//	                                         && text.endsWith(SUFFIX_CDATA)) {   
//	                                          writer.write(text);   
//	                                      }else{   
//	                                          super.writeText(writer, text);   
//	                                      }   
//	                      }   
//	                    };   
//	                  };   
//	                }   
//	            );   
//	        }else{   
//	            xstream = new XStream();   
//	        }   
//	     return xstream;   
//	 }
//	XStream xstream =initXStream(true);
	
	
	
	@SuppressWarnings("unchecked")
	public  Map<String,String> parseXml(HttpServletRequest request) throws Exception
	{
		Map<String,String> map = new HashMap<String,String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		
		for(Element e: elementList)
			map.put(e.getName(),e.getText());
		inputStream.close();
		inputStream = null;
		
		return map;
	}
	
	public String textMessageToXml(TextMessage textMessage){
		xstream.alias("xml",textMessage.getClass());
//		System.out.println("xstream : "+JSON.toJSONString(xstream));
		return xstream.toXML(textMessage);
	}
	

}
