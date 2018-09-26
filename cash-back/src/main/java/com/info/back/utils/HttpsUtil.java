package com.info.back.utils;

import com.info.config.PayContents;
import org.springframework.util.Base64Utils;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-09-20 下午 4:53
 **/
public class HttpsUtil {

    /**
     * 发送https请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {

        String result = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.addRequestProperty("Authorization", getAuthHeader());
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("utf-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            int code = conn.getResponseCode();
            if(code == 200){
                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str;
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                // 释放资源
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                conn.disconnect();
                result = buffer.toString();
            }
        } catch (ConnectException ce) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Basic Auth认证头信息
     *
     * @return authHeader
     */
    private static String getAuthHeader() {
        String auth = PayContents.AUTHENTICATE_USERNAME + ":" + PayContents.AUTHENTICATE_PASSWORD;
        byte[] encodedAuth = Base64Utils.encode(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }
}
