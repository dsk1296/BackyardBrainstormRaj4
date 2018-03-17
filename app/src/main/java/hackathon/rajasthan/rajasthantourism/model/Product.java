package hackathon.rajasthan.rajasthantourism.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Deepak on 11/18/2017.
 */

public class Product implements Serializable {

    private String name,url,type, subtype,destination,description,uid;
    private ArrayList<Seller> seller;
    public Product(){
    }


    public Product(String name, String url, String type, String subtype, String destination, String description,String uid) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.subtype = subtype;
        this.destination = destination;
        this.description = description;
        this.uid = uid;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ArrayList<Seller> getSeller() {
        return seller;
    }

    public void setSeller(ArrayList<Seller> seller) {
        this.seller = seller;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
