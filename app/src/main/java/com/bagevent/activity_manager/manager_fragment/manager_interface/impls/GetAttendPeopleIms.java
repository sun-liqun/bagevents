package com.bagevent.activity_manager.manager_fragment.manager_interface.impls;

import android.content.Context;
import android.util.Log;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.callback.CheckInCallback;
import com.bagevent.activity_manager.manager_fragment.callback.GetAttendPeopleCallback;
import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetAttendPeopleInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnCheckInListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetAttendPeopleListener;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.util.LogUtil;
import com.bagevent.util.OkHttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;

/**
 * Created by zwj on 2016/6/6.
 */
public class GetAttendPeopleIms implements GetAttendPeopleInterface {

    private int pageCount = -1;

    /**
     * 首次获得参会人员
     *
     * @param eventId
     * @param page
     * @param onGetAttendPeopleListener
     */
    @Override
    public void getAttendPeople(final Context mContext, final String eventId, final String page, final OnGetAttendPeopleListener onGetAttendPeopleListener) {
        //   Log.e("url",Constants.URL + Constants.ATTENDEE + eventId + Constants.SYNCCALL + "&page=" + page + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET);
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.ATTENDEE + eventId + Constants.SYNCCALL + "&page=" + page +"&with_jdbc=1"+ Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .tag("GetAttendPeopleInfo")
                .build()
                .readTimeOut(2000)
                .writeTimeOut(2000)
                .connTimeOut(2000)
                .execute(new GetAttendPeopleCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                           LogUtil.e("--------getAttendPeople请求取消");
                        }else {
                            onGetAttendPeopleListener.getAttendPeopleFailed(mContext.getResources().getString(R.string.error_gain));
                        }
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        onGetAttendPeopleListener.getAttendPeopleSuccess(response);
                    }
                });

    }

    /**
     * 获得参会人员信息
     * 有更新时间或下拉刷新调用
     *
     * @param eventId
     * @param from_time
     * @param onGetAttendPeopleListener
     */

    @Override
    public void getRefreshAttendPeople(Context mContext, String eventId, String from_time, final String page, final OnGetAttendPeopleListener onGetAttendPeopleListener) {
        //Log.e("GetAttendpeoppleims","timeTmp="+from_time);
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + Constants.ATTENDEE + eventId + Constants.SYNCCALL + "&from_time=" + from_time +"&with_jdbc=1"+ Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .tag("GetAttendPeopleInfo")
                .build()
                .execute(new GetAttendPeopleCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("--------getRefreshAttendPeople请求取消");
                        }else {
                            Log.e("GetAttendpeoppleims", e.getMessage());
                            onGetAttendPeopleListener.getAttendPeopleFailed(R.string.error_gain+"");
                        }
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        onGetAttendPeopleListener.getAttendPeopleSuccess(response);
                    }
                });
    }


    /**
     * 签到
     *
     * @param eventId
     * @param attendId
     * @param checkInStatus
     * @param upDateTime
     * @param onCheckInListener
     */
    @Override
    public void AttendeeCheckIn(final Context mContext, String eventId, final String attendId, String checkInStatus, final String upDateTime, final OnCheckInListener onCheckInListener) {
        //   Log.e("GetAttendpeoppleims","timeTmp="+upDateTime);
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + "event/" + eventId + "/checkin/" + attendId + "/" + checkInStatus + "?update_time=" + upDateTime + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .tag("AttendeeCheckIn")
                .build()
                .execute(new CheckInCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (call.isCanceled()){
                            LogUtil.e("AttendeeCheckIn取消请求");
                        }else {
                            if (e instanceof UnknownHostException || e instanceof ConnectException) {
                                onCheckInListener.checkInFailed(mContext.getResources().getString(R.string.check_network));
                            }else if(e instanceof SocketTimeoutException || e instanceof TimeoutException){
                                onCheckInListener.checkInFailed(mContext.getResources().getString(R.string.net_overtime));
                            } else {
                                onCheckInListener.checkInFailed(mContext.getResources().getString(R.string.modify_failed));
                            }
                        }

                    }

                    @Override
                    public void onResponse(CheckIn response, int id) {
                        //   Log.e("GetAttendPeopleIms", attendId);
                        if (response.getRespObject() == null) {
                            onCheckInListener.checkInSuccess(response);
                        } else {
                            onCheckInListener.checkInFailed(mContext.getResources().getString(R.string.modify_failed));
                        }
                    }
                });

    }

    /**
     * ref_code签到
     *
     * @param eventId
     * @param ref_code
     * @param checkIn
     * @param upDateTime
     * @param onCheckInListener
     */
    @Override
    public void AddAttendeeCheckIn(final Context mContext, String eventId, final String ref_code, String checkIn, final String upDateTime, final OnCheckInListener onCheckInListener) {
//        Log.e("GetAttendPeopleImpls", Constants.URL + "event/" + eventId + "/checkin_by_code/" + ref_code + "/" + checkIn + "?update_time=" + upDateTime + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET);
        OkHttpUtil.okGet(mContext)
                .url(Constants.URL + "event/" + eventId + "/checkin_by_code/" + ref_code + "/" + checkIn + "?update_time=" + upDateTime + Constants.ACCESS_TOKEN + Constants.ACCESS_SERCRET)
                .build()
                .execute(new CheckInCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (e instanceof UnknownHostException || e instanceof ConnectException) {
                            onCheckInListener.checkInFailed(mContext.getResources().getString(R.string.check_network));
                        }else if(e instanceof SocketTimeoutException || e instanceof TimeoutException){
                            onCheckInListener.checkInFailed(mContext.getResources().getString(R.string.net_overtime));
                        } else {
                            EventBus.getDefault().post(new MsgEvent(Common.SIGN_FAILED));
//                            onCheckInListener.checkInFailed(mContext.getResources().getString(R.string.modify_failed));
                        }
                    }

                    @Override
                    public void onResponse(CheckIn response, int id) {
                        //   Log.e("aa",upDateTime);
                        if (response.getRespObject() == null) {
                            onCheckInListener.checkInSuccess(response);
                        } else {
                            onCheckInListener.checkInFailed(mContext.getResources().getString(R.string.modify_failed));
                        }
                    }
                });
    }

}
