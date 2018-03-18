package hackathon.rajasthan.rajasthantourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import hackathon.rajasthan.rajasthantourism.Adapters.CategoriesAdapter;
import hackathon.rajasthan.rajasthantourism.database.Database;
import hackathon.rajasthan.rajasthantourism.model.Constants;
import hackathon.rajasthan.rajasthantourism.model.Type;

public class ProductListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView ProductListRecycler;
    LinearLayoutManager linearLayoutManager;
    CategoriesAdapter categoriesAdapter;
    List<Type> typeList = new ArrayList<>();
    String TypeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        toolbar = findViewById(R.id.toolbarCategoryActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");
        ProductListRecycler = findViewById(R.id.product_list_recycler);
        linearLayoutManager = new LinearLayoutManager(ProductListActivity.this,LinearLayoutManager.VERTICAL,false);

        if (getIntent()!=null){
            TypeName = getIntent().getStringExtra("TypeName");
        }
        typeList = new Database(ProductListActivity.this).getSubtype(Constants.currentCity,TypeName);
        categoriesAdapter = new CategoriesAdapter(typeList,ProductListActivity.this);
        ProductListRecycler.setLayoutManager(linearLayoutManager);
        ProductListRecycler.setAdapter(categoriesAdapter);






        if(Constants.currentCity.equals("")){


            //TODO: LOAD ALL THE SUBTYPES OF WHOLE RAJASTHAN FROM SQL TO RECYCLERVIEW
        }
        else{
            //TODO: LOAD ALL SUBTYPES OF THE CURRENT CITY FROM SQL TO THE RECYCLERVIEW
        }

    }
}
