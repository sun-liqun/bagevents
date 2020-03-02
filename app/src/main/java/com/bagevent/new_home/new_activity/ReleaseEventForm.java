package com.bagevent.new_home.new_activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bagevent.R;
import com.bagevent.activity_manager.manager_fragment.MsgEvent;
import com.bagevent.activity_manager.manager_fragment.data.FormType;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.GetFormTypeView;
import com.bagevent.new_home.adapter.ReleaseEventFormAdapter;
import com.bagevent.new_home.adapter.ReleaseEventFormPopAdater;
import com.bagevent.new_home.data.AddFormResponse;
import com.bagevent.new_home.data.GetAllFormData;
import com.bagevent.new_home.new_interface.new_view.AddFormView;
import com.bagevent.new_home.new_interface.new_view.DeleteFormView;
import com.bagevent.new_home.new_interface.new_view.GetAllFormView;
import com.bagevent.new_home.new_interface.new_view.UpdateFormView;
import com.bagevent.new_home.new_interface.presenter.AddCustomPresenter;
import com.bagevent.new_home.new_interface.presenter.AddFormPresenter;
import com.bagevent.new_home.new_interface.presenter.DeleteFormPresenter;
import com.bagevent.new_home.new_interface.presenter.GetAllFormPresenter;
import com.bagevent.new_home.new_interface.presenter.GetFormContentPresenter;
import com.bagevent.new_home.new_interface.presenter.UpdateFormPresenter;
import com.bagevent.util.AppManager;
import com.bagevent.util.NetUtil;
import com.bagevent.util.SharedPreferencesUtil;
import com.wevey.selector.dialog.MDEditDialog;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwj on 2016/9/21.
 */
public class ReleaseEventForm extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, TagFlowLayout.OnTagClickListener,
        GetFormTypeView, GetAllFormView, DeleteFormView, AddFormView, ReleaseEventFormAdapter.OnDeleteFormListener,ReleaseEventFormAdapter.OnRequiredListener,UpdateFormView {

    private AutoLinearLayout ll_event_form_back;
    private AutoLinearLayout ll_event_form_confirm;
    private ListView lv_form;
    private TagFlowLayout flowlayout;
    private AutoLinearLayout ll_event_form_footer;
    private AutoLinearLayout form_footer;
    private AutoLinearLayout ll_sys_form;
    private SwipeRefreshLayout swipe_form;
    private EditText et_getFocus;

    /**
     * pop
     */
    private PopupWindow mPopupWindow;
    private View mPopView;
    private TextView tv_form_cancel;
    private ListView lv_pop_form;

    /**
     * 获取用户表单数据
     */
    private GetFormContentPresenter getFormContentPresenter;
    private GetAllFormPresenter getAllFormPresenter;
    private ArrayList<FormType.RespObjectBean> mForms;//活动表单
    private List<GetAllFormData.RespObjectBean> mReserserForm;//系统常用表单
    private ArrayList<GetAllFormData.RespObjectBean> mCustomForm;//自定义表单
    private int eventId = -1;
    private String userId = "";
    private ReleaseEventFormAdapter adapter;
    private ReleaseEventFormPopAdater popAdater;
    /**
     * 添加表单
     */
    private AddFormPresenter addFormPresenter;
    private AddCustomPresenter addCustomPresenter;
    private String fieldName = "";
    private int filedType = -1;
    private String showName = "";
    /**
     * 删除表单
     */
    private DeleteFormPresenter deleteFormPresenter;
    private int formFieldId = -1;
    private int deletePos = -1;//删除的位置
    private MDEditDialog dialog6;

    /**
     * 修改表单
     */
    private String formFieldValue = "";
    private int type = -1;
    private int isSystemForm = -1;
    private int index = -1;
    private int  modifyOrRequired = -1;//modifyOrRequired == 1修改名称,modifyOrRequired == 2修改是否必填
    private UpdateFormPresenter updateFormPresenter;


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
//        setContentView(R.layout.release_event_forms);
//        Intent intent = getIntent();
//        eventId = intent.getIntExtra("eventId", -1);
//        userId = SharedPreferencesUtil.get(this, "userId", "");
//        initView();
//        initPop();
//        setListener();
//        initData();
//
//    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.release_event_forms);
        Intent intent = getIntent();
        eventId = intent.getIntExtra("eventId", -1);
        userId = SharedPreferencesUtil.get(this, "userId", "");
        initView();
        initPop();
        setListener();
        initData();
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

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onEvent(MsgEvent msgEvent) {
        if(msgEvent.mMsg.equals("fromChildForm")) {
            initData();
        }
    }

    private void setCustomForm(final String mark, final int postion) {//根据mark决定添加or修改
        dialog6 = new MDEditDialog.Builder(this)
                .setTitleVisible(true)
                .setTitleText(getString(R.string.custom_field_name))
                .setTitleTextSize(20)
                .setTitleTextColor(R.color.black_light)
                .setContentTextSize(14)
                .setHintText(getString(R.string.please_input))
                .setInputTpye(InputType.TYPE_CLASS_TEXT)
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
                    public void clickLeftButton(View view, String text) {
                        dialog6.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view, String text) {
                        if(mark.equals("add")) {
                            showName = text;
                            filedType = 1;
                            isSystemForm = 1;
                            fieldName = mCustomForm.get(postion).getFieldName();
                            if (NetUtil.isConnected(ReleaseEventForm.this)) {
                                addCustomPresenter = new AddCustomPresenter(ReleaseEventForm.this);
                                addCustomPresenter.addEventForm();
                            } else {
                                setToastMsg(R.string.net_err, "");
                            }
                        }else {
                            modifyOrRequired = 1;
                            formFieldId = mForms.get(postion).getFormFieldId();
                            type = 2;
                            formFieldValue = text;
                            index = postion;
                            if(NetUtil.isConnected(ReleaseEventForm.this)) {
                                updateFormPresenter = new UpdateFormPresenter(ReleaseEventForm.this);
                                updateFormPresenter.updateForm();
                            }else {
                                setToastMsg(R.string.net_err,"");
                            }
                        }

                    }
                })
                .setMinHeight(0.1f)
                .setWidth(0.8f)
                .build();
        dialog6.show();
    }

    private void initData() {
        if (NetUtil.isConnected(this)) {
            ll_sys_form.setVisibility(View.VISIBLE);
            getFormContentPresenter = new GetFormContentPresenter(this);
            getFormContentPresenter.getForm();//活动表单
            getAllFormPresenter = new GetAllFormPresenter(this);
            getAllFormPresenter.allForm();//系统表单
        } else {
            ll_sys_form.setVisibility(View.GONE);
            setToastMsg(R.string.net_err, "");
        }
    }

    private void setToastMsg(int iMsg, String sMsg) {
        if (!TextUtils.isEmpty(sMsg)) {
            Toast toast = Toast.makeText(this, sMsg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, iMsg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    private void initPop() {
        mPopView = getLayoutInflater().inflate(R.layout.release_event_form_pop, null);
        tv_form_cancel = (TextView) mPopView.findViewById(R.id.tv_form_cancel);
        lv_pop_form = (ListView) mPopView.findViewById(R.id.lv_custom_form);
        mPopupWindow = new PopupWindow(mPopView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void setPop(View v) {
        //产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        mPopupWindow.showAtLocation((View) v.getParent(), Gravity.BOTTOM, 0, 0);
    }

    private void setListener() {
        ll_event_form_confirm.setOnClickListener(this);
        ll_event_form_back.setOnClickListener(this);
        tv_form_cancel.setOnClickListener(this);
        flowlayout.setOnTagClickListener(this);
        ll_event_form_footer.setOnClickListener(this);
        form_footer.setOnClickListener(this);
        swipe_form.setOnRefreshListener(this);
        adapter.setOnDeleteFormListener(this);
        adapter.setOnRequiredListener(this);
        lv_form.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mForms.get(i).getOptionItems() != null && mForms.get(i).getOptionItems().size() > 0) {
                    Intent intent = new Intent(ReleaseEventForm.this,ReleaseEventMultiForm.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type","formModify");
                    bundle.putInt("eventId",eventId);
                    bundle.putString("showName",mForms.get(i).getShowName());
                    bundle.putString("fieldName",mForms.get(i).getFieldName());
                    bundle.putInt("formFieldId",mForms.get(i).getFormFieldId());
                    bundle.putSerializable("options",(Serializable) mForms.get(i).getOptionItems());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(mForms.get(i).getReserveField() != 0){
                //    Log.e("fdf","Fd"+mForms.get(i).getReserveField()+"F");
                    setCustomForm("modify",i);
                }else {
                    Log.e("fdf","Fd" + mForms.get(i).getReserveField()+"f");

                }

            }
        });

        lv_pop_form.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
           //     Log.e("fdf",mCustomForm.get(position).getFieldName());
                if (mCustomForm.get(position).getFieldName().equals("radio") || mCustomForm.get(position).getFieldName().equals("checkbox") || mCustomForm.get(position).getFieldName().equals("select")) {
                    Intent intent = new Intent(ReleaseEventForm.this,ReleaseEventMultiForm.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type","addForm");
                    bundle.putInt("eventId",eventId);
                    bundle.putString("fieldName",mCustomForm.get(position).getFieldName());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    setCustomForm("add",position);
                }
            }
        });
    }

    private void initView() {
        ll_event_form_back = (AutoLinearLayout) findViewById(R.id.ll_event_form_back);
        ll_event_form_confirm = (AutoLinearLayout) findViewById(R.id.ll_event_form_confirm);
        lv_form = (ListView) findViewById(R.id.lv_form);
        swipe_form = (SwipeRefreshLayout) findViewById(R.id.swipe_form);
        View footer = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.release_event_form_footer, null, false);
        flowlayout = (TagFlowLayout) footer.findViewById(R.id.form_tag);
        ll_event_form_footer = (AutoLinearLayout) footer.findViewById(R.id.ll_event_form_footer);
        form_footer = (AutoLinearLayout) footer.findViewById(R.id.form_footer);
        ll_sys_form = (AutoLinearLayout) footer.findViewById(R.id.ll_sys_form);
        et_getFocus = (EditText) footer.findViewById(R.id.et_getFocus);
        lv_form.addFooterView(footer);
        mForms = new ArrayList<FormType.RespObjectBean>();
        adapter = new ReleaseEventFormAdapter(mForms, this);
        lv_form.setAdapter(adapter);
    }

    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        boolean isContainsForm = false;
        for (int i = 0; i < mForms.size(); i++) {//判断列表中是否已经有该系统字段,没有该字段则进行添加操作
            if (mForms.get(i).getFieldName().equals(mReserserForm.get(position).getFieldName())) {
                isContainsForm = true;
            }
        }
        if (NetUtil.isConnected(this)) {
            if (!isContainsForm) {
                filedType = 0;
                isSystemForm = 0;
                fieldName = mReserserForm.get(position).getFieldName();
                addFormPresenter = new AddFormPresenter(this);
                addFormPresenter.addEventForm();
            } else {
                setToastMsg(0, getString(R.string.this_form));
            }
        } else {
            setToastMsg(R.string.net_err, "");
        }
        return false;
    }

    @Override
    public void onRefresh() {
        initData();
        swipe_form.setRefreshing(false);
    }

    @Override
    public void onDeleteForm(int position) {
        if (NetUtil.isConnected(this)) {
            deletePos = position;
            formFieldId = mForms.get(position).getFormFieldId();
            deleteFormPresenter = new DeleteFormPresenter(ReleaseEventForm.this);
            deleteFormPresenter.delete();
        } else {
            setToastMsg(R.string.net_err, "");
        }
    }

    @Override
    public void isRequired(int position) {
        if(NetUtil.isConnected(this)) {
            modifyOrRequired = 2;
            index = position;
            type = 1;
            formFieldId = mForms.get(position).getFormFieldId();
            if(mForms.get(position).getRequired() == 1) {
                formFieldValue = "0";
            }else {
                formFieldValue = "1";
            }
            updateFormPresenter = new UpdateFormPresenter(this);
            updateFormPresenter.updateForm();
        }else {
            setToastMsg(R.string.net_err,"");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_form_cancel:
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.ll_event_form_footer:
                if(NetUtil.isConnected(this)) {
                    setPop(v);
                }else {
                    setToastMsg(R.string.net_err,"");
                }
                break;
            case R.id.form_footer://为footer添加点击事件以屏幕listview的Item点击事件
                break;
            case R.id.ll_event_form_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ll_event_form_confirm:
                AppManager.getAppManager().finishActivity();
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
    public int sType() {
        return 0;
    }

    @Override
    public String userId() {
        return userId;
    }


    @Override
    public void showAllForm(GetAllFormData response) {
        mReserserForm = new ArrayList<GetAllFormData.RespObjectBean>();
        mCustomForm = new ArrayList<GetAllFormData.RespObjectBean>();
        for (int i = 0; i < response.getRespObject().size(); i++) {
            GetAllFormData.RespObjectBean data = response.getRespObject().get(i);
            GetAllFormData.RespObjectBean form = new GetAllFormData.RespObjectBean();
            if (data.getReserveField() == 0 && !data.getFieldName().equals("username") && !data.getFieldName().equals("email_address") && !data.getFieldName().equals("mobile_phone")
                    && !data.getFieldName().equals("first_name") && !data.getFieldName().equals("last_name")) {
                form.setShowName(data.getShowName());
                form.setFieldIcon(data.getFieldIcon());
                form.setFieldId(data.getFieldId());
                form.setFieldName(data.getFieldName());
                form.setFieldType(data.getFieldType());
                form.setReserveField(data.getReserveField());
                mReserserForm.add(form);
            } else if (data.getReserveField() == 1) {
                form.setShowName(data.getShowName());
                form.setFieldIcon(data.getFieldIcon());
                form.setFieldId(data.getFieldId());
                form.setFieldName(data.getFieldName());
                form.setFieldType(data.getFieldType());
                form.setReserveField(data.getReserveField());
                mCustomForm.add(form);
            }
        }
        final LayoutInflater mInflater = LayoutInflater.from(this);
        flowlayout.setAdapter(new TagAdapter(mReserserForm) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View view = mInflater.inflate(R.layout.release_event_form_tag, parent, false);
                TextView tv = (TextView) view.findViewById(R.id.tv_form_tag);
                tv.setText(mReserserForm.get(position).getShowName());
                return view;
            }
        });

        popAdater = new ReleaseEventFormPopAdater(mCustomForm, this);
        lv_pop_form.setAdapter(popAdater);
    }

    @Override
    public void showAllFormFailed(String errInfo) {
        setToastMsg(0, errInfo);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showDetailInfo(FormType formType) {
        mForms.clear();
        for (int i = 0; i < formType.getRespObject().size(); i++) {
            FormType.RespObjectBean bean = formType.getRespObject().get(i);
            FormType.RespObjectBean form = new FormType.RespObjectBean();
            form.setFieldTypeName(bean.getFieldTypeName());
            form.setFormFieldId(bean.getFormFieldId());
            form.setOptionItems(bean.getOptionItems());
            form.setOptions(bean.getOptions());
            form.setRequired(bean.getRequired());
            form.setShowName(bean.getShowName());
            form.setSort(bean.getSort());
            form.setFieldName(bean.getFieldName());
            form.setReserveField(bean.getReserveField());
            mForms.add(form);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showDetailErrInfo(String errInfo) {
        setToastMsg(0, errInfo);
    }

    @Override
    public void showBadgeFormInfo(FormType formType) {

    }

    @Override
    public void showBadgeFormErrInfo(String errInfo) {

    }


    @Override
    public int formFieldId() {
        return formFieldId;
    }

    @Override
    public int type() {
        return type;
    }

    @Override
    public String formFieldValue() {
        return formFieldValue;
    }

    @Override
    public void upDateSuccess() {
        if(modifyOrRequired == 1) {
            dialog6.dismiss();
            setToastMsg(0,getString(R.string.change_success));
            initData();
        }else {
            mForms.get(index).setRequired(Integer.parseInt(formFieldValue));
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void upDateFailed(String errInfo) {

    }

    @Override
    public void deleteSuccess() {
        mForms.remove(deletePos);
        adapter.notifyDataSetChanged();
        setToastMsg(0, getString(R.string.delete_success));
    }

    @Override
    public void deleteFailed(String errInfo) {

    }

    @Override
    public String filedName() {
        return fieldName;
    }

    @Override
    public String showName() {
        return showName;
    }

    @Override
    public String items() {
        return null;
    }

    @Override
    public int filedType() {
        return filedType;
    }

    @Override
    public void eventAddFormSuccess(AddFormResponse response) {//添加常用表单项
        FormType.RespObjectBean form = new FormType.RespObjectBean();
        AddFormResponse.RespObjectBean bean = response.getRespObject();
        form.setFormFieldId(bean.getFormFieldId());
        form.setRequired(bean.getRequired());
        form.setShowName(bean.getShowName());
        form.setSort(bean.getSort());
        form.setFieldName(bean.getFieldName());
        form.setOptions(bean.getOptions());
        form.setReserveField(isSystemForm);
      //  Log.e("fdf","Fd");
        mForms.add(form);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void eventAddFormFailed(String errInfo) {
        setToastMsg(0,errInfo);
    }

    @Override
    public void eventAddCustomFormSuccess(AddFormResponse response) {//添加自定义表单项
        FormType.RespObjectBean form = new FormType.RespObjectBean();
        AddFormResponse.RespObjectBean bean = response.getRespObject();
        form.setFormFieldId(bean.getFormFieldId());
        form.setRequired(bean.getRequired());
        form.setShowName(bean.getShowName());
        form.setSort(bean.getSort());
        form.setFieldName(bean.getFieldName());
        form.setReserveField(isSystemForm);
       // Log.e("Fd",form.getReserveField()+"FF");
        mForms.add(form);
        adapter.notifyDataSetChanged();
        dialog6.dismiss();
    }

    @Override
    public void eventAddCustomFormFailed(String errInfo) {

    }

    @Override
    public void eventAddMultiFormSuccess(AddFormResponse response) {

    }

    @Override
    public void eventAddMultiFormFailed(String errInfo) {

    }

}
