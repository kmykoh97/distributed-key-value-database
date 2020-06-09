package sjtu.zookeeper.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ZKServersUtil {

    public static final String ELECTION_MASTER = "/election/master";
    public static final String ELECTION_NODE = "/election";
    public static final String ELECTION_NODE_2 = "/election2";
    public static final String LIVE_NODES = "/livenodes";
    public static final String ALL_NODES = "/allnodes";

    private static String ipPort = null;

    public static String getHostPostOfServer() {
        if (ipPort != null) {
            return ipPort;
        }

        String ip;

        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("failed to fetch Ip!", e);
        }

        int port = Integer.parseInt(System.getProperty("server.port"));
        ipPort = ip.concat(":").concat(String.valueOf(port));

        return ipPort;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    private ZKServersUtil() {}

}
