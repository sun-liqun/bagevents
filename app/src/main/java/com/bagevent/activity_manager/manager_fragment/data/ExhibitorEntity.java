package com.bagevent.activity_manager.manager_fragment.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * =============================================
 * <p>
 * author sunun
 * <p>
 * copy ©2016 百格活动
 * <p>
 * time 2019/03/12
 * <p>
 * desp
 * <p>
 * <p>
 * =============================================
 */
public class ExhibitorEntity implements MultiItemEntity {
    private ExhibitorDetail.RespObjectBean exhibitorData;
    private ExhibitorDetail.ExExhibitorReceiptOrgs exhibitorReceiptOrgs;
    private ExhibitorDetail.ExExhibitorInfo exExhibitorInfo;
    private ExhibitorDetail.ExExhibitorInfoDTO exExhibitorInfoDTO;

    private String name;
    private int type;

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_BOOTH = 1;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_NAME = 3;

    @Override
    public int getItemType() {
        return type;
    }

    public ExhibitorEntity(int type) {
        this.type = type;
    }

    public ExhibitorEntity(int type,ExhibitorDetail.RespObjectBean exhibitorData) {
        this.type = type;
        this.exhibitorData = exhibitorData;
    }

    public ExhibitorEntity(int type,ExhibitorDetail.ExExhibitorReceiptOrgs exhibitorReceiptOrgs) {
        this.type = type;
        this.exhibitorReceiptOrgs=exhibitorReceiptOrgs;
    }

    public ExhibitorEntity(int type,ExhibitorDetail.ExExhibitorInfo exExhibitorInfo) {
        this.type = type;
        this.exExhibitorInfo = exExhibitorInfo;
    }
    public ExhibitorEntity(int type,ExhibitorDetail.ExExhibitorInfo exExhibitorInfo,ExhibitorDetail.ExExhibitorInfoDTO exExhibitorInfoDTO) {
        this.type = type;
        this.exExhibitorInfo = exExhibitorInfo;
        this.exExhibitorInfoDTO =exExhibitorInfoDTO;
    }
    public ExhibitorEntity(int type,ExhibitorDetail.ExExhibitorInfo exExhibitorInfo,
                           ExhibitorDetail.ExExhibitorInfoDTO exExhibitorInfoDTO,ExhibitorDetail.RespObjectBean exhibitorData) {
        this.type = type;
        this.exExhibitorInfo = exExhibitorInfo;
        this.exExhibitorInfoDTO =exExhibitorInfoDTO;
        this.exhibitorData=exhibitorData;
    }

    public ExhibitorEntity(int type,ExhibitorDetail.ExExhibitorInfoDTO exExhibitorInfoDTO) {
        this.type = type;
        this.exExhibitorInfoDTO = exExhibitorInfoDTO;
    }
    public ExhibitorEntity(int type,String name) {
        this.type = type;
        this.name = name;
    }

    public ExhibitorDetail.RespObjectBean getExhibitorData() {
        return exhibitorData;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    public ExhibitorDetail.ExExhibitorReceiptOrgs getExhibitorReceiptOrgs() {
        return exhibitorReceiptOrgs;
    }

    public ExhibitorDetail.ExExhibitorInfo getExExhibitorInfo() {
        return exExhibitorInfo;
    }

    public ExhibitorDetail.ExExhibitorInfoDTO getExExhibitorInfoDTO() {
        return exExhibitorInfoDTO;
    }
}
