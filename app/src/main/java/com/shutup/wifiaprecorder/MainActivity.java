package com.shutup.wifiaprecorder;

import android.net.wifi.WifiManager;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private ConnectivityManagerCompat connectivityManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
