package hackathon.rajasthan.rajasthantourism;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import hackathon.rajasthan.rajasthantourism.database.Database;
import hackathon.rajasthan.rajasthantourism.model.Products;

public class ProductActivity extends AppCompatActivity {

    ImageView imgProduct,imgPlaceholder;
    TextView txtName, txtDestination, txtDescription, txtFindSellers;
    String ProductName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        txtName = findViewById(R.id.txtName);
        txtDestination = findViewById(R.id.txtDestination);
        txtDescription = findViewById(R.id.txtDescription);
        txtFindSellers = findViewById(R.id.txtFindSellers);
        imgProduct = findViewById(R.id.imgProducts);
        imgPlaceholder = findViewById(R.id.imgPlaceholder);

        if (getIntent()!=null){
            ProductName = getIntent().getStringExtra("ProductName");
            Products products;
            products = new Database(ProductActivity.this).getCurrentProductObject(ProductName);
            txtName.setText(products.getName());
            txtDestination.setText(products.getDestination());
            txtDescription.setText(products.getDesc());
            Uri uri = Uri.parse(products.getUrl());
            Log.d("url",products.getUrl());
            Glide.with(ProductActivity.this)
                    .load(products.getUrl())
                    .into(imgPlaceholder);
        }

        //TODO: Set the values of the views using the data from the SQL


        txtFindSellers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSellerList = new Intent(ProductActivity.this, SellerListActivity.class);
                //intentSellerList.putExtra(/*Pass the product name here to use in the seller list activity to fetch sellers for the product*/);
                startActivity(intentSellerList);
            }
        });


    }
}
