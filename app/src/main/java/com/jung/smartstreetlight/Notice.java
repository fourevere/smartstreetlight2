package com.jung.smartstreetlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

public class Notice extends AppCompatActivity {

    //
    MediaPlayer mp;
    MediaPlayer mpclose;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        //
        mp = MediaPlayer.create(this, R.raw.sss);
        mp.setLooping(false); // 반복재샐
        mpclose = MediaPlayer.create(this, R.raw.close);
        mpclose.setLooping(false);
        //
        this.registerReceiver(wifiEventReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private final BroadcastReceiver wifiEventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
                    mp.start();
                }
                else {
                    mpclose.start();
                }
            }
        }
    };
}
