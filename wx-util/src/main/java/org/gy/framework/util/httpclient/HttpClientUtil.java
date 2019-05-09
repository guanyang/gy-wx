package org.gy.framework.util.httpclient;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http交互工具类
 */
public class HttpClientUtil {

    protected static final Logger            logger                          = LoggerFactory.getLogger(HttpClientUtil.class);

    public static final String               METHOD_POST                     = "POST";
    public static final String               METHOD_GET                      = "GET";
    public static final String               DEFAULT_CHARSET                 = "utf-8";
    public static final int                  DEFAULT_CONNECT_TIMEOUT         = 5000;
    public static final int                  DEFAULT_READ_TIMEOUT            = 5000;
    public static final int                  DEFAULT_CONNECT_REQUEST_TIMEOUT = 5000;

    private static final int                 MAX_TOTAL                       = 64;

    private static final int                 MAX_PER_ROUTE                   = 32;

    private static final CloseableHttpClient httpClient;

    static {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_READ_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT).build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);

        HttpClientBuilder httpBuilder = HttpClientBuilder.create();
        httpBuilder.setDefaultRequestConfig(requestConfig);
        httpBuilder.setConnectionManager(connectionManager);
        httpClient = httpBuilder.build();
    }

    private HttpClientUtil() {

    }

    public static String get(String url,
                             Map<String, String> headers) {
        return get(httpClient, url, headers);
    }

    public static String get(CloseableHttpClient httpClient,
                             String url,
                             Map<String, String> headers) {
        HttpGet request = new HttpGet(url);
        try {
            wrapHeader(request, headers);// 设置请求头
            return execute(request, httpClient);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            request.releaseConnection();
        }
        return null;
    }

    public static String postBody(String url,
                                  String body,
                                  Map<String, String> headers) {
        return postBody(httpClient, url, body, headers);
    }

    public static String postBody(CloseableHttpClient httpClient,
                                  String url,
                                  String body,
                                  Map<String, String> headers) {
        HttpPost request = new HttpPost(url);
        try {
            wrapHeader(request, headers);// 设置请求头
            wrapStringEntity(request, body);// 设置body
            return execute(request, httpClient);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            request.releaseConnection();
        }
        return null;
    }

    public static String postForm(String url,
                                  Map<String, String> params,
                                  Map<String, String> headers) {
        return postForm(httpClient, url, params, headers);
    }

    public static String postForm(CloseableHttpClient httpClient,
                                  String url,
                                  Map<String, String> params,
                                  Map<String, String> headers) {
        HttpPost request = new HttpPost(url);
        try {
            wrapHeader(request, headers);// 设置请求头
            wrapFormEntity(request, params);
            return execute(request, httpClient);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            request.releaseConnection();
        }
        return null;
    }

    private static String execute(HttpRequestBase request,
                                  CloseableHttpClient httpClient) {
        String respJson = null;
        try (CloseableHttpResponse response = httpClient.execute(request);) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                respJson = EntityUtils.toString(httpEntity, DEFAULT_CHARSET);
                EntityUtils.consume(httpEntity);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return respJson;
    }

    private static void wrapHeader(HttpRequestBase request,
                                   Map<String, String> headers) {
        // 设置请求头
        if (null != headers) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void wrapStringEntity(HttpPost request,
                                         String body) {
        // 设置body
        if (body != null) {
            StringEntity entity = new StringEntity(body, DEFAULT_CHARSET);// 解决中文乱码问题
            entity.setContentEncoding(DEFAULT_CHARSET);
            request.setEntity(entity);
        }
    }

    private static void wrapFormEntity(HttpPost request,
                                       Map<String, String> params) throws UnsupportedEncodingException {
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET));
        }
    }
}
