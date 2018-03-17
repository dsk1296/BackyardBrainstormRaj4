package hackathon.rajasthan.rajasthantourism.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Deepak on 11/18/2017.
 */

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME="brainStorm.db";
    private static final int DB_VER = 1;


    public static final String PRODUCT ="Products";
    public static final String STATE ="Destinations";
    public static final String TYPE ="Type";
    public static final String SELLER ="Sellers";


    public static final String PRODUCT_TABLE ="product_table";
    public static final String PRODUCT_SUBTYPE ="product_subtype";
    public static final String PRODUCT_NAME ="product_name";
    public static final String PRODUCT_DESTINATION ="product_destination";
    public static final String PRODUCT_TYPE ="product_type";
    public static final String PRODUCT_DP_URL ="product_url";
    public static final String GI_PRODUCT_UID="product_uid";
    public static final String GI_PRODUCT_DESCRIPTION="product_description";
    //public static final String GI_PRODUCT_WEB_URL="gi_product_web_url";

    public static final String DESTINATION_TABLE ="destination_table";
    public static final String DESTINATION_NAME ="destination_name";
    public static final String DESTINATION_DP_URL ="destination_dp_url";

    public static final String TYPE_TABLE ="type_table";
    public static final String TYPE_NAME ="type_name";
    public static final String TYPE_DP_URL ="type_dpurl";

    public static final String SELLER_TABLE ="seller_table";
    public static final String SELLER_UID ="seller_uid";
    public static final String SELLER_NAME ="seller_name";
    public static final String SELLER_CONTACT ="seller_contact";
    public static final String SELLER_ADDRESS ="seller_address";
    public static final String SELLER_LAT ="seller_lat";
    public static final String SELLER_LON ="seller_lon";

    public static final String SEARCH_TABLE ="search_table";
    public static final String SEARCH_NAME ="search_name";
    public static final String SEARCH_ID ="_id";
    public static final String SEARCH_TYPE ="search_type";

    public Database(Context context) {
        super(context,DB_NAME,null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableProducts="Create table "+ PRODUCT_TABLE +"( "+
                GI_PRODUCT_UID+" text, "+ PRODUCT_NAME +" text, "
                +GI_PRODUCT_DESCRIPTION+" text, "+
                PRODUCT_SUBTYPE +" text, "+ PRODUCT_DP_URL +" text, "+
                PRODUCT_TYPE +" text,"+ PRODUCT_DESTINATION +" text);";

        String createTableDestination="Create table "+ DESTINATION_TABLE +"( "+
                DESTINATION_DP_URL +" text, "+ DESTINATION_NAME +" text);";

        String createTableType="Create table "+ TYPE_TABLE +"( "+
                TYPE_NAME +" text,"+ TYPE_DP_URL +" text);";

        String createTableSeller="Create table "+ SELLER_TABLE +"( "+
                SELLER_UID +" text, "+ SELLER_NAME +" text, "+
                SELLER_ADDRESS +" text, "+ SELLER_CONTACT +" text,"+
                SELLER_LAT +" real, "+ SELLER_LON +" real);";

        String createTableGISearch="Create table "+ SEARCH_TABLE +"( "+
                SEARCH_ID +" integer primary key autoincrement, "+ SEARCH_NAME +" text, "+
                SEARCH_TYPE +" text);";


        db.execSQL(createTableType);
        db.execSQL(createTableProducts);
        db.execSQL(createTableSeller);
        db.execSQL(createTableDestination);
        db.execSQL(createTableGISearch);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}