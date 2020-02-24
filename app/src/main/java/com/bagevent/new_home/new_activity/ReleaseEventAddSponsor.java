package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.manager_interface.presenter.UpLoadImagePresenter;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpLoadImageView;
import com.bagevent.common.Constants;
import com.bagevent.new_home.new_interface.new_view.SummitSponsorView;
import com.bagevent.new_home.new_interface.presenter.SummitSponsorPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.SoftKeyboardStateHelper;
import com.bumptech.glide.Glide;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * Created by zwj on 2016/9/18.
 */
public class ReleaseEventAddSponsor extends BaseActivity implements View.OnClickListener, TextWatcher,UpLoadImageView,SummitSponsorView {

    private AutoLinearLayout sponsorAddBack;
    private AutoLinearLayout sponsorAddConfirm;
    private AutoLinearLayout chooseSponsorLogo;

    private EditText sponsorName;
    private EditText sponsorPhone;
    private EditText sponsorEmail;
    private EditText sponsorNetURL;
    private EditText sponsorIntroduce;

    private ImageView sponsorLogo;
    private ImageView ivClearName;
    private ImageView ivClearPhone;
    private ImageView ivClearEmail;
    private ImageView ivClearNetURL;
    private ImageView ivClearIntroduce;

    private UpLoadImagePresenter upLoadImagePresenter;
    private SummitSponsorPresenter summitSponsorPresenter;
    private File uploadFile;
    private String userId;
    private String logoUrl;

    private SoftKeyboardStateHelper softKeyboardStateHelper;
    private SoftObserveListener softObserveListener;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event_sponsor_add);
//        userId = SharedPreferencesUtil.get(ReleaseEventAddSponsor.this,"userId","");
//        initView();
//        setListener();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event_sponsor_add);
        userId = SharedPreferencesUtil.get(ReleaseEventAddSponsor.this,"userId","");
        initView();
        setListener();
    }

    private void getValue() {
        if(NetUtil.isConnected(this)) {
            if(!TextUtils.isEmpty(sponsorName.getText().toString())) {
                summitSponsorPresenter = new SummitSponsorPresenter(this);
                summitSponsorPresenter.summit();
            }else {
                setToastMsg(getString(R.string.organizer_name));
            }
        }else {
            setToastMsg(getString(R.string.net_err));
        }
    }

    private void setToastMsg(String msg) {
        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    /**
     * 从相机选择图片
     */
    private void openCameraChoose() {
//        RxGalleryFinal
//                .with(this)
//                .image()
//                .radio()
//                .hideCamera()
//                .imageLoader(ImageLoaderType.GLIDE)
//                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
//                    @Override
//                    protected void onEvent(ImageRadioResultEvent result) throws Exception {
//                        File file = new File(result.getResult().getOriginalPath());
//                        Luban.get(ReleaseEventAddSponsor.this)
//                                .load(file)
//                                .putGear(Luban.THIRD_GEAR)
//                                .setCompressListener(new OnCompressListener() {
//                            @Override
//                            public void onStart() {
//
//                            }
//
//                            @Override
//                            public void onSuccess(File file) {
//                                if(NetUtil.isConnected(ReleaseEventAddSponsor.this)) {
//                                    uploadFile = file;
//                               //     Log.e("filefdsf",file.getAbsolutePath());
//                                    upLoadImagePresenter = new UpLoadImagePresenter(ReleaseEventAddSponsor.this);
//                                    upLoadImagePresenter.upLoadImageFile();
//                                }
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//                        }).launch();
//                    }
//                })
//                .openGallery();
    }

    private class SoftObserveListener implements SoftKeyboardStateHelper.SoftKeyboardStateListener {

        @Override
        public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        }

        @Override
        public void onSoftKeyboardClosed() {
            if(ivClearName.getVisibility() == View.VISIBLE) {
                ivClearName.setVisibility(View.GONE);
            }

            if(ivClearIntroduce.getVisibility() == View.VISIBLE) {
                ivClearIntroduce.setVisibility(View.GONE);
            }

            if(ivClearPhone.getVisibility() == View.VISIBLE) {
                ivClearPhone.setVisibility(View.GONE);
            }

            if(ivClearEmail.getVisibility() == View.VISIBLE) {
                ivClearEmail.setVisibility(View.GONE);
            }

            if(ivClearNetURL.getVisibility() == View.VISIBLE) {
                ivClearNetURL.setVisibility(View.GONE);
            }

        }
    }

    private void setListener() {
        sponsorAddBack.setOnClickListener(this);
        sponsorAddConfirm.setOnClickListener(this);
        chooseSponsorLogo.setOnClickListener(this);
        sponsorName.addTextChangedListener(this);
        sponsorPhone.addTextChangedListener(this);
        sponsorEmail.addTextChangedListener(this);
        sponsorNetURL.addTextChangedListener(this);
        sponsorIntroduce.addTextChangedListener(this);
        ivClearEmail.setOnClickListener(this);
        ivClearNetURL.setOnClickListener(this);
        ivClearPhone.setOnClickListener(this);
        ivClearName.setOnClickListener(this);
        ivClearIntroduce.setOnClickListener(this);

        softObserveListener = new SoftObserveListener();
        softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.ll_add_sponsor));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softObserveListener);
    }


    private void initView() {
        sponsorAddBack = (AutoLinearLayout) findViewById(R.id.ll_event_sponsor_add_back);
        sponsorAddConfirm = (AutoLinearLayout) findViewById(R.id.ll_event_sponser_add_confirm);
        chooseSponsorLogo = (AutoLinearLayout) findViewById(R.id.ll_event_sponsor_choose);
        sponsorName = (EditText) findViewById(R.id.et_sponsor_name);
        sponsorEmail = (EditText) findViewById(R.id.et_sponsor_email);
        sponsorPhone = (EditText) findViewById(R.id.et_sponsor_phone);
        sponsorNetURL = (EditText) findViewById(R.id.et_sponsor_neturl);
        sponsorIntroduce = (EditText) findViewById(R.id.et_sponsor_introduce);
        sponsorLogo = (ImageView) findViewById(R.id.iv_event_sponsor_logo);
        ivClearName = (ImageView) findViewById(R.id.iv_clear_sponsor_name);
        ivClearPhone = (ImageView) findViewById(R.id.iv_clear_sponsor_phone);
        ivClearEmail = (ImageView) findViewById(R.id.iv_clear_sponsor_email);
        ivClearNetURL = (ImageView) findViewById(R.id.iv_clear_sponsor_neturl);
        ivClearIntroduce = (ImageView) findViewById(R.id.iv_clear_sponsor_introduce);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_event_sponser_add_confirm:
                getValue();
                break;
            case R.id.ll_event_sponsor_add_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_event_sponsor_choose:
                openCameraChoose();
                break;
            case R.id.iv_clear_sponsor_phone:
                sponsorPhone.setText("");
                break;
            case R.id.iv_clear_sponsor_email:
                sponsorEmail.setText("");
                break;
            case R.id.iv_clear_sponsor_name:
                sponsorName.setText("");
                break;
            case R.id.iv_clear_sponsor_neturl:
                sponsorNetURL.setText("");
                break;
            case R.id.iv_clear_sponsor_introduce:
                sponsorIntroduce.setText("");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        isEmpty(sponsorName,sponsorName.getText().toString(), ivClearName);
        isEmpty(sponsorEmail,sponsorEmail.getText().toString(), ivClearEmail);
        isEmpty(sponsorPhone,sponsorPhone.getText().toString(), ivClearPhone);
        isEmpty(sponsorNetURL,sponsorNetURL.getText().toString(), ivClearNetURL);
        isEmpty(sponsorIntroduce,sponsorIntroduce.getText().toString(), ivClearIntroduce);
    }

    private void isEmpty(EditText editText,String value, ImageView iv) {
        if ( editText.isFocused() && !TextUtils.isEmpty(value)) {
            iv.setVisibility(View.VISIBLE);
        } else {
            iv.setVisibility(View.GONE);
        }
    }

    @Override
    public File upLoadFile() {
        return uploadFile;
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
    public String organizerName() {
        return sponsorName.getText().toString();
    }

    @Override
    public String contactPhone() {
        return sponsorPhone.getText().toString();
    }

    @Override
    public String contactMail() {
        return sponsorEmail.getText().toString();
    }

    @Override
    public String officialHomePage() {
        return sponsorNetURL.getText().toString();
    }

    @Override
    public String brief() {
        return sponsorIntroduce.getText().toString();
    }

    @Override
    public String logoUrl() {
        return logoUrl;
    }

    @Override
    public void summitSponsorSuccess() {
        EventBus.getDefault().postSticky(new MsgEvent("from_ReleaseEventAddSponsor_add_success"));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void summitSponsorFailed(String errInfo) {

    }

    @Override
    public void uploadSuccess(String response) {
        logoUrl = response;
        Glide.with(this).load(Constants.imgsURL + response).into(sponsorLogo);
    }

    @Override
    public void uploadFailed(String response) {

    }
}
