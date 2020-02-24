package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.new_home.new_interface.new_view.AddTicketView;
import com.bagevent.new_home.new_interface.new_view.UpdateTicketView;
import com.bagevent.new_home.new_interface.presenter.AddTicketPresenter;
import com.bagevent.new_home.new_interface.presenter.UpdateTicketPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.SoftKeyboardStateHelper;
import com.kyleduo.switchbutton.SwitchButton;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zwj on 2016/9/19.
 */
public class ReleaseEventAddTicket extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextWatcher, AddTicketView, UpdateTicketView {

    private AutoLinearLayout tset_back;
    private AutoLinearLayout tset_confirm;
    private SwitchButton switchIsFree;
    private SwitchButton switchIsAudit;
    private AutoRelativeLayout rl_ticketPrice;
    private AutoRelativeLayout rl_ticketAudit;
    private EditText et_ticketName;
    private EditText et_ticketPrice;
    private EditText et_ticketCount;
    private ImageView ivClearName;
    private ImageView ivClearPrice;
    private ImageView ivClearCount;

    private int ticketAudit = -1;
    private int ticketId = -1;
    private int eventId = -1;
    private String userId = "";
    private String type = "";
    private SoftKeyboardStateHelper softKeyboardStateHelper;
    private SoftObserveListener softObserveListener;

    private AddTicketPresenter addTicketPresenter;
    private UpdateTicketPresenter updateTicketPresenter;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event_ticket_add);
//        userId = SharedPreferencesUtil.get(this, "userId", "");
//        initView();
//        setTextValue();
//        setListener();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event_ticket_add);
        userId = SharedPreferencesUtil.get(this, "userId", "");
        initView();
        setTextValue();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        softKeyboardStateHelper.removeSoftKeyboardStateListener(softObserveListener);
    }

    private void saveAndUpdateTicket() {
        if (NetUtil.isConnected(this)) {
            if (!TextUtils.isEmpty(et_ticketName.getText().toString()) && !TextUtils.isEmpty(et_ticketCount.getText().toString())) {//判断门票必填项是否为空
                if (type.equals("add")) {//添加门票
                    addTicketPresenter = new AddTicketPresenter(this);
                    addTicketPresenter.addTicket();
                } else {
                    updateTicketPresenter = new UpdateTicketPresenter(this);
                    updateTicketPresenter.updateTicket();
                }
            } else {
                setToast(getString(R.string.edit_ticket_info));
            }
        } else {
            setToast(getString(R.string.net_err));
        }

    }

    private void setToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void setTextValue() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        eventId = intent.getIntExtra("eventId", -1);
        if (type.equals("set")) {
            String ticetName = intent.getStringExtra("ticketName");
            float ticketPrice = intent.getFloatExtra("ticketPrice", -1);
            String strPrice = ticketPrice + "";
            String ticketCount = intent.getIntExtra("ticketCount", -1) + "";
            ticketAudit = intent.getIntExtra("ticketAudit", -1);
            ticketId = intent.getIntExtra("ticketId", -1);
            et_ticketName.setText(ticetName);
            et_ticketPrice.setText(strPrice);
            et_ticketCount.setText(ticketCount);
            if (ticketPrice != 0) {
                rl_ticketAudit.setVisibility(View.GONE);
                rl_ticketPrice.setVisibility(View.VISIBLE);
                switchIsFree.setChecked(true);
            } else {
                switchIsFree.setChecked(false);
                rl_ticketAudit.setVisibility(View.VISIBLE);
                rl_ticketPrice.setVisibility(View.GONE);
            }
            if (ticketAudit == 1) {
                switchIsAudit.setChecked(true);
            } else {
                switchIsAudit.setChecked(false);
            }
        }
    }

    private void setListener() {
        tset_back.setOnClickListener(this);
        tset_confirm.setOnClickListener(this);
        switchIsFree.setOnCheckedChangeListener(this);
        switchIsAudit.setOnCheckedChangeListener(this);
        ivClearCount.setOnClickListener(this);
        ivClearPrice.setOnClickListener(this);
        ivClearName.setOnClickListener(this);

        et_ticketName.addTextChangedListener(this);
        et_ticketPrice.addTextChangedListener(this);
        et_ticketCount.addTextChangedListener(this);

        softObserveListener = new SoftObserveListener();
        softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.ll_add_ticket));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softObserveListener);
    }

    private void initView() {
        tset_back = (AutoLinearLayout) findViewById(R.id.ll_event_tset_back);
        tset_confirm = (AutoLinearLayout) findViewById(R.id.ll_event_tset_confirm);
        switchIsFree = (SwitchButton) findViewById(R.id.is_free);
        switchIsAudit = (SwitchButton) findViewById(R.id.is_audit);
        rl_ticketPrice = (AutoRelativeLayout) findViewById(R.id.rl_set_ticket_price);
        rl_ticketAudit = (AutoRelativeLayout) findViewById(R.id.rl_is_audit);
        et_ticketName = (EditText) findViewById(R.id.et_event_ticket_name);
        et_ticketPrice = (EditText) findViewById(R.id.et_event_ticket_price);
        et_ticketCount = (EditText) findViewById(R.id.et_event_ticket_count);
        ivClearName = (ImageView) findViewById(R.id.iv_clear_ticket_name);
        ivClearPrice = (ImageView) findViewById(R.id.iv_clear_ticket_price);
        ivClearCount = (ImageView) findViewById(R.id.iv_clear_ticket_count);
    }

    private class SoftObserveListener implements SoftKeyboardStateHelper.SoftKeyboardStateListener {

        @Override
        public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        }

        @Override
        public void onSoftKeyboardClosed() {
            if (ivClearCount.getVisibility() == View.VISIBLE) {
                ivClearCount.setVisibility(View.GONE);
            }

            if (ivClearPrice.getVisibility() == View.VISIBLE) {
                ivClearPrice.setVisibility(View.GONE);
            }

            if (ivClearName.getVisibility() == View.VISIBLE) {
                ivClearName.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_event_tset_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_event_tset_confirm:
                saveAndUpdateTicket();
                break;
            case R.id.iv_clear_ticket_name:
                et_ticketName.setText("");
                break;
            case R.id.iv_clear_ticket_count:
                et_ticketCount.setText("");
                break;
            case R.id.iv_clear_ticket_price:
                et_ticketPrice.setText("");
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (switchIsFree.isChecked()) {
            rl_ticketAudit.setVisibility(View.GONE);
            rl_ticketPrice.setVisibility(View.VISIBLE);
        } else {
            rl_ticketAudit.setVisibility(View.VISIBLE);
            rl_ticketPrice.setVisibility(View.GONE);
        }

        if (switchIsAudit.isChecked()) {
            ticketAudit = 1;
        } else {
            ticketAudit = 0;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (et_ticketName.isFocused() && !TextUtils.isEmpty(et_ticketName.getText().toString())) {
            ivClearName.setVisibility(View.VISIBLE);
        } else {
            ivClearName.setVisibility(View.GONE);
        }

        if (et_ticketPrice.isFocused() && !TextUtils.isEmpty(et_ticketPrice.getText().toString())) {
            ivClearPrice.setVisibility(View.VISIBLE);
        } else {
            ivClearPrice.setVisibility(View.GONE);
        }

        if (et_ticketCount.isFocused() && !TextUtils.isEmpty(et_ticketCount.getText().toString())) {
            ivClearCount.setVisibility(View.VISIBLE);
        } else {
            ivClearCount.setVisibility(View.GONE);
        }

    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public int ticketId() {
        return ticketId;
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public String ticketName() {
        return et_ticketName.getText().toString();
    }

    @Override
    public String ticketCount() {
        return et_ticketCount.getText().toString();
    }

    @Override
    public String ticketPrice() {
        if (!TextUtils.isEmpty(et_ticketPrice.getText().toString())) {
            return et_ticketPrice.getText().toString();
        } else {
            return "0";
        }
    }

    @Override
    public int audit() {
        return ticketAudit;
    }

    @Override
    public void updateSuccess() {
        EventBus.getDefault().postSticky(new MsgEvent("fromReleaseEventAddTicket"));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void updateFailed(String errInfo) {
        setToast(errInfo);
    }

    @Override
    public void addSuccess() {
        EventBus.getDefault().postSticky(new MsgEvent("fromReleaseEventAddTicket"));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void addFailed(String errInfo) {
        setToast(errInfo);
    }
}
