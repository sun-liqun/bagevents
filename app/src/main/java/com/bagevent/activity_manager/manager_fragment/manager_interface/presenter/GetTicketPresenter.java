package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetTicketInfoInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.GetTicketInfoImps;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetTicketListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetTicketInfoView;

/**
 * Created by zwj on 2016/6/2.
 */
public class GetTicketPresenter {
    private GetTicketInfoInterface getTicketInfo;
    private GetTicketInfoView getTicketInfoView;

    public GetTicketPresenter(GetTicketInfoView getTicketInfoView) {
        this.getTicketInfoView = getTicketInfoView;
        this.getTicketInfo = new GetTicketInfoImps();
    }
    public void getTicket(){
        getTicketInfo.getTicket(getTicketInfoView.getContext(),getTicketInfoView.getEventId(), new OnGetTicketListener() {
            @Override
            public void getTicketSuccess(TicketInfo ticketInfo) {
                getTicketInfoView.showTicketInfo(ticketInfo);
            }

            @Override
            public void getTicketFailed(String errInfo) {
                getTicketInfoView.showErrInfo(errInfo);
            }
        });
    }
    public void hideProgress(){
        getTicketInfoView.hideProgress();
    }
    public void showProgress(){
        getTicketInfoView.showProgress();
    }
}
