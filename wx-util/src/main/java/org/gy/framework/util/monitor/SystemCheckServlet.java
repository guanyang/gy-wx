package org.gy.framework.util.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemCheckServlet extends HttpServlet {

    private static final long               serialVersionUID      = 8353616646290443012L;

    private final Logger                    logger                = LoggerFactory.getLogger(getClass());

    public static final String              DEFAULT_LOCATION_NAME = "propertyConfigLocation";

    public static final Map<String, String> PropertyMap           = new HashMap<String, String>();

    private static HttpClient               httpClient;

    private String                          propertyConfigLocation;

    static {
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(10000).build();
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setMaxConnTotal(10000);
        builder.setMaxConnPerRoute(10);
        builder.setDefaultSocketConfig(socketConfig);
        httpClient = builder.build();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder result = new StringBuilder();
        result.append("{");
        if (PropertyMap != null && PropertyMap.size() > 0) {
            String key = null;
            String value = null;
            for (Map.Entry<String, String> entry : PropertyMap.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                if (value != null && !value.equals("")) {
                    String res = check(value);
                    result.append("\"" + key + "-" + value + "\":");
                    result.append("\"" + res + "\"");
                    result.append(",");
                }
            }
            if (result.toString().endsWith(",")) {
                result.deleteCharAt(result.length() - 1);
            }
        }
        result.append("}");
        resp.getWriter().write(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        propertyConfigLocation = config.getInitParameter(DEFAULT_LOCATION_NAME);
        loadProperties(propertyConfigLocation, PropertyMap);
    }

    private void loadProperties(String location, Map<String, String> propertyMap) {
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(location);
            if (is == null) {
                return;
            }
            Properties pro = new Properties();
            pro.load(is);
            Enumeration<?> e = pro.propertyNames();
            String key = null;
            String value = null;
            while (e.hasMoreElements()) {
                key = (String) e.nextElement();
                value = pro.getProperty(key);
                propertyMap.put(key, value == null ? "" : value.trim());
            }
        } catch (IOException ie) {
            logger.error(ie.getMessage(), ie);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ie) {
                }
            }
        }
    }

    private String check(String url) {
        StringBuilder sb = new StringBuilder();
        HttpGet get = null;
        try {
            get = new HttpGet(url);
            HttpResponse response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                sb.append(response.getStatusLine().toString());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            sb.append(e.getMessage());
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
        }
        return sb.toString();
    }

}
