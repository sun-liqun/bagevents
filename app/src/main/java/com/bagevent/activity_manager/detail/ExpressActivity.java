package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.data.OrderHandleData;
import com.bagevent.activity_manager.manager_fragment.data.OrderServiceData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ExpressActionPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.OrderHandleView;
import com.bagevent.db.Invoice;
//import com.bagevent.db.Invoice_Table;
import com.bagevent.db.Invoice_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.new_home.new_activity.SetSenderAddress;
import com.bagevent.util.AppManager;
import com.bagevent.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bagevent.util.EncodeUtils.encrypt;
import static com.bagevent.util.EncodeUtils.urlEncoder;

/**
 * Created by ZWJ on 2017/12/27 0027.
 */

public class ExpressActivity extends BaseActivity implements OrderHandleView, TextWatcher {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    @BindView(R.id.ll_common_title)
    AutoLinearLayout llCommonTitle;
    @BindView(R.id.tv_express_name)
    TextView tvExpressName;
    @BindView(R.id.tv_express_cellphone)
    TextView tvExpressCellphone;
    @BindView(R.id.tv_express_address)
    TextView tvExpressAddress;

    private final static int REQUESTCODE = 1;
    @BindView(R.id.et_express_number)
    EditText etExpressNumber;
    @BindView(R.id.iv_sweep)
    ImageView ivSweep;
    @BindView(R.id.tv_express)
    TextView tvExpress;
    private ExpressActionPresenter expressActionPresenter;
    private String userId;
    private int eventId;
    private int invoiceId;
    private String requestData;
    private String eBusinessID;
    private String requestType;
    private String dataSign;
    private String dataType;
    private String shipperCode;//快递公司编码
    private String logisticCode;//快递单号
    private String shipperName;
    private String ID = "1317654";
    private String key = "d218d80e-1344-4f00-9d45-b3ee935e4eca";
    private Invoice singleInvoice;
    private NormalAlertDialog expressDialog;
    private NormalAlertDialog setSenderAddressDialog;

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.layout_express);
        ButterKnife.bind(this);
        initView();
        getIntentValue();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == REQUESTCODE) {
                String text = data.getStringExtra("expressNumber");
                etExpressNumber.setText(text);
                logisticCode = text;
            }
        }
    }

    @OnClick({R.id.ll_title_back, R.id.tv_order_service, R.id.iv_sweep})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_order_service:
                String addressProvince = SharedPreferencesUtil.get(this, "expressProvince", "");
                String addressCity = SharedPreferencesUtil.get(this, "expressCity", "");
                String detailAddress = SharedPreferencesUtil.get(this, "expressDetailAddress", "");
                if (!TextUtils.isEmpty(addressProvince) && !TextUtils.isEmpty(addressCity) && !TextUtils.isEmpty(detailAddress)) {
                    if (!TextUtils.isEmpty(etExpressNumber.getText().toString())) {
                        showCallphoneDialog(shipperName);
                    } else {
                        shipperCode = "SF";
                        showCallphoneDialog(getString(R.string.sf));
                    }
                } else {
                    showSetSenderAddress();
                }

                break;
            case R.id.iv_sweep:
                //Toast.makeText(this,"扫描快递单号",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ScannerExpress.class);
                startActivityForResult(intent, REQUESTCODE);
                break;
        }
    }

    private void initData() {
        if (singleInvoice!=null){
            tvExpressName.setText(singleInvoice.receiver);
            tvExpressCellphone.setText(singleInvoice.cellphone);
            tvExpressAddress.setText(singleInvoice.address);
        }
    }

    private String orderHandleRequestData(String expressNumber) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("LogisticCode", expressNumber);
        return new Gson().toJson(map);
    }

    private String setRequestData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("OrderCode", singleInvoice.orderNumber);
        map.put("ShipperCode", shipperCode);
        map.put("PayType", "1");
        map.put("ExpType", "1");
        if (!TextUtils.isEmpty(logisticCode)) {
            map.put("LogisticCode", logisticCode);
        }

        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("Company", "南京弟齐信息技术有限公司");
        map1.put("Name", "ZWJ");
        map1.put("Mobile", "17712906965");
        map1.put("ProvinceName", "江苏省");
        map1.put("CityName", "南京市");
        map1.put("ExpAreaName", "雨花台区");
        map1.put("Address", "长虹路德盈国际2栋212");

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("Name", singleInvoice.receiver);
        map2.put("Mobile", singleInvoice.cellphone);
        map2.put("ProvinceName", "江苏省");
        map2.put("CityName", "苏州市");
        map2.put("ExpAreaName", "XXXXX");
        map2.put("Address", singleInvoice.address);

        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("GoodsName", "发票");

        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        listMap.add(map3);
        map.put("Sender", map1);
        map.put("Receiver", map2);
        map.put("Commodity", listMap);
        return new Gson().toJson(map);
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        if (userId != null) {
            eventId = intent.getIntExtra("eventId", 0);
            invoiceId = intent.getIntExtra("invoiceId", 0);
            singleInvoice = new Select().from(Invoice.class).where(Invoice_Table.userId.is(Integer.parseInt(userId))).and(Invoice_Table.eventId.is(String.valueOf(eventId))).and(Invoice_Table.orderInvoiceId.is(invoiceId)).querySingle();
        }
    }

    private void showSetSenderAddress() {
        setSenderAddressDialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black_light)
                .setContentText(getString(R.string.weather_setting_address))
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.go_to))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        setSenderAddressDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        Intent intent = new Intent(ExpressActivity.this, SetSenderAddress.class);
                        startActivity(intent);
                        setSenderAddressDialog.dismiss();
                    }
                })
                .build();
        setSenderAddressDialog.show();
    }

    private void showCallphoneDialog(String expressName) {
        expressDialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.confirm_order))
                .setTitleTextColor(R.color.black_light)
                .setContentText(R.string.express_company + expressName)
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        expressDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        String data = setRequestData();
                        try {
                            requestData = urlEncoder(data, "utf-8");
                            eBusinessID = ID;
                            requestType = "1007";
                            dataSign = urlEncoder(encrypt(data, key, "utf-8"), "utf-8");
                            dataType = "2";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        expressActionPresenter = new ExpressActionPresenter(ExpressActivity.this);
                        expressActionPresenter.orderService();
                        expressDialog.dismiss();
                    }
                })
                .build();
        expressDialog.show();
    }


    private void initView() {
        tvTitle.setText(R.string.express_info);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        etExpressNumber.addTextChangedListener(this);
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String requestData() {
        return requestData;
    }

    @Override
    public String eBusinessID() {
        return eBusinessID;
    }

    @Override
    public String requestType() {
        return requestType;
    }

    @Override
    public String dataSign() {
        return dataSign;
    }

    @Override
    public String dataType() {
        return dataType;
    }

    @Override
    public void querySuccess() {

    }

    @Override
    public void queryFailed() {

    }

    @Override
    public void orderHandleSuccess(OrderHandleData response) {
        if (response.getShippers().size() == 1) {
            shipperCode = response.getShippers().get(0).getShipperCode();
            logisticCode = response.getLogisticCode();
            shipperName = response.getShippers().get(0).getShipperName();
            //showCallphoneDialog(response.getShippers().get(0).getShipperName());
            tvExpress.setVisibility(View.VISIBLE);
            tvExpress.setText(response.getShippers().get(0).getShipperName());
        } else {
            tvExpress.setVisibility(View.GONE);
            if (response.getShippers().size() < 1) {
                Toast.makeText(this, R.string.input_express, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void orderHandleFailed(String errCode) {

    }

    @Override
    public void orderServiceSuccess(OrderServiceData response) {
        Toast.makeText(this, response.getOrder().getShipperCode(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void orderServiceFailed() {
        Toast.makeText(this, "下单失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String result = s.toString();
        if (result.length() > 7) {
            try {
                requestType = "2002";
                dataType = "2";
                eBusinessID = ID;
                String data = orderHandleRequestData(result);
                requestData = urlEncoder(data, "utf-8");
                dataSign = urlEncoder(encrypt(data, key, "utf-8"), "utf-8");
                expressActionPresenter = new ExpressActionPresenter(this);
                expressActionPresenter.orderHandle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
