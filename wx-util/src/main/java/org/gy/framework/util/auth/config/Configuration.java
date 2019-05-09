package org.gy.framework.util.auth.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.auth.AuthFilter;
import org.gy.framework.util.auth.storage.StorageAccessor;
import org.gy.framework.util.auth.storage.redis.RedisStorageAccessorImpl;
import org.gy.framework.util.auth.storage.redis.RedisStorageAccessorImpl.RedisStorageConfig;
import org.gy.framework.util.cache.RedisConfig.CacheKey;
import org.gy.framework.util.jedis.RedisClient;
import org.gy.framework.util.properties.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Configuration {

    private static final Logger         LOGGER                       = LoggerFactory.getLogger(AuthFilter.class);

    private static final String         AUTH_ACCESS_POLICY_FILE_PATH = "authAccessPolicyFilePath";

    private static final String         WX_ACCESS_POLICY_FILE_PATH   = "wxAccessPolicyFilePath";

    private static final String         CONFIG_ATTR_NAME             = "AUTH.Configuration";

    public static final String          DEFAULT_KEY_PATTERN          = CacheKey.GY_CACHE_AUTH_KEY.getKeyPrefix();

    public static final int             DEFAULT_EXPIRE_TIME          = CacheKey.GY_CACHE_AUTH_KEY.getExpireTime();

    public static final String          DEFAULT_SNAPSHOT_KEY_PATTERN = CacheKey.GY_CACHE_SNAPSHOT_KEY.getKeyPrefix();

    public static final int             DEFAULT_SNAPSHOT_EXPIRE_TIME = CacheKey.GY_CACHE_SNAPSHOT_KEY.getExpireTime();

    private static final String         AUTH_COOKIE_NAME             = "authId";

    private static final AntPathMatcher pathMatcher                  = new AntPathMatcher();
    /**
     * 受限资源
     */
    private List<String>                restrictedAccessPatterns     = new ArrayList<>();
    /**
     * gateway资源
     */
    private List<String>                gatewayPatterns              = new ArrayList<>();

    /**
     * 商户访问资源
     */
    private List<String>                supplierPatterns             = new ArrayList<>();
    /**
     * 用户访问资源
     */
    private List<String>                customerPatterns             = new ArrayList<>();

    /**
     * storage Accessor.
     */
    private StorageAccessor             storageAccessor;
    /**
     * auth Url.
     */
    private String                      authUrl                      = PropertiesUtil.getValue("webServer") + "/login.htm";
    /**
     * cookie Domain.
     */
    private String                      cookieDomain;

    /**
     * auth Id Cookie Name.
     */
    private String                      authIdCookieName             = AUTH_COOKIE_NAME;

    private Configuration() {

    }

    public static void initialize(ServletContext servletContext) throws ServletException {
        try {
            if (servletContext.getAttribute(CONFIG_ATTR_NAME) != null) {
                LOGGER.warn("Configuration has initialized already.");
                return;
            }
            Configuration instance = new Configuration();
            ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            RedisClient redisClient = ctx.getBean(RedisClient.class);

            RedisStorageConfig storageConfig = new RedisStorageConfig();
            StorageAccessor storageAccessor = new RedisStorageAccessorImpl(redisClient, storageConfig);
            instance.setStorageAccessor(storageAccessor);

            // parse auth-accessPolicy.xml
            String restrictXml = servletContext.getInitParameter(AUTH_ACCESS_POLICY_FILE_PATH);
            if (StringUtils.isBlank(restrictXml)) {
                throw new ServletException("authAccessPolicyFilePath is null!");
            }
            InputStream accessIs = servletContext.getResourceAsStream(restrictXml);
            parseAccessPolicy(accessIs, instance);

            // parse wx-accessPolicy.xml
            String wxXml = servletContext.getInitParameter(WX_ACCESS_POLICY_FILE_PATH);
            if (StringUtils.isBlank(wxXml)) {
                throw new ServletException("wxAccessPolicyFilePath is null!");
            }
            InputStream wxAccess = servletContext.getResourceAsStream(wxXml);
            parseWxAccessPolicy(wxAccess, instance);

            // set configuration variable in servletContext
            servletContext.setAttribute(CONFIG_ATTR_NAME, instance);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    public static Configuration getInstance(ServletContext servletContext) {
        Configuration configuration = (Configuration) servletContext.getAttribute(CONFIG_ATTR_NAME);
        if (configuration == null) {
            throw new IllegalStateException("Can not find AUTH.Configuration in servletContext.");
        }
        return configuration;
    }

    private static void parseAccessPolicy(InputStream is,
                                          Configuration conf) throws ServletException {
        try {
            DocumentBuilder dbd = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dbd.parse(is);
            XPath path = XPathFactory.newInstance().newXPath();
            Node rootN = getNode(path, doc, "access-policy");
            if (null == rootN) {
                throw new ServletException("Invalid xml format, can't find <access-policy> root node!");
            }
            NodeList restrictedAccessLst = getList(path, rootN, "restricted-access/url-pattern");
            for (int i = 0; i < restrictedAccessLst.getLength(); i++) {
                Node c = restrictedAccessLst.item(i);
                conf.addRestrictedAccessPattern(c.getTextContent().trim());
            }
            NodeList gatewayLst = getList(path, rootN, "gateway/url-pattern");
            for (int i = 0; i < gatewayLst.getLength(); i++) {
                Node c = gatewayLst.item(i);
                conf.addGatewayPattern(c.getTextContent().trim());
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("Could not close is.", e);
                }
            }
        }
    }

    private static void parseWxAccessPolicy(InputStream is,
                                            Configuration conf) throws ServletException {
        try {
            DocumentBuilder dbd = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dbd.parse(is);
            XPath path = XPathFactory.newInstance().newXPath();
            Node rootN = getNode(path, doc, "wx-access-policy");
            if (null == rootN) {
                throw new ServletException("Invalid xml format, can't find <wx-access-policy> root node!");
            }
            NodeList supplierList = getList(path, rootN, "supplier-restricted-access/url-pattern");
            for (int i = 0; i < supplierList.getLength(); i++) {
                Node c = supplierList.item(i);
                conf.addSupplierPattern(c.getTextContent().trim());
            }
            NodeList customerList = getList(path, rootN, "customer-restricted-access/url-pattern");
            for (int i = 0; i < customerList.getLength(); i++) {
                Node c = customerList.item(i);
                conf.addCustomerPattern(c.getTextContent().trim());
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("Could not close is.", e);
                }
            }
        }
    }

    protected void addRestrictedAccessPattern(String pattern) {
        restrictedAccessPatterns.add(pattern);
    }

    protected void addGatewayPattern(String pattern) {
        gatewayPatterns.add(pattern);
    }

    protected void addSupplierPattern(String pattern) {
        supplierPatterns.add(pattern);
    }

    protected void addCustomerPattern(String pattern) {
        customerPatterns.add(pattern);
    }

    public static String getString(XPath path,
                                   Object node,
                                   String expression) throws XPathExpressionException {
        return (String) path.evaluate(expression, node, XPathConstants.STRING);
    }

    public static NodeList getList(XPath path,
                                   Object node,
                                   String expression) throws XPathExpressionException {
        return (NodeList) path.evaluate(expression, node, XPathConstants.NODESET);
    }

    public static Node getNode(XPath path,
                               Object node,
                               String expression) throws XPathExpressionException {
        return (Node) path.evaluate(expression, node, XPathConstants.NODE);
    }

    public static boolean matching(String uri,
                                   List<String> patterns) {
        if (patterns == null || patterns.isEmpty()) {
            return false;
        }
        for (String pattern : patterns) {
            if (pathMatcher.match(pattern, uri)) {
                return true;
            }
        }
        return false;
    }

    public AccessPolicy matchAccessPolicy(String uri) {
        AccessPolicy policy;
        if (matching(uri, this.restrictedAccessPatterns)) {
            policy = AccessPolicy.RESTRICTED;
        } else if (matching(uri, this.gatewayPatterns)) {
            policy = AccessPolicy.GATEWAY;
        } else {
            policy = AccessPolicy.PASS_THOUGH;
        }
        return policy;
    }

    public WxAccessPolicy matchWxAccessPolicy(String uri) {
        WxAccessPolicy policy;
        if (matching(uri, this.customerPatterns)) {
            policy = WxAccessPolicy.CUSTOMER;
        } else {
            policy = WxAccessPolicy.SUPPLIER;
        }
        return policy;
    }

    public enum AccessPolicy {
        RESTRICTED, PASS_THOUGH, GATEWAY
    }

    public enum WxAccessPolicy {
        SUPPLIER, CUSTOMER
    }

    public StorageAccessor getStorageAccessor() {
        return storageAccessor;
    }

    public void setStorageAccessor(StorageAccessor storageAccessor) {
        this.storageAccessor = storageAccessor;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    /**
     * 获取 authIdCookieName
     * 
     * @return authIdCookieName authIdCookieName
     */
    public String getAuthIdCookieName() {
        return authIdCookieName;
    }

    /**
     * 设置 authIdCookieName
     * 
     * @param authIdCookieName authIdCookieName
     */
    public void setAuthIdCookieName(String authIdCookieName) {
        this.authIdCookieName = authIdCookieName;
    }

}
