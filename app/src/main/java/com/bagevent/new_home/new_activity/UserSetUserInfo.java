package com.bagevent.new_home.new_activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.UpLoadImagePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpLoadImageView;
import com.bagevent.common.Constants;
import com.bagevent.login.LoginActivity;
import com.bagevent.new_home.data.UserInfoData;
import com.bagevent.new_home.new_interface.new_view.GetUserInfoView;
import com.bagevent.new_home.new_interface.new_view.UpdateUserInfoView;
import com.bagevent.new_home.new_interface.presenter.GetUserInfoPresenter;
import com.bagevent.new_home.new_interface.presenter.UpdateUserInfoPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.KeyUtil;
import com.bagevent.util.LogUtil;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.TosUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wevey.selector.dialog.DialogOnClickListener;
import com.wevey.selector.dialog.MDEditDialog;
import com.wevey.selector.dialog.NormalAlertDialog;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zwj on 2016/10/12.
 */
public class UserSetUserInfo extends BaseActivity implements UpdateUserInfoView, GetUserInfoView, UpLoadImageView, View.OnClickListener {
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.ll_title_back)
    AutoLinearLayout llTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_set_name)
    TextView tvSetName;
    @BindView(R.id.tv_set_nickName)
    TextView tvSetNickName;
    @BindView(R.id.tv_set_company)
    TextView tvSetCompany;
    @BindView(R.id.tv_set_position)
    TextView tvSetPosition;
    @BindView(R.id.tv_user_account)
    TextView tvUserAccount;
    private static final int REQUECT_CODE_CAMERA = 2;
    private static final int REQUEST_CODE_CHOOSE = 3;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;

    private Dialog dialog;
    private MDEditDialog modifyDialog;
    private String formName = "";
    private String formValue = "";
    private String userId = "";
    private File upLoadFile ;
    private UpdateUserInfoPresenter presenter;
    private GetUserInfoPresenter getUserInfoPresenter;
    private UpLoadImagePresenter upLoadImagePresenter;
    private NormalAlertDialog exitDialog;
    private String modifyInfo = "";
    Uri uri;
    File mFile;
    private String path = "";

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.user_set_user_info);
        ButterKnife.bind(this);
        userId = SharedPreferencesUtil.get(this, "userId", "");
        initView();
        getInfo();
        exitAccount();
        initPermission();
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().postSticky(new MsgEvent("fromUserInfo"));
        OkHttpUtils.getInstance().cancelTag("getAvatar");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (path!= null) {
            outState.putString("EXTRA_RESTORE_PATH", path);
        }
        if (uri != null) {
            outState.putString("EXTRA_RESTORE_URI", uri.toString());
        }
        LogUtil.i("-------------------------onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.getString("EXTRA_RESTORE_URI")!=null){
            uri = Uri.parse(savedInstanceState.getString("EXTRA_RESTORE_URI"));
        }
        if(savedInstanceState.getString("EXTRA_RESTORE_PATH")!=null){
            path=savedInstanceState.getString("EXTRA_RESTORE_PATH");
        }
        LogUtil.i("-------------------------onRestoreInstanceState");
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
        return degree;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        uploadAvatar(getPath(rotateBitmapByDegree(bm, getBitmapDegree(path))));
                    }else{
                        Bitmap bm = null;
                        try {
                            bm = getBitmapFormUri(uri);//这个是拍照得到的的bitmap
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        uploadAvatar(getPath(bm));
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
                    uploadAvatar(path);
                    break;
            }
        }
    }

    private String getPath(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/avatar.jpg");
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
        path=file.getPath();
        return path;
    }

    private void selectImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
            File file = new File("/mnt/sdcard/Test/");
            if (!file.exists()){
                file.mkdir();
            }
            mFile=new File(file,System.currentTimeMillis()+".jpg");
            path=mFile.getAbsolutePath();
            if (Build.VERSION.SDK_INT>=24){
                uri = FileProvider.getUriForFile(this, "com.bagevent.fileprovider", mFile);
            }else {
                uri= Uri.fromFile(mFile);
            }
            try {// 尽可能调用系统相机
                String cameraPackageName = getCameraPhoneAppInfos(UserSetUserInfo.this);
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
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
            startActivityForResult(intent,1);
        }else {
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

    private void uploadAvatar(String path) {
        upLoadFile = new File(path);
        upLoadImagePresenter = new UpLoadImagePresenter(this);
        upLoadImagePresenter.upLoadImageFile();
    }

    private void exitAccount() {
        exitDialog = new NormalAlertDialog.Builder(this)
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
                        SharedPreferencesUtil.put(UserSetUserInfo.this, "userId", "");
                        SharedPreferencesUtil.put(UserSetUserInfo.this, "email", "");
                        SharedPreferencesUtil.put(UserSetUserInfo.this, "cellphone", "");
                        SharedPreferencesUtil.put(UserSetUserInfo.this, "userName", "");
                        SharedPreferencesUtil.put(UserSetUserInfo.this, "avatar", "");
                        SharedPreferencesUtil.put(UserSetUserInfo.this, "source", "");
                        SharedPreferencesUtil.put(UserSetUserInfo.this, "token", "");
                        SharedPreferencesUtil.put(UserSetUserInfo.this, "state", "");
                        SharedPreferencesUtil.put(UserSetUserInfo.this, "autoLoginToken", "");
                        SharedPreferencesUtil.put(UserSetUserInfo.this, "autoLoginExpireTime", "");
                        SharedPreferencesUtil.put(UserSetUserInfo.this, KeyUtil.KEY_SELECT_EVENT_ID, "");
                        Intent intent = new Intent(UserSetUserInfo.this, LoginActivity.class);
                        startActivity(intent);
                        exitDialog.dismiss();
                        deleteAlias();
                        AppManager.getAppManager().finishAllActivity();
                    }
                })
                .build();
    }

    /**
     * 清除极光设置的别名
     */
    private void deleteAlias() {
        final String alias = SharedPreferencesUtil.get(this, "userId", "");
        if (TextUtils.isEmpty(alias)) {
            return;
        }

        SharedPreferencesUtil.put(this, "alias" + alias, "");
        JPushInterface.deleteAlias(this, 0);
    }

    private void getInfo() {
        if (NetUtil.isConnected(this)) {
            getUserInfoPresenter = new GetUserInfoPresenter(this);
            getUserInfoPresenter.getUserInfo();
        } else {
            Toast toast = Toast.makeText(this, R.string.net_err, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void updateUserInfos() {
        if (NetUtil.isConnected(UserSetUserInfo.this)) {
            presenter = new UpdateUserInfoPresenter(UserSetUserInfo.this);
            presenter.updateUserInfo();
        } else {
            Toast toast = Toast.makeText(UserSetUserInfo.this, R.string.net_err, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void showDialogs(String mark, String info) {
        modifyDialog = new MDEditDialog.Builder(this)
                .setTitleVisible(true)
                .setTitleText(getString(R.string.please_input_your) + mark)
                .setTitleTextSize(20)
                .setTitleTextColor(R.color.black_light)
                .setContentTextSize(14)
                .setHintText(getString(R.string.please_input))
                .setInputTpye(InputType.TYPE_CLASS_TEXT)
                .setContentText(info)
                .setMaxLines(1)
                .setContentTextColor(R.color.black)
                .setButtonTextSize(14)
                .setLeftButtonTextColor(R.color.grey)
                .setLeftButtonText(getString(R.string.cancel))
                .setRightButtonTextColor(R.color.fe6900)
                .setRightButtonText(getString(R.string.pickerview_submit))
                .setLineColor(R.color.black)
                .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                    @Override
                    public void clickLeftButton(View view, String editText) {
                        modifyDialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view, String editText) {
                        formValue = editText;
                        //  if (!TextUtils.isEmpty(formValue)) {
                        updateUserInfos();
                        //  }
                    }
                })
                .setMinHeight(0.1f)
                .setWidth(0.8f)
                .build();
        modifyDialog.show();
    }


    private void initView() {
        Glide.with(this).load(R.drawable.fanhui).into(ivTitleBack);
        tvTitle.setText(R.string.user_info);
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public File upLoadFile() {
        return upLoadFile;
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public void uploadSuccess(String response) {
        formName = "avatar";
        formValue = response;
        SharedPreferencesUtil.put(this, "avatar", response);
        RequestOptions options = new RequestOptions().
                placeholder(R.drawable.img_loading).error(R.mipmap.icon);
        if (response.startsWith("http")) {
            Glide.with(this).load(response).apply(options).into(ivAvatar);
        } else if(response.startsWith("//thirdwx")){
            Glide.with(this).load("http:" + response).apply(options).into(ivAvatar);
        }else{
            Glide.with(this).load(Constants.imgsURL + response).apply(options).into(ivAvatar);
        }
        updateUserInfos();
    }

    @Override
    public void uploadFailed(String response) {
    }

    @Override
    public String formName() {
        return formName;
    }

    @Override
    public String formValue() {
        return formValue;
    }

    @Override
    public void getUserInfoSuccess(UserInfoData response) {
        UserInfoData.RespObjectBean bean = response.getRespObject();
        if (!TextUtils.isEmpty(bean.getEmail())) {
            tvUserAccount.setText(bean.getEmail());
        } else if (!TextUtils.isEmpty(bean.getCellphone())) {
            tvUserAccount.setText(bean.getCellphone());
        }
        tvSetCompany.setText(bean.getCompany());
        tvSetPosition.setText(bean.getTitle());
        tvSetNickName.setText(bean.getNickName());
        tvSetName.setText(bean.getUserName());
        String avatar = SharedPreferencesUtil.get(this, "avatar", "");
        RequestOptions options = new RequestOptions().
                placeholder(R.drawable.img_loading).error(R.mipmap.icon);
        if (avatar.startsWith("http")) {
            Glide.with(this).load(avatar).apply(options).into(ivAvatar);
        } else if(avatar.startsWith("//thirdwx")){
            Glide.with(this).load("http:" + avatar).apply(options).into(ivAvatar);
        }else{
            Glide.with(this).load(Constants.imgsURL + avatar).apply(options).into(ivAvatar);
        }

    }

    @Override
    public void getUserInfoFailed(String errInfo) {

    }

    @Override
    public void updateUserInfoSuccess() {
        if (formName.equals("userName")) {
            modifyDialog.dismiss();
            tvSetName.setText(formValue);
        } else if (formName.equals("nickName")) {
            modifyDialog.dismiss();
            tvSetNickName.setText(formValue);
        } else if (formName.equals("title")) {
            modifyDialog.dismiss();
            tvSetPosition.setText(formValue);
        } else if (formName.equals("company")) {
            modifyDialog.dismiss();
            tvSetCompany.setText(formValue);
        }

    }

    @Override
    public void updateUserInfoFailed(String errInfo) {

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


    @OnClick({R.id.ll_title_back, R.id.tv_exit_login, R.id.rl_user_name, R.id.rl_user_nickName, R.id.rl_user_company, R.id.rl_user_position, R.id.iv_avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_user_company:
                formName = "company";
                modifyInfo = tvSetCompany.getText().toString();
                showDialogs(getString(R.string.company), modifyInfo);
                break;
            case R.id.rl_user_name:
                formName = "userName";
                modifyInfo = tvSetName.getText().toString();
                showDialogs(getString(R.string.name), modifyInfo);
                break;
            case R.id.rl_user_nickName:
                formName = "nickName";
                modifyInfo = tvSetNickName.getText().toString();
                showDialogs(getString(R.string.nickName), modifyInfo);
                break;
            case R.id.rl_user_position:
                formName = "title";
                modifyInfo = tvSetPosition.getText().toString();
                showDialogs(getString(R.string.position), modifyInfo);
                break;
            case R.id.ll_title_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_exit_login:
                exitDialog.show();
                break;
            case R.id.iv_avatar:
                PackageManager pm = getPackageManager();
                boolean permission = (PackageManager.PERMISSION_GRANTED ==
                        pm.checkPermission("android.permission.CAMERA", "com.bagevent"));
                if (permission) {
                    showDialog();
                }else {
                    TosUtil.show("需要获取您的相册、照相使用权限");
                }
//                MPermissions.requestPermissions(this, REQUECT_CODE_CAMERA, Manifest.permission.CAMERA);
                break;
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
}
