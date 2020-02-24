package com.bagevent.activity_manager.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.db.Attends_Table;
import com.bagevent.new_home.data.ProAndCity;
import com.bagevent.new_home.data.ProvinceBean;
import com.bagevent.util.TimeUtil;
import com.bagevent.util.dbutil.DBUtil;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.AddDatePicker;
import com.bagevent.activity_manager.manager_fragment.data.AddEditTextList;
import com.bagevent.activity_manager.manager_fragment.data.AddRadioButton;
import com.bagevent.activity_manager.manager_fragment.data.AddViewList;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.data.ModifyData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.ModifyUserInfoPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.UpLoadImagePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.ModifyUserInfoView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpLoadImageView;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.IsPhoneNum;
import com.bagevent.util.NetUtil;
import com.bagevent.util.image_download.CityUtils;
import com.bagevent.view.wheelview.OnWheelScrollListener;
import com.bagevent.view.wheelview.WheelView;
import com.bagevent.view.wheelview.adapter.NumericWheelAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.pickerview.OptionsPickerView;
import com.pickerview.TimePickerView;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zwj on 2016/6/30.
 */
public class ModifyUserInfo extends BaseActivity implements ModifyUserInfoView, UpLoadImageView {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right2)
    ImageView ivRight2;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_right_click)
    AutoLinearLayout llRightClick;
    private LinearLayout mGroupLayout;
    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ProAndCity cityData;


    private int mEventId = -1;
    private int mAttendId = -1;
    private int mTicketId = -1;
    private String mRef_code = "";
    private String mFormType = "";
    private int position = -1;
    private FormType mType = null;
    private static final int FORM_TEXT_SIZE = 12;
    private static final int TEXT_SIZE = 16;


    /**
     * 定义存储控件的list
     */
    private List<AddViewList> mViewList = new ArrayList<AddViewList>();//多选框
    private List<AddEditTextList> mEditTextList = new ArrayList<AddEditTextList>();//editTExt
    private List<AddRadioButton> mRadioButtonList = new ArrayList<AddRadioButton>();//单选按钮
    private List<AddRadioButton> mSpinnerList = new ArrayList<AddRadioButton>();//单选按钮
    private AddDatePicker addDatePicker;
    private AddViewList addkBox;
    private AddEditTextList addEdit;
    private String checkboxValue = "";
    private Map<String, String> map = new HashMap<String, String>();//用于离线修改参会人员信息
    private Map<String, String> add = new HashMap<String, String>();//用于离线修改现场添加的参会人员信息
    private Map<String, Map<String, String>> addMap = new HashMap<String, Map<String, String>>();//用于离线修改现场添加的参会人员信息
    private Map<String, String> allInfo = new HashMap<String, String>();//用于存储表单中的所有信息,用于更换gsonUser
    private String str;//提交到服务器的现场添加参会人员信息
    private String strAdd = "";//现场添加参会人员的字符串
    private String strAllInfo = "";
    private ModifyUserInfoPresenter modifyPresenter;
    private InputMethodManager imm;

    private Attends attends = null;
    private String strNone = "";
    private String date = "";


    private LayoutInflater inflater = null;
    private WheelView year;
    private WheelView month;
    private WheelView day;
    View view = null;

    private String userName;
    private int formFiledId = -1;//日期的filedId

    /**
     * 图片选择器
     */
    private final int REQUEST_CODE_GALLERY = 1001;
    private File uploadFile = null;
    private String userId = "";
    private UpLoadImagePresenter upLoadImagePresenter;
    private ImageView attendee_avatar;
    private boolean isContainPic = false;//判断是否包含图像采集
    private String imgPath = "";//保存图片路径
    private String imgFormField;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        Intent intent = getIntent();
        mEventId = intent.getIntExtra("eventId", 0);
        mAttendId = intent.getIntExtra("attendId", 0);
        mRef_code = intent.getStringExtra("ref_code");
        mTicketId = intent.getIntExtra("ticketId", 0);
        position = intent.getIntExtra("position", 0);
        mFormType = (String) NetUtil.readObject(this, Constants.FORM_FILE_NAME + mEventId + "");//从文件中获得表单类型
        mType = new Gson().fromJson(mFormType, FormType.class);//解析表单数据
        if (mAttendId != 0) {
            attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.attendId.is(mAttendId)).querySingle();
        } else {
            attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.refCodes.is(mRef_code)).querySingle();
        }
        modifyPresenter = new ModifyUserInfoPresenter(this);
        initView();
        getProCity();
    }

    private void initView() {
        setContentView(R.layout.attend_modify_user_info);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), Constants.STATUS_ALPHA);
        tvTitle.setText(R.string.change_attend_meeting_info);
        ivRight2.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        Glide.with(this).load(R.mipmap.confirm).into(ivRight);
        mGroupLayout = (LinearLayout) findViewById(R.id.ll_parent_viewgroup);
        // ll_modify_confirm = (AutoLinearLayout) findViewById(R.id.ll_modify_confirm);
        // ll_modify_back = (AutoLinearLayout) findViewById(R.id.ll_modify_back);
        int count = mType.getRespObject().size();//获得表单的数据长度，然后从中逐一获取数据
        LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RadioGroup.LayoutParams rbParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);//单选框
        LinearLayout.LayoutParams cbParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);//多选框
        LinearLayout.LayoutParams horLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//水平
        FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(134, 134);
        for (int i = 0; i < count; i++) {
            FormType.RespObjectBean form = mType.getRespObject().get(i);
            if (form.getFieldTypeName().contains("username")) {//姓名
                initTextView(form, horLp, form.getRequired(), 25, 25);
                initEditText(form, tvLp, 25, 10);//editeText的id为 1　＋ i;
                initViewLine(lps, 10);
                addEdit = new AddEditTextList();
                addEdit.setShowName("username");
            } else if (form.getFieldTypeName().contains("radio")) {
                initTextView(form, horLp, form.getRequired(), 25, 25);
                for (int j = 0; j < form.getOptionItems().size(); j++) {
                    initRadioButton(mRadioButtonList, rbParams, form, j, 25, 25);
                }
                initViewLine(lps, 10);
            } else if (form.getFieldTypeName().contains("sex")) {
                initTextView(form, horLp, form.getRequired(), 25, 25);
                initSexRadioButton(mRadioButtonList, rbParams, form, getString(R.string.male), 25, 25);
                initSexRadioButton(mRadioButtonList, rbParams, form, getString(R.string.female), 25, 25);
                initViewLine(lps, 10);
            } else if (form.getFieldTypeName().contains("checkbox")) {
                initTextView(form, horLp, form.getRequired(), 25, 15);
                for (int a = 0; a < form.getOptionItems().size(); a++) {
                    initCheckBox(cbParams, form, a, 25, 20);
                }
                addkBox = new AddViewList();
                addkBox.setFieldId(form.getFormFieldId());
                initViewLine(lps, 10);
            } else if (form.getFieldTypeName().contains("select")) {
                initTextView(form, horLp, form.getRequired(), 25, 15);
                for (int m = 0; m < form.getOptionItems().size(); m++) {
                    initRadioButton(mSpinnerList, rbParams, form, m, 25, 20);
                }
                initViewLine(lps, 10);
            } else if (form.getFieldTypeName().contains("attendee_avatar")) {
                initTextView(form, horLp, form.getRequired(), 25, 15);
                initImageView(imgParams, form);
                initViewLine(lps, 10);
            } else {
                initTextView(form, horLp, form.getRequired(), 25, 15);
                initEditText(form, tvLp, 25, 10);
                initViewLine(lps, 10);
            }
        }
    }

    /**
     * 初始化省市选择器
     */
    private void initCityWidget(final EditText editText) {
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, true);
        //设置选择的三级单位
        pvOptions.setLabels(" ", " ", " ");
        pvOptions.setTitle(getString(R.string.select_city));
        pvOptions.setCyclic(false, false, false);
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
                editText.setText(tx);
            }
        });
        pvOptions.show();
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

    private void initTimeWidget(final EditText editText, int type) {
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 20);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(false);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                //Log.e("fdasf",date.getTime()+"");
                editText.setText(getTime(date));
            }
        });
        pvTime.show();
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(date);
    }


    private void initTextView(FormType.RespObjectBean form, LinearLayout.LayoutParams lp, int required, int left, int top) {
        LinearLayout lin = new LinearLayout(this);
        LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lin.setLayoutParams(par);
        lin.setOrientation(LinearLayout.HORIZONTAL);

        TextView tv = new TextView(this);

        tv.setText("*");
        tv.setTextColor(ContextCompat.getColor(this, R.color.fe6900));
        tv.setVisibility(View.GONE);

        TextView tv_form_userName = new TextView(this);
        par.setMargins(0, 25, 0, 0);
        tv_form_userName.setLayoutParams(lp);
        tv_form_userName.setPadding(left, 0, 0, 0);
        if (form.getRequired() == 1) {
            tv_form_userName.setText(form.getShowName());
            tv.setVisibility(View.VISIBLE);
        } else {
            tv_form_userName.setText(form.getShowName());
            tv.setVisibility(View.GONE);
        }
        tv_form_userName.setTextSize(FORM_TEXT_SIZE);
        tv_form_userName.setTextColor(ContextCompat.getColor(this, R.color.a797d7f));
        lin.addView(tv_form_userName);
        lin.addView(tv);
        mGroupLayout.addView(lin);
    }

    private void initEditText(FormType.RespObjectBean form, LinearLayout.LayoutParams lp, int left, int top) {
        AddEditTextList addEditText = new AddEditTextList();
        EditText et_userName = new EditText(this);
        if (form.getFieldTypeName().contains("mobile_phone")) {
            et_userName.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        et_userName.setId(View.generateViewId());
        addEditText.setRequired(form.getRequired());
        addEditText.setEditText(et_userName);
        addEditText.setFiledName(form.getFieldName());
        addEditText.setFieldId(form.getFormFieldId());
        addEditText.setFiledTypeName(form.getFieldTypeName());
        mEditTextList.add(addEditText);
        lp.setMargins(0, 30, 0, 10);
        et_userName.setLayoutParams(lp);
        et_userName.setPadding(left, 0, 0, 0);
        try {
            String text = userInfo(form.getFormFieldId());
            et_userName.setText(text);
            et_userName.setSelection(text.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        et_userName.setBackground(null);
        et_userName.setTextSize(TEXT_SIZE);
        et_userName.setTextColor(ContextCompat.getColor(this, R.color.black));
        // et_userName.setSingleLine(true);
        if (form.getFieldTypeName().contains("date") || form.getFieldTypeName().contains("country")) {
            //et_userName.setInputType(InputType.TYPE_NULL);
            et_userName.setFocusableInTouchMode(false);
        }
        mGroupLayout.addView(et_userName);

        et_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mEditTextList.size(); i++) {
                    //Log.e("id",mEditTextList.get(i).getEditText().getId() +" " + v.getId() + " " + mEditTextList.get(i).getFiledName());
                    if (mEditTextList.get(i).getEditText().getId() == v.getId()) {
                        if (mEditTextList.get(i).getFiledTypeName().contains("date")) {
                            initTimeWidget(mEditTextList.get(i).getEditText(), 0);
                        } else if (mEditTextList.get(i).getFiledTypeName().contains("country")) {
                            initCityWidget(mEditTextList.get(i).getEditText());
                        }
                    }
                }
            }
        });
    }

    private void initImageView(FrameLayout.LayoutParams params, FormType.RespObjectBean form) {
        RelativeLayout relative = new RelativeLayout(this);
        relative.setGravity(Gravity.LEFT);
        attendee_avatar = new ImageView(this);
        attendee_avatar.setId(View.generateViewId());
        attendee_avatar.setBackgroundResource(R.drawable.imageviewboundshape);
        RelativeLayout.LayoutParams rp1 = new RelativeLayout.LayoutParams(134, 134);
        rp1.setMargins(25, 20, 0, 20);
        rp1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        rp1.addRule(RelativeLayout.ALIGN_PARENT_START);
        relative.addView(attendee_avatar, rp1);
        mGroupLayout.addView(relative);

        String imgUrl = "";
        try {
            imgUrl = userInfo(form.getFormFieldId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imgFormField = form.getFormFieldId() + "";
        allInfo.put(form.getFormFieldId() + "", imgUrl);
        if (!TextUtils.isEmpty(imgUrl)) {
            map.put(form.getFormFieldId() + "", imgUrl);
            add.put(form.getFieldName(), imgUrl);
            attendee_avatar.setVisibility(View.VISIBLE);
            if (NetUtil.isConnected(this)) {
                RequestOptions options=new RequestOptions()
                        .error(R.mipmap.icon)
                        .centerCrop();
                Glide.with(this).load(Constants.imgsURL + imgUrl).apply(options).into(attendee_avatar);
            } else {//注释掉部分为添加图片
                String path = "";
                /*if(imgUrl.contains("luban_disk_cache")) {
                    path = imgUrl;
                }else {*/
                path = Environment.getExternalStorageDirectory() + "/bageventCacheImage" + "/" + imgUrl.replace("/", "_");
                //  }
                File file = new File(path);
                RequestOptions options=new RequestOptions()
                        .centerCrop();
                Glide.with(this).load(file).apply(options).into(attendee_avatar);
            }
        }

        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(ModifyUserInfo.this,"选择图片",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initViewLine(LinearLayout.LayoutParams lps, int top) {
        View view_line = new View(this);
        lps.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lps.height = 1;
        lps.setMargins(0, top, 0, 0);
        view_line.setBackgroundColor(ContextCompat.getColor(this, R.color.e5e5e5));
        view_line.setLayoutParams(lps);
        mGroupLayout.addView(view_line);
    }


    private void initSexRadioButton(final List<AddRadioButton> mRadioButtonList, RadioGroup.LayoutParams rbParams, FormType.RespObjectBean form, String name, int left, int top) {
        CheckBox rb = new CheckBox(this);
        rb.setId(View.generateViewId());
        rbParams.setMargins(left, top, 0, 0);
        rb.setLayoutParams(rbParams);
        rb.setText(name);
        rb.setButtonDrawable(R.drawable.radiobtn_select);
        try {
            JSONObject obj = new JSONObject(attends.gsonUser);
            if (obj.getString(form.getFormFieldId() + "").equals(name)) {
                rb.setChecked(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rb.setPaddingRelative(15, 0, 0, 0);
        mGroupLayout.addView(rb, rbParams);

        AddRadioButton radioButton = new AddRadioButton();
        radioButton.setCheckBoxId(rb.getId());
        radioButton.setRadioButton(rb);
        radioButton.setRequired(form.getRequired());
        radioButton.setFieldId(form.getFormFieldId());
        radioButton.setShowName(form.getShowName());
        radioButton.setFieldName(form.getFieldName());
        mRadioButtonList.add(radioButton);

        rb.setTag(form.getFormFieldId());
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int fieldId = (int) buttonView.getTag();
                if (isChecked) {
                    for (int i = 0; i < mRadioButtonList.size(); i++) {
                        if (buttonView.getId() == mRadioButtonList.get(i).getCheckBoxId() && fieldId == mRadioButtonList.get(i).getFieldId()) {
                            mRadioButtonList.get(i).getRadioButton().setChecked(true);
                        } else if (fieldId == mRadioButtonList.get(i).getFieldId()) {
                            mRadioButtonList.get(i).getRadioButton().setChecked(false);
                        }
                    }
                }
            }
        });
    }

    private void initRadioButton(final List<AddRadioButton> mRadioButtonList, RadioGroup.LayoutParams rbParams, FormType.RespObjectBean form, int pos, int left, int top) {
        CheckBox rb = new CheckBox(this);
        rb.setId(View.generateViewId());
        rbParams.setMargins(left, top, 0, 0);
        rb.setLayoutParams(rbParams);
        rb.setText(form.getOptionItems().get(pos).getValue());
        rb.setButtonDrawable(R.drawable.radiobtn_select);
        try {
            JSONObject obj = new JSONObject(attends.gsonUser);
            if (obj.getString(form.getFormFieldId() + "").equals(form.getOptionItems().get(pos).getValue())) {
                rb.setChecked(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rb.setPaddingRelative(15, 0, 0, 0);
        mGroupLayout.addView(rb, rbParams);

        AddRadioButton radioButton = new AddRadioButton();
        radioButton.setCheckBoxId(rb.getId());
        radioButton.setRadioButton(rb);
        radioButton.setRequired(form.getRequired());
        radioButton.setFieldId(form.getFormFieldId());
        radioButton.setShowName(form.getShowName());
        radioButton.setFieldName(form.getFieldName());
        mRadioButtonList.add(radioButton);

        rb.setTag(form.getFormFieldId());
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int fieldId = (int) buttonView.getTag();
                if (isChecked) {
                    for (int i = 0; i < mRadioButtonList.size(); i++) {
                        if (buttonView.getId() == mRadioButtonList.get(i).getCheckBoxId() && fieldId == mRadioButtonList.get(i).getFieldId()) {
                            mRadioButtonList.get(i).getRadioButton().setChecked(true);
                        } else if (fieldId == mRadioButtonList.get(i).getFieldId()) {
                            mRadioButtonList.get(i).getRadioButton().setChecked(false);
                        }
                    }
                }
            }
        });
    }

    private void initCheckBox(LinearLayout.LayoutParams cbParams, FormType.RespObjectBean form, int pos, int left, int top) {
        AddViewList addCheckBox = new AddViewList();
        final CheckBox cb = new CheckBox(this);
        cb.setId(View.generateViewId());
        addCheckBox.setCheckBox(cb);
        addCheckBox.setRequired(form.getRequired());
        addCheckBox.setFieldId(form.getFormFieldId());
        addCheckBox.setShowName(form.getShowName());
        addCheckBox.setOptionItems(form.getOptionItems().get(pos).getKey());
        mViewList.add(addCheckBox);
        cb.setText(form.getOptionItems().get(pos).getValue());
        cbParams.setMargins(left, top, 0, 0);
        cb.setPaddingRelative(15, 0, 0, 0);
        cb.setButtonDrawable(R.drawable.checkbox_select);
        try {
            JSONObject obj = new JSONObject(attends.gsonUser);
            if (obj.getString(form.getFormFieldId() + "").contains(form.getOptionItems().get(pos).getValue())) {
                cb.setChecked(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mGroupLayout.addView(cb, cbParams);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });

    }

    /**
     * 获得attendeeMap中的数据
     *
     * @param filedId
     * @return
     * @throws JSONException
     */
    private String userInfo(int filedId) throws JSONException {
        String info = "";
        if (attends != null) {
            JSONObject object = new JSONObject(attends.gsonUser);
            info = object.getString(filedId + "");
        }
        return info;
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public int eventId() {
        return mEventId;
    }

    @Override
    public String attendId() {
        return mAttendId + "";
    }

    @Override
    public String infoMap() {
        Log.e("modify", str);
        return str;
    }

    @Override
    public void showModifySuccess(ModifyData modifyData) {
        Toast.makeText(this, R.string.change_success, Toast.LENGTH_SHORT).show();
        DBUtil.updateModify(mEventId, mAttendId, strAllInfo, str, isContainPic, imgPath, mRef_code, Constants.MODIFY_SYNC);
        EventBus.getDefault().postSticky(new MsgEvent(position, mAttendId, Common.MODIFY_ATTNEDEE_SUCCESS));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void showModifyFailed(ModifyData modifyData) {
        Toast.makeText(this, R.string.change_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public File upLoadFile() {
        return uploadFile;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public void uploadSuccess(String response) {
        imgPath = response;
    }

    @Override
    public void uploadFailed(String response) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.ll_title_back, R.id.ll_right_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_right_click:
                int none = 0;
                String radioNone = "";
                String spinnerNone = "";
                String checkNone = "";
                for (int e = 0; e < mEditTextList.size(); e++) { //遍历所有editText
                    if (mEditTextList.get(e).getRequired() == 1) {
                        if (!mEditTextList.get(e).getEditText().getText().toString().isEmpty()) {
                            if (mEditTextList.get(e).getFiledName().contains("username")) {
                                userName = mEditTextList.get(e).getEditText().getText().toString();
                                if (!CompareRex.isContainsNum(userName)) {
                                    map.put(mEditTextList.get(e).getFieldId() + "", mEditTextList.get(e).getEditText().getText().toString());
                                    add.put(mEditTextList.get(e).getFiledName(), mEditTextList.get(e).getEditText().getText().toString());
                                } else {
                                    none = 3;
                                }
                            } else if (mEditTextList.get(e).getFiledName().contains("email_address")) {
                                String s = mEditTextList.get(e).getEditText().getText().toString();
                                if (CompareRex.checkEmail(s)) {
                                    map.put(mEditTextList.get(e).getFieldId() + "", s);
                                    add.put(mEditTextList.get(e).getFiledName(), s);
                                } else {
                                    none = 2;
                                }
                            } else if (mEditTextList.get(e).getFiledName().contains("mobile_phone")) {
                                String s = mEditTextList.get(e).getEditText().getText().toString();
                                if (IsPhoneNum.isPhone(s)) {
                                    map.put(mEditTextList.get(e).getFieldId() + "", s);
                                    add.put(mEditTextList.get(e).getFiledName(), s);
                                } else {
                                    none = 4;
                                }
                            } else {
                                map.put(mEditTextList.get(e).getFieldId() + "", CompareRex.replaceBlank(mEditTextList.get(e).getEditText().getText().toString()));
                                String s = mEditTextList.get(e).getEditText().getText().toString();
                                if (!TextUtils.isEmpty(s)) {
                                    add.put(mEditTextList.get(e).getFiledName(), s);
                                }
                            }
                        } else {
                            none = 1;
                        }
                    } else {
                        if (!mEditTextList.get(e).getEditText().getText().toString().isEmpty()) {
                            if (mEditTextList.get(e).getFiledName().contains("username")) {
                                userName = mEditTextList.get(e).getEditText().getText().toString();
                                map.put(mEditTextList.get(e).getFieldId() + "", mEditTextList.get(e).getEditText().getText().toString());
                                add.put(mEditTextList.get(e).getFiledName(), mEditTextList.get(e).getEditText().getText().toString());
                            } else if (mEditTextList.get(e).getFiledName().contains("email_address")) {
                                String s = mEditTextList.get(e).getEditText().getText().toString();
                                if (CompareRex.checkEmail(s)) {
                                    map.put(mEditTextList.get(e).getFieldId() + "", s);
                                    add.put(mEditTextList.get(e).getFiledName(), mEditTextList.get(e).getEditText().getText().toString());
                                } else {
                                    none = 2;
                                }
                            } else if (mEditTextList.get(e).getFiledName().contains("mobile_phone")) {
                                String s = mEditTextList.get(e).getEditText().getText().toString();
                                if (IsPhoneNum.isPhone(s)) {
                                    map.put(mEditTextList.get(e).getFieldId() + "", s);
                                    add.put(mEditTextList.get(e).getFiledName(), s);
                                } else {
                                    none = 4;
                                }
                            } else {
                                map.put(mEditTextList.get(e).getFieldId() + "", CompareRex.replaceBlank(mEditTextList.get(e).getEditText().getText().toString()));
                                String s = mEditTextList.get(e).getEditText().getText().toString();
                                if (!TextUtils.isEmpty(s)) {
                                    add.put(mEditTextList.get(e).getFiledName(), s);
                                }
                            }
                        } else {
                            map.remove(mEditTextList.get(e).getFieldId() + "");
                        }
                    }
                    allInfo.put(mEditTextList.get(e).getFieldId() + "", CompareRex.replaceBlank(mEditTextList.get(e).getEditText().getText().toString()));
                }
                String s = "";
                for (int r = 0; r < mRadioButtonList.size(); r++) { //遍历所有radiobutton

                    if (mRadioButtonList.get(r).getRadioButton().isChecked()) {
                        s = mRadioButtonList.get(r).getRadioButton().getText().toString();
                    }
                    if (mRadioButtonList.get(r).getRequired() == 1) {
                        if (!s.isEmpty()) {
                            map.put(mRadioButtonList.get(r).getFieldId() + "", s);
                            add.put(mRadioButtonList.get(r).getFieldName(), s);
                            allInfo.put(mRadioButtonList.get(r).getFieldId() + "", s);
                            radioNone = "";
                        } else {
                            allInfo.put(mRadioButtonList.get(r).getFieldId() + "", s);
                            radioNone = mRadioButtonList.get(r).getShowName();
                        }
                    } else {
                        if (!TextUtils.isEmpty(s)) {
                            map.put(mRadioButtonList.get(r).getFieldId() + "", s);
                            add.put(mRadioButtonList.get(r).getFieldName(), s);
                            allInfo.put(mRadioButtonList.get(r).getFieldId() + "", s);
                        } else {
                            allInfo.put(mRadioButtonList.get(r).getFieldId() + "", s);
                        }
                    }
                }

                String s1 = "";
                for (int r = 0; r < mSpinnerList.size(); r++) { //遍历所有spinner
                    if (mSpinnerList.get(r).getRadioButton().isChecked()) {
                        s1 = mSpinnerList.get(r).getRadioButton().getText().toString();

                    }
                    if (mSpinnerList.get(r).getRequired() == 1) {
                        if (!s1.isEmpty()) {
                            map.put(mSpinnerList.get(r).getFieldId() + "", s1);
                            add.put(mSpinnerList.get(r).getFieldName(), s1);
                            allInfo.put(mSpinnerList.get(r).getFieldId() + "", s1);
                            spinnerNone = "";
                        } else {
                            spinnerNone = mSpinnerList.get(r).getShowName();
                            allInfo.put(mSpinnerList.get(r).getFieldId() + "", s1);
                        }
                    } else {
                        if (!TextUtils.isEmpty(s1)) {
                            add.put(mSpinnerList.get(r).getFieldName(), s1);
                            map.put(mSpinnerList.get(r).getFieldId() + "", s1);
                            allInfo.put(mSpinnerList.get(r).getFieldId() + "", s1);
                        } else {
                            allInfo.put(mSpinnerList.get(r).getFieldId() + "", s1);
                        }
                    }

                }

                for (int i = 0; i < mViewList.size(); i++) { //遍历所有checkbox
                    String addCheckboxValue = "";//修改现场添加的参会人员,checkbox需要传递参数的格式
                    if (mViewList.get(i).getCheckBox().isChecked()) {
                        checkboxValue = checkboxValue + mViewList.get(i).getCheckBox().getText().toString() + ",";
                        addCheckboxValue = addCheckboxValue + mViewList.get(i).getCheckBox().getText().toString();
                    }
                    if (mViewList.get(i).getRequired() == 1) {
                        if (!checkboxValue.isEmpty()) {
                            if (i == mViewList.size() - 1) {
                                checkboxValue = checkboxValue.substring(0, checkboxValue.length() - 1);
                                map.put(mViewList.get(i).getFieldId() + "", checkboxValue);
                                checkNone = "";
                            }
                            add.put(mViewList.get(i).getOptionItems(), addCheckboxValue);
                        } else {
                            checkNone = mViewList.get(i).getShowName();
                        }
                    } else {
                        if (!checkboxValue.isEmpty()) {
                            if (i == mViewList.size() - 1) {
                                checkboxValue = checkboxValue.substring(0, checkboxValue.length() - 1);
                                map.put(mViewList.get(i).getFieldId() + "", checkboxValue);
                            }
                            add.put(mViewList.get(i).getOptionItems(), addCheckboxValue);
                        }
                    }
                    allInfo.put(mViewList.get(i).getFieldId() + "", checkboxValue);
                }

                add.put("ref_code", mRef_code);
                Gson gson = new Gson();
                str = gson.toJson(map);
                addMap.put(mTicketId + "_0", add);
                strAdd = gson.toJson(addMap);
                strAllInfo = gson.toJson(allInfo);
                if (none == 1) {
                    Toast toast = Toast.makeText(ModifyUserInfo.this, R.string.info_not_complete, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (!TextUtils.isEmpty(radioNone)) {
                    Toast toast = Toast.makeText(this, radioNone + R.string.is_null, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (!TextUtils.isEmpty(spinnerNone)) {
                    Toast toast = Toast.makeText(this, spinnerNone + R.string.is_null, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (!TextUtils.isEmpty(checkNone)) {
                    Toast toast = Toast.makeText(this, checkNone + R.string.is_null, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (none == 2) {
                    Toast toast = Toast.makeText(ModifyUserInfo.this, R.string.emai_err, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (none == 3) {
                    Toast toast = Toast.makeText(ModifyUserInfo.this, R.string.not_input_num, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    //   AppManager.getAppManager().finishActivity();

                } else if (none == 4) {
                    Toast toast = Toast.makeText(ModifyUserInfo.this, R.string.phone_err, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    //     AppManager.getAppManager().finishActivity();
                } else {
                    //将用户名改为更改后的用户名
                    if (!TextUtils.isEmpty(mRef_code)) {
                        SQLite.update(Attends.class).set(Attends_Table.names.is(userName)).where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.refCodes.is(mRef_code)).execute();
                    } else {
                        SQLite.update(Attends.class).set(Attends_Table.names.is(userName)).where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.attendId.is(mAttendId)).execute();
                    }
                    if (mAttendId != 0) {
                        DBUtil.updateModify(mEventId, mAttendId, strAllInfo, str, isContainPic, imgPath, mRef_code, Constants.MODIFY_NOT_SYNC);
                        if (NetUtil.isConnected(this)) {
                            modifyPresenter.modifyInfo();
                        } else {
                            // EventBus.getDefault().postSticky(new MsgEvent(mAttendId, userName, "name"));
                            Toast.makeText(this, R.string.change_success, Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().postSticky(new MsgEvent(position, mAttendId, Common.MODIFY_ATTNEDEE_SUCCESS));
                            AppManager.getAppManager().finishActivity();
                        }
                    } else {
                        //修改现场添加参会人员信息,判断是否已经同步到服务器中
                        Attends attends = new Select().from(Attends.class).where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.refCodes.is(mRef_code)).and(Attends_Table.attendId.isNot(0)).querySingle();
                        if (attends != null) {//attends不为空,则说明现场添加人员已同步到服务器中,将attendid不为0的那条记录信息同时修改
                            mAttendId = attends.attendId;
                            if (NetUtil.isConnected(this)) {
                                DBUtil.updateModify(mEventId, mAttendId, strAllInfo, str, isContainPic, imgPath, mRef_code, Constants.MODIFY_NOT_SYNC);
                                modifyPresenter.modifyInfo();
                            } else {
                                Toast.makeText(this, R.string.change_success, Toast.LENGTH_SHORT).show();
                                DBUtil.updateModify(mEventId, mAttendId, strAllInfo, str, isContainPic, imgPath, mRef_code, Constants.MODIFY_NOT_SYNC);
                                EventBus.getDefault().postSticky(new MsgEvent(position, mAttendId, Common.MODIFY_ATTNEDEE_SUCCESS));
                                AppManager.getAppManager().finishActivity();
                            }
                        } else {//如果attends为空,则说明未同步到服务器,只需要将现场添加参会人员的map更改为修改过的map即可,同步状态仍然为"添加参会人员未同步"
                            // SQLite.update(Attends.class).set(Attends_Table.names.is(userName)).where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.refCodes.is(mRef_code)).execute();
                            SQLite.update(Attends.class).set(Attends_Table.imgPath.is(imgPath)).where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.attendId.is(0)).and(Attends_Table.refCodes.is(mRef_code)).execute();
                            SQLite.update(Attends.class).set(Attends_Table.gsonUser.is(strAllInfo)).where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.attendId.is(0)).and(Attends_Table.refCodes.is(mRef_code)).execute();
                            SQLite.update(Attends.class).set(Attends_Table.addMap.is(strAdd)).where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.attendId.is(0)).and(Attends_Table.refCodes.is(mRef_code)).execute();
                            //  EventBus.getDefault().postSticky(new MsgEvent(5, userName, mRef_code));
                            EventBus.getDefault().postSticky(new MsgEvent(position, mRef_code, Common.MODIFY_ATTENDEE_REFCODE_SUCCESS));
                        }
                        AppManager.getAppManager().finishActivity();
                    }
                }
                break;
        }
    }
}
