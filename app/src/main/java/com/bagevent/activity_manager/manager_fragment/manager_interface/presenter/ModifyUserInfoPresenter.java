package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.ModifyData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.ModifyUserInfoInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.ModifyUserInfoImpls;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnModifyUserInfoListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ModifyUserInfoView;

/**
 * Created by zwj on 2016/7/1.
 */
public class ModifyUserInfoPresenter {
    private ModifyUserInfoInterface modifyUserInfo;
    private ModifyUserInfoView modifyUserInfoView;

    public ModifyUserInfoPresenter(ModifyUserInfoView modifyUserInfoView) {
        this.modifyUserInfoView = modifyUserInfoView;
        this.modifyUserInfo = new ModifyUserInfoImpls();
    }

    public void modifyInfo(){
        modifyUserInfo.modifyUserInfo(modifyUserInfoView.mContext(),modifyUserInfoView.eventId() +"", modifyUserInfoView.attendId(), modifyUserInfoView.infoMap(), new OnModifyUserInfoListener() {
            @Override
            public void modifyUserInfoSuccess(ModifyData modifyData) {
                modifyUserInfoView.showModifySuccess(modifyData);
            }

            @Override
            public void modifyUserInoFailed(ModifyData modifyData) {
                modifyUserInfoView.showModifyFailed(modifyData);
            }
        });
    }
}
