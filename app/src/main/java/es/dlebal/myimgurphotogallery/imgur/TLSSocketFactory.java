package es.dlebal.myimgurphotogallery.imgur;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;

/**
 * Class class TLSSocketFactory extends javax.net.ssl.SSLSocketFactory
 *
 * This class is used to instantiate TLSSocketFactory objects
 */
class TLSSocketFactory extends javax.net.ssl.SSLSocketFactory {

    /**
     * Constants
     */
    private static final String[] PROTOCOLS = new String[] {
            "TLSv1",
            "TLSv1.1",
            "TLSv1.2"
    }; // Protocols
    private static final String ENABLED_CIPHERS[] = {
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_DHE_RSA_WITH_AES_128_CBC_SHA",
            "TLS_DHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_RC4_128_SHA",
            "TLS_ECDHE_ECDSA_WITH_RC4_128_SHA",
            "TLS_RSA_WITH_AES_128_CBC_SHA",
            "TLS_RSA_WITH_AES_256_CBC_SHA",
            "SSL_RSA_WITH_3DES_EDE_CBC_SHA",
            "SSL_RSA_WITH_RC4_128_SHA",
            "SSL_RSA_WITH_RC4_128_MD5"
    }; // Enabled ciphers
    private static final String TLS_SOCKET_FACTORY_CLASS_NAME = TLSSocketFactory.class.getSimpleName(); // Class name

    /**
     * Variables
     */
    private javax.net.ssl.SSLSocketFactory _sslSocketFactory; // SSLSocketFactory

    /**
     * Constructor TLSSocketFactory() throws KeyManagementException, NoSuchAlgorithmException
     *
     * Performs the following tasks:
     *   - Initialize the instances of the class TLSSocketFactory
     */
    TLSSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {

        // Constants
        final String METHOD_NAME = "TLSSocketFactory"; // Method name

        // Initialize the instances of the class TLSSocketFactory
        Log.d(TLS_SOCKET_FACTORY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the instances of the class TLSSocketFactory");
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
        this._sslSocketFactory = sslContext.getSocketFactory();

    }

    /**
     * Method public String[] getDefaultCipherSuites()
     *
     * Performs the following tasks:
     *   - Returns the names of the cipher suites which could be enabled for use on an SSL connection created by this factory
     *
     * Parameters:
     *   @return String[]: An array of cipher suite names
     */
    @Override
    public String[] getDefaultCipherSuites() {

        // Constants
        final String METHOD_NAME = "getDefaultCipherSuites"; // Method name

        // Returns the names of the cipher suites which could be enabled for use on an SSL connection created by this factory
        Log.d(TLS_SOCKET_FACTORY_CLASS_NAME + ": " + METHOD_NAME, "Returns the names of the cipher suites which could be enabled for use on an SSL connection created by this factory");
        return this._sslSocketFactory.getDefaultCipherSuites();

    }

    /**
     * Method public String[] getSupportedCipherSuites()
     *
     * Performs the following tasks:
     *   - Returns the names of the cipher suites which could be enabled for use on an SSL connection created by this factory
     *
     * Parameters:
     *   @return String[]: An array of cipher suite names
     */
    @Override
    public String[] getSupportedCipherSuites() {

        // Constants
        final String METHOD_NAME = "getSupportedCipherSuites"; // Method name

        // Returns the names of the cipher suites which could be enabled for use on an SSL connection created by this factory
        Log.d(TLS_SOCKET_FACTORY_CLASS_NAME + ": " + METHOD_NAME, "Returns the names of the cipher suites which could be enabled for use on an SSL connection created by this factory");
        return this._sslSocketFactory.getSupportedCipherSuites();

    }

    /**
     * Method public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException
     *
     * Performs the following tasks:
     *   - Returns a socket layered over an existing socket connected to the named host, at the given port
     *
     * Parameters:
     *   @param s: The existing socket
     *   @param host: The server host
     *   @param port: The server port
     *   @param autoClose: Close the underlying socket when this socket is closed
     *   @return Socket: A socket connected to the specified host and port
     */
    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {

        // Constants
        final String METHOD_NAME = "createSocket"; // Method name

        // Returns a socket layered over an existing socket connected to the named host, at the given port
        Log.d(TLS_SOCKET_FACTORY_CLASS_NAME + ": " + METHOD_NAME, "Returns a socket layered over an existing socket connected to the named host, at the given port");
        return this.enableTLSAndCiphers(this._sslSocketFactory.createSocket(s, host, port, autoClose));

    }

    /**
     * Method public Socket createSocket(String host, int port) throws IOException
     *
     * Performs the following tasks:
     *   - Returns a socket layered over an existing socket connected to the named host, at the given port
     *
     * Parameters:
     *   @param host: The server host name with which to connect, or null for the loopback address
     *   @param port: The server port
     *   @return Socket: A socket connected to the specified host and port
     */
    @Override
    public Socket createSocket(String host, int port) throws IOException {

        // Constants
        final String METHOD_NAME = "createSocket"; // Method name

        // Returns a socket layered over an existing socket connected to the named host, at the given port
        Log.d(TLS_SOCKET_FACTORY_CLASS_NAME + ": " + METHOD_NAME, "Returns a socket layered over an existing socket connected to the named host, at the given port");
        return this.enableTLSAndCiphers(this._sslSocketFactory.createSocket(host, port));

    }

    /**
     * Method public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException
     *
     * Performs the following tasks:
     *   - Returns a socket layered over an existing socket connected to the named host, at the given port
     *
     * Parameters:
     *   @param host: The server host name with which to connect, or null for the loopback address
     *   @param port: The server port
     *   @param localHost: The local address the socket is bound to
     *   @param localPort: The local port the socket is bound to
     *   @return Socket: A socket connected to the specified host and port
     */
    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {

        // Constants
        final String METHOD_NAME = "createSocket"; // Method name

        // Returns a socket layered over an existing socket connected to the named host, at the given port
        Log.d(TLS_SOCKET_FACTORY_CLASS_NAME + ": " + METHOD_NAME, "Returns a socket layered over an existing socket connected to the named host, at the given port");
        return this.enableTLSAndCiphers(this._sslSocketFactory.createSocket(host, port, localHost, localPort));

    }

    /**
     * Method public Socket createSocket(InetAddress host, int port) throws IOException
     *
     * Performs the following tasks:
     *   - Returns a socket layered over an existing socket connected to the named host, at the given port
     *
     * Parameters:
     *   @param host: The server host
     *   @param port: The server port
     *   @return Socket: A socket connected to the specified host and port
     */
    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {

        // Constants
        final String METHOD_NAME = "createSocket"; // Method name

        // Returns a socket layered over an existing socket connected to the named host, at the given port
        Log.d(TLS_SOCKET_FACTORY_CLASS_NAME + ": " + METHOD_NAME, "Returns a socket layered over an existing socket connected to the named host, at the given port");
        return this.enableTLSAndCiphers(this._sslSocketFactory.createSocket(host, port));

    }

    /**
     * Method public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException
     *
     * Performs the following tasks:
     *   - Returns a socket layered over an existing socket connected to the named host, at the given port
     *
     * Parameters:
     *   @param address: The server network address
     *   @param port: The server port
     *   @param localAddress: The client network address
     *   @param localPort: The client port
     *   @return Socket: A socket connected to the specified host and port
     */
    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {

        // Constants
        final String METHOD_NAME = "createSocket"; // Method name

        // Returns a socket layered over an existing socket connected to the named host, at the given port
        Log.d(TLS_SOCKET_FACTORY_CLASS_NAME + ": " + METHOD_NAME, "Returns a socket layered over an existing socket connected to the named host, at the given port");
        return this.enableTLSAndCiphers(this._sslSocketFactory.createSocket(address, port, localAddress, localPort));

    }

    /**
     * Method private Socket enableTLSAndCiphers(Socket socket)
     *
     * Performs the following tasks:
     *   - Returns a socket with TLS and ciphers enabled
     *
     * Parameters:
     *   @param socket: Socket
     *   @return socket: Socket
     */
    private Socket enableTLSAndCiphers(Socket socket) {

        // Constants
        final String METHOD_NAME = "enableTLSAndCiphers"; // Method name

        // Returns a socket with TLS and ciphers enabled
        Log.d(TLS_SOCKET_FACTORY_CLASS_NAME + ": " + METHOD_NAME, "Returns a socket with TLS and ciphers enabled");
        if(socket != null && (socket instanceof SSLSocket)) {
            try {
                ((SSLSocket) socket).setEnabledProtocols(PROTOCOLS);
                ((SSLSocket) socket).setEnabledCipherSuites(ENABLED_CIPHERS);
            } catch (Exception e) {
                Log.e(TLS_SOCKET_FACTORY_CLASS_NAME + ": " + METHOD_NAME, "Exception: " + e.toString());
            }
        }
        return socket;

    }

}
