package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.RequireContentData;
import com.bagevent.new_home.new_interface.GetRequireContentInterface;
import com.bagevent.new_home.new_interface.impl.GetRequireContentInterfaceImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnGetRequireContentListener;
import com.bagevent.new_home.new_interface.new_view.RequireContentView;

/**
 * Created by zwj on 2016/9/12.
 */
public class GetRequireContentPresenter {
    private GetRequireContentInterface getRequireContent;
    private RequireContentView requireContentView;

    public GetRequireContentPresenter(RequireContentView requireContentView) {
        this.requireContentView = requireContentView;
        this.getRequireContent = new GetRequireContentInterfaceImpl();
    }

    public void getContent() {
        getRequireContent.getRequireContent(requireContentView.mContext(),requireContentView.userId(), new OnGetRequireContentListener() {
            @Override
            public void showRequireContent(RequireContentData response) {
                requireContentView.showRequireContent(response);
            }

            @Override
            public void showRequireContentFailed(String errInfo) {
                requireContentView.showRequireContentFailed(errInfo);
            }
        });
    }
}
