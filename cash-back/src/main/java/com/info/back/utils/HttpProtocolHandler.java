package com.info.back.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.FilePartSource;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;
import org.apache.commons.lang3.StringUtils;


/* *
 *类名：HttpProtocolHandler
 *功能：HttpClient方式访问
 *详细：获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class HttpProtocolHandler {

    private static String DEFAULT_CHARSET = "UTF-8";

    /** 连接超时时间，由bean factory设置，缺省为8秒钟 */
    private int defaultConnectionTimeout = 8000;

    /** 回应超时时间, 由bean factory设置，缺省为30秒钟 */
    private int defaultSoTimeout = 30000;

    /** 闲置连接超时时间, 由bean factory设置，缺省为60秒钟 */
    private int defaultIdleConnTimeout = 60000;

    private int defaultMaxConnPerHost = 30;

    private int defaultMaxTotalConn = 80;

    /** 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒 */
    private static final long defaultHttpConnectionManagerTimeout = 3 * 1000;

    /**
     * HTTP连接管理器，该连接管理器必须是线程安全的.
     */
    private HttpConnectionManager connectionManager;

    private static HttpProtocolHandler httpProtocolHandler = new HttpProtocolHandler();

    /**
     * 工厂方法
     *
     * @return
     */
    public static HttpProtocolHandler getInstance() {
        return httpProtocolHandler;
    }

    /**
     * 私有的构造方法
     */
    private HttpProtocolHandler() {
        // 创建一个线程安全的HTTP连接池
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(defaultMaxConnPerHost);
        connectionManager.getParams().setMaxTotalConnections(defaultMaxTotalConn);

        IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
        ict.addConnectionManager(connectionManager);
        ict.setConnectionTimeout(defaultIdleConnTimeout);

        ict.start();
    }

    /**
     * 初始化httpClient
     * @param request
     * @return
     */
    private HttpClient initClient(HttpRequest request){
        HttpClient httpclient = new HttpClient(connectionManager);

        // 设置连接超时
        int connectionTimeout = defaultConnectionTimeout;
        if (request.getConnectionTimeout() > 0) {
            connectionTimeout = request.getConnectionTimeout();
        }
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);

        // 设置回应超时
        int soTimeout = defaultSoTimeout;
        if (request.getTimeout() > 0) {
            soTimeout = request.getTimeout();
        }
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

        // 设置等待ConnectionManager释放connection的时间
        httpclient.getParams().setConnectionManagerTimeout(defaultHttpConnectionManagerTimeout);

        return httpclient;
    }

    /**
     * 初始化getMethod
     * @param request
     * @return
     */
    private HttpMethod initGetMethod(HttpRequest request){

        HttpMethod method = null;
        String charset = request.getCharset();
        charset = charset == null ? DEFAULT_CHARSET : charset;

        method = new GetMethod(request.getUrl());
        method.getParams().setCredentialCharset(charset);

        // parseNotifyConfig会保证使用GET方法时，request一定使用QueryString
        method.setQueryString(request.getQueryString());

        // 设置Http Header中的User-Agent属性
        method.addRequestHeader("User-Agent", "Mozilla/4.0");
        return method;
    }

    /**
     * 初始化postMethod
     * @param request
     * @return
     */
    private HttpMethod initPostMethod(HttpRequest request){
        HttpMethod method = null;
        String charset = request.getCharset();
        charset = charset == null ? DEFAULT_CHARSET : charset;
        DEFAULT_CHARSET = charset;
        //post模式且不带上传文件
        method = new PostMethod(request.getUrl());
        return method;
    }

    /**
     * get方式请求
     * @param request
     * @return String
     * @throws UnsupportedEncodingException
     */
    public String getHttpClient(HttpRequest request) throws UnsupportedEncodingException {

        HttpClient httpclient = initClient(request);
        HttpMethod method = initGetMethod(request);
        HttpResponse response = new HttpResponse();

        try {
            httpclient.executeMethod(method);
            response.setStringResult(method.getResponseBodyAsString());
            response.setResponseHeaders(method.getResponseHeaders());
        } catch (UnknownHostException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        } finally {
            method.releaseConnection();
        }
        return response.getStringResult();

    }

    /**
     * get方式请求
     * @param url
     * @param resultType 返回的结果字符方式 1:字符串方式  0:BYTES, 不设置传空
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getHttpClient(String url, String resultType) throws UnsupportedEncodingException{
        HttpRequest request = null;
        if(StringUtils.isNotEmpty(resultType)){
            request = new HttpRequest(HttpResultType.BYTES);
        } else {
            request = new HttpRequest(resultType == "1" ? HttpResultType.BYTES : HttpResultType.STRING);
        }
        //设置编码集
        request.setCharset("utf-8");
        request.setUrl(url);
        return getHttpClient(request);
    }

    /**
     * get方式请求
     * @param url
     * @param resultType 返回的结果字符方式 1:字符串方式  0:BYTES, 不设置传空
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getHttpClient(String url, String resultType,  Map<String, String> params) throws UnsupportedEncodingException{
        HttpRequest request = null;
        if(StringUtils.isNotEmpty(resultType)){
            request = new HttpRequest(HttpResultType.BYTES);
        } else {
            request = new HttpRequest(resultType == "1" ? HttpResultType.BYTES : HttpResultType.STRING);
        }
        //设置编码集
        request.setCharset("utf-8");
        request.setParameters(generatNameValuePair(params));
        request.setQueryString(toString(request.getParameters()));
        request.setUrl(url);
        return getHttpClient(request);
    }

    /**
     * post方式请求,post模式且不带上传文件
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public String postHttpClient(HttpRequest request) throws UnsupportedEncodingException{
        HttpClient httpclient = initClient(request);

        HttpMethod method = initPostMethod(request);
        ((PostMethod) method).addParameters(request.getParameters());
        method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + DEFAULT_CHARSET);
        HttpResponse response = new HttpResponse();

        try {
            httpclient.executeMethod(method);
            response.setStringResult(method.getResponseBodyAsString());
            response.setResponseHeaders(method.getResponseHeaders());
        } catch (UnknownHostException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        } finally {
            method.releaseConnection();
        }
        return response.getStringResult();
    }

    /**
     * post方式请求,post模式且不带上传文件
     * @param url 请求地址
     * @param resultType 1为字节数组方式，0为字符串方式
     * @return
     * @throws UnsupportedEncodingException
     */
    public String postHttpClient(String url, String resultType) throws UnsupportedEncodingException{

        HttpRequest request = null;
        if(StringUtils.isNotEmpty(resultType)){
            request = new HttpRequest(HttpResultType.BYTES);
        } else {
            request = new HttpRequest(resultType == "1" ? HttpResultType.BYTES : HttpResultType.STRING);
        }

        //设置编码集
        request.setCharset("utf-8");
        request.setUrl(url);
        return postHttpClient(request);
    }

    /**
     * post方式请求,post模式且不带上传文件
     * @param request 请求地址
     * @param resultType 1为字节数组方式，0为字符串方式
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public String postHttpClient(String url, String resultType,  Map<String, String> params) throws UnsupportedEncodingException{

        HttpRequest request = null;
        if(StringUtils.isNotEmpty(resultType)){
            request = new HttpRequest(HttpResultType.BYTES);
        } else {
            request = new HttpRequest(resultType == "1" ? HttpResultType.BYTES : HttpResultType.STRING);
        }

        //设置编码集
        request.setCharset("utf-8");
        request.setParameters(generatNameValuePair(params));
        request.setUrl(url);
        return postHttpClient(request);
    }

    /**
     * post方式请求,post模式且不带上传文件
     * @param request 请求地址
     * @param resultType 1为字节数组方式，0为字符串方式
     * @param params
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public String postHttpClient(String url, String resultType,  Map<String, String> params,String charset ) throws UnsupportedEncodingException{

        HttpRequest request = null;
        if(StringUtils.isNotEmpty(resultType)){
            request = new HttpRequest(HttpResultType.BYTES);
        } else {
            request = new HttpRequest(resultType == "1" ? HttpResultType.BYTES : HttpResultType.STRING);
        }

        //设置编码集
        request.setCharset(charset);
        request.setParameters(generatNameValuePair(params));
        request.setUrl(url);
        return postHttpClient(request);
    }

    /**
     * post方式请求,post模式且带上传文件
     * @param request
     * @param strParaFileName
     * @param strFilePath
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public String postHttpClient(HttpRequest request, String strParaFileName, String strFilePath) throws UnsupportedEncodingException, FileNotFoundException{
        HttpClient httpclient = initClient(request);

        HttpMethod method = initPostMethod(request);
        List<Part> parts = new ArrayList<Part>();
        for (int i = 0; i < request.getParameters().length; i++) {
            parts.add(new StringPart(request.getParameters()[i].getName(), request.getParameters()[i].getValue(), DEFAULT_CHARSET));
        }
        //增加文件参数，strParaFileName是参数名，使用本地文件
        parts.add(new FilePart(strParaFileName, new FilePartSource(new File(strFilePath))));

        // 设置请求体
        ((PostMethod) method).setRequestEntity(new MultipartRequestEntity(parts.toArray(new Part[0]), new HttpMethodParams()));
        HttpResponse response = new HttpResponse();

        try {
            httpclient.executeMethod(method);
            response.setStringResult(method.getResponseBodyAsString());
            response.setResponseHeaders(method.getResponseHeaders());
        } catch (UnknownHostException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        } finally {
            method.releaseConnection();
        }
        return response.getStringResult();
    }

    /**
     * post方式请求,post模式且带上传文件
     * @param url
     * @param strParaFileName
     * @param strFilePath
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public String postHttpClient(String url, String resultType, String strParaFileName, String strFilePath) throws UnsupportedEncodingException, FileNotFoundException{
        HttpRequest request = null;
        if(StringUtils.isNotEmpty(resultType)){
            request = new HttpRequest(HttpResultType.BYTES);
        } else {
            request = new HttpRequest(resultType == "1" ? HttpResultType.BYTES : HttpResultType.STRING);
        }
        //设置编码集
        request.setCharset("utf-8");
        request.setUrl(url);

        return postHttpClient(request);
    }


    /**
     * 执行Http请求
     *
     * @param request 请求数据
     * @param strParaFileName 文件类型的参数名
     * @param strFilePath 文件路径
     * @return
     * @throws HttpException, IOException
     */
    public HttpResponse execute(HttpRequest request, String strParaFileName, String strFilePath) throws HttpException, IOException {
        HttpClient httpclient = initClient(request);

        HttpMethod method = null;
        String charset = request.getCharset();
        charset = charset == null ? DEFAULT_CHARSET : charset;

        //get模式且不带上传文件
        if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
            method = new GetMethod(request.getUrl());
            method.getParams().setCredentialCharset(charset);

            // parseNotifyConfig会保证使用GET方法时，request一定使用QueryString
            method.setQueryString(request.getQueryString());
        } else if(strParaFileName.equals("") && strFilePath.equals("")) {
            //post模式且不带上传文件
            method = new PostMethod(request.getUrl());
            ((PostMethod) method).addParameters(request.getParameters());
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
        } else {
            //post模式且带上传文件
            method = new PostMethod(request.getUrl());
            List<Part> parts = new ArrayList<Part>();
            for (int i = 0; i < request.getParameters().length; i++) {
                parts.add(new StringPart(request.getParameters()[i].getName(), request.getParameters()[i].getValue(), charset));
            }
            //增加文件参数，strParaFileName是参数名，使用本地文件
            parts.add(new FilePart(strParaFileName, new FilePartSource(new File(strFilePath))));

            // 设置请求体
            ((PostMethod) method).setRequestEntity(new MultipartRequestEntity(parts.toArray(new Part[0]), new HttpMethodParams()));
        }

        // 设置Http Header中的User-Agent属性
        method.addRequestHeader("User-Agent", "Mozilla/4.0");
        HttpResponse response = new HttpResponse();

        try {
            httpclient.executeMethod(method);
            if (request.getResultType().equals(HttpResultType.STRING)) {
                response.setStringResult(method.getResponseBodyAsString());
            } else if (request.getResultType().equals(HttpResultType.BYTES)) {
                response.setByteResult(method.getResponseBody());
            }
            response.setResponseHeaders(method.getResponseHeaders());
        } catch (UnknownHostException ex) {

            return null;
        } catch (IOException ex) {

            return null;
        } catch (Exception ex) {

            return null;
        } finally {
            method.releaseConnection();
        }
        return response;
    }

    /**
     * 将NameValuePairs数组转变为字符串
     *
     * @param nameValues
     * @return
     */
    protected String toString(NameValuePair[] nameValues) {
        if (nameValues == null || nameValues.length == 0) {
            return "null";
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < nameValues.length; i++) {
            NameValuePair nameValue = nameValues[i];

            if (i == 0) {
                buffer.append(nameValue.getName() + "=" + nameValue.getValue());
            } else {
                buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
            }
        }

        return buffer.toString();
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    protected static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        return nameValuePair;
    }

    /**
     *
     * @param URL
     * @return
     * @throws Exception
     */
    public static String httpsRequest(String URL) throws Exception{
        URL reqURL = new URL(URL); //创建URL对象
        HttpsURLConnection httpsConn = (HttpsURLConnection)reqURL.openConnection();

        //取得该连接的输入流，以读取响应内容
        InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream(),"UTF-8");
        BufferedReader reader = new BufferedReader(insr);
        //读取服务器的响应内容并显示

        StringBuffer sb=new StringBuffer();
        while(true){
            String str = reader.readLine();
            if(str==null)break;
            sb.append(str);
        }
        reader.close();
        insr.close();

        return sb.toString();
    }
    /**
     * 流浪猫  发送短信接口(liuyuan)
     * @param map
     * @return 返回信息为xml格式，看打印的line
     * error_code的值异常对应如下
     * 000000 提交成功
     * 100001  	缺少参数
     *   100002	访问地址异常（IP不在白名单，请与管理员联系）
     * 100003	app_key错误
     * 100004	sign错误
     * 100005	time_stamp错误
     * 100006	content参数体错误
     * 100008	访问频率过快 ，请稍后再试
     * 100009	参数错误，mission_num错误,dest_id填误
     * 100010	该接口暂停使用
     * 100011	该用户被冻结
     * 100012	content中短信签名错误
     * 100013	短信数量超出最大值
     * 100014	找不到模板
     * 200001	批次号重复
     * 200002	余额不足
     * 200003	批次号错误
     * 999998	系统错误，
     * @throws IOException
     */
    public String getPost(String app_key,String app_secret,String nonce_str,
                          String dest_id,String title,String content,String URL) {
        String code = "1";
        try {
            URL url = new URL(URL);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            con.setRequestProperty("charset", "UTF-8");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(),"UTF-8");
            GetXmlInfo xml=new GetXmlInfo();
            String xmlInfo = xml.getXmlInfo( app_key,app_secret,nonce_str,dest_id,title,content);
            System.out.println("urlStr=" + URL);
            System.out.println("xmlInfo=" + xmlInfo);
            out.write(new String(xmlInfo.getBytes("UTF-8"),"UTF-8"));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                if(line.contains("000000")){
                    code="0";
                }
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }
}
