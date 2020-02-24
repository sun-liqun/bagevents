package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.PostChatMessageData;
import com.bagevent.new_home.new_interface.PostChatMessageInterface;
import com.bagevent.new_home.new_interface.impl.PostMessageImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnPostMessageListener;
import com.bagevent.new_home.new_interface.new_view.PostMessageView;

/**
 * Created by ZWJ on 2017/11/29 0029.
 */

public class PostMessagePresenter {
    private PostChatMessageInterface postChatMessage;
    private PostMessageView postMessageView;

    public PostMessagePresenter(PostMessageView postMessageView) {
        this.postChatMessage = new PostMessageImpl();
        this.postMessageView = postMessageView;
    }

    public void postMessage() {
        postChatMessage.postMessage(postMessageView.mContext(), postMessageView.userId(), postMessageView.eventId(),
                Integer.parseInt(postMessageView.attendeeId()), postMessageView.postSendToken(), postMessageView.receiverToken(), postMessageView.content(), new OnPostMessageListener() {
                    @Override
                    public void postMessageSuccess(PostChatMessageData response) {
                        postMessageView.postMessageSuccess(response);
                    }

                    @Override
                    public void postMessageFailed(String errInfo) {
                        postMessageView.postMessageFailed(errInfo);
                    }
                });
    }
}
