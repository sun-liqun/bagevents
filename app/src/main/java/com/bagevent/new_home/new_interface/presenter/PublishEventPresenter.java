package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.PublishEventInterface;
import com.bagevent.new_home.new_interface.impl.PublishEventImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnPublishEventListener;
import com.bagevent.new_home.new_interface.new_view.PublishEventView;

/**
 * Created by zwj on 2016/10/10.
 */
public class PublishEventPresenter {
    private PublishEventInterface publishEventInterface;
    private PublishEventView publishEventView;

    public PublishEventPresenter(PublishEventView publishEventView) {
        this.publishEventInterface = new PublishEventImpl();
        this.publishEventView = publishEventView;
    }

    public void publishEvent() {
        publishEventInterface.publish(publishEventView.mContext(),publishEventView.eventId(), publishEventView.userIds(), new OnPublishEventListener() {
            @Override
            public void publishSuccess() {
                publishEventView.publishEventSuccess();
            }

            @Override
            public void publishFailed(String errInfo) {
                publishEventView.publishEventFailed(errInfo);
            }
        });
    }
}
