package hackathon.rajasthan.rajasthantourism.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import hackathon.rajasthan.rajasthantourism.MainActivity;

import hackathon.rajasthan.rajasthantourism.database.Database;
import hackathon.rajasthan.rajasthantourism.model.Destinations;
import hackathon.rajasthan.rajasthantourism.model.Type;

public class DatabaseFetch {

    private Database databaseInstance;
    private SQLiteDatabase database;
        public void populateDisplayListFromDB(Context context) {

            MainActivity.mDisplayDestinationsList.clear();
            MainActivity.mDisplayTypeList.clear();

            databaseInstance = new Database(context);
            database=databaseInstance.getReadableDatabase();

            Cursor categoryCursor=database.query(Database.TYPE_TABLE,null,null,null,null,null,null,null);
            while(categoryCursor.moveToNext()){
                String name=categoryCursor.getString(categoryCursor.getColumnIndex(Database.TYPE_NAME));
                String dpurl=categoryCursor.getString(categoryCursor.getColumnIndex(Database.TYPE_DP_URL));
                Type oneType=new Type(name,dpurl);
                MainActivity.mDisplayTypeList.add(oneType);

            }

            Cursor stateCursor=database.query(Database.DESTINATION_TABLE,null,null,null,null,null,null,null);
            while(stateCursor.moveToNext()){
                String name=stateCursor.getString(stateCursor.getColumnIndex(Database.DESTINATION_NAME));
                String dpurl=stateCursor.getString(stateCursor.getColumnIndex(Database.DESTINATION_DP_URL));

                Destinations oneDest=new Destinations(name,dpurl);
                MainActivity.mDisplayDestinationsList.add(oneDest);
            }
            categoryCursor.close();
            stateCursor.close();
        }

   /* private void fetchGIFromDB() {
        subGIList.clear();
        Cursor cursor;
        String[] s={value};
//        if(type.equals("state")){
//            cursor=database.query(Database.GI_PRODUCT_TABLE,null,Database.GI_PRODUCT_STATE+"=?",s,null,null,null);
//        }
//        else{
//            cursor=database.query(Database.GI_PRODUCT_TABLE,null,Database.GI_PRODUCT_CATEGORY+"=?",s,null,null,null);
//        }

        cursor=database.query(Database.GI_PRODUCT_TABLE,null,null,null,null,null,null);

        while (cursor.moveToNext()){
            String name,detail,category,state,dpurl,uid;

            name=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_NAME));
            detail=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_DETAIL));
            category=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_CATEGORY));
            state=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_STATE));
            dpurl=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_DP_URL));
            uid=cursor.getString(cursor.getColumnIndex(Database.GI_PRODUCT_UID));

            Product oneGI=new Product(name,dpurl,detail,category,state,uid);

            subGIList.add(oneGI);
        }
        cursor.close();
    }*/
}

