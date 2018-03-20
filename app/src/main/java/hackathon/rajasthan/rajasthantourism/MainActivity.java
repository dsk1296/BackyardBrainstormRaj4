package hackathon.rajasthan.rajasthantourism;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import hackathon.rajasthan.rajasthantourism.Adapters.CategoriesAdapter;
import hackathon.rajasthan.rajasthantourism.Adapters.DestinationsAdapter;


import hackathon.rajasthan.rajasthantourism.database.Database;

import hackathon.rajasthan.rajasthantourism.fragments.BannerMainFragment;
import hackathon.rajasthan.rajasthantourism.model.Destinations;
import hackathon.rajasthan.rajasthantourism.model.Type;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerDestinations, recyclerCategories;
    AutoScrollViewPager bannerViewpager;
    ScrollView contentMain;
    ProgressBar progressBar;
    ConstraintLayout constraintUnsuccessful;
    DestinationsAdapter destinationsAdapter;
    CategoriesAdapter categoriesAdapter;


    public  List<Type> mDisplayTypeList = new ArrayList<>();
    public  List<Destinations> mDisplayDestinationList = new ArrayList<>();
    private LinearLayoutManager categoriesLayoutManager;
    private LinearLayoutManager destinationsLayoutManager;

    private DatabaseReference mDatabase;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contentMain = findViewById(R.id.contentMain);
        bannerViewpager = findViewById(R.id.viewpagerMainActivity);

        recyclerCategories= findViewById(R.id.recyclerCategories);
        recyclerDestinations= findViewById(R.id.recyclerDestinations);
        categoriesLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        destinationsLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        progressBar = findViewById(R.id.progressBar);
        constraintUnsuccessful = findViewById(R.id.constraintUnsuccessful);


        mDisplayDestinationList = new Database(MainActivity.this).getDestinations();
        mDisplayTypeList = new Database(MainActivity.this).getType();
        recyclerDestinations.setLayoutManager(destinationsLayoutManager);
        recyclerCategories.setLayoutManager(categoriesLayoutManager);

        categoriesAdapter = new CategoriesAdapter(mDisplayTypeList,MainActivity.this);
        destinationsAdapter = new DestinationsAdapter(mDisplayDestinationList,MainActivity.this);
        recyclerCategories.setAdapter(categoriesAdapter);
        recyclerDestinations.setAdapter(destinationsAdapter);




        progressBar.setVisibility(View.GONE);
        contentMain.setVisibility(View.VISIBLE);
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
                bannerViewpager.setAdapter(adapter);
                bannerViewpager.startAutoScroll();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_nearme){
           startActivity(new Intent(MainActivity.this,NearMe.class));

        }
        else if (id == R.id.nav_social_feed){
            startActivity(new Intent(MainActivity.this,SocialFeedActivity.class));

        }
        else if (id == R.id.nav_sos){
            startActivity(new Intent(MainActivity.this,EmergencyActivity.class));

        }
        else if (id == R.id.nav_helpline){
            startActivity(new Intent(MainActivity.this,HelplineActivity.class));

        }
        else if (id == R.id.nav_pined){

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void refreshDestination(String destinationName){

        destinationsAdapter.notifyItemRangeChanged(0, destinationsAdapter.getItemCount());

        //TODO:REFRESH CATEGORIES ARRAYLIST FOR THE SELECTED CITY USING SQL
        List<Type> typeList= new ArrayList<>();
        List<String> TypeNames = new Database(MainActivity.this).getFilteredTypeName(destinationName);
        for (int i=0;i<TypeNames.size();i++){
            Type type = new Database(MainActivity.this).getFilteredTypeObject(TypeNames.get(i));
            typeList.add(type);
        }
        recyclerCategories.setAdapter(new CategoriesAdapter(typeList,MainActivity.this));
        recyclerCategories.invalidate();
        //categoriesAdapter.notifyDataSetChanged();
    }

}






