package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.UpLoadImagePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpLoadImageView;
import com.bagevent.common.Constants;
import com.bagevent.new_home.data.CreateEventData;
import com.bagevent.new_home.new_interface.new_view.CreateEventView;
import com.bagevent.new_home.new_interface.new_view.PublishEventView;
import com.bagevent.new_home.new_interface.new_view.SaveEventLocationView;
import com.bagevent.new_home.new_interface.new_view.UploadEventInfoView;
import com.bagevent.new_home.new_interface.presenter.CreateEventPresenter;
import com.bagevent.new_home.new_interface.presenter.PublishEventPresenter;
import com.bagevent.new_home.new_interface.presenter.SaveLocationPresenter;
import com.bagevent.new_home.new_interface.presenter.UploadEventInfoPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.SoftKeyboardStateHelper;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * Created by zwj on 2016/9/13.
 */
public class ReleaseEvent extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, CreateEventView, SaveEventLocationView, UpLoadImageView, UploadEventInfoView,PublishEventView {

    private AutoLinearLayout eventPageBack;
    private AutoLinearLayout eventRelease;
    private ImageView eventPage;
    private EditText eventTitle;
    private ImageView clearTitle;
    private SwipeRefreshLayout swipeReleaseEvent;

    /**
     * 发布需求的具体选项
     */
    private AutoRelativeLayout eventTime;
    private AutoRelativeLayout eventLocaion;
    private AutoRelativeLayout eventDetail;
    private AutoRelativeLayout eventSponsor;
    private AutoRelativeLayout eventTicket;
    private AutoRelativeLayout eventForm;

    /**
     * 显示文本
     */
    private TextView tv_event_time;//时间
    private TextView tv_event_location;//地点
    private TextView tv_event_master;//主办方
    private TextView tv_event_detail;//详情
    private TextView tv_event_ticket;//门票
    private TextView tv_event_enter;//报名设置

    /**
     * 输入法
     */
    private InputMethodManager inputMethodManager;
    /**
     * popupwindow
     */
    private TextView tv_motify_userInfo;
    private TextView tv_add_contact;
    private TextView tv_popup_cancel;
    private View mPopUpView;
    private PopupWindow mPopupWindow;

    /**
     * 创建活动
     */
    private CreateEventPresenter createEventPresenter;
    private String userId = "";
    private int eventId = -1;

    private SaveLocationPresenter saveLocationPresenter;
    private UploadEventInfoPresenter uploadEventInfoPresenter;
    private UpLoadImagePresenter upLoadImagePresenter;
    private String eventName = "";
    private String textName = "";
    private int addrType = -1;
    private String address = "";
    private String sponsorId = "";
//    private int tempUserId = -1;
    private File tempFile;
    private String textValue = "";
    private String detail = "";

    private SoftKeyboardStateHelper softKeyboardStateHelper;
    private SoftObserveListener listener;
    private PublishEventPresenter publishEventPresenter;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event);
//        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
//        userId = SharedPreferencesUtil.get(this, "userId", "");
//        initView();
//        initPopupWindow();
//        setStatus();
//        setListener();
//        initData();
//        observerKeyboard();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//软键盘
        userId = SharedPreferencesUtil.get(this, "userId", "");
        initView();
        initPopupWindow();
        setStatus();
        setListener();
        initData();
        observerKeyboard();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        if (softKeyboardStateHelper!=null){
            softKeyboardStateHelper.removeSoftKeyboardStateListener(listener);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals("fromChildPage")) {
            initData();
        }
    }

    /**
     * 监听软键盘
     */

    private void observerKeyboard() {
        listener = new SoftObserveListener();
        softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.release_event));
        softKeyboardStateHelper.addSoftKeyboardStateListener(listener);
    }

    private class SoftObserveListener implements SoftKeyboardStateHelper.SoftKeyboardStateListener {

        @Override
        public void onSoftKeyboardOpened(int keyboardHeightInPx) {
          //  Log.e("fdf","fdf");
            if(clearTitle.getVisibility() == View.GONE && !TextUtils.isEmpty(eventTitle.getText().toString())) {
                clearTitle.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onSoftKeyboardClosed() {
         //   Log.e("fdf","fdfaa");
            if(clearTitle.getVisibility() == View.VISIBLE) {
                clearTitle.setVisibility(View.GONE);
            }
            if(NetUtil.isConnected(ReleaseEvent.this)) {
                if(!eventTitle.getText().toString().equals(eventName) && !TextUtils.isEmpty(eventTitle.getText().toString())) {
                    textName = "eventName";
                    textValue = eventTitle.getText().toString();
                    uploadEventInfoPresenter = new UploadEventInfoPresenter(ReleaseEvent.this);
                    uploadEventInfoPresenter.eventLogo();
                }
            }else {
                setToastMsg(R.string.net_err);
            }
        }
    }

    /**
     * 发布活动
     */
    private void publish() {
        if(NetUtil.isConnected(this)) {
            if(!TextUtils.isEmpty(eventTitle.getText().toString())) {//判断条件是否符合发布活动
                if(!TextUtils.isEmpty(tv_event_time.getText().toString())) {//活动时间
                    if(!TextUtils.isEmpty(tv_event_location.getText().toString())) {//活动地点
                        if(!TextUtils.isEmpty(tv_event_master.getText().toString())) {//主办方
                            if(!TextUtils.isEmpty(tv_event_detail.getText().toString())) {//详情
                                if(!TextUtils.isEmpty(tv_event_ticket.getText().toString())) {//门票
                                    publishEventPresenter = new PublishEventPresenter(this);
                                    publishEventPresenter.publishEvent();
                                }else {
                                    setToastMsg(R.string.event_ticket);
                                }
                            }else {
                                setToastMsg(R.string.event_detail);
                            }

                        }else {
                            setToastMsg(R.string.event_sponsor);
                        }
                    }else {
                        setToastMsg(R.string.event_address);
                    }
                }else {
                    setToastMsg(R.string.event_time);
                }
            }else {
                setToastMsg(R.string.event_name);
            }
        }else {
            setToastMsg(R.string.net_err);
        }


    }


    private void initData() {
        if (NetUtil.isConnected(this)) {
            createEventPresenter = new CreateEventPresenter(this);
            createEventPresenter.create();
        } else {
            Glide.with(this).load(R.drawable.logo_11).into(eventPage);
            setToastMsg(R.string.net_err);
        }
    }

    private void setOnLineEvent() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        textName = "address";
        addrType = 1;
        address = "线上活动";
        if (NetUtil.isConnected(this)) {
            saveLocationPresenter = new SaveLocationPresenter(this);
            saveLocationPresenter.saveLocation();
        } else {
            setToastMsg(R.string.net_err);
        }
    }

    /**
     * 从相机选择图片
     */
    private void openCameraChoose() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
//        RxGalleryFinal
//                .with(this)
//                .image()
//                .radio()
//                .hideCamera()
//                .imageLoader(ImageLoaderType.GLIDE)
//                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
//                    @Override
//                    protected void onEvent(ImageRadioResultEvent result) throws Exception {
//                        File file = new File(result.getResult().getOriginalPath());
//                        Luban.get(ReleaseEvent.this)
//                                .load(file)
//                                .putGear(Luban.THIRD_GEAR)
//                                .setCompressListener(new OnCompressListener() {
//                                    @Override
//                                    public void onStart() {
//
//                                    }
//                                    @Override
//                                    public void onSuccess(File file) {
//                                        if(NetUtil.isConnected(ReleaseEvent.this)) {
//                                            tempFile = file;
//                                            upLoadImagePresenter = new UpLoadImagePresenter(ReleaseEvent.this);
//                                            upLoadImagePresenter.upLoadImageFile();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//
//                                    }
//                                }).launch();
//
//                    }
//                })
//                .openGallery();
    }

    private Point getScreenWidthAndHeight() {
        Point point = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(point);
        return point;
    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = this.getResources().getDimensionPixelSize(resourceId);
            AutoRelativeLayout.LayoutParams params1 = new AutoRelativeLayout.LayoutParams(120, 100);
            params1.setMargins(0, statusBarHeight, 0, 0);
            AutoRelativeLayout.LayoutParams params2 = new AutoRelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.setMargins(getScreenWidthAndHeight().x - 150, statusBarHeight + 20, 0, 0);
            eventPageBack.setLayoutParams(params1);
            eventRelease.setLayoutParams(params2);
            StatusBarUtil.setTranslucentForImageView(this, 0, null);
        }
    }

    /**
     * 设置提示信息
     */
    private void setToastMsg(int msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void initPopupWindow() {
        mPopUpView = getLayoutInflater().inflate(R.layout.attend_people_detail_popwindow, null);
        mPopupWindow = new PopupWindow(mPopUpView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_motify_userInfo = (TextView) mPopUpView.findViewById(R.id.tv_motify_userinfo);
        tv_add_contact = (TextView) mPopUpView.findViewById(R.id.tv_add_contact);
        tv_popup_cancel = (TextView) mPopUpView.findViewById(R.id.tv_popup_cancel);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopupWindow.setBackgroundDrawable(cd);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void setPop(View v, String value1, String value2) {
        tv_add_contact.setText(value2);
        tv_motify_userInfo.setText(value1);
        //产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        mPopupWindow.showAtLocation((View) v.getParent(), Gravity.BOTTOM, 0, 0);
    }

    private void setListener() {
        eventPageBack.setOnClickListener(this);
        eventRelease.setOnClickListener(this);
        eventPage.setOnClickListener(this);
        swipeReleaseEvent.setOnRefreshListener(this);

        tv_add_contact.setOnClickListener(this);
        tv_motify_userInfo.setOnClickListener(this);
        tv_popup_cancel.setOnClickListener(this);
        clearTitle.setOnClickListener(this);
        eventDetail.setOnClickListener(this);

        eventTime.setOnClickListener(this);
        eventLocaion.setOnClickListener(this);
        eventSponsor.setOnClickListener(this);
        eventTicket.setOnClickListener(this);
        eventForm.setOnClickListener(this);
    }

    private void initView() {
        eventPageBack = (AutoLinearLayout) findViewById(R.id.ll_event_back);
        eventRelease = (AutoLinearLayout) findViewById(R.id.ll_event_release);
        eventPage = (ImageView) findViewById(R.id.iv_event_page);
        eventTitle = (EditText) findViewById(R.id.et_event_title);
        clearTitle = (ImageView) findViewById(R.id.iv_clear_title);
        swipeReleaseEvent = (SwipeRefreshLayout) findViewById(R.id.swipe_release_event);

        eventTime = (AutoRelativeLayout) findViewById(R.id.rl_event_time);
        eventLocaion = (AutoRelativeLayout) findViewById(R.id.rl_event_locaion);
        tv_event_location = (TextView) findViewById(R.id.tv_event_location);
        eventDetail = (AutoRelativeLayout) findViewById(R.id.rl_event_detail);
        eventSponsor = (AutoRelativeLayout) findViewById(R.id.rl_event_sponsor);
        eventTicket = (AutoRelativeLayout) findViewById(R.id.rl_event_ticket);
        eventForm = (AutoRelativeLayout) findViewById(R.id.rl_event_form);

        tv_event_time = (TextView) findViewById(R.id.tv_event_time);
        tv_event_location = (TextView) findViewById(R.id.tv_event_location);
        tv_event_master = (TextView) findViewById(R.id.tv_event_master);
        tv_event_detail = (TextView) findViewById(R.id.tv_event_detail);
        tv_event_ticket = (TextView) findViewById(R.id.tv_event_ticket);
        tv_event_enter = (TextView) findViewById(R.id.tv_event_enter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {//首先判断是否有网络,无网络则不允许进入下个界面
            case R.id.ll_event_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_event_release:
                publish();
                break;
            case R.id.iv_event_page:
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(eventTitle.getWindowToken(), 0);
                }
                setPop(v, getString(R.string.select_form_camera), getString(R.string.use_system_pic));
                break;
            case R.id.tv_popup_cancel:
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.tv_motify_userinfo://设置线下活动或打开相册
                if(NetUtil.isConnected(this)) {
                    if (tv_motify_userInfo.getText().toString().contains(getString(R.string.set_place))) {
                        if (mPopupWindow != null && mPopupWindow.isShowing()) {
                            mPopupWindow.dismiss();
                        }
                        Intent intentLocation = new Intent(this, ReleaseEventLocation.class);
                        intentLocation.putExtra("eventId", eventId);
                        startActivity(intentLocation);
                    } else {
                        //openCameraChoose();
                    }
                }else {
                    setToastMsg(R.string.net_err);
                }
                break;
            case R.id.tv_add_contact://设置线上活动或打开系统图库
                if(tv_add_contact.getText().toString().equals("使用系统图库")) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    if(NetUtil.isConnected(this)) {
                        Intent intentSysPic = new Intent(this,ReleaseEventSystemPic.class);
                        intentSysPic.putExtra("eventId",eventId);
                        startActivity(intentSysPic);
                    }else {
                        setToastMsg(R.string.net_err);
                    }
                }else {
                    setOnLineEvent();
                }
                break;
            case R.id.iv_clear_title:
                eventTitle.setText("");
                break;
            case R.id.rl_event_time://设置活动时间
                if(NetUtil.isConnected(this)) {
                    Intent intent = new Intent(this, ReleaseEventTime.class);
                    intent.putExtra("eventId", eventId);
                    startActivity(intent);
                }else {
                    setToastMsg(R.string.net_err);
                }
                break;
            case R.id.rl_event_locaion:
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(eventTitle.getWindowToken(), 0);
                }
                setPop(v, getString(R.string.set_place1), getString(R.string.tv_address));
                break;
            case R.id.rl_event_detail://设置活动详情
                if(NetUtil.isConnected(this)) {
                    Intent intentDetail = new Intent(this, ReleaseEventDetail.class);
                    intentDetail.putExtra("eventId",eventId);
                    intentDetail.putExtra("detail",detail);
                    startActivity(intentDetail);
                }else {
                    setToastMsg(R.string.net_err);
                }
                break;
            case R.id.rl_event_sponsor://设置主办方
                if(NetUtil.isConnected(this)) {
                    Intent intentSponsor = new Intent(this, ReleaseEventSponsor.class);
                    intentSponsor.putExtra("eventId", eventId);
                    intentSponsor.putExtra("sponsorId", sponsorId);
                    startActivity(intentSponsor);
                }else {
                    setToastMsg(R.string.net_err);
                }
                break;
            case R.id.rl_event_ticket:
                if(NetUtil.isConnected(this)) {
                    Intent intentTicket = new Intent(this, ReleaseEventTicket.class);
                    intentTicket.putExtra("eventId", eventId);
                    startActivity(intentTicket);
                }else {
                    setToastMsg(R.string.net_err);
                }
                break;
            case R.id.rl_event_form:
                if(NetUtil.isConnected(this)) {
                    Intent intentForm = new Intent(this, ReleaseEventForm.class);
                    intentForm.putExtra("eventId", eventId);
                    startActivity(intentForm);
                }else {
                    setToastMsg(R.string.net_err);
                }

                break;
        }
    }

    @Override
    public int eventId() {
        return eventId;
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String userIds() {
        return userId;
    }

    @Override
    public void publishEventSuccess() {
        Toast toast = Toast.makeText(this, R.string.release_success, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void publishEventFailed(String errInfo) {
        Toast toast = Toast.makeText(this,errInfo,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    @Override
    public File upLoadFile() {
        return tempFile;
    }

    @Override
    public String userId() {
    //    Log.e("fd",userId);
        return userId;
    }


    @Override
    public void uploadSuccess(String response) {
        Glide.with(ReleaseEvent.this).load(Constants.imgsURL + response).into(eventPage);
        if(NetUtil.isConnected(this)) {
            textName = "logo";
            textValue = response;
        //    Log.e("fdf",textValue);
            uploadEventInfoPresenter = new UploadEventInfoPresenter(this);
            uploadEventInfoPresenter.eventLogo();
        }else {
            setToastMsg(R.string.net_err);
        }
    }

    @Override
    public void uploadFailed(String response) {
        Toast toast = Toast.makeText(this, response, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public String textName() {
        return textName;
    }

    @Override
    public String textValue() {
        //Log.e("fdf",textValue);
        return textValue;
    }

    @Override
    public void upEventLogoSuccess() {
        if(textValue.contains(".jpg") || textValue.contains(".png") || textValue.contains(".gif") || textValue.contains(".jpeg")) {
            Glide.with(ReleaseEvent.this).load(Constants.imgsURL + textValue).into(eventPage);
        }else {
          //  Log.e("fdf","Fdf");
            eventTitle.setText(textValue);
        }
    }

    @Override
    public void upEventLogoFailed(String errInfo) {
        Toast toast = Toast.makeText(this, errInfo, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public int addrType() {
        return addrType;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public void showSaveLocationSuccess() {
        tv_event_location.setText(address);
    }

    @Override
    public void showSaveLocationFailed(String errInfo) {
        Toast toast = Toast.makeText(this, errInfo, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public int type() {
        return 1;
    }

    @Override
    public void createSuccess(CreateEventData response) {
        eventId = response.getRespObject().getEvent().getEventId();
      //  Log.e("fd",eventId+"");
        if (!TextUtils.isEmpty(response.getRespObject().getEvent().getLogo())) {
            Glide.with(this).load(Constants.imgsURL + response.getRespObject().getEvent().getLogo()).into(eventPage);
        } else {
            Glide.with(this).load(R.drawable.logo_11).into(eventPage);
        }

        if (!TextUtils.isEmpty(response.getRespObject().getEvent().getEventName())) {
            eventName = response.getRespObject().getEvent().getEventName();
            eventTitle.setText(response.getRespObject().getEvent().getEventName());
        }

        if (!TextUtils.isEmpty(response.getRespObject().getEvent().getStartTime()) && !TextUtils.isEmpty(response.getRespObject().getEvent().getEndTime())) {//时间
            String time = response.getRespObject().getEvent().getStartTime() + "~" + response.getRespObject().getEvent().getEndTime();
            tv_event_time.setText(time);
        }

        if (response.getRespObject().getEvent().getAddrType() == 1) {
            tv_event_location.setText(R.string.tv_address);
        } else {
            tv_event_location.setText(response.getRespObject().getEvent().getAddress());
        }

        if (response.getRespObject().isIsTicket()) {//门票
            tv_event_ticket.setText(R.string.already_setting);
        }

        if (response.getRespObject().isIsForm()) {//表单
            tv_event_enter.setText(R.string.already_setting);
        }
        if (response.getRespObject().getOrganizerList() != null) {
            String sponsor = "";
            sponsorId = "";
            if (response.getRespObject().getOrganizerList().size() > 0) {
                for (int i = 0; i < response.getRespObject().getOrganizerList().size(); i++) {
                    if (response.getRespObject().getOrganizerList().get(i).getUsedState() == 1) {
                        sponsor = sponsor + response.getRespObject().getOrganizerList().get(i).getOrganizerName() + ",";
                        sponsorId = sponsorId + response.getRespObject().getOrganizerList().get(i).getOrganizerId() + ",";
                    }
                }
                if(sponsor.length() > 1) {
                    sponsor = sponsor.substring(0, sponsor.length() - 1);
                    sponsorId = sponsorId.substring(0, sponsorId.length() - 1);
                    tv_event_master.setText(sponsor);
                }
            }
        }

        detail = response.getRespObject().getEvent().getEventContent();
        if(!TextUtils.isEmpty(detail)) {
            tv_event_detail.setText(R.string.already_setting);
        }else {
            tv_event_detail.setText("");
        }
    }

    @Override
    public void createFailed(String errInfo) {
        Toast toast = Toast.makeText(this, errInfo, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onRefresh() {
        initData();
        swipeReleaseEvent.setRefreshing(false);
    }
}
