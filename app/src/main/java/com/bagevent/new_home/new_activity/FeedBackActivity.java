package com.bagevent.new_home.new_activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.common.Constants;
import com.bagevent.dialog.LDialog;
import com.bagevent.dialog.ViewHelper;
import com.bagevent.dialog.listener.OnViewClick;
import com.bagevent.eventbus.MessageAction;
import com.bagevent.eventbus.MessageEvent;
import com.bagevent.new_home.new_activity.adapter.PicAdapter;
import com.bagevent.util.AppManager;
import com.bagevent.util.DxPxUtils;
import com.bagevent.util.OkHttpUtil;
import com.bagevent.util.TosUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

public class FeedBackActivity extends BaseActivity {

    String path = "";

    PicAdapter adapter;
    List<String> pathList;
    ArrayList<String> uploadPicData;

    String picStr = "";
    String data="";

    private LDialog lDialog;

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_pic_num)
    TextView tv_pic_num;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.btn_commit_feedback)
    Button btn_commit_feedback;

    @OnClick({R.id.ll_add_pic, R.id.btn_commit_feedback})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_add_pic:
                if (pathList.size() == 4) {
                    Toast.makeText(this, R.string.select_four_pic, Toast.LENGTH_SHORT).show();
                } else {
                    showDialog();
                }
                break;
            case R.id.btn_commit_feedback:
                String etContext=et.getText().toString().trim();
                if (TextUtils.isEmpty(etContext)){
                    TosUtil.show(getString(R.string.feedback_not_null));
                    return;
                }else {
                    if (pathList.size()>0){
                        uploadPicToServer(pathList);
                    }else {
                        uploadSuggestToServer();
                    }
                    showLoading();
                }

        }
    }


    @OnClick(R.id.iv_back)
    public void back() {
        AppManager.getAppManager().finishActivity();
    }


    private void showDialog() {
        new LDialog.Builder(getFragmentManager())
                .setLayoutId(R.layout.dialog_choose_pic)
                .setGravity(Gravity.BOTTOM)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setDialogAnimationRes(R.style.BottomDialogAnimation)
                .setCancelableOutside(true)
                .addOnClick(R.id.tv_from_album, R.id.tv_take_photo, R.id.tv_cancel)
                .setOnViewClick(new OnViewClick() {
                    @Override
                    public void viewClick(ViewHelper helper, View view, LDialog dialog) {
                        switch (view.getId()) {
                            case R.id.tv_from_album:
                                selectImageFromAlbum();
                                dialog.dismiss();
                                break;
                            case R.id.tv_take_photo:
                                selectImageFromCamera();
                                dialog.dismiss();
                                break;
                            case R.id.tv_cancel:
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .build()
                .show();
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        uploadPicData=new ArrayList<>();
        initEditText();
        initRV();
        initPermission();

        EventBus.getDefault().register(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent event) {
        if (event.getAction() == MessageAction.ACTION_SELECT_PIC) {
            tv_pic_num.setText((int) event.getValue() + "/4");
        }
    }

    private void initEditText() {
        btn_commit_feedback.setText(R.string.btn_commit_feedback);
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
                    Toast.makeText(FeedBackActivity.this, R.string.most_three_hundred, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initRV() {
        pathList = new ArrayList<>();
        adapter = new PicAdapter(pathList, FeedBackActivity.this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        rv.setHasFixedSize(true);
    }

    private void selectImageFromAlbum() {
        Intent picIntent = new Intent(Intent.ACTION_PICK, null);
        picIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(picIntent, 2);
    }

    private void selectImageFromCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 1);
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
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    path = cursor.getString(column_index);
                    showPicDetail();
                    pathList.add(path);
                    tv_pic_num.setText(pathList.size() + "/4");
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

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if (response.contains("\"retStatus\":200")) {
                                    JsonObject asJsonObject = new JsonParser().parse(response).getAsJsonObject();
                                    JsonObject respData = asJsonObject.getAsJsonObject("respData");
                                    data = respData.get("data").getAsString();
                                    uploadPicData.add(data);
                                    StringBuilder builder=new StringBuilder();
                                    for (int i = 0; i < uploadPicData.size() ; i++) {
                                        if (builder.length()>0){
                                            builder.append(",");
                                        }
                                        builder.append(uploadPicData.get(i));
                                    }
                                    picStr=builder.toString();
                                    if (uploadPicData.size()==pathList.size()){
                                       uploadSuggestToServer();
                                    }
                                } else {
                                    TosUtil.show(getString(R.string.error_upload));
                                }
                            }
                        });
            }
        }
    }

    private void uploadSuggestToServer() {

        OkHttpUtil.Post(getApplication())
                .url(Constants.NEW_URL + Constants.SUGGESTION_UPLOAD)
                .addParams("content", et.getText().toString().trim())
                .addParams("source", "0")
                .addParams("img_urls", picStr)
                .build()
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
                            AppManager.getAppManager().finishActivity();
                        } else {
                            TosUtil.show(getString(R.string.submit_error));
                        }
                    }
                });
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

    private void showPicDetail() {
        tv_pic_num.setText(pathList.size() + "/4");
        rv.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
