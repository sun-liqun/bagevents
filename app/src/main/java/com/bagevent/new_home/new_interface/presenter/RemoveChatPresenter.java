package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.RemoveChatInterface;
import com.bagevent.new_home.new_interface.impl.RemoveChatImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnRemoveChatListener;
import com.bagevent.new_home.new_interface.new_view.RemoveChatView;

/**
 * Created by ZWJ on 2017/12/3 0003.
 */

public class RemoveChatPresenter {
    private RemoveChatInterface removeChat;
    private RemoveChatView removeChatView;

    public RemoveChatPresenter(RemoveChatView removeChatView) {
        this.removeChat = new RemoveChatImpl();
        this.removeChatView = removeChatView;
    }

    public void removeChat() {
        removeChat.removeChat(removeChatView.mContext(), removeChatView.userId(), removeChatView.contactId(), new OnRemoveChatListener() {
            @Override
            public void removeChat() {
                removeChatView.removeChatSuccess();
            }

            @Override
            public void removeChatFailed(String errInfo) {
                removeChatView.removeChatFailed(errInfo);
            }
        });
    }
}
