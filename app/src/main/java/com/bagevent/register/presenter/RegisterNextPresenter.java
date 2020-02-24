package com.bagevent.register.presenter;

import com.bagevent.register.regImpl.NextRegisterInterfaceImps;
import com.bagevent.register.reginterface.clicklistener.NextOnClickListener;
import com.bagevent.register.reginterface.RegisterNextInterface;
import com.bagevent.register.registerview.NextRegisterViewInterface;

/**
 * Created by zwj on 2016/5/27.
 */
public class RegisterNextPresenter {
    private RegisterNextInterface registerNext;
    private NextRegisterViewInterface registerView;

    public RegisterNextPresenter(NextRegisterViewInterface registerViewInterface) {
        this.registerView = registerViewInterface;
        this.registerNext = new NextRegisterInterfaceImps();
    }
    public void registerNext(){
        registerNext.checkPhone(registerView.mContext(),registerView.getPhoneNum(), new NextOnClickListener() {
            @Override
            public void checkSuccess() {
                registerView.doSuccess();
            }

            @Override
            public void checkFail(String errInfo) {
                registerView.showFailInfo(errInfo);
            }

            @Override
            public void checkIsExist(String errInfo) {
                registerView.showPhoneNumIsExist(errInfo);
                //修改测试使用
                //registerView.doSuccess();
            }
        });
    }
}
