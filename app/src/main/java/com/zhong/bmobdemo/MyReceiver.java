package com.zhong.bmobdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zhong on 2016/12/14.
 */

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myServiceIntent = new Intent(context, MyService.class);
        context.startService(myServiceIntent);
    }
}