package hackathon.rajasthan.rajasthantourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import hackathon.rajasthan.rajasthantourism.Adapters.SubtypeAdapter;
import hackathon.rajasthan.rajasthantourism.database.Database;
import hackathon.rajasthan.rajasthantourism.model.Constants;
import hackathon.rajasthan.rajasthantourism.model.Type;

public class SubtypeListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView ProductListRecycler;
    LinearLayoutManager linearLayoutManager;
    SubtypeAdapter subtypeAdapter;
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
        getSupportActionBar().setTitle("Subtypes");
        ProductListRecycler = findViewById(R.id.product_list_recycler);
        linearLayoutManager = new LinearLayoutManager(SubtypeListActivity.this,LinearLayoutManager.VERTICAL,false);
        ProductListRecycler.setLayoutManager(linearLayoutManager);
        if (getIntent()!=null){
            TypeName = getIntent().getStringExtra("TypeName");
        }
        if(Constants.currentCity.equals("")){

            typeList = new Database(SubtypeListActivity.this).getAllSubtype(TypeName);
            subtypeAdapter = new SubtypeAdapter(typeList,SubtypeListActivity.this,TypeName);
            ProductListRecycler.setAdapter(subtypeAdapter);


            //TODO: LOAD ALL THE SUBTYPES OF WHOLE RAJASTHAN FROM SQL TO RECYCLERVIEW
        }
        else{

            typeList = new Database(SubtypeListActivity.this).getSubtype(Constants.currentCity,TypeName);
            subtypeAdapter = new SubtypeAdapter(typeList,SubtypeListActivity.this,TypeName);
            ProductListRecycler.setAdapter(subtypeAdapter);
            //TODO: LOAD ALL SUBTYPES OF THE CURRENT CITY FROM SQL TO THE RECYCLERVIEW
        }

    }
}
