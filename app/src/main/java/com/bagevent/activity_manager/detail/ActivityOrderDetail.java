package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.adapter.ActivityOrderDetailAdapter;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.data.InvoiceBean;
import com.bagevent.activity_manager.manager_fragment.data.SingleOrderData;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ConfirmOrderPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetFormTypePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetInvoicePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ModifyOrderPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.QuitTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ReSendTicketPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.SingleAttendeePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.SingleOrderPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.UpdateNotesPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AuditOrderView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ConfirmOrderView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetFormTypeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetInvoiceView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetTicketInfoView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.QuitTicketFromOrderView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ReSendTicketFromOrderView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SingOrderView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SingleAttendeeView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpdateOrderNotesView;
import com.bagevent.common.Common;
import com.bagevent.db.Attends;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.EventList;
import com.bagevent.db.EventList_Table;
import com.bagevent.db.Order;
import com.bagevent.db.Order_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.dbutil.SyncSingleAttendeeUtil;
import com.bagevent.util.dbutil.SyncSingleOrderUtil;
import com.bagevent.util.dbutil.SyncTicketUtil;
import com.bagevent.view.RecyclerViewItemDecoration;
import com.bagevent.view.listener.CancelListener;
import com.bagevent.view.listener.ConfirmListener;
import com.bagevent.view.mydialog.EditDialog;
import com.bagevent.view.mydialog.PointDialog;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.gudong.loading.LoadingView;

/**
 * Created by zwj on 2017/10/26.
 */

public class ActivityOrderDetail extends BaseActivity implements GetInvoiceView, PopupMenu.OnMenuItemClickListener, ReSendTicketFromOrderView, BaseQuickAdapter.OnItemClickListener, ConfirmOrderView,
        QuitTicketFromOrderView, UpdateOrderNotesView, AuditOrderView, SingOrderView, SingleAttendeeView, GetTicketInfoView, GetFormTypeView {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rv_order_detail)
    RecyclerView rvOrderDetail;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.ll_resend_ticket)
    AutoLinearLayout llResendTicket;
    @BindView(R.id.ll_confirm_pays)
    AutoLinearLayout llConfirmPays;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.ll_audit_refuse)
    AutoLinearLayout llAuditRefuse;
    @BindView(R.id.ll_audit_pass)
    AutoLinearLayout llAuditPass;
    @BindView(R.id.ll_audit_action)
    AutoLinearLayout llAuditAction;
    @BindView(R.id.ll_order_action)
    AutoLinearLayout llOrderAction;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.ll_order_bottom)
    AutoLinearLayout llOrderBottom;
    @BindView(R.id.loading)
    LoadingView loading;
    private View view;

    private ActivityOrderDetailAdapter detailAdapter;
    private GetInvoicePresenter invoicePresenter;
    private ReSendTicketPresenter reSendTicketPresenter;
    private ConfirmOrderPresenter confirmOrderPresenter;
    private QuitTicketPresenter quitTicketPresenter;
    private UpdateNotesPresenter notesPresenter;
    private ModifyOrderPresenter modifyOrderPresenter;
    private SingleOrderPresenter singleOrderPresenter;
    private SingleAttendeePresenter singleAttendeePresenter;
    private GetFormTypePresenter getFormTypePresenter;
    private GetTicketPresenter getTicketPresenter;
    private InvoiceBean.RespObjectBean mInvoice;
    private List<Attends> attendss = new ArrayList<Attends>();
    private int position;//用于修改订单后，更改订单列表中该订单的信息
    private int eventId;
    private int orderId;
    private String buyerName;
    private String buyerCellphone;
    private String buyerEmail;
    private String notes;
    private String orderNumber;
    private int payWay;
    private int payStatus;
    private String userId;
    private String accountType;
    private int audit = 0;
    private int buyingGroupId=0;
    private int buyingGroupState=0;
    private int invoiceType = -1;//发票类型
    private int isSendEmail = 0;//退票是否发送邮件
    private int isFromAudit = 0;//0 = 普通订单列表进入，1 = 审核列表进入,3 = 聊天界面进入
    private HeaderViewBind headerView;
    private PopupMenu menu;
    private NormalAlertDialog sendTicketDialog;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_order_detail);
        ButterKnife.bind(this);
        getIntentValue();
        initView();
        isLoading();
        getHeaderView();
        headerView = new HeaderViewBind(view);
        if (isFromAudit == 0 || isFromAudit == 1) {
            initData();
            showPopMenu(llRightClick);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        rvOrderDetail.setVisibility(View.GONE);
    }

    private void loadingFinished() {
        llLoading.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        rvOrderDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        EventList eventList = new Select().from(EventList.class).where(EventList_Table.userId.is(Integer.parseInt(userId))).and(EventList_Table.eventId.is(eventId)).querySingle();
        Intent intent = new Intent(this, AttendPeopleDetailInfo.class);
        intent.putExtra("eventId", eventId);
        intent.putExtra("attendId", attendss.get(position).attendId);
        //  Log.e("orderDetail attendId",attendss.get(position).attendId+"");
        intent.putExtra("ref_code", attendss.get(position).refCodes);
        intent.putExtra("position", position);
        intent.putExtra("remark", attendss.get(position).notes);
        if (eventList != null) {
            intent.putExtra("stType", eventList.sType);
        }
        if (attendss.get(position).audits == 1) {
            intent.putExtra("detailType", 1);
        } else {
            intent.putExtra("detailType", 0);
        }
        intent.putExtra("orderId", attendss.get(position).orderIds);
        startActivity(intent);
    }


    class HeaderViewBind {
        @BindView(R.id.tv_buer_name)
        TextView tvBuerName;
        @BindView(R.id.tv_buer_email)
        TextView tvBuerEmail;
        @BindView(R.id.tv_buyer_cellphone)
        TextView tvBuyerCellphone;
        @BindView(R.id.tv_payway)
        TextView tvPayway;
        @BindView(R.id.ll_common_invoice)
        AutoLinearLayout llCommonInvoice;
        @BindView(R.id.ll_vatman_invoice)
        AutoLinearLayout llVatmanInvoice;
        @BindView(R.id.tv_reciver_type)
        TextView tvReciverType;
        @BindView(R.id.tv_use_companycode)
        TextView tvUseCompanycode;
        @BindView(R.id.tv_obtain_type)
        TextView tvObtainType;
        @BindView(R.id.tv_invoice_companyname)
        TextView tvInvoiceCompanyname;
        @BindView(R.id.ll_invoice_companyname)
        AutoLinearLayout llInvoiceCompanyname;
        @BindView(R.id.tv_invoice_recognition)
        TextView tvInvoiceRecognition;
        @BindView(R.id.ll_invoice_recognition)
        AutoLinearLayout llInvoiceRecognition;
        @BindView(R.id.tv_invoice_remark)
        TextView tvInvoiceRemark;
        @BindView(R.id.ll_invoice)
        AutoLinearLayout llInvoice;
        @BindView(R.id.ll_common_remark)
        AutoLinearLayout llCommonRemark;
        @BindView(R.id.tv_vatman_remark)
        TextView tvVatmanRemark;
        @BindView(R.id.ll_vatman_remark)
        AutoLinearLayout llVatmanRemark;
        @BindView(R.id.tv_invoice_type)
        TextView tvInvoiceType;
        @BindView(R.id.tv_invoice_Item)
        TextView tvInvoiceItem;
        @BindView(R.id.tv_vatman_company)
        TextView tvVatmanCompany;
        @BindView(R.id.ll_vatman_company)
        AutoLinearLayout llVatmanCompany;
        @BindView(R.id.tv_vatman_recognicode)
        TextView tvVatmanRecognicode;
        @BindView(R.id.ll_vatman_recognicode)
        AutoLinearLayout llVatmanRecognicode;
        @BindView(R.id.tv_vatman_regis_addr)
        TextView tvVatmanRegisAddr;
        @BindView(R.id.ll_vatman_regis_addr)
        AutoLinearLayout llVatmanRegisAddr;
        @BindView(R.id.tv_vatman_cellphone)
        TextView tvVatmanCellphone;
        @BindView(R.id.ll_vatman_cellphone)
        AutoLinearLayout llVatmanCellphone;
        @BindView(R.id.tv_vatman_bank)
        TextView tvVatmanBank;
        @BindView(R.id.ll_vatman_bank)
        AutoLinearLayout llVatmanBank;
        @BindView(R.id.tv_vatman_bankcode)
        TextView tvVatmanBankcode;
        @BindView(R.id.ll_vatman_bankcode)
        AutoLinearLayout llVatmanBankcode;
        @BindView(R.id.ll_invoice_more)
        AutoLinearLayout llInvoiceMore;
        @BindView(R.id.ll_invoice_header2)
        AutoLinearLayout llInvoiceHeader2;
        @BindView(R.id.ll_invoice_heder)
        AutoLinearLayout llInvoiceHeder;
        @BindView(R.id.iv_invoice_more)
        ImageView ivInvoiceMore;
        @BindView(R.id.tv_common_recivername)
        TextView tvCommonRecivername;
        @BindView(R.id.tv_common_recivercellphone)
        TextView tvCommonRecivercellphone;
        @BindView(R.id.tv_common_reciveraddress)
        TextView tvCommonReciveraddress;
        @BindView(R.id.ll_common_receives)
        AutoLinearLayout llCommonReceive;
        @BindView(R.id.tv_vatman_recivernames)
        TextView tvVatmanRecivernames;
        @BindView(R.id.tv_vatnan_recivercellphone)
        TextView tvVatnanRecivercellphone;
        @BindView(R.id.tv_vatman_reciveraddr)
        TextView tvVatmanReciveraddr;
        @BindView(R.id.ll_vatman_receive)
        AutoLinearLayout llVatmanReceive;
        @BindView(R.id.tv_common_companycode)
        TextView tvCommonCompanycode;
        @BindView(R.id.ll_common_usecompanycode)
        AutoLinearLayout llCommonUsecompanycode;
        @BindView(R.id.tv_vatman_companycode)
        TextView tvVatmanCompanycode;
        @BindView(R.id.ll_vatman_usecompanycode)
        AutoLinearLayout llVatmanUsecompanycode;
        @BindView(R.id.ll_modify_invoice)
        AutoLinearLayout llModifyInvoice;
        @BindView(R.id.tv_remark_mark)
        TextView tvRemarkMark;
        @BindView(R.id.tv_remark)
        TextView tvRemark;

        public HeaderViewBind(View headerView) {
            ButterKnife.bind(this, headerView);
        }

        @OnClick({R.id.ll_invoice_more, R.id.ll_modify_invoice})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.ll_invoice_more:
                    ActivityOrderDetail.this.onInvoiceMore(view);
                    break;
                case R.id.ll_modify_invoice:
                    ActivityOrderDetail.this.onModifyInvoice();
                    break;
            }
        }
    }

    private void confirmPay() {
        final PointDialog pointDialog = new PointDialog(this);
        pointDialog.setText(getString(R.string.confirm_receivables));
        pointDialog.setConfirmListener(new ConfirmListener() {
            @Override
            public void confirm(View view) {
                Toast.makeText(ActivityOrderDetail.this, R.string.confirm_receipt, Toast.LENGTH_SHORT).show();
                if (NetUtil.isConnected(ActivityOrderDetail.this)) {
                    confirmOrderPresenter = new ConfirmOrderPresenter(ActivityOrderDetail.this);
                    confirmOrderPresenter.confirm();
                } else {
                    Toast.makeText(ActivityOrderDetail.this, R.string.check_network, Toast.LENGTH_SHORT).show();
                }
                pointDialog.dismiss();
            }
        });

        pointDialog.setCancelListener(new CancelListener() {
            @Override
            public void cancel(View view) {
                pointDialog.dismiss();
            }
        });
        pointDialog.show();
    }

    public void onModifyInvoice() {
        if (invoiceType == 0) {
            Intent intent = new Intent(this, ModifyCommonInvoice.class);
            Bundle bundle = new Bundle();
            bundle.putInt("eventId", eventId);
            bundle.putString("orderNumber", orderNumber);
            bundle.putSerializable("invoice", (Serializable) mInvoice);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (invoiceType == 1) {
            Intent intent = new Intent(this, ModifyVatmanInvoice.class);
            Bundle bundle = new Bundle();
            bundle.putInt("eventId", eventId);
            bundle.putString("orderNumber", orderNumber);
            bundle.putSerializable("invoice", (Serializable) mInvoice);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void onInvoiceMore(View view) {
        setInvoiceView(mInvoice);
        if (headerView.llInvoiceHeader2.getVisibility() == View.VISIBLE) {
            Glide.with(this).load(R.drawable.xiala).into(headerView.ivInvoiceMore);
            headerView.llInvoiceHeader2.setVisibility(View.GONE);
            headerView.llCommonInvoice.setVisibility(View.GONE);
            headerView.llVatmanInvoice.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(R.drawable.shouqi).into(headerView.ivInvoiceMore);
            headerView.llInvoiceHeader2.setVisibility(View.VISIBLE);
            if (mInvoice.getInvoiceType() == 0) {
                headerView.llCommonInvoice.setVisibility(View.VISIBLE);
            } else {
                headerView.llVatmanInvoice.setVisibility(View.VISIBLE);
            }
        }
        if (detailAdapter != null) {
            detailAdapter.notifyDataSetChanged();

        }
    }

    @OnClick({R.id.ll_title_back, R.id.iv_right2, R.id.ll_right_click, R.id.ll_resend_ticket, R.id.ll_confirm_pays, R.id.ll_audit_refuse, R.id.ll_audit_pass, R.id.ll_add_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_right_click:
                if (payStatus == 1) {
                    if(buyingGroupId==0){
                        menu.show();
                    }else{
                        if(buyingGroupState==1){
                            menu.show();
                        }else{
                            removeMenuItem();
                            menu.show();
                        }
                    }

                } else if (payStatus == 10) {
                    Toast.makeText(this, R.string.back_order, Toast.LENGTH_SHORT).show();
                } else if (payStatus == 13) {
                    Toast.makeText(this, R.string.order_refuse, Toast.LENGTH_SHORT).show();
                } else {
                    removeMenuItem();
                    menu.show();
                }
                break;
            case R.id.ll_resend_ticket: //重发电子发票
                showResendTicketDialog();
                break;
            case R.id.ll_confirm_pays:
                confirmPay();
                break;
            case R.id.ll_audit_refuse:
                if (NetUtil.isConnected(this)) {
                    audit = 3;
                    modifyOrderPresenter = new ModifyOrderPresenter(this);
                    modifyOrderPresenter.auditOrder();
                } else {
                    Toast.makeText(this, R.string.check_your_net, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_audit_pass:
                if (NetUtil.isConnected(this)) {
                    audit = 2;
                    modifyOrderPresenter = new ModifyOrderPresenter(this);
                    modifyOrderPresenter.auditOrder();
                } else {
                    Toast.makeText(this,R.string.check_your_net, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_add_note:
                final EditDialog dialog = new EditDialog(this);
                if (!TextUtils.isEmpty(notes)) {
                    dialog.setText(notes);
                }
                dialog.setConfirmListener(new ConfirmListener() {
                    @Override
                    public void confirm(View view) {
                        if (!TextUtils.isEmpty(dialog.getText())) {
                            if (NetUtil.isConnected(ActivityOrderDetail.this)) {
                                notes = dialog.getText();
                                notesPresenter = new UpdateNotesPresenter(ActivityOrderDetail.this);
                                notesPresenter.updateOrderNotes();
                            } else {
                                Toast.makeText(ActivityOrderDetail.this,R.string.check_network, Toast.LENGTH_SHORT).show();

                            }
                            dialog.dismiss();
                        } else {
                            Toast.makeText(ActivityOrderDetail.this, R.string.et_remarks_hint, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_modify_orderinfo:
                Intent intent = new Intent(this, ModifyOrder.class); //修改订单信息
                intent.putExtra("orderId", orderId);
                intent.putExtra("eventId", eventId);
                intent.putExtra("position", position);
                startActivity(intent);
                break;
            case R.id.menu_quit_ticket:
                if (payStatus == 1) {
                    final PointDialog dialog = new PointDialog(this);
                    dialog.setText(getString(R.string.confirm_receivables1));
                    dialog.setCancelText(getString(R.string.cancel));
                    dialog.setConfirmText(getString(R.string.confirm));
                    dialog.setConfirmListener(new ConfirmListener() {
                        @Override
                        public void confirm(View v) {
                            if (NetUtil.isConnected(ActivityOrderDetail.this)) {
                                isSendEmail = 1;
                                quitTicketPresenter = new QuitTicketPresenter(ActivityOrderDetail.this);
                                quitTicketPresenter.quitTicketFromOrderView();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(ActivityOrderDetail.this, R.string.check_network1, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    dialog.setCancelListener(new CancelListener() {
                        @Override
                        public void cancel(View v) {
                            dialog.dismiss();

                        }
                    });

                    dialog.show();
                } else {
                    Toast.makeText(this,R.string.confirm_receivables2, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.menu_add_remark:
                // Toast.makeText(this, "添加备注", Toast.LENGTH_SHORT).show();

                break;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals(Common.MODIFY_ORDER)) {
            Order order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(orderId)).querySingle();
            if (order != null) {
                headerView.tvBuerName.setText(order.buyerName);
                headerView.tvBuerEmail.setText(order.buyerEmail);
                headerView.tvBuyerCellphone.setText(order.buyerCellphone);
                if (detailAdapter != null) {
                    detailAdapter.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(this,R.string.no_find_order, Toast.LENGTH_SHORT).show();
            }
        } else if (event.mMsg.equals(Common.MODIFY_INVOICE)) {
            if (NetUtil.isConnected(this)) {
                invoicePresenter = new GetInvoicePresenter(this);
                invoicePresenter.getInvoice();
            } else {
                Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
            }
        } else if (event.mMsg.equals(Common.SINGLE_DATA)) {
            Order order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(orderId)).querySingle();
            List<Attends> attend = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.orderIds.is(orderId)).queryList();
            if (order != null && attend.size() > 0) {
                buyerCellphone = order.buyerCellphone;
                buyerName = order.buyerName;
                buyerEmail = order.buyerEmail;
                notes = order.notes;
                orderNumber = order.orderNumber;
                payWay = order.payWay;
                accountType = order.accountType;
                payStatus = order.payStatus;
                audit = order.audit;
                buyingGroupId=order.buyingGroupId;
                buyingGroupState=order.buyingGroupState;
                LogUtil.i("-----------buyingGroupId:"+buyingGroupId+"buyingGroupState:"+buyingGroupState);
                initData();
                showPopMenu(llRightClick);
            }
        }
    }

    public void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    private void removeMenuItem() {
        if (menu != null) {
            menu.getMenu().findItem(R.id.menu_quit_ticket).setVisible(false);
        }
    }

    private void addMenuItem() {
        if (menu != null) {
            menu.getMenu().findItem(R.id.menu_quit_ticket).setVisible(true);
        }
    }

    private void showPopMenu(View v) {
        menu = new PopupMenu(this, v);
        menu.getMenuInflater().inflate(R.menu.order_menu, menu.getMenu());
        menu.setOnMenuItemClickListener(this);
        setForceShowIcon(menu);
        if ((payStatus == 10 || (payStatus == 6 && payWay > 10)) || (isFromAudit == 1)) {
            removeMenuItem();
        }
    }

    private void setInvoiceGone() {
        headerView.llInvoiceHeder.setVisibility(View.GONE);
        headerView.llInvoice.setVisibility(View.GONE);
        headerView.llCommonInvoice.setVisibility(View.GONE);
        headerView.llVatmanInvoice.setVisibility(View.GONE);
    }

    private void setInvoiceVisible() {
        headerView.llInvoiceHeder.setVisibility(View.VISIBLE);
        headerView.llInvoice.setVisibility(View.VISIBLE);
        headerView.llCommonInvoice.setVisibility(View.VISIBLE);
        headerView.llVatmanInvoice.setVisibility(View.VISIBLE);
    }

    private void setOrderInfo() {
//        if (payStatus == 6 && payWay > 10) {
//            llConfirmPays.setVisibility(View.VISIBLE);
//        } else {
//            llConfirmPays.setVisibility(View.GONE);
//        }
        if (payStatus == 6) {
            llAuditAction.setVisibility(View.GONE);
            llConfirmPays.setVisibility(View.VISIBLE);
            if(buyingGroupId!=0){
                if(buyingGroupState==1){
                    llResendTicket.setVisibility(View.VISIBLE);
                }else{
                    llResendTicket.setVisibility(View.GONE);
                }
            }else{
                llResendTicket.setVisibility(View.VISIBLE);
            }
            llOrderAction.setVisibility(View.VISIBLE);
        } else if (payStatus == 12) {
            llAuditAction.setVisibility(View.VISIBLE);
            llOrderAction.setVisibility(View.GONE);
        } else if (payStatus == 1) {
            llOrderAction.setVisibility(View.VISIBLE);
            if(buyingGroupId!=0){
                if(buyingGroupState==1){
                    llResendTicket.setVisibility(View.VISIBLE);
                }else{
                    llResendTicket.setVisibility(View.GONE);
                }
            }else{
                llResendTicket.setVisibility(View.VISIBLE);
            }
            llAuditAction.setVisibility(View.GONE);
            llConfirmPays.setVisibility(View.GONE);
        } else {
            llAuditAction.setVisibility(View.GONE);
            llOrderBottom.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(buyerName)) {
            //    Log.e("aa", buyerEmail);
            headerView.tvBuerName.setText(buyerName);
        }
        if (!TextUtils.isEmpty(buyerEmail)) {
            headerView.tvBuerEmail.setText(buyerEmail);
        }
        if (!TextUtils.isEmpty(buyerCellphone)) {
            headerView.tvBuyerCellphone.setText(buyerCellphone);
        }

        if (!TextUtils.isEmpty(notes)) {
            headerView.tvRemark.setVisibility(View.VISIBLE);
            headerView.tvRemarkMark.setVisibility(View.VISIBLE);
            headerView.tvRemark.setText(notes);
        } else {
            headerView.tvRemark.setVisibility(View.GONE);
            headerView.tvRemarkMark.setVisibility(View.GONE);
        }
        //   Log.e("fdsaf",payWay+"" +accountType);
        if (payWay == 0) {
            String pay = Integer.toString(payWay) + accountType;
            Log.e("ActivityOrderDetail", payWay + " " + accountType);
            if ("00".equals(pay)) {
                headerView.tvPayway.setText(R.string.alipay);
            } else if ("01".equals(pay)) {
                headerView.tvPayway.setText(R.string.wechat);
            } else if ("02".equals(pay)) {
                headerView.tvPayway.setText("PayPal");
            } else if ("06".equals(pay) || "07".equals(pay)) {
                headerView.tvPayway.setText(R.string.credit_card);
            } else {
                headerView.tvPayway.setText("-");
            }
        } else if (payWay == 10) {
            headerView.tvPayway.setText(R.string.online_bank_payment);
        } else if (payWay == 11) {
            headerView.tvPayway.setText(R.string.bank_transfer);
        } else if (payWay == 13) {
            headerView.tvPayway.setText(R.string.bank_transfer);
        } else if (payWay == 23) {
            headerView.tvPayway.setText(R.string.spot_payment);
        } else if (payWay == 33) {
            headerView.tvPayway.setText(R.string.free_give);
        }
    }

    private void initData() {
        loadingFinished();
        setInvoiceGone();
        if (audit != 1) {
            llAuditAction.setVisibility(View.GONE);
            llOrderAction.setVisibility(View.VISIBLE);
            if(buyingGroupId!=0){
                if(buyingGroupState==1){
                    llResendTicket.setVisibility(View.VISIBLE);
                }else{
                    llResendTicket.setVisibility(View.GONE);
                }
            }else{
                llResendTicket.setVisibility(View.VISIBLE);
            }
        } else {
            llAuditAction.setVisibility(View.VISIBLE);
            llOrderAction.setVisibility(View.GONE);
        }
        setOrderInfo();
        attendss = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.orderIds.is(orderId)).queryList();
        //   Log.e("orderDetail size",attendss.size()+"");
        if (attendss.size() > 0) {
            if (detailAdapter == null) {
                initAdapter();
            }
        } else {
            singleAttendeePresenter = new SingleAttendeePresenter(this);
            singleAttendeePresenter.singleAttendee();
        }
        if (NetUtil.isConnected(this)) {
            invoicePresenter = new GetInvoicePresenter(this);
            invoicePresenter.getInvoice();
        }
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        eventId = intent.getIntExtra("eventId", -1);
        orderId = intent.getIntExtra("orderId", -1);
        isFromAudit = intent.getIntExtra("enterStatus", 0);
        if (isFromAudit == 3) {//聊天界面进入
            if (NetUtil.isConnected(this)) {
                orderNumber = intent.getStringExtra("orderNumber");
                userId = SharedPreferencesUtil.get(this, "userId", "");
                List<Attends> isExistAttendee = new Select().from(Attends.class).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.orderIds.is(orderId)).queryList();
                if (isExistAttendee.size() == 0) {
                    getFormTypePresenter = new GetFormTypePresenter(this);
                    getFormTypePresenter.getFormType();
                    getTicketPresenter = new GetTicketPresenter(this);
                    getTicketPresenter.getTicket();
                    singleAttendeePresenter = new SingleAttendeePresenter(this);
                    singleAttendeePresenter.singleAttendee();
                }
                singleOrderPresenter = new SingleOrderPresenter(this);
                singleOrderPresenter.getSingleOrder();
            } else {
                Toast.makeText(this, R.string.check_network1, Toast.LENGTH_SHORT).show();
            }
        } else if (isFromAudit == 4) {//参会人员详情界面进入
            if (NetUtil.isConnected(this)) {
                orderNumber = orderId + "";
                userId = SharedPreferencesUtil.get(this, "userId", "");
                singleOrderPresenter = new SingleOrderPresenter(this);
                singleOrderPresenter.getSingleOrder();
            } else {
                Toast.makeText(this,R.string.check_network1, Toast.LENGTH_SHORT).show();
            }
        } else {
            Order order = new Select().from(Order.class).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(orderId)).querySingle();
            if (order != null) {
                buyerCellphone = order.buyerCellphone;
                buyerName = order.buyerName;
                buyerEmail = order.buyerEmail;
                notes = order.notes;
                orderNumber = order.orderNumber;
                payWay = order.payWay;
                accountType = order.accountType;
                payStatus = order.payStatus;
                audit = order.audit;
                buyingGroupId=order.buyingGroupId;
                buyingGroupState=order.buyingGroupState;
                LogUtil.i("-----------buyingGroupIdw:"+buyingGroupId+"buyingGroupState:"+buyingGroupState);
            }
        }

    }

    private void showResendTicketDialog() {
        sendTicketDialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black_light)
                .setContentText(getString(R.string.weather_ticket_again))
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        sendTicketDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        if (NetUtil.isConnected(ActivityOrderDetail.this)) {
//                            Toast toast = Toast.makeText(ActivityOrderDetail.this, "正在重发...", Toast.LENGTH_SHORT);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
                            reSendTicketPresenter = new ReSendTicketPresenter(ActivityOrderDetail.this);
                            reSendTicketPresenter.resendTicketFromOrder();
                        } else {
                            Toast.makeText(ActivityOrderDetail.this, R.string.check_network, Toast.LENGTH_SHORT).show();
                        }
                        sendTicketDialog.dismiss();
                    }
                })
                .build();
        sendTicketDialog.show();
    }


    private void getHeaderView() {
        view = getLayoutInflater().inflate(R.layout.order_detail_item_header, (ViewGroup) rvOrderDetail.getParent(), false);
    }

    private void initAdapter() {
        detailAdapter = new ActivityOrderDetailAdapter(R.layout.layout_attendee_item, attendss);
        detailAdapter.openLoadAnimation();
        detailAdapter.addHeaderView(view);
        detailAdapter.setOnItemClickListener(this);
        detailAdapter.setOrderPaystatus(payStatus);
        rvOrderDetail.setAdapter(detailAdapter);
    }

    private void initView() {
        ivRight.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.gengduo).into(ivRight2);
        Glide.with(this)
                .load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.order_detail);
        userId = SharedPreferencesUtil.get(this, "userId", "");
        rvOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        rvOrderDetail.addItemDecoration(new RecyclerViewItemDecoration());
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public String getEventId() {
        return eventId + "";
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showTicketInfo(TicketInfo ticketInfo) {
        SyncTicketUtil util = new SyncTicketUtil(this, eventId, ticketInfo, false);
        util.syncTicket();
    }

    @Override
    public void showErrInfo(String errInfo) {

    }

    @Override
    public String detailEventId() {
        return eventId + "";
    }

    @Override
    public int sType() {
        return 0;
    }

    @Override
    public void showDetailInfo(FormType formType) {

    }

    @Override
    public void showDetailErrInfo(String errInfo) {

    }

    @Override
    public void showBadgeFormInfo(FormType formType) {

    }

    @Override
    public void showBadgeFormErrInfo(String errInfo) {

    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public int orderId() {
        return orderId;
    }

    @Override
    public void showSingleAttendee(String response) {
        SyncSingleAttendeeUtil util = new SyncSingleAttendeeUtil(this, eventId, response, false);
        util.syncSingleAttendeeUtil();
    }

    @Override
    public void showSingleAttendeeFailed(String errInfo) {

    }

    @Override
    public int isSendEmail() {
        return isSendEmail;
    }

    @Override
    public void showQuitSuccess(String response) {//退票成功，则订单中所有人退票
        payStatus = 10;
        SQLite.update(Attends.class).set(Attends_Table.payStatuss.is(10)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.orderIds.is(orderId)).execute();
        SQLite.update(Order.class).set(Order_Table.payStatus.is(10)).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(orderId)).execute();
        for (int i = 0; i < attendss.size(); i++) {
            attendss.get(i).payStatuss = 10;
        }
        if (detailAdapter != null) {
            detailAdapter.notifyDataSetChanged();

        }
        removeMenuItem();
        EventBus.getDefault().postSticky(new MsgEvent(position, Common.CONFIRM_ORDER, Common.CONFIRM_ORDER));
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showQuitFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showConfirmSuccess(String response) {
        llConfirmPays.setVisibility(View.GONE);
        addMenuItem();
        payStatus = 1;//只有当paystatus = 1的时候才能够进行退票操作
        SQLite.update(Attends.class).set(Attends_Table.payStatuss.is(1)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.orderIds.is(orderId)).execute();
        SQLite.update(Order.class).set(Order_Table.payStatus.is(1)).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(orderId)).execute();
        EventBus.getDefault().postSticky(new MsgEvent(position, Common.CONFIRM_ORDER, Common.CONFIRM_ORDER));
    }

    @Override
    public void showConfirmFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resendSuccess(String response) {
        Toast toast = Toast.makeText(this, response, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void resendFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String orderNumber() {
        return orderNumber;
    }

    @Override
    public void showSingleOrderSuccess(SingleOrderData response) {
        loadingFinished();
        if (response.getRespObject() != null) {
            SyncSingleOrderUtil util = new SyncSingleOrderUtil(this, eventId, response.getRespObject());
            util.syncSinleOrderUtil();
        }
    }

    @Override
    public void showSingleOrderFailed(String errInfo) {

    }

    @Override
    public int audit() {
        return audit;
    }

    @Override
    public void showAuditSuccess(String response) {
        // Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        if (audit == 2) {
            llOrderAction.setVisibility(View.VISIBLE);
            if(buyingGroupId!=0){
                if(buyingGroupState==1){
                    llResendTicket.setVisibility(View.VISIBLE);
                }else{
                    llResendTicket.setVisibility(View.GONE);
                }
            }else{
                llResendTicket.setVisibility(View.VISIBLE);
            }
            llAuditAction.setVisibility(View.GONE);
            SQLite.update(Attends.class).set(Attends_Table.audits.is(2)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.orderIds.is(orderId)).execute();
            SQLite.update(Order.class).set(Order_Table.audit.is(audit), Order_Table.payStatus.is(0)).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(orderId)).execute();
        } else {
            payStatus = 13;
            llOrderAction.setVisibility(View.GONE);
            llAuditAction.setVisibility(View.GONE);
            detailAdapter.setOrderPaystatus(payStatus);
            detailAdapter.notifyDataSetChanged();
            SQLite.update(Attends.class).set(Attends_Table.audits.is(3)).where(Attends_Table.eventId.is(eventId)).and(Attends_Table.orderIds.is(orderId)).execute();
            SQLite.update(Order.class).set(Order_Table.audit.is(audit), Order_Table.payStatus.is(13)).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(orderId)).execute();
        }
        //  llConfirmPays.setVisibility(View.VISIBLE);
        EventBus.getDefault().postSticky(new MsgEvent(position, Common.AUDIT_ORDER, Common.AUDIT_ORDER));
    }

    @Override
    public void showAuditFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();

    }

    @Override
    public String notes() {
        return notes;
    }

    @Override
    public void editNotesSuccess(String response) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        headerView.tvRemarkMark.setVisibility(View.VISIBLE);
        headerView.tvRemark.setVisibility(View.VISIBLE);
        headerView.tvRemark.setText(notes);
        if (detailAdapter != null) {
            detailAdapter.notifyDataSetChanged();
        }
        EventBus.getDefault().postSticky(new MsgEvent(position, Common.UPDATE_NOTES, Common.UPDATE_NOTES));
        SQLite.update(Order.class).set(Order_Table.notes.is(notes)).where(Order_Table.eventId.is(eventId)).and(Order_Table.orderId.is(orderId)).execute();
    }

    @Override
    public void editNotesFailed(String errInfo) {
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showInvoiceSuccess(InvoiceBean response) {
        setInvoiceVisible();
        InvoiceBean.RespObjectBean bean = response.getRespObject();
        if (bean != null) {
            mInvoice = bean;
            setInvoiceView(bean);
        } else {
            Toast.makeText(this, R.string.faild_ticket_info, Toast.LENGTH_SHORT).show();
        }
        headerView.llInvoiceHeader2.setVisibility(View.GONE);
        headerView.llCommonInvoice.setVisibility(View.GONE);
        headerView.llVatmanInvoice.setVisibility(View.GONE);
        if (detailAdapter != null) {
            detailAdapter.notifyDataSetChanged();

        }
    }

    private void setInvoiceView(InvoiceBean.RespObjectBean bean) {

        if (bean.getInvoiceType() == 0) {//发票类型 0--普票 1---专票 3--电子票
            invoiceType = 0;
            headerView.llVatmanInvoice.setVisibility(View.GONE);
            headerView.llCommonInvoice.setVisibility(View.VISIBLE);
            headerView.tvInvoiceType.setText(R.string.added_tax_ordinary_invoice);
            if (!TextUtils.isEmpty(bean.getBrief())) {
                headerView.llCommonRemark.setVisibility(View.VISIBLE);
                headerView.tvInvoiceRemark.setText(bean.getBrief());
            } else {
                headerView.llCommonRemark.setVisibility(View.GONE);
            }

            if (bean.getReceiverType() == 1) {
                headerView.tvReciverType.setText(R.string.company);
            } else {
                headerView.tvReciverType.setText(R.string.personal);
            }

            if (bean.getObtainWay() == 0) {//领取方式 0---现场领取 1---快递 4---网上下载
                headerView.llCommonReceive.setVisibility(View.GONE);
                headerView.tvObtainType.setText(R.string.rb_obtainway_scene);
            } else if (bean.getObtainWay() == 1) {
                headerView.llCommonReceive.setVisibility(View.VISIBLE);
                headerView.tvCommonRecivername.setText(bean.getReceiver());
                headerView.tvCommonRecivercellphone.setText(bean.getCellphone());
                headerView.tvCommonReciveraddress.setText(bean.getAddress());
                headerView.tvObtainType.setText(R.string.rb_obtainway_send);
            } else if (bean.getObtainWay() == 4) {
                headerView.llCommonReceive.setVisibility(View.GONE);
                headerView.tvObtainType.setText(R.string.download_ticket);
            }

            if (bean.getUseCompanyCode() == 0) {
                headerView.llCommonUsecompanycode.setVisibility(View.GONE);
            } else if (bean.getUseCompanyCode() == 1) {
                headerView.llCommonUsecompanycode.setVisibility(View.VISIBLE);
                headerView.tvCommonCompanycode.setText(bean.getCompanyCode());
            }

        } else if (bean.getInvoiceType() == 1) {
            invoiceType = 1;
            headerView.llCommonInvoice.setVisibility(View.GONE);
            headerView.llVatmanInvoice.setVisibility(View.VISIBLE);
            headerView.tvInvoiceType.setText(R.string.added_tax_major_invoice);
            headerView.llVatmanRecognicode.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(bean.getBrief())) {
                headerView.tvVatmanRemark.setText(bean.getBrief());
            } else {
                headerView.llVatmanRemark.setVisibility(View.GONE);
            }

            if (bean.getObtainWay() == 0) {//领取方式 0---现场领取 1---快递 4---网上下载
                headerView.llVatmanReceive.setVisibility(View.GONE);
                headerView.tvObtainType.setText(R.string.rb_obtainway_scene);
            } else if (bean.getObtainWay() == 1) {
                headerView.llVatmanReceive.setVisibility(View.VISIBLE);
                headerView.tvVatmanRecivernames.setText(bean.getReceiver());
                headerView.tvVatmanReciveraddr.setText(bean.getAddress());
                headerView.tvVatnanRecivercellphone.setText(bean.getCellphone());
                headerView.tvObtainType.setText(R.string.rb_obtainway_send);
            } else if (bean.getObtainWay() == 4) {
                headerView.llVatmanReceive.setVisibility(View.GONE);
                headerView.tvObtainType.setText(R.string.download_ticket);
            }

            if (bean.getUseCompanyCode() == 0) {
                headerView.llVatmanUsecompanycode.setVisibility(View.GONE);
            } else if (bean.getUseCompanyCode() == 1) {
                headerView.llVatmanUsecompanycode.setVisibility(View.VISIBLE);
                headerView.tvVatmanCompanycode.setText(bean.getCompanyCode());
            }
        }
        headerView.tvInvoiceItem.setText(bean.getInvoiceItem());

        if (bean.getUseCompanyCode() == 0) {//不使用开票代码 0--不使用 1--使用
            if (bean.getInvoiceType() == 0) {
                headerView.llInvoiceCompanyname.setVisibility(View.VISIBLE);
                if (bean.getReceiverType() == 1) {
                    headerView.llInvoiceRecognition.setVisibility(View.VISIBLE);
                } else {
                    headerView.llInvoiceRecognition.setVisibility(View.GONE);
                }
                headerView.tvInvoiceCompanyname.setText(bean.getInvoiceTitle());
                headerView.tvInvoiceRecognition.setText(bean.getTaxNum());
            } else {
                headerView.llVatmanCompany.setVisibility(View.VISIBLE);
                headerView.llVatmanRecognicode.setVisibility(View.VISIBLE);
                headerView.llVatmanRegisAddr.setVisibility(View.VISIBLE);
                headerView.llVatmanCellphone.setVisibility(View.VISIBLE);
                headerView.llVatmanBank.setVisibility(View.VISIBLE);
                headerView.llVatmanBankcode.setVisibility(View.VISIBLE);
                headerView.tvVatmanCompany.setText(bean.getInvoiceTitle());
                headerView.tvVatmanBank.setText(bean.getCompanyBank());
                headerView.tvVatmanBankcode.setText(bean.getBankAccount());
                headerView.tvVatmanCellphone.setText(bean.getCompanyFinanceContact());
                headerView.tvVatmanRegisAddr.setText(bean.getCompanyRegAddr());
                headerView.tvVatmanRecognicode.setText(bean.getTaxNum());
            }
            headerView.tvUseCompanycode.setText(R.string.no_use);
        } else if (bean.getUseCompanyCode() == 1) {
            if (bean.getInvoiceType() == 0) {
                headerView.llInvoiceCompanyname.setVisibility(View.GONE);
                headerView.llInvoiceRecognition.setVisibility(View.GONE);
            } else if (bean.getInvoiceType() == 1) {
                headerView.llVatmanCompany.setVisibility(View.GONE);
                headerView.llVatmanRecognicode.setVisibility(View.GONE);
                headerView.llVatmanRegisAddr.setVisibility(View.GONE);
                headerView.llVatmanCellphone.setVisibility(View.GONE);
                headerView.llVatmanBank.setVisibility(View.GONE);
                headerView.llVatmanBankcode.setVisibility(View.GONE);
            }
            headerView.tvUseCompanycode.setText(R.string.use);
        }

    }

    @Override
    public void showInvoiceFailed(String errInfo) {
//        if (!"0".equals(errInfo)) {
//            Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
//        }
        setInvoiceGone();
    }
}
