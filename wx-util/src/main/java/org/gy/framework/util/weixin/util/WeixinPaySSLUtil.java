package org.gy.framework.util.weixin.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.gy.framework.util.httpclient.HttpClientUtil;
import org.gy.framework.util.weixin.config.WeiXinConfig;
import org.gy.framework.util.weixin.exception.WeiXinException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class WeixinPaySSLUtil {
    private static final Logger              logger                          = LoggerFactory.getLogger(WeixinPaySSLUtil.class);

    private static final String              DEFAULT_LOCATION                = WeiXinConfig.getValue("wx.pay.cert.path");

    private static final String              MCHID                           = WeiXinConfig.getValue("wx.pay.mchid");

    public static final String               DEFAULT_CHARSET                 = "utf-8";
    public static final int                  DEFAULT_CONNECT_TIMEOUT         = 5000;
    public static final int                  DEFAULT_READ_TIMEOUT            = 5000;
    public static final int                  DEFAULT_CONNECT_REQUEST_TIMEOUT = 5000;

    private static final int                 MAX_TOTAL                       = 32;
    private static final int                 MAX_PER_ROUTE                   = 16;

    private static final CloseableHttpClient httpClient;

    static {

        SSLConnectionSocketFactory sslsf = initSSLSocketFactory();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", sslsf).register("http", PlainConnectionSocketFactory.getSocketFactory()).build();

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_READ_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT).build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(MAX_TOTAL);
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);

        HttpClientBuilder httpBuilder = HttpClients.custom();
        httpBuilder.setDefaultRequestConfig(requestConfig);
        httpBuilder.setConnectionManager(connectionManager);
        httpBuilder.setSSLSocketFactory(sslsf);

        httpClient = httpBuilder.build();
    }

    private WeixinPaySSLUtil() {

    }

    private static SSLConnectionSocketFactory initSSLSocketFactory() {

        SSLContext sslcontext;
        try {
            KeyStore keyStore = getKeyStore(DEFAULT_LOCATION);// 加载默认证书配置
            sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MCHID.toCharArray()).build();
        } catch (Exception e) {
            logger.error("加载微信证书异常：" + e.getMessage(), e);
            throw new WeiXinException(e.getMessage(), e);
        }
        // Allow TLSv1 protocol only
        return new SSLConnectionSocketFactory(sslcontext, new String[] {
            "TLSv1"
        }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    private static KeyStore getKeyStore(String configPath) {
        KeyStore keyStore = null;
        try (FileInputStream is = new FileInputStream(new File(configPath))) {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(is, MCHID.toCharArray());
        } catch (Exception e) {
            throw new WeiXinException(e.getMessage(), e);
        }
        return keyStore;
    }

    public static String get(String url,
                             Map<String, String> headers) {

        return HttpClientUtil.get(httpClient, url, headers);
    }

    public static String postBody(String url,
                                  String body,
                                  Map<String, String> headers) {
        return HttpClientUtil.postBody(httpClient, url, body, headers);
    }

    public static String postForm(String url,
                                  Map<String, String> params,
                                  Map<String, String> headers) {
        return HttpClientUtil.postForm(httpClient, url, params, headers);
    }

}
