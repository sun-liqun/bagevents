package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.GetReqireSmsCodeInterface;
import com.bagevent.new_home.new_interface.impl.GetRequireSmsCodeImpl;
import com.bagevent.new_home.new_interface.listenerInterface.GetRequireSmsCodeListener;
import com.bagevent.new_home.new_interface.new_view.GetRequireSmsCodeView;
import com.bagevent.register.data.GetSMSData;

/**
 * Created by zwj on 2016/11/9.
 */
public class GetRequireSmsCodePresenter {
    private GetReqireSmsCodeInterface getReqireSmsCodeInterface;
    private GetRequireSmsCodeView getRequireSmsCodeView;

    public GetRequireSmsCodePresenter(GetRequireSmsCodeView getRequireSmsCodeView) {
        this.getReqireSmsCodeInterface = new GetRequireSmsCodeImpl();
        this.getRequireSmsCodeView = getRequireSmsCodeView;
    }


    public void getSms() {
        getReqireSmsCodeInterface.getSmsCode(getRequireSmsCodeView.mContext(),getRequireSmsCodeView.phoneNum(), getRequireSmsCodeView.source(), new GetRequireSmsCodeListener() {
            @Override
            public void getSmsCodeSuccess(GetSMSData response) {
                getRequireSmsCodeView.showSmsCode(response);
            }

            @Override
            public void getSmsCodeFailed(String errInfo) {
                getRequireSmsCodeView.showSmsFailed(errInfo);
            }
        });
    }
}
