package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.data.InvoiceBean;
import com.bagevent.activity_manager.manager_fragment.manager_interface.GetInvoiceInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.GetInvoiceImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.GetInvoiceListListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnGetInvoiceListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetInvoiceListView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetInvoiceView;

/**
 * Created by zwj on 2017/10/26.
 */

public class GetInvoicePresenter {
    private GetInvoiceInterface getInvoice;
    private GetInvoiceView getInvoiceView;
    private GetInvoiceListView getInvoiceListView;

    public GetInvoicePresenter(GetInvoiceView getInvoiceView) {
        this.getInvoice = new GetInvoiceImpl();
        this.getInvoiceView = getInvoiceView;
    }

    public GetInvoicePresenter(GetInvoiceListView invoiceListView) {
        this.getInvoice = new GetInvoiceImpl();
        this.getInvoiceListView = invoiceListView;
    }

    public void getInvoiceListFromTime() {
        getInvoice.getInvoiceListFromTime(getInvoiceListView.mContext(), getInvoiceListView.userId(), getInvoiceListView.eventId() + "", getInvoiceListView.fromTiem(), new GetInvoiceListListener() {
            @Override
            public void invoiceListSuccess(String response) {
                getInvoiceListView.showInvoiceListSuccess(response);
            }

            @Override
            public void invoiceListFailed(String errInfo) {
                getInvoiceListView.showInvoiceListFailed(errInfo);
            }
        });
    }

    public void getInvoiceList() {
        getInvoice.getInvoiceList(getInvoiceListView.mContext(), getInvoiceListView.userId(), getInvoiceListView.eventId() + "",getInvoiceListView.page(), new GetInvoiceListListener() {
            @Override
            public void invoiceListSuccess(String resposne) {
                getInvoiceListView.showInvoiceListSuccess(resposne);
            }

            @Override
            public void invoiceListFailed(String errInfo) {
                getInvoiceListView.showInvoiceListFailed(errInfo);
            }
        });
    }

    public void getInvoice() {
        getInvoice.getInvoice(getInvoiceView.mContext(), getInvoiceView.eventId(), getInvoiceView.orderNumber(), new OnGetInvoiceListener() {

            @Override
            public void showInvoiceSuccess(InvoiceBean response) {
                getInvoiceView.showInvoiceSuccess(response);
            }

            @Override
            public void showInvoiceFailed(String errInfo) {
                getInvoiceView.showInvoiceFailed(errInfo);
            }
        });
    }
}
