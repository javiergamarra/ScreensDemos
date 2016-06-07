package com.liferay.mobile.android.http;

import com.liferay.mobile.android.http.client.OkHttpClientImpl;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author Javier Gamarra
 */
public class UnsafeOkHttpClient {

    private static SSLContext getSslContext() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // DON'T DO THIS IN PRODUCTION
    // *NEVER*
    public static void changeOkHttpClientToUNSAFE() {
        HttpUtil.client = new OkHttpClientImpl() {
            protected OkHttpClient getClient(int connectionTimeout) {
                OkHttpClient clone = super.getClient(connectionTimeout);
                clone.setSslSocketFactory(getSslContext().getSocketFactory());
                clone.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                return clone;
            }

        };
    }

    public static OkHttpClient getUnsafeClient() {
        OkHttpClient client = new OkHttpClient();
        client.setCache(new Cache(LiferayScreensContext.getContext().getCacheDir(), 100000000));
        client.setSslSocketFactory(getSslContext().getSocketFactory());
        client.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return client;
    }
}
