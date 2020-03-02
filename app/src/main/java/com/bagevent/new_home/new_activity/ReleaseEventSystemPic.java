package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.new_home.new_interface.new_view.UploadEventInfoView;
import com.bagevent.new_home.new_interface.presenter.UploadEventInfoPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by zwj on 2016/9/21.
 */
public class ReleaseEventSystemPic extends BaseActivity implements View.OnClickListener, MultiItemTypeAdapter.OnItemClickListener,UploadEventInfoView {

    private AutoLinearLayout ll_sys_pic_back;
    private RecyclerView rcSystemPic;
    private ArrayList<Integer> mPics;
    private CommonAdapter<Integer> adapter;
    private int eventId = -1;
    private String userId;
    private String textName;
    private String textValue;
    private UploadEventInfoPresenter uploadEventLogoPresenter;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event_system_pic);
//        Intent intent = getIntent();
//        eventId = intent.getIntExtra("eventId",-1);
//        userId = SharedPreferencesUtil.get(this,"userId","");
//        initData();
//        initView();
//        setAdapter();
//        setListener();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event_system_pic);
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId",-1);
        userId = SharedPreferencesUtil.get(this,"userId","");
        initData();
        initView();
        setAdapter();
        setListener();
    }

    private void setAdapter() {
        adapter = new CommonAdapter<Integer>(this,R.layout.release_event_system_pic_item,mPics) {
            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                Glide.with(ReleaseEventSystemPic.this).load(integer).into((ImageView) holder.getView(R.id.iv_sys_pic));
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        rcSystemPic.setLayoutManager(gridLayoutManager);
        rcSystemPic.setAdapter(adapter);
    }

    private void initData() {
        mPics = new ArrayList<Integer>();
        mPics.add(R.drawable.logo_01);
        mPics.add(R.drawable.logo_02);
        mPics.add(R.drawable.logo_03);
        mPics.add(R.drawable.logo_04);
        mPics.add(R.drawable.logo_05);
        mPics.add(R.drawable.logo_06);
        mPics.add(R.drawable.logo_07);
        mPics.add(R.drawable.logo_08);
        mPics.add(R.drawable.logo_09);
        mPics.add(R.drawable.logo_10);
        mPics.add(R.drawable.logo_11);
        mPics.add(R.drawable.logo_12);
        mPics.add(R.drawable.logo_13);
        mPics.add(R.drawable.logo_14);
        mPics.add(R.drawable.logo_15);
        mPics.add(R.drawable.logo_16);
        mPics.add(R.drawable.logo_17);
        mPics.add(R.drawable.logo_18);
        mPics.add(R.drawable.logo_19);
        mPics.add(R.drawable.logo_20);
        mPics.add(R.drawable.logo_21);
        mPics.add(R.drawable.logo_22);
        mPics.add(R.drawable.logo_23);
        mPics.add(R.drawable.logo_24);
    }

    private void setListener() {
        ll_sys_pic_back.setOnClickListener(this);
        adapter.setOnItemClickListener(this);
    }

    private void initView() {
        rcSystemPic = (RecyclerView) findViewById(R.id.rc_sys_pic);
        ll_sys_pic_back = (AutoLinearLayout) findViewById(R.id.ll_release_sys_pic_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_release_sys_pic_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        String name = view.getResources().getResourceEntryName(mPics.get(position));
        if(NetUtil.isConnected(this)) {
            textName = "logo";
            textValue = "/resources/img/event_logo/" + name.substring(5,name.length()) + ".jpg";
            uploadEventLogoPresenter = new UploadEventInfoPresenter(this);
            uploadEventLogoPresenter.eventLogo();
        }else {
            Toast toast = Toast.makeText(this, R.string.net_err, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
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
    public String textValue() {
        return textValue;
    }

    @Override
    public void upEventLogoSuccess() {
        EventBus.getDefault().postSticky(new MsgEvent("fromChildPage"));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void upEventLogoFailed(String errInfo) {
        Toast toast = Toast.makeText(this, errInfo, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
