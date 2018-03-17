package hackathon.rajasthan.rajasthantourism.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import hackathon.rajasthan.rajasthantourism.database.Database;
import hackathon.rajasthan.rajasthantourism.model.Destinations;
import hackathon.rajasthan.rajasthantourism.model.Product;
import hackathon.rajasthan.rajasthantourism.model.Seller;
import hackathon.rajasthan.rajasthantourism.model.Type;


public class downloadThread implements Runnable {
    Thread thread;
    Context mContext;

    boolean download1;

    public static ArrayList<Type> mTypeList=new ArrayList<>();
    public static ArrayList<Destinations> destinationsList =new ArrayList<>();
    public static HashMap<String,ArrayList<Product>> destinationMapping =new HashMap<>();
    public static HashMap<String,ArrayList<Product>> typeMapping =new HashMap<>();
    public static HashMap<String,String> mtypeMap =new HashMap<>();
    public static HashMap<String,String> mDestinationMap =new HashMap<>();

    ArrayList<Product> mainGIList=new ArrayList<>();

    private DatabaseReference mRef1,mRef2,mRef3;

    Database databaseInstance;
    SQLiteDatabase database;

    public downloadThread(Context context) {
        this.mContext=context;
        this.thread = new Thread(this,"downloadThread");
        thread.start();
        databaseInstance = new Database(mContext);
        database = databaseInstance.getWritableDatabase();
    }

    @Override
    public void run() {

        mRef1 = FirebaseDatabase.getInstance().getReference("Products");
        startDownload();
    }

    void startDownload(){

        mRef1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot oneGIData : dataSnapshot.getChildren()) {
                    Product currentGI;
                    ArrayList<Seller> oneGISellersList=new ArrayList<>();
                    currentGI = oneGIData.getValue(Product.class);
                    String currentUID=oneGIData.getKey();
                    currentGI.setUid(currentUID);


                    DataSnapshot sellerData=oneGIData.child("seller");
                    for(DataSnapshot oneSellerData : sellerData.getChildren()){
                        Seller oneSeller=oneSellerData.getValue(Seller.class);
                        oneSeller.setUid(currentUID);
                        oneGISellersList.add(oneSeller);
                    }
                    currentGI.setSeller(oneGISellersList);
                    mainGIList.add(currentGI);
                }
                for (int i = 0; i < mainGIList.size(); i++) {
                    String currentDestination = mainGIList.get(i).getDestination();
                    String currentType = mainGIList.get(i).getType();
                    ArrayList<Product> destinationList = destinationMapping.get(currentDestination);
                    ArrayList<Product> typeList = typeMapping.get(currentType);

                    if (destinationList == null) {
                        destinationList = new ArrayList<>();
                    }

                    destinationList.add(mainGIList.get(i));
                    destinationMapping.put(currentDestination, destinationList);

                }
                for (String oneDestinationName : destinationMapping.keySet()) {
                    Destinations oneState = new Destinations();
                    oneState.setName(oneDestinationName);
                    ArrayList<Product> k = destinationMapping.get(oneDestinationName);
                    oneState.setDestinationProductList(k);
                    destinationsList.add(oneState);
                }
                for (String oneTypeName : typeMapping.keySet()) {
                    Type oneType = new Type();
                    oneType.setName(oneTypeName);
                    ArrayList<Product> k = typeMapping.get(oneTypeName);
                    oneType.setTypeProductList(k);
                    mTypeList.add(oneType);
                }
                Toast.makeText(mContext, "download 1 success", Toast.LENGTH_SHORT).show();
//                HomePage.statesAdapter.notifyDataSetChanged();
//                HomePage.categoryAdapter.notifyDataSetChanged();
                download1 = true;
                shallStartDataLoading();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, "Download 1 cancelled", Toast.LENGTH_SHORT).show();

            }
        });

    }

    void shallStartDataLoading(){
        if(download1){
            for(int i=0;i<mainGIList.size();i++){
                ContentValues contentValuesGI=new ContentValues();
                ContentValues contentValuesGISearch=new ContentValues();

                contentValuesGI.put(Database.PRODUCT_NAME,mainGIList.get(i).getName());
                contentValuesGI.put(Database.PRODUCT_TYPE,mainGIList.get(i).getSubtype());
                contentValuesGI.put(Database.PRODUCT_SUBTYPE,mainGIList.get(i).getType());
                contentValuesGI.put(Database.PRODUCT_DP_URL,mainGIList.get(i).getUrl());
                contentValuesGI.put(Database.PRODUCT_DESTINATION,mainGIList.get(i).getDestination());
                contentValuesGI.put(Database.GI_PRODUCT_UID,mainGIList.get(i).getUid());
                contentValuesGI.put(Database.GI_PRODUCT_DESCRIPTION,mainGIList.get(i).getDescription());

                database.insert(Database.PRODUCT_TABLE,null,contentValuesGI);

                contentValuesGISearch.put(Database.SEARCH_NAME,mainGIList.get(i).getName());
                contentValuesGISearch.put(Database.SEARCH_TYPE,Database.PRODUCT);

                database.insert(Database.SEARCH_TABLE,null,contentValuesGISearch);

                ArrayList<Seller> currentSellerList=mainGIList.get(i).getSeller();
                for(int j=0;j<currentSellerList.size();j++){
                    Seller oneSeller=currentSellerList.get(j);

                    ContentValues contentValuesSeller=new ContentValues();
                    ContentValues contentValuesSellerSearch=new ContentValues();

                    contentValuesSeller.put(Database.SELLER_UID,mainGIList.get(i).getUid());
                    contentValuesSeller.put(Database.SELLER_NAME,oneSeller.getName());
                    contentValuesSeller.put(Database.SELLER_ADDRESS,oneSeller.getAddress());
                    contentValuesSeller.put(Database.SELLER_CONTACT,oneSeller.getContact());
                    contentValuesSeller.put(Database.SELLER_LAT,oneSeller.getLat());
                    contentValuesSeller.put(Database.SELLER_LON,oneSeller.getLon());

                    database.insert(Database.SELLER_TABLE,null,contentValuesSeller);

                    contentValuesSellerSearch.put(Database.SEARCH_NAME,oneSeller.getName());
                    contentValuesSellerSearch.put(Database.SEARCH_TYPE,Database.SELLER);

                    database.insert(Database.SEARCH_TABLE,null,contentValuesSellerSearch);
                }
            }
            for(int i = 0; i< destinationsList.size(); i++){
                Destinations currentState= destinationsList.get(i);
                String dpurl= mDestinationMap.get(currentState.getName());

                ContentValues contentValuesState=new ContentValues();
                ContentValues contentValuesStateSearch=new ContentValues();

                contentValuesState.put(Database.DESTINATION_NAME,currentState.getName());
                contentValuesState.put(Database.DESTINATION_DP_URL,dpurl);

                database.insert(Database.DESTINATION_TABLE,null,contentValuesState);

                contentValuesStateSearch.put(Database.SEARCH_NAME,currentState.getName());
                contentValuesStateSearch.put(Database.SEARCH_TYPE,Database.STATE);

                database.insert(Database.SEARCH_TABLE,null,contentValuesStateSearch);
            }
            for(int i=0;i<mTypeList.size();i++){
                Type currentCategory=mTypeList.get(i);
                String dpurl= mtypeMap.get(currentCategory.getName());

                ContentValues contentValuesCategory=new ContentValues();
                ContentValues contentValuesCategotySearch=new ContentValues();

                contentValuesCategory.put(Database.TYPE_NAME,currentCategory.getName());
                contentValuesCategory.put(Database.TYPE_DP_URL,dpurl);

                database.insert(Database.TYPE_TABLE,null,contentValuesCategory);

                contentValuesCategotySearch.put(Database.SEARCH_NAME,currentCategory.getName());
                contentValuesCategotySearch.put(Database.SEARCH_TYPE,Database.TYPE);

                database.insert(Database.SEARCH_TABLE,null,contentValuesCategotySearch);
            }
        }
    }
}