package com.info.back.utils;

import com.info.back.vo.JxlResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    // 聚信立get
    public static JxlResponse get(String url, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //超时设置
        RequestConfig requestConfig = getRequestConfig();
        CloseableHttpResponse response = null;
        try {
            String requestParams = parseParams(params);
            String extraUrl = url + (StringUtils.isEmpty(requestParams) ? "" : "?" + requestParams);
            HttpGet get = new HttpGet(extraUrl);
            get.setConfig(requestConfig);
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            String type= response.getFirstHeader("X-SUBTYPE").getValue();
            if (StringUtils.isNoneBlank(type)){
                JxlResponse jxlResponse = new JxlResponse();
                jxlResponse.setJxlType(type);
                jxlResponse.setJxlData(EntityUtils.toString(entity));
                return jxlResponse;
            }
//            String  str = EntityUtils.toString(entity);
//            System.out.println(str);
            return null;

        } catch (Exception e) {
            LOGGER.error("访问链接异常[GET] url=" + url, e);
            return null;
        } finally {
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }

    public static String doGet(String url, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //超时设置
        RequestConfig requestConfig = getRequestConfig();
        CloseableHttpResponse response = null;
        try {
            String requestParams = parseParams(params);
            String extraUrl = url + (StringUtils.isEmpty(requestParams) ? "" : "?" + requestParams);
            HttpGet get = new HttpGet(extraUrl);
            get.setConfig(requestConfig);
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            LOGGER.error("访问链接异常[GET] url=" + url, e);
            return null;
        } finally {
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }


    public static String post(String url, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parsePairs(params), "UTF-8");
            post.setEntity(entity);
            response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity);
        } catch (Exception e) {
            LOGGER.error("访问链接异常[POST] url=" + url, e);
            return null;
        } finally {
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }

    public static String post(String url, String text) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = getRequestConfig();
        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            StringEntity entity = new StringEntity(text, "UTF-8");
            post.setEntity(entity);
            response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity);
        } catch (Exception e) {
            LOGGER.error("访问链接异常[POST] url=" + url, e);
            return null;
        } finally {
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }

    /**
     * 设置HTTP配置
     * 3秒超时
     * @return
     */
    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(3000).setConnectTimeout(3000)
                .setSocketTimeout(3000).build();
    }

    @SuppressWarnings("unchecked")
    private static String parseParams(Map<String, Object> params) throws Exception{
        if(CollectionUtils.isEmpty(params)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value == null) {
                continue;
            }
            if (value.getClass().isArray()) {
                for (int i = 0; i < Array.getLength(value); i++) {
                    String item = URLEncoder.encode(Array.get(value, i).toString(), "UTF-8");
                    sb.append(key).append('=').append(item).append('&');
                }
            } else if(value instanceof List) {
                List<Object> items = (List<Object>) value;
                for (Object item : items) {
                    String str = URLEncoder.encode(item.toString(), "UTF-8");
                    sb.append(key).append('=').append(str).append('&');
                }
            } else {
                String str = URLEncoder.encode(value.toString(), "UTF-8");
                sb.append(key).append('=').append(str).append('&');
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private static List<BasicNameValuePair> parsePairs(Map<String, Object> params) {
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        if(CollectionUtils.isEmpty(params)) {
            return list;
        }

        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value == null) {
                continue;
            }
            if (value.getClass().isArray()) {
                for (int i = 0; i < Array.getLength(value); i++) {
                    String item = Array.get(value, i).toString();
                    list.add(new BasicNameValuePair(key, item));
                }
            } else if(value instanceof List) {
                List<Object> items = (List<Object>) value;
                for (Object item : items) {
                    String str = item.toString();
                    list.add(new BasicNameValuePair(key, str));
                }
            } else {
                String str = value.toString();
                list.add(new BasicNameValuePair(key, str));
            }

        }
        return list;
    }

}
