package com.bagevent.synchro_data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bagevent.util.NetUtil;

/**
 * Created by zwj on 2016/7/21.
 * 利用网络实时监听网络
 */
public class NetWorkBroadcast extends BroadcastReceiver {
    private static final String TAG = "NetWorkBroadcast";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetUtil.isConnected(context)) {
            try {
                Intent intentService = new Intent(context, StartSyncDataService.class);
                context.startService(intentService);
            } catch (Exception e) {

            }
        }
    }


}
