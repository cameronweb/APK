package com.example.workcalendar.Presenter.Notify;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.example.workcalendar.GlobalApplication;

public class WIFIStateService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean wifiState=checkWifi();
        GlobalApplication.setWifiConnected(wifiState);

    }
    public boolean checkWifi()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager) GlobalApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network=connectivityManager.getActiveNetwork();
        if(network!=null)
        {
            NetworkCapabilities nc=connectivityManager.getNetworkCapabilities(network);
            if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            {
                return  true;
            }else return false;
        }else return false;

    }


}
