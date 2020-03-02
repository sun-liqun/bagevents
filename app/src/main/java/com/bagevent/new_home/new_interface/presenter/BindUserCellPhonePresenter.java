package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.BindUserCellPhoneInterface;
import com.bagevent.new_home.new_interface.impl.BindUserCellPhoneImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnBindUserCellPhoneListener;
import com.bagevent.new_home.new_interface.new_view.BindUserCellPhoneView;

/**
 * Created by zwj on 2016/9/12.
 */
public class BindUserCellPhonePresenter {
    private BindUserCellPhoneInterface bindUserCellPhone;
    private BindUserCellPhoneView bindUserCellPhoneView;

    public BindUserCellPhonePresenter(BindUserCellPhoneView bindUserCellPhoneView) {
        this.bindUserCellPhoneView = bindUserCellPhoneView;
        this.bindUserCellPhone = new BindUserCellPhoneImpl();
    }

    public void bindCellPhone() {
        bindUserCellPhone.bindUserCellPhone(bindUserCellPhoneView.mContext(),bindUserCellPhoneView.cellPhone(), bindUserCellPhoneView.randomCode(), bindUserCellPhoneView.userId(), new OnBindUserCellPhoneListener() {
            @Override
            public void bindSuccess() {
                bindUserCellPhoneView.bindSuccess();
            }

            @Override
            public void bindFailed(String errInfo) {
                bindUserCellPhoneView.bindFailed(errInfo);
            }
        });
    }
}
