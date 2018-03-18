package hackathon.rajasthan.rajasthantourism;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtName, txtDestination, txtDescription, txtFindSellers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        txtName = findViewById(R.id.txtName);
        txtDestination = findViewById(R.id.txtDestination);
        txtDescription = findViewById(R.id.txtDescription);
        txtFindSellers = findViewById(R.id.txtFindSellers);
        imgProduct = findViewById(R.id.imgProduct);

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
