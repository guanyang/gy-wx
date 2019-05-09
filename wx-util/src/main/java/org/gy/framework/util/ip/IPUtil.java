package org.gy.framework.util.ip;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统信息工具类
 * 
 * @author piaohailin
 * @date 2013-5-6
 */
public final class IPUtil {

    private static Logger logger = LoggerFactory.getLogger(IPUtil.class);

    private IPUtil() {
    }

    /**
     * 取到当前机器的IP地址
     * 
     * @return
     * @author piaohailin
     * @date 2013-5-6
     */
    public static String getIp() {
        String hostIp = null;
        if (hostIp == null) {
            List<String> ips = new ArrayList<String>();
            Enumeration<NetworkInterface> netInterfaces = null;
            try {
                netInterfaces = NetworkInterface.getNetworkInterfaces();
                while (netInterfaces.hasMoreElements()) {
                    NetworkInterface netInterface = netInterfaces.nextElement();
                    Enumeration<InetAddress> inteAddresses = netInterface.getInetAddresses();
                    while (inteAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inteAddresses.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                            ips.add(inetAddress.getHostAddress());
                        }
                    }
                }
            } catch (SocketException ex) {
                ex.printStackTrace();
            }
            hostIp = collectionToDelimitedString(ips, ",");
        }
        return hostIp;
    }

    private static String collectionToDelimitedString(Collection<String> coll,
                                                      String delim) {
        if (coll == null || coll.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = coll.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    public static String getHostName() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return hostName;
    }
}
