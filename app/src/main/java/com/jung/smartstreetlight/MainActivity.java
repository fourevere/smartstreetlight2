package com.jung.smartstreetlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //위치권한설정
    int nCurrentPermission = 0;
    static final int PERMISSIONS_REQUEST = 0x0000001;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        키해시 불러오는 코드
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
*/

        //위치권한설정
        OnCheckPermission();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel =
                    new NotificationChannel(
                            "back_id",
                            "백그라운드",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );
            notificationChannel.setDescription("백그라운드");
            notificationManager.createNotificationChannel(notificationChannel);

     /*       NotificationChannel notificationChannel2 =
                    new NotificationChannel(
                            "push_message",
                            "푸쉬알림",
                            NotificationManager.IMPORTANCE_HIGH
                    );
            notificationChannel2.setDescription("푸쉬알림");
            notificationManager.createNotificationChannel(notificationChannel2); */

            String NOTIFICATION_ID = "push_message";
            String NOTIFICATION_NAME = "푸쉬알림";
            int IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel2 = new NotificationChannel(NOTIFICATION_ID, NOTIFICATION_NAME, IMPORTANCE);
            notificationManager.createNotificationChannel(channel2);


        }


        //ImageButton navi = (ImageButton) findViewById(R.id.navi);


        ImageButton qna = (ImageButton) findViewById(R.id.qna);

        qna.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Qna.class);
                startActivity(intent);
            }
        });

        ImageButton notice = (ImageButton) findViewById(R.id.notice);

        notice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Notice.class);
                Intent backgroundserviceintent = new Intent(getApplicationContext(), backgroundservice.class);
                startActivity(intent);
                startService(backgroundserviceintent);
                backgroundserviceintent.putExtra("번호", 1);
            }
        });

        ImageButton info = (ImageButton) findViewById(R.id.info);

        info.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Info.class);
                startActivity(intent);
            }
        });
    }



    //위치권한설정
    public void OnCheckPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Toast.makeText(this, "위치권한설정중..",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST :
                if (grantResults.length > 0
                        &&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "설정완료", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "이앱을 사용하기 위해선 위치권한 설정이 필요합니다", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }



}