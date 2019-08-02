package oysd.com.trade_app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Net utils.
 * Created by Liam on 2018/7/20
 */
public final class NetUtils {

    private NetUtils() {
        // Prevent from being instantiating.
    }

    /**
     * Enum class of net state.
     */
    public enum NetState {
        NET_2G("2g"),
        NET_3G("3g"),
        NET_4G("4g"),
        NET_MOBILE("mobile"),
        NET_WIFI("wifi"),
        NET_UNKNOWN("unknown");

        private String name;

        NetState(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Gets current NetworkInfo.
     * return a NetworkInfo or null if no default network is currently active.
     */
    private static NetworkInfo getNetworkInfo(@NonNull Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
    }

    /**
     * Indicates whether it's net connected current now.
     */
    public static boolean isConnected(@NonNull Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Indicates whether it's wifi connected current now.
     */
    public static boolean isWifiConnected(@NonNull Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Indicates whether it's mobile connected current now.
     */
    public static boolean isMobileConnected(@NonNull Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * Returns the mac address, or an empty string if WifiManager is null.
     */
    public static String getMacAddress(@NonNull Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager == null ? null : wifiManager.getConnectionInfo();

        if (wifiInfo == null || wifiInfo.getMacAddress() == null) {
            return null;
        }

        String macAddress = wifiInfo.getMacAddress().trim();
        return "02:00:00:00:00:00".equals(macAddress) ? null : macAddress;
    }

    /**
     * Returns the ip address, or an empty string if any exception occurred.
     */
    public static String getIPAddress(@NonNull Context context) {
        String ip = "";
        NetState netState = getNetType(context);
        switch (netState) {
            case NET_MOBILE:
                try {
                    for (Enumeration<NetworkInterface> enNetIntf = NetworkInterface.getNetworkInterfaces();
                         enNetIntf.hasMoreElements(); ) {
                        NetworkInterface netIntf = enNetIntf.nextElement();
                        for (Enumeration<InetAddress> enInetAddr = netIntf.getInetAddresses();
                             enInetAddr.hasMoreElements(); ) {
                            InetAddress inetAddr = enInetAddr.nextElement();
                            if (!inetAddr.isLoopbackAddress()) {
                                ip = inetAddr.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    Logger.e(e.toString());
                }
                break;
            case NET_WIFI:
                WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                        .getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                // converts int ip address to string.
                ip = (ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "." +
                        (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff);
                break;
            default:
                break;
        }

        return ip;
    }

    /**
     * Returns the current net type.
     */
    public static NetState getNetType(@NonNull Context context) {
        NetState netState = NetState.NET_UNKNOWN;
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo == null) {
            return netState;
        }

        if (networkInfo.isConnectedOrConnecting()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_MOBILE) {
                netState = NetState.NET_MOBILE;
            } else if (type == ConnectivityManager.TYPE_WIFI) {
                netState = NetState.NET_WIFI;
            }
        }
        return netState;
    }

    /**
     * Returns the detail net type.
     */
    public static NetState getDetailNetType(@NonNull Context context) {
        NetState netState = NetState.NET_UNKNOWN;
        NetworkInfo networkInfo = getNetworkInfo(context);

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            int type = networkInfo.getType();
            switch (type) {
                case ConnectivityManager.TYPE_WIFI:
                    netState = NetState.NET_WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    TelephonyManager tm =
                            (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    int netType = tm == null ? 0 : tm.getNetworkType();

                    switch (netType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            netState = NetState.NET_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            netState = NetState.NET_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            netState = NetState.NET_4G;
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        return netState;
    }

    /**
     * Gets detail mobile net type.
     *
     * @return Detail mobile net type.(2G/3G/4G/Unknown)
     */
    @Deprecated
    public static String getMobileNetType(@NonNull Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int netType = tm == null ? 0 : tm.getNetworkType();
        switch (netType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "Unknown";
        }
    }

    /**
     * 设备是否使用 代理 连接网络。
     *
     * @return {@code true} 表示通过代理连接；otherwise {@code false}.
     */
    public static boolean isWifiProxy(@NonNull Context context) {
        final boolean IS_ICS_OR_LATER =
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;

        String proxyAddress;
        int proxyPort;

        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));

        } else {
            proxyAddress = android.net.Proxy.getHost(context);
            proxyPort = android.net.Proxy.getPort(context);
        }

        return (EmptyUtils.isNotEmpty(proxyAddress)) && (proxyPort != -1);

    }

}
