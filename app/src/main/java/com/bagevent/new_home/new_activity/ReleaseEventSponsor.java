package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.TicketInfo;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetTicketInfoView;
import com.bagevent.new_home.data.SponsorData;
import com.bagevent.new_home.new_interface.new_view.GetSponsorView;
import com.bagevent.new_home.new_interface.new_view.SaveSponsorView;
import com.bagevent.new_home.new_interface.presenter.GetSponsorPresenter;
import com.bagevent.new_home.new_interface.presenter.SaveSponsorPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.githang.android.snippet.adapter.ChoiceListAdapter;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.request.OkHttpRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by zwj on 2016/9/18.
 */
public class ReleaseEventSponsor extends BaseActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,GetSponsorView,SaveSponsorView{

    private AutoLinearLayout sponsorBack;
    private AutoLinearLayout sponsorConfirm;
    private ListView lv_sponsor;
    private SwipeRefreshLayout sponsorSwipe;
    private ArrayList<SponsorData.RespObjectBean> data;
    private GetSponsorPresenter getSponsorPresenter;
    private String userId = "";
    private int eventId = -1;
    private String sponsorId = "";
    private String organizerIds = "";
    private SaveSponsorPresenter saveSponsorPresenter;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event_sponsor);
//        userId = SharedPreferencesUtil.get(this,"userId","");
//        Intent intent =getIntent();
//        eventId = intent.getIntExtra("eventId",-1);
//        sponsorId = intent.getStringExtra("sponsorId");
//        initView();
//        getData();
//        setListener();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event_sponsor);
        userId = SharedPreferencesUtil.get(this,"userId","");
        Intent intent =getIntent();
        eventId = intent.getIntExtra("eventId",-1);
        sponsorId = intent.getStringExtra("sponsorId");
        initView();
        getData();
        setListener();
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
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if(event.mMsg.equals("from_ReleaseEventAddSponsor_add_success")) {
            getData();
        }
    }

    private void saveValue() {
        SparseBooleanArray array = lv_sponsor.getCheckedItemPositions();
        if(array.size() > 0) {
            for (int i = 0; i < array.size() ; i++) {
                if(array.valueAt(i)) {
                    organizerIds = organizerIds + data.get(array.keyAt(i)).getOrganizerId() + ",";
                }
            }
            if(organizerIds.length() > 0) {
                organizerIds = organizerIds.substring(0,organizerIds.length() - 1);
                if(NetUtil.isConnected(this)) {
                    saveSponsorPresenter = new SaveSponsorPresenter(this);
                    saveSponsorPresenter.saveEventSponsor();
                }else {
                    setToast(getString(R.string.net_err));
                }
            }else {
                setToast(getString(R.string.sponsor));
            }

        }else {
            setToast(getString(R.string.select_organize));
        }
    }

    private void setToast(String msg) {
        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    private void getData() {
        if(NetUtil.isConnected(this)) {
            getSponsorPresenter = new GetSponsorPresenter(this);
            getSponsorPresenter.sponsor();
        }else {
            setToast(getString(R.string.net_err));
        }

    }

    private void setAdapter() {
        final ChoiceListAdapter adapter = new ChoiceListAdapter<SponsorData.RespObjectBean>(this, R.layout.release_event_sponsor_item,
                data, R.id.sponsor_checkedView) {
            @Override
            protected void holdView(ChoiceLayout view) {
                view.hold(R.id.tv_sponsor_name);
            }

            @Override
            protected void bindData(ChoiceLayout view, int position, SponsorData.RespObjectBean data) {
                TextView text = view.get(R.id.tv_sponsor_name);
                if(!TextUtils.isEmpty(data.getOrganizerName())) {
                    text.setText(data.getOrganizerName());
                }
            }
        };
        lv_sponsor.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv_sponsor.setAdapter(adapter);
        lv_sponsor.clearChoices();
       for (int i = 0; i < data.size(); i++) {//设置默认选中项
            if(sponsorId.contains(data.get(i).getOrganizerId()+"")) {
                lv_sponsor.setItemChecked(i,true);
            }else {
                lv_sponsor.setItemChecked(i,false);
            }
        }
        lv_sponsor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Log.e("fd",lv_sponsor.getCheckedItemCount()+"");
                if(lv_sponsor.getCheckedItemCount() > 3) {
                    lv_sponsor.setItemChecked(i,false);
                    setToast(getString(R.string.select_three_organize));
                }
            }
        });

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
    public String userId() {
        return userId;
    }

    @Override
    public String textName() {
        return "organizer";
    }

    @Override
    public String organizerIds() {
        return organizerIds;
    }

    @Override
    public void saveSponsorsuccess() {
        EventBus.getDefault().postSticky(new MsgEvent("fromChildPage"));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void saveSponsorFailed(String errInfo) {

    }

    @Override
    public void getSponsor(SponsorData response) {
        data = new ArrayList<SponsorData.RespObjectBean>();
        for (int i = 0; i < response.getRespObject().size(); i++) {
            SponsorData.RespObjectBean bean = new SponsorData.RespObjectBean();
            String name = response.getRespObject().get(i).getOrganizerName();
            bean.setOrganizerName(name);
            int id = response.getRespObject().get(i).getOrganizerId();
            bean.setOrganizerId(id);
            data.add(bean);
        }
        setAdapter();
    }

    @Override
    public void getSponsorFailed(String errInfo) {

    }

    private void setListener() {
        sponsorBack.setOnClickListener(this);
        sponsorConfirm.setOnClickListener(this);
        sponsorSwipe.setOnRefreshListener(this);
    }

    private void initView() {
        lv_sponsor = (ListView) findViewById(android.R.id.list);
        sponsorBack = (AutoLinearLayout)findViewById(R.id.ll_event_sponsor_back);
        sponsorConfirm = (AutoLinearLayout)findViewById(R.id.ll_event_sponser_confirm);
        sponsorSwipe = (SwipeRefreshLayout)findViewById(R.id.sponsorSwipe);
        View footer = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.release_event_sponsor_footer,null,false);
        lv_sponsor.addFooterView(footer);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReleaseEventSponsor.this,ReleaseEventAddSponsor.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_event_sponser_confirm:
                saveValue();

                break;
            case R.id.ll_event_sponsor_back:
                AppManager.getAppManager().finishActivity();
                break;
        }
    }

    @Override
    public void onRefresh() {
        getData();
        sponsorSwipe.setRefreshing(false);
    }
}
