package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.ShareInfoData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetShareInfoInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.GetShareInfoImpls;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetShareInfoListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetShareInfoView;

/**
 * Created by zwj on 2016/12/5.
 */
public class GetShareInfoPresenter {
    private GetShareInfoInterface getShareInfoInterface;
    private GetShareInfoView getShareInfoView;

    public GetShareInfoPresenter(GetShareInfoView getShareInfoView) {
        this.getShareInfoInterface = new GetShareInfoImpls();
        this.getShareInfoView = getShareInfoView;
    }

    public void showShareInfo() {
        getShareInfoInterface.shareInfo(getShareInfoView.mContext(), getShareInfoView.getEventId(), getShareInfoView.userId(), new OnGetShareInfoListener() {
            @Override
            public void shareInfo(ShareInfoData response) {
                getShareInfoView.showShareInfo(response);
            }

            @Override
            public void shareErr(String errInfo) {
                getShareInfoView.showShareInfoErr(errInfo);
            }
        });
    }
}
