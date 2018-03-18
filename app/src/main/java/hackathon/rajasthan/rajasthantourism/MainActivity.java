package hackathon.rajasthan.rajasthantourism;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hackathon.rajasthan.rajasthantourism.Adapters.CategoriesAdapter;
import hackathon.rajasthan.rajasthantourism.Adapters.DestinationsAdapter;
import hackathon.rajasthan.rajasthantourism.common.DatabaseFetch;
import hackathon.rajasthan.rajasthantourism.fragments.BannerMainFragment;
import hackathon.rajasthan.rajasthantourism.model.Destinations;
import hackathon.rajasthan.rajasthantourism.model.Type;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerDestinations, recyclerCategories;
    ViewPager bannerViewpager;
    ScrollView contentMain;
    ProgressBar progressBar;
    ConstraintLayout constraintUnsuccessful;
    DestinationsAdapter destinationsAdapter;
    CategoriesAdapter categoriesAdapter;
    private LinearLayoutManager categoriesLayoutManager;
    private LinearLayoutManager destinationsLayoutManager;
    public static ArrayList<Destinations> mDisplayDestinationsList = new ArrayList<>();
    public static ArrayList<Type> mDisplayTypeList = new ArrayList<>();
    private DatabaseFetch databaseFetch;
    private DatabaseReference mDatabase;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentMain = findViewById(R.id.contentMain);
        bannerViewpager = findViewById(R.id.viewpagerMainActivity);
        recyclerCategories = findViewById(R.id.recyclerCategories);
        recyclerDestinations = findViewById(R.id.recyclerDestinations);
        progressBar = findViewById(R.id.progressBar);
        constraintUnsuccessful = findViewById(R.id.constraintUnsuccessful);

        databaseFetch = new DatabaseFetch();
        databaseFetch.populateDisplayListFromDB(MainActivity.this);

        categoriesLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        destinationsLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);

        recyclerCategories.setAdapter(new CategoriesAdapter(mDisplayTypeList, MainActivity.this));
        recyclerDestinations.setAdapter(new DestinationsAdapter(mDisplayDestinationsList, MainActivity.this));
/*
        if(InternetChecker.isOnline(MainActivity.this)){
            if(*//*SQL EMPTY*//*){
                //TODO:DOWNLOAD DATA FROM FIREBASE AND STORE IN SQL
            }
            else{
                //TODO:CHECK VERSION OF DATA IN FIREBASE.
                if(*//*VERSION IN APP LESS THAN VERSION IN FIREBASE*//*){
                    //TODO:DOWNLOAD DATA FROM FIREBASE AND STORE IN SQL
                }
                else{
                    //TODO: USE THE CURRENT DATA IN SQL
                }
            }
        }
        else{
            if(*//*IF DATA PRESENT IN SQL*//*){
                //TODO: USE THE CURRENT DATA IN SQL
            }
            else{
                progressBar.setVisibility(View.GONE);
                constraintUnsuccessful.setVisibility(View.VISIBLE);
            }
        }*/

        //---------------------Viewpager Code begins here--------------------------------------

        mDatabase = FirebaseDatabase.getInstance().getReference("BannerImages");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<String> bannerImgUrls= new ArrayList<>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    bannerImgUrls.add(snap.getValue(String.class));
                }

                adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return new BannerMainFragment(bannerImgUrls.get(position));
                    }

                    @Override
                    public int getCount() {
                        return bannerImgUrls.size();
                    }
                };
                autoScrollViewPager.setAdapter(adapter);
                autoScrollViewPager.startAutoScroll();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }






    public void refreshDestination(){
        destinationsAdapter.notifyItemRangeChanged(0, destinationsAdapter.getItemCount());

        //TODO:REFRESH CATEGORIES ARRAYLIST FOR THE SELECTED CITY USING SQL
        categoriesAdapter.notifyDataSetChanged();
    }

}






