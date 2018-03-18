package hackathon.rajasthan.rajasthantourism.model;

/**
 * Created by MY on 18-03-2018.
 */

public class EmergencyObject {

    public String IMEI;
    public double lat;
    public double lng;

    public EmergencyObject(String IMEI, double lat, double lng) {
        this.IMEI = IMEI;
        this.lat = lat;
        this.lng = lng;
    }

}
