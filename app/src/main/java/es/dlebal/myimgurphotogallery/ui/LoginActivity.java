package es.dlebal.myimgurphotogallery.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.regex.Matcher;

import es.dlebal.myimgurphotogallery.Config;
import es.dlebal.myimgurphotogallery.R;
import es.dlebal.myimgurphotogallery.Util;

/**
 * Class public class LoginActivity extends AppCompatActivity
 *
 * This class is used to instantiate LoginActivity objects
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Constants
     */
    private static final String LOGIN_ACTIVITY_CLASS_NAME = LoginActivity.class.getSimpleName(); // Class name

    /**
     * Variables
     */
    protected RelativeLayout _rlWrapper; // Wrapper
    protected ProgressBar _pbProgress; // Progress
    protected WebView _wvWeb; // Web

    /**
     * Method protected void onCreate(Bundle savedInstanceState)
     *
     * Performs the following tasks:
     *   - Initialize the activity
     *
     * Parameters:
     *   @param savedInstanceState: If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Constants
        final String METHOD_NAME = "onCreate"; // Method name

        // Initialize the activity
        Log.d(LOGIN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the activity");
        setContentView(R.layout.login_activity);
        this.initializeUI();

    }

    /**
     * Method private void initializeUI()
     *
     * Performs the following tasks:
     *   - Initialize the wrapper
     *   - Initialize the progress
     *   - Initialize the web
     *   - Load the web
     */
    private void initializeUI() {

        // Constants
        final String METHOD_NAME = "initializeUI"; // Method name

        // Initialize the wrapper
        Log.d(LOGIN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the wrapper");
        this._rlWrapper = findViewById(R.id.login_activity_rlWrapper);

        // Initialize the progress
        Log.d(LOGIN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the progress");
        this._pbProgress = findViewById(R.id.login_activity_pbProgress);
        this._pbProgress.setVisibility(View.VISIBLE);

        // Initialize the web
        Log.d(LOGIN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the web");
        this._wvWeb = findViewById(R.id.login_activity_wvWeb);
        final WebSettings webSettings = this._wvWeb.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        this._wvWeb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        this._wvWeb.setWebViewClient(new WebViewClient() {

            /**
             * Method public void onPageStarted(WebView view, String url, Bitmap favicon)
             *
             * Performs the following tasks:
             *   - Notify the host application that a page has started loading
             *
             * Parameters:
             *   @param view: The WebView that is initiating the callback
             *   @param url: The url to be loaded
             *   @param favicon: The favicon for this page if it already exists in the database
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                // Constants
                final String SUBMETHOD_NAME = "onPageStarted"; // Method name

                // Notify the host application that a page has started loading
                Log.d(LOGIN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Notify the host application that a page has started loading");

            }

            /**
             * Method public void onPageFinished(WebView view, String url)
             *
             * Performs the following tasks:
             *   - Notify the host application that a page has finished loading
             *
             * Parameters:
             *   @param view: The WebView that is initiating the callback
             *   @param url: The url of the page
             */
            @Override
            public void onPageFinished(WebView view, String url) {

                // Constants
                final String SUBMETHOD_NAME = "onPageFinished"; // Method name

                // Notify the host application that a page has started loading
                Log.d(LOGIN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Notify the host application that a page has started loading");
                LoginActivity.this._pbProgress.setVisibility(View.INVISIBLE);

            }

            /**
             * Method public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse)
             *
             * Performs the following tasks:
             *   - Notify the host application that an HTTP error has been received from the server while loading a resource
             *
             * Parameters:
             *   @param view: The WebView that is initiating the callback
             *   @param request: The originating request
             *   @param errorResponse: Information about the error occured
             */
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {

                // Constants
                final String SUBMETHOD_NAME = "onReceivedHttpError"; // Method name

                // Notify the host application that an HTTP error has been received from the server while loading a resource
                Log.d(LOGIN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Notify the host application that an HTTP error has been received from the server while loading a resource. Error response: " + String.valueOf(errorResponse.getStatusCode()));
                LoginActivity.this._pbProgress.setVisibility(View.INVISIBLE);
                Snackbar.make(LoginActivity.this._rlWrapper, Html.fromHtml("<font color=\"" + Util.convertIntColorToHexColor(LoginActivity.this, R.color.colorError) + "\">" + getString(R.string.message_error_login) + "</font>"), Snackbar.LENGTH_SHORT).show();

            }

            /**
             * Method public boolean shouldOverrideUrlLoading(WebView view, String url)
             *
             * Performs the following tasks:
             *   - Intercept the tokens in the redirect url
             *
             * Parameters:
             *   @param view: The WebView that is initiating the callback
             *   @param url: The url to be loaded
             *   @return leaveCurrentWebView: true if the host application wants to leave the current WebView and handle the url itself, otherwise return false
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // Constants
                final String SUBMETHOD_NAME = "shouldOverrideUrlLoading"; // Method name

                // Variables
                boolean leaveCurrentWebView = false; // Leave the current WebView?
                Matcher matcher; // Matcher

                // Intercept the tokens in the redirect url
                Log.d(LOGIN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Intercept the tokens in the redirect url");
                if (url.startsWith(Config.IMGUR_REDIRECT_URL)) {
                    leaveCurrentWebView = true;
                    matcher = Config.IMGUR_PATTERN_ACCESS_TOKEN.matcher(url);
                    matcher.find();
                    String accessToken = matcher.group(1);
                    matcher = Config.IMGUR_PATTERN_REFRESH_TOKEN.matcher(url);
                    matcher.find();
                    String refreshToken = matcher.group(1);
                    matcher = Config.IMGUR_PATTERN_EXPIRES_IN.matcher(url);
                    matcher.find();
                    long expiresIn = Long.valueOf(matcher.group(1));
                    Util.login(getApplicationContext(), accessToken, refreshToken, expiresIn);
                    startActivity(new Intent(LoginActivity.this, GalleryActivity.class));
                    finish();
                }
                return leaveCurrentWebView;

            }

        });

        // Load the web
        Log.d(LOGIN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Load the web");
        if (Util.isNetworkAvailable(getApplicationContext())) {
            this._wvWeb.loadUrl(String.format(Config.IMGUR_API_URL + Config.IMGUR_PATH_AUTHORIZE, Config.IMGUR_CLIENT_ID));
        } else {
            Toast.makeText(this, getString(R.string.message_error_network), Toast.LENGTH_LONG).show();
            finish();
        }

    }

}
