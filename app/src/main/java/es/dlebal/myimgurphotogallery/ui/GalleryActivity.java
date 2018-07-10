package es.dlebal.myimgurphotogallery.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import es.dlebal.myimgurphotogallery.Config;
import es.dlebal.myimgurphotogallery.R;
import es.dlebal.myimgurphotogallery.Util;
import es.dlebal.myimgurphotogallery.imgur.WebService;
import es.dlebal.myimgurphotogallery.imgur.model.Photo;

/**
 * Class public class GalleryActivity extends AppCompatActivity
 *
 * This class is used to instantiate GalleryActivity objects
 */
public class GalleryActivity extends AppCompatActivity {

    /**
     * Constants
     */
    private static final String GALLERY_ACTIVITY_CLASS_NAME = GalleryActivity.class.getSimpleName(); // Class name

    /**
     * Variables
     */
    protected SwipeRefreshLayout _srlWrapper; // Wrapper
    protected RecyclerView _rvGallery; // Gallery
    protected TextView _tvGalleryEmpty; // Gallery empty
    protected FloatingActionButton _fabAddPhoto; // Add photo

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
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the activity");
        setContentView(R.layout.gallery_activity);
        this.initializeUI();

    }

    /**
     * Method public boolean onCreateOptionsMenu(Menu menu)
     *
     * Performs the following tasks:
     *   - Inflate the menu items for use in the action bar
     *
     * Parameters:
     *   @param menu: The options menu in which you place your items
     *   @return boolean: You must return true for the menu to be displayed. If you return false it will not be shown
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Constants
        final String METHOD_NAME = "onCreateOptionsMenu"; // Method name

        // Inflate the menu items for use in the action bar
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Inflate the menu items for use in the action bar");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Method public boolean onOptionsItemSelected(MenuItem item)
     *
     * Performs the following tasks:
     *   - Handle the menu item clicks
     *
     * Parameters:
     *   @param item: The menu item that was selected
     *   @return boolean: Return false to allow normal menu processing to proceed, true to consume it here
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Constants
        final String METHOD_NAME = "onOptionsItemSelected"; // Method name

        // Handle the menu item clicks
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Handle the menu item clicks");
        switch (item.getItemId()) {
            case R.id.menu_item_logout:
                Util.logout(getApplicationContext());
                startActivity(new Intent(GalleryActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Method public void onActivityResult(int requestCode, int resultCode, Intent data)
     *
     * Performs the following tasks:
     *   - Handle the result for the image captured
     *
     * Parameters:
     *   @param requestCode: The integer request code originally supplied to startActivityForResult(), allowing you to identify who this result came from
     *   @param resultCode: The integer result code returned by the child activity through its setResult()
     *   @param data: An Intent, which can return result data to the caller (various data can be attached to Intent "extras")
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Constants
        final String METHOD_NAME = "onActivityResult"; // Method name

        // Handle the result for the image captured
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Handle the result for the image captured");
        if (requestCode == Config.REQUEST_CAMERA) {
            if ((resultCode == RESULT_OK) && (data.getExtras() != null)) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    this.uploadImage(Util.getBase64Image(bitmap));
                } else {
                    Snackbar.make(GalleryActivity.this._srlWrapper, Html.fromHtml("<font color=\"" + Util.convertIntColorToHexColor(this, R.color.colorError) + "\">" + getString(R.string.message_error_capturing_image) + "</font>"), Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(GalleryActivity.this._srlWrapper, Html.fromHtml("<font color=\"" + Util.convertIntColorToHexColor(this, R.color.colorError) + "\">" + getString(R.string.message_error_capturing_image) + "</font>"), Snackbar.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * Method public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
     *
     * Performs the following tasks:
     *   - Handle the result for the add photo permissions requested
     *
     * Parameters:
     *   @param requestCode: The request code passed
     *   @param permissions: The requested permissions
     *   @param grantResults: The grant results for the corresponding permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // Constants
        final String METHOD_NAME = "onRequestPermissionsResult"; // Method name

        // Handle the result for the add photo permissions requested
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Handle the result for the add photo permissions requested");
        if (requestCode == Config.REQUEST_PERMISSIONS_ADD_IMAGE) {
            if ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Config.REQUEST_CAMERA);
            }
        }

    }

    /**
     * Method private void initializeUI()
     *
     * Performs the following tasks:
     *   - Initialize the wrapper
     *   - Initialize the gallery
     *   - Initialize the gallery empty
     *   - Initialize the add photo
     *   - Get the account images
     */
    private void initializeUI() {

        // Constants
        final String METHOD_NAME = "initializeUI"; // Method name

        // Initialize the wrapper
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the wrapper");
        this._srlWrapper = findViewById(R.id.gallery_activity_srlWrapper);
        this._srlWrapper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            /**
             * Method public void onRefresh()
             *
             * Performs the following tasks:
             *   - Refresh the gallery
             */
            @Override
            public void onRefresh() {

                // Constants
                final String SUBMETHOD_NAME = "onRefresh"; // Method name

                // Refresh the gallery
                Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Refresh the gallery");
                GalleryActivity.this.getAccountImages();

            }

        });

        // Initialize the gallery
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the gallery");
        this._rvGallery = findViewById(R.id.gallery_activity_rvGallery);
        this._rvGallery.setHasFixedSize(true);
        this._rvGallery.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        // Initialize the gallery empty
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the gallery empty");
        this._tvGalleryEmpty = findViewById(R.id.gallery_activity_tvGalleryEmpty);

        // Initialize the add photo
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the add photo");
        this._fabAddPhoto = findViewById(R.id.gallery_activity_fabAddPhoto);
        this._fabAddPhoto.setOnClickListener(new View.OnClickListener() {

            /**
             * Method public void onClick(View v)
             *
             * Performs the following tasks:
             *   - Handle the click event for add photo
             *
             * Parameters:
             *   @param v: The View that was clicked
             */
            public void onClick(View v) {

                // Constants
                final String SUBMETHOD_NAME = "onClick"; // Method name

                // Handle the click event for add photo
                Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Handle the click event for add photo");
                if ((ActivityCompat.checkSelfPermission(GalleryActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(GalleryActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, Config.REQUEST_CAMERA);
                } else {
                    ActivityCompat.requestPermissions(GalleryActivity.this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, Config.REQUEST_PERMISSIONS_ADD_IMAGE);
                }

            }

        });

        // Get the account images
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Get the account images");
        this.getAccountImages();

    }

    /**
     * Method private void getAccountImages()
     *
     * Performs the following tasks:
     *   - Get the account images
     */
    private void getAccountImages() {

        // Constants
        final String METHOD_NAME = "getAccountImages"; // Method name

        // Get the account images
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Get the account images");
        WebService.getInstance(getApplicationContext()).accountImages(new WebService.AccountImagesListener() {

            /**
             * Method public void onAccountImagesResponse(final ArrayList<Photo> photos)
             *
             * Performs the following tasks:
             *   - Load the photos
             *
             * Parameters:
             *   @param photos: Photos
             */
            @Override
            public void onAccountImagesResponse(final ArrayList<Photo> photos) {

                // Constants
                final String SUBMETHOD_NAME = "onAccountImagesResponse"; // Method name

                // Load the photos
                Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Load the photos");
                GalleryActivity.this.showGalleryEmpty(photos.size() == 0);
                GalleryAdapter galleryAdapter = new GalleryAdapter(GalleryActivity.this, photos);
                galleryAdapter.GalleryAdapterListener(new GalleryAdapter.GalleryAdapterListener() {

                    /**
                     * Method public void onSizeChangedResponse(int size)
                     *
                     * Performs the following tasks:
                     *   - Show or hide the gallery empty
                     *
                     * Parameters:
                     *   @param size: Size
                     */
                    @Override
                    public void onSizeChangedResponse(int size) {

                        // Constants
                        final String SUBMETHOD_NAME = "onSizeChangedResponse"; // Method name

                        // Show or hide the gallery empty
                        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Show or hide the gallery empty");
                        GalleryActivity.this._rvGallery.getAdapter().notifyDataSetChanged();
                        GalleryActivity.this.showGalleryEmpty(size == 0);

                    }
                });
                GalleryActivity.this._rvGallery.setAdapter(galleryAdapter);
                GalleryActivity.this._srlWrapper.setRefreshing(false);

            }

            /**
             * Method public void onAccountImagesErrorResponse()
             *
             * Performs the following tasks:
             *   - Handle the photos error
             */
            @Override
            public void onAccountImagesErrorResponse() {

                // Constants
                final String SUBMETHOD_NAME = "onAccountImagesErrorResponse"; // Method name

                // Handle the photos error
                Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Handle the photos error");
                Snackbar.make(GalleryActivity.this._srlWrapper, Html.fromHtml("<font color=\"" + Util.convertIntColorToHexColor(GalleryActivity.this, R.color.colorError)+ "\">" + getString(R.string.message_error_getting_gallery) + "</font>"), Snackbar.LENGTH_SHORT).show();
                GalleryActivity.this._srlWrapper.setRefreshing(false);

            }

        });

    }

    /**
     * Method private void showGalleryEmpty(boolean show)
     *
     * Performs the following tasks:
     *   - Show or hide the gallery empty
     *
     * Parameters:
     *   @param show: Show the gallery empty?
     */
    private void showGalleryEmpty(boolean show) {

        // Constants
        final String METHOD_NAME = "showGalleryEmpty"; // Method name

        // Show or hide the gallery empty
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Show or hide the gallery empty. Show? -> " + String.valueOf(show));
        if (show) {
            GalleryActivity.this._rvGallery.setVisibility(View.GONE);
            GalleryActivity.this._tvGalleryEmpty.setVisibility(View.VISIBLE);
        } else {
            GalleryActivity.this._rvGallery.setVisibility(View.VISIBLE);
            GalleryActivity.this._tvGalleryEmpty.setVisibility(View.GONE);
        }

    }

    /**
     * Method private void uploadImage(String image)
     *
     * Performs the following tasks:
     *   - Upload an image
     *
     * Parameters:
     *   @param image: Image (base 64)
     */
    private void uploadImage(String image) {

        // Constants
        final String METHOD_NAME = "uploadImage"; // Method name

        // Upload an image
        Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Upload an image");
        GalleryActivity.this._srlWrapper.setRefreshing(true);
        WebService.getInstance(getApplicationContext()).imageUpload(new WebService.ImageUploadListener() {

            /**
             * Method public void onImageUploadResponse()
             *
             * Performs the following tasks:
             *   - Show the success of the image upload
             */
            @Override
            public void onImageUploadResponse() {

                // Constants
                final String SUBMETHOD_NAME = "onImageUploadResponse"; // Method name

                // Show the success of the image upload
                Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Show the success of the image upload");
                GalleryActivity.this.getAccountImages();
                Snackbar.make(GalleryActivity.this._srlWrapper, Html.fromHtml("<font color=\"" + Util.convertIntColorToHexColor(GalleryActivity.this, R.color.colorSuccess) + "\">" + getString(R.string.message_image_uploaded) + "</font>"), Snackbar.LENGTH_SHORT).show();
                //GalleryActivity.this._srlWrapper.setRefreshing(false);

            }

            /**
             * Method public void onImageUploadErrorResponse()
             *
             * Performs the following tasks:
             *   - Show the error
             */
            @Override
            public void onImageUploadErrorResponse() {

                // Constants
                final String SUBMETHOD_NAME = "onImageUploadErrorResponse"; // Method name

                // Show the error
                Log.d(GALLERY_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Show the error");
                Snackbar.make(GalleryActivity.this._srlWrapper, Html.fromHtml("<font color=\"" + Util.convertIntColorToHexColor(GalleryActivity.this, R.color.colorError) + "\">" + getString(R.string.message_error_uploading_image) + "</font>"), Snackbar.LENGTH_SHORT).show();
                GalleryActivity.this._srlWrapper.setRefreshing(false);

            }

        }, image);

    }

}
