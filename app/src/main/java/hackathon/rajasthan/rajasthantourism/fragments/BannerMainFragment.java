package hackathon.rajasthan.rajasthantourism.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import hackathon.rajasthan.rajasthantourism.R;

/**
 * Created by MY on 18-03-2018.
 */

public class BannerMainFragment extends Fragment {

    private View v;
    private ImageView imgBanner;
    private String imgUrl;

    public BannerMainFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public BannerMainFragment(String imgUrl){
        this.imgUrl = imgUrl;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_banner_main, container, false);
        imgBanner = v.findViewById(R.id.imgBanner);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.viewpager_placeholder);
        Glide.with(getActivity()).load(imgUrl).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade()).into(imgBanner);
        return v;
    }

}
