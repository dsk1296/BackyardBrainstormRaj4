package hackathon.rajasthan.rajasthantourism.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

/**
 * Created by Deepak on 11/18/2017.
 */

public class Destinations {
    private String name,dpurl;
    @JsonIgnore
    ArrayList<Product> destinationProductList =new ArrayList<>();

    public Destinations(){

    }

    public Destinations(String name, String dpurl) {
        this.name = name;
        this.dpurl = dpurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDpurl() {
        return dpurl;
    }

    public void setDpurl(String dpurl) {
        this.dpurl = dpurl;
    }

    public ArrayList<Product> getDestinationProductList() {
        return destinationProductList;
    }

    public void setDestinationProductList(ArrayList<Product> destinationProductList) {
        this.destinationProductList = destinationProductList;
    }
}
