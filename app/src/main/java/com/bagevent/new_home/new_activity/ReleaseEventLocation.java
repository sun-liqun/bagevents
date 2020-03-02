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
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.new_home.data.ProAndCity;
import com.bagevent.new_home.data.ProvinceBean;
import com.bagevent.new_home.new_interface.new_view.SaveEventLocationView;
import com.bagevent.new_home.new_interface.presenter.SaveLocationPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.image_download.CityUtils;
import com.google.gson.Gson;
import com.pickerview.OptionsPickerView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by zwj on 2016/9/14.
 */
public class ReleaseEventLocation extends BaseActivity implements View.OnClickListener, TextWatcher, SaveEventLocationView {

    private AutoLinearLayout ll_event_location_back;
    private AutoLinearLayout ll_event_location_confirm;
    private AutoRelativeLayout event_location;
    private EditText etEventLocation;
    private ImageView ivEventClearLocation;
    private TextView tvEventLoaction;

    /**
     * 城市选择
     */
    private OptionsPickerView pvOptions;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ProAndCity cityData;

    private InputMethodManager inputMethodManager;

    private int eventId = -1;
    private String userId = "";
    private String textName = "";
    private SaveLocationPresenter saveLocationPresenter;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event_location);
//        userId = SharedPreferencesUtil.get(this, "userId", "");
//        Intent intent = getIntent();
//        eventId = intent.getIntExtra("eventId", -1);
//        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
//        initView();
//        getProCity();
//        initCityWidget();
//        setListener();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event_location);
        userId = SharedPreferencesUtil.get(this, "userId", "");
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", -1);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
        initView();
        getProCity();
        initCityWidget();
        setListener();
    }

    private void saveValue() {
        if (!TextUtils.isEmpty(tvEventLoaction.getText().toString())) {
            if (NetUtil.isConnected(this)) {
                textName = "address";
                saveLocationPresenter = new SaveLocationPresenter(this);
                saveLocationPresenter.saveLocation();
            } else {
                setToast(getString(R.string.net_err));
            }
        } else {
            setToast(getString(R.string.edit_address));
        }
    }

    private void setToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 初始化省市选择器
     */
    private void initCityWidget() {
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, true);
        //设置选择的三级单位
        pvOptions.setLabels(getString(R.string.province), getString(R.string.city1), getString(R.string.area));
        pvOptions.setTitle(getString(R.string.select_city));
        pvOptions.setCyclic(false, true, true);
        pvOptions.setCancelable(true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(0, 0, 0);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + " " + options2Items.get(options1).get(option2);
                tvEventLoaction.setText(tx);
                //  vMasker.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 解析省市数据
     */
    private void getProCity() {
        String city = CityUtils.getCity();
        Gson gson = new Gson();
        cityData = gson.fromJson(city, ProAndCity.class);
        for (int i = 0; i < cityData.getProvinces().size(); i++) {
            ArrayList<String> citys = new ArrayList<>();
            ProAndCity.ProvincesBean temp = cityData.getProvinces().get(i);
            options1Items.add(new ProvinceBean(0, temp.getProvinceName()));
            for (int j = 0; j < temp.getCitys().size(); j++) {
                citys.add(temp.getCitys().get(j).getCitysName());
            }
            options2Items.add(citys);
        }
    }

    private void setListener() {
        ll_event_location_back.setOnClickListener(this);
        ll_event_location_confirm.setOnClickListener(this);
        event_location.setOnClickListener(this);
        ivEventClearLocation.setOnClickListener(this);
        etEventLocation.addTextChangedListener(this);
    }

    private void initView() {
        ll_event_location_back = (AutoLinearLayout) findViewById(R.id.ll_event_location_back);
        ll_event_location_confirm = (AutoLinearLayout) findViewById(R.id.ll_event_location_confirm);
        event_location = (AutoRelativeLayout) findViewById(R.id.event_location);
        etEventLocation = (EditText) findViewById(R.id.et_event_location);
        ivEventClearLocation = (ImageView) findViewById(R.id.iv_clear_location);
        tvEventLoaction = (TextView) findViewById(R.id.tv_event_location);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear_location:
                etEventLocation.setText("");
                break;
            case R.id.event_location:
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(etEventLocation.getWindowToken(), 0);
                }
                pvOptions.show();
                break;
            case R.id.ll_event_location_back:
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(etEventLocation.getWindowToken(), 0);
                }
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_event_location_confirm:
                saveValue();
                break;
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
        if (!TextUtils.isEmpty(etEventLocation.getText().toString())) {
            ivEventClearLocation.setVisibility(View.VISIBLE);
        } else {
            ivEventClearLocation.setVisibility(View.GONE);
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
    public String userIds() {
        return userId;
    }

    @Override
    public String textName() {
        return textName;
    }

    @Override
    public int addrType() {
        return 2;
    }

    @Override
    public String address() {
        if (!TextUtils.isEmpty(etEventLocation.getText().toString())) {
            return tvEventLoaction.getText().toString() + " " + etEventLocation.getText().toString();
        } else {
            return tvEventLoaction.getText().toString();
        }
    }

    @Override
    public void showSaveLocationSuccess() {
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(etEventLocation.getWindowToken(), 0);
        }
        EventBus.getDefault().postSticky(new MsgEvent("fromChildPage"));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void showSaveLocationFailed(String errInfo) {
        setToast(errInfo);
    }
}
