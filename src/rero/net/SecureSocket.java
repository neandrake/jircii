package rero.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SecureSocket {
	protected SSLSocket socket;

	public SecureSocket(String host, int port) throws Exception {
		socket = null;

		DummySSLSocketFactory factory = new DummySSLSocketFactory();
		socket = (SSLSocket) factory.createSocket(host, port);
		socket.startHandshake();
	}

	public Socket getSocket() {
		return socket;
	}

	private static class DummySSLSocketFactory extends SSLSocketFactory {
		private SSLSocketFactory factory;

		public DummySSLSocketFactory() {
			try {
				SSLContext sslcontext = SSLContext.getInstance("SSL");
				sslcontext.init(null, new TrustManager[] { new DummyTrustManager() }, new java.security.SecureRandom());
				factory = sslcontext.getSocketFactory();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public static SocketFactory getDefault() {
			return new DummySSLSocketFactory();
		}

		@Override
		public Socket createSocket(Socket socket, String s, int i, boolean flag) throws IOException {
			return factory.createSocket(socket, s, i, flag);
		}

		@Override
		public Socket createSocket(InetAddress inaddr, int i, InetAddress inaddr1, int j) throws IOException {
			return factory.createSocket(inaddr, i, inaddr1, j);
		}

		@Override
		public Socket createSocket(InetAddress inaddr, int i) throws IOException {
			return factory.createSocket(inaddr, i);
		}

		@Override
		public Socket createSocket(String s, int i, InetAddress inaddr, int j) throws IOException {
			return factory.createSocket(s, i, inaddr, j);
		}

		@Override
		public Socket createSocket(String s, int i) throws IOException {
			return factory.createSocket(s, i);
		}

		@Override
		public String[] getDefaultCipherSuites() {
			return factory.getSupportedCipherSuites();
		}

		@Override
		public String[] getSupportedCipherSuites() {
			return factory.getSupportedCipherSuites();
		}
	}

	private static class DummyTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate ax509certificate[], String authType) {
			return;
		}

		@Override
		public void checkServerTrusted(X509Certificate ax509certificate[], String authType) {
			return;
		}

		public boolean isClientTrusted(X509Certificate[] cert) {
			return true;
		}

		public boolean isServerTrusted(X509Certificate[] cert) {
			return true;
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}
}
