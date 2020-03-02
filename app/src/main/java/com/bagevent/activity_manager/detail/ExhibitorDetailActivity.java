package com.bagevent.activity_manager.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.AcManager;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.adapter.ExhibitorDetialAdapter;
import com.bagevent.activity_manager.manager_fragment.data.ExhibitorData;
import com.bagevent.activity_manager.manager_fragment.data.ExhibitorDetail;
import com.bagevent.activity_manager.manager_fragment.data.ExhibitorEntity;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.UpdateNotesPresenter;
import com.bagevent.common.Constants;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.data.AttendeeInfoEntity;
import com.bagevent.new_home.new_activity.BaseActivity;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bagevent.view.listener.ConfirmListener;
import com.bagevent.view.mydialog.EditDialog;
import com.bumptech.glide.Glide;
import com.google.gson.JsonParser;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.gudong.loading.LoadingView;
import okhttp3.Call;
import okhttp3.Response;

public class ExhibitorDetailActivity extends BaseActivity implements PopupMenu.OnMenuItemClickListener{

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
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.loading)
    LoadingView loading;
    @BindView(R.id.ll_audit_action)
    AutoLinearLayout llAuditAction;
    @BindView(R.id.rv_exhibitor_detial)
    RecyclerView rvExhibitorDetail;
    private PopupMenu menu;
    private String remark;
    private int REMARK = 0;
    private int AUDIT = 1;

    private int eventId;
    private int exhibitorId;
    private int audit;

    @OnClick({R.id.ll_right_click,R.id.ll_title_back,R.id.ll_audit_refuse, R.id.ll_audit_pass,})
    public void click(View view){
        switch (view.getId()){
            case R.id.ll_right_click:
                menu.show();
                break;
            case R.id.ll_title_back:
                finish();
                break;
            case R.id.ll_audit_refuse:
                updateExhibitor(AUDIT,"2");
                break;
            case R.id.ll_audit_pass:
                updateExhibitor(AUDIT,"1");
                break;
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exhibitor_detail);
//        ButterKnife.bind(this);
//        initView();
//        getIntentValue();
//        initData();
//        showPopMenu(llRightClick);
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exhibitor_detail);
        ButterKnife.bind(this);
        initView();
        getIntentValue();
        initData();
        showPopMenu(llRightClick);
    }

    private void showPopMenu(View v) {
        menu = new PopupMenu(this, v);
        menu.getMenuInflater().inflate(R.menu.item_menu_remark, menu.getMenu());
        menu.setOnMenuItemClickListener(this);
        setForceShowIcon(menu);
    }
    public void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        Glide.with(this).load(R.drawable.img_more).into(ivRight2);
        ivRight.setVisibility(View.GONE);
        tvTitle.setSelected(true);
        tvTitle.setText(R.string.exhibitor_detail);
    }

    private void getIntentValue() {
        Intent intent=getIntent();
        eventId=intent.getIntExtra("eventId",0);
        exhibitorId=intent.getIntExtra("exhibitorId",0);
        audit=intent.getIntExtra("audit",-1);
        if (audit==0){
            llAuditAction.setVisibility(View.VISIBLE);
        }else {
            llAuditAction.setVisibility(View.GONE);
        }
    }

    private void initData() {
        if (!NetUtil.isConnected(getBaseContext())) {
            TosUtil.show(getString(R.string.check_network));
            return;
        }
        OkHttpUtil.Get(this)
                .url(Constants.NEW_URL+Constants.EXHIBITOR_DETAIL)
                .addParams("eventId",eventId+"")
                .addParams("exhibitorId",exhibitorId+"")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ExhibitorDetailActivity.this, R.string.error_exhibitor, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            parserDataList(response);
                            loadingFinished();
                        } else {
                            Toast.makeText(ExhibitorDetailActivity.this, R.string.error_exhibitor, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void parserDataList(String response) {

        ExhibitorDetail exhibitorDetail=new ExhibitorDetail(new JsonParser().parse(response).getAsJsonObject());

        ExhibitorDetail.RespObjectBean respObject = exhibitorDetail.getRespObject();
        ExhibitorDetail.ExExhibitorInfo exExhibitorInfo = respObject.getExExhibitorInfo();
        ExhibitorDetail.ExExhibitorInfoDTO exExhibitorInfoDTO = exExhibitorInfo.getExExhibitorInfoDTO();
        ArrayList<ExhibitorDetail.ExExhibitorReceiptOrgs> exExhibitorReceiptOrgs = respObject.getExExhibitorReceiptOrgs();
        ArrayList<ExhibitorEntity> exhibitorEntities = new ArrayList<>();

        exhibitorEntities.add(new ExhibitorEntity(ExhibitorEntity.TYPE_HEADER,exExhibitorInfo,exExhibitorInfoDTO,respObject));

        exhibitorEntities.add(new ExhibitorEntity(ExhibitorEntity.TYPE_NAME,getString(R.string.exhibitor_right)));

        exhibitorEntities.add(new ExhibitorEntity(ExhibitorEntity.TYPE_BOOTH,exExhibitorInfo));

        if (exExhibitorReceiptOrgs.size()>0){
            exhibitorEntities.add(new ExhibitorEntity(ExhibitorEntity.TYPE_NAME,getString(R.string.payment_record)));
            for (int i = 0; i < exExhibitorReceiptOrgs.size() ; i++) {
                exhibitorEntities.add(new ExhibitorEntity(ExhibitorEntity.TYPE_ITEM,exExhibitorReceiptOrgs.get(i)));
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ExhibitorDetialAdapter detailAdapter=new ExhibitorDetialAdapter(exhibitorEntities);
        rvExhibitorDetail.setAdapter(detailAdapter);
        rvExhibitorDetail.setLayoutManager(linearLayoutManager);
        rvExhibitorDetail.setHasFixedSize(true);
    }

    private void loadingFinished(){
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_add_remark:
                final EditDialog dialog = new EditDialog(this);
                if (!TextUtils.isEmpty(remark)) {
                    dialog.setText(remark);
                }
                dialog.setConfirmListener(new ConfirmListener() {
                    @Override
                    public void confirm(View view) {
                        if (!TextUtils.isEmpty(dialog.getText())) {
                            if (NetUtil.isConnected(ExhibitorDetailActivity.this)) {
                                remark = dialog.getText();
                                updateExhibitor(0,remark);
                            } else {
                                Toast.makeText(ExhibitorDetailActivity.this, R.string.check_network, Toast.LENGTH_SHORT).show();

                            }
                            dialog.dismiss();
                        } else {
                            Toast.makeText(ExhibitorDetailActivity.this, R.string.et_remarks_hint, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                break;
        }
        return false;
    }

    private void updateExhibitor(final int type, String value){
        OkHttpUtil.Post(ExhibitorDetailActivity.this)
                .url(Constants.NEW_URL+Constants.UPDATE_EXHIBITOR)
                .addParams("eventId",eventId+"")
                .addParams("exhibitorId",exhibitorId+"")
                .addParams("type",type+"")
                .addParams("value",value)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        praseError(type);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            praseSuccess(type);
                        } else {
                            praseError(type);
                        }
                    }
                });
    }

    private void praseSuccess(int type) {
        if (type==REMARK){
            Toast.makeText(ExhibitorDetailActivity.this, R.string.remarks_suceess, Toast.LENGTH_SHORT).show();
            initData();
        }else {
            EventBus.getDefault().post(new MessageEvent(MessageAction.REGRESH_EXHIBITOR_AUDIT));
            Toast.makeText(ExhibitorDetailActivity.this, R.string.audit_success, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void praseError(int type) {
        if (type==REMARK){
            Toast.makeText(ExhibitorDetailActivity.this, R.string.remarks_error, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(ExhibitorDetailActivity.this, R.string.audit_failed, Toast.LENGTH_SHORT).show();
        }
    }

}
