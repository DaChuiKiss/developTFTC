package oysd.com.trade_app.http;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Https .
 * Created by Liam on 2018/10/22
 */
public class TrustManager {

    /**
     * @return a trust manager that does not validate certificate chains.
     */
    public static X509TrustManager getX509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static SSLSocketFactory getUnsafeSSLSocketFactory() {
        try {
            // Installs the all-trusting trust manager.
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new X509TrustManager[]{getX509TrustManager()}, new SecureRandom());

            // Creates an ssl socket factory with our all-trusting manager.
            // final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return sslContext.getSocketFactory();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 信任所以的 Hostname .
     */
    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}
