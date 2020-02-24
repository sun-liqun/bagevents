package com.bagevent.activity_manager.manager_fragment.data;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by zwj on 2017/10/24.
 */

public class OrderInfoSection extends SectionEntity<OrderInfo> {

    public OrderInfoSection(boolean isHeader, String header,OrderInfo.RespObjectBean.ObjectsBean bean) {
        super(isHeader, header);
    }

}
