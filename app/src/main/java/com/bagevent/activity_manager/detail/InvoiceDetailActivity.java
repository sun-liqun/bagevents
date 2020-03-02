package com.bagevent.activity_manager.detail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.db.Invoice;
//import com.bagevent.db.Invoice_Table;
import com.bagevent.db.Invoice_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZWJ on 2017/12/28 0028.
 */

public class InvoiceDetailActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_invoice_type)
    TextView tvInvoiceType;
    @BindView(R.id.tv_invoice_Item)
    TextView tvInvoiceItem;
    @BindView(R.id.iv_invoice_more)
    ImageView ivInvoiceMore;
    @BindView(R.id.ll_invoice_more)
    AutoLinearLayout llInvoiceMore;
    @BindView(R.id.tv_use_companycode)
    TextView tvUseCompanycode;
    @BindView(R.id.tv_reciver_type)
    TextView tvReciverType;
    @BindView(R.id.tv_obtain_type)
    TextView tvObtainType;
    @BindView(R.id.ll_invoice_header2)
    AutoLinearLayout llInvoiceHeader2;
    @BindView(R.id.ll_invoice_heder)
    AutoLinearLayout llInvoiceHeder;
    @BindView(R.id.tv_common_companycode)
    TextView tvCommonCompanycode;
    @BindView(R.id.ll_common_usecompanycode)
    AutoLinearLayout llCommonUsecompanycode;
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
    @BindView(R.id.ll_common_remark)
    AutoLinearLayout llCommonRemark;
    @BindView(R.id.tv_common_recivername)
    TextView tvCommonRecivername;
    @BindView(R.id.tv_common_recivercellphone)
    TextView tvCommonRecivercellphone;
    @BindView(R.id.tv_common_reciveraddress)
    TextView tvCommonReciveraddress;
    @BindView(R.id.ll_common_invoice)
    AutoLinearLayout llCommonInvoice;
    @BindView(R.id.tv_vatman_companycode)
    TextView tvVatmanCompanycode;
    @BindView(R.id.ll_vatman_usecompanycode)
    AutoLinearLayout llVatmanUsecompanycode;
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
    @BindView(R.id.tv_vatman_remark)
    TextView tvVatmanRemark;
    @BindView(R.id.ll_vatman_remark)
    AutoLinearLayout llVatmanRemark;
    @BindView(R.id.tv_vatman_recivernames)
    TextView tvVatmanRecivernames;
    @BindView(R.id.tv_vatnan_recivercellphone)
    TextView tvVatnanRecivercellphone;
    @BindView(R.id.tv_vatman_reciveraddr)
    TextView tvVatmanReciveraddr;
    @BindView(R.id.ll_vatman_receive)
    AutoLinearLayout llVatmanReceive;
    @BindView(R.id.ll_vatman_invoice)
    AutoLinearLayout llVatmanInvoice;
    @BindView(R.id.ll_common_receives)
    AutoLinearLayout llCommonReceives;


    private String userId;
    private int eventId;
    private int invoiceId;
    private int invoiceType;
    private Invoice singleInvoice;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_invoice_detail);
        ButterKnife.bind(this);
        initView();
        getIntentValue();
        setInvoiceView(singleInvoice);
    }


    @OnClick({R.id.ll_title_back, R.id.ll_express})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_express:
                if (singleInvoice.obtainWay == 1) {
                    Intent intent = new Intent(this, ExpressActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("eventId", eventId);
                    intent.putExtra("invoiceId", invoiceId);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.no_need_express, Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void setInvoiceView(Invoice bean) {
        if (bean != null) {
            ivInvoiceMore.setVisibility(View.GONE);
            if (bean.invoiceType == 0) {//发票类型 0--普票 1---专票 3--电子票
                invoiceType = 0;
                llVatmanInvoice.setVisibility(View.GONE);
                llCommonInvoice.setVisibility(View.VISIBLE);
                tvInvoiceType.setText(R.string.added_tax_ordinary_invoice);
                if (!TextUtils.isEmpty(bean.brief)) {
                    llCommonRemark.setVisibility(View.VISIBLE);
                    tvInvoiceRemark.setText(bean.brief);
                } else {
                    llCommonRemark.setVisibility(View.GONE);
                }

                if (bean.receiverType == 1) {
                    tvReciverType.setText(R.string.company);
                } else {
                    tvReciverType.setText(R.string.personal);
                }

                if (bean.obtainWay == 0) {//领取方式 0---现场领取 1---快递 4---网上下载
                    llCommonReceives.setVisibility(View.GONE);
                    tvObtainType.setText(R.string.rb_obtainway_scene);
                } else if (bean.obtainWay == 1) {
                    llCommonReceives.setVisibility(View.VISIBLE);
                    tvCommonRecivername.setText(bean.receiver);
                    tvCommonRecivercellphone.setText(bean.cellphone);
                    tvCommonReciveraddress.setText(bean.address);
                    tvObtainType.setText(R.string.rb_obtainway_send);
                } else if (bean.obtainWay == 4) {
                    llCommonReceives.setVisibility(View.GONE);
                    tvObtainType.setText(R.string.download_ticket);
                }

                if (bean.useCompanyCode == 0) {
                    llCommonUsecompanycode.setVisibility(View.GONE);
                } else if (bean.useCompanyCode == 1) {
                    llCommonUsecompanycode.setVisibility(View.VISIBLE);
                    tvCommonCompanycode.setText(bean.companyCode);
                }

            } else if (bean.invoiceType == 1) {
                invoiceType = 1;
                llCommonInvoice.setVisibility(View.GONE);
                llVatmanInvoice.setVisibility(View.VISIBLE);
                tvInvoiceType.setText(R.string.added_tax_major_invoice);
                llVatmanRecognicode.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(bean.brief)) {
                    tvVatmanRemark.setText(bean.brief);
                } else {
                    llVatmanRemark.setVisibility(View.GONE);
                }

                if (bean.obtainWay == 0) {//领取方式 0---现场领取 1---快递 4---网上下载
                    llVatmanReceive.setVisibility(View.GONE);
                    tvObtainType.setText(R.string.rb_obtainway_scene);
                } else if (bean.obtainWay == 1) {
                    llVatmanReceive.setVisibility(View.VISIBLE);
                    tvVatmanRecivernames.setText(bean.receiver);
                    tvVatmanReciveraddr.setText(bean.address);
                    tvVatnanRecivercellphone.setText(bean.cellphone);
                    tvObtainType.setText(R.string.rb_obtainway_send);
                } else if (bean.obtainWay == 4) {
                    llVatmanReceive.setVisibility(View.GONE);
                    tvObtainType.setText(R.string.download_ticket);
                }

                if (bean.useCompanyCode == 0) {
                    llVatmanUsecompanycode.setVisibility(View.GONE);
                } else if (bean.useCompanyCode == 1) {
                    llVatmanUsecompanycode.setVisibility(View.VISIBLE);
                    tvVatmanCompanycode.setText(bean.companyCode);
                }
            }
            tvInvoiceItem.setText(bean.invoiceItem);

            if (bean.useCompanyCode == 0) {//不使用开票代码 0--不使用 1--使用
                if (bean.invoiceType == 0) {
                    llInvoiceCompanyname.setVisibility(View.VISIBLE);
                    if (bean.receiverType == 1) {
                        llInvoiceRecognition.setVisibility(View.VISIBLE);
                    } else {
                        llInvoiceRecognition.setVisibility(View.GONE);
                    }
                    tvInvoiceCompanyname.setText(bean.invoiceTitle);
                    tvInvoiceRecognition.setText(bean.taxNum);
                } else {
                    llVatmanCompany.setVisibility(View.VISIBLE);
                    llVatmanRecognicode.setVisibility(View.VISIBLE);
                    llVatmanRegisAddr.setVisibility(View.VISIBLE);
                    llVatmanCellphone.setVisibility(View.VISIBLE);
                    llVatmanBank.setVisibility(View.VISIBLE);
                    llVatmanBankcode.setVisibility(View.VISIBLE);
                    tvVatmanCompany.setText(bean.invoiceTitle);
                    tvVatmanBank.setText(bean.companyBank);
                    tvVatmanBankcode.setText(bean.bankAccount);
                    tvVatmanCellphone.setText(bean.companyFinanceContact);
                    tvVatmanRegisAddr.setText(bean.companyRegAddr);
                    tvVatmanRecognicode.setText(bean.taxNum);
                }
                tvUseCompanycode.setText(R.string.no_use);
            } else if (bean.useCompanyCode == 1) {
                if (bean.invoiceType == 0) {
                    llInvoiceCompanyname.setVisibility(View.GONE);
                    llInvoiceRecognition.setVisibility(View.GONE);
                } else if (bean.invoiceType == 1) {
                    llVatmanCompany.setVisibility(View.GONE);
                    llVatmanRecognicode.setVisibility(View.GONE);
                    llVatmanRegisAddr.setVisibility(View.GONE);
                    llVatmanCellphone.setVisibility(View.GONE);
                    llVatmanBank.setVisibility(View.GONE);
                    llVatmanBankcode.setVisibility(View.GONE);
                }
                tvUseCompanycode.setText(R.string.use);
            }
        }
    }


    private void getIntentValue() {
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        if (userId != null) {
            eventId = intent.getIntExtra("eventId", 0);
            invoiceId = intent.getIntExtra("invoiceId", 0);
            singleInvoice = new Select().from(Invoice.class).where(Invoice_Table.userId.is(Integer.parseInt(userId))).and(Invoice_Table.eventId.is(eventId + "")).and(Invoice_Table.orderInvoiceId.is(invoiceId)).querySingle();
        }
    }

    private void initView() {
        tvTitle.setText(R.string.invoice_details);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
    }


}
