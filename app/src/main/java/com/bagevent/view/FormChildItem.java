package com.bagevent.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bagevent.R;
import com.bagevent.util.SoftKeyboardStateHelper;

/**
 * Created by zwj on 2016/9/23.
 */
public class FormChildItem extends LinearLayout implements TextWatcher {

    private Context mContext;
    private EditText et_form_name;
    private ImageView iv_clear_form_child_name;
    private ImageView iv_child_delete;

    private SoftKeyboardStateHelper softKeyboardStateHelper;
    private SoftObserveListener softListener;

    private class SoftObserveListener implements SoftKeyboardStateHelper.SoftKeyboardStateListener {

        @Override
        public void onSoftKeyboardOpened(int keyboardHeightInPx) {
          /*  if(iv_clear_form_child_name.getVisibility() == View.GONE && !TextUtils.isEmpty(et_form_name.getText().toString())) {
                iv_clear_form_child_name.setVisibility(View.VISIBLE);
            }*/
        }

        @Override
        public void onSoftKeyboardClosed() {
            if(iv_clear_form_child_name.getVisibility() == View.VISIBLE) {
                iv_clear_form_child_name.setVisibility(View.GONE);
            }
        }
    }

    public FormChildItem(Context context) {
        super(context);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.release_event_multichoice_item,this,true);
        et_form_name = (EditText)view.findViewById(R.id.et_form_child_name);
        iv_clear_form_child_name = (ImageView)view.findViewById(R.id.iv_clear_form_child_name);
        iv_child_delete = (ImageView)view.findViewById(R.id.iv_child_delete);
        et_form_name.setText(R.string.child_item);
        et_form_name.addTextChangedListener(this);

        softListener = new SoftObserveListener();
        softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.child_form));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softListener);
    }

    public FormChildItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormChildItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void OnClearListener(OnClickListener listener) {
        iv_clear_form_child_name.setOnClickListener(listener);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(et_form_name.isFocused() && !TextUtils.isEmpty(et_form_name.getText().toString())) {
            iv_clear_form_child_name.setVisibility(VISIBLE);
        }else {
            iv_clear_form_child_name.setVisibility(GONE);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    public void setIvTag(int tag) {
        iv_clear_form_child_name.setTag(tag);
    }

    public void setDeleteTag(int tag) {
        iv_child_delete.setTag(tag);
    }

    public String value() {
        return et_form_name.getText().toString();
    }

    public EditText getEt_form_name() {
        return et_form_name;
    }

    public void setName(String name) {
        et_form_name.setText(name);
    }

    public void clearText() {
        et_form_name.setText("");
    }

    public void setDeleteListener(OnClickListener listener) {
        iv_child_delete.setOnClickListener(listener);
    }
    public void setDeleteVisible () {
        iv_child_delete.setVisibility(VISIBLE);
    }

    public void setDeleteGone() {
        iv_child_delete.setVisibility(GONE);
    }

}
