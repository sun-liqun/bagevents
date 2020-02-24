package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.WithdrawRecordData;
import com.bagevent.new_home.new_interface.WithdrawRecordInterface;
import com.bagevent.new_home.new_interface.impl.WithdrawRecordImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnWithdrawRecordListener;
import com.bagevent.new_home.new_interface.new_view.WithdrawRecordView;

/**
 * Created by ZWJ on 2017/12/6 0006.
 */

public class WithdrawRecordPresenter {
    private WithdrawRecordInterface withdrawRecord;
    private WithdrawRecordView withdrawRecordView;

    public WithdrawRecordPresenter(WithdrawRecordView withdrawRecordView) {
        this.withdrawRecord = new WithdrawRecordImpl();
        this.withdrawRecordView = withdrawRecordView;
    }

    public void getWithdrawRecord() {
        withdrawRecord.withdrawRecord(withdrawRecordView.mContext(), withdrawRecordView.userId(), withdrawRecordView.page(), withdrawRecordView.pageSize(), new OnWithdrawRecordListener() {
            @Override
            public void showWithdrawRecordSuccess(WithdrawRecordData response) {
                withdrawRecordView.showWithDrawRecordSuccess(response);
            }

            @Override
            public void showWithddrawRecordFailed(String errInfo) {
                withdrawRecordView.showWithDrawRecordFailed(errInfo);
            }
        });
    }
}
