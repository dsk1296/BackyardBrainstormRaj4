package hackathon.rajasthan.rajasthantourism;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import hackathon.rajasthan.rajasthantourism.Adapters.CategoriesAdapter;
import hackathon.rajasthan.rajasthantourism.Adapters.DestinationsAdapter;
import hackathon.rajasthan.rajasthantourism.Model.Constants;
import hackathon.rajasthan.rajasthantourism.Utilities.InternetChecker;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentMain = findViewById(R.id.contentMain);
        bannerViewpager = findViewById(R.id.viewpagerMainActivity);
        recyclerCategories= findViewById(R.id.recyclerCategories);
        recyclerDestinations= findViewById(R.id.recyclerDestinations);
        progressBar = findViewById(R.id.progressBar);
        constraintUnsuccessful = findViewById(R.id.constraintUnsuccessful);

        categoriesLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        destinationsLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);

        if(InternetChecker.isOnline(MainActivity.this)){
            if(/*SQL EMPTY*/){
                //TODO:DOWNLOAD DATA FROM FIREBASE AND STORE IN SQL
            }
            else{
                //TODO:CHECK VERSION OF DATA IN FIREBASE.
                if(/*VERSION IN APP LESS THAN VERSION IN FIREBASE*/){
                    //TODO:DOWNLOAD DATA FROM FIREBASE AND STORE IN SQL
                }
                else{
                    //TODO: USE THE CURRENT DATA IN SQL
                }
            }
        }
        else{
            if(/*IF DATA PRESENT IN SQL*/){
                //TODO: USE THE CURRENT DATA IN SQL
            }
            else{
                progressBar.setVisibility(View.GONE);
                constraintUnsuccessful.setVisibility(View.VISIBLE);
            }
        }
    }

    public void refreshDestination(){
        destinationsAdapter.notifyItemRangeChanged(0, destinationsAdapter.getItemCount());

        //TODO:REFRESH CATEGORIES ARRAYLIST FOR THE SELECTED CITY USING SQL
        categoriesAdapter.notifyDataSetChanged();
    }
}






