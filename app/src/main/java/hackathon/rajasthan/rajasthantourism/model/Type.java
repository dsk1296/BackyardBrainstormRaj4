package hackathon.rajasthan.rajasthantourism.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

/**
 * Created by Deepak on 11/18/2017.
 */

public class Type {
    private String name,dpurl;
    @JsonIgnore
    ArrayList<Product> TypeProductList =new ArrayList<>();

    public Type(){

    }

    public Type(String name, String dpurl) {
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

    public ArrayList<Product> getTypeProductList() {
        return TypeProductList;
    }

    public void setTypeProductList(ArrayList<Product> typeProductList) {
        this.TypeProductList = typeProductList;
    }
}
