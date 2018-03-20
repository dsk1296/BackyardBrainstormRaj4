package hackathon.rajasthan.rajasthantourism.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;


public class Seller implements Serializable {

    private String name,address,contact, sells,desc;
    private double lon,lat;
    @JsonIgnore
    private String uid;

    public Seller(){

    }

    public Seller(String name, String address, String contact, String sells, String desc, double lon, double lat) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.sells = sells;
        this.desc = desc;
        this.lon = lon;
        this.lat = lat;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSells() {
        return sells;
    }

    public void setSells(String sells) {
        this.sells = sells;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
