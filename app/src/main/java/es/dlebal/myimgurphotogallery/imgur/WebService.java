package es.dlebal.myimgurphotogallery.imgur;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dlebal.myimgurphotogallery.Config;
import es.dlebal.myimgurphotogallery.R;
import es.dlebal.myimgurphotogallery.imgur.model.Photo;

import static es.dlebal.myimgurphotogallery.Config.*;

/**
 * Class public class WebService
 *
 * This class is used to instantiate WebService objects
 */
public class WebService {

	/**
	 * Constants
	 */
	private static final String WEB_SERVICE_CLASS_NAME = WebService.class.getSimpleName(); // Class name

	/**
	 * Variables
	 */
	private Context _context; // Context
	private static WebService _ws; // WebService
	private RequestQueue _requestQueue; // RequestQueue

	/**
	 * Interface public interface AccountImagesListener
	 *
	 * Performs the following tasks:
	 *   - Declare AccountImagesListener interfaces
	 */
	public interface AccountImagesListener {

		// Declare AccountImagesListener interfaces
		void onAccountImagesResponse(ArrayList<Photo> photos);
		void onAccountImagesErrorResponse();

	}

	/**
	 * Interface public interface ImageDeletionListener
	 *
	 * Performs the following tasks:
	 *   - Declare ImageDeletionListener interfaces
	 */
	public interface ImageDeletionListener {

		// Declare ImageDeletionListener interfaces
		void onImageDeletionResponse();
		void onImageDeletionErrorResponse();

	}

	/**
	 * Interface public interface ImageUploadListener
	 *
	 * Performs the following tasks:
	 *   - Declare ImageUploadListener interfaces
	 */
	public interface ImageUploadListener {

		// Declare ImageUploadListener interfaces
		void onImageUploadResponse();
		void onImageUploadErrorResponse();

	}

	/**
	 * Constructor private WebService(Context context)
	 *
	 * Performs the following tasks:
	 *   - Initialize the instances of the class WebService
	 *
	 * Parameters:
	 *   @param context: Context
	 */
	private WebService(Context context) {

		// Constants
		final String METHOD_NAME = "WebService"; // Method name

		// Initialize the instances of the class WebService
		Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME, "Initialize the instances of the class WebService");
		this._context = context;
		try {
			this._requestQueue = Volley.newRequestQueue(this._context, new HurlStack(null, new TLSSocketFactory()));
		} catch (KeyManagementException e) {
			Log.e(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME, "KeyManagementException: " + e.toString());
		} catch (NoSuchAlgorithmException e) {
			Log.e(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME, "NoSuchAlgorithmException: " + e.toString());
		}

	}

	/**
	 * Constructor public static synchronized WebService getInstance(Context context)
	 *
	 * Performs the following tasks:
	 *   - Initialize the instances of the class WebService
	 *
	 * Parameters:
	 *   @param context: Context
	 *   @return _ws: WebService
	 */
	public static synchronized WebService getInstance(Context context) {

		// Constants
		final String METHOD_NAME = "WebService"; // Method name

		// Initialize the instances of the class WebService
		Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME, "Initialize the instances of the class WebService");
		if (_ws == null) {
			_ws = new WebService(context);
		}
		return _ws;

	}

	/**
	 * Method public void accountImages(final AccountImagesListener accountImagesListener)
	 *
	 * Performs the following tasks:
	 *   - Get the account images
	 *
	 * Parameters:
	 *   @param accountImagesListener: AccountImagesListener
	 */
	public void accountImages(final AccountImagesListener accountImagesListener) {

		// Constants
		final String METHOD_NAME = "accountImages"; // Method name

		// Get the account images
		Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME, "Get the account images");
		StringRequest stringRequest = new StringRequest(Request.Method.GET, buildUrl(Config.IMGUR_API_URL, Config.IMGUR_PATH_ACCOUNT_IMAGES), new Response.Listener<String>() {

			/**
			 * Method public void onResponse(String response)
			 *
			 * Performs the following tasks:
			 *   - Get the response
			 *
			 * Parameters:
			 *   @param response: Response
			 */
			@Override
			public void onResponse(String response) {

				// Constants
				final String SUBMETHOD_NAME = "onResponse"; // Method name

                // Variables
                ArrayList<Photo> photos = new ArrayList<>(); // Photos

				// Get the response
				Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Get the response");
				try {
					JSONObject result = new JSONObject(response);
					if ((result.has(IMGUR_PARAMETER_DATA)) && (result.has(IMGUR_PARAMETER_SUCCESS))) {
						if (result.getBoolean(IMGUR_PARAMETER_SUCCESS)) {
							JSONArray data = result.getJSONArray(IMGUR_PARAMETER_DATA);
							for (int i = 0; i < data.length(); i++) {
								Photo photo = new Photo(data.getJSONObject(i));
								if (!TextUtils.isEmpty(photo.getId())) {
									photos.add(photo);
								}
							}
							accountImagesListener.onAccountImagesResponse(photos);
						} else {
							accountImagesListener.onAccountImagesErrorResponse();
						}
					} else {
						accountImagesListener.onAccountImagesErrorResponse();
					}
				} catch (JSONException e) {
					Log.e(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "JSONException: " + e.toString());
					accountImagesListener.onAccountImagesErrorResponse();
				}

			}

		}, new Response.ErrorListener() {

			/**
			 * Method public void onErrorResponse(VolleyError error)
			 *
			 * Performs the following tasks:
			 *   - Get the error
			 *
			 * Parameters:
			 *   @param error: Error
			 */
			@Override
			public void onErrorResponse(VolleyError error) {

				// Constants
				final String SUBMETHOD_NAME = "onErrorResponse"; // Method name

				// Get the error
				Log.e(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Get the error -> " + error.toString());
				WebService.this.showVolleyErrorMessage(error);
				accountImagesListener.onAccountImagesErrorResponse();

			}

		})
		{

			/**
			 * Method public Map<String, String> getHeaders()
			 *
			 * Performs the following tasks:
			 *   - Return the headers
			 *
			 * Parameters:
			 *   @return Map<String, String>: Headers
			 */
			@Override
			public Map<String, String> getHeaders() {

				// Constants
				final String SUBMETHOD_NAME = "getHeaders"; // Method name

				// Variables
				Map<String,String> headers = new HashMap<>(); // Headers
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WebService.this._context); // SharedPreferences

				// Return the headers
				Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Return the headers");
				headers.put("Authorization", "Bearer " + sharedPreferences.getString(WebService.this._context.getString(R.string.imgur_access_token_key), WebService.this._context.getString(R.string.imgur_access_token_default_value)));
				return headers;

			}

		};
		this._requestQueue.add(stringRequest);

	}

	/**
	 * Method public void imageDeletion(final ImageDeletionListener imageDeletionListener, final String imageDeleteHash)
	 *
	 * Performs the following tasks:
	 *   - Delete an image
	 *
	 * Parameters:
	 *   @param imageDeletionListener: ImageDeletionListener
	 *   @param imageDeleteHash: Image delete hash
	 */
	public void imageDeletion(final ImageDeletionListener imageDeletionListener, final String imageDeleteHash) {

		// Constants
		final String METHOD_NAME = "imageDeletion"; // Method name

		// Delete an image
		Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME, "Delete an image");
		StringRequest stringRequest = new StringRequest(Request.Method.DELETE, buildUrl(Config.IMGUR_API_URL, String.format(Config.IMGUR_PATH_DELETE_IMAGE, imageDeleteHash)), new Response.Listener<String>() {

			/**
			 * Method public void onResponse(String response)
			 *
			 * Performs the following tasks:
			 *   - Get the response
			 *
			 * Parameters:
			 *   @param response: Response
			 */
			@Override
			public void onResponse(String response) {

				// Constants
				final String SUBMETHOD_NAME = "onResponse"; // Method name

				// Get the response
				Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Get the response");
				try {
					JSONObject result = new JSONObject(response);
					if ((result.has(IMGUR_PARAMETER_DATA)) && (result.has(IMGUR_PARAMETER_SUCCESS))) {
						if (result.getBoolean(IMGUR_PARAMETER_SUCCESS)) {
							imageDeletionListener.onImageDeletionResponse();
						} else {
							imageDeletionListener.onImageDeletionErrorResponse();
						}
					} else {
						imageDeletionListener.onImageDeletionErrorResponse();
					}
				} catch (JSONException e) {
					Log.e(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "JSONException: " + e.toString());
					imageDeletionListener.onImageDeletionErrorResponse();
				}

			}

		}, new Response.ErrorListener() {

			/**
			 * Method public void onErrorResponse(VolleyError error)
			 *
			 * Performs the following tasks:
			 *   - Get the error
			 *
			 * Parameters:
			 *   @param error: Error
			 */
			@Override
			public void onErrorResponse(VolleyError error) {

				// Constants
				final String SUBMETHOD_NAME = "onErrorResponse"; // Method name

				// Get the error
				Log.e(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Get the error -> " + error.toString());
				WebService.this.showVolleyErrorMessage(error);
				imageDeletionListener.onImageDeletionErrorResponse();

			}

		})
		{

			/**
			 * Method public Map<String, String> getHeaders()
			 *
			 * Performs the following tasks:
			 *   - Return the headers
			 *
			 * Parameters:
			 *   @return Map<String, String>: Headers
			 */
			@Override
			public Map<String, String> getHeaders() {

				// Constants
				final String SUBMETHOD_NAME = "getHeaders"; // Method name

				// Variables
				Map<String,String> headers = new HashMap<>(); // Headers

				// Return the headers
				Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Return the headers");
				headers.put("Authorization", "Client-ID " + Config.IMGUR_CLIENT_ID);
				return headers;

			}

		};
		this._requestQueue.add(stringRequest);

	}

	/**
	 * Method public void imageUpload(final ImageUploadListener imageUploadListener, final String image)
	 *
	 * Performs the following tasks:
	 *   - Upload an image
	 *
	 * Parameters:
	 *   @param imageUploadListener: ImageUploadListener
	 *   @param image: Image (base 64)
	 */
	public void imageUpload(final ImageUploadListener imageUploadListener, final String image) {

		// Constants
		final String METHOD_NAME = "imageUpload"; // Method name

		// Upload an image
		Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME, "Upload an image");
		StringRequest stringRequest = new StringRequest(Request.Method.POST, buildUrl(Config.IMGUR_API_URL, Config.IMGUR_PATH_UPLOAD_IMAGE), new Response.Listener<String>() {

			/**
			 * Method public void onResponse(String response)
			 *
			 * Performs the following tasks:
			 *   - Get the response
			 *
			 * Parameters:
			 *   @param response: Response
			 */
			@Override
			public void onResponse(String response) {

				// Constants
				final String SUBMETHOD_NAME = "onResponse"; // Method name

				// Get the response
				Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Get the response");
				try {
					JSONObject result = new JSONObject(response);
					if ((result.has(IMGUR_PARAMETER_DATA)) && (result.has(IMGUR_PARAMETER_SUCCESS))) {
						if (result.getBoolean(IMGUR_PARAMETER_SUCCESS)) {
							imageUploadListener.onImageUploadResponse();
						} else {
							imageUploadListener.onImageUploadErrorResponse();
						}
					} else {
						imageUploadListener.onImageUploadErrorResponse();
					}
				} catch (JSONException e) {
					Log.e(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "JSONException: " + e.toString());
					imageUploadListener.onImageUploadErrorResponse();
				}

			}

		}, new Response.ErrorListener() {

			/**
			 * Method public void onErrorResponse(VolleyError error)
			 *
			 * Performs the following tasks:
			 *   - Get the error
			 *
			 * Parameters:
			 *   @param error: Error
			 */
			@Override
			public void onErrorResponse(VolleyError error) {

				// Constants
				final String SUBMETHOD_NAME = "onErrorResponse"; // Method name

				// Get the error
				Log.e(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Get the error -> " + error.toString());
				WebService.this.showVolleyErrorMessage(error);
				imageUploadListener.onImageUploadErrorResponse();

			}

		})
		{

			/**
			 * Method public Map<String, String> getHeaders()
			 *
			 * Performs the following tasks:
			 *   - Return the headers
			 *
			 * Parameters:
			 *   @return Map<String, String>: Headers
			 */
			@Override
			public Map<String, String> getHeaders() {

				// Constants
				final String SUBMETHOD_NAME = "getHeaders"; // Method name

				// Variables
				Map<String,String> headers = new HashMap<>(); // Headers
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WebService.this._context); // SharedPreferences

				// Return the headers
				Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Return the headers");
				headers.put("Authorization", "Bearer " + sharedPreferences.getString(WebService.this._context.getString(R.string.imgur_access_token_key), WebService.this._context.getString(R.string.imgur_access_token_default_value)));
				return headers;

			}

			/**
			 * Method protected Map<String, String> getParams()
			 *
			 * Performs the following tasks:
			 *   - Return the parameters
			 *
			 * Parameters:
			 *   @return Map<String, String>: Parameters
			 */
			@Override
			protected Map<String, String> getParams() {

				// Constants
				final String SUBMETHOD_NAME = "getParams"; // Method name

				// Variables
				Map<String,String> parameters = new HashMap<>(); // Parameters

				// Return the parameters
				Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Return the parameters");
				parameters.put(Config.IMGUR_PARAMETER_IMAGE, image);
				return parameters;

			}

		};
		this._requestQueue.add(stringRequest);

	}

	/**
	 * Method private static String buildUrl(String urlBase, String method)
	 *
	 * Performs the following tasks:
	 *   - Return the web service url
	 *
	 * Parameters:
	 *   @param urlBase: Url base
	 *   @param method: Method
	 *   @return url: Web service url
	 */
	private static String buildUrl(String urlBase, String method) {

		// Constants
		final String METHOD_NAME = "buildUrl"; // Method name

		// Variables
		String url; // Url

		// Return the web service url
		Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME, "Return the web service url");
		if ((urlBase.endsWith("/")) && method.startsWith("/")) {
			url = urlBase + method.replaceFirst("/", "");
		} else if ((urlBase.endsWith("/")) || method.startsWith("/")) {
			url = urlBase + method;
		} else {
			url = urlBase + "/" + method;
		}
		return url;

	}

	/**
	 * Method private void showVolleyErrorMessage(VolleyError volleyError)
	 *
	 * Performs the following tasks:
	 *   - Show the Volley error message to the user
	 *
	 * Parameters:
	 *   @param volleyError: VolleyError
	 */
	private void showVolleyErrorMessage(VolleyError volleyError) {

		// Constants
		final String METHOD_NAME = "showVolleyErrorMessage"; // Method name

		// Show the Volley error message to the user
		Log.d(WEB_SERVICE_CLASS_NAME + ": " + METHOD_NAME, "Show the Volley error message to the user -> " + volleyError.toString());
		if (volleyError instanceof AuthFailureError) {
			Toast.makeText(this._context, this._context.getString(R.string.message_error_network_authentication_failure), Toast.LENGTH_LONG).show();
		} else if (volleyError instanceof NetworkError) {
			Toast.makeText(this._context, this._context.getString(R.string.message_error_network), Toast.LENGTH_LONG).show();
		} else if (volleyError instanceof ParseError) {
			Toast.makeText(this._context, this._context.getString(R.string.message_error_network_parse), Toast.LENGTH_LONG).show();
		} else if (volleyError instanceof ServerError) {
			Toast.makeText(this._context, this._context.getString(R.string.message_error_network_server), Toast.LENGTH_LONG).show();
		} else if (volleyError instanceof TimeoutError) {
			Toast.makeText(this._context, this._context.getString(R.string.message_error_network_timeout), Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this._context, this._context.getString(R.string.message_error_network), Toast.LENGTH_LONG).show();
		}

	}

}
