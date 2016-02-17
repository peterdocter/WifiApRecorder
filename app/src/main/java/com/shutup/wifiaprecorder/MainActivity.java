package com.shutup.wifiaprecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_info)
    Button btnInfo;
    @Bind(R.id.btn_scan)
    Button btnScan;

    private WifiManager mWifiManager;
    private ConnectivityManager connectivityManager;
    private List<ScanResult> mScanResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        registerWifiScanResultsReceiver();
    }

    private void registerWifiScanResultsReceiver() {
        mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver,intentFilter);
    }

    @OnClick({R.id.btn_info, R.id.btn_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_info:
                getWifiInfo();
                break;
            case R.id.btn_scan:
                getWifiAround();
                break;
        }
    }

    private void getWifiAround() {

//        SSID: master, BSSID: f0:b4:29:63:f1:97, MAC: 02:00:00:00:00:00, Supplicant state: COMPLETED, RSSI: -51, Link speed: 433Mbps, Frequency: 5785MHz, Net ID: 32, Metered hint: false, score: 60
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
//        Toast.makeText(this, wifiInfo.toString(), Toast.LENGTH_SHORT).show();
        mWifiManager.startScan();
    }

    private void getWifiInfo() {
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Toast.makeText(this, wifiInfo.toString(), Toast.LENGTH_SHORT).show();
    }
    /**
     * 广播接收，监听网络
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            // wifi已成功扫描到可用wifi。
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                mScanResults = mWifiManager.getScanResults();
                Toast.makeText(context, "mScanResults.size():" + mScanResults.size(), Toast.LENGTH_SHORT).show();
            }
            //系统wifi的状态
            else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int wifiState = intent.getIntExtra(
                        WifiManager.EXTRA_WIFI_STATE, 0);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_ENABLED:
                        mWifiManager.startScan();
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        break;
                }
            }
        }
    };
}
