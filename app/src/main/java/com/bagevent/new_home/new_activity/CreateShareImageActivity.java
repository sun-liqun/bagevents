package com.bagevent.new_home.new_activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.detail.AuditAttendeeActivity;
import com.bagevent.activity_manager.detail.AuditOrder;
import com.bagevent.activity_manager.manager_fragment.data.ShareInfoData;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.GetShareInfoPresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetShareInfoView;
import com.bagevent.common.Constants;
import com.bagevent.util.AppManager;
import com.bagevent.util.AppUtils;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import name.gudong.loading.LoadingView;
import okhttp3.Call;
import okhttp3.Response;

public class CreateShareImageActivity extends BaseActivity implements GetShareInfoView {

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
    @BindView(R.id.iv_ShareImg)
    ImageView ivShareImage;

    private String userId;
    private int eventId;
    private int exhibitorId;

    private GetShareInfoPresenter getShareInfoPresenter;

    //分享
    private String shareImgUrl = "";
    private String shareTitle = "";
    private String shareContent = "";
    private String shareUrl = "";

    private String img = "";
    private NormalAlertDialog backDialog;

    @OnClick(R.id.iv_ShareImg)
    public void onClick() {
        if (ivShareImage.getDrawable() == null) {
            return;
        }
        showBackDialog();
    }

    private void showBackDialog() {
        backDialog = new NormalAlertDialog.Builder(this)
                .setHeight(0.23f)
                .setWidth(0.65f)
                .setTitleVisible(false)
                .setContentText(getString(R.string.weather_save))
                .setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setRightButtonTextColor(R.color.black)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        backDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
//                        Bitmap bitmap=ivShareImage.getDrawingCache();
//                        Bitmap bitmap = Bitmap.createBitmap(ivShareImage.getDrawingCache());
//                        bitmap.setConfig(Bitmap.Config.ARGB_8888);
                        Drawable drawable = ivShareImage.getDrawable();
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                        Bitmap bitmap = bitmapDrawable.getBitmap();
//                        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.bg);

                        saveImageToGallery(CreateShareImageActivity.this, bitmap);

                        ivShareImage.setDrawingCacheEnabled(false);
                        backDialog.dismiss();
                    }
                })
                .build();
        backDialog.show();
    }


    private void saveImageToGallery(Context context, Bitmap bitmap) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
//        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() ;
//        String galleryPath = Environment.getExternalStorageDirectory().getAbsolutePath()
//                + File.separator + "bagevent"+File.separator;

        File appDir = new File("/mnt/sdcard/bagevent/");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        FileOutputStream fos;
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(),
                    fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //最后通知图库更新
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        //扫描单个文件
        intent.setData(Uri.fromFile(file));
        //给图片的绝对路径
        context.sendBroadcast(intent);
        Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_share_image);
        ButterKnife.bind(this);
        initView();
        getIntentValue();
        isLoading();
        initPermission();
        getShareImage();
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        Glide.with(this).load(R.drawable.share).into(ivRight);
        ivRight2.setVisibility(View.GONE);
        tvTitle.setSelected(true);
        tvTitle.setText(R.string.poster);
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", 0);
        exhibitorId = intent.getIntExtra("exhibitorId", 0);
        userId = SharedPreferencesUtil.get(this, "userId", "");
    }

    private void getShareImage() {
        if (NetUtil.isConnected(getBaseContext())) {

            OkHttpUtil.Get(getBaseContext())
                    .url(Constants.NEW_URL + Constants.CREATESHAREIMAGE
                            + "?eventId=" + eventId + "&exhibitorId=" + exhibitorId + "&shareType=" + "5")
                    .build()
                    .execute(new Callback<String>() {
                        @Override
                        public String parseNetworkResponse(Response response, int id) throws Exception {
                            return response.body().string();
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            TosUtil.show(getString(R.string.send_error));
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (response.contains("\"retStatus\":200")) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    img = "https://file.bagevent.com" + jsonObject.getJSONObject("respObject").getString("resourceName");
//                                    Glide.with(getApplication())
//                                            .load("https://file.bagevent.com" + img)
//                                            .listener(new RequestListener<String, GlideDrawable>() {
//                                                @Override
//                                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                                    return false;
//                                                }
//
//                                                @Override
//                                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                                    ((GlideBitmapDrawable) resource).getBitmap();
//
//                                                    return false;
//                                                }
//                                            }).into(ivShareImage);
                                    ivShareImage.setDrawingCacheEnabled(true);

                                    Glide.with(CreateShareImageActivity.this).asBitmap().apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).load(img)
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                                    loadingFinished();
                                                    ivShareImage.setImageBitmap(resource);
                                                }
                                            });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                TosUtil.show(getString(R.string.send_error));
                            }
                        }
                    });
        } else {
            Toast.makeText(this, R.string.check_your_net, Toast.LENGTH_SHORT).show();
        }

    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
//        loading.start();
    }

    private void loadingFinished() {
        llLoading.setVisibility(View.GONE);
//        loading.stop();
    }

    @OnClick({R.id.ll_title_back, R.id.ll_right_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_right_click:
                showShare(getBaseContext(), null, true);
                break;
        }
    }

    @Override
    public Context mContext() {
        if (super.getApplicationContext() != null) {
            return super.getApplicationContext();
        } else {
            return AppUtils.getContext();
        }
    }

    @Override
    public String getEventId() {
        return eventId + "";
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public void showShareInfo(ShareInfoData response) {
        ShareInfoData.RespObjectBean share = response.getRespObject();
        shareContent = share.getContent();
        shareImgUrl = share.getImg();
        shareTitle = share.getTitle();
        shareUrl = share.getEventUrl();
        showShare(getBaseContext(), null, true);
    }

    /**
     * 分享
     */
    private void showShare(Context context, String platformToShare, boolean showContentEdit) {
        final OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        //    oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setText("");
                    paramsToShare.setShareType(Platform.SHARE_IMAGE);
                    paramsToShare.setImageUrl(img);
                    paramsToShare.setVenueName(getString(R.string.app_name));
                } else {
                    paramsToShare.setText("");
                    paramsToShare.setShareType(Platform.SHARE_IMAGE);
                    paramsToShare.setImageUrl(img);
                    paramsToShare.setTitle("");
                    paramsToShare.setUrl("https://www.bagevent.com/"); //微信不绕过审核分享链接
                    paramsToShare.setSite(getString(R.string.app_name));  //QZone分享完之后返回应用时提示框上显示的名称
                    paramsToShare.setSiteUrl("https://www.bagevent.com/");//QZone分享参数
                    paramsToShare.setVenueName(getString(R.string.app_name));
                }
            }
        });

        oks.addHiddenPlatform("ShortMessage");
        oks.addHiddenPlatform("Email");
        oks.addHiddenPlatform("WechatFavorite");
        oks.addHiddenPlatform("QZone");
        // 启动分享
        oks.show(context);
    }

    @Override
    public void showShareInfoErr(String errInfo) {

    }
}
