package es.dlebal.myimgurphotogallery;

import java.util.regex.Pattern;

/**
 * Class public class Config
 *
 * This class is used to instantiate Config objects
 */
public class Config {

    // Imgur
    public static final String IMGUR_API_URL = "https://api.imgur.com/"; // Imgur api url
    public final static String IMGUR_CLIENT_ID = "08ddecf7e678a69"; // Imgur client identifier
    public final static String IMGUR_PARAMETER_DATA = "data"; // Imgur parameter data
    public final static String IMGUR_PARAMETER_DELETE_HASH = "deletehash"; // Imgur parameter delete hash
    public final static String IMGUR_PARAMETER_ID = "id"; // Imgur parameter identifier
    public final static String IMGUR_PARAMETER_IMAGE = "image"; // Imgur parameter image
    public final static String IMGUR_PARAMETER_LINK = "link"; // Imgur parameter link
    public final static String IMGUR_PARAMETER_SUCCESS = "success"; // Imgur parameter success
    public static final String IMGUR_PATH_ACCOUNT_IMAGES = "3/account/me/images"; // Imgur path account images
    public final static String IMGUR_PATH_AUTHORIZE = "oauth2/authorize?client_id=%s&response_type=token"; // Imgur path authorize
    public static final String IMGUR_PATH_DELETE_IMAGE = "3/image/%s"; // Imgur path delete image
    public static final String IMGUR_PATH_UPLOAD_IMAGE = "3/image"; // Imgur path upload image
    public final static Pattern IMGUR_PATTERN_ACCESS_TOKEN = Pattern.compile("access_token=([^&]*)"); // Imgur access token pattern
    public final static Pattern IMGUR_PATTERN_REFRESH_TOKEN = Pattern.compile("refresh_token=([^&]*)"); // Imgur refresh token pattern
    public final static Pattern IMGUR_PATTERN_EXPIRES_IN = Pattern.compile("expires_in=(\\d+)"); // Imgur expires in pattern
    public final static String IMGUR_REDIRECT_URL = "https://imgur.com/"; // Imgur redirect url

    // Request
    public final static int REQUEST_CAMERA = 1000; // Request camera
    public final static int REQUEST_PERMISSIONS_ADD_IMAGE = 1001; // Request permissions add image

}
