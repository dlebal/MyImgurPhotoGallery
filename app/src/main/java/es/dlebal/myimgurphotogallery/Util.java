package es.dlebal.myimgurphotogallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.webkit.CookieManager;

import java.io.ByteArrayOutputStream;

/**
 * Class public class Util
 *
 * This class is used to instantiate Util objects
 */
public class Util {

    /**
     * Constants
     */
    private static final String UTIL_CLASS_NAME = Util.class.getSimpleName(); // Class name

    /**
     * Method public static String convertIntColorToHexColor(Context context, int intColor)
     *
     * Performs the following tasks:
     *   - Return the result of converting an integer color to a hexadecimal color
     *
     * Parameters:
     *   @param context: Context
     *   @param intColor: Integer color
     *   @return String: Hexadecimal color
     */
    public static String convertIntColorToHexColor(Context context, int intColor) {

        // Constants
        final String METHOD_NAME = "convertIntColorToHexColor"; // Method name

        // Return the result of converting an int color to a hexadecimal color
        Log.d(UTIL_CLASS_NAME + ": " + METHOD_NAME, "Return the result of converting an int color to a hexadecimal color");
        return "#" + Integer.toHexString(ContextCompat.getColor(context, intColor) & 0x00ffffff);

    }

    /**
     * Method public static String getBase64Image(Bitmap bitmap)
     *
     * Performs the following tasks:
     *   - Return the base 64 image
     *
     * Parameters:
     *   @param image: Image
     *   @return String: Base 64 image
     */
    public static String getBase64Image(Bitmap image) {

        // Constants
        final String METHOD_NAME = "getBase64Image"; // Method name

        // Return the base 64 image
        Log.d(UTIL_CLASS_NAME + ": " + METHOD_NAME, "Return the base 64 image");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }

    /**
     * Method public static boolean isNetworkAvailable(Context context)
     *
     * Performs the following tasks:
     *   - Check if the network is available
     *   - Return if the network is available
     *
     * Parameters:
     *   @param context: Context
     *   @return boolean: Is the network available?
     */
    public static boolean isNetworkAvailable(Context context) {

        // Constants
        final String METHOD_NAME = "isNetworkAvailable"; // Method name

        // Variables
        boolean isNetworkAvailable = false; // Is the network available?

        // Check if the network is available
        Log.d(UTIL_CLASS_NAME + ": " + METHOD_NAME, "Check if the network is available");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            isNetworkAvailable = (networkInfo != null && networkInfo.isConnected());
        }

        // Return if the network is available
        Log.d(UTIL_CLASS_NAME + ": " + METHOD_NAME, "Return if the network is available. Is the network available? -> " + String.valueOf(isNetworkAvailable));
        return isNetworkAvailable;

    }

    /**
     * Method public static void login(Context context, String accessToken, String refreshToken, long expiresIn)
     *
     * Performs the following tasks:
     *   - Do the user login
     *
     * Parameters:
     *   @param context: Context
     *   @param accessToken: Access token returned from the Imgur authorization
     *   @param refreshToken: Refresh token returned from the Imgur authorization
     *   @param expiresIn: Lifetime of the token in seconds
     */
    public static void login(Context context, String accessToken, String refreshToken, long expiresIn) {

        // Constants
        final String METHOD_NAME = "login"; // Method name

        // Variables
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context); // SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit(); // Editor

        // Do the user login
        Log.d(UTIL_CLASS_NAME + ": " + METHOD_NAME, "Do the user login");
        editor.putString(context.getString(R.string.imgur_access_token_key), accessToken);
        editor.putString(context.getString(R.string.imgur_refresh_token_key), refreshToken);
        editor.putLong(context.getString(R.string.imgur_expires_in_key), expiresIn);
        editor.apply();

    }

    /**
     * Method public static void logout(Context context)
     *
     * Performs the following tasks:
     *   - Do the user logout
     *
     * Parameters:
     *   @param context: Context
     */
    public static void logout(Context context) {

        // Constants
        final String METHOD_NAME = "logout"; // Method name

        // Do the user logout
        Log.d(UTIL_CLASS_NAME + ": " + METHOD_NAME, "Do the user logout");
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);
        cookieManager.flush();
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();

    }

}
