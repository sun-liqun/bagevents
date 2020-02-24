package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.FindUnreadData;
import com.bagevent.new_home.new_interface.FindExistUnRead;
import com.bagevent.new_home.new_interface.impl.FindExistUnReadImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnFindExistUnReadListener;
import com.bagevent.new_home.new_interface.new_view.FindExistUnReadView;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public class FindExistUnReadPresenter {
    private FindExistUnRead findExistUnRead;
    private FindExistUnReadView findExistUnReadView;

    public FindExistUnReadPresenter(FindExistUnReadView findExistUnReadView) {
        this.findExistUnRead = new FindExistUnReadImpl();
        this.findExistUnReadView = findExistUnReadView;
    }

    public void findExistUnRead() {
        findExistUnRead.findExistUnRead(findExistUnReadView.mContext(), findExistUnReadView.userId(), new OnFindExistUnReadListener() {
            @Override
            public void findExistUnRead(FindUnreadData respnse) {
                findExistUnReadView.findExistUnReadMsg(respnse);
            }

            @Override
            public void findExistUnReadFailed(String errInfo) {
                findExistUnReadView.findExistUnReadMsgFailed(errInfo);
            }
        });
    }
}
