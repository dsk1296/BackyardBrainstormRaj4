package hackathon.rajasthan.rajasthantourism;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;

import hackathon.rajasthan.rajasthantourism.fragments.Onboarding1;
import hackathon.rajasthan.rajasthantourism.fragments.Onboarding2;
import hackathon.rajasthan.rajasthantourism.utils.Constants;
import hackathon.rajasthan.rajasthantourism.utils.downloadThread;


public class IntroActivity extends AppIntro {
    downloadThread downloadThread1;
    ProgressDialog progress;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(this);
        progress.setMessage("Configuring");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
        checkOnBoardingStatus();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        finishOnboarding();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        finishOnboarding();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    public void finishOnboarding() {
        SharedPreferences preferences = getSharedPreferences(Constants.MY_PREFERENCES, MODE_PRIVATE);

        preferences.edit().putBoolean(Constants.ONBOARDING_COMPLETE, true).apply();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    public void checkOnBoardingStatus() {

        SharedPreferences preferences = getSharedPreferences(Constants.MY_PREFERENCES, MODE_PRIVATE);
        if (!preferences.getBoolean(Constants.ONBOARDING_COMPLETE, false)){
            if(!preferences.getBoolean(Constants.ONDOWNLOAD_INITIATED,false)) {
                downloadThread1 = new downloadThread(this);
                preferences.edit().putBoolean(Constants.ONDOWNLOAD_INITIATED, true).apply();
            }
            addSlide(new Onboarding1());
            addSlide(new Onboarding2());
            setSpecs();
        }
        else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void setSpecs() {
        setBarColor(Color.TRANSPARENT);
        setSeparatorColor(Color.TRANSPARENT);
        setImageNextButton(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_next));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setColorTransitionsEnabled(true);
        // Hide Skip/Done button.
        showSkipButton(true);
        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(40);
    }


    public void updateProgress(){
        count++;
        if(count==5){
            progress.hide();
        }
    }
}
