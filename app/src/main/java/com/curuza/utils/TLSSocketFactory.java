package com.curuza.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TLSSocketFactory extends SSLSocketFactory {

  private SSLSocketFactory mSSLSocketFactory;

  public TLSSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, null, null);
    mSSLSocketFactory = sslContext.getSocketFactory();
  }

  @Override
  public String[] getDefaultCipherSuites() {
    return mSSLSocketFactory.getDefaultCipherSuites();
  }

  @Override
  public String[] getSupportedCipherSuites() {
    return mSSLSocketFactory.getSupportedCipherSuites();
  }

  @Override
  public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
    return enableTLSOnSocket(mSSLSocketFactory.createSocket(s, host, port, autoClose));
  }

  @Override
  public Socket createSocket(String host, int port) throws IOException {
    return enableTLSOnSocket(mSSLSocketFactory.createSocket(host, port));
  }

  @Override
  public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
    return enableTLSOnSocket(mSSLSocketFactory.createSocket(host, port, localHost, localPort));
  }

  @Override
  public Socket createSocket(InetAddress host, int port) throws IOException {
    return enableTLSOnSocket(mSSLSocketFactory.createSocket(host, port));
  }

  @Override
  public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
    return enableTLSOnSocket(mSSLSocketFactory.createSocket(address, port, localAddress, localPort));
  }

  private Socket enableTLSOnSocket(Socket socket) {
    if (socket instanceof SSLSocket) {
      ((SSLSocket) socket).setEnabledProtocols(new String[]{"TLSv1.1", "TLSv1.2"});
    }

    return socket;
  }
}
