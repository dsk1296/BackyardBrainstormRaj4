package hackathon.rajasthan.rajasthantourism.Model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by MY on 15-03-2018.
 */

public class Destination {

    private String name;
    private String dpUrl;

    public static ArrayList<Destination> listDestinations = new ArrayList<>(Arrays.asList(new Destination("Current Location", "")));

    public Destination(String name, String dpUrl) {
        this.name = name;
        this.dpUrl = dpUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDpUrl() {
        return dpUrl;
    }

    public void setDpUrl(String dpUrl) {
        this.dpUrl = dpUrl;
    }
}
