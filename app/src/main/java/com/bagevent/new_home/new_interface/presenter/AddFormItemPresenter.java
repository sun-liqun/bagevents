package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.data.AddFormItemData;
import com.bagevent.new_home.new_interface.AddFormItemInterface;
import com.bagevent.new_home.new_interface.impl.AddFormItemImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnAddFormItemListener;
import com.bagevent.new_home.new_interface.new_view.AddFormItemView;

/**
 * Created by zwj on 2016/9/26.
 */
public class AddFormItemPresenter {
    private AddFormItemInterface addFormItemInterface;
    private AddFormItemView addFormItemView;

    public AddFormItemPresenter(AddFormItemView addFormItemView) {
        this.addFormItemInterface = new AddFormItemImpl();
        this.addFormItemView = addFormItemView;
    }

    public void addItem() {
        addFormItemInterface.addFormItem(addFormItemView.mContext(),addFormItemView.detailEventId(), addFormItemView.userId(), addFormItemView.formFieldId(), new OnAddFormItemListener() {
            @Override
            public void addItemSuccess(AddFormItemData response) {
                addFormItemView.addFormItemSuccess(response);
            }

            @Override
            public void addItemFailed(String errInfo) {
                addFormItemView.addFormItemFailed(errInfo);
            }
        });
    }
}
