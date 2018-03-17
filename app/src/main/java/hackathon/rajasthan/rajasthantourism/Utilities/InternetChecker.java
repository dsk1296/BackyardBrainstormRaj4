package hackathon.rajasthan.rajasthantourism.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by MY on 16-03-2018.
 */

public class InternetChecker {

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
