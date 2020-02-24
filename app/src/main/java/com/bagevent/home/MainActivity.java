package com.bagevent.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.common.Constants;
import com.bagevent.home.fragment.ExercisingFragment;
import com.bagevent.home.fragment.FinishedExerciseFragment;
import com.bagevent.login.LoginActivity;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.synchro_data.NetWorkBroadcast;
import com.bagevent.util.AppManager;
import com.bagevent.util.LogUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.view.CircleTextView;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.nineoldandroids.view.ViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.v_eventing)
    View vEventing;
    @BindView(R.id.v_finished)
    View vFinished;
    private DrawerLayout mDrawerLayout;
    /*
     * 设置fragment
     *
     * @param savedInstanceState
     */
    private static final int EXERCISINGFRAGMENT = 0x1;
    private static final int FINISHEDFRAGMENT = 0x2;

    private ExercisingFragment exercisingFragment;
    private FinishedExerciseFragment finishedExerciseFragment;

    private FragmentManager mFragmentManager;
    private FrameLayout home_content;
    private LinearLayout ll_exercising;
    private LinearLayout ll_finished;
    private TextView text_excising, text_finished;


    /**
     * 侧滑界面
     */
    //  private AutoLinearLayout btn_siding;
    private TextView text_email;
    private TextView text_phone;
    private TextView tv_name;
    private CircleTextView left_tv_icon;
    private CircleImageView profile_image;
    private AutoRelativeLayout rl_exit;
    private AutoLinearLayout ll_contact;
    private AutoLinearLayout ll_send_email;
    private TextView tv_phonenum;
    /**
     * 用户信息
     * 邮箱,手机号,头像,昵称
     */
    private String eMail = "";
    private String cellPhone = "";
    private String imgUrl = "";
    private String userName = "";

    /**
     * 发送监听网络状态的广播
     */
    private NetWorkBroadcast broadcast;

    private int statusBarColor;
    private ViewGroup content;

    /**
     * 标识连续按两次返回键退出
     */
    private long exitTime = 0;
    private NormalAlertDialog exitDialog;


    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = this.getResources().getDimensionPixelSize(resourceId);
            AutoRelativeLayout.LayoutParams params = new AutoRelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, statusBarHeight, 0, 0);
            AutoFrameLayout frameLayout = (AutoFrameLayout) findViewById(R.id.frameLayout);
            frameLayout.setLayoutParams(params);
            statusBarColor = ContextCompat.getColor(this, R.color.white);
            StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.id_drawerLayout), statusBarColor, 112);
        }
        //初始化友盟统计
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //初始化mob分享
        // ShareSDK.initSDK(this);
        initView();
        initEvents();
        setListener();

        getUserInfo();
        sendNetWorkBroadcast();
        exitAccount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void OpenLeftMenu(View view) {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    private void initEvents() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;
                if (drawerView.getTag().equals("LEFT")) {
                    float leftScale = 1 - 0.3f * scale;
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }
        });
    }

    private void setTabFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllTransaction(transaction);
        setDefaultColor();
        switch (index) {
            case EXERCISINGFRAGMENT:
                ll_exercising.setSelected(true);
                if (exercisingFragment == null) {
                    exercisingFragment = new ExercisingFragment();
                    transaction.add(R.id.home_content, exercisingFragment);
                } else {
                    transaction.show(exercisingFragment);
                }
                vFinished.setVisibility(View.GONE);
                vEventing.setVisibility(View.VISIBLE);
                break;
            case FINISHEDFRAGMENT:
                ll_finished.setSelected(true);
                if (finishedExerciseFragment == null) {
                    finishedExerciseFragment = new FinishedExerciseFragment();
                    transaction.add(R.id.home_content, finishedExerciseFragment);
                } else {
                    transaction.show(finishedExerciseFragment);
                }
                vFinished.setVisibility(View.VISIBLE);
                vEventing.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void setDefaultColor() {
        vEventing.setVisibility(View.GONE);
        vFinished.setVisibility(View.GONE);
    }

    private void hideAllTransaction(FragmentTransaction transaction) {
        if (exercisingFragment != null) {
            transaction.hide(exercisingFragment);
        }
        if (finishedExerciseFragment != null) {
            transaction.hide(finishedExerciseFragment);
        }

        ll_exercising.setSelected(false);
        ll_finished.setSelected(false);

    }

    private void setListener() {
        ll_finished.setOnClickListener(this);
        ll_exercising.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
        ll_contact.setOnClickListener(this);
        ll_send_email.setOnClickListener(this);
        //btn_siding.setOnClickListener(this);
    }

    private void getUserInfo() {
        imgUrl = SharedPreferencesUtil.get(this, "avatar", "");
        eMail = SharedPreferencesUtil.get(this, "email", "");
        cellPhone = SharedPreferencesUtil.get(this, "cellphone", "");
        userName = SharedPreferencesUtil.get(this, "userName", "");
        tv_name.setText(userName);
        text_phone.setText(cellPhone);
        text_email.setText(eMail);
        //  Log.e("fd",text_phone.getText().toString());
        if (TextUtils.isEmpty(imgUrl)) {
            profile_image.setVisibility(View.INVISIBLE);
            left_tv_icon.setVisibility(View.VISIBLE);
//            left_tv_icon.setText(userName.substring(0,1));
        } else {
            String img = Constants.imgsURL + imgUrl;
            // Log.e("fd",img);
            profile_image.setVisibility(View.VISIBLE);
            left_tv_icon.setVisibility(View.INVISIBLE);
            Glide.with(this).load(img).into(profile_image);
        }
    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.my_event);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        content = (AutoLinearLayout) findViewById(R.id.main);
        home_content = (FrameLayout) findViewById(R.id.home_content);
        ll_exercising = (LinearLayout) findViewById(R.id.ll_exercising);
        ll_finished = (LinearLayout) findViewById(R.id.ll_finished);
        text_excising = (TextView) findViewById(R.id.text_excsing);
        text_finished = (TextView) findViewById(R.id.text_finished);

        //btn_siding = (AutoLinearLayout)findViewById(R.id.btn_siding);
        text_phone = (TextView) findViewById(R.id.text_phone);
        text_email = (TextView) findViewById(R.id.text_email);
        tv_name = (TextView) findViewById(R.id.tv_name);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        left_tv_icon = (CircleTextView) findViewById(R.id.left_tv_icon);
        rl_exit = (AutoRelativeLayout) findViewById(R.id.rl_exit);
        ll_contact = (AutoLinearLayout) findViewById(R.id.ll_contact);
        ll_send_email = (AutoLinearLayout) findViewById(R.id.ll_send_email);
        tv_phonenum = (TextView) findViewById(R.id.tv_phonenum);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//关闭drawerlayout的手势滑动功能
        mFragmentManager = getSupportFragmentManager();
        setTabFragment(EXERCISINGFRAGMENT);
    }

    private void exitAccount() {
        exitDialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setTitleTextColor(R.color.black_light)
                .setContentText(getString(R.string.exit_now_account))
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        exitDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        exitDialog.dismiss();
                        SharedPreferencesUtil.clear(MainActivity.this);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .build();
    }

    private void setDialog() {
        View view = View.inflate(this, R.layout.exit_dialog, null);
        final TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        final TextView tv_exit_cancel = (TextView) view.findViewById(R.id.tv_exit_cancel);
        final Dialog editDialog = new Dialog(this);
        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
                editDialog.dismiss();
                SharedPreferencesUtil.clear(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_exit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exercising:
                setTabFragment(EXERCISINGFRAGMENT);
                break;
            case R.id.ll_finished:
                setTabFragment(FINISHEDFRAGMENT);
                break;
            case R.id.rl_exit:
                //setDialog();
                exitDialog.show();
                break;
            case R.id.ll_contact:
                String num = tv_phonenum.getText().toString();
                num = num.trim();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
                try {
                    startActivity(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ll_send_email:
                break;
        }
    }

    private void sendNetWorkBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadcast = new NetWorkBroadcast();
        registerReceiver(broadcast, filter);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcast);
        super.onDestroy();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        }
//        return super.onKeyDown(keyCode,event);
//    }

    private void exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(getApplicationContext(), R.string.exit_app, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().AppExit(this);
            System.exit(0);
        }
    }

    @OnClick(R.id.ll_title_back)
    public void onViewClicked() {
        AppManager.getAppManager().finishActivity();
    }



}
