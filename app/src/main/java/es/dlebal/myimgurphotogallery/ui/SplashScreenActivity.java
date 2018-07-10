package es.dlebal.myimgurphotogallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import es.dlebal.myimgurphotogallery.R;

/**
 * Class public class SplashScreenActivity extends AppCompatActivity
 *
 * This class is used to instantiate SplashScreenActivity objects
 */
public class SplashScreenActivity extends AppCompatActivity {

    /**
     * Constants
     */
    private static final String SPLASH_SCREEN_ACTIVITY_CLASS_NAME = SplashScreenActivity.class.getSimpleName(); // Class name

    /**
     * Variables
     */
    protected RelativeLayout _rlWrapper; // Wrapper
    protected ImageView _ivLogo; // Logo

    /**
     * Method protected void onCreate(Bundle savedInstanceState)
     *
     * Performs the following tasks:
     *   - Initialize the activity and prevent relaunch from launcher icon (only in root activity)
     *
     * Parameters:
     *   @param savedInstanceState: If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Constants
        final String METHOD_NAME = "onCreate"; // Method name

        // Initialize the activity and prevent relaunch from launcher icon (only in root activity)
        Log.d(SPLASH_SCREEN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the activity and prevent relaunch from launcher icon (only in root activity)");
        if (!isTaskRoot()) { // Android launched another instance of the root activity into an existing task so just quietly finish and go away, dropping the user back into the activity at the top of the stack
            finish();
            return;
        }
        setContentView(R.layout.splash_screen_activity);
        this.initializeUI();

    }

    /**
     * Method private void initializeUI()
     *
     * Performs the following tasks:
     *   - Initialize the wrapper
     *   - Initialize the logo
     *   - Start the presentation
     */
    private void initializeUI() {

        // Constants
        final String METHOD_NAME = "initializeUI"; // Method name

        // Initialize the wrapper
        Log.d(SPLASH_SCREEN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the wrapper");
        this._rlWrapper = findViewById(R.id.splash_screen_activity_rlWrapper);

        // Initialize the logo
        Log.d(SPLASH_SCREEN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Initialize the logo");
        this._ivLogo = findViewById(R.id.splash_screen_activity_ivLogo);

        // Start the presentation
        Log.d(SPLASH_SCREEN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Start the presentation");
        this.startPresentation();

    }

    /**
     * Method private void startPresentation()
     *
     * Performs the following tasks:
     *   - Do the fade out logo animation
     *   - Do the translate top logo animation and switch to other activity with animation:
     *       · Do the user login
     */
    private void startPresentation() {

        // Constants
        final String METHOD_NAME = "startPresentation"; // Method name

        // Do the fade out logo animation
        Log.d(SPLASH_SCREEN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Do the fade out logo animation");
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_out_3000);
        animation.reset();
        this._rlWrapper.clearAnimation();
        this._rlWrapper.startAnimation(animation);

        // Do the translate top logo animation and switch to other activity with animation
        Log.d(SPLASH_SCREEN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME, "Do the translate top logo animation and switch to other activity with animation");
        animation = AnimationUtils.loadAnimation(this, R.anim.translate_top_2000);
        animation.reset();
        this._ivLogo.clearAnimation();
        this._ivLogo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            /**
             * Method public void onAnimationStart(Animation animation)
             *
             * Performs the following tasks:
             *   - Notifies the start of the animation
             *
             * Parameters:
             *   @param animation: The started animation
             */
            @Override
            public void onAnimationStart(Animation animation) {

                // Constants
                final String SUBMETHOD_NAME = "onAnimationStart"; // Method name

                // Notifies the start of the animation
                Log.d(SPLASH_SCREEN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Notifies the start of the animation");

            }

            /**
             * Method public void onAnimationEnd(Animation animation)
             *
             * Performs the following tasks:
             *   - Do the user login
             *
             * Parameters:
             *   @param animation: The animation which reached its end
             */
            @Override
            public void onAnimationEnd(Animation animation) {

                // Constants
                final String SUBMETHOD_NAME = "onAnimationEnd"; // Method name

                // Do the user login
                Log.d(SPLASH_SCREEN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Do the user login");
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();

            }

            /**
             * Method public void onAnimationRepeat(Animation animation)
             *
             * Performs the following tasks:
             *   - Notifies the repetition of the animation
             *
             * Parameters:
             *   @param animation: The animation which was repeated
             */
            @Override
            public void onAnimationRepeat(Animation animation) {

                // Constants
                final String SUBMETHOD_NAME = "onAnimationRepeat"; // Method name

                // Notifies the repetition of the animation
                Log.d(SPLASH_SCREEN_ACTIVITY_CLASS_NAME + ": " + METHOD_NAME + " - " + SUBMETHOD_NAME, "Notifies the repetition of the animation");

            }

        });

    }

}
