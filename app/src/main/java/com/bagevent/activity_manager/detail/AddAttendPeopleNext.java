package com.bagevent.activity_manager.detail;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.AddDatePicker;
import com.bagevent.activity_manager.manager_fragment.data.AddEditTextList;
import com.bagevent.activity_manager.manager_fragment.data.AddImage;
import com.bagevent.activity_manager.manager_fragment.data.AddPeopleData;
import com.bagevent.activity_manager.manager_fragment.data.AddRadioButton;
import com.bagevent.activity_manager.manager_fragment.data.AddViewList;
import com.bagevent.activity_manager.manager_fragment.data.AttendPeople;
import com.bagevent.activity_manager.manager_fragment.data.CheckIn;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.data.GetAllAddPeople;
import com.bagevent.activity_manager.manager_fragment.data.ModifyData;
import com.bagevent.activity_manager.manager_fragment.data.SerializableMap;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.AddPeopleCheckInPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.SubmitDelegateOrderPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.SubmitOrderPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.UpLoadImagePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AddPeopleCheckInView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SubmitDelegateOrderView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.SubmitOrderView;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpLoadImageView;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.AppDatabase;
import com.bagevent.db.Attends;
//import com.bagevent.db.Attends_Table;
import com.bagevent.db.Attends_Table;
import com.bagevent.db.BackupCheckin;
//import com.bagevent.db.BackupCheckin_Table;
import com.bagevent.db.BackupCheckin_Table;
import com.bagevent.db.EventTicket;
//import com.bagevent.db.EventTicket_Table;
import com.bagevent.db.EventTicket_Table;
import com.bagevent.new_home.data.ProAndCity;
import com.bagevent.new_home.data.ProvinceBean;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.CompareRex;
import com.bagevent.util.IsPhoneNum;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.util.dbutil.DBUtil;
import com.bagevent.util.image_download.CityUtils;
import com.bagevent.view.wheelview.OnWheelScrollListener;
import com.bagevent.view.wheelview.WheelView;
import com.bagevent.view.wheelview.adapter.NumericWheelAdapter;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.pickerview.OptionsPickerView;
import com.pickerview.TimePickerView;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;

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
 * Created by zwj on 2016/7/4.
 */
public class AddAttendPeopleNext extends BaseActivity implements SubmitOrderView, SubmitDelegateOrderView, AddPeopleCheckInView, UpLoadImageView {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_add_parent)
    LinearLayout mGroupLayout;
    @BindView(R.id.tv_confirm_attendee)
    TextView tvConfirmAttendee;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.scroll_ui)
    ScrollView scrollUi;
    private TimePickerView pvTime;

    private OptionsPickerView pvOptions;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ProAndCity cityData;
    private SerializableMap serMap = new SerializableMap();
    private List<AddPeopleData> mList = new ArrayList<AddPeopleData>();
    private int mEventId = -1;
    private int stType = -1;

    private WheelView year;
    private WheelView month;
    private WheelView day;
    private View view = null;

    private String mFormType = "";//获取表单
    private String mBadgeFormType = "";//胸卡表单
    private FormType mType = null;
    private FormType mBadgeType = null;
    private static final int FORM_TEXT_SIZE = 12;//表单字体大小
    private static final int TEXT_SIZE = 16;

    private int buyWay = -1;
    private int tempBuyWay = -1;

    private int checkStatus = 1;//将签到状态默认设为要请求的状态
    private AddPeopleCheckInPresenter checkInPresenter = null;
    private String payType = "1";
    private String attendeeMap = "";
    private String badgeMap = "";
    private String ticketId = "";
    private int index = 0;


    /**
     * 获取数据
     *
     * @param savedInstanceState
     */
    private List<AttendPeople.RespObjectBean.ObjectsBean> mAddList = new ArrayList<AttendPeople.RespObjectBean.ObjectsBean>();//保存添加的用户信息,方便存到数据库中
    //   private Map<String, Map<String, String>> objMap = new HashMap<String, Map<String, String>>();//用于向服务器提交数据
    private String strMap = "";//将向服务器提交的map转为json
    private List<GetAllAddPeople> mAllPeople = new ArrayList<GetAllAddPeople>();//存储添加的所有用户
    //   private List<AddImage> mAddImg = new ArrayList<AddImage>();//存储每一页的图片路径
    String date = "";

    /**
     * 上传图片
     */
    private File uploadFile;
    private String userId = "";
    private String imgPath = "";
    private UpLoadImagePresenter upLoadImagePresenter;
    private static final int REQUECT_CODE_PHONE_STATE = 1;
    private boolean isGrantPhoneState = false;//是否允许访问手机状态


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_people_next);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white), Constants.STATUS_ALPHA);
        ButterKnife.bind(this);
        initView();
        getProCity();
        MPermissions.requestPermissions(this, REQUECT_CODE_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);
        getKeyAndValue();
        userId = SharedPreferencesUtil.get(this, "userId", "");
        // if (NetUtil.isConnected(this)) {
        mFormType = (String) NetUtil.readObject(this, Constants.FORM_FILE_NAME + mEventId + "");//从文件中获得表单类型
        mBadgeFormType = (String) NetUtil.readObject(this, Constants.BADGE_FORM_FILE_NAME + mEventId + "");
        if (stType != 1) {
            if (mFormType != null) {
                mType = new Gson().fromJson(mFormType, FormType.class);//解析表单数据
                initContentView();
            } else {
                Toast toast = Toast.makeText(this, getString(R.string.no_form), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } else {
            if (mFormType != null && mBadgeFormType != null) {
                mType = new Gson().fromJson(mFormType, FormType.class);//解析表单数据
                mBadgeType = new Gson().fromJson(mBadgeFormType, FormType.class);
                initContentView();
            } else {
                Toast toast = Toast.makeText(this, getString(R.string.no_form), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    @OnClick({R.id.ll_title_back, R.id.tv_confirm_attendee})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_confirm_attendee:
                getData();
                break;
        }
    }

    private void initView() {
        tvTitle.setText(R.string.edit_attendee1);
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
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

    private void initContentView() {
        int count = mType.getRespObject().size();//获得表单的数据长度，然后从中逐一获取数据
        LinearLayout.LayoutParams tvlp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 66);
        LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RadioGroup.LayoutParams rbParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);//单选框
        LinearLayout.LayoutParams cbParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);//多选框
        LinearLayout.LayoutParams horLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//水平
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(134, 134);//图片
        mAllPeople.clear();
        for (int n = 0; n < mList.size(); n++) {
            int ticketId = mList.get(n).getTicketId();
            EventTicket ticket = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(mEventId)).and(EventTicket_Table.ticketIds.is(ticketId)).querySingle();
            int addNum = mList.get(n).getTicketNum();
            for (int num = 0; num < addNum; num++) {
                List<AddViewList> mViewList = new ArrayList<AddViewList>();//多选框
                List<AddEditTextList> mEditTextList = new ArrayList<AddEditTextList>();//editTExt
                List<AddRadioButton> mRadioButtonList = new ArrayList<AddRadioButton>();//单选按钮
                List<AddRadioButton> mSpinnerList = new ArrayList<AddRadioButton>();//单选按钮
                List<AddDatePicker> mDatePicker = new ArrayList<AddDatePicker>();//日期
                List<AddImage> mAddImage = new ArrayList<AddImage>();//图像采集
                GetAllAddPeople mGetAllPeople = new GetAllAddPeople();

                TextView tv_title = new TextView(this);
                if (ticket != null) {
                    tv_title.setText(ticket.ticketNames);
                }
                tv_title.setPadding(25, 23, 0, 23);
                tv_title.setBackgroundResource(R.color.e6edf2);
                tv_title.setTextSize(14);
                tv_title.setTextColor(ContextCompat.getColor(this, R.color.black));
                tv_title.setLayoutParams(tvLp);
                mGroupLayout.addView(tv_title);
                for (int i = 0; i < count; i++) {
                    FormType.RespObjectBean form = mType.getRespObject().get(i);
                    if (form.getFieldTypeName().contains("radio")) {
                        initTextView(form, horLp, form.getRequired(), 25, 15);
                        for (int j = 0; j < form.getOptionItems().size(); j++) {
                            initRadioButton(rbParams, form, mRadioButtonList, j, 25, 20);
                        }
                        initViewLine(lps, 10);
                    } else if (form.getFieldTypeName().contains("checkbox")) {
                        initTextView(form, horLp, form.getRequired(), 25, 15);
                        for (int a = 0; a < form.getOptionItems().size(); a++) {
                            initCheckBox(cbParams, form, mViewList, a, 25, 20);
                        }
                        initViewLine(lps, 10);
                    } else if (form.getFieldTypeName().contains("select")) {//下拉
                        initTextView(form, horLp, form.getRequired(), 25, 15);
                        for (int m = 0; m < form.getOptionItems().size(); m++) {
                            initRadioButton(rbParams, form, mSpinnerList, m, 25, 20);
                        }
                        initViewLine(lps, 10);

                    } else if (form.getFieldTypeName().contains("textarea")) {
                        initTextView(form, horLp, form.getRequired(), 25, 15);
                        initEditText(form, tvLp, mEditTextList, 25, 10);
                        initViewLine(lps, 10);
                    } else {
                        initTextView(form, horLp, form.getRequired(), 25, 15);
                        initEditText(form, tvLp, mEditTextList, 25, 10);
                        initViewLine(lps, 10);
                    }
                }
                mGetAllPeople.setTicketId(ticketId);
                mGetAllPeople.setEditTextList(mEditTextList);
                mGetAllPeople.setAddViewList(mViewList);
                mGetAllPeople.setAddRadioButton(mRadioButtonList);
                mGetAllPeople.setSpinnerList(mSpinnerList);
                mGetAllPeople.setAddDatePickers(mDatePicker);
                mGetAllPeople.setAddImageList(mAddImage);
                // mAllPeople.add(mGetAllPeople);

                if (stType == 1) {
                    List<AddViewList> mBadgeViewList = new ArrayList<AddViewList>();//多选框
                    List<AddEditTextList> mBadgeEditTextList = new ArrayList<AddEditTextList>();//editTExt
                    List<AddRadioButton> mBadgeRadioButtonList = new ArrayList<AddRadioButton>();//单选按钮
                    List<AddRadioButton> mBadgeSpinnerList = new ArrayList<AddRadioButton>();//单选按钮
                    List<AddDatePicker> mBadgeDatePicker = new ArrayList<AddDatePicker>();//日期
                    List<AddImage> mBadgeAddImage = new ArrayList<AddImage>();//图像采集
                    int badgeCount = mBadgeType.getRespObject().size();
                    TextView tv_badeg_title = new TextView(this);
                    tv_badeg_title.setText(getString(R.string.card_info));
                    tv_badeg_title.setPadding(25, 0, 0, 0);
                    tv_badeg_title.setBackgroundResource(R.color.e6edf2);
                    tv_badeg_title.setTextSize(16);
                    tv_badeg_title.setLayoutParams(tvLp);
                    mGroupLayout.addView(tv_badeg_title);
                    for (int i = 0; i < badgeCount; i++) {
                        FormType.RespObjectBean form = mBadgeType.getRespObject().get(i);
                        if (form.getFieldTypeName().contains("radio")) {
                            initTextView(form, horLp, form.getRequired(), 25, 15);
                            for (int j = 0; j < form.getOptionItems().size(); j++) {
                                initRadioButton(rbParams, form, mBadgeRadioButtonList, j, 25, 20);
                            }
                            initViewLine(lps, 10);

                        } else if (form.getFieldTypeName().contains("checkbox")) {
                            initTextView(form, horLp, form.getRequired(), 25, 15);
                            for (int a = 0; a < form.getOptionItems().size(); a++) {
                                initCheckBox(cbParams, form, mBadgeViewList, a, 25, 20);
                            }
                            initViewLine(lps, 10);
                        } else if (form.getFieldTypeName().contains("select")) {//下拉
                            initTextView(form, horLp, form.getRequired(), 25, 15);
                            for (int m = 0; m < form.getOptionItems().size(); m++) {
                                initRadioButton(rbParams, form, mBadgeSpinnerList, m, 25, 20);
                            }
                            initViewLine(lps, 10);

                        } else if (form.getFieldTypeName().contains("textarea")) {
                            initTextView(form, horLp, form.getRequired(), 25, 15);
                            initEditText(form, tvLp, mBadgeEditTextList, 25, 10);
                            initViewLine(lps, 10);
                        } else {
                            initTextView(form, horLp, form.getRequired(), 25, 15);
                            initEditText(form, tvLp, mBadgeEditTextList, 25, 10);
                            initViewLine(lps, 10);
                        }
                    }
                    mGetAllPeople.setBadgeEditTextList(mBadgeEditTextList);
                    mGetAllPeople.setBadgeAddViewList(mBadgeViewList);
                    mGetAllPeople.setBadgeAddRadioButton(mBadgeRadioButtonList);
                    mGetAllPeople.setBadgeSpinnerList(mBadgeSpinnerList);
                    mGetAllPeople.setBadgeAddDatePickers(mBadgeDatePicker);
                    mGetAllPeople.setBadgeAddImageList(mBadgeAddImage);
                }
                mAllPeople.add(mGetAllPeople);
            }


        }

        loadingFinished();
    }


    private void isLoading() {
        llLoading.setVisibility(View.GONE);
        scrollUi.setVisibility(View.GONE);
    }

    private void loadingFinished() {
        llLoading.setVisibility(View.GONE);
        scrollUi.setVisibility(View.VISIBLE);
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
        par.setMargins(0, top, 0, 0);
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


    private void initImageView(LinearLayout.LayoutParams params, FormType.RespObjectBean form, final List<AddImage> mAddImage) {
        RelativeLayout relative = new RelativeLayout(this);
        relative.setGravity(Gravity.RIGHT);
        TextView tv_pic = new TextView(this);
        tv_pic.setText(getString(R.string.no_support));
        tv_pic.setTextSize(14);
        tv_pic.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams rp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 134);
        rp1.setMarginEnd(55);
        relative.addView(tv_pic, rp1);
        mGroupLayout.addView(relative);
    }

    private void initEditText(FormType.RespObjectBean form, LinearLayout.LayoutParams lp, final List<AddEditTextList> mEditTextList, int left, int top) {
        EditText et_userName = new EditText(this);
        et_userName.setId(View.generateViewId());
        lp.setMargins(0, 30, 0, 10);
        et_userName.setLayoutParams(lp);
        et_userName.setPadding(left, 0, 0, 0);
        et_userName.setBackground(null);
        et_userName.setTextSize(TEXT_SIZE);
        et_userName.setSingleLine(true);
        if (form.getFieldTypeName().contains("mobile_phone")) {
            et_userName.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (form.getFieldTypeName().contains("attendee_avatar")) {
            et_userName.setFocusable(false);
            et_userName.setHint(getString(R.string.no_support));
        } else if (form.getFieldTypeName().contains("date") || form.getFieldTypeName().contains("country")) {
            //et_userName.setInputType(InputType.TYPE_NULL);
            et_userName.setFocusableInTouchMode(false);
        }
        et_userName.setTextColor(ContextCompat.getColor(this, R.color.black));
        mGroupLayout.addView(et_userName);
        //将editText和相关数据保存起来保存起来
        AddEditTextList addEdit = new AddEditTextList();
        addEdit.setEditText(et_userName);
        addEdit.setFiledName(form.getFieldName());
        addEdit.setFieldId(form.getFormFieldId());
        addEdit.setRequired(form.getRequired());
        addEdit.setShowName(form.getShowName());
        addEdit.setFiledTypeName(form.getFieldTypeName());
        mEditTextList.add(addEdit);
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

    private void initViewLine(LinearLayout.LayoutParams lps, int top) {
        View view_line = new View(this);
        lps.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lps.height = 1;
        lps.setMargins(0, top, 0, 0);
        view_line.setBackgroundColor(ContextCompat.getColor(this, R.color.e6edf2));
        view_line.setLayoutParams(lps);
        mGroupLayout.addView(view_line);
    }

    private void initRadioButton(RadioGroup.LayoutParams rbParams, FormType.RespObjectBean form, final List<AddRadioButton> mRadioButtonList, int pos, int left, int top) {
        final CheckBox rb = new CheckBox(this);
        rb.setId(View.generateViewId());
        rbParams.setMargins(left, top, 0, 0);
        rb.setLayoutParams(rbParams);
        rb.setText(form.getOptionItems().get(pos).getValue());
        rb.setButtonDrawable(R.drawable.radiobtn_select);
        rb.setPaddingRelative(15, 0, 0, 0);
        mGroupLayout.addView(rb);

        AddRadioButton addRadio = new AddRadioButton();
        addRadio.setCheckBoxId(rb.getId());
        addRadio.setRequired(form.getRequired());
        addRadio.setFieldId(form.getFormFieldId());
        addRadio.setRadioButton(rb);
        addRadio.setShowName(form.getShowName());
        addRadio.setFieldName(form.getFieldName());
        mRadioButtonList.add(addRadio);
        rb.setTag(form.getFormFieldId());
        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int formFieldId = (int) buttonView.getTag();
                if (isChecked) {
                    for (int i = 0; i < mRadioButtonList.size(); i++) {
                        if (buttonView.getId() == mRadioButtonList.get(i).getCheckBoxId() && mRadioButtonList.get(i).getFieldId() == formFieldId) {
                            mRadioButtonList.get(i).getRadioButton().setChecked(true);
                        } else if (mRadioButtonList.get(i).getFieldId() == formFieldId) {
                            mRadioButtonList.get(i).getRadioButton().setChecked(false);
                        }
                    }
                }
            }
        });
    }

    private void initCheckBox(LinearLayout.LayoutParams cbParams, FormType.RespObjectBean form, final List<AddViewList> mViewList, int pos, int left, int top) {
        CheckBox cb = new CheckBox(this);
        cb.setId(View.generateViewId());
        cb.setText(form.getOptionItems().get(pos).getValue());
        cbParams.setMargins(left, top, 0, 0);
        cb.setPaddingRelative(15, 0, 0, 0);
        cb.setButtonDrawable(R.drawable.checkbox_select);
        mGroupLayout.addView(cb, cbParams);

        AddViewList addCheck = new AddViewList();
        addCheck.setId(cb.getId());
        addCheck.setFieldName(form.getFieldName());
        addCheck.setRequired(form.getRequired());
        addCheck.setFieldId(form.getFormFieldId());
        addCheck.setShowName(form.getShowName());
        addCheck.setOptionItems(form.getOptionItems().get(pos).getKey());//获得每个checkBox
        addCheck.setCheckBox(cb);
        mViewList.add(addCheck);
    }

    //获得Map中的key和value
    private void getKeyAndValue() {
        Map<String, String> map = new HashMap<String, String>();
        serMap = new SerializableMap();
        Bundle bundle = getIntent().getExtras();
        mEventId = bundle.getInt("eventId", 0);
        stType = bundle.getInt("stType", 0);
        tempBuyWay = bundle.getInt("buyWay", 0);
        serMap = (SerializableMap) bundle.get("map");
        if (serMap != null) {
            map = serMap.getMap();
            for (Map.Entry entry : map.entrySet()) {
                AddPeopleData data = new AddPeopleData();
                data.setTicketId(Integer.parseInt(entry.getKey().toString()));
                data.setTicketNum(Integer.parseInt(entry.getValue().toString()));
                mList.add(data);
            }
        }
    }


    private void getData() {
        mAddList.clear();
        int none = -1;
        String radioNone = "";
        String spinnerNone = "";
        String checkboxNone = "";
        // if (isGrantPhoneState) {//如果允许访问则进行添加参会人员
        for (int i = 0; i < mAllPeople.size(); i++) {
            Map<String, Map<String, String>> addMap = new HashMap<String, Map<String, String>>();//添加参会人员的信息，用于保存到数据库中方便同步数据(每个人一个map)
            Map<String, String> map = new HashMap<String, String>();//此map用于向服务器请求添加用户,key为fieldName
            Map<String, String> mapUser = new HashMap<String, String>();//此map用于将人员信息存入数据库中，key为fieldId
            Map<String, String> badgeMap = new HashMap<String, String>();//存放胸卡信息
            String userName = "";
            for (int e = 0; e < mAllPeople.get(i).getEditTextList().size(); e++) {//遍历所有的editText
                AddEditTextList edit = mAllPeople.get(i).getEditTextList().get(e);
                if (edit.getRequired() == 1) {
                    if (!edit.getEditText().getText().toString().isEmpty()) {
                        if (edit.getFiledName().contains("username")) {
                            userName = edit.getEditText().getText().toString();
                            if (!CompareRex.isContainsNum(userName)) {
                                map.put(edit.getFiledName(), edit.getEditText().getText().toString());
                            } else {
                                none = 3;
                            }
                        } else if (edit.getFiledName().contains("email_address")) {
                            String s = edit.getEditText().getText().toString();
                            if (CompareRex.checkEmail(s)) {
                                map.put(edit.getFiledName(), edit.getEditText().getText().toString());
                            } else {
                                none = 2;
                            }
                        } else if (edit.getFiledName().contains("mobile_phone")) {
                            String s = edit.getEditText().getText().toString();
                            if (IsPhoneNum.isPhone(s)) {
                                map.put(edit.getFiledName(), edit.getEditText().getText().toString());
                            } else {
                                none = 4;
                            }
                        } else {
                            map.put(edit.getFiledName(), edit.getEditText().getText().toString());
                        }
                    } else {
                        none = 1;//若none为1,则表示有有必填项未填写
                    }
                } else {
                    if (!edit.getEditText().getText().toString().isEmpty()) {
                        if (edit.getFiledName().contains("username")) {
                            userName = edit.getEditText().getText().toString();
                            map.put(edit.getFiledName(), edit.getEditText().getText().toString());
                        } else if (edit.getFiledName().contains("email_address")) {
                            if (CompareRex.checkEmail(edit.getEditText().getText().toString())) {
                                map.put(edit.getFiledName(), edit.getEditText().getText().toString());
                            } else {
                                none = 2;
                            }
                        } else if (edit.getFiledName().contains("mobile_phone")) {
                            String s = edit.getEditText().getText().toString();
                            if (IsPhoneNum.isPhone(s)) {
                                map.put(edit.getFiledName(), edit.getEditText().getText().toString());
                            } else {
                                none = 4;
                            }
                        } else {
                            map.put(edit.getFiledName(), edit.getEditText().getText().toString());
                        }
                    }
                }
                mapUser.put(edit.getFieldId() + "", edit.getEditText().getText().toString());
            }

            String s = "";
            for (int r = 0; r < mAllPeople.get(i).getAddRadioButton().size(); r++) { //遍历所有radiobutton
                AddRadioButton radio = mAllPeople.get(i).getAddRadioButton().get(r);

                if (radio.getRadioButton().isChecked()) {
                    s = radio.getRadioButton().getText().toString();
                }
                if (radio.getRequired() == 1) {
                    if (!s.isEmpty()) {
                        map.put(radio.getFieldName(), s);
                        radioNone = "";
                    } else {
                        radioNone = radio.getShowName();
                    }
                } else {
                    if (!s.isEmpty()) {
                        map.put(radio.getFieldName(), s);
                    }
                }
                mapUser.put(radio.getFieldId() + "", s);
            }

            String s1 = "";
            for (int r = 0; r < mAllPeople.get(i).getSpinnerList().size(); r++) { //遍历所有spinner
                AddRadioButton radio = mAllPeople.get(i).getSpinnerList().get(r);

                if (radio.getRadioButton().isChecked()) {
                    s1 = radio.getRadioButton().getText().toString();
                }
                if (radio.getRequired() == 1) {
                    if (!s1.isEmpty()) {
                        map.put(radio.getFieldName(), s1);
                        spinnerNone = "";
                    } else {
                        spinnerNone = radio.getShowName();
                    }
                } else {
                    if (!s1.isEmpty()) {
                        map.put(radio.getFieldName(), s1);
                    }
                }
                mapUser.put(radio.getFieldId() + "", s1);
            }

            for (int c = 0; c < mAllPeople.get(i).getAddViewList().size(); c++) { //遍历所有checkbox
                String checkboxValue = "";
                AddViewList checkbox = mAllPeople.get(i).getAddViewList().get(c);
                if (checkbox.getCheckBox().isChecked()) {
                    checkboxValue = checkboxValue + checkbox.getCheckBox().getText().toString();
                }
                if (checkbox.getRequired() == 1) {
                    if (!checkboxValue.isEmpty()) {
                        map.put(checkbox.getOptionItems(), checkboxValue);
                        checkboxNone = "";
                    } else {
                        checkboxNone = checkbox.getShowName();
                    }
                } else {
                    if (!checkboxValue.isEmpty()) {
                        map.put(checkbox.getOptionItems(), checkboxValue);
                    }
                }
                mapUser.put(checkbox.getFieldId() + "", checkboxValue);
            }

            for (int d = 0; d < mAllPeople.get(i).getAddDatePickers().size(); d++) {//遍历所有日期控件
                AddDatePicker datePicker = mAllPeople.get(i).getAddDatePickers().get(d);
                date = new StringBuilder().append((datePicker.getYear().getCurrentItem() + 1950)).append("-").append((datePicker.getMonth().getCurrentItem() + 1) < 10 ? "0" + (datePicker.getMonth().getCurrentItem() + 1) : (datePicker.getMonth().getCurrentItem() + 1)).append("-").append(((datePicker.getDay().getCurrentItem() + 1) < 10) ? "0" + (datePicker.getDay().getCurrentItem() + 1) : (datePicker.getDay().getCurrentItem() + 1)).toString();
                map.put(datePicker.getFiledName(), date);
                mapUser.put(datePicker.getFiled() + "", date);
            }
            String lastName = "";
            String firstName = "";
            if (stType == 1) {//如果为学术会议，遍历胸卡信息
                for (int e = 0; e < mAllPeople.get(i).getBadgeEditTextList().size(); e++) {//遍历所有的editText
                    AddEditTextList edit = mAllPeople.get(i).getBadgeEditTextList().get(e);
                    if (edit.getRequired() == 1) {
                        if (!edit.getEditText().getText().toString().isEmpty()) {
                            if (edit.getFiledName().contains("last_name")) {
                                lastName = edit.getEditText().getText().toString();
                                if (!CompareRex.isContainsNum(lastName)) {
                                    badgeMap.put(edit.getFiledName(), edit.getEditText().getText().toString());
                                } else {
                                    none = 3;
                                }
                            } else if (edit.getFiledName().contains("first_name")) {
                                firstName = edit.getEditText().getText().toString();
                                if (!CompareRex.isContainsNum(firstName)) {
                                    badgeMap.put(edit.getFiledName(), edit.getEditText().getText().toString());
                                } else {
                                    none = 3;
                                }
                            } else if (edit.getFiledName().contains("email_address")) {
                                String email = edit.getEditText().getText().toString();
                                if (CompareRex.checkEmail(email)) {
                                    badgeMap.put(edit.getFiledName(), edit.getEditText().getText().toString());
                                } else {
                                    none = 2;
                                }
                            } else if (edit.getFiledName().contains("mobile_phone")) {
                                String mobile_phone = edit.getEditText().getText().toString();
                                if (IsPhoneNum.isPhone(mobile_phone)) {
                                    badgeMap.put(edit.getFiledName(), edit.getEditText().getText().toString());
                                } else {
                                    none = 4;
                                }
                            } else {
                                badgeMap.put(edit.getFiledName(), edit.getEditText().getText().toString());
                            }
                        } else {
                            none = 1;//若none为1,则表示有有必填项未填写
                        }
                    } else {
                        if (!edit.getEditText().getText().toString().isEmpty()) {
                            if (edit.getFiledName().contains("last_name")) {
                                if (!CompareRex.isContainsNum(lastName)) {
                                    badgeMap.put(edit.getFiledName(), edit.getEditText().getText().toString());
                                } else {
                                    none = 3;
                                }
                            } else if (edit.getFiledName().contains("first_name")) {
                                if (!CompareRex.isContainsNum(lastName)) {
                                    badgeMap.put(edit.getFiledName(), edit.getEditText().getText().toString());
                                } else {
                                    none = 3;
                                }
                            } else if (edit.getFiledName().contains("email_address")) {
                                if (CompareRex.checkEmail(edit.getEditText().getText().toString())) {
                                    badgeMap.put(edit.getFiledName(), edit.getEditText().getText().toString());
                                } else {
                                    none = 2;
                                }
                            } else if (edit.getFiledName().contains("mobile_phone")) {
                                String mobile_phone = edit.getEditText().getText().toString();
                                if (IsPhoneNum.isPhone(mobile_phone)) {
                                    badgeMap.put(edit.getFiledName(), edit.getEditText().getText().toString());
                                } else {
                                    none = 4;
                                }
                            } else {
                                badgeMap.put(edit.getFiledName(), edit.getEditText().getText().toString());
                            }
                        }
                    }
                }

                String strRadioButton = "";
                for (int r = 0; r < mAllPeople.get(i).getBadgeAddRadioButton().size(); r++) { //遍历所有radiobutton
                    AddRadioButton radio = mAllPeople.get(i).getBadgeAddRadioButton().get(r);
                    if (radio.getRadioButton().isChecked()) {
                        strRadioButton = radio.getRadioButton().getText().toString();
                    }
                    if (radio.getRequired() == 1) {
                        if (!strRadioButton.isEmpty()) {
                            badgeMap.put(radio.getFieldName(), strRadioButton);
                            radioNone = "";
                        } else {
                            radioNone = radio.getShowName();
                        }
                    } else {
                        if (!s.isEmpty()) {
                            badgeMap.put(radio.getFieldName(), strRadioButton);
                        }
                    }
                }

                String strSpinner = "";
                for (int r = 0; r < mAllPeople.get(i).getBadgeSpinnerList().size(); r++) { //遍历所有spinner
                    AddRadioButton radio = mAllPeople.get(i).getBadgeSpinnerList().get(r);

                    if (radio.getRadioButton().isChecked()) {
                        strSpinner = radio.getRadioButton().getText().toString();
                    }
                    if (radio.getRequired() == 1) {
                        if (!strSpinner.isEmpty()) {
                            badgeMap.put(radio.getFieldName(), strSpinner);
                            spinnerNone = "";
                        } else {
                            spinnerNone = radio.getShowName();
                        }
                    } else {
                        if (!s1.isEmpty()) {
                            badgeMap.put(radio.getFieldName(), strSpinner);
                        }
                    }
                }

                for (int c = 0; c < mAllPeople.get(i).getBadgeAddViewList().size(); c++) { //遍历所有checkbox
                    String checkboxValue = "";
                    AddViewList checkbox = mAllPeople.get(i).getBadgeAddViewList().get(c);
                    if (checkbox.getCheckBox().isChecked()) {
                        checkboxValue = checkboxValue + checkbox.getCheckBox().getText().toString();
                    }
                    if (checkbox.getRequired() == 1) {
                        if (!checkboxValue.isEmpty()) {
                            badgeMap.put(checkbox.getOptionItems(), checkboxValue);
                            checkboxNone = "";
                        } else {
                            checkboxNone = checkbox.getShowName();
                        }
                    } else {
                        if (!checkboxValue.isEmpty()) {
                            badgeMap.put(checkbox.getOptionItems(), checkboxValue);
                        }
                    }
                }

//                for (int d = 0; d < mAllPeople.get(i).getBadgeAddDatePickers().size(); d++) {//遍历所有日期控件
//                    AddDatePicker datePicker = mAllPeople.get(i).getBadgeAddDatePickers().get(d);
//                    date = new StringBuilder().append((datePicker.getYear().getCurrentItem() + 1950)).append("-").append((datePicker.getMonth().getCurrentItem() + 1) < 10 ? "0" + (datePicker.getMonth().getCurrentItem() + 1) : (datePicker.getMonth().getCurrentItem() + 1)).append("-").append(((datePicker.getDay().getCurrentItem() + 1) < 10) ? "0" + (datePicker.getDay().getCurrentItem() + 1) : (datePicker.getDay().getCurrentItem() + 1)).toString();
//                    badgeMap.put(datePicker.getFiledName(), date);
//                }
            }

            String ref = refCode();
            map.put("ref_code", ref);
            Gson gson = new Gson();
            String sMap = gson.toJson(mapUser);
            addMap.put(mAllPeople.get(i).getTicketId() + "_0", map);
            String add = gson.toJson(addMap);
            String strAttendeeMap = gson.toJson(map);
            String strBadge = gson.toJson(badgeMap);
            if (stType != 1) {
                setPeopleDataToList(userName, mAllPeople.get(i).getTicketId(), sMap, add, "", ref, imgPath);
            } else {
                //  Log.e("AddAttendPeopleNext",mAllPeople.size()+"");
                setPeopleDataToList(lastName + firstName, mAllPeople.get(i).getTicketId(), sMap, strAttendeeMap, strBadge, ref, imgPath);
            }
        }
        if (none == 1) {
            Toast toast = Toast.makeText(this, R.string.drop_message, Toast.LENGTH_SHORT);
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
        } else if (!TextUtils.isEmpty(checkboxNone)) {
            Toast toast = Toast.makeText(this, checkboxNone + R.string.is_null, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (none == 2) {
            Toast toast = Toast.makeText(this, R.string.emai_err, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (none == 3) {
            Toast toast = Toast.makeText(this, R.string.not_input_num, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if (none == 4) {
            Toast toast = Toast.makeText(this, R.string.phone_err, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            setPeopleDataToDB(Constants.ADD_NOT_SYNC);
            if (NetUtil.isConnected(this)) {
                tvConfirmAttendee.setText(R.string.adding);
                if (stType != 1) {
                    summitAttendee(index);
//                    for (int i = 0; i < mAddList.size(); i++) {
//                    }
                } else {
                    //  Log.e("AddAttendPeopleNext",mAddList.size() +"");
                    for (int i = 0; i < mAddList.size(); i++) {
                        String refCode = mAddList.get(i).getRefCode();
                        ticketId = mAddList.get(i).getTicketId() + "";
                        attendeeMap = mAddList.get(i).getAdd();
                        buyWay = mAddList.get(i).getBuyWay();
                        badgeMap = mAddList.get(i).getBadgeMaps();
                        SubmitDelegateOrderPresenter submitDelegateOrderPresenter = new SubmitDelegateOrderPresenter(this);
                        submitDelegateOrderPresenter.submitDelegate(refCode);
                    }
                    //    showDialog();
                }
            } else {
                Toast.makeText(this, R.string.success_add, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(new MsgEvent(Common.ADD_ATTENDEE_SUCCESS));
                AppManager.getAppManager().finishActivity();
            }

        }
//        } else {
//            Toast toast = Toast.makeText(this, "请授权允许访问手机信息", Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//        }

    }

    private void summitAttendee(int indexs) {
        String refCode = mAddList.get(indexs).getRefCode();
        strMap = mAddList.get(indexs).getAdd();
        buyWay = mAddList.get(indexs).getBuyWay();
        SubmitOrderPresenter presenter = new SubmitOrderPresenter(this);
        presenter.submitOrder(refCode);
    }

    /**
     * 将每页的参会人员保存下来，以便添加到数据库中
     *
     * @param userName
     * @param ticketId
     * @param map
     * @param ref_code
     */
    private void setPeopleDataToList(String userName, int ticketId, String map, String add, String badge, String ref_code, String imagePath) {
        EventTicket id = new Select().from(EventTicket.class).where(EventTicket_Table.eventIds.is(mEventId)).and(EventTicket_Table.ticketIds.is(ticketId)).querySingle();
        if (id != null) {
            if (id.ticketPrices > 0) {
                buyWay = tempBuyWay;
            } else {
                buyWay = 2;
            }
        }
        AttendPeople.RespObjectBean.ObjectsBean people = new AttendPeople.RespObjectBean.ObjectsBean();
        people.setEventId(mEventId);
        people.setAttendeeId(0);
        people.setName(userName);
        people.setTicketId(ticketId);
        people.setGsonUser(map);
        people.setCheckin(0);
        people.setPayStatus(1);
        people.setAudit(0);
        people.setSort("#");
        people.setRefCode(ref_code);
        people.setBuyWay(buyWay);
        people.setUpdateTime(updateTime());
        people.setAdd(add);
        people.setBadgeMap(badge);
        people.setImagePath(imagePath);
        mAddList.add(people);
        //  Log.e("mAddList  size -->",mAddList.size()+"");
    }

    /**
     * 添加参会人员成功，则将参会人员保存到数据库中，并标记是否同步到服务器
     */
    public void setPeopleDataToDB(final int ISSYNC) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Attends> list = new ArrayList<Attends>();
                for (int i = 0; i < mAddList.size(); i++) {
                    Attends attends = new Attends();
                    // Delete.table(Attends.class, ConditionGroup.clause().and(Attends_Table.eventId.is(mAddList.get(i).getEventId())).and(Attends_Table.refCodes.is(mAddList.get(i).getRefCode())));
                    attends.eventId = mAddList.get(i).getEventId();
                    attends.gsonUser = mAddList.get(i).getGsonUser();
                    attends.checkins = mAddList.get(i).getCheckin();
                    attends.attendId = mAddList.get(i).getAttendeeId();
                    attends.ticketIds = mAddList.get(i).getTicketId();
                    attends.names = mAddList.get(i).getName();
                    attends.audits = mAddList.get(i).getAudit();
                    attends.payStatuss = mAddList.get(i).getPayStatus();
                    attends.strSort = mAddList.get(i).getSort();
                    attends.refCodes = mAddList.get(i).getRefCode();
                    attends.updateTimes = mAddList.get(i).getUpdateTime();
                    attends.buyWays = mAddList.get(i).getBuyWay();
                    attends.addMap = mAddList.get(i).getAdd();
                    attends.badgeMap = mAddList.get(i).getBadgeMaps();
                    attends.imgPath = mAddList.get(i).getImagePath();
                    attends.userId = Integer.parseInt(userId);
                    attends.addSync = ISSYNC;
                    list.add(attends);
                }
                FlowManager.getDatabase(AppDatabase.class)
                        .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                new ProcessModelTransaction.ProcessModel<Attends>() {
                                    @Override
                                    public void processModel(Attends model, DatabaseWrapper wrapper) {
                                        model.save();
                                    }
                                }).addAll(list).build())
                        .error(new Transaction.Error() {
                            @Override
                            public void onError(Transaction transaction, Throwable error) {
                                //   Log.e("list", "Database transaction failed.", error);
                            }
                        })
                        .success(new Transaction.Success() {
                            @Override
                            public void onSuccess(Transaction transaction) {
                                // Log.e("list", "Database transaction success.");
                            }
                        }).build().execute();
            }
        };
        runnable.run();

    }

    private String updateTime() {
        Long time = System.currentTimeMillis();
        return time.toString();
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
    public String payType() {
        return payType;
    }

    @Override
    public String attendeeMap() {
        return attendeeMap;
    }

    @Override
    public String badgeMap() {
        return badgeMap;
    }

    @Override
    public String ticketId() {
        return ticketId;
    }

    @Override
    public void submitDelegateOrderSuccess() {
        Toast.makeText(this, R.string.success_add, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().postSticky(new MsgEvent(Common.ADD_ATTENDEE_SUCCESS));
        AppManager.getAppManager().finishActivity();
        //loadingFinished();
    }

    @Override
    public void submitDelegateOrderFailed() {
        tvConfirmAttendee.setText(R.string.sure_add);
        // loadingFinished();
    }

    @Override
    public String map() {
        Log.e("AddAttendeePeople", strMap);
        return strMap;
    }

    @Override
    public String buyWay() {
        return buyWay + "";
    }

    @Override
    public void showSuccess(ModifyData data, String refCode) {
        mAddList.remove(index);
        //loadingFinished();
        //index = index + 1;
        SQLite.update(Attends.class).set(Attends_Table.addSync.is(Constants.ADD_SYNC)).where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.refCodes.is(refCode)).async().execute();
        Delete.table(Attends.class, OperatorGroup.clause().and(Attends_Table.eventId.is(mEventId)).and(Attends_Table.refCodes.is(refCode)).and(Attends_Table.attendId.is(0)));
        if (index < mAddList.size()) {
            summitAttendee(index);
        } else {
            tvConfirmAttendee.setText(R.string.sure_add);
            Toast.makeText(this, R.string.success_add, Toast.LENGTH_SHORT).show();
            EventBus.getDefault().postSticky(new MsgEvent(Common.ADD_ATTENDEE_SUCCESS));
            AppManager.getAppManager().finishActivity();
        }
    }

    @Override
    public void showFailed(ModifyData data) {
        //loadingFinished();
        index = 0;
        tvConfirmAttendee.setText(R.string.success_add);
        Toast toast = Toast.makeText(AddAttendPeopleNext.this, R.string.add_people_failed, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 弹出对话框
     */
    private void showDialog() {
        View view = View.inflate(this, R.layout.exit_dialog, null);
        final TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        final TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        final TextView tv_exit_cancel = (TextView) view.findViewById(R.id.tv_exit_cancel);
        final Dialog editDialog = new Dialog(this);
        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        tv_content.setText(R.string.ischeckin);
        tv_confirm.setText(R.string.checkin);
        tv_exit_cancel.setText(R.string.cancel);
        editDialog.show();

        WindowManager windowManager = getWindowManager();
        final Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = editDialog.getWindow().getAttributes();
        Point size = new Point();
        display.getSize(size);
        lp.width = size.x;
        editDialog.getWindow().setAttributes(lp);
        editDialog.getWindow().setContentView(view);

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIn();
            }
        });
        tv_exit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
                //  EventBus.getDefault().postSticky(new MsgEvent("success"));
                EventBus.getDefault().postSticky(new MsgEvent(Common.ADD_ATTENDEE_SUCCESS));
                AppManager.getAppManager().finishActivity();

            }
        });
    }

    @PermissionGrant(REQUECT_CODE_PHONE_STATE)
    public void requestSdcardSuccess() {
        isGrantPhoneState = true;
    }

    @PermissionDenied(REQUECT_CODE_PHONE_STATE)
    public void requestSdcardFailed() {
        isGrantPhoneState = false;
    }

    /**
     * 根据设备Id和当前时间（精确到毫秒）生成ref_code
     *
     * @return
     */
    private String refCode() {
        //请求权限----获得设备Id,ref_code中采用前8位
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String sn = telephonyManager.getDeviceId();
        //获得当前时间,精确到毫秒
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        String str = format.format(curDate);
        return sn.substring(0, 8) + "-" + str;
    }

    /**
     * 签到
     */
    private void checkIn() {
        if (NetUtil.isConnected(this)) {
            checkInPresenter = new AddPeopleCheckInPresenter(this);
            for (int i = 0; i < mAddList.size(); i++) {
                setCheckInStatus(1, Constants.NOTSYNC);
                checkInPresenter.addPeopleCheckIn(mAddList.get(i).getRefCode());
            }
        } else {
            setCheckInStatus(1, Constants.NOTSYNC);
            //  EventBus.getDefault().postSticky(new MsgEvent("success"));
            AppManager.getAppManager().finishActivity();

        }

    }

    /**
     * 将签到状态同步到数据库中
     *
     * @param isSync
     */
    private void setCheckInStatus(int checkin, int isSync) {
        for (int i = 0; i < mAddList.size(); i++) {
            BackupCheckin backupCheckin = new Select().from(BackupCheckin.class).where(BackupCheckin_Table.eventId.is(mEventId)).and(BackupCheckin_Table.refCodes.is(mAddList.get(i).getRefCode())).querySingle();
            if (backupCheckin == null) {
                Attends attends = new Attends();
                attends.eventId = mAddList.get(i).getEventId();
                attends.gsonUser = mAddList.get(i).getGsonUser();
                attends.checkins = mAddList.get(i).getCheckin();
                attends.attendId = mAddList.get(i).getAttendeeId();
                attends.ticketIds = mAddList.get(i).getTicketId();
                attends.names = mAddList.get(i).getName();
                attends.audits = mAddList.get(i).getAudit();
                attends.payStatuss = mAddList.get(i).getPayStatus();
                attends.strSort = mAddList.get(i).getSort();
                attends.refCodes = mAddList.get(i).getRefCode();
                attends.updateTimes = mAddList.get(i).getUpdateTime();
                attends.buyWays = mAddList.get(i).getBuyWay();
                attends.addMap = mAddList.get(i).getAdd();
                attends.imgPath = mAddList.get(i).getImagePath();
                attends.userId = Integer.parseInt(userId);
                DBUtil.addCheckinToBackup(attends);
            }
            SQLite.update(BackupCheckin.class).set(BackupCheckin_Table.checkins.is(checkin)).where(BackupCheckin_Table.eventId.is(mEventId)).and(BackupCheckin_Table.refCodes.is(mAddList.get(i).getRefCode())).async().execute();
            SQLite.update(Attends.class).set(Attends_Table.checkins.is(checkin),
                    Attends_Table.isSync.is(isSync))
                    .where(Attends_Table.eventId.is(mEventId)).and(Attends_Table.refCodes.is(mAddList.get(i).getRefCode())).async().execute();
        }
    }

    @Override
    public String checkInStatus() {
        return checkStatus + "";
    }

    @Override
    public String checkInupdateTime() {
        return updateTime();
    }


    @Override
    public void showCheckInSuccess(CheckIn checkIn) {
        setCheckInStatus(1, Constants.SYNC);
        EventBus.getDefault().postSticky(new MsgEvent(Common.ADD_ATTENDEE_SUCCESS));
        AppManager.getAppManager().finishActivity();

    }

    @Override
    public void showCheckInFailed(String errInfo) {
        Toast toast = Toast.makeText(this, R.string.modify_failed, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().postSticky(Common.ADD_ATTENDEE_SUCCESS);
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
        AddImage img = new AddImage();
        img.setImgPath(response);
    }

    @Override
    public void uploadFailed(String response) {

    }

}
