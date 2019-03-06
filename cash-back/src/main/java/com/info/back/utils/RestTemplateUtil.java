package com.info.back.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Myron on 2017/8/4 0004.
 */
public class RestTemplateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateUtil.class);

    /**
     * 发送post请求
     * @param restTemplate
     * @param url 请求链接
     * @param param 参数
     * @param httpHeaders 请求头
     * @param responseType 返回类型
     * @param <T> 返回类型
     * @return
     */
    public static <T> T postForEntity(RestTemplate restTemplate, String url, Object param
            , HttpHeaders httpHeaders , Class<T> responseType) {
        LOGGER.info("请求开始 >>>>>>> 请求地址：{}, 请求参数： {}", url, JSON.toJSONString(param));
        T result = null;
        try {
            HttpEntity<?> httpEntity = new HttpEntity<>(param, httpHeaders);
            // 构建参数
            httpEntity = convert(httpEntity);
            // 发送请求
            ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, httpEntity, responseType);
            // 返回内容
            result = responseEntity.getBody();
            // 状态码
            if (HttpStatus.OK.value() != responseEntity.getStatusCode().value()) {
                LOGGER.error("请求服务返回错误，statusCode: {}，statusContent: {}",
                        responseEntity.getStatusCode().value(), responseEntity.getStatusCode().getReasonPhrase());
            }
        } catch (Exception e) {
            LOGGER.error("请求服务失败，失败原因为：", e);
            throw e;
        }
        LOGGER.info("请求结束 >>>>>>> 请求地址：{}, 请求结果： {}", url, JSON.toJSONString(result));
        return result;
    }

    /**
     * 发送post请求
     * @param restTemplate
     * @param url 请求链接
     * @param param 参数
     * @param httpHeaders 请求头
     * @param responseType 返回类型
     * @param <T> 返回类型
     * @return
     */
    public static <T> T postExchance(RestTemplate restTemplate, String url, Object param
            , HttpHeaders httpHeaders , Class<T> responseType) {
        LOGGER.info("请求开始 >>>>>>> 请求地址：{}, 请求参数： {}", url, JSON.toJSONString(param));
        T result = null;
        try {
            HttpEntity<?> httpEntity = new HttpEntity<>(param, httpHeaders);
            // 构建参数
            httpEntity = convert(httpEntity);
            // 发送请求
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType);
            // 返回内容
            result = responseEntity.getBody();
            // 状态码
            if (HttpStatus.OK.value() != responseEntity.getStatusCode().value()) {
                LOGGER.error("请求服务返回错误，statusCode: {}，statusContent: {}",
                        responseEntity.getStatusCode().value(), responseEntity.getStatusCode().getReasonPhrase());
            }
        } catch (Exception e) {
            LOGGER.error("请求服务失败，失败原因为：", e);
            throw e;
        }
        LOGGER.info("请求结束 >>>>>>> 请求地址：{}, 请求结果： {}", url, JSON.toJSONString(result));
        return result;
    }

    /**
     * 发送post请求
     * @param restTemplate
     * @param url 请求链接
     * @param param 参数
     * @param responseType 返回类型
     * @param <T> 返回类型
     * @return
     */
    public static  <T> T postExchance(RestTemplate restTemplate, String url, Object param
            , Class<T> responseType) {
        HttpHeaders httpHeaders = buildBasicFORMHeaders();
        return postExchance(restTemplate, url, param, httpHeaders, responseType);
    }

    /**
     * 发送post请求
     * @param restTemplate
     * @param url 请求链接
     * @param param 参数
     * @param responseType 返回类型
     * @param <T> 返回类型
     * @return
     */
    public static  <T> T postFormForEntity(RestTemplate restTemplate, String url, Object param
            , Class<T> responseType) {
        HttpHeaders httpHeaders = buildBasicFORMHeaders();
        return postForEntity(restTemplate, url, param, httpHeaders, responseType);
    }

    /**
     * 发送post请求
     * @param restTemplate
     * @param url 请求链接
     * @param param 参数
     * @param responseType 返回类型
     * @param <T> 返回类型
     * @return
     */
    public static  <T> T postJsonForEntity(RestTemplate restTemplate, String url, Object param
            , Class<T> responseType) {
        HttpHeaders httpHeaders = buildBasicJSONHeaders();
        return postForEntity(restTemplate, url, param, httpHeaders, responseType);
    }

    /**
     * test
     * @param restTemplate
     * @param url
     * @param paramMap
     * @return
     */
    public String postForTest(RestTemplate restTemplate, String url, Map<String, String> paramMap) {
        return restTemplate.postForEntity(url, paramMap, String.class).getBody();
    }


    /**
     * 获取一个application/x-www-form-urlencoded头
     *
     * @return
     */
    public static HttpHeaders buildBasicFORMHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    /**
     * 获取一个application/json头
     *
     * @return
     */
    public static HttpHeaders buildBasicJSONHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * 获取一个text/html头
     *
     * @return
     */
    public static HttpHeaders buildBasicHTMLHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return headers;
    }

    /**
     * 对bean对象转表单模型做处理
     *
     * @param requestEntity
     * @return
     */
    private static HttpEntity<?> convert(HttpEntity<?> requestEntity) {
        Object body = requestEntity.getBody();
        HttpHeaders headers = requestEntity.getHeaders();

        if (body == null) {
            return requestEntity;
        }

        if (body instanceof Map) {
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            Map<String, ?> _body = (Map<String, ?>) body;
            for (String key : _body.keySet()) {
                multiValueMap.add(key, MapUtils.getString(_body, key));
            }

            requestEntity = new HttpEntity<>(multiValueMap, headers);
        }
        if (body instanceof String) {
            return requestEntity;
        }

        if (body instanceof Collection) {
            return requestEntity;
        }

        if (body instanceof Map) {
            return requestEntity;
        }


        if (headers != null && MediaType.APPLICATION_JSON.equals(headers.getContentType())) {
            return new HttpEntity<>(JSON.toJSONString(body), headers);
        }

        if (headers == null || !MediaType.APPLICATION_FORM_URLENCODED.equals(headers.getContentType())) {
            return requestEntity;
        }

        MultiValueMap<String, Object> formEntity = new LinkedMultiValueMap<>();

        Field[] fields = body.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            String value = null;

            try {
                value = BeanUtils.getProperty(body, name);
            } catch (Exception e) {
                LOGGER.error("resttemplate转换参数错误：{}", e);
            }

            formEntity.add(name, value);
        }

        return new HttpEntity<>(formEntity, headers);
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://SERVICE-GATEWAY/ucfpay/";
        Map<String, Object> param = new HashMap<>();
        Class<String> responseType = String.class;

        String aa = postFormForEntity(restTemplate, url, param, responseType);

        System.out.println(aa);
    }
}
