package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.new_home.data.ProAndCity;
import com.bagevent.new_home.data.ProvinceBean;
import com.bagevent.new_home.data.RequireContentData;
import com.bagevent.new_home.new_interface.new_view.GetRequireSmsCodeView;
import com.bagevent.new_home.new_interface.new_view.RequireContentView;
import com.bagevent.new_home.new_interface.new_view.SummitRequireView;
import com.bagevent.new_home.new_interface.presenter.GetRequireContentPresenter;
import com.bagevent.new_home.new_interface.presenter.GetRequireSmsCodePresenter;
import com.bagevent.new_home.new_interface.presenter.SummitRequirePresenter;
import com.bagevent.register.data.GetSMSData;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.image_download.CityUtils;
import com.google.gson.Gson;
import com.pickerview.OptionsPickerView;
import com.pickerview.TimePickerView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zwj on 2016/9/7.
 */
public class ReleaseRequire extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener, TextWatcher,RequireContentView,SummitRequireView,GetRequireSmsCodeView {

    private AutoLinearLayout ll_require_back;
    private AutoLinearLayout ll_require_confirm;
    private AutoLinearLayout ll_require_confirm_phone;
    private ScrollView scRequire;
    private SwipeRefreshLayout requireRefresh;

    /**
     * 验证用户
     */
    private EditText phone;
    private EditText confirmCode;
    private ImageView clearPhone;
    private ImageView clearConfirmCode;
    private TextView getConfirmCode;
    private Button nextPage;

    /**
     * 发布需求
     */
    private AutoRelativeLayout rl_require_startTime;
    private AutoRelativeLayout rl_require_endTime;
    private AutoRelativeLayout rl_city;
    private TagFlowLayout flowLayout;
    private TextView tv_require_start_time;
    private TextView tv_require_end_time;
    private TextView tv_city;
    private TextView tempTime;
    private EditText personCount;
    private EditText require;
    private ImageView clearPersonCount;

    /**
     * 软键盘
     */
    private InputMethodManager inputMethodManager;

    /**
     * 时间地点控件
     */
    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;

    /**
     * 设置省市数据
     */
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ProAndCity cityData;

    /**
     * 获得需求内容
     */
    private GetRequireContentPresenter getRequireContentPresenter;
    private RequireContentData content;
    private String[] mVals = {};

    /**
     * 获取验证码、验证绑定的手机号
     */
    private String userId;
    private String cityName;

    /**
     * 发布需求
     */
    private SummitRequirePresenter summitRequirePresenter;
    private GetRequireSmsCodePresenter getRequireSmsCodePresenter;
    private String value = "";
    private String startTime = "";
    private String endTime = "";
    private String person = "";
    private String completeRequire = "";
    private String smsCode = "";

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_require);
//        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
//        initView();
//        setListener();
//        getUserInfo();
//        requireContent();
//        initTimeWidget();
//        getProCity();
//        initCityWidget();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_require);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
        initView();
        setListener();
        getUserInfo();
        requireContent();
        initTimeWidget();
        getProCity();
        initCityWidget();
    }

    private void getEventRequire() {
        value = "";
        boolean dateIslegal = false;//结束时间是否大于开始时间
        boolean infoIsComplete = false;//信息填写是否完整
        //获得各个输入项
        startTime = tv_require_start_time.getText().toString();
        endTime = tv_require_end_time.getText().toString();
        cityName = tv_city.getText().toString();
        person = personCount.getText().toString();
        completeRequire = require.getText().toString();
        if(flowLayout.getSelectedList().size() > 0) {//获得活动需求内容
            String s = "";
            infoIsComplete = true;
            for (int key : flowLayout.getSelectedList()) {
                s += key + "|";
            }
       //     Log.e("selectPos-->", s);
            String[] checked = s.split("\\|");
            for (int i = 0; i < checked.length; i++) {
                int index = Integer.parseInt(checked[i]);
                for (int j = 0; j < content.getRespObject().size(); j++) {

                    if(content.getRespObject().get(j).getName().equals(mVals[index])) {
                        value += content.getRespObject().get(j).getEventOfferTypeId()+",";
                    }
                }
               // value += mVals[index] + ",";
            }
            value = value.substring(0,value.length() - 1);
           // Log.e("value-->",value);

        }else {
            infoIsComplete = false;
        }

        if(!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime) && !TextUtils.isEmpty(cityName) &&!TextUtils.isEmpty(person) && !TextUtils.isEmpty(completeRequire)) {//各个输入项不允许为空
            infoIsComplete = true;
            if(CompareRex.compareStartAndEndTime(startTime,endTime)) {
                dateIslegal = true;
            }else {
                dateIslegal = false;
            }
        }else {
            infoIsComplete = false;
        }
        if(NetUtil.isConnected(this)) {
            if(infoIsComplete) {
                if(dateIslegal) {
                    summitRequirePresenter = new SummitRequirePresenter(this);
                    summitRequirePresenter.summitRequire();
                }else {
                    setToastMsg(getString(R.string.greater_then));
                }
            }else {
                setToastMsg(getString(R.string.add_demand));
            }
        }else {
            setToastMsg(getString(R.string.check_network2));
        }

    }

    private void setToastMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showPage() {
        ll_require_confirm_phone.setVisibility(View.GONE);
        scRequire.setVisibility(View.VISIBLE);
    }

    private void hidePage() {
        ll_require_confirm_phone.setVisibility(View.VISIBLE);
        scRequire.setVisibility(View.GONE);
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

    /**
     * 初始化省市选择器
     */
    private void initCityWidget() {
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, true);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
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
                        + " "+ options2Items.get(options1).get(option2);
                tv_city.setText(tx);
                tv_city.setText(tx);
                //  vMasker.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 初始化时间选择器
     */
    private void initTimeWidget() {
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 20);//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tempTime.setText(getTime(date));
            }
        });
    }

    private void requireContent() {
        if(NetUtil.isConnected(this)) {
            getRequireContentPresenter = new GetRequireContentPresenter(this);
            getRequireContentPresenter.getContent();
        }else {
            setToastMsg(getString(R.string.check_network2));
        }

    }

    private void initFlowLayout() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
       // Log.e("fdf",mVals.length+"F");
        flowLayout.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = mInflater.inflate(R.layout.release_require_tag,
                        flowLayout, false);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tv.setText(s);
                return view;
            }
        });

    }

    private void setListener() {
        requireRefresh.setOnRefreshListener(this);
        ll_require_back.setOnClickListener(this);
        ll_require_confirm.setOnClickListener(this);
        rl_city.setOnClickListener(this);
        rl_require_startTime.setOnClickListener(this);
        rl_require_endTime.setOnClickListener(this);
        getConfirmCode.setOnClickListener(this);
        nextPage.setOnClickListener(this);
        clearConfirmCode.setOnClickListener(this);
        clearPhone.setOnClickListener(this);
        clearPersonCount.setOnClickListener(this);
        phone.addTextChangedListener(this);
        confirmCode.addTextChangedListener(this);
        personCount.addTextChangedListener(this);
    }

    private void initView() {
        ll_require_back = (AutoLinearLayout) findViewById(R.id.ll_require_back);
        ll_require_confirm = (AutoLinearLayout) findViewById(R.id.ll_require_confirm);
        ll_require_confirm_phone = (AutoLinearLayout) findViewById(R.id.ll_require_confirm_phone);
        scRequire = (ScrollView) findViewById(R.id.sc_require);
        requireRefresh = (SwipeRefreshLayout) findViewById(R.id.require_refresh);

        phone = (EditText) findViewById(R.id.et_require_phone);
        confirmCode = (EditText) findViewById(R.id.et_require_confirm_code);
        clearPhone = (ImageView) findViewById(R.id.iv_require_clear_phone);
        clearConfirmCode = (ImageView) findViewById(R.id.iv_require_clear_confirm_code);
        nextPage = (Button) findViewById(R.id.btn_require_next);
        getConfirmCode = (TextView) findViewById(R.id.tv_get_confirm_code);

        rl_city = (AutoRelativeLayout) findViewById(R.id.rl_city);
        rl_require_endTime = (AutoRelativeLayout) findViewById(R.id.require_end_time);
        rl_require_startTime = (AutoRelativeLayout) findViewById(R.id.require_start_time);
        tv_require_start_time = (TextView) findViewById(R.id.tv_require_start_time);
        tv_require_end_time = (TextView) findViewById(R.id.tv_require_end_time);
        tv_city = (TextView) findViewById(R.id.tv_city);
        flowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
        personCount = (EditText) findViewById(R.id.et_person_count);
        require = (EditText) findViewById(R.id.et_require);
        clearPersonCount = (ImageView) findViewById(R.id.iv_clear_person_count);

    }

    private void getUserInfo() {
       String cellphone = SharedPreferencesUtil.get(this, "cellphone", "");
      //  Log.e("cellphone-->",cellphone);
        userId = SharedPreferencesUtil.get(this, "userId", "");
        if (!TextUtils.isEmpty(cellphone)) {//判断用户手机号是否为空,为空则需要进行短信确认
            showPage();
        } else {
            hidePage();
        }
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return format.format(date);
    }

    @Override
    public void onRefresh() {
        requireContent();
        requireRefresh.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_require_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_require_confirm:
              //  setToastMsg("请选择会议需求");
                getEventRequire();
                break;
            case R.id.rl_city://选择城市
                pvOptions.show();
                break;
            case R.id.require_start_time://开始时间
                tempTime = tv_require_start_time;
                pvTime.show();
                break;
            case R.id.require_end_time://结束时间
                tempTime = tv_require_end_time;
                pvTime.show();
                break;
            case R.id.tv_get_confirm_code://获取验证码
                if (!TextUtils.isEmpty(phone.getText().toString())) {
                    getRequireSmsCodePresenter = new GetRequireSmsCodePresenter(this);
                    getRequireSmsCodePresenter.getSms();
                } else {
                    setToastMsg(getString(R.string.input_phone));
                }
                break;
            case R.id.btn_require_next:
                if (phone.isFocused()) {//点击下一步首先隐藏软键盘
                    inputMethodManager.hideSoftInputFromWindow(phone.getWindowToken(), 0);
                } else {
                    inputMethodManager.hideSoftInputFromWindow(confirmCode.getWindowToken(), 0);
                }

                if(!TextUtils.isEmpty(phone.getText().toString()) &&  !TextUtils.isEmpty(confirmCode.getText().toString())) {
                    if(confirmCode.getText().toString().equals(smsCode)) {
                        showPage();
                    }
                }

                break;
            case R.id.iv_require_clear_phone://清空手机号
                phone.setText("");
                break;
            case R.id.iv_require_clear_confirm_code://清空验证码
                confirmCode.setText("");
                break;
            case R.id.iv_clear_person_count://清空人数
                personCount.setText("");
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
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(phone.getText().toString())) {
            clearPhone.setVisibility(View.VISIBLE);
        } else {
            clearPhone.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(confirmCode.getText().toString())) {
            clearConfirmCode.setVisibility(View.VISIBLE);
        } else {
            clearConfirmCode.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(personCount.getText().toString())) {
            clearPersonCount.setVisibility(View.VISIBLE);
        } else {
            clearPersonCount.setVisibility(View.GONE);
        }
    }

    /**
     * 获得验证码
     *
     * @return
     */


    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String phoneNum() {
        return phone.getText().toString();
    }

    @Override
    public int source() {
        return 3;
    }

    @Override
    public void showSmsCode(GetSMSData response) {
        setToastMsg(getString(R.string.send_success));
        smsCode = response.getReturnObj();
    }

    @Override
    public void showSmsFailed(String errInfo) {

    }

    /**
     * 验证绑定手机号
     *
     * @return
     */
    @Override
    public String cellPhone() {
        return phone.getText().toString();
    }

    @Override
    public String randomCode() {
        return confirmCode.getText().toString();
    }

    @Override
    public String userId() {
      //  Log.e("userId-->",userId);
        return userId;
    }


    /**
     * 获得活动需求内容
     * @param response
     */
    @Override
    public void showRequireContent(RequireContentData response) {
        content = response;
        int size = response.getRespObject().size();
        if(size > 0) {
            mVals = new String[size];
            for (int i = 0; i < size; i++) {
                mVals[i] = response.getRespObject().get(i).getName();
            }
            initFlowLayout();
        }

    }

    @Override
    public void showRequireContentFailed(String errInfo) {

    }

    /**
     * 发布需求
     */
    @Override
    public String demandStartTime() {
        return startTime;
    }

    @Override
    public String demandEndTime() {
        return endTime;
    }

    @Override
    public String cityName() {
        return cityName;
    }

    @Override
    public String demandOfPerson() {
        return person;
    }

    @Override
    public String demandOtherRequire() {
        return completeRequire;
    }

    @Override
    public String type() {
        return value;
    }

    @Override
    public void summitRequireSuccess() {
        setToastMsg(getString(R.string.release_success));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void summitRequireFailed(String errInfo) {
        setToastMsg(errInfo);
    }


}
