package es.dlebal.myimgurphotogallery.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import es.dlebal.myimgurphotogallery.R;
import es.dlebal.myimgurphotogallery.Util;
import es.dlebal.myimgurphotogallery.imgur.WebService;
import es.dlebal.myimgurphotogallery.imgur.model.Photo;

/**
 * Class public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>
 *
 * This class is used to instantiate GalleryAdapter objects
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    /**
     * Constants
     */
    private static final String GALLERY_ADAPTER_CLASS_NAME = GalleryAdapter.class.getSimpleName(); // Class name

    /**
     * Variables
     */
    private Context _context; // Context
    private ArrayList<Photo> _photos; // Photos
    private GalleryAdapterListener _galleryAdapterListener; // GalleryAdapterListener

    /**
     * Interface public interface GalleryAdapterListener
     *
     * Performs the following tasks:
     *   - Declare GalleryAdapterListener interfaces
     */
    public interface GalleryAdapterListener {

        // Declare AccountImagesListener interfaces
        void onSizeChangedResponse(int size);

    }

    /**
     * Constructor public GalleryAdapter(Context context, ArrayList<Photo> photos)
     *
     * Performs the following tasks:
     *   - Initialize the instances of the class GalleryAdapter
     *
     * Parameters:
     *   @param context: Context
     *   @param photos: Photos
     */
    public GalleryAdapter(Context context, ArrayList<Photo> photos) {

        // Constants
        final String METHOD_NAME = "GalleryAdapter"; // Method name

        // Initialize the instances of the class GalleryAdapter
        Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME, "Initialize the instances of the class GalleryAdapter");
        this._context = context;
        this._photos = photos;
        this._galleryAdapterListener = null;

    }

    /**
     * Method public void GalleryAdapterListener(GalleryAdapterListener galleryAdapterListener)
     *
     * Performs the following tasks:
     *   - Assign the listener implementing events interface that will receive the events
     *
     * Parameters:
     *   @param galleryAdapterListener: GalleryAdapterListener
     */
    public void GalleryAdapterListener(GalleryAdapterListener galleryAdapterListener) {

        // Constants
        final String METHOD_NAME = "GalleryAdapterListener"; // Method name

        // Assign the listener implementing events interface that will receive the events
        Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME, "Assign the listener implementing events interface that will receive the events");
        this._galleryAdapterListener = galleryAdapterListener;

    }

    /**
     * Method public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
     *
     * Performs the following tasks:
     *   - Get a View that displays the item
     *
     * Parameters:
     *   @param viewGroup: The ViewGroup into which the new View will be added after it is bound to an adapter position
     *   @param viewType: The view type of the new View
     *   @return itemView: A new ViewHolder that holds a View of the given view type
     */
    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        // Constants
        final String METHOD_NAME = "onCreateViewHolder"; // Method name

        // Variables
        View viewItem; // View item

        // Get a View that displays the item
        Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME, "Get a View that displays the item");
        viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_item, viewGroup, false);
        return new GalleryViewHolder(viewItem);

    }

    /**
     * Method public void onBindViewHolder(@NonNull GalleryViewHolder galleryViewHolder, int position)
     *
     * Performs the following tasks:
     *   - Update the contents of the itemView to reflect the item at the given position
     *
     * Parameters:
     *   @param galleryViewHolder: The ViewHolder which should be updated to represent the contents of the item at the given position in the data set
     *   @param position: The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder galleryViewHolder, int position) {

        // Constants
        final String METHOD_NAME = "onBindViewHolder"; // Method name

        // Update the contents of the itemView to reflect the item at the given position
        Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME, "Update the contents of the itemView to reflect the item at the given position");
        Picasso.with(this._context)
                .load(this._photos.get(position).getLink())
                .into(galleryViewHolder._ivPhoto);
        final int positionAux = position;
        galleryViewHolder._bDeletePhoto.setOnClickListener(new View.OnClickListener() {

            /**
             * Method public void onClick(final View v)
             *
             * Performs the following tasks:
             *   - Handle the click event for delete photo
             *
             * Parameters:
             *   @param v: The View that was clicked
             */
            public void onClick(final View v) {

                // Constants
                final String SUBMETHOD_NAME = "onClick"; // Method name

                // Handle the click event for delete photo
                Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Handle the click event for delete photo");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GalleryAdapter.this._context);
                alertDialogBuilder
                        .setPositiveButton(GalleryAdapter.this._context.getString(R.string.alert_dialog_button_ok), new DialogInterface.OnClickListener() {

                            /**
                             * Method public void onClick(DialogInterface dialogInterface, int which)
                             *
                             * Performs the following tasks:
                             *   - Delete image
                             *
                             * Parameters:
                             *   @param dialogInterface: The dialog that received the click
                             *   @param which: The button that was clicked or the position of the item clicked
                             */
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                                // Constants
                                final String SUBMETHOD_NAME_2 = "onClick"; // Method name

                                // Delete image
                                Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME + " - " + SUBMETHOD_NAME_2, "Delete image");
                                GalleryAdapter.this.deleteImage(v, GalleryAdapter.this._photos.get(positionAux).getDeleteHash(), positionAux);

                            }

                        })
                        .setNegativeButton(GalleryAdapter.this._context.getString(R.string.alert_dialog_button_cancel), new DialogInterface.OnClickListener() {

                            /**
                             * Method public void onClick(DialogInterface dialogInterface, int which)
                             *
                             * Performs the following tasks:
                             *   - Dismiss the AlertDialog
                             *
                             * Parameters:
                             *   @param dialogInterface: The dialog that received the click
                             *   @param which: The button that was clicked or the position of the item clicked
                             */
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                                // Constants
                                final String METHOD_NAME = "onClick"; // Method name

                                // Dismiss the AlertDialog
                                Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME, "Dismiss the AlertDialog");

                            }

                        });
                alertDialogBuilder.setMessage(R.string.message_delete_image_confirmation);
                alertDialogBuilder.show();

            }

        });

    }

    /**
     * Method public int getItemCount()
     *
     * Performs the following tasks:
     *   - Return the total number of items in the data set held by the adapter
     *
     * Parameters:
     *   @return int: The total number of items in this adapter
     */
    @Override
    public int getItemCount() {

        // Constants
        final String METHOD_NAME = "getItemCount"; // Method name

        // Return the total number of items in the data set held by the adapter
        Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME, "Return the total number of items in the data set held by the adapter");
        return this._photos.size();

    }

    /**
     * Method private void deleteImage(final View view, String imageDeleteHash, final int position)
     *
     * Performs the following tasks:
     *   - Delete an image
     *
     * Parameters:
     *   @param view: View
     *   @param imageDeleteHash: Image delete hash
     *   @param position: Position
     */
    private void deleteImage(final View view, String imageDeleteHash, final int position) {

        // Constants
        final String METHOD_NAME = "deleteImage"; // Method name

        // Delete an image
        Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME, "Delete an image");
        WebService.getInstance(this._context).imageDeletion(new WebService.ImageDeletionListener() {

            /**
             * Method public void onImageDeletionResponse()
             *
             * Performs the following tasks:
             *   - Show the success of the image deletion
             */
            @Override
            public void onImageDeletionResponse() {

                // Constants
                final String SUBMETHOD_NAME = "onImageDeletionResponse"; // Method name

                // Show the success of the image deletion
                Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Show the success of the image deletion");
                GalleryAdapter.this._photos.remove(position);
                GalleryAdapter.this._galleryAdapterListener.onSizeChangedResponse(GalleryAdapter.this._photos.size());
                Snackbar.make(view, Html.fromHtml("<font color=\"" + Util.convertIntColorToHexColor(GalleryAdapter.this._context, R.color.colorSuccess) + "\">" + GalleryAdapter.this._context.getString(R.string.message_image_deleted) + "</font>"), Snackbar.LENGTH_SHORT).show();

            }

            /**
             * Method public void onImageDeletionErrorResponse()
             *
             * Performs the following tasks:
             *   - Show the error
             */
            @Override
            public void onImageDeletionErrorResponse() {

                // Constants
                final String SUBMETHOD_NAME = "onImageDeletionErrorResponse"; // Method name

                // Show the error
                Log.d(GALLERY_ADAPTER_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Show the error");
                Snackbar.make(view, Html.fromHtml("<font color=\"" + Util.convertIntColorToHexColor(GalleryAdapter.this._context, R.color.colorError) + "\">" + GalleryAdapter.this._context.getString(R.string.message_error_deleting_image) + "</font>"), Snackbar.LENGTH_SHORT).show();

            }

        }, imageDeleteHash);

    }

    /**
     * Class public static class GalleryViewHolder extends RecyclerView.ViewHolder
     *
     * This class is used to instantiate GalleryViewHolder objects
     */
    public static class GalleryViewHolder extends RecyclerView.ViewHolder {

        /**
         * Constants
         */
        private final String GALLERY_VIEW_HOLDER_CLASS_NAME = GalleryViewHolder.class.getSimpleName(); // Class name

        /**
         * Variables
         */
        private ImageView _ivPhoto; // Photo
        private Button _bDeletePhoto; // Delete photo

        /**
         * Constructor private GalleryViewHolder(View view)
         *
         * Performs the following tasks:
         *   - Initialize the instances of the class GalleryViewHolder
         *
         * Parameters:
         *   @param view: View
         */
        private GalleryViewHolder(View view) {

            super(view);

            // Constants
            final String METHOD_NAME = "GalleryViewHolder"; // Method name

            // Initialize the instances of the class GalleryViewHolder
            Log.d(GALLERY_VIEW_HOLDER_CLASS_NAME + ": " + METHOD_NAME, "Initialize the instances of the class GalleryViewHolder");
            this._ivPhoto = itemView.findViewById(R.id.gallery_item_ivPhoto);
            this._bDeletePhoto = itemView.findViewById(R.id.gallery_item_bDeletePhoto);

        }

    }

}
