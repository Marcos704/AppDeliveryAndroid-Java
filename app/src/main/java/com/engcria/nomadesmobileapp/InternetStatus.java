package com.engcria.nomadesmobileapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class InternetStatus {
    private Context localContext;
    public InternetStatus(Context context) {
        localContext = context;
    }

    public boolean internetAcess(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo != null){
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                return true;
            }
            if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                return true;
            }
        }else {
            return false;
        }
        return false;
    }
}
