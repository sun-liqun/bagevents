package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.UpdateChatTimeInterface;
import com.bagevent.new_home.new_interface.impl.UpdateChatTimeImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateChatTimeListener;
import com.bagevent.new_home.new_interface.new_view.UpdateChatTimeView;

/**
 * Created by ZWJ on 2017/11/30 0030.
 */

public class UpdateChatTimePresenter {
    private UpdateChatTimeInterface updateChatTime;
    private UpdateChatTimeView updateChatTimeView;

    public UpdateChatTimePresenter(UpdateChatTimeView updateChatTimeView) {
        this.updateChatTime = new UpdateChatTimeImpl();
        this.updateChatTimeView = updateChatTimeView;
    }

    public void updateChatTime() {
        updateChatTime.updateChatTime(updateChatTimeView.mContext(), updateChatTimeView.userId(), updateChatTimeView.contactId(), updateChatTimeView.upDateChatTime(), new OnUpdateChatTimeListener() {
            @Override
            public void upDateTimeSuccess() {
                updateChatTimeView.showUpdateChatTimeSuccess();
            }

            @Override
            public void upDateTimeFailed(String errInfo) {
                updateChatTimeView.showUpdateChatTimeFailed(errInfo);
            }
        });
    }
}
