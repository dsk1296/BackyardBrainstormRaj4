package hackathon.rajasthan.rajasthantourism.model;

import java.io.Serializable;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Deepak on 11/18/2017.
 */

public class Products implements Serializable {

    private String name,url,type,subtype,destination,desc;
    @JsonIgnore
    private String uid;
    @JsonIgnore
    private ArrayList<String> seller;

    public Products() {

    }

    public Products(String name, String subtype, String destination, String type, String url, String desc, String uid) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.subtype = subtype;
        this.destination = destination;
        this.desc = desc;
        this.uid = uid;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<String> getSeller() {
        return seller;
    }

    public void setSeller(ArrayList<String> seller) {
        this.seller = seller;
    }
}
