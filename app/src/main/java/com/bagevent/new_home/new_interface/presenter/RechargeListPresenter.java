package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.MsgRecordData;
import com.bagevent.new_home.new_interface.RechargeListInterface;
import com.bagevent.new_home.new_interface.impl.RechargeListImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnRechargeListListener;
import com.bagevent.new_home.new_interface.new_view.RechargeListView;

/**
 * Created by zwj on 2017/10/23.
 */

public class RechargeListPresenter {
    private RechargeListInterface rechargeList;
    private RechargeListView rechargeListView;

    public RechargeListPresenter(RechargeListView rechargeListView) {
        this.rechargeList = new RechargeListImpl();
        this.rechargeListView = rechargeListView;
    }

    public void getRechargeList() {
        rechargeList.rechargeList(rechargeListView.mContext(), rechargeListView.userId(), rechargeListView.page(), rechargeListView.pageSize(), new OnRechargeListListener() {
            @Override
            public void showRechargeList(MsgRecordData response) {
                rechargeListView.showRechargeList(response);
            }

            @Override
            public void showRechargeListErrInfo(String errInfo) {
                rechargeListView.showRechargeListErrInfo(errInfo);
            }
        });
    }
}
