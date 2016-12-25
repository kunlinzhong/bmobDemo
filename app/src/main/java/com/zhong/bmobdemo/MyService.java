package com.zhong.bmobdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

import com.zhong.tool.LogCatUtil;
import com.zhong.tool.getLocation;
import java.util.Date;


/**
 * Created by zhong on 2016/12/14.
 */

public class MyService extends Service {
    private Context con;
    private SharedPreferences sp;
    public final static String TAG = "com.comodin.service.AlarmUploadLatLonService";
    public MyService() {}

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new LogCatUtil().writerLog("service onCreate ！");
        con = MyService.this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new LogCatUtil().writerLog("service onStartCommand ！");
        alarmStartService();
        return super.onStartCommand(intent, flags, startId);
    }
    private void alarmStartService(){
        sp = this.getSharedPreferences("config", 0);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 10* 1000; // 10秒
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, MyReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        new getLocation(con,sp.getString("deviceId"," ")).getLocation();
    }

    @Override
    public void onDestroy() {
        new LogCatUtil().writerLog("service onDestroy ！");
        SharedPreferences serviceLog_sp = getSharedPreferences("serviceLog",0);
        SharedPreferences.Editor edit= serviceLog_sp.edit();
        edit.putBoolean("uploadService",false);
        edit.putLong("uploadServiceStopTime", new Date().getTime());
        edit.commit();
        super.onDestroy();
    }
}
