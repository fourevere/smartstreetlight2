package com.jung.smartstreetlight;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class backgroundservice extends Service {

    //public static final String MESSEAGE_KEY ="";

    private final Handler mHandler = new Handler();
    String rcvIp, rcvPort, rcvPacket;
    ReceiveData rcvServer = new ReceiveData(8266);
    int defaultmp;
    private final Runnable rnb = new Runnable() {
        @Override
        public void run() {
            if(defaultmp == 1) {
                NotificationCompat.Builder mBuilder2 =
                        new NotificationCompat.Builder(backgroundservice.this, "push_message")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setSmallIcon(R.drawable.ic_stat_image2)
                                .setContentTitle("스마트가로등시스템")
                                .setContentText("가로등에서 보행자가 감지되었습니다")
                                .setTimeoutAfter(3000)
                                .setColor(ContextCompat.getColor(backgroundservice.this, R.color.teal_700));
                NotificationManager mNotifyMgr2 =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr2.notify(002, mBuilder2.build());
                mp.start();
            }
            else
            {
                defaultmp = 1;
            }
        }
    };
    //
    public backgroundservice() {
    }
    MediaPlayer mp;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        //서비스에서 가장 먼저 호출(최초한번)
        mp = MediaPlayer.create(this, R.raw.ssyour);
        mp.setLooping(false); // 반복재생
        super.onCreate();

        rcvServer.start();
    }
    class ReceiveData extends Thread {
        //UDP로 패킷을 받았을때 받은 패킷을 UI로 올리기 위해 handler 받아옴
        Handler handler = mHandler;
        DatagramSocket rSocket;

        //UDP 수신 포트를 열기(원하는 port 아무거나)
        public ReceiveData(int port) {
            try {
                //수신 소켓 생성
                rSocket = new DatagramSocket(port);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                while (true) {
              //      stop = 1;
                    //받을 패킷 생성
                    byte[] buf = new byte[1024];
                    //패킷으로 변경 (바이트 버퍼, 버퍼길이)
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    //데이터 수신 대기
                    rSocket.receive(packet);
                    //패킷을 보낸 상대방의 ip를 저장함
                    InetAddress ina = packet.getAddress();
                    rcvIp = ina.toString();
                    //패킷을 보낸 상대방의 port를 저장함
                    int inp = packet.getPort();
                    rcvPort = String.valueOf(inp);
                    //수신받은 데이터를 문자열로 변환
                    rcvPacket = new String(buf);
                    //에코하기 위해 송신용 패킷을 만듦 (받은 패킷, 패킷 길이, 상대방 IP, 상대방 PORT)
                    packet = new DatagramPacket(buf, buf.length, ina, inp);
                    //이 기기로 UDP송신을 한 상대방에게 받은패킷을 돌려줌
                    rSocket.send(packet);
                    //데이터를 받았다면 UI로 표현해주기 위해 runnable 사용
                    handler.post(rnb);
                    //쓰레드를 인터럽트로 종료시키기 위해 sleep을 사용함
                    sleep(20);
                }
            } catch (InterruptedException e) {
                rcvPacket = e.toString();
                handler.post(rnb);
            } catch (Exception e) {
                rcvPacket = e.toString();
                handler.post(rnb);
            }
        }

    }
    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        //   boolean message = intent.getExtras().getBoolean(backgroundservice.MESSEAGE_KEY);
      //  int name = intent.getExtras().getInt("name");
      //      if(name == 1) {
            Intent mMainIntent = new Intent(this,MainActivity.class);
            PendingIntent mPendingIntent = PendingIntent.getActivity(
                    this,1,mMainIntent,PendingIntent.FLAG_UPDATE_CURRENT
            );

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this,"back_id")
                            .setSmallIcon(R.drawable.ic_stat_image2)
                            .setContentTitle("스마트가로등시스템")
                            .setContentIntent(mPendingIntent)
                            .setOngoing(true)
                            .setContentText("현재 주위 보행자알림서비스가 실행중입니다");
            NotificationManager mNotifyMgr =
                    (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(001, mBuilder.build());
            defaultmp = 1;
/*            if(rcvPacket !=null && !rcvPacket.equals("")) {
                mp.start();
                NotificationCompat.Builder mBuilder2 =
                        new NotificationCompat.Builder(this, "push_message")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setSmallIcon(R.drawable.ic_stat_image2)
                                .setContentTitle("스마트가로등시스템")
                                .setContentText("가로등에서 보행자가 감지되었습니다")
                                .setTimeoutAfter(5000)
                                .setColor(ContextCompat.getColor(this, R.color.teal_700));
                //   //                 .setAutoCancel(true)
                NotificationManager mNotifyMgr2 =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr2.notify(002, mBuilder2.build());


            }*/
       //    }
       //    else {
       //        mp.stop();
       //        mp.release();

       //     }
            return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
        NotificationManager mNotifyMgr =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.cancelAll();
        //moveTaskToBack(true);						// 태스크를 백그라운드로 이동
       // finishAndRemoveTask();						// 액티비티 종료 + 태스크 리스트에서 지우기
        android.os.Process.killProcess(android.os.Process.myPid());	// 앱 프로세스 종료
        super.onDestroy();
    }
}
