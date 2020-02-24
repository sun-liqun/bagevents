package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.UpdateFormItemInterface;
import com.bagevent.new_home.new_interface.impl.UpdateFormItemImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnUpdateFormItemListener;
import com.bagevent.new_home.new_interface.new_view.UpdateFormItemView;

/**
 * Created by zwj on 2016/9/26.
 */
public class UpdateFormItemPresenter {
    private UpdateFormItemInterface updateFormItemInterface;
    private UpdateFormItemView updateFormItemView;

    public UpdateFormItemPresenter(UpdateFormItemView updateFormItemView) {
        this.updateFormItemInterface = new UpdateFormItemImpl();
        this.updateFormItemView = updateFormItemView;
    }

    public void updateMultiItem() {
        updateFormItemInterface.updateFormItem(updateFormItemView.mContext(),updateFormItemView.detailEventId(), updateFormItemView.userId(), updateFormItemView.formFieldId(), updateFormItemView.itemName(), updateFormItemView.itemValue(), new OnUpdateFormItemListener() {
            @Override
            public void updateFormItemSuccess() {
                updateFormItemView.upDateItemSuccess();
            }

            @Override
            public void updateFormItemFailed(String errInfo) {
                updateFormItemView.upDateItemFailed(errInfo);
            }
        });
    }
}
