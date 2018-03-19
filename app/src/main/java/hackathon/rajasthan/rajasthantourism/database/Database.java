package hackathon.rajasthan.rajasthantourism.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import hackathon.rajasthan.rajasthantourism.model.Constants;
import hackathon.rajasthan.rajasthantourism.model.Destinations;
import hackathon.rajasthan.rajasthantourism.model.Products;
import hackathon.rajasthan.rajasthantourism.model.Type;



/**
 * Created by Deepak on 10/29/2017.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="tourismdb.db";
    private static final int DB_VER = 1;
    private Context context;

    public Database(Context context) {
        super(context,DB_NAME,null,DB_VER);
        this.context = context.getApplicationContext();
    }

    public void addProducts(Products products){
        SQLiteDatabase db  = getReadableDatabase();
        String query = String.format("INSERT INTO Products(productName,productType,productSubtype,productDestination,productUrl,productDescription) VALUES('%s','%s','%s','%s','%s','%s');",
                products.getName(),
                products.getType(),
                products.getSubtype(),
                products.getDestination(),
                products.getUrl(),
                products.getDesc());

        db.execSQL(query);
    }
    public void addDestinations(Destinations destinations){
        SQLiteDatabase db  = getReadableDatabase();
        String query = String.format("INSERT INTO Destinations(DestinationName,DestinationUrl) VALUES('%s','%s');",
                destinations.getName(),
                destinations.getDpurl());
        db.execSQL(query);
    }
    public void addTypes(Type type){
        SQLiteDatabase db  = getReadableDatabase();
        String query = String.format("INSERT INTO Type(TypeName,TypeUrl) VALUES('%s','%s');",
                type.getName(),
                type.getDpurl());
        db.execSQL(query);
    }


    public List<Destinations> getDestinations(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"DestinationName,DestinationUrl"};
        String sqlTable= "Destinations";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Destinations> result =new ArrayList<>();
        if(c!=null && c.moveToFirst()){
            do {
                result.add(new Destinations(
                        c.getString(c.getColumnIndex("DestinationName")),
                        c.getString(c.getColumnIndex("DestinationUrl"))
                        ));}

            while (c.moveToNext());

        }
        return result;
    }
    public List<Type> getType(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"TypeName,TypeUrl"};
        String sqlTable= "Type";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Type> result =new ArrayList<>();
        if(c!=null && c.moveToFirst()){
            do {
                result.add(new Type(
                        c.getString(c.getColumnIndex("TypeName")),
                        c.getString(c.getColumnIndex("TypeUrl"))
                ));}

            while (c.moveToNext());

        }
        return result;
    }

    public List<String> getFilteredTypeName(String destinationName){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"DISTINCT productType"};
        String sqlTable= "Products";
        String selection = "productDestination = ?";
        String[] selectionArgs = {destinationName};

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,selection,selectionArgs,null,null,null);
        final List<String> result =new ArrayList<>();
        if(c != null && c.moveToFirst()){
            do {
                result.add(c.getString(c.getColumnIndex("productType")));}
            while (c.moveToNext());
        }
        return result;

    }
    public Type getFilteredTypeObject(String typeName){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"TypeName,TypeUrl"};
        String sqlTable= "Type";
        String selection = "TypeName = ?";
        String[] selectionArgs = {typeName};

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,selection,selectionArgs,null,null,null);
        Type result = new Type();
        if(c != null && c.moveToFirst()){
            do {
                result = new Type(
                        c.getString(c.getColumnIndex("TypeName")),
                        c.getString(c.getColumnIndex("TypeUrl")));}
            while (c.moveToNext());
        }
        return result;

    }



    public List<Type> getSubtype(String destinationName,String typeName){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"DISTINCT productSubtype"};
        String sqlTable= "Products";
        String selection = "productDestination = ? and productType = ?";
        String[] selectionArgs = {destinationName,typeName};

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,selection,selectionArgs,null,null,null);
        final List<Type> result =new ArrayList<>();
        if(c != null && c.moveToFirst()){
            do {
                result.add(new Type(
                        c.getString(c.getColumnIndex("productSubtype")),
                        c.getString(c.getColumnIndex("productSubtype"))

                ));}
            while (c.moveToNext());
        }
        return result;

    }
    public List<Type> getAllSubtype(String typeName){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"DISTINCT productSubtype"};
        String sqlTable= "Products";
        String selection = "productType = ?";
        String[] selectionArgs = {typeName};

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,selection,selectionArgs,null,null,null);
        final List<Type> result =new ArrayList<>();
        if(c != null && c.moveToFirst()){
            do {
                result.add(new Type(
                        c.getString(c.getColumnIndex("productSubtype")),
                        c.getString(c.getColumnIndex("productSubtype"))

                ));}
            while (c.moveToNext());
        }
        return result;

    }

    public List<Type> getProductList(String destName,String typeName,String subtype){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"productName,productUrl"};
        String sqlTable= "Products";
        String selection = "productDestination = ? and productType = ? and productSubtype = ?";
        String[] selectionArgs = {destName,typeName,subtype};

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,selection,selectionArgs,null,null,null);
        final List<Type> result =new ArrayList<>();
        if(c != null && c.moveToFirst()){
            do {
                result.add(new Type(
                        c.getString(c.getColumnIndex("productName")),
                        c.getString(c.getColumnIndex("productUrl"))

                ));}
            while (c.moveToNext());
        }
        return result;

    }
    public List<Type> getProductAllList(String typeName,String subtype){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"productName,productUrl"};
        String sqlTable= "Products";
        String selection = "productType = ? and productSubtype = ?";
        String[] selectionArgs = {typeName,subtype};

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,selection,selectionArgs,null,null,null);
        final List<Type> result =new ArrayList<>();
        if(c != null && c.moveToFirst()){
            do {
                result.add(new Type(
                        c.getString(c.getColumnIndex("productName")),
                        c.getString(c.getColumnIndex("productUrl"))

                ));}
            while (c.moveToNext());
        }
        return result;

    }







    //-----------------------------------------------old------------------------------------------------------------------------------

    /*public void addtoBox(Box box){
        SQLiteDatabase db  = getReadableDatabase();
        String query = String.format("INSERT INTO BoxDetails(foodHW,foodName,foodPrice,foodQuantity,foodDiscount,foodCategory,foodMenuID) VALUES('%s','%s','%s','%s','%s','%s','%s');",
                box.getFoodHW(),
                box.getFoodName(),
                box.getFoodPrice(),
                box.getFoodQuantity(),
                box.getFoodDiscount(),
                box.getFoodCategory(),
                box.getFoodMenuID());

        db.execSQL(query);
    }
    public void cleanCart(){
        SQLiteDatabase db  = getReadableDatabase();
        String query = String.format("DELETE FROM BoxDetails");
                db.execSQL(query);

    }

    public List<Box> getHWsBox(String name){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"foodHW","foodName","foodPrice","foodQuantity","foodDiscount","foodCategory","foodMenuID"};
        String sqlTable= "BoxDetails";
        String selection = "foodHW = ?";
        String[] selectionArgs = {name};

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,selection,selectionArgs,null,null,null);

        final List<Box> result =new ArrayList<>();
        if(c.moveToFirst()){
            do {
                result.add(new Box(
                        c.getString(c.getColumnIndex("foodHW")),
                        c.getString(c.getColumnIndex("foodName")),
                        c.getString(c.getColumnIndex("foodPrice")),
                        c.getString(c.getColumnIndex("foodQuantity")),
                        c.getString(c.getColumnIndex("foodDiscount")),
                        c.getString(c.getColumnIndex("foodCategory")),
                        c.getString(c.getColumnIndex("foodMenuID"))

                ));}
            while (c.moveToNext());
        }
        return result;

}

    public List<CartModel> getHWs(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"DISTINCT foodHW"};
        String sqlTable= "BoxDetails";
            qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<CartModel> result =new ArrayList<>();
        if(c.moveToFirst()){
            do {
                result.add(new CartModel(
                        c.getString(c.getColumnIndex("foodHW"))

                        ));}

            while (c.moveToNext());

        }
        return result;

    }

    public void RemoveBox(String name){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM BoxDetails WHERE foodHW='"+name+"'");
        db.execSQL(query);
    }

    public void RemoveBoxItem(String foodMenuID,String foodname){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM BoxDetails WHERE foodMenuID='"+foodMenuID+"'"+"AND foodName" +
                " ='"+foodname +"'");
        db.execSQL(query);
    }

    public String getBoxPrice(String hwname){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();


        String[] sqlSelect = {"SUM(foodPrice * foodQuantity) As BoxPrice"};
        String sqlTable= "BoxDetails";
        String selection = "foodHW = ?";
        String[] selectionArgs = {hwname};
        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,selection,selectionArgs,null,null,null);

        String result="";
        if( c != null && c.moveToFirst() ){
            result = c.getString(c.getColumnIndex("BoxPrice"));
            c.close();
        }
        return result;



    }
    public String getTotalPrice(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();


        String[] sqlSelect = {"SUM(foodPrice * foodQuantity) As BoxPrice"};
        String sqlTable= "BoxDetails";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        String result="0";
        if( c != null && c.moveToFirst() ){
            result = c.getString(c.getColumnIndex("BoxPrice"));
            c.close();
        }
        return result;

    }

    public void checkQuantity() {
        SQLiteDatabase db = getReadableDatabase();
        final SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"foodHW", "foodName","foodQuantity", "foodCategory", "foodMenuID"};
        String sqlTable = "BoxDetails";

        qb.setTables(sqlTable);
        final Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);
        final DatabaseReference mDatabaseFood = FirebaseDatabase.getInstance().getReference();
        final int[] count = {0};
        final boolean[] quantityChanged = {false};

            if (c != null && c.moveToFirst()) {
               do {
                   final String hwname=c.getString(c.getColumnIndex("foodHW"));
                   final String foodname=c.getString(c.getColumnIndex("foodName"));
                   ;
                   final DatabaseReference currentFood = mDatabaseFood.child("menu").child(c.getString(c.getColumnIndex("foodMenuID"))).child(c.getString(c.getColumnIndex("foodCategory"))).child(c.getString(c.getColumnIndex("foodKey"))).child("availability");
                   final int quantity = Integer.parseInt(c.getString(c.getColumnIndex("foodQuantity")));

                   currentFood.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           count[0]++;
                           final int CurrentAvailability = Integer.parseInt(dataSnapshot.getValue(String.class));
                           if (quantity>CurrentAvailability) {
                               changeQuantity(hwname, foodname, "" + CurrentAvailability);
                               Toast.makeText(context, foodname + " quantity changed", Toast.LENGTH_LONG).show();
                               quantityChanged[0] = true;
                           }
                           if (c.getCount() ==count[0]){
                               if (quantityChanged[0])
                                    Cart.LoadAlertDialog();
                               else
                               {    OrderManagement orderManagement = new  OrderManagement(context);
                                    orderManagement.ReserveQuantities();
                                    Cart.Order();
                                    Cart.LoadPaymentActivity();
                                   }
                           }}
                       @Override
                       public void onCancelled(DatabaseError databaseError) {
                           Toast.makeText(context, databaseError.toString(), Toast.LENGTH_LONG).show();}
                   });
               }
               while (c.moveToNext());
           }
    }



    public List<Box> prepareOrder(String name){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"foodHW","foodName","foodPrice","foodQuantity","foodDiscount","foodCategory","foodMenuID"};
        String sqlTable= "BoxDetails";
        String selection = "foodHW = ?";
        String[] selectionArgs = {name};

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,selection,selectionArgs,null,null,null);
        final List<Box> result =new ArrayList<>();
        if(c != null && c.moveToFirst()){
            do {
                result.add(new Box(
                        c.getString(c.getColumnIndex("foodHW")),
                        c.getString(c.getColumnIndex("foodName")),
                        c.getString(c.getColumnIndex("foodPrice")),
                        c.getString(c.getColumnIndex("foodQuantity")),
                        c.getString(c.getColumnIndex("foodDiscount")),
                        c.getString(c.getColumnIndex("foodCategory")),
                        c.getString(c.getColumnIndex("foodMenuID"))

                ));}
            while (c.moveToNext());
        }
        return result;

    }
    public void changeQuantity(String foodMenuID, String foodname, String quantity){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE BoxDetails SET  foodQuantity = '"+ quantity+"' WHERE (foodMenuID ='"+foodMenuID +"' AND foodName = '"+ foodname+"');");
        db.execSQL(query);
    }

    public List<Box> getCompleteCart(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"foodHW","foodName","foodPrice","foodQuantity","foodDiscount","foodCategory","foodMenuID"};
        String sqlTable= "BoxDetails";


        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);
        final List<Box> result =new ArrayList<>();
        if(c != null && c.moveToFirst()){
            do {
                result.add(new Box(
                        c.getString(c.getColumnIndex("foodHW")),
                        c.getString(c.getColumnIndex("foodName")),
                        c.getString(c.getColumnIndex("foodPrice")),
                        c.getString(c.getColumnIndex("foodQuantity")),
                        c.getString(c.getColumnIndex("foodDiscount")),
                        c.getString(c.getColumnIndex("foodCategory")),
                        c.getString(c.getColumnIndex("foodMenuID"))

                ));}
            while (c.moveToNext());
        }
        return result;

    }

    public String getItemQuantity(String hwMenuID,String foodname){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();


        String[] sqlSelect = {"foodQuantity"};
        String sqlTable= "BoxDetails";
        String selection = "foodMenuID = ? and foodName = ?";
        String[] selectionArgs = {hwMenuID,foodname};
        qb.setTables(sqlTable);

        Cursor c = qb.query(db,sqlSelect,selection,selectionArgs,null,null,null);
         String result ="0";
        if(c != null && c.moveToFirst()){
            do {
                result = c.getString(c.getColumnIndex("foodQuantity"));}
            while (c.moveToNext());
        }
        return result;

    }*/}
