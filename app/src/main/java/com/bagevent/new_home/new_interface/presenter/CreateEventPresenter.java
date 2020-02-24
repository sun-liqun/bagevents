package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.CreateEventData;
import com.bagevent.new_home.new_interface.CreateEventInterface;
import com.bagevent.new_home.new_interface.impl.CreateEventImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnCreateEventListener;
import com.bagevent.new_home.new_interface.new_view.CreateEventView;

/**
 * Created by zwj on 2016/9/19.
 */
public class CreateEventPresenter {
    private CreateEventInterface createEventInterface;
    private CreateEventView createEventView;

    public CreateEventPresenter(CreateEventView createEventView) {
        this.createEventInterface = new CreateEventImpl();
        this.createEventView = createEventView;
    }

    public void create() {
        createEventInterface.create(createEventView.mContext(),createEventView.userIds(), createEventView.type(), new OnCreateEventListener() {
            @Override
            public void createSuccess(CreateEventData response) {
                createEventView.createSuccess(response);
            }

            @Override
            public void createFailed(String errInfo) {
                createEventView.createFailed(errInfo);
            }
        });

    }
}
