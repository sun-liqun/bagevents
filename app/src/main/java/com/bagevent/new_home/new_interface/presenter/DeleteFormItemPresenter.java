package com.bagevent.new_home.new_interface.presenter;

import com.bagevent.new_home.new_interface.DeleteFormItemInterface;
import com.bagevent.new_home.new_interface.impl.DeleteFormItemImpl;
import com.bagevent.new_home.new_interface.listenerInterface.OnDeleteFormItemListener;
import com.bagevent.new_home.new_interface.new_view.DeleteFormItemView;

/**
 * Created by zwj on 2016/9/26.
 */
public class DeleteFormItemPresenter {
    private DeleteFormItemInterface deleteFormItemInterface;
    private DeleteFormItemView deleteFormItemView;

    public DeleteFormItemPresenter(DeleteFormItemView deleteFormItemView) {
        this.deleteFormItemInterface = new DeleteFormItemImpl();
        this.deleteFormItemView = deleteFormItemView;
    }

    public void deletMultiItem() {
        deleteFormItemInterface.deleteFormItem(deleteFormItemView.mContext(),deleteFormItemView.detailEventId(), deleteFormItemView.userId(), deleteFormItemView.formFieldId(), deleteFormItemView.itemName(), new OnDeleteFormItemListener() {
            @Override
            public void deleteItemSuccess() {
                deleteFormItemView.deleteFormItemSuccess();
            }

            @Override
            public void deleteItemFailed(String errInfo) {
                deleteFormItemView.deleteFormItemFailed(errInfo);
            }
        });
    }
}
