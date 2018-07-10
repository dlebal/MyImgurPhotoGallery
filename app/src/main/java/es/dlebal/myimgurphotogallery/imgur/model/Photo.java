package es.dlebal.myimgurphotogallery.imgur.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static es.dlebal.myimgurphotogallery.Config.*;

/**
 * Class public class Photo
 *
 * This class is used to instantiate Photo objects
 */
public class Photo {

    /**
     * Constants
     */
    private static final String PHOTO_CLASS_NAME = Photo.class.getSimpleName(); // Class name

    /**
     * Variables
     */
    private String _id = null; // Identifier
    private String _link = null; // Link
    private String _deleteHash = null; // Delete hash

    /**
     * Constructor public Photo(JSONObject photo)
     *
     * Performs the following tasks:
     *   - Performs the following tasks:
     *
     * Parameters:
     *   @param photo: JSONObject
     */
    public Photo(JSONObject photo) {

        // Constants
        final String METHOD_NAME = "Photo"; // Method name

        // Performs the following tasks:
        Log.d(PHOTO_CLASS_NAME + ": " + METHOD_NAME, "Performs the following tasks:");
        if ((photo.has(IMGUR_PARAMETER_ID)) && (photo.has(IMGUR_PARAMETER_LINK)) && (photo.has(IMGUR_PARAMETER_DELETE_HASH))) {
            try {
                this.setId(photo.getString(IMGUR_PARAMETER_ID));
                this.setLink(photo.getString(IMGUR_PARAMETER_LINK));
                this.setDeleteHash(photo.getString(IMGUR_PARAMETER_DELETE_HASH));
            } catch (JSONException e) {
                Log.e(PHOTO_CLASS_NAME + ": " + METHOD_NAME, "JSONException: " + e.toString());
            }
        }

    }

    /**
     * Method public String getId()
     *
     * Performs the following tasks:
     *   - Get the identifier
     *
     * Parameters:
     *   @return this._id: Identifier
     */
    public String getId() {

        // Constants
        final String METHOD_NAME = "getId"; // Method name

        // Get the identifier
        Log.d(PHOTO_CLASS_NAME + ": " + METHOD_NAME, "Get the identifier");
        return this._id;

    }

    /**
     * Method public void setId(String id)
     *
     * Performs the following tasks:
     *   - Set the identifier
     *
     * Parameters:
     *   @param id: Identifier
     */
    public void setId(String id) {

        // Constants
        final String METHOD_NAME = "setId"; // Method name

        // Set the identifier
        Log.d(PHOTO_CLASS_NAME + ": " + METHOD_NAME, "Set the identifier");
        this._id = id;

    }

    /**
     * Method public String getLink()
     *
     * Performs the following tasks:
     *   - Get the link
     *
     * Parameters:
     *   @return this._link: Link
     */
    public String getLink() {

        // Constants
        final String METHOD_NAME = "getLink"; // Method name

        // Get the link
        Log.d(PHOTO_CLASS_NAME + ": " + METHOD_NAME, "Get the link");
        return this._link;

    }

    /**
     * Method public void setLink(String link)
     *
     * Performs the following tasks:
     *   - Set the link
     *
     * Parameters:
     *   @param link: Link
     */
    public void setLink(String link) {

        // Constants
        final String METHOD_NAME = "setLink"; // Method name

        // Set the link
        Log.d(PHOTO_CLASS_NAME + ": " + METHOD_NAME, "Set the link");
        this._link = link;

    }

    /**
     * Method public String getDeleteHash()
     *
     * Performs the following tasks:
     *   - Get the delete hash
     *
     * Parameters:
     *   @return this._deleteHash: Delete hash
     */
    public String getDeleteHash() {

        // Constants
        final String METHOD_NAME = "getDeleteHash"; // Method name

        // Get the delete hash
        Log.d(PHOTO_CLASS_NAME + ": " + METHOD_NAME, "Get the delete hash");
        return this._deleteHash;

    }

    /**
     * Method public void setDeleteHash(String deleteHash)
     *
     * Performs the following tasks:
     *   - Set the delete hash
     *
     * Parameters:
     *   @param deleteHash: Delete hash
     */
    public void setDeleteHash(String deleteHash) {

        // Constants
        final String METHOD_NAME = "setDeleteHash"; // Method name

        // Set the delete hash
        Log.d(PHOTO_CLASS_NAME + ": " + METHOD_NAME, "Set the delete hash");
        this._deleteHash = deleteHash;

    }

}
