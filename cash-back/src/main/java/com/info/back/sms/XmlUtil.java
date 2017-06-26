package com.info.back.sms;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

public class XmlUtil {

	private static final Logger log = LoggerFactory.getLogger(XmlUtil.class);
	/**
	 * 解析消息内容
	 * @param objClass
	 * @param msg
	 * @return
	 */
	public static Object readMessage(Class objClass,String msg){
		try {
			JAXBContext jc = JAXBContext.newInstance(new Class[] {objClass});
			Unmarshaller u = jc.createUnmarshaller();
			Object ebean = objClass.cast(u.unmarshal(new StringReader(msg)));
			return ebean;
		} catch (JAXBException e) {
			e.printStackTrace();
			log.error("解析消息出错",e.getMessage());
		}
		return null;
	}
	/**
	 * 生成内容
	 * @param <T>
	 * @param mesgBean
	 * @return
	 */
	public static String bulidMessage(Object mesgBean)
	  {
	    Marshaller m = null;
	    try {
	      JAXBContext jc = JAXBContext.newInstance(new Class[] { mesgBean.getClass() });
	      m = jc.createMarshaller();
	      m.setProperty("jaxb.encoding", "UTF-8");
	      m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));

	      m.setProperty("jaxb.fragment", Boolean.valueOf(true));

	      m.setProperty("com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler", new CharacterEscapeHandler()
	      {
	        public void escape(char[] ch, int start, int length, boolean isAttVal, Writer writer)
	          throws IOException
	        {
	          writer.write(ch, start, length);
	        }
	      });
	      StringWriter ws = new StringWriter();
	      m.marshal(mesgBean, ws);
	      return ws.toString();
	    } catch (JAXBException e) {
	      log.error("生成消息出现错误: [{}]", e.getMessage());
	      e.printStackTrace();
	    }return null;
	  }
	
	/**
	 * 解析内容
	 * @param clazz
	 * @param xmlStr
	 * @return
	 */
	public static Object convertXmlStrToObject(Class clazz, String xmlStr) {  
        Object xmlObject = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(clazz);  
            // 进行将Xml转成对象的核心接口  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            StringReader sr = new StringReader(xmlStr);  
            xmlObject = unmarshaller.unmarshal(sr);  
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  
        return xmlObject;  
    }  
  
	
	public static void main(String[] args) {
	}
}
