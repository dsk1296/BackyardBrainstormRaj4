package hackathon.rajasthan.rajasthantourism.Model;

/**
 * Created by MY on 15-03-2018.
 */

public class Category {

    private String name;
    private String bgUrl;

    public Category(String name, String bgUrl) {
        this.name = name;
        this.bgUrl = bgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }
}
