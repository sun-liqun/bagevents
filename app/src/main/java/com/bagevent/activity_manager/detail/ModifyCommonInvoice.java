package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.InvoiceBean;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ModifyInvoicePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ModifyCommonInvoiceView;
import com.bagevent.common.Common;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.NetUtil;
import com.bumptech.glide.Glide;
import com.xw.repo.XEditText;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WenJie on 2017/10/31.
 */

public class ModifyCommonInvoice extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ModifyCommonInvoiceView {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.ll_common_title)
    LinearLayout llCommonTitle;
    @BindView(R.id.rb_companycode_unuse)
    RadioButton rbCompanycodeUnuse;
    @BindView(R.id.rb_companycode_use)
    RadioButton rbCompanycodeUse;
    @BindView(R.id.rg_companycode)
    RadioGroup rgCompanycode;
    @BindView(R.id.rb_receive_company)
    RadioButton rbReceiveCompany;
    @BindView(R.id.rb_receive_personal)
    RadioButton rbReceivePersonal;
    @BindView(R.id.rg_receive)
    RadioGroup rgReceive;
    @BindView(R.id.et_common_companycode)
    XEditText etCommonCompanycode;
    @BindView(R.id.ll_modify_companycode)
    AutoLinearLayout llModifyCompanycode;
    @BindView(R.id.et_common_company)
    XEditText etCommonCompany;
    @BindView(R.id.ll_modify_companyname)
    AutoLinearLayout llModifyCompanyname;
    @BindView(R.id.et_service_type)
    XEditText etServiceType;
    @BindView(R.id.ll_modify_servicetype)
    AutoLinearLayout llModifyServicetype;
    @BindView(R.id.et_taxnum)
    XEditText etTaxnum;
    @BindView(R.id.ll_modify_taxnum)
    AutoLinearLayout llModifyTaxnum;
    @BindView(R.id.ll_modify_remark)
    AutoLinearLayout llModifyRemark;
    @BindView(R.id.rb_obtainway_scene)
    RadioButton rbObtainwayScene;
    @BindView(R.id.rb_obtainway_send)
    RadioButton rbObtainwaySend;
    @BindView(R.id.rg_obtainway)
    RadioGroup rgObtainway;
    @BindView(R.id.et_receive_name)
    XEditText etReceiveName;
    @BindView(R.id.ll_modify_receivename)
    AutoLinearLayout llModifyReceivename;
    @BindView(R.id.et_receive_cellphone)
    XEditText etReceiveCellphone;
    @BindView(R.id.ll_modify_receivecellphone)
    AutoLinearLayout llModifyReceivecellphone;
    @BindView(R.id.et_receive_addr)
    XEditText etReceiveAddr;
    @BindView(R.id.ll_modify_receiveaddr)
    AutoLinearLayout llModifyReceiveaddr;
    @BindView(R.id.tv_confirm_modify)
    TextView tvConfirmModify;
    @BindView(R.id.et_remark)
    XEditText etRemark;
    @BindView(R.id.ll_modify_receivetype)
    AutoLinearLayout llModifyReceivetype;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;

    private InvoiceBean.RespObjectBean bean;
    private int eventId;
    private String orderNumber;
    private int isUseCompanyCode = 0;//0 = 不使用,1 = 使用
    private int isReceiveType = 1;//1 = 公司,2 = 个人
    private int isInvoiceSend = 0;//0 = 现场领取,1 = 快递

    private String companCode;
    private String serviceItem;
    private String remark;
    private String invoiceTitle;
    private String taxNum;
    private String receiveName;
    private String receiveCellphone;
    private String receiveAddr;

    private ModifyInvoicePresenter invoicePresenter;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_modify_common_invoice);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
        setData();
    }

    @OnClick({R.id.ll_title_back, R.id.tv_confirm_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;

            case R.id.tv_confirm_modify:
                postModify();
                break;
        }
    }

    private void postModify() {
        if (NetUtil.isConnected(this)) {
            if (isUseCompanyCode == 0) {
                if (!TextUtils.isEmpty(etCommonCompany.getText().toString())) {
                    invoiceTitle = etCommonCompany.getText().toString();
                    if (!TextUtils.isEmpty(etServiceType.getText().toString())) {
                        serviceItem = etServiceType.getText().toString();
                        if (isReceiveType == 1) {
                            if (!TextUtils.isEmpty(etTaxnum.getText().toString())) {
                                taxNum = etTaxnum.getText().toString();
                                modify();
                            } else {
                                Toast.makeText(this, R.string.taxpayer_hint, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            modify();
                        }
                    } else {
                        Toast.makeText(this, R.string.service_items_hint, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.invoice_rise_hint, Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!TextUtils.isEmpty(etCommonCompanycode.getText().toString())) {
                    companCode = etCommonCompanycode.getText().toString();
                    if (!TextUtils.isEmpty(etServiceType.getText().toString())) {
                        serviceItem = etServiceType.getText().toString();
                        modify();
                    } else {
                        Toast.makeText(this, R.string.service_items_hint, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.invoicing_code_hint, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, R.string.check_network, Toast.LENGTH_SHORT).show();
        }

    }

    private void modify() {
        if (isInvoiceSend == 1) {
            if (isUseExpress()) {
                //发起请求
                if (!TextUtils.isEmpty(etRemark.getText().toString())) {
                    remark = etRemark.getText().toString();
                }
                tvConfirmModify.setText(R.string.commiting);
                invoicePresenter = new ModifyInvoicePresenter(this);
                invoicePresenter.modifyCommonInvoice();
              //  Toast.makeText(this, "确认修改", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (!TextUtils.isEmpty(etRemark.getText().toString())) {
                remark = etRemark.getText().toString();
            }
            tvConfirmModify.setText(R.string.commiting);
            invoicePresenter = new ModifyInvoicePresenter(this);
            invoicePresenter.modifyCommonInvoice();
            //发起请求
        //    Toast.makeText(this, "确认修改", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取出快递中的值
     */
    private boolean isUseExpress() {
        if (!TextUtils.isEmpty(etReceiveName.getText().toString())) {
            receiveName = etReceiveName.getText().toString();
            if (!TextUtils.isEmpty(etReceiveCellphone.getText().toString())) {
                receiveCellphone = etReceiveCellphone.getText().toString();
                if (CompareRex.isCellPhone(receiveCellphone)) {
                    if (!TextUtils.isEmpty(etReceiveAddr.getText().toString())) {
                        receiveAddr = etReceiveAddr.getText().toString();
                        return true;
                    }
                } else {
                    Toast.makeText(this, R.string.phone_format_error, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, R.string.input_contact, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.input_contact_name, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void setData() {
        if (bean != null) {
            if (bean.getUseCompanyCode() == 0) {
                rbCompanycodeUnuse.setChecked(true);
                etCommonCompany.setText(bean.getInvoiceTitle());
                etTaxnum.setText(bean.getTaxNum());
            } else {
                rbCompanycodeUse.setChecked(true);
                etCommonCompanycode.setText(bean.getCompanyCode());
            }

            if (bean.getReceiverType() == 1) {
                rbReceiveCompany.setChecked(true);
            } else {
                rbReceivePersonal.setChecked(true);
            }

            if (bean.getObtainWay() == 0) {
                rbObtainwayScene.setChecked(true);
            } else {
                rbObtainwaySend.setChecked(true);
                etReceiveName.setText(bean.getReceiver());
                etReceiveCellphone.setText(bean.getCellphone());
                etReceiveAddr.setText(bean.getAddress());
            }
            etServiceType.setText(bean.getInvoiceItem());
            etRemark.setText(bean.getBrief());
        }
    }

    private void setCompanyCodeVisible() {
        llModifyCompanyname.setVisibility(View.GONE);
        llModifyTaxnum.setVisibility(View.GONE);
        llModifyCompanycode.setVisibility(View.VISIBLE);
        llModifyReceivetype.setVisibility(View.GONE);
    }

    private void setCompanyCodeGone() {
        llModifyCompanyname.setVisibility(View.VISIBLE);
        if (isReceiveType == 1) {
            llModifyTaxnum.setVisibility(View.VISIBLE);
        }
        llModifyReceivetype.setVisibility(View.VISIBLE);
        llModifyCompanycode.setVisibility(View.GONE);
    }

    private void setInvoiceSendVisible() {
        llModifyReceivename.setVisibility(View.VISIBLE);
        llModifyReceivecellphone.setVisibility(View.VISIBLE);
        llModifyReceiveaddr.setVisibility(View.VISIBLE);

    }

    private void setInvoiceSendGone() {
        llModifyReceivename.setVisibility(View.GONE);
        llModifyReceivecellphone.setVisibility(View.GONE);
        llModifyReceiveaddr.setVisibility(View.GONE);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            eventId = bundle.getInt("eventId");
            orderNumber = bundle.getString("orderNumber");
            bean = (InvoiceBean.RespObjectBean) bundle.getSerializable("invoice");
        }
    }

    private void initListener() {
        rgCompanycode.setOnCheckedChangeListener(this);
        rgObtainway.setOnCheckedChangeListener(this);
        rgReceive.setOnCheckedChangeListener(this);
    }

    private void initView() {
        tvTitle.setText(R.string.change_ticket_info);
        llRightClick.setVisibility(View.INVISIBLE);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.rg_companycode:
                switch (checkedId) {
                    case R.id.rb_companycode_unuse:
                        isUseCompanyCode = 0;
                        setCompanyCodeGone();
                        break;
                    case R.id.rb_companycode_use:
                        isUseCompanyCode = 1;
                        setCompanyCodeVisible();
                        break;
                }
                break;
            case R.id.rg_receive:
                switch (checkedId) {
                    case R.id.rb_receive_company:
                        isReceiveType = 1;
                        if (isUseCompanyCode == 0) {
                            llModifyTaxnum.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.rb_receive_personal:
                        isReceiveType = 2;
                        llModifyTaxnum.setVisibility(View.GONE);
                        break;
                }
                break;
            case R.id.rg_obtainway:
                switch (checkedId) {
                    case R.id.rb_obtainway_scene:
                        isInvoiceSend = 0;
                        setInvoiceSendGone();
                        break;
                    case R.id.rb_obtainway_send:
                        isInvoiceSend = 1;
                        setInvoiceSendVisible();
                        break;
                }
                break;
        }
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public String orderNumber() {
        return orderNumber;
    }

    @Override
    public int obtainWay() {
        return isInvoiceSend;
    }

    @Override
    public int invoiceType() {
        return 0;
    }

    @Override
    public String receiver() {
        return receiveName;
    }

    @Override
    public String cellphone() {
        return receiveCellphone;
    }

    @Override
    public String address() {
        return receiveAddr;
    }

    @Override
    public String invoiceTitle() {
        return invoiceTitle;
    }

    @Override
    public String invoiceItem() {
        return serviceItem;
    }

    @Override
    public String brief() {
        return remark;
    }

    @Override
    public String taxNum() {
        return taxNum;
    }

    @Override
    public int companyCodeType() {
        return isUseCompanyCode;
    }

    @Override
    public String companyCode() {
        return companCode;
    }

    @Override
    public int receiverType() {
        return isReceiveType;
    }

    @Override
    public void showModifySuccess(String response) {
        tvConfirmModify.setText(R.string.confirm_update);
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().postSticky(new MsgEvent(Common.MODIFY_INVOICE));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void showmodifyFailed(String errInfo) {
        tvConfirmModify.setText(R.string.confirm_update);
        Toast.makeText(this, errInfo, Toast.LENGTH_SHORT).show();
    }
}
