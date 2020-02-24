package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.ReportComment;
import com.bagevent.common.Constants;
import com.bagevent.db.EventList;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.HomePage;
import com.bagevent.new_home.data.ChatData;
import com.bagevent.new_home.fragment.DynamicFragment;
import com.bagevent.new_home.new_interface.new_view.SingleChatListView;
import com.bagevent.new_home.new_interface.presenter.SingleChatListPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.PageTool;
import com.bagevent.util.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ZWJ on 2018/1/3 0003.
 */

public class JPushActionActivity extends BaseActivity implements SingleChatListView {

    private String contactIds;
    private String userId;
    private SingleChatListPresenter singleChatListPresenter;

    private String eventId;

    private ArrayList<ReportComment> reportComments;
    private int reportListCount;
    @Override
    protected void initUI(Bundle savedInstanceState) {
        getIntentValue();
    }



//    @Override
//    public boolean isSupportSwipeBack() {
//        return false;
//    }



    private void getSingleChat() {
        //   Log.e("fdsf",contactIds+" f");
        if (!TextUtils.isEmpty(contactIds)) {
            if (NetUtil.isConnected(this)) {
                singleChatListPresenter = new SingleChatListPresenter(this);
                singleChatListPresenter.singleChatList();
            } else {
                AppManager.getAppManager().finishActivity();
            }
        } else {

            getReportList();
        }
    }
    private void getReportList() {
        eventId=SharedPreferencesUtil.get(this,"eventListId","");
        OkHttpUtil.Post(getApplication())
                .url(Constants.NEW_URL + Constants.FIND_REPORT_COMMENT)
                .addParams("eventId", eventId)
                .build()
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            parserReportList(response);
                        }
                    }
                });
    }

    private void parserReportList(String response) {
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonObject respObject = jsonObject.getAsJsonObject("respObject");
        JsonArray reportCommentList = respObject.getAsJsonArray("reportCommentList");
        reportComments = new ArrayList<>();
        reportListCount = reportCommentList.size();
        if (reportCommentList.size() != 0) {
            reportComments.clear();
            Gson gson = new Gson();
            for (JsonElement element : reportCommentList) {
                ReportComment reportComment = gson.fromJson(element, new TypeToken<ReportComment>() {
                }.getType());
                reportComments.add(reportComment);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt("reportListCount", reportListCount);
        bundle.putParcelableArrayList("reportComments", reportComments);
        PageTool.go(this, ReportListActivity.class, bundle);
        EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
        AppManager.getAppManager().finishActivity();
    }


    private void getIntentValue() {
        userId = SharedPreferencesUtil.get(this, "userId", "");
        Bundle bundle = getIntent().getExtras();
        if (!TextUtils.isEmpty(userId)) {
            if (bundle != null) {
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    contactIds = extraJson.optString("contactId");
                    getSingleChat();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
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
    public int contactId() {
        return Integer.parseInt(contactIds);
    }

    @Override
    public void showSingleChatSuccess(ChatData response) {
        if (response.getRespObject().size() == 1) {
            boolean isSys = response.getRespObject().get(0).getInteractPeople().isSys();
            if (isSys) {
                Intent intent = new Intent(this, ChattingSysActivity.class);
                intent.putExtra("token", response.getRespObject().get(0).getInteractPeople().getToken());
                intent.putExtra("contactId", response.getRespObject().get(0).getContactId());
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ChattingActivity.class);
                intent.putExtra("sendToken", response.getRespObject().get(0).getInteractPeople().getToken());
                intent.putExtra("eventId", response.getRespObject().get(0).getInteractPeople().getEventId());
                intent.putExtra("chatName", response.getRespObject().get(0).getInteractPeople().getShowName());
                intent.putExtra("avatar", response.getRespObject().get(0).getInteractPeople().getAvatar());
                intent.putExtra("peopleId", response.getRespObject().get(0).getInteractPeople().getPeopleId());
                intent.putExtra("contactId", response.getRespObject().get(0).getContactId());
                startActivity(intent);
            }
            AppManager.getAppManager().finishActivity(this);
        } else {
            AppManager.getAppManager().finishActivity(this);
        }
    }

    @Override
    public void showSingleChatFailed(String errInfo) {
        AppManager.getAppManager().finishActivity();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().unregister(this);
//        }
//    }
}
