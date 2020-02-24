package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.new_home.data.AddFormItemData;
import com.bagevent.new_home.data.AddFormResponse;
import com.bagevent.new_home.data.MarkChildFormData;
import com.bagevent.new_home.new_interface.new_view.AddFormItemView;
import com.bagevent.new_home.new_interface.new_view.AddFormView;
import com.bagevent.new_home.new_interface.new_view.DeleteFormItemView;
import com.bagevent.new_home.new_interface.new_view.UpdateFormItemView;
import com.bagevent.new_home.new_interface.new_view.UpdateFormView;
import com.bagevent.new_home.new_interface.presenter.AddCustomPresenter;
import com.bagevent.new_home.new_interface.presenter.AddFormItemPresenter;
import com.bagevent.new_home.new_interface.presenter.DeleteFormItemPresenter;
import com.bagevent.new_home.new_interface.presenter.UpdateFormItemPresenter;
import com.bagevent.new_home.new_interface.presenter.UpdateFormPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.bagevent.util.SoftKeyboardStateHelper;
import com.bagevent.view.FormChildItem;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/9/23.
 */
public class ReleaseEventMultiForm extends BaseActivity implements View.OnClickListener,TextWatcher, AddFormView, AddFormItemView, DeleteFormItemView, UpdateFormItemView, UpdateFormView {

    private AutoLinearLayout ll_multi_back;
    private AutoLinearLayout ll_multi_confirm;
    private AutoLinearLayout ll_event_ticket_footer;
    private TextView tv_add_mark;
    private EditText et_form_name;
    private ImageView iv_clear_form_name;
    private AutoLinearLayout ll_add_child;
    private ArrayList<MarkChildFormData> data;

    private SoftKeyboardStateHelper softKeyboardStateHelper;
    private SoftObserveListener softListener;

    private int eventId = -1;
    private String userId = "";
    private String filedName = "";
    private int fieldId = -1;
    private String items = "";//添加的子item
    private String showName = "";
    private List<FormType.options> option;
    private String type = "";
    private String itemName = "";
    private String itemValue = "";
    private int itemId;//删除item之后,需要移除的view
    private int position = -1;//标识修改item的位置
    private AddCustomPresenter addCustomPresenter;
    private AddFormItemPresenter addFormItemPresenter;
    private DeleteFormItemPresenter deleteFormItemPresenter;
    private UpdateFormItemPresenter updateFormItemPresenter;
    private UpdateFormPresenter updateFormPresenter;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event_multichoice);
//        Bundle bundle = getIntent().getExtras();
//        eventId = bundle.getInt("eventId");
//        filedName = bundle.getString("fieldName");
//        userId = SharedPreferencesUtil.get(this, "userId", "");
//        type = bundle.getString("type");
//        if (!TextUtils.isEmpty(type)) {
//            showName = bundle.getString("showName");
//            fieldId = bundle.getInt("formFieldId");
//            if (type.equals("formModify")) {
//                option = new ArrayList<>();
//                option = (List) bundle.getSerializable("options");
//            }
//        }
//        initView();
//        setListener();
//        initItem();
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event_multichoice);
        Bundle bundle = getIntent().getExtras();
        eventId = bundle.getInt("eventId");
        filedName = bundle.getString("fieldName");
        userId = SharedPreferencesUtil.get(this, "userId", "");
        type = bundle.getString("type");
        if (!TextUtils.isEmpty(type)) {
            showName = bundle.getString("showName");
            fieldId = bundle.getInt("formFieldId");
            if (type.equals("formModify")) {
                option = new ArrayList<>();
                option = (List) bundle.getSerializable("options");
            }
        }
        initView();
        setListener();
        initItem();
    }

    private void setToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    int isRequiredModify = -1;

    private void modifyChild() {
        for (int i = 0; i < data.size(); i++) {
            if (!TextUtils.isEmpty(data.get(i).getItem().value())) {
                if (!data.get(i).getOldValue().equals(data.get(i).getItem().value())) {
                    isRequiredModify = 1;
                    position = i;
                    itemName = data.get(i).getKey();
                    itemValue = data.get(i).getItem().value();
                    updateFormItemPresenter = new UpdateFormItemPresenter(this);
                    updateFormItemPresenter.updateMultiItem();
                }
            } else {
                isRequiredModify = 1;
                setToast(getString(R.string.add_child_item));
                break;
            }
        }
        if(isRequiredModify != 1) {
            EventBus.getDefault().postSticky(new MsgEvent("fromChildForm"));
            AppManager.getAppManager().finishActivity();
        }
     //   Log.e("fd", isRequiredModify + "F");
    }

    private void getAllValue() {
        if (type.equals("formModify")) {
            itemName = "";
            itemValue = "";
            isRequiredModify = -1;
            if (!showName.equals(et_form_name.getText().toString())) {
                isRequiredModify = 1;
                showName = et_form_name.getText().toString();
                updateFormPresenter = new UpdateFormPresenter(this);
                updateFormPresenter.updateForm();
            } else {
                modifyChild();
            }

        } else {
            showName = "";
            items = "";
            itemName = "";
            int isEmpty = -1;
            showName = et_form_name.getText().toString();
            if (!TextUtils.isEmpty(showName)) {
                for (int i = 0; i < data.size(); i++) {
                    if (!TextUtils.isEmpty(data.get(i).getItem().value())) {
                        items = items + data.get(i).getItem().value() + ",";
                        isEmpty = 1;//表示所有子项目均填写
                    } else {
                        isEmpty = -1;
                        setToast(getString(R.string.add_child_item));
                        break;
                    }
                }
                if (isEmpty == 1) {
                    items = items.substring(0, items.length() - 1);
                    addCustomPresenter = new AddCustomPresenter(this);
                    addCustomPresenter.addMultiForm();
                }
            } else {
                setToast(getString(R.string.input_form_name));
            }

        }

    }

    private void setView() {
        final FormChildItem item = new FormChildItem(this);
        item.setId(View.generateViewId());
        ll_add_child.addView(item);
        MarkChildFormData mark = new MarkChildFormData();
        mark.setItem(item);
        mark.setId(item.getId());
        data.add(mark);
        item.OnClearListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.clearText();
            }
        });
    }

    /**
     * 初始化修改的item
     *
     * @param value
     * @param key
     */
    private void modifyItem(String value, String key) {
        final FormChildItem item = new FormChildItem(this);
        item.setId(View.generateViewId());
        item.setIvTag(item.getId());//将id设为清空和删除按钮的tag
        item.setName(value);
        item.setDeleteTag(item.getId());
        item.setDeleteVisible();
        ll_add_child.addView(item);
        MarkChildFormData mark = new MarkChildFormData();
        mark.setItem(item);
        mark.setId(item.getId());
        mark.setKey(key);
        mark.setOldValue(item.value());
        data.add(mark);
        item.OnClearListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.clearText();
            }
        });
        item.setDeleteListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetUtil.isConnected(ReleaseEventMultiForm.this)) {
                    if (data.size() > 1) {
                        for (int i = 0; i < data.size(); i++) {
                            if ((int) view.getTag() == data.get(i).getId()) {
                                itemId = (int) view.getTag();
                                itemName = data.get(i).getKey();
                            }
                        }
                        deleteFormItemPresenter = new DeleteFormItemPresenter(ReleaseEventMultiForm.this);
                        deleteFormItemPresenter.deletMultiItem();
                    } else {
                        setToast(getString(R.string.retail));
                    }

                } else {
                    setToast(getString(R.string.net_err));
                }
            }
        });
    }

    private void initItem() {
        data = new ArrayList<MarkChildFormData>();
        if (type.equals("formModify")) {//如果为修改表单
            et_form_name.setText(showName);
            for (int i = 0; i < option.size(); i++) {
                modifyItem(option.get(i).getValue(), option.get(i).getKey());
            }
        } else {
            for (int i = 0; i < 2; i++) {
                final FormChildItem item = new FormChildItem(this);
                item.setId(View.generateViewId());
                item.setIvTag(item.getId());//将id设为清空和删除按钮的tag
                ll_add_child.addView(item);
                MarkChildFormData mark = new MarkChildFormData();
                mark.setItem(item);
                mark.setId(item.getId());
                data.add(mark);
                item.setDeleteGone();
                item.OnClearListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item.clearText();
                    }
                });
            }
        }
    }

    private void setListener() {
        ll_multi_back.setOnClickListener(this);
        ll_multi_confirm.setOnClickListener(this);
        ll_event_ticket_footer.setOnClickListener(this);
        et_form_name.addTextChangedListener(this);
        iv_clear_form_name.setOnClickListener(this);
        softListener = new SoftObserveListener();
        softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.ll_multi));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softListener);
    }

    private void initView() {
        ll_multi_back = (AutoLinearLayout) findViewById(R.id.ll_event_multi_back);
        ll_multi_confirm = (AutoLinearLayout) findViewById(R.id.ll_event_multi_confirm);
        ll_add_child = (AutoLinearLayout) findViewById(R.id.ll_add_child);
        ll_event_ticket_footer = (AutoLinearLayout) findViewById(R.id.ll_event_ticket_footer);
        et_form_name = (EditText) findViewById(R.id.et_form_name);
        iv_clear_form_name = (ImageView) findViewById(R.id.iv_clear_form_name);
        tv_add_mark = (TextView) findViewById(R.id.tv_add_mark);
        tv_add_mark.setText(R.string.add_child);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_event_multi_back:
                EventBus.getDefault().postSticky(new MsgEvent("fromChildForm"));
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_event_multi_confirm:
                getAllValue();//获得数据上传到服务器
                break;
            case R.id.ll_event_ticket_footer:
                if (!TextUtils.isEmpty(type) && type.equals("formModify")) {
                    if (NetUtil.isConnected(this)) {
                        addFormItemPresenter = new AddFormItemPresenter(this);
                        addFormItemPresenter.addItem();
                    } else {
                        setToast(getString(R.string.net_err));
                    }
                } else {
                    setView();
                }
                break;
            case R.id.iv_clear_form_name:
                et_form_name.setText("");
                break;
        }
    }

    @Override
    public Context mContext() {
        return this;
    }

    @Override
    public String detailEventId() {
        return eventId + "";
    }

    @Override
    public String userId() {
        return userId;
    }

    @Override
    public int formFieldId() {
        return fieldId;
    }

    @Override
    public int type() {
        return 2;
    }

    @Override
    public String formFieldValue() {
        return showName;
    }

    @Override
    public void upDateSuccess() {
        showName = et_form_name.getText().toString();
        modifyChild();
    }

    @Override
    public void upDateFailed(String errInfo) {
        setToast(errInfo);
    }

    @Override
    public String itemName() {
        return itemName;
    }

    @Override
    public String itemValue() {
        return itemValue;
    }

    @Override
    public void upDateItemSuccess() {
        EventBus.getDefault().postSticky(new MsgEvent("fromChildForm"));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void upDateItemFailed(String errInfo) {
        setToast(errInfo);
    }

    @Override
    public void deleteFormItemSuccess() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == itemId) {
                ll_add_child.removeView(data.get(i).getItem());
                data.remove(i);
            }
        }

    }

    @Override
    public void deleteFormItemFailed(String errInfo) {

    }

    @Override
    public void addFormItemSuccess(AddFormItemData response) {
        modifyItem(response.getRespObject().getValue(), response.getRespObject().getKey());
    }

    @Override
    public void addFormItemFailed(String errInfo) {
        setToast(errInfo);
    }

    @Override
    public String filedName() {
        return filedName;
    }

    @Override
    public String showName() {
        return showName;
    }

    @Override
    public String items() {
        return items;
    }

    @Override
    public int filedType() {
        return 1;
    }

    @Override
    public void eventAddFormSuccess(AddFormResponse response) {

    }

    @Override
    public void eventAddFormFailed(String errInfo) {

    }

    @Override
    public void eventAddCustomFormSuccess(AddFormResponse response) {

    }

    @Override
    public void eventAddCustomFormFailed(String errInfo) {

    }

    @Override
    public void eventAddMultiFormSuccess(AddFormResponse response) {
        EventBus.getDefault().postSticky(new MsgEvent("fromChildForm"));
        AppManager.getAppManager().finishActivity();
    }

    @Override
    public void eventAddMultiFormFailed(String errInfo) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!TextUtils.isEmpty(et_form_name.getText().toString()) && et_form_name.isFocused()) {
            iv_clear_form_name.setVisibility(View.VISIBLE);
        }else {
            iv_clear_form_name.setVisibility(View.GONE);
        }
    }

    private class SoftObserveListener implements SoftKeyboardStateHelper.SoftKeyboardStateListener {

        @Override
        public void onSoftKeyboardOpened(int keyboardHeightInPx) {
           /* if (iv_clear_form_name.getVisibility() == View.GONE && !TextUtils.isEmpty(et_form_name.getText().toString())) {
                iv_clear_form_name.setVisibility(View.VISIBLE);
            }*/
        }

        @Override
        public void onSoftKeyboardClosed() {
            if (iv_clear_form_name.getVisibility() == View.VISIBLE) {
                iv_clear_form_name.setVisibility(View.GONE);
            }
        }
    }
}
