package hackathon.rajasthan.rajasthantourism.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import hackathon.rajasthan.rajasthantourism.database.Database;
import hackathon.rajasthan.rajasthantourism.model.Destinations;
import hackathon.rajasthan.rajasthantourism.model.Products;
import hackathon.rajasthan.rajasthantourism.model.Seller;
import hackathon.rajasthan.rajasthantourism.model.Type;


public class downloadThread implements Runnable {
    Thread thread;
    Context mContext;

    boolean download1,download2,download3,download4,download5;

    public  ArrayList<Type> mTypeList=new ArrayList<>();
    public  ArrayList<Seller> mSellerList=new ArrayList<>();
    public  ArrayList<Type> mSubtypeList=new ArrayList<>();
    public  ArrayList<Destinations> destinationsList =new ArrayList<>();
    public static HashMap<String,ArrayList<Products>> destinationMapping =new HashMap<>();
    public static HashMap<String,ArrayList<Products>> typeMapping =new HashMap<>();
    public static HashMap<String,String> mtypeMap =new HashMap<>();
    public static HashMap<String,String> mDestinationMap =new HashMap<>();

    ArrayList<Products> mainGIList=new ArrayList<>();

    private DatabaseReference mRef1,mRef2,mRef3,mRef4,mRef5;



    public downloadThread(Context context) {
        this.mContext=context;
        this.thread = new Thread(this,"downloadThread");
        thread.start();
    }

    @Override
    public void run() {


        startDownload();
    }

    void startDownload(){

        mRef1 = FirebaseDatabase.getInstance().getReference("Products");
        mRef2 = FirebaseDatabase.getInstance().getReference("Destinations");
        mRef3 = FirebaseDatabase.getInstance().getReference("Type");
        mRef5 = FirebaseDatabase.getInstance().getReference("Subtype");
        mRef4 = FirebaseDatabase.getInstance().getReference("Sellers");

        mRef1.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("full",dataSnapshot.toString());
            Products currentProduct;
            for (DataSnapshot oneData : dataSnapshot.getChildren()) {
                Log.d("oneData",oneData.toString());
                 currentProduct =  oneData.getValue(Products.class);

                String currentUID=oneData.getKey();
                currentProduct.setUid(currentUID);

                DataSnapshot sellerData=oneData.child("sellers");
                ArrayList<String> oneSellersList= new ArrayList<>();

                for(DataSnapshot oneSellerData : sellerData.getChildren()){
                    String oneSeller=oneSellerData.getValue(String.class);
                    oneSellersList.add(oneSeller);
                }
                currentProduct.setSeller(oneSellersList);
                mainGIList.add(currentProduct);
            }
    /*        for (int i = 0; i < mainGIList.size(); i++) {

                String currentDestination = mainGIList.get(i).getDestination();
                String currentType = mainGIList.get(i).getType();
                ArrayList<Products> destinationList = destinationMapping.get(currentDestination);
                ArrayList<Products> typeList = typeMapping.get(currentType);

                if (destinationList == null) {
                    destinationList = new ArrayList<>();
                }
                if (typeList == null) {
                    typeList = new ArrayList<>();
                }

                destinationList.add(mainGIList.get(i));
                destinationMapping.put(currentDestination, destinationList);

                typeList.add(mainGIList.get(i));
                typeMapping.put(currentType, typeList);

            }
            for (String oneDestinationName : destinationMapping.keySet()) {
                Destinations oneDestination = new Destinations();
                oneDestination.setName(oneDestinationName);
                ArrayList<Products> k = destinationMapping.get(oneDestinationName);
                oneDestination.setDestinationProductsList(k);
                destinationsList.add(oneDestination);
            }
            for (String oneTypeName : typeMapping.keySet()) {
                Type oneType = new Type();
                oneType.setName(oneTypeName);
                ArrayList<Products> k = typeMapping.get(oneTypeName);
                oneType.setTypeProductsList(k);
                mTypeList.add(oneType);
            }*/
            Toast.makeText(mContext, "download 1 success", Toast.LENGTH_LONG).show();
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
        mRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot oneData : dataSnapshot.getChildren()) {
                    Destinations oneDest = oneData.getValue(Destinations.class);
                    destinationsList.add(oneDest);
                }
                Toast.makeText(mContext, "Download 2 Success", Toast.LENGTH_SHORT).show();
//                HomePage.statesAdapter.notifyDataSetChanged();
                download2 = true;
                shallStartDataLoading();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, "Download 2 Cancelled", Toast.LENGTH_SHORT).show();

            }
        });

        mRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot oneTypeData : dataSnapshot.getChildren()) {

                    Type oneType = oneTypeData.getValue(Type.class);
                    mTypeList.add(oneType);
                }
                Toast.makeText(mContext, "Download 3 Success", Toast.LENGTH_SHORT).show();
//                HomePage.categoryAdapter.notifyDataSetChanged();
                download3 = true;
                shallStartDataLoading();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot oneSellerData : dataSnapshot.getChildren()) {

                    Seller oneSeller = oneSellerData.getValue(Seller.class);
                    mSellerList.add(oneSeller);
                }
                Toast.makeText(mContext, "Download 4 Success", Toast.LENGTH_SHORT).show();
//                HomePage.categoryAdapter.notifyDataSetChanged();
                download4 = true;
                shallStartDataLoading();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mRef5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot oneSubtypeData : dataSnapshot.getChildren()) {

                    Type oneSubtype = oneSubtypeData.getValue(Type.class);
                    mSubtypeList.add(oneSubtype);
                }
                Toast.makeText(mContext, "Download 5 Success", Toast.LENGTH_SHORT).show();
//                HomePage.categoryAdapter.notifyDataSetChanged();
                download5 = true;
                shallStartDataLoading();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void shallStartDataLoading(){
        if(download1&download2&download3&download4&download5){
            for(int i=0;i<mainGIList.size();i++){
                new Database(mContext).addProducts(mainGIList.get(i));

            }
            for (int i=0;i<destinationsList.size();i++){

                new Database(mContext).addDestinations(destinationsList.get(i));
            }
            for (int i=0;i<mTypeList.size();i++){

                new Database(mContext).addTypes(mTypeList.get(i));
            }
            for (int i=0;i<mSellerList.size();i++){

                new Database(mContext).addSellers(mSellerList.get(i));
            }
            for (int i=0;i<mSubtypeList.size();i++){

                new Database(mContext).addSubtypes(mTypeList.get(i));
            }



/*                ArrayList<Seller> currentSellerList=mainGIList.get(i).getSeller();
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
                }*/

  /*          for(int i = 0; i< destinationsList.size(); i++){
                Toast.makeText(mContext,destinationsList.size()+"",Toast.LENGTH_LONG).show();
                Destinations currentState= destinationsList.get(i);
                String dpurl= mDestinationMap.get(currentState.getName());

                ContentValues contentValuesDestination=new ContentValues();
                ContentValues contentValuesStateSearch=new ContentValues();

                contentValuesDestination.put(Database.DESTINATION_NAME,currentState.getName());
                contentValuesDestination.put(Database.DESTINATION_DP_URL,dpurl);

                database.insert(Database.DESTINATION_TABLE,null,contentValuesDestination);

                contentValuesStateSearch.put(Database.SEARCH_NAME,currentState.getName());
                contentValuesStateSearch.put(Database.SEARCH_TYPE,Database.STATE);

                database.insert(Database.SEARCH_TABLE,null,contentValuesStateSearch);
            }

            for(int i=0;i<mTypeList.size();i++){
                Toast.makeText(mContext,mTypeList.size()+"",Toast.LENGTH_LONG).show();
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
            }*/
        }
    }
}