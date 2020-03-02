package com.bagevent.activity_manager.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.adapter.AuditViewpagerAdapter;
import com.bagevent.activity_manager.manager_fragment.data.AttendPeople;
import com.bagevent.activity_manager.manager_fragment.data.FormData;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/7/7.
 */
public class AuditDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private ViewPager vp_audit;

    private ArrayList<AttendPeople.RespObjectBean.ObjectsBean> mList;
    private List<FormData> mDetail = new ArrayList<FormData>();
    private int eventId = -1;
    private int position = -1;
    private int attendId = -1;

    private AuditViewpagerAdapter adapter;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.activity_audit_detail);
//        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white), Constants.STATUS_ALPHA);
//
//        Bundle bundle = getIntent().getExtras();
//        mList = (ArrayList<AttendPeople.RespObjectBean.ObjectsBean>)bundle.getSerializable("list");
//        eventId = bundle.getInt("eventId");
//        position = bundle.getInt("position");
//        attendId = bundle.getInt("attendId");
//        String formType = (String) NetUtil.readObject(this, Constants.FORM_FILE_NAME + eventId + "");
//        if (formType != null) {
//            FormType mType = new Gson().fromJson(formType, FormType.class);//解析表单数据
//            for (int i = 0; i < mType.getRespObject().size(); i++) {
//                FormData data = new FormData();
//                data.setFormName(mType.getRespObject().get(i).getShowName());
//                data.setFormFieldId(mType.getRespObject().get(i).getFormFieldId());
//                data.setFieldTypeName(mType.getRespObject().get(i).getFieldTypeName());
//                mDetail.add(data);
//            }
//        }
//        initView();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_audit_detail);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.white), Constants.STATUS_ALPHA);

        Bundle bundle = getIntent().getExtras();
        mList = (ArrayList<AttendPeople.RespObjectBean.ObjectsBean>)bundle.getSerializable("list");
        eventId = bundle.getInt("eventId");
        position = bundle.getInt("position");
        attendId = bundle.getInt("attendId");
        String formType = (String) NetUtil.readObject(this, Constants.FORM_FILE_NAME + eventId + "");
        if (formType != null) {
            FormType mType = new Gson().fromJson(formType, FormType.class);//解析表单数据
            for (int i = 0; i < mType.getRespObject().size(); i++) {
                FormData data = new FormData();
                data.setFormName(mType.getRespObject().get(i).getShowName());
                data.setFormFieldId(mType.getRespObject().get(i).getFormFieldId());
                data.setFieldTypeName(mType.getRespObject().get(i).getFieldTypeName());
                mDetail.add(data);
            }
        }
        initView();
    }

    private void initView() {
        vp_audit = (ViewPager)findViewById(R.id.vp_audit);
        adapter = new AuditViewpagerAdapter(mList,mDetail,eventId,attendId,this);
        vp_audit.setAdapter(adapter);
        vp_audit.setOffscreenPageLimit(0);
        vp_audit.setCurrentItem(position);

  //      vp_audit.getCurrentItem();
     //   vp_audit.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
