package com.bagevent.new_home.new_activity;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.detail.CollectionBarcode;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Common;
import com.bagevent.common.Constants;
import com.bagevent.db.EventList;
import com.bagevent.new_home.ReleaseExDynamicActivity;
import com.bagevent.new_home.adapter.ExhibitionDynamicAdapter;
import com.bagevent.new_home.data.ExhibitorDynamicData;
import com.bagevent.util.AppUtils;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.gudong.loading.LoadingView;
import okhttp3.Call;
import okhttp3.Response;

import static com.bagevent.util.AppUtils.getContext;


public class ExhibitionActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.ll_view)
    AutoLinearLayout ll_view;
    @BindView(R.id.tv_exhibition_company)
    TextView tv_company;
    @BindView(R.id.tv_boothHall)
    TextView tv_boothHall;
    @BindView(R.id.tv_boothNo)
    TextView tv_boothNo;
    @BindView(R.id.tv_viewCount)
    TextView tv_viewCount;
    @BindView(R.id.tv_favouriteNum)
    TextView tv_favouriteNum;
    @BindView(R.id.tv_rank)
    TextView tv_rank;
    @BindView(R.id.view_divide)
    View view_divide;
    @BindView(R.id.rl_haveAttendee)
    RelativeLayout rl_haveAttendee;
    @BindView(R.id.tv_attendeeCount)
    TextView tv_attendeeCount;
    @BindView(R.id.tv_toCollectionPage)
    TextView tv_toCollectionPage;
    @BindView(R.id.tv_my_invide)
    TextView tv_my_invide;
    @BindView(R.id.company_logo)
    ImageView iv_company_logo;
    @BindView(R.id.btn_share)
    Button btn_share;

    @BindView(R.id.refresh_exhibition)
    SwipeRefreshLayout refreshExhibition;
    @BindView(R.id.rv_dynamic)
    RecyclerView rvDynamic;
    @BindView(R.id.ll_loading)
    AutoLinearLayout llLoading;
    @BindView(R.id.loading)
    LoadingView loading;
    @BindView(R.id.iv_barcode_checkin)
    ImageView iv_barcode_checkin;
    @BindView(R.id.iv_release)
    ImageView iv_release;
    //    @BindView(R.id.fl_container)
//    FrameLayout fl_container;
    private ExhibitionDynamicAdapter dynamicAdapter;
    private final byte ACTION_LAOD = 0;
    private final byte ACTION_REFRESH = 1;

    private NoDataViewBind noDataViewBind;
    @SuppressWarnings("unchecked")
    private ArrayList<ExhibitorDynamicData.DynamicList> arrayList = new ArrayList();

    Uri outputUri;
    Uri uri;
    File mFile;

    private int eventId;
    private String company;
    private String boothNo;
    private String boothHall;
    private String logo;
    private int exhibitorId;
    private int viewCount;
    private int favoriteCount;
    private int rank;
    private int attendeeCount;
    private int collectPointCount;
    private String collectionName;
    private int topCount;
    private int export;
    private int collectionId;
    private int sumNumber;
    private int sendNumber;
    private int collectPoint;
    private int showNum;
    private Dialog dialog;
    private String path = "";

    @OnClick({R.id.ll_left_back, R.id.tv_toCollectionPage, R.id.btn_share, R.id.iv_release,
            R.id.iv_barcode_checkin, R.id.company_logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_share:
                Intent intent = new Intent(this, CreateShareImageActivity.class);
                intent.putExtra("eventId", eventId);
                intent.putExtra("exhibitorId", exhibitorId);
                startActivity(intent);
                break;
            case R.id.tv_toCollectionPage:
                Intent intent1 = new Intent(this, CollectionAttendeeActivity.class);
                intent1.putExtra("eventId", eventId);
                intent1.putExtra("exhibitorId", exhibitorId);
                startActivity(intent1);
                break;
            case R.id.iv_release:
                Intent intentToRelease = new Intent(ExhibitionActivity.this, ReleaseExDynamicActivity.class);
                intentToRelease.putExtra("topCount", sumNumber - sendNumber);
                intentToRelease.putExtra("eventId", eventId);
                intentToRelease.putExtra("exhibitionId", exhibitorId);
                startActivity(intentToRelease);
                break;
            case R.id.iv_barcode_checkin:
                Intent toBarcodeCollect = new Intent(this, CollectionBarcode.class);
                toBarcodeCollect.putExtra("collectionId", collectionId);
                toBarcodeCollect.putExtra("eventId", eventId);
                toBarcodeCollect.putExtra("export", export);
                toBarcodeCollect.putExtra("exhibitorId", exhibitorId);
//                toBarcodeCollect.putExtra("exhibitorAttendeeId",);
                toBarcodeCollect.putExtra("isExhibitor", true);
//                toBarcodeCollect.putExtra("collectionName",collectionName);
                toBarcodeCollect.putExtra(Common.COLLECT_LOGIN_TYPE, Common.COLLECT_LOGIN_TYPE_MANAGER);
                startActivity(toBarcodeCollect);
                break;
            case R.id.company_logo:
                PackageManager pm = getPackageManager();
                boolean permission = (PackageManager.PERMISSION_GRANTED ==
                        pm.checkPermission("android.permission.CAMERA", "com.bagevent"));
                if (permission) {
                    showDialog();
                } else {
                    TosUtil.show("请打开拍照权限");
                }

                break;
        }
    }

    private void showDialog() {
        dialog = new Dialog(this, R.style.bottomDialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_pic, null);

        TextView tv_from_album = view.findViewById(R.id.tv_from_album);
        TextView tv_take_photo = view.findViewById(R.id.tv_take_photo);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);

        tv_from_album.setOnClickListener(this);
        tv_take_photo.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = WindowManager.LayoutParams.MATCH_PARENT;
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);
        dialog.show();
    }

    private void selectImageFromAlbum() {
        Intent picIntent = new Intent(Intent.ACTION_PICK, null);
        picIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(picIntent, 2);
    }

    private void selectImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            File file = new File("/mnt/sdcard/Test/");
            if (!file.exists()) {
                file.mkdir();
            }
            mFile = new File(file, System.currentTimeMillis() + ".jpg");
            path = mFile.getAbsolutePath();
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(this, "com.bagevent.fileprovider", mFile);
            } else {
                uri = Uri.fromFile(mFile);
            }
            try {// 尽可能调用系统相机
                String cameraPackageName = getCameraPhoneAppInfos(ExhibitionActivity.this);
                if (cameraPackageName == null) {
                    cameraPackageName = "com.android.camera";
                }
                final Intent intent_camera = this.getPackageManager()
                        .getLaunchIntentForPackage(cameraPackageName);
                if (intent_camera != null) {
                    intent.setPackage(cameraPackageName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(this, R.string.confirm_sd, Toast.LENGTH_SHORT).show();
        }
    }

    public String getCameraPhoneAppInfos(Activity context) {
        try {
            String strCamera = "";
            List<PackageInfo> packages = context.getPackageManager()
                    .getInstalledPackages(0);
            for (int i = 0; i < packages.size(); i++) {
                try {
                    PackageInfo packageInfo = packages.get(i);
                    String strLabel = packageInfo.applicationInfo.loadLabel(
                            context.getPackageManager()).toString();
                    // 一般手机系统中拍照软件的名字
                    if ("相机,照相机,照相,拍照,摄像,Camera,camera".contains(strLabel)) {
                        strCamera = packageInfo.packageName;
                        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (strCamera != null) {
                return strCamera;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_exhibition);
        ButterKnife.bind(this);
        isLoading();
        getIntentValue();
        setListener();
        getExhibitorData();
        initPermission();
        EventBus.getDefault().register(this);
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    private void getExhibitorData() {
        if (!NetUtil.isConnected(this)) {
            TosUtil.show(getString(R.string.check_your_net));
            return;
        }
        getExhibitorInfo();
        getDynamic(ACTION_LAOD);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent event) {
        if (event.mMsg.equals("refresh_exbition_dynamic")) {
            getDynamic(ACTION_REFRESH);
        }
    }

    private void setListener() {
        refreshExhibition.setOnRefreshListener(this);
    }

    private void getDynamic(final byte action) {
        OkHttpUtil.Post(this).url(Constants.NEW_URL + Constants.EXHIBITORCOMMENTLIST)
                .addParams("eventId", eventId + "")
                .addParams("exhibitorId", exhibitorId + "")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showNoData();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadFinish();
                        if (response.contains("\"retStatus\":200")) {
                            parserSuccess(response, action);
                        } else {
                            showNoData();
                        }
                    }
                });
    }


    private void parserSuccess(String response, byte action) {
        switch (action) {
            case ACTION_LAOD:
                parserLoadData(response);
                break;
            case ACTION_REFRESH:
                parserRefreshData(response);
                break;
        }
    }

    private void parserLoadData(String response) {
        ArrayList<ExhibitorDynamicData.DynamicList> dynamicLists = getListData(response);
        initRecyclerView(dynamicLists);
        if (dynamicLists.size() == 0) {
            showNoData();
        }

    }

    private void parserRefreshData(String response) {
        ArrayList<ExhibitorDynamicData.DynamicList> dynamicLists = getListData(response);
        refreshExhibition.setRefreshing(false);
        dynamicAdapter.setNewData(dynamicLists);

    }

    private void initRecyclerView(ArrayList<ExhibitorDynamicData.DynamicList> dynamicLists) {
        dynamicAdapter = new ExhibitionDynamicAdapter(dynamicLists);
        rvDynamic.setAdapter(dynamicAdapter);
        dynamicAdapter.setOnItemClickListener(this);
        rvDynamic.setNestedScrollingEnabled(false);
        rvDynamic.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rvDynamic.setHasFixedSize(true);

    }

    private ArrayList<ExhibitorDynamicData.DynamicList> getListData(String response) {
        ExhibitorDynamicData exhibitionListData = new ExhibitorDynamicData(new JsonParser().parse(response).getAsJsonObject());
        if (exhibitionListData.getRespObject() == null || exhibitionListData.getRespObject().getDynamicLists() == null) {
            return new ArrayList<>();
        } else {
            return exhibitionListData.getRespObject().getDynamicLists();
        }
    }

    private void showNoData() {
        if (dynamicAdapter != null) {
            if (dynamicAdapter.getData() != null) {
                dynamicAdapter.getData().clear();
                dynamicAdapter.notifyDataSetChanged();
            }
        } else {
            dynamicAdapter = new ExhibitionDynamicAdapter(arrayList);
            rvDynamic.setAdapter(dynamicAdapter);
            rvDynamic.setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false));
        }
        noDataViewBind = new NoDataViewBind(LayoutInflater.from(getContext()).inflate(R.layout.layout_no_attendee, null));
        noDataViewBind.tvPageStatus.setVisibility(View.GONE);
        noDataViewBind.ivPageStatus.setVisibility(View.VISIBLE);
        noDataViewBind.ivPageStatus.setImageResource(R.drawable.no_bg);
        dynamicAdapter.setEmptyView(noDataViewBind.itemView);
    }

    private void isLoading() {
        llLoading.setVisibility(View.VISIBLE);
        ll_view.setVisibility(View.GONE);
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", 0);
        exhibitorId = intent.getIntExtra("exhibitorId", 0);
        btn_share.setText(R.string.share_pic);
    }

    private void setExhibitionData() {
        RequestOptions options = new RequestOptions().
                placeholder(R.drawable.img_loading).error(R.drawable.img_failed);
        tv_company.setText(company);
        if (TextUtils.isEmpty(boothHall) && TextUtils.isEmpty(boothNo)) {
            tv_boothHall.setText(R.string.no_booth_hall);
            tv_boothNo.setVisibility(View.GONE);
        } else {
            tv_boothHall.setText(boothHall);
            tv_boothNo.setText(boothNo);
        }
        if (collectPointCount > 0) {
            rl_haveAttendee.setVisibility(View.VISIBLE);
            view_divide.setVisibility(View.VISIBLE);
            tv_attendeeCount.setText(attendeeCount + "");
            iv_barcode_checkin.setVisibility(View.VISIBLE);
        } else {
            tv_my_invide.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(logo)) {
            if (logo.startsWith("//")) {
                logo = "https:" + logo;
            } else if (logo.startsWith("/")) {
//                logo = "https://www.bagevent.com" + logo;
                logo = "https://img.bagevent.com" + logo;
            }
            iv_company_logo.setVisibility(View.VISIBLE);
            if (Util.isOnMainThread()) {
                Glide.with(this).load(logo).apply(options).into(iv_company_logo);
            }
        } else {
            if (Util.isOnMainThread()) {
                Glide.with(this).load(R.drawable.upload).apply(options).into(iv_company_logo);
            }
        }
        tv_viewCount.setText(viewCount + "");
        tv_favouriteNum.setText(favoriteCount + "");
        tv_rank.setText(rank + "");
    }

    private void getExhibitorInfo() {

        OkHttpUtil.Post(getBaseContext()).url(Constants.NEW_URL + Constants.EXHIBITORINFO)
                .addParams("eventId", eventId + "")
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
                        loadFinish();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boothNo = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getString("boothNo");
                            boothHall = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getString("boothHall");
                            favoriteCount = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getInt("favouriteNum");
                            viewCount = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getInt("viewCount");
                            favoriteCount = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getInt("favouriteNum");
                            rank = jsonObject.getJSONObject("respObject").getInt("rank");
                            sumNumber = jsonObject.getJSONObject("respObject").getInt("sumNumber");
                            sendNumber = jsonObject.getJSONObject("respObject").getInt("sendNumber");
                            attendeeCount = jsonObject.getJSONObject("respObject").getInt("collectAttendeeCount");
                            collectPointCount = jsonObject.getJSONObject("respObject").getJSONArray("collectPointList").length();
                            if (collectPointCount > 0) {
                                export = jsonObject.getJSONObject("respObject").getJSONArray("collectPointList").getJSONObject(0).getInt("export");
                                collectionId = jsonObject.getJSONObject("respObject").getJSONArray("collectPointList").getJSONObject(0).getInt("collectPointId");
                                collectionName = jsonObject.getJSONObject("respObject").getJSONArray("collectPointList").getJSONObject(0).getString("collectionName");
                                showNum = jsonObject.getJSONObject("respObject").getJSONArray("collectPointList").getJSONObject(0).getInt("showNum");
                            }
                            company = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getJSONObject("exExhibitorInfoDTO").getString("company");
                            logo = jsonObject.getJSONObject("respObject").getJSONObject("exExhibitorInfo").getJSONObject("exExhibitorInfoDTO").getString("logo");
                            setExhibitionData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void loadFinish() {
        llLoading.setVisibility(View.GONE);
        ll_view.setVisibility(View.VISIBLE);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_take_photo:
                selectImageFromCamera();
                dialog.cancel();
                break;
            case R.id.tv_from_album:
                selectImageFromAlbum();
                dialog.cancel();
                break;
            case R.id.tv_cancel:
                dialog.cancel();
                break;
        }
    }

    @Override
    public void onRefresh() {
        getExhibitorInfo();
        getDynamic(ACTION_REFRESH);
        refreshExhibition.setRefreshing(false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ExhibitorDynamicData.DynamicList dynamicList = dynamicAdapter.getData().get(position);
//        if (dynamicList.getType() != 4) {
//            Intent intent = new Intent(ExhibitionActivity.this, DynamicDetailActivity.class);
//            if (dynamicList.getType() == 2 || dynamicList.getType() == 1 || dynamicList.getType() == 5) {
//                intent.putExtra(KeyUtil.KEY_EVENT_ID, dynamicList.getEventId());
//                intent.putExtra(KeyUtil.KEY_COMMENT_ID, dynamicList.getCommentId());
//                intent.putExtra(KeyUtil.KEY_TYPE,1);
//                intent.putExtra("exhibitorId",exhibitorId);
//            } else if (dynamicList.getType() == 3) {
////                DynamicListData.CommentList parentComment = dynamicList.getParentComment();
////                intent.putExtra(KeyUtil.KEY_EVENT_ID, parentComment.getEventId());
////                intent.putExtra(KeyUtil.KEY_COMMENT_ID, parentComment.getCommentId());
//            }
//            startActivity(intent);
//        }
    }

    public Bitmap getBitmapFormUri(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = getContentResolver().openInputStream(uri);

        //这一段代码是不加载文件到内存中也得到bitmap的真是宽高，主要是设置inJustDecodeBounds为true
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;//不加载到内存
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        input = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return bitmap;//再进行质量压缩
    }

    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    if (getBitmapDegree(path) != 0) {
                        Bitmap bm = null;
                        try {
                            bm = getBitmapFormUri(uri);//这个是拍照得到的的bitmap
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        uploadPicToServer(getPath(rotateBitmapByDegree(bm, getBitmapDegree(path))));
                    } else {
                        Bitmap bm = null;
                        try {
                            bm = getBitmapFormUri(uri);//这个是拍照得到的的bitmap
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        uploadPicToServer(getPath(bm));
                    }
                    break;
                case 2:
                    if (data == null || data.getData() == null) {
                        return;
                    }
                    Uri originalUri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(originalUri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    path = cursor.getString(column_index);
                    uploadPicToServer(path);
                    break;
            }
        }
    }

    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.i("-----------------path：" + path + "degreen" + degree);
        return degree;
    }

    private String getPath(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/logo.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        path = file.getPath();
        return path;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (path != null) {
            outState.putString("EXTRA_RESTORE_PATH", path);
        }
        if (uri != null) {
            outState.putString("EXTRA_RESTORE_URI", uri.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getString("EXTRA_RESTORE_PATH") != null) {
            path = savedInstanceState.getString("EXTRA_RESTORE_PATH");
        }
        if (savedInstanceState.getString("EXTRA_RESTORE_URI") != null) {
            uri = Uri.parse(savedInstanceState.getString("EXTRA_RESTORE_URI"));
        }

    }

    private void uploadPicToServer(String fileName) {
        File file = new File(fileName);
        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.COMMON_PIC_UPLOAD)
                .addParams("rfType", "5")
                .addFile("img", file.getName(), file.getAbsoluteFile())
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        TosUtil.show("图片上传失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            JsonObject asJsonObject = new JsonParser().parse(response).getAsJsonObject();
                            JsonObject respData = asJsonObject.getAsJsonObject("respData");
                            String picData = respData.get("data").getAsString();
                            updateExhibitorLogo(picData);
                        } else {
                            TosUtil.show(getString(R.string.error_upload));
                        }
                    }
                });
        updateExhibitorLogo(fileName);
    }

    private void updateExhibitorLogo(String imgUrl) {
        OkHttpUtil.Post(this)
                .url(Constants.NEW_URL + Constants.UPDATE_EXHIBITOR_LOGO)
                .addParams("eventId", eventId + "")
                .addParams("imgUrl", imgUrl)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        TosUtil.show("logo上传失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("\"retStatus\":200")) {
                            getExhibitorInfo();
                            getDynamic(ACTION_REFRESH);
                        } else {
//                            TosUtil.show("图片上传失败");
                        }
                    }
                });
    }

    class NoDataViewBind {
        @BindView(R.id.iv_page_status)
        ImageView ivPageStatus;
        @BindView(R.id.tv_page_status)
        TextView tvPageStatus;
        @BindView(R.id.ll_page_status)
        AutoLinearLayout llPageStatus;
        View itemView;

        public NoDataViewBind(View view) {
            ButterKnife.bind(this, view);
            itemView = view;
        }

        @OnClick(R.id.ll_page_status)
        public void onClicked() {
//            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (Util.isOnMainThread()) {
            Glide.with(AppUtils.getContext()).pauseRequests();
        }
    }
}
