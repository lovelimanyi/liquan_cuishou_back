package com.info.back.utils;

import com.info.back.vo.JxlResponse;
import com.info.config.PayContents;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static final String JXL_URL = "/report/jxl_raw_report";

    private static final Logger logger = Logger.getLogger(HttpUtils.class);

    /**
     * Basic Auth认证头信息
     *
     * @return authHeader
     */
    private static String getAuthHeader() {
//        HashMap<String, String> map = SysCacheUtils.getConfigParams("GET_JXL");
//        String key = map.get("JXL_GET_AUTH_NAME");
//        String pwd = map.get("JXL_GET_AUTH_PWD");
//        String auth = "xjx_application_client_stage"+":"+"xjx_stage";
        String auth = PayContents.AUTHENTICATE_USERNAME + ":" + PayContents.AUTHENTICATE_PASSWORD;
        byte[] encodedAuth = Base64Utils.encode(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    private static String getOldAuthHeader() {
//        HashMap<String, String> map = SysCacheUtils.getConfigParams("GET_JXL");
//        String key = map.get("JXL_GET_AUTH_NAME");
//        String pwd = map.get("JXL_GET_AUTH_PWD");
        String auth = "xjx_application_client_stage"+":"+"xjx_stage";
        byte[] encodedAuth = Base64Utils.encode(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }


    // 聚信立get
    public static JxlResponse get(String url, Map<String, Object> params) {
        String phone = url.substring(url.length() - 11, url.length());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //超时设置
        RequestConfig requestConfig = getRequestConfig();
        CloseableHttpResponse response = null;
        try {
            String requestParams = parseParams(params);
            String extraUrl = url + (StringUtils.isEmpty(requestParams) ? "" : "?" + requestParams);
            HttpGet get = new HttpGet(extraUrl);
            if (url.contains(JXL_URL)) {
                get.addHeader("Authorization", getOldAuthHeader());
            }
            get.setConfig(requestConfig);
            response = httpClient.execute(get);
            String tuid = response.getFirstHeader("X-TUID") == null ? null : response.getFirstHeader("X-TUID").getValue();
            String subtype = response.getFirstHeader("X-SUBTYPE") == null ? null : response.getFirstHeader("X-SUBTYPE").getValue();
            logger.info(phone + " phone-jxlGetJsonData-tuid= " + tuid + ",subtype=" + subtype);
            HttpEntity entity = response.getEntity();
//            logger.info(phone+"phone-JxlData="+EntityUtils.toString(entity));
//            String type= response.getFirstHeader("X-SUBTYPE").getValue();
            if (StringUtils.isNoneBlank(subtype)) {
                JxlResponse jxlResponse = new JxlResponse();
                jxlResponse.setJxlType(subtype);
                jxlResponse.setJxlData(EntityUtils.toString(entity));
                return jxlResponse;
            }
            return null;

        } catch (Exception e) {
            logger.error("访问链接异常[GET] url=" + url, e);
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }

    public static String postJson(String apiURL, String params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(apiURL);
        String body = null;
//      logger.info("parameters:" + parameters);
        try {

            // 建立一个NameValuePair数组，用于存储欲传送的参数
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            if (params != null) {
                // 设置字符集
                StringEntity stringEntity = new StringEntity(params, "utf-8");
                // 设置参数实体
                httpPost.setEntity(stringEntity);
            }
//          httpPost.setEntity(new SerializableEntity(parameters, Charset.forName("UTF-8")));

            org.apache.http.HttpResponse response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
//                logger.error("Method failed:" + response.getStatusLine());
            }

            // Read the response body
            body = EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            // 网络错误
            e.printStackTrace();
        }
        return body;
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
            if (url.contains(PayContents.GXB_ECOMMERCE_INFOS)) {
                get.addHeader("Authorization", getAuthHeader());
            }
            get.setConfig(requestConfig);
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            logger.error("访问链接异常[GET] url=" + url, e);
            return null;
        } finally {
            if (response != null) {
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
            logger.error("访问链接异常[POST] url=" + url, e);
            return null;
        } finally {
            if (response != null) {
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
            logger.error("访问链接异常[POST] url=" + url, e);
            return null;
        } finally {
            if (response != null) {
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
     *
     * @return
     */
    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(3000).setConnectTimeout(3000)
                .setSocketTimeout(3000).build();
    }

    @SuppressWarnings("unchecked")
    private static String parseParams(Map<String, Object> params) throws Exception {
        if (CollectionUtils.isEmpty(params)) {
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
            } else if (value instanceof List) {
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
        if (CollectionUtils.isEmpty(params)) {
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
            } else if (value instanceof List) {
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
