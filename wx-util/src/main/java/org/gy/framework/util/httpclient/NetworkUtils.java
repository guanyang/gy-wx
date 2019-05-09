package org.gy.framework.util.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网络相关工具类
 * 
 */
public class NetworkUtils {
    /** 记录日志的变量 */
    private static Logger       logger  = LoggerFactory.getLogger(NetworkUtils.class);

    /** 未知 */
    private static final String UNKNOWN = "unknown";

    /**
     * 取得客户端IP地址 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串Ｉｐ值，究竟哪个才是真正的用户端的真实IP呢？ <br>
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。</br> <br>
     * 如：X-Forwarded-For：192.168.1.110， 192.168.1.120， 192.168.1.130， 192.168.1.100用户真实IP为： 192.168.1.110</br>
     * 
     * @param request
     * @return
     */
    public synchronized static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (checkIp(ip) == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (checkIp(ip) == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (checkIp(ip) == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (checkIp(ip) == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (checkIp(ip) == null) {
            ip = request.getRemoteAddr();
        }
        if (null != ip && ip.indexOf(',') != -1) {
            // 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串 IP 值
            // 取X-Forwarded-For中第一个非unknown的有效IP字符串
            // 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
            // 192.168.1.100
            // 用户真实IP为： 192.168.1.110
            // 注意:当访问地址为 localhost 时 地址格式为 0:0:0:0:0:0:1
            logger.debug("ip=" + ip);
            String[] ips = ip.split(",");
            for (int i = 0; i < ips.length; i++) {
                if (null != ips[i] && !UNKNOWN.equalsIgnoreCase(ips[i])) {
                    ip = ips[i];
                    break;
                }
            }
            if ("0:0:0:0:0:0:1".equals(ip)) {
                logger.warn("由于客户端访问地址使用 localhost，获取客户端真实IP地址错误，请使用IP方式访问");
            }
        }

        if (UNKNOWN.equalsIgnoreCase(ip) || ip == null) {
            logger.warn("由于客户端通过Squid反向代理软件访问，获取客户端真实IP地址错误，" + "请更改squid.conf配置文件forwarded_for项默认是为on解决");
            ip = "192.168.0.1";
        }

        return ip;
    }

    /**
     * 取得当前机器的IP地址
     * 
     * @return IP地址
     */
    public synchronized static final String getLocalIpAddr() {
        InetAddress inet = null;
        try {
            inet = InetAddress.getLocalHost();
        } catch (UnknownHostException ue) {
            logger.error(ue.getMessage(), ue);
        }
        if (inet != null) {
            return inet.getHostAddress();
        }
        return null;
    }

    /**
     * 取得本地域名
     * 
     * @return 域名字符串
     */
    public static String getLocalHostName() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            return addr.getHostName();
        } catch (UnknownHostException ue) {
            logger.error(ue.getMessage(), ue);
        }
        return null;
    }

    /**
     * 检测IP是否为空
     * 
     * @param ip IP地址
     * @return IP地址
     */
    private static String checkIp(String ip) {
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            return null;
        }
        return ip;
    }

    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
     */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取.如果有特殊系统请继续扩充新的取mac地址方法.
     * 
     * @return mac地址
     */
    public static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inReader = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("ifconfig eth0");// linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
            inReader = new InputStreamReader(process.getInputStream());
            bufferedReader = new BufferedReader(inReader);
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("hwaddr");// 寻找标示字符串[hwaddr]
                if (index >= 0) {// 找到了
                    mac = line.substring(index + "hwaddr".length() + 1).trim();// 取出mac地址并去除2边空格
                    break;
                }
            }
        } catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
        } finally {
            try {
                if (inReader != null) {
                    inReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ioe) {
                logger.error(ioe.getMessage(), ioe);
            }
            inReader = null;
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    /**
     * 获取widnows网卡的mac地址.
     * 
     * @return mac地址
     */
    public static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inReader = null;
        Process process = null;
        try {
            String command = "cmd.exe /c ipconfig /all";
            process = Runtime.getRuntime().exec(command);// windows下的命令，显示信息中包含有mac地址信息
            inReader = new InputStreamReader(process.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("physical address");// 寻找标示字符串[physical address]
                if (index >= 0) {// 找到了
                    index = line.indexOf(":");// 寻找":"的位置
                    if (index >= 0) {
                        mac = line.substring(index + 1).trim();// 取出mac地址并去除2边空格
                    }
                    break;
                }
            }
        } catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
        } finally {
            try {
                if (inReader != null) {
                    inReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ioe) {
                logger.error(ioe.getMessage(), ioe);
            }
            inReader = null;
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    /**
     * 功能描述: Windows7 mac地址<br>
     */
    private static String getWin7MacAddress() {
        StringBuffer sb = new StringBuffer();
        // 获取本地IP对象
        InetAddress ia;
        try {
            ia = InetAddress.getLocalHost();
            // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
        } catch (UnknownHostException ue) {
            logger.error(ue.getMessage(), ue);
        } catch (SocketException se) {
            logger.error(se.getMessage(), se);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }

    /**
     * 功能描述: 获取网卡的MAC地址<br>
     * 〈功能详细描述〉根据操作系统进行判断
     * 
     * @return MAC地址
     */
    public static String getMacAddress() {
        String os = getOSName();
        String mac = "";
        if (os.startsWith("windows 7") || os.startsWith("windows vista")) {
            mac = getWin7MacAddress();
        } else if (os.startsWith("windows")) {
            mac = getWindowsMACAddress();
        } else {
            mac = getUnixMACAddress();
        }
        return (mac == null || mac.length() == 0) ? "00-00-00-00-00-01" : mac;
    }

}
