package com.bagevent.new_home.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bagevent.MyFlutterView;
import com.bagevent.BaseFragment;
import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.home.MainActivity;
import com.bagevent.login.LoginActivity;
import com.bagevent.login.loginview.LoginViewInterface;
import com.bagevent.login.model.UserInfo;
import com.bagevent.login.presenter.LoginPresenter;
import com.bagevent.new_home.data.UserInfoData;
import com.bagevent.new_home.new_activity.ExhibitionActivity;
import com.bagevent.new_home.new_activity.FeedBackActivity;
import com.bagevent.new_home.new_activity.ReleaseEvent;
import com.bagevent.new_home.new_activity.SetSenderAddress;
import com.bagevent.new_home.new_activity.UserSetEventIncome;
import com.bagevent.new_home.new_activity.UserSetUserInfo;
import com.bagevent.new_home.new_interface.new_view.GetUserInfoView;
import com.bagevent.new_home.new_interface.presenter.GetUserInfoPresenter;
import com.bagevent.synchro_data.UpdateVersionService;
import com.bagevent.util.AppManager;
import com.bagevent.util.AppUtils;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.OnMultipleClickListener;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TimeUtil;
import com.bagevent.view.CircleTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
//import com.netease.nim.uikit.common.ui.imageview.CircleImageView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zwj on 2016/8/25.
 */
public class UserSetFragment extends BaseFragment implements GetUserInfoView, LoginViewInterface {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    Unbinder unbinder;
    @BindView(R.id.iv_circle_avatar)
    CircleImageView ivCircleAvatar;
    @BindView(R.id.tv_circle_avatar)
    CircleTextView tv_circle_avatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_update_version)
    TextView tv_update_version;
    private View view;
    private String userId = "";
    private String avatar = "";

    private GetUserInfoPresenter getUserInfoPresenter;
    private LoginPresenter loginPresenter;
    private NormalAlertDialog dialog;
    private NormalAlertDialog exitDialog;


    private static final int EXIT_ACCOUNT = 1;
    private static final int UPDATE_VERSION = 2;
    private int id = -1;

    /**
     * 版本更新
     */
    private AutoRelativeLayout rl_update_version;
    private NormalAlertDialog forceUpdateDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_set, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userId = SharedPreferencesUtil.get(getActivity(), "userId", "");
        avatar = SharedPreferencesUtil.get(getActivity(), "avatar", "");
        initView();
        userAvatar();
        exitAccount();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals("fromUserInfo")) {
            avatar = SharedPreferencesUtil.get(getActivity(), "avatar", "");
            RequestOptions options=new RequestOptions()
                    .error(R.mipmap.icon);
            Glide.with(this).load(Constants.imgsURL + avatar).apply(options).into(ivCircleAvatar);
            userAvatar();
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    private void showRequestDialog() {
        id = UPDATE_VERSION;
        showDialog(getString(R.string.download));
    }

    private void startLoad() {
        String url = SharedPreferencesUtil.get(getActivity(), "apkUrl", "");
        if (!TextUtils.isEmpty(url)) {
            Toast toast = Toast.makeText(getActivity(), R.string.new_version, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent intent = new Intent(getActivity(), UpdateVersionService.class);
            intent.putExtra("url", url);
            intent.putExtra("state", Constants.NOT_FORCE_UPDATE);
            intent.putExtra("appName", "百格活动");
            intent.putExtra("description", "正在下载");
            getActivity().startService(intent);
        } else {
            Toast toast = Toast.makeText(getActivity(), R.string.up_to_data_version, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }
    }

    private void initView() {
        tvTitle.setText(R.string.setting);
        tvVersion.setText(AppUtils.getVersioncode(getContext()));

        tv_update_version.setOnClickListener(new OnMultipleClickListener(3) {
            @Override
            public void onMultipleClick(View v) {
                if (NetUtil.isConnected(getContext())) {
                    uploadDbToServer();
                }

            }
        });
    }

    private void userAvatar() {
        if (NetUtil.isConnected(getActivity())) {
            loginPresenter = new LoginPresenter(this);
            loginPresenter.autoLogin(getContext(),SharedPreferencesUtil.get(getActivity(), "autoLoginToken", ""));
            getUserInfoPresenter = new GetUserInfoPresenter(this);
            getUserInfoPresenter.getUserInfo();
        } else {
            Toast toast = Toast.makeText(getActivity(), R.string.net_err, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void userInfo() {
        if (NetUtil.isConnected(getActivity())) {
            getUserInfoPresenter = new GetUserInfoPresenter(this);
            getUserInfoPresenter.getUserInfo();
        } else {
            Toast toast = Toast.makeText(getActivity(), R.string.net_err, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void exitAccount() {
        exitDialog = new NormalAlertDialog.Builder(getActivity())
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
                        deleteAlias();
                        SharedPreferencesUtil.put(getActivity(), "userId", "");
                        SharedPreferencesUtil.put(getActivity(), "email", "");
                        SharedPreferencesUtil.put(getActivity(), "cellphone", "");
                        SharedPreferencesUtil.put(getActivity(), "userName", "");
                        SharedPreferencesUtil.put(getActivity(), "avatar", "");
                        SharedPreferencesUtil.put(getActivity(), "source", "");
                        SharedPreferencesUtil.put(getActivity(), "token", "");
                        SharedPreferencesUtil.put(getActivity(), "state", "");
                        SharedPreferencesUtil.put(getActivity(), "autoLoginToken", "");
                        SharedPreferencesUtil.put(getActivity(), "autoLoginExpireTime", "");
                        SharedPreferencesUtil.put(getActivity(),"select_event_id","");
                        SharedPreferencesUtil.put(getActivity(),"accid","");
                        SharedPreferencesUtil.put(getActivity(),"yunxin_token","");
                        SharedPreferencesUtil.put(getActivity(),"loginType","");
                        NIMClient.getService(AuthService.class).logout();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        exitDialog.dismiss();
                        AppManager.getAppManager().finishAllActivity();
                    }
                })
                .build();
    }

    /**
     * 清除极光设置的别名
     */
    private void deleteAlias() {
        final String alias = SharedPreferencesUtil.get(getContext(), "userId", "");
        if (TextUtils.isEmpty(alias)) {
            return;
        }

        SharedPreferencesUtil.put(getContext(), "alias" + alias, "");
        JPushInterface.deleteAlias(getContext(), 0);
    }


    private void showDialog(String content) {
        dialog = new NormalAlertDialog.Builder(getActivity())
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true)
                .setTitleText(getString(R.string.warm_remind))
                .setContentText(content)
                .setTitleTextColor(R.color.black_light)
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialog.dismiss();
                        if (id == EXIT_ACCOUNT) {// 1---退出账户,2---下载新版本
                            SharedPreferencesUtil.clear(getActivity());
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            getActivity().finish();
                        } else if (id == UPDATE_VERSION) {
                            startLoad();
                        }

                    }
                })
                .build();
        dialog.show();
    }


    @Override
    public Context mContext() {
        return getActivity();
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public void getUserInfoSuccess(UserInfoData response) {
//        String name = response.getRespObject().getUserName();
//        String email = SharedPreferencesUtil.get(getContext(), "email", "");
//        String cellphone = SharedPreferencesUtil.get(getActivity(), "cellphone", "");
//        if (!TextUtils.isEmpty(name)) {
//            tvName.setText(name);
//        } else if (!TextUtils.isEmpty(response.getRespObject().getNickName())) {
//            tvName.setText(response.getRespObject().getNickName());
//        } else if (!TextUtils.isEmpty(email)) {
//            tvName.setText(email);
//        } else if (!TextUtils.isEmpty(cellphone)) {
//            tvName.setText(cellphone);
//        }

        String name = "";
        UserInfoData.RespObjectBean respObject = response.getRespObject();
        if (!TextUtils.isEmpty(respObject.getNickName())) {
            name = respObject.getNickName();
        } else if (!TextUtils.isEmpty(respObject.getUserName())) {
            name = respObject.getUserName();
        } else if (!TextUtils.isEmpty(respObject.getEmail())) {
            name = respObject.getEmail();
        } else if (!TextUtils.isEmpty(respObject.getCellphone())) {
            name = respObject.getCellphone();
        }

        if (!TextUtils.isEmpty(avatar)) {
            if (avatar.contains("wx")) {
                if (!TextUtils.isEmpty(name)) {
                    if (name.length() > 0) {
                        tv_circle_avatar.setVisibility(View.VISIBLE);
                        ivCircleAvatar.setVisibility(View.GONE);
                        tv_circle_avatar.setText(name.substring(0, 1));
                    } else {
                        tv_circle_avatar.setText(name);
                    }
                }
            } else {
                tv_circle_avatar.setVisibility(View.GONE);
                ivCircleAvatar.setVisibility(View.VISIBLE);
                RequestOptions options=new RequestOptions()
                        .error(R.mipmap.icon);
                Glide.with(this).load(Constants.imgsURL + avatar).apply(options).into(ivCircleAvatar);
            }
        } else {
            tv_circle_avatar.setVisibility(View.VISIBLE);
            ivCircleAvatar.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(name)) {
                if (name.length() > 0) {
                    tv_circle_avatar.setVisibility(View.VISIBLE);
                    ivCircleAvatar.setVisibility(View.GONE);
                    tv_circle_avatar.setText(name.substring(0, 1));
                } else {
                    tv_circle_avatar.setText(name);
                }
            }
        }
        tvName.setText(name);
    }

    @Override
    public void getUserInfoFailed(String errInfo) {

    }


    @Override
    public Context getContext() {

        if (super.getContext() != null) {
            return super.getContext();
        }
        return AppUtils.getContext();
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearPassword() {

    }

    @Override
    public void toMainActivity(UserInfo userInfo) {
        avatar = userInfo.getReturnObj().getAvatar();
        userInfo();
    }

    /**
     * 把数据库上传到服务器
     */
    private void uploadDbToServer() {
        String fileName = "bagEvent_Android_" + TimeUtil.getCurrentTime("YYYY年MM月dd日_HH时mm分") + ".db";
        File dbFile = new File("/data/data/com.bagevent/databases/AppDatabase.db");
        if (dbFile == null || !dbFile.exists()) {
            return;
        }

        OkHttpUtil.Post(getActivity())
                .url(Constants.NEW_URL + Constants.COMMON_FILEUPLOAD)
                .addFile("bagEvent", fileName, dbFile.getAbsoluteFile())
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

                    }
                });
    }

    @Override
    public void showFailedErr(String errInfo) {
        userInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        OkHttpUtils.getInstance().cancelTag("GetUser");
        OkHttpUtils.getInstance().cancelTag("autoLogin");
    }

    @OnClick({R.id.rl_account_ownevent, R.id.rl_account_income, R.id.rl_account_update_version,
            R.id.ll_user_info, R.id.rl_account_address, R.id.tv_quit, R.id.rl_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_user_info: //个人信息
                Intent intentUserInfo = new Intent(getActivity(), UserSetUserInfo.class);
                startActivity(intentUserInfo);
                break;
            case R.id.rl_account_income: //活动收入
                Intent intent = new Intent(getActivity(), UserSetEventIncome.class);
                startActivity(intent);
                break;
            case R.id.rl_account_ownevent: //我的活动
                Intent intentEvent = new Intent(getActivity(), MainActivity.class);
                startActivity(intentEvent);
//                Intent intentExhibition = new Intent(getActivity(),ExhibitionActivity.class);
//                intentExhibition.putExtra("userId",userId);
//                startActivity(intentExhibition);
                break;
            case R.id.rl_account_address:
                Intent intent1 = new Intent(getActivity(), SetSenderAddress.class);
                startActivity(intent1);
                break;
            case R.id.rl_account_update_version:

                String localVersion = AppUtils.getVersioncode(getActivity());
                String onLine = SharedPreferencesUtil.get(getActivity(), "versionCode", "");
                if (localVersion != null && onLine != null && !localVersion.equals(onLine)) {
                    if (NetUtil.isConnected(getActivity())) {
                        if (NetUtil.isWifi(getActivity())) {//无线环境下方能直接下载
                            startLoad();
                        } else {
                            showRequestDialog();
                        }
                    } else {
                        Toast toast = Toast.makeText(getActivity(), R.string.check_your_net, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getActivity(), R.string.up_to_data_version, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            case R.id.rl_feedback:
                Intent intent2 = new Intent(getActivity(), FeedBackActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_quit:
                exitDialog.show();
                break;
        }
    }
}
