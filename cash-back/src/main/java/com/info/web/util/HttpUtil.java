package com.info.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	Logger logger = Logger.getLogger(getClass());
	public static HttpUtil httpUtil;

	public static HttpUtil getInstance() {
		if (httpUtil == null) {
			httpUtil = new HttpUtil();
		}
		return httpUtil;
	}

	public String doPost(String url, String params)
			throws ClientProtocolException, IOException {
		logger.info("请求参数:" + params.toString());
		String result = "";
		HttpPost httpPost = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(params.toString(), "utf-8");
		httpPost.setHeader("Content-Type", "text/xml;charset=utf-8");

		httpPost.setEntity(stringEntity);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(httpPost);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			String resultStr = reader.readLine();
			logger.info("resultStr=" + resultStr);
			while (null != resultStr) {
				result = resultStr;
				resultStr = reader.readLine();
			}
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}
	
	
	
    public static String dopostMap(String url,Map<String, String> params) {
    	String result="";
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (String temp : params.keySet()) {
			formparams.add(new BasicNameValuePair(temp, params.get(temp).toString()));
		}
        UrlEncodedFormEntity uefEntity;
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result= EntityUtils.toString(entity, "utf-8");
                }
            } finally {  
                response.close();
            }  
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) { 
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace(); 
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) { 
                e.printStackTrace();
            }
        }
		return result;
    } 
	
	
	
	public static String  getHttpMess(String surl,String inputParam,String requestMethod, String charset){
		StringBuffer sbReturn = new StringBuffer();
		URL url=null;
		HttpURLConnection connection=null;
		try {
			url=new URL(surl);
			connection=(HttpURLConnection)url.openConnection();
			connection.setRequestMethod(requestMethod);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "text/xml");
			connection.connect();
	        OutputStream output = connection.getOutputStream();
	        OutputStreamWriter osWriter = new OutputStreamWriter(output);
	        osWriter.write(inputParam.toString().toCharArray(), 0, inputParam
	                .toString().length());
	        osWriter.flush();
	        osWriter.close();
			int statusCode = connection.getResponseCode();
			if (statusCode != HttpURLConnection.HTTP_OK) {/* 4 判断访问的状态码 */
				return null;
			}
			InputStream in = null;
			in = connection.getInputStream();
			BufferedReader data = new BufferedReader(
					new InputStreamReader(in,charset));
			String tempbf;
			while ((tempbf = data.readLine()) != null) {
				sbReturn.append(tempbf);
				sbReturn.append("\r\n");
			}
			data.close();
			in.close();
		}catch (Exception e) {
			return null;
		}finally{
			try{
				if(connection!=null){
					connection.disconnect();
				}
			}catch (Exception e) {
				connection=null;
			}
		}
		return sbReturn.toString();
	}
}
