package com.bagevent.view;

import android.support.annotation.LayoutRes;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by ZWJ on 2017/11/20 0020.
 */

public abstract class BaseMuliItemAdapter<T> extends BaseItemDraggableAdapter<T,BaseViewHolder> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    private SparseIntArray layouts;
    public BaseMuliItemAdapter(List<T> data) {
        super(data);
    }

    protected int getDefItemViewType(int position) {
        T item = this.mData.get(position);
        return item != null ? getItemType(item) : DEFAULT_VIEW_TYPE;
    }

    protected abstract int getItemType(T t);


    private int getLayoutId(int viewType) {
        return this.layouts.get(viewType);
    }

    private void addItemType(int type, @LayoutRes int layoutResId) {
        if (this.layouts == null) {
            this.layouts = new SparseIntArray();
        }
        this.layouts.put(type, layoutResId);
    }

    protected BaseMuliItemAdapter registerItemType(@LayoutRes int... layoutResIds) {
        for (int i = 0; i < layoutResIds.length; i++) {
            addItemType(i, layoutResIds[i]);
        }
        return this;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {

    }
}
