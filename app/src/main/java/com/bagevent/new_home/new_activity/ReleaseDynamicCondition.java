package com.bagevent.new_home.new_activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.db.EventList;
//import com.bagevent.db.EventList_Table;
import com.bagevent.db.EventList_Table;
import com.bagevent.dialog.LDialog;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.new_activity.adapter.PicAdapter;
import com.bagevent.util.AppManager;
import com.bagevent.util.DxPxUtils;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.TosUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import okhttp3.Call;
import okhttp3.Response;

public class ReleaseDynamicCondition extends BaseActivity implements View.OnClickListener {

    private Dialog dialog;
    private LDialog lDialog;
    private NormalAlertDialog backDialog;

    String path = "";
    String picStr = "";

    String data = "";

    List<String> pathList;
    PicAdapter adapter;

    ArrayList<String> uploadPicData;

    int eventId;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_pic_num)
    TextView tv_pic_num;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.iv_add_pic)
    ImageView iv_add_pic;
    @BindView(R.id.tv_select_activity)
    TextView tv_select_activity;

    @OnClick({R.id.rl_select_activity, R.id.btn_release, R.id.iv_add_pic, R.id.iv_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_select_activity:
                Intent intent = new Intent(ReleaseDynamicCondition.this, SelectActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_add_pic:
                PackageManager pm = getPackageManager();
                boolean permission = (PackageManager.PERMISSION_GRANTED ==
                        pm.checkPermission("android.permission.CAMERA", "com.bagevent"));
                if (permission) {
                    if (pathList.size() == 9) {
                        Toast.makeText(this, R.string.select_nine_pic, Toast.LENGTH_SHORT).show();
                    } else {
                        showDialog();
                    }
                }else {
                    TosUtil.show("请开启相机和相册权限");
                }
                break;
            case R.id.btn_release:
                if (TextUtils.isEmpty(et.getText().toString().trim())) {
                    TosUtil.show(getString(R.string.dynamic_is_not_null));
                    return;
                } else {
                    if (eventId == 0) {
                        TosUtil.show(getString(R.string.please_select_event));
                    } else {
                        if (pathList.size() > 0) {
                            uploadPicToServer(pathList);
                        } else {
                            uploadDynamicToServer();
                        }
                        showLoading();
                    }
                }

                break;
            case R.id.iv_back:
                if (!TextUtils.isEmpty(et.getText().toString().trim()) || eventId > 0 || pathList.size() > 0) {
                    showBackDialog();
                } else {
                    AppManager.getAppManager().finishActivity();
                }
                break;
        }
    }

    private void showBackDialog() {
        backDialog = new NormalAlertDialog.Builder(ReleaseDynamicCondition.this)
                .setHeight(0.23f)
                .setWidth(0.65f)
                .setTitleVisible(false)
                .setContentText(getString(R.string.weather_release))
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
                        backDialog.dismiss();
                        AppManager.getAppManager().finishActivity();
                    }
                })
                .build();
        backDialog.show();
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

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.release_dynamic_condition);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        uploadPicData = new ArrayList<>();
        tv_pic_num.setText("0/9");

        initPermission();
        initEditText();
        initAdapter();
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }


    private void initEditText() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_num.setText(s.length() + "/300");
                if (s.length() == 300) {
                    Toast.makeText(ReleaseDynamicCondition.this, R.string.most_three_hundred, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initAdapter() {
        pathList = new ArrayList<>();
        int spacing=10;
        adapter = new PicAdapter(pathList, ReleaseDynamicCondition.this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rv.setHasFixedSize(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent event) {
        if (event.getAction() == MessageAction.ACTION_SELECT_PIC) {
            tv_pic_num.setText((int) event.getValue() + "/9");
        } else if (event.getAction() == MessageAction.ACTION_SELECT_EVENT_ID) {
            eventId = (int) event.getValue();
            EventList eventList = new Select().from(EventList.class)
                    .where(EventList_Table.eventId.is(eventId)).querySingle();
            tv_select_activity.setText(eventList.eventName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    private void selectImageFromAlbum() {
        Intent picIntent = new Intent(Intent.ACTION_PICK, null);
        picIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(picIntent, 2);
    }

    private void selectImageFromCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        try {// 尽可能调用系统相机
            String cameraPackageName = getCameraPhoneAppInfos(ReleaseDynamicCondition.this);
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
        startActivityForResult(intent, 1);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    saveCameraImage(data);
                    break;
                case 2:
                    if (data == null || data.getData() == null) {
                        return;
                    }
                    Uri originalUri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(originalUri, proj, null, null, null);
//                    Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    path = cursor.getString(column_index);
                    pathList.add(path);
                    showPicDetail();
                    tv_pic_num.setText(pathList.size() + "/9");
                    break;
            }
        }
    }

    private void saveCameraImage(Intent data) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return;
        }
        String name = System.currentTimeMillis() + ".jpg";
        Bitmap bmp = (Bitmap) data.getExtras().get("data");
        FileOutputStream fos = null;
        File file = new File("/mnt/sdcard/Test/");
        file.mkdirs();
        String fileName = "/mnt/sdcard/Test/" + name;
        try {
            fos = new FileOutputStream(fileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pathList.add(fileName);
        showPicDetail();
    }

    private void showPicDetail() {
        tv_pic_num.setText(pathList.size() + "/9");
        rv.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }


    private void uploadPicToServer(final List<String> pathList) {
        if (pathList != null) {
            for (int i = 0; i < pathList.size(); i++) {
                File file = new File(pathList.get(i));
                OkHttpUtil.Post(getApplication())
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
                                TosUtil.show(getString(R.string.error_upload));
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if (response.contains("\"retStatus\":200")) {
                                    JsonObject asJsonObject = new JsonParser().parse(response).getAsJsonObject();
                                    JsonObject respData = asJsonObject.getAsJsonObject("respData");
                                    data = respData.get("data").getAsString();
                                    uploadPicData.add(data);
                                    StringBuilder builder = new StringBuilder();
                                    for (int i = 0; i < uploadPicData.size(); i++) {
                                        if (builder.length() > 0) {
                                            builder.append(",");
                                        }
                                        builder.append(uploadPicData.get(i));
                                    }
                                    picStr = builder.toString();
                                    if (uploadPicData.size() == pathList.size()) {
                                        uploadDynamicToServer();
                                    }
                                } else {
                                    TosUtil.show(getString(R.string.error_upload));
                                }
                            }
                        });
            }

        }
    }

    private void showLoading() {
        if (lDialog == null) {
            lDialog = new LDialog.Builder(getFragmentManager())
                    .setLayoutId(R.layout.view_loading)
                    .setWidth(DxPxUtils.dip2px(this, 150))
                    .setHeight(DxPxUtils.dip2px(this, 150))
                    .build()
                    .show();
        } else {
            lDialog.show();
        }
    }

    private void hideLoading() {
        lDialog.dismiss();
    }

    private void uploadDynamicToServer() {
        OkHttpUtil.Post(getApplication())
                .url(Constants.NEW_URL + Constants.PublishDynamic)
                .addParams("eventId", String.valueOf(eventId))
                .addParams("commentText", et.getText().toString().trim())
                .addParams("images", picStr)
                .build()
                .connTimeOut(5_1000L)
                .readTimeOut(5_1000L)
                .writeTimeOut(5_1000L)
                .execute(new Callback<String>() {

                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoading();
                        TosUtil.show(getString(R.string.send_error));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoading();
                        if (response.contains("\"retStatus\":200")) {
                            EventBus.getDefault().post(new MsgEvent("refresh_dynamic"));
                            AppManager.getAppManager().finishActivity(ReleaseDynamicCondition.this);
                            TosUtil.show(getString(R.string.release_success));
                        } else {
                            TosUtil.show(getString(R.string.send_error));
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String etContext = et.getText().toString().trim();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!TextUtils.isEmpty(etContext) || eventId > 0 || pathList.size() > 0) {
                showBackDialog();
            } else {
                AppManager.getAppManager().finishActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
