package com.bagevent.register.presenter;

import android.os.CountDownTimer;

import com.bagevent.register.regImpl.GetSMSInterfaceImps;
import com.bagevent.register.reginterface.GetSMSInterface;
import com.bagevent.register.reginterface.clicklistener.GetSMSClickListener;
import com.bagevent.register.registerview.GetSMSCountDown;

/**
 * Created by zwj on 2016/5/27.
 */
public class GetSMSCoutDownPresenter {
    private GetSMSInterface getSMS;
    private GetSMSCountDown getSMSCountDown;
    private TimeCount time;
    public GetSMSCoutDownPresenter(GetSMSCountDown getSMSCountDown) {
        this.getSMSCountDown = getSMSCountDown;
        this.getSMS = new GetSMSInterfaceImps();
    }
    public void startCountDown(){
        getSMS.getSMS(getSMSCountDown.mContext(),getSMSCountDown.getPhoneNum(),getSMSCountDown.getSource(),new GetSMSClickListener() {
            @Override
            public void doCountDown() {
                time = new TimeCount(60000,1000);
                time.start();
            }

            @Override
            public void errInfo(String errInfo) {
                getSMSCountDown.showErrInfo(errInfo);
            }

            @Override
            public void setToastMsg() {
                getSMSCountDown.showToastMsg();
            }
        });
    }
    class TimeCount extends CountDownTimer{
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            //开始倒计时,将textview设为不可点击
            getSMSCountDown.textClickUnable();
            getSMSCountDown.startCutDown(millisUntilFinished);
        }

        @Override
        public void onFinish() {
            //倒计时结束将textview设为可点击
            getSMSCountDown.textClickEnable();
            getSMSCountDown.stopCutDown();
        }
    }
}
