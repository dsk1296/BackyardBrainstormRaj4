package hackathon.rajasthan.rajasthantourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import hackathon.rajasthan.rajasthantourism.model.Constants;

public class ProductListActivity extends AppCompatActivity {

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        toolbar = findViewById(R.id.toolbarCategoryActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");

        if(Constants.currentCity.equals("")){
            //TODO: LOAD ALL THE SUBTYPES OF WHOLE RAJASTHAN FROM SQL TO RECYCLERVIEW
        }
        else{
            //TODO: LOAD ALL SUBTYPES OF THE CURRENT CITY FROM SQL TO THE RECYCLERVIEW
        }

    }
}
