package hackathon.rajasthan.rajasthantourism.model;

import java.util.ArrayList;
import java.util.List;

import hackathon.rajasthan.rajasthantourism.R;

/**
 * Created by MY on 15-03-2018.
 */

public class Constants {

    public static String currentCity = "";
    public static List<Destinations> destinationsList = new ArrayList<>();
    public static List<Type> typeList = new ArrayList<>();

    public static int getIconId(String type){
        if(type.equals("handicraft")){
            return R.drawable.paintpalette;
        }
        else if(type.equals("food")){
            return R.drawable.restaurant;
        }

        else if(type.equals("services")){
            return R.drawable.customer;
        }

        else if(type.equals("places")){
            return R.drawable.indiagate;
        }
        else{return R.drawable.indiagate;}
    }
}
