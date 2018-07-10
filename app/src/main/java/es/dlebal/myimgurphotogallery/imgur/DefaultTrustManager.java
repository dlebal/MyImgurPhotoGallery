package es.dlebal.myimgurphotogallery.imgur;

import android.util.Log;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Class class DefaultTrustManager implements X509TrustManager
 *
 * This class is used to instantiate DefaultTrustManager objects
 */
class DefaultTrustManager implements X509TrustManager {

    /**
     * Constants
     */
    private static final String DEFAULT_TRUST_MANAGER_CLASS_NAME = DefaultTrustManager.class.getSimpleName(); // Class name

    /**
     * Method public void checkClientTrusted(X509Certificate[] chain, String authType)
     *
     * Performs the following tasks:
     *   - Give the partial or complete certificate chain provided by the peer
     *
     * Parameters:
     *   @param chain: The peer certificate chain
     *   @param authType: The authentication type based on the client certificate
     */
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {

        // Constants
        final String METHOD_NAME = "checkClientTrusted"; // Method name

        // Give the partial or complete certificate chain provided by the peer
        Log.d(DEFAULT_TRUST_MANAGER_CLASS_NAME + ": " + METHOD_NAME, "Give the partial or complete certificate chain provided by the peer");

    }

    /**
     * Method public void checkServerTrusted(X509Certificate[] chain, String authType)
     *
     * Performs the following tasks:
     *   - Give the partial or complete certificate chain provided by the peer
     *
     * Parameters:
     *   @param chain: The peer certificate chain
     *   @param authType: The key exchange algorithm used
     */
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {

        // Constants
        final String METHOD_NAME = "checkServerTrusted"; // Method name

        // Give the partial or complete certificate chain provided by the peer
        Log.d(DEFAULT_TRUST_MANAGER_CLASS_NAME + ": " + METHOD_NAME, "Give the partial or complete certificate chain provided by the peer");

    }

    /**
     * Method public X509Certificate[] getAcceptedIssuers()
     *
     * Performs the following tasks:
     *   - Return an array of certificate authority certificates which are trusted for authenticating peers
     *
     * Parameters:
     *   @return null: A non-null (possibly empty) array of acceptable CA issuer certificates
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {

        // Constants
        final String METHOD_NAME = "getAcceptedIssuers"; // Method name

        // Return an array of certificate authority certificates which are trusted for authenticating peers
        Log.d(DEFAULT_TRUST_MANAGER_CLASS_NAME + ": " + METHOD_NAME, "Return an array of certificate authority certificates which are trusted for authenticating peers");
        return null;

    }

}
