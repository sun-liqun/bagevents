package com.bagevent.jpush;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import com.bagevent.common.Common;
import com.bagevent.new_home.new_activity.JPushActionActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.LocalBroadcastManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.SharedPreferencesUtil;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.VIBRATOR_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by zwj on 2017/4/12.
 */

public class JpushReceiver extends BroadcastReceiver {
    private static final String TAG = "JpushReceiver";

    private NotificationManager mNotificationManager;


    @Override
    public void onReceive(Context context, Intent intent) {

        if (null == mNotificationManager) {
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();
        Log.e(TAG, "action : " + intent.getAction());

        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            receivingNotification(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) { //推送消息被点击了
            Log.e("JPushReceiver", "Notification is clicked");
            String userId = SharedPreferencesUtil.get(context, "userId", "");
            if (!TextUtils.isEmpty(userId)) {
                if (bundle != null) {
                    Intent intent1 = new Intent(context, JPushActionActivity.class);
                    intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent1.putExtras(bundle);
                    context.startActivity(intent1);
                }
            } else {
                if (bundle != null) {
                    int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                    JPushInterface.clearNotificationById(context, notificationId);
                }
            }
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = bundle.getBoolean(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtil.e(connected ? "连接" : "未连接");
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        //     Log.e(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e(TAG, "extras : " + extras);
        if (AppManager.getAppManager().activityStackCount() > 0) {
            Intent msgIntent = new Intent(Common.CHATTING_MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra("notificationId", notificationId);
            msgIntent.putExtra("content", message);
            msgIntent.putExtra("extras", extras);
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        } else {
            Log.e(TAG, "未打开");
        }
    }

    private void processCustomMessage(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.e(TAG, message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e(TAG, extras);

    }

    private void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(500);
        }
    }
}
