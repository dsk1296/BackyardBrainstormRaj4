package hackathon.rajasthan.rajasthantourism;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import hackathon.rajasthan.rajasthantourism.utils.Constants;


public abstract class BaseActivity extends AppCompatActivity {

    String textSize;
    boolean downloadImages,notificationPreference;
    Toolbar mToolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setUpFont();

        super.onCreate(savedInstanceState);

       /* initializeTwitter();*/

        sharedPreferencesListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(prefListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(prefListener);
    }

    //get toolbar ID
    protected abstract int getToolbarID();

    protected void setUpToolbar(Activity activity) {

        mToolbar = findViewById(getToolbarID());
        setSupportActionBar(mToolbar);
        loadPreferences();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

 /*   protected void initializeTwitter() {

        Twitter.initialize(this);
        Twitter.getInstance();
    }*/

    protected void setUpFont() {

        loadPreferences();
/*
        if (textSize.equals(getString(R.string.text_size_small))) {
            setTheme(R.style.TextSizeSmall);
        } else if (textSize.equals(getString(R.string.text_size_medium))) {
            setTheme(R.style.TextSizeMedium);
        } else if (textSize.equals(getString(R.string.text_size_large))) {
            setTheme(R.style.TextSizeLarge);
        }*/
    }

    protected void loadPreferences() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        textSize = sharedPreferences.getString(Constants.KEY_TEXT_SIZE, getString(R.string.text_size_small));
        downloadImages = sharedPreferences.getBoolean(Constants.KEY_DOWNLOAD_IMAGES, true);
        notificationPreference=sharedPreferences.getBoolean(Constants.KEY_NOTIFICATIONS,true);
    }

    protected void sharedPreferencesListener() {

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                recreate();
            }
        };
    }
}
