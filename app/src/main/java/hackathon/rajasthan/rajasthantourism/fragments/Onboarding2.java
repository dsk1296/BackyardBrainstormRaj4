package hackathon.rajasthan.rajasthantourism.fragments;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import hackathon.rajasthan.rajasthantourism.IntroActivity;
import hackathon.rajasthan.rajasthantourism.R;


/**
 * Created by karan on 11/10/2017.
 */

public class Onboarding2 extends Fragment implements ISlideBackgroundColorHolder {

    LinearLayout     layoutContainer;
    IntroActivity introActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_onboarding_screen2, container, false);
        layoutContainer= view.findViewById(R.id.onboarding_2_linear_layout);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introActivity=new IntroActivity();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public int getDefaultBackgroundColor() {
        return ContextCompat.getColor(getContext(),R.color.colorPrimary);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if (layoutContainer != null) {
            layoutContainer.setBackgroundColor(backgroundColor);
        }
    }
}