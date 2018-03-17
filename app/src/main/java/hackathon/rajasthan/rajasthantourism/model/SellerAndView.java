package hackathon.rajasthan.rajasthantourism.model;

import android.view.View;


public class SellerAndView {
    private Seller seller;
    private View view;

    public SellerAndView(Seller seller, View view) {
        this.seller = seller;
        this.view = view;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
