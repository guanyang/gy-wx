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

import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.PropertyDefiner;

public class ServerIPPropertyDefiner extends ContextAwareBase implements PropertyDefiner {

    @Override
    public String getPropertyValue() {
        try {
            String ip = getHostIp();
            char[] ips = ip.toCharArray();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < ips.length; i++) {
                if ('.' != ips[i]) {
                    buffer.append(ips[i]);
                }
            }
            return buffer.toString();
        } catch (Exception e) {
            addWarn("Can not find LocalServer IP");
        }
        return "NOT_WEBSPHERE_WEB_SERVER";
    }

    public String getHostIp() {
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

    private String collectionToDelimitedString(Collection<String> coll, String delim) {
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
}
