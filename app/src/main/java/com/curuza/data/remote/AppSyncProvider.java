package com.curuza.data.remote;

import android.content.Context;
import android.os.Build;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicCognitoUserPoolsAuthProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.curuza.utils.TLSSocketFactory;

import java.security.KeyStore;
import java.util.Arrays;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class AppSyncProvider {
  private static AWSAppSyncClient sAppSyncClient;
  private static final Object LOCK = new Object();

  // FIXME AppSync client initialization fails on API 19 because the default OKHttp library version
  // only supports API 21 and higher
  public static AWSAppSyncClient getAppSyncClient(Context context) {
    if (sAppSyncClient == null) {
      synchronized (LOCK) {
        AWSConfiguration awsConfig = new AWSConfiguration(context.getApplicationContext());
        BasicCognitoUserPoolsAuthProvider cognitoAuthProvider =
            new BasicCognitoUserPoolsAuthProvider(new CognitoUserPool(context.getApplicationContext(), awsConfig));

        AWSAppSyncClient.Builder appSyncClientBuilder = AWSAppSyncClient.builder()
            .context(context.getApplicationContext())
            .awsConfiguration(awsConfig)
            .defaultResponseFetcher(AppSyncResponseFetchers.NETWORK_ONLY)
            .cognitoUserPoolsAuthProvider(cognitoAuthProvider);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
          appSyncClientBuilder.okHttpClient(getPreLollipopOkHttpClient());
        }

        sAppSyncClient = appSyncClientBuilder.build();
      }
    }

    return sAppSyncClient;
  }

  private static OkHttpClient getPreLollipopOkHttpClient() {
    try {
      TrustManagerFactory tmf =
          TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init((KeyStore) null);

      TrustManager[] tms = tmf.getTrustManagers();
      if (tms.length != 1 || !(tms[0] instanceof X509TrustManager)) {
        throw new IllegalStateException("Unexpected default trust managers: " + Arrays.toString(tms));
      }

      return new OkHttpClient.Builder()
          .sslSocketFactory(new TLSSocketFactory(), (X509TrustManager) tms[0])
          .build();
    } catch (Exception e) {
      throw new SecurityException(e.getMessage());
    }
  }
}
